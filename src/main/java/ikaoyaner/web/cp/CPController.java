package ikaoyaner.web.cp;

import java.io.IOException;
import java.util.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.google.gson.GsonBuilder;
import ikaoyaner.constants.WxConstants;
import ikaoyaner.util.common.AccountValidatorUtil;
import ikaoyaner.util.common.DesRun;
import ikaoyaner.util.common.StringUtils;
import ikaoyaner.util.common.SymmetricEncoder;
import ikaoyaner.util.http.HttpUtils;
import ikaoyaner.util.mail.MailUtil;
import ikaoyaner.util.spring.SpringPropertiesUtil;
import ikaoyaner.webSocket.entity.Message;
import ikaoyaner.webSocket.ws.MyWebSocketHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.view.json.MappingJackson2JsonView;

import ikaoyaner.dao.entity.cp.WxBlindDate;
import ikaoyaner.service.cp.IWxBlindDateService;
import ikaoyaner.util.http.RequestUtil;
import org.springframework.web.socket.TextMessage;

@Controller
@RequestMapping("/cp")
public class CPController {


	@Autowired
	@Qualifier("ikaoyaner.service.cp.impl.WxBlindDateServiceImpl")
	private IWxBlindDateService blindDateService;



	@RequestMapping("/signup")
	public ModelAndView cp() {
		ModelAndView mv = new ModelAndView("/cp/signup");
		return mv;
	}
	
	@RequestMapping("/success")
	public ModelAndView success() {
		ModelAndView mv = new ModelAndView("/cp/signup_success");
		return mv;
	}
	
	@RequestMapping("/result")
	public ModelAndView result() {
		ModelAndView mv = new ModelAndView("/cp/result");
		return mv;
	}
	
	@RequestMapping("/report")
	public ModelAndView report() {
		ModelAndView mv = new ModelAndView("/cp/report");
		return mv;
	}

	@ResponseBody
	@RequestMapping(value = "/getBlindDateObjectInfo", method = RequestMethod.GET)
	public List<WxBlindDate> getBlindDateObjectInfo(@RequestBody WxBlindDate param, HttpServletRequest request){
		return blindDateService.getBlindDateObjectInfo(param);

	}

	@ResponseBody
	@RequestMapping(value = "/getBlindDateObjectInfoById", method = RequestMethod.GET)
	public ModelAndView getBlindDateObjectInfoById(  String id, HttpServletRequest request){
		ModelAndView mv = new ModelAndView("/cp/result");
        //String _id = SymmetricEncoder.AESDncode("AES",id);
		String _id = new DesRun(id).depsw; //解密
        WxBlindDate param = new WxBlindDate();
        blindDateService.updateCpInfo(Long.parseLong(_id));
        param.setId(Long.parseLong(_id));
		WxBlindDate bd =  blindDateService.getBlindDateObjectInfoById(param);

		mv.addObject("bd", bd);
		return mv;
	}

	@ResponseBody
	@RequestMapping(value = "/getBlindDateObjectInfoByCode", method = RequestMethod.GET)
	public ModelAndView getBlindDateObjectInfoByCode( String code,String openid, HttpServletRequest request){
		ModelAndView mv = new ModelAndView(new MappingJackson2JsonView());
		//String _id = SymmetricEncoder.AESDncode("AES",id);
		//String _id = //new DesRun(id).depsw; //解密
		WxBlindDate param = new WxBlindDate();
		if(StringUtils.isEmpty(openid)){
			if(StringUtils.isNotEmpty(code)){
				openid = getOpenidByCode(code);
				param.setOpenid(openid);
			}
			if(StringUtils.isEmpty(openid)){
				return null; //返回null 代表无法获取到openid
			}
		}
		blindDateService.updateCpInfoByCode(param);

		WxBlindDate bd =  blindDateService.getBlindDateObjectInfoById(param);

		mv.addObject("bd", bd);
		return mv;
	}

	@ResponseBody
	@RequestMapping(value = "/insertBlindDateObjectInfo", method = RequestMethod.POST)
	public ModelAndView insertBlindDateObjectInfo(@RequestBody WxBlindDate param, HttpServletRequest request){
		ModelAndView mv = new ModelAndView(new MappingJackson2JsonView());
		mv.addObject("success", false);
		int result = 0;
		try {
			if(!StringUtils.isEmpty(param.getLiveCity())){
				String[] lives = param.getLiveCity().split(" ");
				param.setLiveProvincial(lives[0]);
				param.setLiveUrban(lives[1]);
				param.setLiveArea(lives[2]);
			}
			result = blindDateService.insertBlindDateObjectInfo(param);
		} catch (Exception e) {
			e.printStackTrace();
			mv.addObject("message", "服务器发生错误，请稍后重试！");
		}
		if(result == 1) {
			mv.addObject("success", true);
			mv.addObject("url", RequestUtil.getContextPath() + "/cp/success");
		}
		return mv;

	}

	@ResponseBody
	@RequestMapping(value = "/insertWXBlindDateInfo", method = RequestMethod.POST)
	public ModelAndView insertWXBlindDateInfo(@RequestBody WxBlindDate param, HttpServletRequest request){
		ModelAndView mv = new ModelAndView(new MappingJackson2JsonView());
		mv.addObject("success", false);
		int result = 0;
		try {
			if(!StringUtils.isEmpty(param.getLiveCity())){
				String[] lives = param.getLiveCity().split(" ");
				param.setLiveProvincial(lives[0]);
				param.setLiveUrban(lives[1]);
				param.setLiveArea(lives[2]);
			}

			result = blindDateService.insertBlindDateObjectInfo(param);
		} catch (Exception e) {
			e.printStackTrace();
			mv.addObject("message", "服务器发生错误，请稍后重试！");
		}
		if(result == 1) {
			mv.addObject("status", true);
		}
		return mv;

	}

	@ResponseBody
	@RequestMapping(value = "/cpOptSendMailResult", method = RequestMethod.GET)
	public void cpOptSendMailResult() {
		String activeCode = StringUtils.getUUID();
		List<Map<String,Object>> resultList = blindDateService.getCpResult();
		for(Map<String,Object> result:resultList){
			String url =  SpringPropertiesUtil.parseStr("project.url") + RequestUtil.getContextPath()
					+ "/cp/getBlindDateObjectInfoById?id="+new DesRun("", result.get("target_id").toString()).enpsw ;
			String urlContent = "状态码"+activeCode+"     "+result.get("nickname").toString()+" 这是小白考研为您匹配的最佳cp,<a href=" + url + ">点我看看呗</a><br>"
					+ "(若无法打开连接，请将此链接复制到浏览器地址栏打开：" + url + " )";
			HashMap<String, Object> model = new HashMap<String, Object>();
			model.put("content", urlContent);
			model.put("signature", "小白考研技术团队");

			try {
				Thread.currentThread().sleep(100);//毫秒
				MailUtil.sslSendMail("", "",result.get("email").toString(), model);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}

		}

	}

    @ResponseBody
    @RequestMapping(value = "/cpOptSendMailResultTest", method = RequestMethod.GET)
    public void cpOptSendMailResultTest(String email) {
        String activeCode = StringUtils.getUUID();
        int i = 0;
        List<Map<String,Object>> resultList = blindDateService.getCpResult();
        for(Map<String,Object> result:resultList){
            String url =  SpringPropertiesUtil.parseStr("project.url") + RequestUtil.getContextPath()
                    + "/cp/getBlindDateObjectInfoById?id="+new DesRun("", result.get("target_id").toString()).enpsw;
            String urlContent = "状态码"+activeCode+"     "+result.get("nickname").toString()+" 这是小白考研为您匹配的最佳cp,<a href=" + url + ">点我看看呗</a><br>"
                    + "(若无法打开连接，请将此链接复制到浏览器地址栏打开：" + url + " )";
            HashMap<String, Object> model = new HashMap<String, Object>();
            model.put("content", urlContent);
            model.put("signature", "小白考研技术团队");

			try {
				Thread.currentThread().sleep(100);//毫秒
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			MailUtil.sslSendMail("", "",email, model);
			System.out.println("延迟"+(i++)+"次");
			break;

		}

    }

	/**
	 * 邮箱验证码
	 * @param email
	 */
	@ResponseBody
	@RequestMapping(value = "/validateMail", method = RequestMethod.GET)
	public Map<String,Object> validateMail(String email)  {
		Map<String,Object> resultMap = new HashMap<String,Object>();
		if(StringUtils.isEmpty(email)&& !AccountValidatorUtil.isEmail(email)){
			try {
				throw new Exception("参数异常,邮箱输入不正确   "+email);
			} catch (Exception e) {
				e.printStackTrace();
				resultMap.put("random_4",-1);
				resultMap.put("message","邮箱输入有误");
				resultMap.put("status","error");
				return resultMap;
			}
		}
		String str="ABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789";
		StringBuilder sb=new StringBuilder(4);
		for(int i=0;i<4;i++)
		{
			char ch=str.charAt(new Random().nextInt(str.length()));
			sb.append(ch);
		}
		//int random = (int)(Math.random() * 9 - 1) * 1000;
		HashMap<String, Object> model = new HashMap<String, Object>();
		String urlContent = "您的4位验证码为："+sb;
		model.put("content", urlContent);
		model.put("signature", "小白考研技术团队");
		try{
			MailUtil.sslSendMail("", "",email, model);
			resultMap.put("random_4",sb);
			resultMap.put("message","操作成功");
			resultMap.put("status","success");
		}catch(Exception e){
			resultMap.put("random_4",-1);
			resultMap.put("message","服务器发送错误，请稍后重试！");
			resultMap.put("status","error");
			return resultMap;
		}
		return resultMap;


	}

	public String getOpenidByCode(String code) {
		String getOpenidUrl = WxConstants.GET_OPENID_URL;
		getOpenidUrl = getOpenidUrl.replace("{{appid}}", WxConstants.WXXCX_APPID)
				.replace("{{secret}}", WxConstants.WXXCX_SECRET)
				.replace("{{jscode}}", code);
		try {
			String json = HttpUtils.downloadPage(getOpenidUrl);
			JSONObject jsonObj = (JSONObject) JSON.parse(json);
			return jsonObj != null ? jsonObj.getString("openid") : "";
		} catch (Exception e) {
			e.printStackTrace();
		}
		return "";
	}




}
