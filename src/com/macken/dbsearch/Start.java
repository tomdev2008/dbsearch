package com.macken.dbsearch;

import java.util.Set;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;

public class Start {
	public static void main(String[] args) {
		//		Jedis jedis=new Jedis("10.12.143.61", 6380);
		//		jedis.select(10);
		//		jedis.set("macken","tan");

		JedisPool pool = new JedisPool(new JedisPoolConfig(), "localhost");
		Jedis jedis = pool.getResource();
		try {
			jedis.set("foo", "bar");
			String foobar = jedis.get("foo");
			jedis.zadd("sose", 0, "car");
			jedis.zadd("sose", 0, "bike");
			Set<String> sose = jedis.zrange("sose", 0, -1);
		} finally {
			pool.returnResource(jedis);
		}
		pool.destroy();

	}
}
