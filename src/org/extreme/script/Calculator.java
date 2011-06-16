package org.extreme.script;

import java.io.Serializable;
import java.io.StringReader;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Level;

import org.extreme.commons.Formula;
import org.extreme.commons.Parameter;
import org.extreme.commons.util.LogUtil;
import org.extreme.commons.util.Utils;
import org.extreme.script.parser.Expression;
import org.extreme.script.parser.InterpreterError;
import org.extreme.script.parser.Node;
import org.extreme.script.parser.UtilEvalError;
import org.extreme.script.parser.XLexer;
import org.extreme.script.parser.XParser;

import antlr.ANTLRException;

public final class Calculator implements Serializable, Cloneable {
    private NameSpaceChain globalNameSpace = new NameSpaceChain();
    // ��ΪXParser.parse���ǱȽϺ�ʱ���,���Ա���<String, Expression>���map
    private Map parsedExpression = new HashMap();
    ////////////////////////////////////////////////////////////////////////////////////////////////////////
    ///////////////////attributes��Calculator����ʱ�����õ���һЩ����////////////////////////////////////////////
    // ��NameSpace������ʱ��,��Ҫ��Calculator����������������
    private Map attributes = new HashMap();
    
    /*
     * :���̰߳󶨻�������
     * ��Calculator�����ʱ����õ�
     */
    private static ThreadLocal savedVariables = new ThreadLocal();

    /*
     * :���ص�ǰ�߳������õ�Calculator
     * 
     * һ���߳�ֻ��һ��Calculator,�����߳��ڵĻ��������Calculator���湲��
     * 
     * ��֪���᲻��������,�᲻���п���ֻ��Ҫ�ֲ���Calculator
     */
    public static Calculator createCalculator() {
        Calculator c = new Calculator();

        // :Ĭ�����ɵ�Calculator�����Խ���������
        c.pushNameSpace(DEFAULT_NAMESPACE);

        return c;
    }

    /*
     * ���滷��������ThreadContext
     */
    public static void setThreadSavedParameter(String name, Object ob) {
        Map context = (Map) savedVariables.get();
        if (context == null) {
            context = new HashMap();
            savedVariables.set(context);
        }

        context.put(name, ob);
    }
    
    public static Parameter[] processParameters(Calculator calculator, Parameter[] parameters) {
    	if (parameters == null) return new Parameter[0];
    	
    	Parameter param;
    	Object result;
    	Parameter[] ps = new Parameter[parameters.length];
    	for (int i = 0; i < parameters.length; i++) {
    		param = parameters[i];
    		if (param == null) continue;
    		
    		result = calculator == null ? null : calculator.resolveVariable(param.getName());
    		ps[i] = new Parameter(param.getName());
    		if (result != null) {
    			ps[i].setValue(result);
    		} else {
    			ps[i].setValue(param.getValue());
    		}
    	}
    	
    	return ps;
    }

    private Calculator() {
    }
    
    public void setAttribute(Object key, Object attribute) {
        this.attributes.put(key, attribute);
    }

    public Object getAttribute(Object key) {
        return this.attributes.get(key);
    }
    
    public void removeAttribute(Object key) {
    	this.attributes.remove(key);
    }

    public void set(String name, Object value) {
        this.globalNameSpace.set(name, value);
    }

    public void pushNameSpace(Scope ns) {
        globalNameSpace.namespaceList.addFirst(ns);
    }

    public boolean removeNameSpace(Scope ns) {
        return globalNameSpace.namespaceList.remove(ns);
    }

    public Object resolveVariable(Object var) {
        Object obj = resolveVariableInCE(var);
        if (obj == null) {
            return null;
        }
        return obj;
    }

    public Object resolveVariableInCE(Object var) {
        return this.globalNameSpace.getVariable(var, this);
    }

    public Function resolveMethod(Object var) {
        return this.globalNameSpace.getMethod(var, this);
    }

    public Expression parse(Object o) throws ANTLRException {
        if (o instanceof Expression) {
            return (Expression) o;
        } else {
            return parse(Utils.objectToString(o));
        }
    }

    public Expression parse(String statement) throws ANTLRException {
        if (statement == null) {
            return null;
        }

        /*
         * ����ַ������ԵȺŴ�ͷ��,�ѵȺ�ȥ��
         */
        statement = statement.trim();
        if (statement.startsWith("=")) {
            statement = statement.substring(1);
        }

        // ���Ѿ��ʷ���������parsedExpression����һ��
        Expression ex = (Expression) parsedExpression.get(statement);

        if (ex == null) {
            // ����ʽ��ǰ�Ȱ�statement�з��ַ����ո�ȥ��
            StringReader in = new StringReader(statement);

            XLexer lexer = new XLexer(in);
            XParser parser = new XParser(lexer);
            ex = null; // TODO FIXME parser.parse();

            // keep
            parsedExpression.put(statement, ex);
        }

        return ex;
    }
    
    public static interface ValueConverter {
    	public Object result2Value(Object res);
    	
    	public static ValueConverter DEFAULT = new ValueConverter() {
			public Object result2Value(Object res) {
				return res;
			}
		};
    }
    
    /*
     * ����obj�������жϼ��㷽ʽ
     * ����ǵ�Ԫ��Ĭ�ϼ���������ǵ�Ԫ�񣬲���ȡֵ
     */
    public Object eval(String obj) throws UtilEvalError {
    	Object res = null;
    	try {
    		res = evalString(obj);
    	} catch (UtilEvalError e) {
    		e.addFormulaContent(obj.toString());
			throw e;
    	}
    	
    	return res;
    }
    
    public Object eval(Object obj) throws UtilEvalError {
    	return obj;
    }
    
    public boolean evalBoolean(Object obj) throws UtilEvalError {
    	Object o = eval(obj);
    	
    	return Boolean.TRUE.equals(o);
    }
    
    public Object eval(Formula obj) throws UtilEvalError {
    	Object res = null;
    	try {
    		res = evalString(((Formula) obj).getContent());
    	} catch (UtilEvalError e) {
    		e.addFormulaContent(obj.toString());
			throw e;
    	}
    	
    	return res;
    }
    
    public Object eval(Node obj) throws UtilEvalError {
    	Object res = null;
    	try {
    		res = ((Node) obj).eval(this);
    	} catch (UtilEvalError e) {
    		e.addFormulaContent(obj.toString());
			throw e;
    	}
    	
    	return res;
    }

    private Object evalString(String statement) throws UtilEvalError {
        Expression ex = null;

        if (statement != null) {
            try {
                ex = parse(statement);
            } catch (ANTLRException e) {
            	LogUtil.getLogger().log(Level.WARNING, e.getMessage(), e);
            }
        }

        if (ex == null) {
            return null;
        } else {
            return ex.eval(this);
        }
    }

    protected static class NameSpaceChain implements Scope {
        //������������name��map,���汣�������Ӧ��ֵ

        private Map variables = new HashMap();
        //���ڽ�����NameSpace�ļ���
        private java.util.LinkedList namespaceList = new java.util.LinkedList();

        public Function getMethod(Object var, Calculator calculator) {
            Function res = null;

            for (int i = 0, len = namespaceList.size(); i < len; i++) {
                res = ((Scope) namespaceList.get(i)).getMethod(var, calculator);
                if (res != null) {
                    return res;
                }
            }

            return null;
        }

        public Object getVariable(Object var, Calculator calculator) {
            Object res = null;

           //����variables�б����
            if (variables.containsKey(var)) {
            	return variables.get(var);
            }

            //����namespaceList�б����
            for (int i = 0, len = namespaceList.size(); i < len; i++) {
                res = ((Scope) namespaceList.get(i)).getVariable(var, calculator);
                if (res != null) {
                    return res;
                }
            }

            return null;
        }

        private void set(Object var, Object value) {
            if (var == null) {
                return;
            }
            // primitives should have been wrapped
            if (value == null) {
                value = Primitive.NULL;
            }

            variables.put(var, value);
        }
    }
    
    /*
     * �����������õĺ�������������
     */
    private static final Scope DEFAULT_NAMESPACE = new Scope() {

        private HashMap initedFunctionClasses; //String -> Class

        public Object getVariable(Object var, Calculator calculator) {
            if (var instanceof String) {
                String name = (String) var;

                if (name.equalsIgnoreCase("null")) {
                    return Primitive.NULL;
                } else if (name.equalsIgnoreCase("nofilter")) {
                    return Primitive.NOFILTER;
                } else if (name.equalsIgnoreCase("true")) {
                    return Boolean.TRUE;
                } else if (name.equalsIgnoreCase("false")) {
                    return Boolean.FALSE;
                }
            }

            return resloveFromThreadContext(var, calculator);
        }

        /*
         * ����savedVariables��savedNameSpaces��������,������DEFAULT_NAMESPACE
         */
        private Object resloveFromThreadContext(Object var, Calculator calculator) {
            Object ret = null;

            if (var instanceof String) {
            	String pName = var.toString();
        		if (pName.startsWith(ScriptConstants.DETAIL_TAG)) {
        			var = pName.substring(1);
        		}
            }
            Map context = (Map) savedVariables.get();
            if (context != null) {
                ret = context.get(var);
            }

            return ret;
        }

        public Function getMethod(Object var, Calculator calculator) {
            if (!(var instanceof String)) {
                throw new InterpreterError("Function_Name_Should_Not_Be" + var + (var != null ? " " + var.getClass() : ""));
            }

            // :�˴�������ͳһת���ɴ�д��
            String name = ((String) var).toUpperCase();

            Class functionClass;

            //��ʼ��initedFunctionClasses
            if (initedFunctionClasses == null) {
                initedFunctionClasses = new HashMap();
            }

            //��initedFunctionClasses��ȡ
            functionClass = (Class) initedFunctionClasses.get(name);

            //û�еĻ��÷������ȡ
            if (functionClass == null) {
                String className = "org.extreme.script.function." + name;;

                try {
                    //��������������������ֱ�ӵ���Class.forName,��Ϊ��Ҫ����WEB-INF\classesĿ¼.
                    functionClass = Class.forName(className);
                } catch (ClassNotFoundException exp) {
                	
                }
            }

            if (functionClass == null) {
                return null;
            }

            //�����functionClass����initedFunctionClasses��ȥ
            initedFunctionClasses.put(name, functionClass);

            //ʵ����,�ٽ���
            Object functionInstance;
            try {
                functionInstance = functionClass.newInstance();
            } catch (Exception exp) {
                LogUtil.getLogger().log(Level.WARNING,exp.getMessage(), exp);
                return null;
            }

            return (Function) functionInstance;
        }

        public String toString() {
            return "FUNCTION_NAMESPACE";
        }
    };
}
