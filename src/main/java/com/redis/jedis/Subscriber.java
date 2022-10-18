package com.redis.jedis;
import redis.clients.jedis.HostAndPort;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPubSub;

public class Subscriber {

	public static void main(String[] args) {
		
		HostAndPort hostAndPort = new HostAndPort("10.10.10.74", 6379);
		try (Jedis jSubscriber = new Jedis(hostAndPort)) {
			jSubscriber.subscribe(new JedisPubSubInner(), "channel");
		}
	}
	
}

class JedisPubSubInner extends JedisPubSub {
	
	@Override
	public void onMessage(String channel, String message) {
		System.out.println( message);
	}
}
