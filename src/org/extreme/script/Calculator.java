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
    // 因为XParser.parse还是比较耗时间的,所以保存<String, Expression>这个map
    private Map parsedExpression = new HashMap();
    ////////////////////////////////////////////////////////////////////////////////////////////////////////
    ///////////////////attributes是Calculator解析时可能用到的一些变量////////////////////////////////////////////
    // 在NameSpace解析的时候,需要从Calculator里面拿这两个变量
    private Map attributes = new HashMap();
    
    /*
     * :跟线程绑定环境变量
     * 在Calculator计算的时候会用到
     */
    private static ThreadLocal savedVariables = new ThreadLocal();

    /*
     * :返回当前线程正在用的Calculator
     * 
     * 一个线程只用一个Calculator,至少线程内的环境在这个Calculator里面共享
     * 
     * 不知道会不会有问题,会不会有可能只需要局部的Calculator
     */
    public static Calculator createCalculator() {
        Calculator c = new Calculator();

        // :默认生成的Calculator都可以解析函数名
        c.pushNameSpace(DEFAULT_NAMESPACE);

        return c;
    }

    /*
     * 保存环境变量于ThreadContext
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
         * 如果字符串是以等号打头的,把等号去掉
         */
        statement = statement.trim();
        if (statement.startsWith("=")) {
            statement = statement.substring(1);
        }

        // 从已经词法解析过的parsedExpression中找一下
        Expression ex = (Expression) parsedExpression.get(statement);

        if (ex == null) {
            // 处理公式这前先把statement中非字符串空格去掉
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
     * 根据obj的类型判断计算方式
     * 如果是单元格，默认计算出来就是单元格，不会取值
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
        //用来解析基本name的map,里面保存参数对应的值

        private Map variables = new HashMap();
        //用于解析的NameSpace的集合
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

           //先找variables中保存的
            if (variables.containsKey(var)) {
            	return variables.get(var);
            }

            //再找namespaceList中保存的
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
     * 根据我们内置的函数名解析机制
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
         * 根据savedVariables和savedNameSpaces解析变量,仅用于DEFAULT_NAMESPACE
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

            // :此处把名字统一转换成大写的
            String name = ((String) var).toUpperCase();

            Class functionClass;

            //初始化initedFunctionClasses
            if (initedFunctionClasses == null) {
                initedFunctionClasses = new HashMap();
            }

            //从initedFunctionClasses中取
            functionClass = (Class) initedFunctionClasses.get(name);

            //没有的话用反射机制取
            if (functionClass == null) {
                String className = "org.extreme.script.function." + name;;

                try {
                    //必须调用这个方法，不能直接调用Class.forName,因为需要查找WEB-INF\classes目录.
                    functionClass = Class.forName(className);
                } catch (ClassNotFoundException exp) {
                	
                }
            }

            if (functionClass == null) {
                return null;
            }

            //把这个functionClass放在initedFunctionClasses中去
            initedFunctionClasses.put(name, functionClass);

            //实例化,再解析
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
