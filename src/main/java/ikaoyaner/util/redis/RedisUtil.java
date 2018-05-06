package ikaoyaner.util.redis;

import java.util.List;

import redis.clients.jedis.Jedis;

public class RedisUtil {
	
	public static Jedis jedis = RedisPoolUtil.getJedis();
	
	public static Jedis getJedis(){
		return jedis;
	}
	
	public static void closeJedis(Jedis jedis){
		if(jedis != null && jedis.isConnected()){
			jedis.disconnect();
		}
	}

	/**
	 * 插入List
	 * @param key
	 * @param list
	 * @return
	 */
	public static Long set(String key, List<String> list){
		long result = 0L;
		if(list != null && jedis != null){
			for (String s : list) {
				result = jedis.lpush(key, s);
			}
		}
		return	result;
	}
	
	/**
	 * 插入数组
	 * @param key
	 * @param str
	 * @return
	 */
	public static Long set(String key, String[] str){
		if(str == null || jedis == null){
			return 0L;
		}
		return	jedis.lpush(key, str);
	}
	
	/**
	 * 删除key
	 * @param key
	 */
	public static Long remove(String key){
		return jedis.del(key);
	}
  
}

