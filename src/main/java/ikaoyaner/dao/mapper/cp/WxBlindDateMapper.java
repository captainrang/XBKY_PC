package ikaoyaner.dao.mapper.cp;

import ikaoyaner.dao.entity.cp.WxBlindDate;
import tk.mybatis.mapper.common.Mapper;

import java.util.List;
import java.util.Map;

public interface WxBlindDateMapper extends Mapper<WxBlindDate> {


    List<Map<String,Object>> getCpResult();

    void updateCpInfo(long targetId);

    WxBlindDate getBlindDateObjectInfoById(WxBlindDate param);

    void updateCpInfoByCode(WxBlindDate param);
}