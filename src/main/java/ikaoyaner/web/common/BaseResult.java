package ikaoyaner.web.common;

public class BaseResult {
	
	public String message = "请求失败，请重试！";  //返回消息
	public String code = ReturnCode.ERROR.getCode();     //返回码 默认错误
	private Object object;  //返回对象
	public String getMessage() {
		return message;
	}
	public void setMessage(String message) {
		this.message = message;
	}
	public String getCode() {
		return code;
	}
	public void setCode(String code) {
		this.code = code;
	}
	public Object getObject() {
		return object;
	}
	public void setObject(Object object) {
		this.object = object;
	}
	/**
	 * 转换失败后的数据
	 * @param result
	 * @param code
	 * @param description
	 * @return
	 */
	public static BaseResult resultChange(String code,String msg){
		BaseResult result = new BaseResult();
		result.setCode(code);
		result.setMessage(msg);
		return result;
	}
	/**
	 * 转换成功数据
	 * @param result
	 * @param object
	 * @param mess
	 * @return
	 * @throws Exception
	 */
	public static BaseResult resultSuccess(BaseResult result,Object object,String msg)throws Exception{
		if(null == result){
			result = new BaseResult();
		}
		result.setCode(ReturnCode.SUCCESS.getCode());
		result.setMessage(msg);
		if(null == object){
			object = new Object();
		}
		result.setObject(object);
		return result;
	}
	/**
	 * 转换成功数据
	 * @param result
	 * @param object
	 * @param mess
	 * @return
	 * @throws Exception
	 */
	public static BaseResult resultSuccess(Object object,String msg)throws Exception{
		BaseResult result = new BaseResult();
		result.setCode(ReturnCode.SUCCESS.getCode());
		result.setMessage(msg);
		if(null == object){
			object = new Object();
		}
		result.setObject(object);
		return result;
	}
}
