package ikaoyaner.util.common;

import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.Map;


public class BeanUtils {

	public static Map<String, Object> objectToMap(Object obj) throws Exception {    
        if(obj == null){    
            return null;    
        }   
  
        Map<String, Object> map = new HashMap<String, Object>();    
  
        Field[] declaredFields = obj.getClass().getDeclaredFields();    
        for (Field field : declaredFields) {    
            field.setAccessible(true);  
            if(field.get(obj) == null)
            	map.put(field.getName(), "");  
            else
            	map.put(field.getName(), field.get(obj));  
        }    
  
        return map;  
    }   
}
