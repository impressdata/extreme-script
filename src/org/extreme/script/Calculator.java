package org.extreme.script;

import java.io.Serializable;
import java.io.StringReader;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Level;

import org.extreme.commons.ColumnRow;
import org.extreme.commons.Formula;
import org.extreme.commons.Parameter;
import org.extreme.commons.util.LogUtil;
import org.extreme.commons.util.StringUtils;
import org.extreme.commons.util.Utils;
import org.extreme.script.parser.Ambiguity;
import org.extreme.script.parser.CRAddress;
import org.extreme.script.parser.ColumnRowLiteral;
import org.extreme.script.parser.ColumnRowRange;
import org.extreme.script.parser.Expression;
import org.extreme.script.parser.InterpreterError;
import org.extreme.script.parser.Node;
import org.extreme.script.parser.Tiny;
import org.extreme.script.parser.TinyHunter;
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

    public void pushNameSpace(NameSpace ns) {
        globalNameSpace.namespaceList.addFirst(ns);
    }

    public boolean removeNameSpace(NameSpace ns) {
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

    public Object resolveRelateCellElements(ColumnRow columnrow) {
        return this.globalNameSpace.getRelateCellElements(columnrow, this);
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

    /*
     * ��statement����ص�ColumnRow
     */
    public static ColumnRow[] relatedColumnRowArray(String statement) throws ANTLRException {
        Expression ex = new Calculator().parse(statement);

        if (ex == null) {
            return new ColumnRow[0];
        }

        ColumnRowHunter hunter = new ColumnRowHunter();
        ex.traversal4Tiny(hunter);

        return hunter.getColumnRowBooty();
    }

    //b:columnrowrange
    public static ColumnRowRange[] relatedColumnRowRangeArray(String statement) throws ANTLRException {
        Expression ex = new Calculator().parse(statement);

        if (ex == null) {
            return new ColumnRowRange[0];
        }

        ColumnRowHunter hunter = new ColumnRowHunter();
        ex.traversal4Tiny(hunter);

        return hunter.getColumnRowRangeBooty();
    }

    /*
     * ��statement����ص�Parameter
     */
    public static String[] relatedParameters(String statement) throws ANTLRException {
        Expression ex = new Calculator().parse(statement);

        if (ex == null) {
            return new String[0];
        }

        ParameterHunter hunter = new ParameterHunter();
        ex.traversal4Tiny(hunter);

        return hunter.getParameterBooty();
    }

    /*
     * ������ص�ColumnRow
     */
    private static class ColumnRowHunter extends TinyHunter {

        private java.util.List list = new java.util.ArrayList();
        private java.util.List rangeList = new java.util.ArrayList();

        public ColumnRow[] getColumnRowBooty() {
            return (ColumnRow[]) list.toArray(new ColumnRow[list.size()]);
        }

        public ColumnRowRange[] getColumnRowRangeBooty() {
            return (ColumnRowRange[]) rangeList.toArray(new ColumnRowRange[rangeList.size()]);
        }

        public void hunter4Tiny(Tiny tiny) {
            if (tiny instanceof ColumnRowRange) {
                ColumnRowRange range = (ColumnRowRange) tiny;
                rangeList.add(tiny);
                ColumnRowLiteral from = range.getFrom();
                if (from == null) {
                    return;
                }

                ColumnRow start = from.getTargetColumnRow();
                ColumnRow end = start;
                ColumnRowLiteral to = range.getTo();
                if (to != null) {
                    end = to.getTargetColumnRow();
                }

                int startColumn = Math.min(start.column, end.column);
                int startRow = Math.min(start.row, end.row);
                int endColumn = Math.max(start.column, end.column);
                int endRow = Math.max(start.row, end.row);

                for (int ci = startColumn; ci <= endColumn; ci++) {
                    for (int ri = startRow; ri <= endRow; ri++) {
                        ColumnRow cr = ColumnRow.valueOf(ci, ri);
                        if (!list.contains(cr) && ColumnRow.validate(cr)) {
                            list.add(cr);
                        }
                    }
                }
            } else if (tiny instanceof CRAddress) {
                ColumnRow cr = ((CRAddress) tiny).getTarget();
                if (!list.contains(cr) && ColumnRow.validate(cr)) {
                    list.add(cr);
                }
            }
        }
    }

    /*
     * ������ص�Parameter
     */
    private static class ParameterHunter extends TinyHunter {

        private java.util.List parameterList = new java.util.ArrayList();

        public String[] getParameterBooty() {
            return (String[]) parameterList.toArray(new String[parameterList.size()]);
        }

        public void hunter4Tiny(Tiny tiny) {
            if (tiny instanceof Ambiguity) {
                String statement = ((Ambiguity) tiny).getStatement();
                if (StringUtils.isNotBlank(statement)
                    && statement.startsWith(ScriptConstants.DETAIL_TAG)
                    && !parameterList.contains(statement)) {
                    parameterList.add(statement);
                }
            }
        }
    }

    public String exStatement(ColumnRow currentColumnRow, String statement) {
    	this.setAttribute(ColumnRow.class, currentColumnRow);

        Expression ex = null;
        try {
            ex = parse(statement);
        } catch (ANTLRException e) {
            if ((ColumnRow)this.getAttribute(ColumnRow.class) != null) {
                StringBuffer sb = new StringBuffer().append("Error Cell: ").append((ColumnRow)this.getAttribute(ColumnRow.class)).append(" and statement is \"").append(statement).append('\"').append('\n').append(e.getMessage());
                LogUtil.getLogger().log(Level.WARNING,sb.toString(), e);
            } else {
                LogUtil.getLogger().log(Level.WARNING,"error statement is \"" + statement + "\"", e);
            }
        }

        return ex == null ? statement : ex.exString(this);
    }

    protected static class NameSpaceChain implements NameSpace {
        //������������name��map,���汣�������Ӧ��ֵ

        private Map variables = new HashMap();
        //���ڽ�����NameSpace�ļ���
        private java.util.LinkedList namespaceList = new java.util.LinkedList();

        public Function getMethod(Object var, Calculator calculator) {
            Function res = null;

            for (int i = 0, len = namespaceList.size(); i < len; i++) {
                res = ((NameSpace) namespaceList.get(i)).getMethod(var, calculator);
                if (res != null) {
                    return res;
                }
            }

            return null;
        }

        public Object getRelateCellElements(ColumnRow columnrow, Calculator calculator) {
            Object res = null;

            for (int i = 0, len = this.namespaceList.size(); i < len; i++) {
                res = ((NameSpace) this.namespaceList.get(i)).getRelateCellElements(columnrow, calculator);
                if (res != null) {
                    return res;
                }
            }

            return res;
        }

        public Object getVariable(Object var, Calculator calculator) {
            Object res = null;

            //����variables�б����
            res = unwrapVariable((Variable) variables.get(var));

            if (res != null) {
                return res;
            }

            //����namespaceList�б����
            for (int i = 0, len = namespaceList.size(); i < len; i++) {
                res = ((NameSpace) namespaceList.get(i)).getVariable(var, calculator);
                if (res != null) {
                    return res;
                }
            }

            return null;
        }

        private Object unwrapVariable(Variable var) {
            return (var == null) ? null : var.getValue();
        }

        private void set(Object var, Object value) {
            if (var == null) {
                return;
            }
            // primitives should have been wrapped
            if (value == null) {
                value = Primitive.NULL;
            }

            Variable existing = (Variable) variables.get(var);

            if (existing != null) {
                existing.setValue(value);
            } else {
                variables.put(var, new Variable(value));
            }
        }
    }
    
    /*
     * �����������õĺ�������������
     */
    private static final NameSpace DEFAULT_NAMESPACE = new AbstractNameSpace() {

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
