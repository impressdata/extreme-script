/*
 * Apache Licence2.0
 */
package org.extreme.script.function;

import org.extreme.script.AbstractFunction;
import org.extreme.script.Primitive;

/**
 * Function.
 */
public class SUBSTITUTE extends AbstractFunction {

    /**
     * Run the function on the stack. Pops the arguments from the stack,
     * then return the result.
     */
    public Object run(Object[] args) {
        if (args.length < 3) {
            return Primitive.ERROR_NAME;
        }

        Object textObj = args[0];
        Object oldTextObj = args[1];
        Object newTextObj = args[2];

        int instanceNum = -1;
        if (args.length > 3) {
            Object instanceNumObj = args[3];
            if (instanceNumObj instanceof Number) {
                instanceNum = Math.abs(((Number) instanceNumObj).intValue());
            }
        }

        String allText = "" + textObj;
        if(instanceNum < 0) {
            return allText.replaceAll("\\Q" + oldTextObj + "\\E", "" + newTextObj);
        } else {
//            while(instanceNum-- > 0) {
//            	allText = allText.replaceFirst("\\Q" + oldTextObj + "\\E", "" + newTextObj);
//            }
        	if(instanceNum > 0){
        		int i,fromIndex=-1;
                for(i=0;i<instanceNum;i++)
                	fromIndex=allText.indexOf(oldTextObj.toString(), fromIndex+1);
                String string1=allText.substring(0,fromIndex);
                String string2=allText.substring(fromIndex,allText.length());
                string2=string2.replaceFirst("\\Q" + oldTextObj + "\\E",newTextObj.toString());
                allText=string1.concat(string2);
        	}
            return allText;                        
        }
    }
	public Type getType() {
		return TEXT;
	}	public String getCN(){
		return "SUBSTITUTE(text,old_text,new_text,instance_num): 用new_text替换文本串中的old_text。\n"
		+"Text:需要被替换字符的文本，或含有文本的单元格引用。\n"
		+"Old_text:需要被替换的部分文本。\n"
		+"New_text:用于替换old_text的文本。\n"
		+"Instance_num:指定用new_text来替换第几次出现的old_text。如果指定了instance_num，则只有指定位置上的old_text被替换，否则文字串中出现的所有old_text都被new_text替换。\n"
		+"备注:\n"
		+"    如果需要替换文本串中的指定文本，则使用SUBSTITUTE函数；如果需要替换文本串中指定位置上的任意文本，则使用REPLACE函数。\n"
		+"示例:\n"
		+"SUBSTITUTE(\"data base\",\"base\",\"model\")等于“data model”。\n"
		+"SUBSTITUTE(\"July 28, 2000\",\"2\",\"1\",1)等于“July 18, 2000”。\n"
		+"SUBSTITUTE(\"July 28, 2000\",\"2\",\"1\")等于“July 18, 1000”。\n"
		+"SUBSTITUTE(\"July 28, 2000\",\"2\",\"1\",2)等于“July 28, 1000”。 ";
	}
	public String getEN(){
		return "SUBSTITUTE(text,old_text,new_text,instance_num): Substitutes new_text for old_text in a text string.\n"
		+"Text is the text or the reference to a cell containing text for which you want to substitute characters.\n"
		+"Old_text is the text you want to replace.\n"
		+"New_text is the text you want to replace old_text with.\n"
		+"Instance_num specifies which occurrence of old_text you want to replace with new_text. If you specify instance_num, only that instance of old_text is replaced. Otherwise, every occurrence of old_text in text is changed to new_text.\n"
		+"\n"
		+"Re:\n"
		+"    Use SUBSTITUTE when you want to replace specific text in a text string; use REPLACE when you want to replace any text that occurs in a specific location in a text string.\n"
		+"\n"
		+"Example:\n"
		+"   SUBSTITUTE(\"data base\",\"base\",\"model\") = \"data model\"\n"
		+"   SUBSTITUTE(\"July 28, 2000\",\"2\",\"1\",1) = \"July 18, 2000\"\n"
		+"   SUBSTITUTE(\"July 28, 2000\",\"2\",\"1\") = \"July 18, 1000\"\n"
		+"   SUBSTITUTE(\"July 28, 2000\",\"2\",\"1\",2) = \"July 28, 1000\"";
	}
}