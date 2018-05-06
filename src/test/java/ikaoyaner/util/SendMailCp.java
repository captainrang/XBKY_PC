package ikaoyaner.util;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import ikaoyaner.dao.entity.cp.WxBlindDate;
import ikaoyaner.service.cp.IWxBlindDateService;
import ikaoyaner.util.common.JsonUtil;
import ikaoyaner.util.common.StringUtils;
import ikaoyaner.util.common.SymmetricEncoder;
import ikaoyaner.util.http.HttpUtils;
import ikaoyaner.util.http.RequestUtil;
import ikaoyaner.util.mail.MailUtil;
import ikaoyaner.util.spring.SpringPropertiesUtil;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by cyx  on 2018/4/28
 */
public class SendMailCp extends BaseTest {

    @Autowired
    private IWxBlindDateService wxBlindDate;

    @Test
    public void testFindSubjectsWithChild() {
        WxBlindDate wxBlindDateBean = new WxBlindDate();
        wxBlindDateBean.setId(27L);
        List<WxBlindDate> result = wxBlindDate.getBlindDateObjectInfo(wxBlindDateBean);
        System.out.println(JsonUtil.toJson(result));
    }

    @Test
    public void testInsertCpInfo() {
        String url = "http://localhost:8080/cp/getBlindDateObjectInfoById?id={id}";
        try {
            String result2 = HttpUtils.downloadPage(url.replace("{id}", SymmetricEncoder.AESEncode("AES","27")));
            System.out.println(JsonUtil.toJson(result2));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    @Test
    public void testgetCpInfo() {
        String url = "http://localhost:8080/cp/insertWXBlindDateInfo";
        try {
            String result2 = HttpUtils.downloadPage(url.replace("{id}", SymmetricEncoder.AESEncode("AES","27")));
            System.out.println(JsonUtil.toJson(result2));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Test
    public void cpOptSendMailResult() {
        List<Map<String,Object>> result = wxBlindDate.getCpResult();
        System.out.println(JsonUtil.toJson(result));
    }

    @Test
    public void testSendMail() {
        String url = "http://localhost:8080/cp/cpOptSendMailResult";
        try {
            String result2 = HttpUtils.downloadPage(url);
            System.out.println(JsonUtil.toJson(result2));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
