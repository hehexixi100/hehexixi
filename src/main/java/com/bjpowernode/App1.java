package com.bjpowernode;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 唐坤
 * 2021/7/18 0018
 */
public class App1 {
    public static void main(String[] args) {
        JedisPool pool = null;
        try {
            pool = RedisUtils.getPool("127.0.0.1", 6379);
            Jedis jedis = pool.getResource();
            jedis.flushAll();
            jedis.hset("hehe", "baidu", "www.baidu.com");
            String result = jedis.hget("hehe", "baidu");
            System.out.println(result);
            System.out.println("==============================");
            Map<String,String> map = new HashMap<>();
            map.put("baidu", "www.baidu.com");
            map.put("sina", "www.sina.com");
            map.put("weixin", "www.weixin.com");
            jedis.hmset("xixi", map);
            List<String> list = jedis.hmget("xixi", "baidu", "sina", "weixin");
            list.forEach(a -> System.out.println(a));
            for (String s : list) {
                System.out.println(s);
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            RedisUtils.closePool();
        }


    }
}
