package ikaoyaner.service.cp;

import java.util.List;
import java.util.Map;

import ikaoyaner.dao.entity.cp.WxBlindDate;


public interface IWxBlindDateService {
	
	/**
	 * 获取个人相亲信息
	 * @param param
	 * @return
	 */
	public List<WxBlindDate> getBlindDateObjectInfo(WxBlindDate param);
	
	/**
	 * 插入相亲信息
	 * @param param
	 * @return
	 */
	public Integer insertBlindDateObjectInfo(WxBlindDate param);

	public List<Map<String,Object>> getCpResult();

	void updateCpInfo(long l);

	WxBlindDate getBlindDateObjectInfoById(WxBlindDate param);

	void updateCpInfoByCode(WxBlindDate param);
}
