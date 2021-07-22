package com.bjpowernode;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;

/**
 * Hello world!
 */
public class App {
    public static void main(String[] args) {
        JedisPool pool = null;
        try {
            pool = RedisUtils.getPool("127.0.0.1", 6379);
            Jedis jedis = pool.getResource();
            jedis.set("username", "xixi");
            String username = jedis.get("username");
            System.out.println(username);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            RedisUtils.closePool();
        }
    }
}
