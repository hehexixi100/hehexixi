package com.bjpowernode;

import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;

/**
 * 唐坤
 * 2021/7/18 0018
 */
public class RedisUtils {
    public static JedisPool pool = null;

    public static JedisPool getPool(String host, int port) {
        if (pool == null) {
            JedisPoolConfig config = new JedisPoolConfig();
            config.setMaxTotal(10);
            config.setMaxIdle(2);
            config.setTestOnBorrow(true);
            pool = new JedisPool(config, host, port);
        }
        return pool;
    }

    public static void closePool(){
        if(pool != null){
            pool.close();

        }
    }
}
