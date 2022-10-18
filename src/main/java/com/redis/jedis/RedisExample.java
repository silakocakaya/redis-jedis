package com.redis.jedis;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Stream;

import redis.clients.jedis.HostAndPort;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.Transaction;

public class RedisExample {

	public static void main(String[] args) {
		
		HostAndPort hostAndPort = new HostAndPort("10.10.10.74", 6379);
		try (Jedis jedis = new Jedis(hostAndPort)) {
			jedis.set("name", "sila");
			String cachedResponse = jedis.get("events/city/rome");
			System.out.println(cachedResponse);
			
			System.out.println("---LIST---");
			
			jedis.lpush("alist", "a");
			jedis.lpush("alist", "b");
			jedis.lpush("alist", "c");
			jedis.lpush("alist", "d");
			
			String lpop = jedis.lpop("alist");
			String rpop = jedis.rpop("alist");
			
			List<String> list = jedis.lrange("alist", 0, 100);
			System.out.println("alist");
			list.stream().forEach(p -> System.out.println(p));
			
			System.out.println("lpop, rpop " + lpop + " " + rpop);
			
			System.out.println("---SET---");
			
			jedis.sadd("nicknames", "1");
			jedis.sadd("nicknames", "2");
			jedis.sadd("nicknames", "3");
			jedis.sadd("nicknames", "3");

			boolean exists = jedis.sismember("nicknames", "nickname#1");
			System.out.println("sismember " + exists);
			
			Set<String> nicknames = jedis.smembers("nicknames");
			
			System.out.println("smembers nicknames");
			
			nicknames.stream().forEach(p -> System.out.println(p));
			
			jedis.hset("user", "name", "Peter");
			jedis.hset("user", "job", "politician");
					
			String name = jedis.hget("user", "name");
			
			Map<String, String> fields = jedis.hgetAll("user");
			String job = fields.get("job");
			
			System.out.println("---MAP---");
			System.out.println("name: " + name);
			System.out.println("job: " + job);
			
			
			System.out.println("---SORTED SET---");
			Map<String, Double> scores = new HashMap();

			scores.put("PlayerOne", 3000.0);
			scores.put("PlayerTwo", 1500.0);
			scores.put("PlayerThree", 8200.0);

			scores.entrySet().forEach(playerScore -> {
			    jedis.zadd("ranking", playerScore.getValue(), playerScore.getKey());
			});
					
			String player = jedis.zrevrange("ranking", 0, 1).iterator().next();
			long rank = jedis.zrevrank("ranking", "PlayerOne");
			
			System.out.println("player: " + player + " rank: " + rank);
			
		}
	}
}
