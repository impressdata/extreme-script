package org.extreme.commons.util;

import java.lang.reflect.Field;

/**
 * 反射用到的Utils工具类
 * JDK也自带有一些反射方法工具，在{@link java.beans.ReflectionUtils <CODE>java.beans.ReflectionUtils</CODE>}
 */
public abstract class ReflectionUtils {
	
	/**
	 * 设置属性的值，包括私有属性和父类中的属性
	 * 向上追溯直到Object
	 * @param obj
	 * @param fieldName
	 * @param value
	 * @return
	 */
	public static void setPrivateFieldValue(Object obj, String fieldName, Object value) throws Exception {
		Class clazz = obj.getClass();
		
		while (!clazz.equals(Object.class)) {
			try {
				Field field = clazz.getDeclaredField(fieldName);
				field.setAccessible(true);
				field.set(obj, value);
			} catch (NoSuchFieldException e) {
				clazz = clazz.getSuperclass();
				
				if (clazz.equals(Object.class)) {
					throw e;
				}
				
				continue;
			}
			
			break;
		}
	}
	
	/**
	 * 得到对象的属性，包括私有属性和父类中的属性
	 * 向上追溯直到Object
	 * @param obj
	 * @param fieldName
	 * @return
	 */
	public static Object getPrivateFieldValue(Object obj, String fieldName) throws Exception {
		Class clazz = obj.getClass();
		
		Object value = null;
		
		while (!clazz.equals(Object.class)) {
			try {
				Field field = clazz.getDeclaredField(fieldName);
				field.setAccessible(true);
				value = field.get(obj);
			} catch (NoSuchFieldException e) {
				clazz = clazz.getSuperclass();
				
				if (clazz.equals(Object.class)) {
					throw e;
				}
				
				continue;
			}
			
			break;
		}
		
		return value;
	}
	
	// TODO: FRCoreContext.classForName
	
	
}
