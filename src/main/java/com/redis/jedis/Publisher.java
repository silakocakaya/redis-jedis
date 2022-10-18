package com.redis.jedis;
import redis.clients.jedis.HostAndPort;
import redis.clients.jedis.Jedis;

public class Publisher {

	public static void main(String[] args) {
		
		HostAndPort hostAndPort = new HostAndPort("10.10.10.74", 6379);
		try (Jedis jPublisher = new Jedis(hostAndPort)) {
			jPublisher.publish("channel", "test message");
		}
	}
}
