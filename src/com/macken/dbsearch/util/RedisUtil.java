package com.macken.dbsearch.util;

import java.util.List;
import java.util.Set;

import com.macken.dbsearch.Config;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;

public class RedisUtil {
	public static RedisUtil instance = new RedisUtil();
	private JedisPool pool;
	private RedisUtil() {
		pool = new JedisPool(new JedisPoolConfig(), "10.12.143.61", 6380, 3000);
	}
	public void set(String key, String value) {
		Jedis jedis = pool.getResource();
		try {
			jedis.set(key, value);
		} finally {
			pool.returnResource(jedis);
		}
	}
	public String get(String key){
		String res=null;;
		Jedis jedis = pool.getResource();
		try {
			res=jedis.get(key);
		} finally {
			pool.returnResource(jedis);
		}
		return res;
	}
	public void setList(String key, String value) {
		Jedis jedis = pool.getResource();
		try {
			jedis.lpush(key, value);
		} finally {
			pool.returnResource(jedis);
		}
	}
	public List<String> getList(String key) {
		Jedis jedis = pool.getResource();
		List<String> res = null;
		try {
			res=jedis.lrange(key, 0, -1);
		} finally {
			pool.returnResource(jedis);
		}
		return res;
	}
	public void addSet(String key,String value){
		Jedis jedis = pool.getResource();
		try {
			jedis.sadd(key, value);
		} finally {
			pool.returnResource(jedis);
		}
	}
	public Set<String> getSet(String key){
		Jedis jedis = pool.getResource();
		Set<String> res=null;
		try {
			res=jedis.smembers(key);
		} finally {
			pool.returnResource(jedis);
		}
		return res;
	}
	public void del(String key){
		Jedis jedis = pool.getResource();
		try {
			jedis.del(key);
		} finally {
			pool.returnResource(jedis);
		}
	}
	public static void initdata(){
		RedisUtil.instance.del(Config.TOPICSET);
		
		String[] array={"http://www.douban.com/group/onepiece/"};
		for(int i=0;i<array.length;i++){
			RedisUtil.instance.addSet(Config.TOPICSET, array[i]);
		}
	}
	public static void main(String[] args) {
		initdata();
//		
	}
}
