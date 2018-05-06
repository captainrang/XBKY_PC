package ikaoyaner.util.http;

import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.regex.PatternSyntaxException;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;

import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import ikaoyaner.util.common.StringUtils;

public class RequestUtil {

	public static final String X_REQUESTED_WIDTH = "X-Requested-With";
	public static final String XML_HTTP_REQUEST = "XMLHttpRequest";

	private RequestUtil() {
	}
	
	public static String getParam(HttpServletRequest request,String paramName){
		if(StringUtils.isEmpty(paramName)) {
			return null; 
		}
		String param = request.getParameter(paramName);
		return param;
	}
	
	/**
	 * 根据参数名获取参数值
	 * @param paramName 参数名
	 * @return
	 */
	public static String getPatam(String paramName) {
		if(StringUtils.isEmpty(paramName)) {
			return null; 
		}
		HttpServletRequest request = ((ServletRequestAttributes)RequestContextHolder.getRequestAttributes()).getRequest();
		return getParam(request, paramName);
	}
	
	/**
	 * 根据参数名获取参数值
	 * @param paramName 参数名
	 * @param filter 是否过滤特殊字符
	 * * <pre>[`~!@#$%^&*()+=|{}':;'\\[\\].<>/?~！@#￥%……&*（）——+|{}【】‘；：”“’。，、？]</pre>
	 * @return
	 */
	public static String getPatam(String paramName, boolean filter) {
		String param = getPatam(paramName);
		if(!filter || param ==null) {
			return param;
		}
		return StringFilter(param);
	}
	
	/**
	 * 根据参数名获取参数值
	 * @param paramName 参数名
	 * @param filter 是否过滤特殊字符
	 * * <pre>[`~!@#$%^&*()+=|{}':;'\\[\\].<>/?~！@#￥%……&*（）——+|{}【】‘；：”“’。，、？]</pre>
	 * @return
	 */
	public static String[] getParameterValues(String paramName) {
		if(StringUtils.isEmpty(paramName)) {
			return null; 
		}
		HttpServletRequest request = ((ServletRequestAttributes)RequestContextHolder.getRequestAttributes()).getRequest();
		return request.getParameterValues(paramName);
	}

	/**
	 * 判断是否ajax请求. 可以看到Ajax 请求多了个 x-requested-with ，可以利用它，
	 * request.getHeader("x-requested-with"); 为 null，则为传统同步请求，为
	 * XMLHttpRequest，则为Ajax 异步请求。
	 * 
	 * @paramrequest HttpServletRequest @return是否ajax请求.
	 */
	public static boolean isAjaxRequest(HttpServletRequest request) {
		String xr = request.getHeader(X_REQUESTED_WIDTH);
		return (xr != null && XML_HTTP_REQUEST.equalsIgnoreCase(xr));
	}

	public static String getCookieValue(HttpServletRequest request, String name) {
		Cookie[] cookies = request.getCookies();
		if (cookies != null) {
			for (Cookie cookie : cookies) {
				String cookieName = cookie.getName();
				if (cookieName.equals(name)) {
					return cookie.getValue();
				}
			}
		}
		return null;
	}
	
	/**
	 * 移除字符中的特殊字符<br>
	 * <pre>[`~!@#$%^&*()+=|{}':;'\\[\\].<>/?~！@#￥%……&*（）——+|{}【】‘；：”“’。，、？]</pre>
	 * @param str
	 * @return
	 */
	public static String StringFilter(String str) throws PatternSyntaxException {
		// 只允许字母和数字 "[^a-zA-Z0-9]"; 
		// 清除掉所有特殊字符 
		String regEx="[`~!@#$%^&*()+=|{}':;'\\[\\].<>/?~！@#￥%……&*（）——+|{}【】‘；：”“’。，、？]"; 
		Pattern p = Pattern.compile(regEx); 
		Matcher m = p.matcher(str);
		return m.replaceAll("").trim();
	}
	
	public static String getContextPath() {
		HttpServletRequest request = ((ServletRequestAttributes)RequestContextHolder.getRequestAttributes()).getRequest();
		return request.getContextPath();
	}

}
