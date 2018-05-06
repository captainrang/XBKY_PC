package ikaoyaner.constants;

import ikaoyaner.util.spring.SpringPropertiesUtil;

public class WxConstants {
	
	public static String GET_ACCESS_TOKEN_URL = SpringPropertiesUtil.parseStr("wx.getAccessTokenUrl");
	
	public static String GET_USERINFO_URL = SpringPropertiesUtil.parseStr("wx.getUserInfoUrl");
	
	/**公众化appid**/
	public static String APPID = SpringPropertiesUtil.parseStr("wx.appid");
	
	/**公众号secret**/
	public static String SECRET = SpringPropertiesUtil.parseStr("wx.secret");

	/*微信小程序地址*/
	public static String GET_OPENID_URL = SpringPropertiesUtil.parseStr("wx.getOpenidUrl");

	/**微信小程序appid**/
	public static String WXXCX_APPID = SpringPropertiesUtil.parseStr("wx.appid_wxxcx_test");

	/**微信小程序secret**/
	public static String WXXCX_SECRET = SpringPropertiesUtil.parseStr("wx.secret_wxxcx_test");

	/**订单状态-下单成功**/
	public static String SHOPPING_ORDER_STATU_SUCCESS = "0";
	
	/**订单状态-下单成功**/
	public static String SHOPPING_ORDER_STATU_CONFIRM = "1";
	
	/**订单状态-运输途中**/
	public static String SHOPPING_ORDER_STATU_WAY = "2";
	
	/**订单状态-收货成功**/
	public static String SHOPPING_ORDER_STATU_RECIEVED = "00";
}
