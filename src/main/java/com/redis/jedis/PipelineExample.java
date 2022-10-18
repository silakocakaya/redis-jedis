package com.redis.jedis;
import java.util.List;
import java.util.Set;

import redis.clients.jedis.HostAndPort;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.Pipeline;
import redis.clients.jedis.Response;

public class PipelineExample {

	public static void main(String[] args) {
		
		HostAndPort hostAndPort = new HostAndPort("10.10.10.74", 6379);
		try (Jedis jedis = new Jedis(hostAndPort)) {
			
			String userOneId = "1";
			String userTwoId = "2";

			Pipeline p = jedis.pipelined();
			p.sadd("searched" + userOneId, "paris");
			p.zadd("rankings", 126, userOneId);
			p.zadd("rankings", 325, userTwoId);
			p.set("deneme", "deneme");
			Response<Boolean> pipeExists = p.sismember("searched" + userOneId, "paris");
			Response<List<String>> pipeRanking = p.zrange("rankings", 0, -1);
			//System.out.println("before sync: " + jedis.get("deneme")); Cannot use Jedis when in Pipeline
			p.sync();

			Boolean exists = pipeExists.get();
			List<String> ranking = pipeRanking.get();
			
			System.out.println(exists);
			ranking.stream().forEach(rank -> System.out.println(rank));
			System.out.println(jedis.get("deneme"));
			
		}
	}
}
