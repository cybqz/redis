package com.cyb.redisclient.util.redis;

import org.springframework.data.redis.connection.DefaultStringRedisConnection;
import org.springframework.data.redis.connection.RedisConnection;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.serializer.RedisSerializer;
import org.springframework.data.redis.serializer.StringRedisSerializer;
import java.util.concurrent.TimeUnit;

public class MyStringRedisTemplate extends MyRedisTemplate<String, String> {

	public MyStringRedisTemplate() {
		RedisSerializer<String> stringSerializer = new StringRedisSerializer();
		setKeySerializer(stringSerializer);
		setValueSerializer(stringSerializer);
		setHashKeySerializer(stringSerializer);
		setHashValueSerializer(stringSerializer);
	}

	public MyStringRedisTemplate(RedisConnectionFactory connectionFactory) {
		this();
		setConnectionFactory(connectionFactory);
		afterPropertiesSet();
	}

	protected RedisConnection preProcessConnection(RedisConnection connection, boolean existingConnection) {
		return new DefaultStringRedisConnection(connection);
	}

	@Override
	public void restore(String key, byte[] value, long timeToLive, TimeUnit unit) {

	}
}
