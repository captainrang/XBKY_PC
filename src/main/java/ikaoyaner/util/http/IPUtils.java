package ikaoyaner.util.http;

import javax.servlet.http.HttpServletRequest;

import org.apache.http.HttpEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;

import ikaoyaner.util.common.StringUtils;

public class IPUtils {
	
	private static CloseableHttpClient httpclient = HttpClients.createDefault();       //创建一个客户端
	
	/** 
	   * 获取用户真实IP地址，不使用request.getRemoteAddr();的原因是有可能用户使用了代理软件方式避免真实IP地址, 
	   * @return ip
	  */
	public static String getIp(){
		HttpServletRequest request = ((ServletRequestAttributes)RequestContextHolder.getRequestAttributes()).getRequest();
	    String ip = request.getHeader("x-forwarded-for"); 
	    if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) { 
	      ip = request.getHeader("Proxy-Client-IP"); 
	    } 
	    if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) { 
	      ip = request.getHeader("WL-Proxy-Client-IP"); 
	    } 
	    if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) { 
	      ip = request.getHeader("HTTP_CLIENT_IP"); 
	    } 
	    if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) { 
	      ip = request.getHeader("HTTP_X_FORWARDED_FOR"); 
	    } 
	    if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) { 
	      ip = request.getRemoteAddr(); 
	    } 
	    return ip; 
	}
	
	public static String getIpLocation(String ip){
		if(!checkIpAddr(ip))return "";
		String queryUrl = "https://sp0.baidu.com/8aQDcjqpAAV3otqbppnN2DJv/api.php?query=ip_address&co=&resource_id=6006&t=1488627546804&ie=utf8&oe=gbk&cb=op_aladdin_callback&format=json&tn=baidu&cb=jQuery1102030828275199121924_1488610573269&_=1488610573309";
		String result = "";         //定义返回的String变量
		try {
            HttpGet request = new HttpGet(queryUrl.replace("ip_address", ip)); //请求资源
            CloseableHttpResponse response = httpclient.execute(request);           //得到回应
            try {
                System.out.println(response.getStatusLine());               //打印状态码
                HttpEntity entity = response.getEntity();                   //获得Entity对象
                result = EntityUtils.toString(entity);                  //将Entity对象转化为字符串
                EntityUtils.consume(entity);                                //销毁对象
            }catch(Exception e){
            	e.printStackTrace();
            } finally {
                response.close();                                       
            }
		} catch (Exception e) {
			e.printStackTrace();
		}
		if(StringUtils.isNotEmpty(result)){
			result = result.substring(result.indexOf("(")+1, result.lastIndexOf("")-2);
		}
		if(StringUtils.isNotEmpty(result)){
			JSONObject json = (JSONObject)(JSON.parse(result));
			if(json != null){
				JSONArray jsonArr = (JSONArray)json.get("data");
				if(jsonArr != null){
					JSONObject jsonObj = jsonArr.getJSONObject(0);
					if(jsonObj != null){
						return jsonObj.getString("location");
					}
				}
			}
		}
		return "";
	}
	
	private static boolean checkIpAddr(String ip) {
		if (ip != null && !ip.isEmpty()) {
            // 定义正则表达式
            String regex = "^(1\\d{2}|2[0-4]\\d|25[0-5]|[1-9]\\d|[1-9])\\."
                    + "(1\\d{2}|2[0-4]\\d|25[0-5]|[1-9]\\d|\\d)\\."
                    + "(1\\d{2}|2[0-4]\\d|25[0-5]|[1-9]\\d|\\d)\\."
                    + "(1\\d{2}|2[0-4]\\d|25[0-5]|[1-9]\\d|\\d)$";
            // 判断ip地址是否与正则表达式匹配
            if (ip.matches(regex)) {
                // 返回判断信息
                return true;
            }
        }
        // 返回判断信息
        return false;
	}
	
	// example : 192.168.1.2  
    public static long ipToLong(String ipAddress) {
    	if(!checkIpAddr(ipAddress)) return 0;
        // ipAddressInArray[0] = 192  
        String[] ipAddressInArray = ipAddress.split("\\.");  
        long result = 0;  
        for (int i = 0; i < ipAddressInArray.length; i++) {  
   
            int power = 3 - i;  
            int ip = Integer.parseInt(ipAddressInArray[i]);  
   
            // 1. 192 * 256^3  
            // 2. 168 * 256^2  
            // 3. 1 * 256^1  
            // 4. 2 * 256^0  
            result += ip * Math.pow(256, power);  
        }  
        return result;  
   
    }  
   
    public static long ipToLong2(String ipAddress) {
    	if(!checkIpAddr(ipAddress)) return 0;
        long result = 0;  
        String[] ipAddressInArray = ipAddress.split("\\.");  
        for (int i = 3; i >= 0; i--) {  
            long ip = Long.parseLong(ipAddressInArray[3 - i]);  
            // left shifting 24,16,8,0 and bitwise OR  
            // 1. 192 << 24  
            // 1. 168 << 16  
            // 1. 1 << 8  
            // 1. 2 << 0  
            result |= ip << (i * 8);  
        }  
        return result;  
    }  
   
    public static String longToIp(long i) {  
        return ((i >> 24) & 0xFF) +   
                   "." + ((i >> 16) & 0xFF) +   
                   "." + ((i >> 8) & 0xFF) +   
                   "." + (i & 0xFF);  
    }  
   
    public static String longToIp2(long ip) {  
        StringBuilder sb = new StringBuilder(15);  
        for (int i = 0; i < 4; i++) {  
            // 1. 2  
            // 2. 1  
            // 3. 168  
            // 4. 192  
            sb.insert(0, Long.toString(ip & 0xff));  
            if (i < 3) {  
                sb.insert(0, '.');  
            }  
            // 1. 192.168.1.2  
            // 2. 192.168.1  
            // 3. 192.168  
            // 4. 192  
            ip = ip >> 8;  
        }  
        return sb.toString();  
    } 

	public static void main(String[] args) {
		System.out.println(getIpLocation(""));
	}

}
