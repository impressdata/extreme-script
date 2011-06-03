package org.extreme.commons.json;

import java.util.Iterator;

import org.extreme.commons.util.StringUtils;
import org.extreme.script.FArray;
import org.extreme.script.Primitive;


public class JSONUtils {
	
	public static Object quoteFrObject(Object obj) {
		if (obj instanceof String) {
			return JSONObject.quote((String)obj);
		} else if (obj instanceof FArray) {
			FArray fArray = (FArray)obj;
			FArray newFArray = new FArray();
			for(int i = 0; i < fArray.length(); i ++) {
				newFArray.add(quoteFrObject(fArray.elementAt(i)));
			}
			return "[" + newFArray + "]";
		}
		
		if ((obj instanceof Primitive || StringUtils.EMPTY.equals(obj.toString()))) {
			obj = null;
		}
		
		return obj;
	}

	/*
	 * 把一段JSONObject的String转成java.util.Map
	 */
	public static java.util.Map jsonString2Map(String json_str) throws JSONException {
		java.util.Map parameterMap = new java.util.HashMap();
		
		if(StringUtils.isNotBlank(json_str)) {
			JSONObject jo = new JSONObject(json_str);
			Iterator keyIt = jo.keys();
			while(keyIt.hasNext()) {
				Object key = keyIt.next();
				Object value = jo.get(key.toString());
				if (value instanceof JSONArray){
					JSONArray ja = (JSONArray)value;
					FArray array = new FArray();
					for (int i = 0; i < ja.length(); i ++){
						array.add(ja.get(i));
					}
					value = array;
				} 
				parameterMap.put(key, value);
			}
		}
		
		return parameterMap;
	}
	
	/*
	 * 得到一个JSONObject,判断是否是Date类型的
	 */
	public static Object objectDecode(Object o) throws JSONException {
		Object ret = o;
		if (o instanceof JSONObject) {
			JSONObject jo = (JSONObject)o;
			
			// 如果key为__time__且x.nextValue为Long
			if (jo.has("__time__")) {
				Object kv = jo.get("__time__");
				if (kv instanceof Long) {
					ret = new java.util.Date(((Long)kv).longValue());
				}
			}
		} else if (JSONObject.NULL.equals(o)) {
			ret = Primitive.NULL;
		}
        
        return ret;
	}
}
