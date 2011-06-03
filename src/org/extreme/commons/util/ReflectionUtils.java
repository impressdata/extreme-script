package org.extreme.commons.util;

import java.lang.reflect.Field;

/**
 * �����õ���Utils������
 * JDKҲ�Դ���һЩ���䷽�����ߣ���{@link java.beans.ReflectionUtils <CODE>java.beans.ReflectionUtils</CODE>}
 */
public abstract class ReflectionUtils {
	
	/**
	 * �������Ե�ֵ������˽�����Ժ͸����е�����
	 * ����׷��ֱ��Object
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
	 * �õ���������ԣ�����˽�����Ժ͸����е�����
	 * ����׷��ֱ��Object
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
