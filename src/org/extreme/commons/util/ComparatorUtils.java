package org.extreme.commons.util;

import java.awt.Shape;
import java.awt.geom.Arc2D;
import java.awt.geom.GeneralPath;
import java.awt.geom.Rectangle2D;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.text.Collator;
import java.util.Comparator;
import java.util.Date;
import java.util.List;

import org.extreme.script.FArray;

/**
 * Some util methods for Comparator.
 */
public class ComparatorUtils {
	private ComparatorUtils() {
	}
	
	private static final double NUMBER_FOR_PRECISE_COMPARE = 1e-9;
	
    /**
     * Compares its two arguments for order.  Returns a negative integer,
     * zero, or a positive integer as the first argument is less than, equal
     * to, or greater than the second.<p>
     *
     * @param obj1 the first object to be compared.(The value of obj1 may be null).
     * @param obj2 the second object to be compared.(The value of obj2 may be null).
     * @return a negative integer, zero, or a positive integer as the
     * 	first argument is less than, equal to, or greater than the
     *	 second.
     */
    public static int compare(Object obj1, Object obj2) {
        // null < not null
        if (obj1 == null || obj2 == null) {
            return (obj1 == null && obj2 == null) ? 0 : ((obj1 == null) ? -1 : 1);
        }
        
        //date :只要有一个是日期型的,就都转成日期型进行比较
        if ((obj1 instanceof Date) || (obj2 instanceof Date)) {
            Date d1 = DateUtils.object2Date(obj1, true);
            Date d2 = DateUtils.object2Date(obj2, true);
            
            long t1 = d1 == null ? 0 : d1.getTime();
            long t2 = d2 == null ? 0 : d2.getTime();

            // do not (int)(t1 - t2)，否则会导致整数越界, copy from Long.compareTo
            return (t1 < t2 ? -1 : (t1 == t2 ? 0 : 1));
        }

        //number
        if ((obj1 instanceof Number) && !(obj2 instanceof Number)) {
            try {
                obj2 = Double.valueOf(obj2.toString());
            } catch (Throwable e) {
                //do nothing
            }
        }

        if ((obj2 instanceof Number) && !(obj1 instanceof Number)) {
            try {
                obj1 = Double.valueOf(obj1.toString());
            } catch (Throwable e) {
                //do nothing
            }
        }

        if ((obj1 instanceof Number) && (obj2 instanceof Number)) {
        	if (obj1 instanceof BigDecimal || obj2 instanceof BigDecimal) {
        		return (new BigDecimal(obj1.toString())).compareTo(new BigDecimal(obj2.toString()));
        	} else if (obj1 instanceof BigInteger || obj2 instanceof BigInteger) {
        		return (new BigInteger(obj1.toString())).compareTo(new BigInteger(obj2.toString()));
        	}
        	return compare(((Number) obj1).doubleValue(), ((Number) obj2).doubleValue());
        }

        //名字比较必须忽略大小写的，windows就是忽略大小写的.
        return obj1.toString().toLowerCase().compareTo(obj2.toString().toLowerCase());
    }
    
    public static int compare(double d1, double d2) {
    	double rc = d1 - d2;
    	return (Math.abs(rc) < NUMBER_FOR_PRECISE_COMPARE) ? 0 : ((rc > 0) ? 1 : -1);
    }
    
    /*
     * Compares its two arguments for order.  Returns a negative integer,
     * zero, or a positive integer as the first argument is less than, equal
     * to, or greater than the second.<p>
     *
     * @param obj1 the first object to be compared.(The value of obj1 may be null).
     * @param obj2 the second object to be compared.(The value of obj2 may be null).
     * @return a negative integer, zero, or a positive integer as the
     * 	first argument is less than, equal to, or greater than the
     *	 second.
     */
    public static int compare(Comparator comparator, Object obj1, Object obj2) {
    	// :不知道这段东西是干什么用的~~
        //:如果是Collator，来比较CJK中文的.
        if (comparator instanceof Collator && obj1 != null && obj2 != null) {
            return comparator.compare(obj1.toString().toLowerCase(), obj2.toString().toLowerCase());
        }
        
        return compare(obj1, obj2);
    }
    
    //b:argument1 isin argument2
    public static boolean arg1InArg2(Object o1, Object o2) {
    	if (o2 instanceof FArray) {
			FArray oa = (FArray) o2;
			// richer:包含于 bug0003065
			boolean var = false;
			for (int i = 0, len = oa.length(); i < len; i++) {
				if (ComparatorUtils.equals(o1, oa.elementAt(i))) {
					var = true;
					break;
				}
			}
			
			return var;
		}
    	if (o1 instanceof String && o2 instanceof String) {
    		return o2.toString().indexOf(o1.toString()) >= 0;
    	}
    	if (o1 instanceof Number && o2 instanceof Number) {
    		return ((Double)o2).doubleValue() >= ((Double)o1).doubleValue();
    	}
        try {
            if ((o1 instanceof Number && o2 instanceof String) || (o2 instanceof Number && o1 instanceof String)) {
                return arg1InArg2(o1.toString(), o2.toString()) || arg1InArg2(Double.valueOf(o1.toString()), Double.valueOf(o2.toString()));
            }
        } catch (NumberFormatException e) {
            return false;
        }
    	if (equals(o1, o2)) {
    		return true;
    	}
    	return false;
    }

    /*
     * :比较相等.
     * @param obj1 may be null.
     * @param obj2 may be null.
     */
    public static boolean equals(Object obj1, Object obj2) {
    	if (obj1 == obj2) {
			return true;
		}
        if (obj1 == null || obj2 == null) {
            return obj1 == null && obj2 == null;
        }

        //date :只要有一个是日期型的,就都转成日期型进行比较
        if ((obj1 instanceof Date) || (obj2 instanceof Date)) {
        	return compare(obj1, obj2) == 0;
        }

        //一个是Number, 另一个非Number
        try {
            if ((obj1 instanceof Number) && !(obj2 instanceof Number)) {
                obj2 = Double.valueOf(obj2.toString());
            }
            if ((obj2 instanceof Number) && !(obj1 instanceof Number)) {
                obj1 = Double.valueOf(obj1.toString());
            }
        } catch (NumberFormatException e) {
            return false;
        }

        //两个同是Number
        if ((obj1 instanceof Number) && (obj2 instanceof Number)) {
        	return compare(obj1, obj2) == 0;
        }
        
        if ((obj1 instanceof Shape) && (obj2 instanceof Shape)) {
        	return equals((Shape)obj1, (Shape)obj2);
        }
        
        // :数组的equals比较
        if (obj1.getClass().isArray() && obj2.getClass().isArray()) {
        	int len1 = java.lang.reflect.Array.getLength(obj1),
        	len2 = java.lang.reflect.Array.getLength(obj2);
        	if (len1 != len2) {
        		return false;
        	}

            // :开始比较数组里面每一个值.
            for (int i = 0; i < len1; i++) {
                if (!ComparatorUtils.equals(
                		java.lang.reflect.Array.get(obj1, i), java.lang.reflect.Array.get(obj2, i)		
                )) {
                    return false;
                }
            }
            
            return true;
        }
        
        if (obj1 instanceof List && obj2 instanceof List) {
        	if (((List)obj1).size() != ((List)obj2).size()) {
        		return false;
        	}
        	for (int i = 0; i < ((List)obj1).size(); i++) {
        		if (!ComparatorUtils.equals(((List)obj1).get(i), ((List)obj2).get(i))) {
        			return false;
        		}
        	}
    		return true;
        }

        return obj1.equals(obj2);
    }
	
	/**
	 * 专门用于比较 Shape  比如 Arc2D Rectangle2D Cylinder等.
	 * @param ob
	 * @param com
	 * @return
	 */
	public static boolean equals(Shape ob, Shape com) {
		if(ob != null) {
			if(com == null) {
				return false;
			}
			// 同一类型的比较 不然就直接返回了.
			if(!ob.getClass().getName().equals(com.getClass().getName())) {
				return false;
			}
			// Arc2D
			if(ob instanceof Arc2D && com instanceof Arc2D) {
				Arc2D oArc = (Arc2D)ob;
				Arc2D cArc = (Arc2D)com;
				if(oArc.getAngleExtent() != cArc.getAngleExtent() 
						|| oArc.getAngleStart() != cArc.getAngleStart()
						|| oArc.getArcType() != cArc.getArcType()
						|| oArc.getX() != cArc.getX()
						|| oArc.getY() != cArc.getY()
						|| oArc.getWidth() != cArc.getWidth()
						|| oArc.getHeight() != cArc.getHeight()) {
					return false;
				}
			}
			// Rectangle2D
			if(ob instanceof Rectangle2D && com instanceof Rectangle2D) {
				Rectangle2D oRec = (Rectangle2D)ob;
				Rectangle2D cRec = (Rectangle2D)com;
				if(oRec.getX() != cRec.getX()
						|| oRec.getY() != cRec.getY()
						|| oRec.getWidth() != cRec.getWidth()
						|| oRec.getHeight() != cRec.getHeight()) {
					return false;
				}
			}
			// GeneralPath
			if(ob instanceof GeneralPath && com instanceof GeneralPath) {
				GeneralPath path = (GeneralPath)ob;
				GeneralPath newPath = (GeneralPath)com;
				// TODO
				if(path.getWindingRule() != newPath.getWindingRule()) {
					return false;
				}
			}
		} 
		if(com == null) {
			return true;
		}
		return true;
	}
    
    /*
     * Check whether obj1 is equals to obj2.
     * 绝对相等，不做任何转换的相等.
     * @param obj1 the first object to be compared.(The value of obj1 may be null).
     * @param obj2 the second object to be compared.(The value of obj2 may be null).
     */
    public static boolean equals_exactly(Object obj1, Object obj2) {
        // null < not null
        if (obj1 == null || obj2 == null) {//null < not null
            return obj1 == null && obj2 == null;
        }

        //date
        if ((obj1 instanceof Date) && (obj2 instanceof Date)) {
            return ((Date) obj1).getTime() == ((Date) obj2).getTime();
        }

        if ((obj1 instanceof Number) && (obj2 instanceof Number)) {
            return ((Number) obj1).doubleValue() == ((Number) obj2).doubleValue();
        }

        return obj1.equals(obj2);
    }
    
    /*
     * 比较两个Iterator是否相等
     * 
     * 比较Iterator里面的每个元素是否ComparatorUtils.equals
     */
    public static boolean equals(java.util.Iterator it1, java.util.Iterator it2) {
    	if (it1 == null) {
    		it1 = java.util.Collections.EMPTY_LIST.iterator();
    	}
    	if (it2 == null) {
    		it2 = java.util.Collections.EMPTY_LIST.iterator();
    	}
    	
    	boolean hasNext1 = it1.hasNext(), hasNext2 = it2.hasNext();
    	// :如果两个iterator都有next,比较取出来的元素是否equals
    	while (hasNext1 && hasNext2) {
    		if (!ComparatorUtils.equals(it1.next(), it2.next())) {
    			return false;
    		}
    		
    		hasNext1 = it1.hasNext();
    		hasNext2 = it2.hasNext();
    	}
    	
    	// :如果不相等,表示一个已经结束了,另一个没有结束
    	return hasNext1 == hasNext2;
    }
}
