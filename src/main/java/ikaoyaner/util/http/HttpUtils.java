package ikaoyaner.util.http;

import javax.servlet.http.HttpServletRequest;

import org.apache.http.HttpEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class HttpUtils {
	
	protected final static Logger logger = LoggerFactory.getLogger(HttpUtils.class);
	
    private static CloseableHttpClient httpclient = HttpClients.createDefault();       //创建一个客户端

	/**
     * 下载页面
     */
    public static String downloadPage(String url) throws Exception {
    	//logger.info("=========>current_download_url:"+url);
    	//System.out.println("=========>getting:"+url);
        String htmlString = "";         //定义返回的String变量
        Thread.sleep(getRandom(1500,2000));
        HttpGet request = new HttpGet(url); //请求资源
      //設置httpGet的头部參數信息   
        
        request.setHeader("Connection", "keep-alive");  
        request.setHeader("Accept-Encoding", "gzip, deflate");  
        request.setHeader("Content-Type", "text/html; charset=gb2312");  
        request.setHeader("Accept", "Accept text/html,application/xhtml+xml,application/xml,application/json;q=0.9,*/*;q=0.8");  
        //request.setHeader("Accept-Charset", "UTF-8,utf-8;q=0.7,*;q=0.7");  
        request.setHeader("Accept-Language", "zh-cn,zh;q=0.5");  
        //request.setHeader("Cookie", "__utma=226521935.73826752.1323672782.1325068020.1328770420.6;");  
        request.setHeader("Host", "www.cingta.com");  
        request.setHeader("Refer", "https://www.cingta.com/srPage.html");  
        request.setHeader("X-Requested-With", "XMLHttpRequest");  
        request.setHeader("User-Agent", "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/65.0.3325.181 Safari/537.36");  
        CloseableHttpResponse response = httpclient.execute(request);           //得到回应
        try {
            HttpEntity entity = response.getEntity();                   //获得Entity对象
            htmlString = EntityUtils.toString(entity);                  //将Entity对象转化为字符串
            EntityUtils.consume(entity);                                //销毁对象
        }catch (Exception e) {
        	e.printStackTrace();
        } finally {
            response.close(); 
        }
        return htmlString;

    }
    
    /**
     * 下载页面
     */
    public static String downloadPageWithCharset(String url, String charset) throws Exception {
    	logger.info("=========>current_download_url:"+url);
    	//System.out.println("=========>getting:"+url);
        String htmlString = "";         //定义返回的String变量
        Thread.sleep(getRandom(400,600));
        HttpGet request = new HttpGet(url); //请求资源
        CloseableHttpResponse response = httpclient.execute(request);           //得到回应
        try {
            HttpEntity entity = response.getEntity();                   //获得Entity对象
            htmlString = EntityUtils.toString(entity, charset);                  //将Entity对象转化为字符串
            EntityUtils.consume(entity);                                //销毁对象
        }catch (Exception e) {
        	e.printStackTrace();
        } finally {
            response.close(); 
        }
        return htmlString;

    }
    
    public static int getRandom(int begin, int end){
    	return (int)(Math.random()*begin)+(end-begin);
    }
    
    public static boolean isAjaxRequest(HttpServletRequest request){
		String header = request.getHeader("X-Requested-With");
		boolean isAjax = "XMLHttpRequest".equals(header) ? true:false;
		return isAjax;
	}
}
