package ikaoyaner.util.redis;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.ShardedJedis;
import redis.clients.jedis.ShardedJedisPool;

/**
 * redis池管理
 * @author BUKAA
 *
 */
public final class RedisPoolUtil {
    
    private static JedisPool jedisPool;//非切片连接池
    
    private static ShardedJedisPool shardedJedisPool;//切片连接池
    
    /**
     * 获取Jedis(非切片)实例
     * @return
     */
    public synchronized static Jedis getJedis() {
        try {
            if (jedisPool != null) {
                return jedisPool.getResource();
            } else {
                return null;
            }
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
    
    /**
     * 获取Jedis(切片)实例
     * @return
     */
    public synchronized static ShardedJedis getShardedJedis() {
    	try {
    		if (shardedJedisPool != null) {
    			return shardedJedisPool.getResource();
    		} else {
    			return null;
    		}
    	} catch (Exception e) {
    		e.printStackTrace();
    		return null;
    	}
    }
    
    /**
     * 释放jedis资源
     * @param jedis
     */
    @SuppressWarnings("deprecation")
	public static void returnResource(final Jedis jedis) {
        if (jedis != null) {
            jedisPool.returnResource(jedis);
        }
    }
}
