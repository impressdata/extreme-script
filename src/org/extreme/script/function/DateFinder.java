package org.extreme.script.function;

import java.util.Date;

import org.extreme.commons.util.DateUtils;
import org.extreme.script.AbstractFunction;
import org.extreme.script.Primitive;


public abstract class DateFinder extends AbstractFunction {
	
	public Object run(Object[] args) {
		if (args.length < 1) {
			return Primitive.ERROR_NAME;
		}
		
		Date scaleDate = null; // ��׼����
		int delta = 0; // ƫ��
		
		// ���ֻ��һ������������Ϊ��ֵ
		if (args.length == 1 && args[0] instanceof Number) {
			scaleDate = new Date();
			delta = ((Number)args[0]).intValue();
		} 
		// ��������������ҵڶ�����������Ϊ��ֵ
		else if (args.length >= 2 && args[1] instanceof Number) {
			delta = ((Number)args[1]).intValue();
			
			// �����һ��������������
			if (args[0] instanceof Date) {
				scaleDate = (Date)args[0];
			} 
			// �����һ���������ַ���
			else if (args[0] instanceof String) {
				// ���ַ���ת���������͵�
				scaleDate = DateUtils.object2Date((String)args[0], false);
			}
			// �����һ����������ֵ,�͵�������ݰ�(��Ϊ��DATEINYEAR�Ǳ���ô֧��)
			else if (args[0] instanceof Number) {
				scaleDate = new Date(((Number)args[0]).intValue() - 1900, 0, 1);
			}
		}
		
		// �����������Ĵ���,scaleDate����null,��ȡ����
		if (scaleDate == null) {
			scaleDate = new Date();
		}
		
		// ���delta��0,�ͷ���scaleDate
		if (delta == 0) {
			return scaleDate;
		}
		
		return findDate(scaleDate, delta);
	}
	
	protected abstract Date findDate(Date scaleDate, int delta);

}
