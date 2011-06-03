package org.extreme.script.function;
/**
 * This function used to get the farray's element at special index.
 * @author richer
 */
import java.util.logging.Level;

import org.extreme.commons.util.LogUtil;
import org.extreme.script.AbstractFunction;
import org.extreme.script.FArray;
import org.extreme.script.Primitive;


public class GETARRAYELEMENT extends AbstractFunction{
	public Object run(Object[] args) {
		if (args.length != 2) {
			return Primitive.ERROR_NAME;
		}
		
		Object array = args[0];
		if (!(array instanceof FArray)) {
			return Primitive.ERROR_NAME;
		}
		try {
		if (array instanceof FArray) {
				FArray farray = (FArray) args[0];
				try {
					return farray.elementAt(Integer.parseInt(args[1].toString()) - 1);
				} catch (ArrayIndexOutOfBoundsException e) {
					LogUtil.getLogger().log(Level.WARNING,e.getMessage(), e);
					return Primitive.ERROR_VALUE;
				}
				
			}
		} catch (NumberFormatException e) {
			LogUtil.getLogger().log(Level.WARNING,e.getMessage(), e);
			return Primitive.ERROR_VALUE;
		}		
		return null;
	}
	public Type getType() {
		return ARRAY;
	}	public String getCN(){
		return "GETARRAYELEMENT(array, index):函数返回数组array的第index个元素。\n"
		+"示例：\n"
		+"String[] array = {\"a\", \"b\", \"c\", \"d\"}\n"
		+"GETARRAYELEMENT(array, 3)等于c.\n"
		+"GETARRAYELEMENT(array, 1)等于a.";
	}
	public String getEN(){
		return "GETARRAYELEMENT(array, index):Returns the index element of the array.\n"
		+"Example:\n"
		+"String[] array = {\"a\", \"b\", \"c\", \"d\"}\n"
		+"GETARRAYELEMENT(array, 3)equals c.\n"
		+"GETARRAYELEMENT(array, 1)equals a.";
	}
}