package com.codelephant.friendzone;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import redis.clients.jedis.Jedis;

@SpringBootApplication
public class FriendzoneApplication {

	public static void main(String[] args) {
		SpringApplication.run(FriendzoneApplication.class, args);


	}
}
