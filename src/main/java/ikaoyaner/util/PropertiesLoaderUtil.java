package ikaoyaner.util;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Properties;

public class PropertiesLoaderUtil {
	
	public static Properties loadProperties(File propFile){
	    Properties prop = new Properties();
	    FileInputStream fis = null;
	    try
	    {
	    	fis = new FileInputStream(propFile);
	    	prop.load(fis);
	    }
	    catch (FileNotFoundException ex)
	    {
	    	System.err.println("指定配置文件" + propFile.getPath() + "不存在，无法获取缓存配置信息！");
	    	throw new IllegalArgumentException("配置文件" + propFile.getPath() + "不存在,请检查你所指定的路径是否正确！");
	    }
	    catch (IOException ioe)
	    {
	    	System.err.println("配置文件" + propFile.getPath() + "无法打开！");
	    	throw new RuntimeException("配置文件" + propFile.getPath() + "无法打开！");
	    }
	    catch (Exception exception)
	    {
	    	System.err.println(exception.getMessage());
	    }
	    try
	    {
	    	if (fis != null) {
	    		fis.close();
	    	}
	    }
	    catch (IOException exl)
	    {
	    	System.err.println("配置文件" + propFile.getPath() + "无法关闭！");
	    }
	    return prop;
	  }
	  
	  public static Properties loadProperties(String filePath)
	  {
		  File propFile = new File(filePath);
		  return loadProperties(propFile);
	  }

}
