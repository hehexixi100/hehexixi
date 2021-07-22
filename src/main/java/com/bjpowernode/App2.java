package com.bjpowernode;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.Transaction;

import java.util.List;

/**
 * 唐坤
 * 2021/7/18 0018
 */
public class App2 {
    public static void main(String[] args) {
        JedisPool pool = null;
        pool = RedisUtils.getPool("127.0.0.1", 6379);
        Jedis jedis = pool.getResource();
        Transaction tran = jedis.multi();
        try {
            tran.set("username", "hehe");
            tran.get("username");

            List<Object> list = tran.exec();

            list.forEach(result -> System.out.println(result));


        } catch (Exception e) {
            e.printStackTrace();
            String discard = tran.discard();
            System.out.println(discard);
        } finally {
            RedisUtils.closePool();
        }
    }
}
