/*
 * Apache Licence2.0
 */
package org.extreme.script;

import java.math.BigDecimal;

import org.extreme.commons.util.Utils;




/**
 * Constants.
 */
public class FunctionHelper {
    private FunctionHelper() {
    }

	public static String NumberToEnglish(BigDecimal ldNumber) throws Exception {
		String lsResult = "";

		int liSw, liGw;
		int liValue = ldNumber.intValue();
		if (liValue >= 20) {
			liSw = (liValue / 10) * 10;
			liGw = liValue - liSw;
			switch (liSw) {
			case 90:
				lsResult = "NINETY";
				break;
			case 80:
				lsResult = "EIGHTY";
				break;
			case 70:
				lsResult = "SEVENTY";
				break;
			case 60:
				lsResult = "SIXTY";
				break;
			case 50:
				lsResult = "FIFTY";
				break;
			case 40:
				lsResult = "FORTY";
				break;
			case 30:
				lsResult = "THIRTY";
				break;
			case 20:
				lsResult = "TWENTY";
				break;
			}

			if (lsResult.trim().length() > 0) {
				lsResult += " ";
			}
		} else {
			liGw = liValue;
		}

		switch (liGw) {
		case 19:
			lsResult += "NINETEEN";
			break;
		case 18:
			lsResult += "EIGHTEEN";
			break;
		case 17:
			lsResult += "SEVENTEEN";
			break;
		case 16:
			lsResult += "SIXTEEN";
			break;
		case 15:
			lsResult += "FIFTEEN";
			break;
		case 14:
			lsResult += "FOURTEEN";
			break;
		case 13:
			lsResult += "THIRTEEN";
			break;
		case 12:
			lsResult += "TWELVE";
			break;
		case 11:
			lsResult += "ELEVEN";
			break;
		case 10:
			lsResult += "TEN";
			break;
		case 9:
			lsResult += "NINE";
			break;
		case 8:
			lsResult += "EIGHT";
			break;
		case 7:
			lsResult += "SEVEN";
			break;
		case 6:
			lsResult += "SIX";
			break;
		case 5:
			lsResult += "FIVE";
			break;
		case 4:
			lsResult += "FOUR";
			break;
		case 3:
			lsResult += "THREE";
			break;
		case 2:
			lsResult += "TWO";
			break;
		case 1:
			lsResult += "ONE";
			break;
		}

		return lsResult;
	}
	
	public static Number asNumber(long lRet) {
		if (lRet > Integer.MAX_VALUE || lRet < Integer.MIN_VALUE) {
        	return new Long(lRet);
        } else {
        	return new Integer((int)lRet);
        }
	}
	
	public static Number asNumber(double dRet) {
		int iRet = (int)dRet;
		if (dRet != iRet) {
			return new Double(dRet);
		}
		
		return asNumber((long)dRet);
	}
	
	public static Object roundUpOrDown(Object[] args, boolean isUp) {
        if (args.length < 2) {
            return Primitive.ERROR_NAME;
        }
        
        double baseNum = Utils.objectToNumber(args[0], false).doubleValue();
        int decimal = Utils.objectToNumber(args[1], false).intValue();
        
        double ret = baseNum;
        if (decimal >= 0) {
        	ret = new BigDecimal(baseNum).setScale(decimal, isUp ? BigDecimal.ROUND_UP : BigDecimal.ROUND_DOWN).doubleValue();
        } else if (decimal < 0) {
        	double pow = Math.pow(10, -decimal * 2);
        	ret = new BigDecimal(baseNum / pow).setScale(-decimal, isUp ? BigDecimal.ROUND_UP : BigDecimal.ROUND_DOWN).doubleValue() * pow;
        }
        
        return asNumber(ret);
	}
}