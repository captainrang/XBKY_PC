package ikaoyaner.web.common;

public enum ReturnCode {
	
	SUCCESS("0","成功"), //成功	
	ERROR("-100","请求失败"), //成功	
	DISABLE("-111","接口已停用"), //停用	
	PARAM_EMPTY("-1","参数为空"), //参数为空
	PARAM_ERROR("-2","参数格式错误"), //数据错误
	PARAM_LONG("-3","参数长度超限"), //参数长度
	DATA_NOT("-4","请求数据不存在"), //请求数据不存在
	DATA_ERROR("-5","请求数据错误"), //请求数据错误
	SAVE_ERROR("-6","保存数据错误"), //操作数据
	PARAM_VALUE_ERROR("-7","参数数值错误"), //参数数值
	DATA_OPERA_NOT("-8","操作数据不存在"), //操作数据
	OUT_RULE("-9","超出现有规则错误"), //超出现有规则
	SERVICE_ERROR("-500","服务器内部错误");//暂无数据
	
	private String code;
	private String description;

	ReturnCode(String description) {
        this.description = description;
    }
	
	ReturnCode(String code,String description) {
		this.code = code;
        this.description = description;
    }
	
	@Override
	public String toString() {
		return this.description;
	}
	
	/**
	 * @return the code
	 */
	public String getCode() {
		return this.code;
	}
	
	public String getDescription(){
		return this.description;
	}
	
}
