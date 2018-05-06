package ikaoyaner.util.common;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;

public class DateUtils {
	
	private static Map<Integer, String> weekData;
	
	static{
		weekData = new HashMap<Integer, String>();
		weekData.put(0, "周日");
		weekData.put(1, "周一");
		weekData.put(2, "周二");
		weekData.put(3, "周三");
		weekData.put(4, "周四");
		weekData.put(5, "周五");
		weekData.put(6, "周六");
	}
	
	public static String getFormatTime(Date date, String format) {
        SimpleDateFormat sdf = new SimpleDateFormat(format);
        return sdf.format(date);
    }

    public static Date addDay(Date date, int day) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        cal.add(Calendar.DATE, 2);
        return cal.getTime();
    }

    public static Date minusDay(Date date, int day) {
        return addDay(date, -day);
    }
	
	/** 
	 * 测试 
	 */  
	@SuppressWarnings("deprecation")
	public static void main(String[] args) {  
	    // 定义输出日期格式  
	    SimpleDateFormat sdf2 = new SimpleDateFormat("dd");  
	    SimpleDateFormat sdf3 = new SimpleDateFormat("MM-dd");  
	    
	    Date currentDate = new Date();  
	    /*Calendar cal = Calendar.getInstance();
	    cal.setTime(currentDate);
	    cal.add(Calendar.DATE, -5);
	    currentDate = cal.getTime();*/
	    List<Date> days = dateToWeek(currentDate, 1);  
	    System.out.println("今天的日期: " + sdf2.format(currentDate));  
	    for (Date date : days) {
	    	if(StringUtils.compare(sdf2.format(date), sdf2.format(currentDate)) == 0){
	    		System.out.println(sdf3.format(date)+" 今天");
	    	}else{
	    		System.out.println(sdf2.format(date)+" "+ DateUtils.getWeekInfo(date.getDay()));  
	    	}
	    }  
	} 
	
	public static String getWeekInfo(Integer day){
		return weekData.get(day);
	}
	  
	/** 
	 * 根据日期获得所在周的日期  
	 * @param mdate 
	 * @param mode 模式：1:周日为第一天、2:周一为第一天
	 * @return 
	 */  
	@SuppressWarnings("deprecation")  
	public static List<Date> dateToWeek(Date mdate, int mode) { 
	    int b = mdate.getDay();
	    if(mode == 1) b++;
	    Date fdate;  
	    List<Date> list = new ArrayList<Date>();  
	    Long fTime = mdate.getTime() - b * 24 * 3600000;  
	    for (int a = 1; a <= 7; a++) {  
	        fdate = new Date();  
	        fdate.setTime(fTime + (a * 24 * 3600000));  
	        list.add(a-1, fdate);  
	    }  
	    return list;  
	}
	
	public static int getCurrentTime(){
		return (int)(System.currentTimeMillis()/1000);
	}
	
	/**
	 * 获取时间说明，eg：1秒前、两天前
	 * @param time
	 * @return
	 */
	public static String getTimeDesc(int time) {
		long ms = (new Date().getTime())/1000 - time;
		if(ms < 60) {
			// 小于1分钟
			return ms+"秒前";
		}else if(ms >= 60 && ms < 60*60) {
			// 大于1分钟 小于1小时
			return (ms/60)+"分钟前";
			
		}else if(ms >= 60*60 && ms < 24*60*60) {
			//大于1小时小于1天
			return (ms/60/60)+"小时前";
		}else if(ms >= 24*60*60 && ms < 30*24*60*60){
			//大于1天小于30天
			return (ms/60/60/24)+"天前";
		}else {
			//大于30天
			return new SimpleDateFormat("yyyy-MM-dd HH:mm").format(time*1000L);
		}
	}
	
}
