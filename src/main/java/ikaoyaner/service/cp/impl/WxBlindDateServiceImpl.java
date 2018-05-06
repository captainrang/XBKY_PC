package ikaoyaner.service.cp.impl;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ikaoyaner.dao.entity.cp.WxBlindDate;
import ikaoyaner.dao.mapper.cp.WxBlindDateMapper;
import ikaoyaner.service.cp.IWxBlindDateService;



@Service("ikaoyaner.service.cp.impl.WxBlindDateServiceImpl")
public class WxBlindDateServiceImpl implements IWxBlindDateService {
	
	@Autowired
	private WxBlindDateMapper dao;

	@Override
	public List<WxBlindDate> getBlindDateObjectInfo(
			WxBlindDate param) {
		return dao.select(param);
	}

	@Override
	public Integer insertBlindDateObjectInfo(WxBlindDate param) {
		return dao.insertSelective(param);
	}

	@Override
	public List<Map<String, Object>> getCpResult() {
		return dao.getCpResult();
	}

	@Override
	public void updateCpInfo(long l) {
		dao.updateCpInfo(l);
	}

	@Override
	public WxBlindDate getBlindDateObjectInfoById(WxBlindDate param) {
		return dao.getBlindDateObjectInfoById(param);
	}

	@Override
	public void updateCpInfoByCode(WxBlindDate param) {
		dao.updateCpInfoByCode(param);
	}


}
