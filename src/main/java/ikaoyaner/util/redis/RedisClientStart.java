package ikaoyaner.util.redis;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Properties;

import org.apache.commons.net.telnet.TelnetClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;

import ikaoyaner.util.PropertiesLoaderUtil;
import ikaoyaner.util.RuntimeProcessUtil;
import ikaoyaner.util.common.StringUtils;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.ShardedJedis;
import redis.clients.jedis.ShardedJedisPool;


public class RedisClientStart {
	
	protected final Logger logger = LoggerFactory.getLogger(this.getClass());
	
	public static String REDIS_EXE_NAME = "redis-server.exe";
	
	public static String REDIS_CONF_NAME = "redis.conf";
	
	public static String REDIS_BAT_NAME = "start.bat";
	
	private JedisPool jedisPool;
	
	private ShardedJedisPool shardedJedisPool;
	
	public RedisClientStart()
	{
		System.out.println("初始化Redis...");
  
		Resource rs = new ClassPathResource("redis.properties");
  
		Properties prop = null;
		try {
			prop = PropertiesLoaderUtil.loadProperties(rs.getFile());
		} catch (IOException e1)
		{
			logger.error("redis.properties FileNotFound");
		}
		try {
			checkRedisServers(prop);
		} catch (Exception e) {
			logger.error("redis初始化失败...");
		}
	}
	
	public JedisPool getJedisPool() {
		return jedisPool;
	}

	public void setJedisPool(JedisPool jedisPool) {
		this.jedisPool = jedisPool;
	}

	public ShardedJedisPool getShardedJedisPool() {
		return shardedJedisPool;
	}

	public void setShardedJedisPool(ShardedJedisPool shardedJedisPool) {
		this.shardedJedisPool = shardedJedisPool;
	}
  
	public Jedis getJedis() {
		return jedisPool.getResource();
	}
  
	public ShardedJedis getShardedJedis() {
		return shardedJedisPool.getResource();
	}

	/**
	 * 测试redis连接，如果配置的redis连接失败，则在本地启动redis进程
	 * @param prop 参数配置
	 */
	private void checkRedisServers(Properties prop){
		boolean isSuccess = false;
		String os = System.getProperty("os.name");
		TelnetClient telnet = new TelnetClient();
		telnet.setConnectTimeout(1000);
		if (os.toLowerCase().contains("win")) {
			  try {
				  telnet.connect(prop.getProperty("redis.host"), Integer.parseInt(prop.getProperty("redis.port")));
				  System.out.println("【缓存管理】redis连接成功==》"+prop.getProperty("redis.host")+":"+prop.getProperty("redis.port"));
		    	  isSuccess = true;
			  }catch (Exception e) {
		    	  System.err.println("【缓存管理】redis连接测试失败" + e.getMessage());
		      }
		      try {
		    	  telnet.disconnect();
		      } catch (Exception e)
		      {
		    	  System.err.println("【缓存管理】redis断开时发生异常" + e.getMessage());
		      }
		      if (!isSuccess) {
		    	  System.out.println("【缓存管理】配置的Redis服务器地址连接失败");
		    	  if (os.toLowerCase().contains("win"))
		    	  {
		    		  System.out.println("【缓存管理】检测本地进程");
		    		  boolean exsit = RuntimeProcessUtil.exsitProcess("redis-server.exe");
		    		  if (!exsit) {
		    			  System.out.println("【缓存管理】本地Redis进程未启动，即将启动本地进程");
		    			  String bat = getRedisBat();
		    			  try {
		    				  Runtime rt = Runtime.getRuntime();
		    				  System.out.println("Running " + bat);  
		    				  Process proc = rt.exec(bat);
		    				  StreamGobbler errorGobbler = new StreamGobbler(proc.getErrorStream(), "ERROR");  
		    				  StreamGobbler outputGobbler = new StreamGobbler(proc.getInputStream(), "OUTPUT");  
		    				  errorGobbler.start();  
		    				  outputGobbler.start();  
		    				  System.out.println("【缓存管理】本地Redis启动成功。。。");
		    			  } catch (Throwable e) {
		    				  System.out.println("【缓存管理】本地Redis启动失败，失败原因："+e.getMessage());
		    			  }
		    		  }
		    	  }
		      }
		}
	}
	
	public static String getWindowsRedisPath(String file_name){
		String arch = System.getProperty("os.arch");
		String path = getClassPath();
	    if ("\\".equals(File.separator)) {
	      path = path.substring(1).replaceAll("/", "\\\\");
	    }
	    if (path.lastIndexOf("\\") < path.length() - 1) {
	      path = path + "\\";
	    }
	    path = path + "redis\\windows";
	    if (arch.toLowerCase().contains("64")) {
	      path = path + "\\x64";
	    } else {
	      path = path + "\\x86";
	    }
	    path = path + "\\" + file_name;
	    return path;
	 }
  
	public static String getLinuxRedisPath()
	{
		return "/usr/local/bin/redis";
	}
  
	/**
	 * 获取redis启动批处理文件在windows中的路径
	 * @return
	 */
	public static String getRedisBat() {
		String os = System.getProperty("os.name");
		if (os.toLowerCase().contains("win"))
		{
			String bat = StringUtils.urlDecode(getWindowsRedisPath(REDIS_BAT_NAME), "UTF-8");
			File batFile = new File(bat);
			if(!batFile.exists()){
				try {
					batFile.createNewFile();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
			try {
				FileWriter fw = new FileWriter(batFile);
				fw.write(StringUtils.urlDecode(getWindowsRedisPath(REDIS_EXE_NAME), "UTF-8")+" "+StringUtils.urlDecode(getWindowsRedisPath(REDIS_CONF_NAME), "UTF-8"));
				try {
					fw.close();
				} catch (Exception e) {
					e.printStackTrace();
				}
				return StringUtils.urlDecode(getWindowsRedisPath(REDIS_BAT_NAME), "UTF-8");
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		return null;
	}
	
	//获取tomcat中class的真实路径
	public static String getClassPath()
	{
		return RedisUtil.class.getClassLoader().getResource("").getPath();
	}
}

