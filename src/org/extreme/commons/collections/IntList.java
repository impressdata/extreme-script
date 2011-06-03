package org.extreme.commons.collections;

import java.io.Serializable;
import java.util.Arrays;

import org.extreme.commons.util.Utils;



/**
 * A simple class that implements a growable array of ints, and knows
 * how to serialize itself as efficiently as a non-growable array.
 */
public class IntList implements Cloneable, Serializable {
    private int[] data; // An array to store the numbers.
    private int size = 0;  // Index of next unused element of array

    public IntList() {
    	this(8);
    }
    
    public IntList(int initialCapacity) {
    	if (initialCapacity < 0)
            throw new IllegalArgumentException("Illegal Capacity: "+
                                               initialCapacity);
    	data = new int[initialCapacity];
    }
    
    /**
     * Return an element of the array
     */
    public int get(int index) throws ArrayIndexOutOfBoundsException {
        if (index >= size) {
            throw new ArrayIndexOutOfBoundsException(index);
        } else {
            return data[index];
        }
    }

    /**
     * Return an element of the array
     */
    public void set(int index, int value) throws ArrayIndexOutOfBoundsException {
        if (index >= size) {
            throw new ArrayIndexOutOfBoundsException(index);
        } else {
            data[index] = value;
        }
    }

    /**
     * Add an int to the array, growing the array if necessary
     */
    public void add(int x) {
        ensureCapacity(size + 1);
        data[size++] = x;         // Store the int in it.
    }
    
    public void add(int index, int x) {
        if (index > size || index < 0)
            throw new IndexOutOfBoundsException("Index: " + index + ", Size: " + size);

        ensureCapacity(size + 1); // Increments modCount!!
        System.arraycopy(data, index, data, index + 1, size - index);
        data[index] = x;
        size++;
    }

    /**
     * Contain.
     */
    public boolean contain(int value) {
        for (int i = 0; i < size; i++) {
            if (data[i] == value) {
                return true;
            }
        }

        return false;
    }

    /**
     * Index of. Return -1;
     */
    public int indexOf(int value) {
        for (int i = 0; i < size; i++) {
            if (data[i] == value) {
                return i;
            }
        }

        return -1;
    }

    public int size() {
        return size;
    }

    public void remove(int index) {
        int numMoved = size - index - 1;
        if (numMoved > 0) {
            System.arraycopy(data, index + 1, data, index, numMoved);
        }
        --size;
    }

    /**
     * 将所有相等的数字去掉
     */
    public void removeEqual() {
        int[] newDataArray = this.toArray();

        this.clear();
        for (int i = 0; i < newDataArray.length; i++) {
            if (!this.contain(newDataArray[i])) {
                this.add(newDataArray[i]);
            }
        }
    }

    /**
     * 排序.
     */
    public void sort() {
        this.trimToSize();

        Arrays.sort(this.data);
    }

    public void clear() {
        this.ensureCapacity(0);
        this.size = 0;
    }

    /**
     * Trim to Size.
     */
    private void trimToSize() {
        this.data = this.toArray();
    }

    /**
     * to Array.
     */
    public int[] toArray() {
        int[] intArray = new int[size];

        System.arraycopy(data, 0, intArray, 0, size);

        return intArray;
    }
    
    public boolean addAll(int index, IntList c) {
        if (index > size || index < 0)
            throw new IndexOutOfBoundsException(
            "Index: " + index + ", Size: " + size);
        int[] a = c.toArray();
        
        int numNew = a.length;
        ensureCapacity(size + numNew);  // Increments modCount

        int numMoved = size - index;
        if (numMoved > 0)
            System.arraycopy(data, index, data, index + numNew,
                     numMoved);

            System.arraycopy(a, 0, data, index, numNew);
        size += numNew;
        return numNew != 0;
    }
    
    /**
     * Appends all of the elements in the specified IntList to the end of
     * this list, in the order that they are returned by the
     * specified IntList's Iterator.  The behavior of this operation is
     * undefined if the specified IntList is modified while the operation
     * is in progress.  (This implies that the behavior of this call is
     * undefined if the specified IntList is this list, and this
     * list is nonempty.)
     *
     * @param c the elements to be inserted into this list.
     * @return <tt>true</tt> if this list changed as a result of the call.
     * @throws    NullPointerException if the specified collection is null.
     */
    public boolean addAll(IntList c) {
        int[] a = c.toArray();
        int numNew = a.length;
    ensureCapacity(size + numNew);  // Increments modCount
        System.arraycopy(a, 0, data, size, numNew);
        size += numNew;
    return numNew != 0;
    }
    
    public int toSum() {
        int sumValue = 0;
        for (int i = 0; i < size; i++) {
            sumValue += this.get(i);
        }

        return sumValue;
    }

    public int toRangeSum(int fromIndex, int toIndex) {
        int tmpSum = 0;

        int startIndex = Math.min(fromIndex, toIndex);
        int endIndex = Math.max(fromIndex, toIndex);
        for (int i = startIndex; i < endIndex; i++) {
            tmpSum += this.get(i);
        }

        return fromIndex <= toIndex ? tmpSum : -tmpSum;
    }

    /**
     * Does this object contain the same values as the object o?
     * We override this Object method so we can test the class.
     */
    public boolean equals(Object o) {
        if (!(o instanceof IntList)) {
            return false;
        }

        IntList that = (IntList) o;
        if (this.size != that.size) {
            return false;
        }

        for (int i = 0; i < this.size; i++) {
            if (this.data[i] != that.data[i]) {
                return false;
            }
        }

        return true;
    }

    /**
     * Clone.
     */
    public Object clone() throws CloneNotSupportedException {
        IntList newIntList = (IntList) super.clone();
        newIntList.clear();

        for (int i = 0; i < this.size(); i++) {
            newIntList.add(this.get(i));
        }

        return newIntList;
    }
    
    public String toString() {
    	StringBuffer sb = new StringBuffer("[");
    	for(int i = 0, len = size(); i < len; i++) {
    		if(i > 0) {
    			sb.append(',');
    		}
    		sb.append(get(i));
    	}
    	sb.append(']');
    	
    	return sb.toString();
    }

    /**
     * An internal method to change the allocated size of the array
     */
    private void ensureCapacity(int minCapacity) {
        int oldCapacity = this.data.length;
        if (minCapacity > oldCapacity) {
            int oldData[] = data;
            int newCapacity = (oldCapacity * 3) / 2 + 1;
            if (newCapacity < minCapacity)
                newCapacity = minCapacity;
            data = new int[newCapacity];
            System.arraycopy(oldData, 0, data, 0, size);
        }
    }
    
    public static IntList asList(int[] array) {
        if(array == null) {
            throw new NullPointerException();
        }
        
        IntList list = new IntList();
        list.data = array;
        list.size = list.data.length;
        return list;
    }

    /**
     * Create string with decimal
     */
    public static String toStringDecimal(IntList intList, char decimal) {
        StringBuffer intBuf = new StringBuffer();

        if (intList != null) {
            for (int i = 0; i < intList.size(); i++) {
                if (i != 0) {
                    intBuf.append(decimal);
                }

                intBuf.append(intList.get(i));
            }
        }

        return intBuf.toString();
    }

    /**
     * Create string with decimal
     */
    public static String toStringDecimal(int[] intArray, char decimal) {
        StringBuffer intBuf = new StringBuffer();
        if (intArray != null) {
            for (int i = 0; i < intArray.length; i++) {
                if (i != 0) {
                    intBuf.append(decimal);
                }

                intBuf.append(intArray[i]);
            }
        }

        return intBuf.toString();
    }

    /**
     * Create IntList with decimal
     * 分析Int序列,(例: 2, 4, 7, 3-6, 8-9, 12)
     */
    public static IntList toIntListDecimal(String str, char decimal) {
        IntList intList = new IntList();
        if (str == null) {
            return intList;
        }

        String[] rowIndexArray = Utils.splitString(str, "" + decimal);
        for (int i = 0; i < rowIndexArray.length; i++) {
            String intTxt = rowIndexArray[i].trim();
            try {
                intList.add(Integer.parseInt(intTxt));
            } catch (Exception exp) {
                //分析 2-6
                String[] intArray = Utils.splitString(intTxt, "-");
                int minInt = -1;
                int maxInt = -1;
                for (int j = 0; j < intArray.length; j++) {
                    int pInt = 0;
                    try {
                        pInt = Integer.parseInt(intArray[j]);
                    } catch (NumberFormatException numberFormatException) {
                        //do nothing.
                    }
                    if (j == 0) {
                        minInt = pInt;
                        maxInt = pInt;
                        continue;
                    }

                    minInt = Math.min(pInt, minInt);
                    maxInt = Math.max(pInt, maxInt);
                }

                for (int j = minInt; j <= maxInt; j++) {
                    intList.add(j);
                }
            }
        }

        return intList;
    }

    /**
     * Create Int Array with decimal
     */
    public static int[] toIntArrayDecimal(String str, char decimal) {
        return IntList.toIntListDecimal(str, decimal).toArray();
    }
    
    /**
     * the IntList returned contains the values both l1 and l2 have
     */
    public static IntList intersectList(IntList l1, IntList l2) {
        if(l1 == null || l2 == null) {
            return null;
        }
        
        int[] ints = intersectArray(l1.toArray(), l2.toArray());
        
        return asList(ints);
    }
    
    //:如果i1和i2中有一个是null就返回另一个
    //否则返回i1和i2的交集
    public static int[] intersectArray(int[] i1, int[]i2) {
    	// 如果i1和i2中有一个是null就返回另一个
		if (i1 == null) {
			return i2 == null ? i2 : (int[]) i2.clone();
		}
		if (i2 == null) {
			return (int[]) i1.clone();
		}
    	
    	int[] array1 = (int[])i1.clone();
    	int[] array2 = (int[])i2.clone();
    	
    	// 先排序,后筛选
    	java.util.Arrays.sort(array1);
    	java.util.Arrays.sort(array2);
    	
    	IntList res = new IntList();
    	int m = 0;
    	for(int x = 0; x < array1.length; x++) {
    		for(int y = m; y < array2.length; y++) {
    			m = y;
    			
    			if(array1[x] == array2[y]) {
    				res.add(array1[x]);
    				break;
    			} else if(array1[x] < array2[y]) {
    				break;
    			}
    		}
    	}
    	
    	return res.toArray();
    }
    
    /**
     * the IntList returned contains all values in l1 and l2
     */
    public static IntList unionList(IntList l1, IntList l2) {
        if(l1 == null) return l2;
        if(l2 == null) return l1;
        
        return asList(unionArray(l1.toArray(), l2.toArray()));
    }
    
    /*
     * 返回 i1与i2的并集
     */
    public static int[] unionArray(int[] i1, int[] i2) {
      //如果i1和i2中有一个是null就返回另一个
        if(i1 == null) {
            return i2 == null ? i2 : (int[])i2.clone();
        }
        if(i2 == null) {
            return (int[])i1.clone();
        }
        
        int[] array1 = (int[])i1.clone();
        int[] array2 = (int[])i2.clone();
        
        java.util.Arrays.sort(array1);
        java.util.Arrays.sort(array2);
        
        IntList res = new IntList();
        int i = 0, j = 0;
        while(i < array1.length || j < array2.length) {
        	if(i == array1.length) {
        		res.add(array2[j]);
        		j++;
        	} else if (j == array2.length) {
        		res.add(array1[i]);
        		i++;
        	} else if(array1[i] < array2[j]) {
        		res.add(array1[i]);
        		i++;
        	} else if (array1[i] > array2[j]) {
        		res.add(array2[j]);
        		j++;
        	} else if (array1[i] == array2[j]) {
        		res.add(array2[j]);
        		i++;
        		j++;
        	}
        }
        
        return res.toArray();
    }
    
    public static void sort(IntList list) {
        int[] array = list.toArray();
        Arrays.sort(array);
        list.data = array;
    }
    
    public static int[] range(int to) {
    	return range(0, to);
    }
    
    public static int[] range(int from, int to) {
    	return range(from, to, 1);
    }
    
    /*
     * [from, to)
     */
    public static int[] range(int from, int to, int step) {
    	IntList list = new IntList();
    	if(step > 0) {
			while(from < to) {
				list.add(from);
				from = from + step;
			}
		} else {
			while(from > to) {
				list.add(from);
				from = from + step;
			}
		}
    	
    	return list.toArray();
    }   
}
