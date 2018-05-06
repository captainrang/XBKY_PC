package ikaoyaner.util;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.StringWriter;
import java.util.Map;

import freemarker.cache.StringTemplateLoader;
import freemarker.template.Configuration;
import freemarker.template.Template;
import ikaoyaner.util.common.StringUtils;

public class FreeMarkerUtil {

	  public static Configuration config = null;
	  
	  public static StringTemplateLoader stringLoader = null;

	  public Configuration getConfig() {
	    return config;
	  }

	  public void setConfig(Configuration config) {
	    FreeMarkerUtil.config = config;
	  }

	  public StringTemplateLoader getStringLoader() {
	    return stringLoader;
	  }

	  public void setStringLoader(StringTemplateLoader stringLoader) {
		FreeMarkerUtil.stringLoader = stringLoader;
	  }

	  public static void clearCache()
	  {
	    if (config != null) {
	      config.clearTemplateCache();
	      stringLoader = null;
	      config = null;
	    }
	  }

	  @SuppressWarnings("deprecation")
	  public static void putTemplate(String name, String content)
	  {
	    if ((StringUtils.isNotEmpty(name)) && (StringUtils.isNotEmpty(content))) {
	      if (stringLoader == null) {
	        config = new Configuration();
	        stringLoader = new StringTemplateLoader();
	      }

	      stringLoader.putTemplate(name, content);

	      if (config.getTemplateLoader() == null)
	        config.setTemplateLoader(stringLoader);
	    }
	  }

	  public static Template getTemplate(String name)
	  {
	    try
	    {
	      if (config.getTemplateLoader().findTemplateSource(name) != null)
	        return config.getTemplate(name, "utf-8");
	    }
	    catch (IOException e) {
	      e.printStackTrace();
	    }

	    return null;
	  }

	  public static Template getTemplate(String name, String content, String macroContent)
	  {
	    try
	    {
	      Template template = getTemplate(name);
	      if (template == null) {
	        putTemplate(name, content);
	        return config.getTemplate(name, "utf-8");
	      }

	      return template;
	    }
	    catch (IOException e)
	    {
	      e.printStackTrace();
	    }

	    return null;
	  }

	  public static String parseTemplateStr(String name, Map<String, Object> data)
	  {
	    Template template = getTemplate(name);
	    if (template != null) {
	      return parseTemplateStr(template, data);
	    }
	    return null;
	  }

	  public static String parseTemplateStr(Template template, Map<String, Object> data)
	  {
	    try
	    {
	      if (template == null) return null;

	      StringWriter writer = new StringWriter();
	      template.process(data, writer);
	      return writer.toString();
	    }
	    catch (Exception e) {
	      e.printStackTrace();
	    }

	    return null;
	  }

	@SuppressWarnings("resource")
	public static void writeTemplateFtl(String templateName, String filePath, Map<String, Object> data)
	    throws Exception
	  {
	    FileWriter out = null;
	    try
	    {
	      out = new FileWriter(new File(filePath));
	      Template template = getTemplate(templateName);
	      if (template != null) {
	        template.process(data, out);
	      }
	      else
	        throw new RuntimeException("找不到模板文件");
	    }
	    catch (Exception e)
	    {
	      e.printStackTrace();
	      throw e;
	    }
	  }
	  
	  @SuppressWarnings("deprecation")
	  public static String parseTemplateStrSingle(String content,Map<String,Object> data){
		  String resultStr = null;
		  Configuration cfg = new Configuration();  
	      StringTemplateLoader stringLoader = new StringTemplateLoader();  
	      String templateContent= content;  
	      stringLoader.putTemplate("myTemplate",templateContent);  
	      cfg.setTemplateLoader(stringLoader);
          Template template;
          try {
				template = cfg.getTemplate("myTemplate","utf-8");
				StringWriter writer = new StringWriter();    
				template.process(data, writer); 
				resultStr = writer.toString();
			} catch (Exception e) {
			}  
          return resultStr;
	  }
}
