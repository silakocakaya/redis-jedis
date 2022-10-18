package com.redis.jedis;
import java.util.Set;

import redis.clients.jedis.HostAndPort;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.Transaction;

public class TransactionExample {

	public static void main(String[] args) {
		
		HostAndPort hostAndPort = new HostAndPort("10.10.10.74", 6379);
		try (Jedis jedis = new Jedis(hostAndPort)) {
			System.out.println("---TRANSACTION---");
			
			String friendsPrefix = "friends";
			String userOneId = "1";
			String userTwoId = "2";
	
			Transaction t = jedis.multi();
			t.sadd(friendsPrefix, userTwoId);
			t.sadd(friendsPrefix, userOneId);
			
			t.set("surname", "kocakaya");
			//jedis.get("surname"); Cannot use Jedis when in Multi
			t.exec();
			
			System.out.println(jedis.watch("friends" + userOneId));
			
			Set<String> friends = jedis.smembers("friends");
			System.out.println("smembers friends");
			friends.stream().forEach(p -> System.out.println(p));
			
			System.out.println(jedis.get("surname"));
		}
	}
}
