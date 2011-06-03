/*
 * Apache Licence2.0
 */
package org.extreme.script.function;

import org.extreme.script.AbstractFunction;
import org.extreme.script.Function;
import org.extreme.script.FunctionHelper;
import org.extreme.script.Primitive;

/**
 * Function.
 */
public class ATAN2 extends AbstractFunction {
    /**
     * Run the function on the stack. Pops the arguments from the stack,
     * then return the result.
     */
    public Object run(Object[] args) {
        if (args.length < 2) {
            return Primitive.ERROR_NAME;
        }

        Object value1 = args[0];
        Object value2 = args[1];
        if (value1 instanceof Number && value2 instanceof Number) {
            return FunctionHelper.asNumber(
                    Math.atan2(((Number) value1).doubleValue(), (((Number) value2).doubleValue())));
        }

        return Primitive.ERROR_NAME;
    }
    public Type getType() {
    	return Function.MATH;
    }	public String getCN(){
		return "ATAN2(x_num,y_num): 返回x、y坐标的反正切值。返回角度为x轴与过（x_num,y_num）与坐标原点（0,0）的一条直线形成的角度。该角度以弧度显示。\n"
		+"X_num:指定点的x坐标。\n"
		+"Y_num:指定点的y坐标。\n"
		+"备注:\n"
		+"    正值表示从x轴开始以逆时针方式所得的角度；负值表示从x轴开始以顺时针方式所得的角度。\n"
		+"    ATAN2(a,b)=ATAN(b/a)，a为0时除外。\n"
		+"    当x_num与y_num都为0时，ATAN2返回错误信息*DIV/0!。\n"
		+"    用角度制显示返回数值时，把返回数值乘以180/PI()。\n"
		+"    返回值以弧度表示（返回值大于-pi且小于等于pi）。\n"
		+"示例:\n"
		+"ATAN2(-2,2)等于2.356194490（弧度制的3*pi/4）。\n"
		+"ATAN2(2,2)等于0.785398163（弧度制的pi/4）。\n"
		+"ATAN2(-2,2)*180/PI()等于135（角度制）。";
	}
	public String getEN(){
		return "ATAN2(x_num,y_num): Returns the arctangent, or inverse tangent, of the specified x- and y-coordinates. The arctangent is the angle from the x-axis to a line containing the origin (0, 0) and a point with coordinates (x_num, y_num). The angle is given in radians.\n"
		+"X_num is the x-coordinate of the point.\n"
		+"Y_num is the y-coordinate of the point.\n"
		+"\n"
		+"Re:\n"
		+"1. A positive result represents a counterclockwise angle from the x-axis; a negative result represents a clockwise angle. \n"
		+"2. ATAN2(a,b) equals ATAN(b/a), except that a can equal 0 in ATAN2. \n"
		+"3. If both x_num and y_num are 0, ATAN2 returns the #DIV/0! error value. \n"
		+"4. To express the arctangent in degrees, multiply the result by 180/PI( ) or use the DEGREES function.\n"
		+"5. The angle is between -pi and pi, excluding -pi.\n"
		+"\n"
		+"Example:\n"
		+"   ATAN2(-2,2)=2.356194490 (3*pi/4 in radians)\n"
		+"   ATAN2(2,2)=0.785398163 (pi/4 in radians)\n"
		+"   ATAN2(-2,2)*180/PI()=135 (in degrees)";
	}
}