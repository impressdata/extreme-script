package org.extreme.script;

import java.io.Serializable;

public class Variable implements Serializable, Cloneable {
    Object value;

    //TODO :���Variable,û�����ж�������,�Ժ����Щ��ʱҪд��ע�Ͱ�!
    //a:�������ڷ��鱨���ʱ��,�������ü�Ļ���ֵ,�᲻�ϵر�,��ô����ֵ��Variable����,�ڱ��ʱ��͸ı�Variable.value,���������þͲ������
    public Variable(Object value) {
        setValue(value);
    }
    public Object getValue() {
        return value;
    }
    public void setValue(Object value) {
        if(value == null) {
            this.value = Primitive.NULL;
        } else {
            this.value = value;
        }
    }
    
    public String toString() {
        return value.toString();
    }
}
