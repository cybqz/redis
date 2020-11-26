package com.cyb.redisclient.config;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.Semaphore;
import com.cyb.redisclient.util.Constant;
import com.cyb.redisclient.util.*;
import com.cyb.redisclient.util.ztree.RedisZtreeUtil;
import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.PropertyAccessor;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisConnection;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.core.RedisConnectionUtils;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.Jackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.StringRedisSerializer;
import com.cyb.redisclient.exception.ConcurrentException;

@Configuration
public abstract class RedisConfig implements Constant {

	private static Log log = LogFactory.getLog(RedisConfig.class);

	@Value("${spring.redis.host}")
	private String host;
	@Value("${spring.redis.port}")
	private String port;
	@Value("${spring.redis.password}")
	private String password;

	private String name = "Rname";

	public static volatile RefreshModeEnum refreshMode = RefreshModeEnum.manually;
	public static volatile ShowTypeEnum showType = ShowTypeEnum.show;

	protected volatile Semaphore limitUpdate = new Semaphore(1);
	protected static final int LIMIT_TIME = 3;

	public static ThreadLocal<Integer> redisConnectionDbIndex = new ThreadLocal<Integer>() {
		@Override
		protected Integer initialValue() {
			return 0;
		}
	};
	protected static ThreadLocal<Semaphore> updatePermition = new ThreadLocal<Semaphore>() {
		@Override
		protected Semaphore initialValue() {
			return null;
		}
	};
	protected static ThreadLocal<Long> startTime = new ThreadLocal<Long>() {
		protected Long initialValue() {
			return 0l;
		}
	};

	@Bean
	@SuppressWarnings("all")
	public RedisTemplate<String, Object> redisTemplate(RedisConnectionFactory factory) {
		RedisTemplate<String, Object> template = new RedisTemplate<String, Object>();
		template.setConnectionFactory(factory);
		Jackson2JsonRedisSerializer jackson2JsonRedisSerializer = new Jackson2JsonRedisSerializer(Object.class);
		ObjectMapper om = new ObjectMapper();
		om.setVisibility(PropertyAccessor.ALL, JsonAutoDetect.Visibility.ANY);
		om.enableDefaultTyping(ObjectMapper.DefaultTyping.NON_FINAL);
		jackson2JsonRedisSerializer.setObjectMapper(om);
		StringRedisSerializer stringRedisSerializer = new StringRedisSerializer();
		// key采用String的序列化方式
		template.setKeySerializer(stringRedisSerializer);
		// hash的key也采用String的序列化方式
		template.setHashKeySerializer(stringRedisSerializer);
		// value序列化方式采用jackson
		template.setValueSerializer(jackson2JsonRedisSerializer);
		// hash的value序列化方式采用jackson
		template.setHashValueSerializer(jackson2JsonRedisSerializer);
		template.afterPropertiesSet();


		RedisConfig.redisTemplatesMap.put(name, template);

		Map<String, Object> redisServerMap = new HashMap<String, Object>();
		redisServerMap.put("name", name);
		redisServerMap.put("host", host);
		redisServerMap.put("port", port);
		redisServerMap.put("password", password);
		RedisConfig.redisServerCache.add(redisServerMap);

		initRedisKeysCache(template, name);

		RedisZtreeUtil.initRedisNavigateZtree(name);

		return template;
	}
	
	private Semaphore getSempahore() {
		startTime.set(System.currentTimeMillis());
		updatePermition.set(limitUpdate);
		return updatePermition.get();
		
	}

	protected boolean getUpdatePermition() {
		Semaphore sempahore = getSempahore();
		boolean permit = sempahore.tryAcquire(1);
		return permit;
	}
	
	protected void finishUpdate() {
		Semaphore semaphore = updatePermition.get();
		if(semaphore==null) {
			throw new ConcurrentException("semaphore==null");
		}
		final Semaphore fsemaphore = semaphore;
		new Thread(new Runnable() {
			
			Semaphore RSemaphore;
			{
				RSemaphore = fsemaphore;
			}
			
			@Override
			public void run() {
				long start = startTime.get();
				long now = System.currentTimeMillis();
				try {
					long needWait = start + LIMIT_TIME * 1000 - now;
					if(needWait > 0L) {
						Thread.sleep(needWait);
					}
				} catch (InterruptedException e) {
					log.warn("finishUpdate 's release semaphore thread had be interrupted");
				}
				RSemaphore.release(1);
				logCurrentTime("semaphore.release(1) finish");
			}
		}).start();
	}
	
	private void initRedisKeysCache(RedisTemplate redisTemplate, String name) {
		for(int i=0;i<=REDIS_DEFAULT_DB_SIZE;i++) {
			initRedisKeysCache(redisTemplate, name, i);
		}
	}
	
	
	protected void initRedisKeysCache(RedisTemplate redisTemplate, String serverName , int dbIndex) {
		RedisConnection connection = RedisConnectionUtils.getConnection(redisTemplate.getConnectionFactory());
		connection.select(dbIndex);
		Set<byte[]> keysSet = connection.keys("*".getBytes());
		connection.close();
		List<RKey> tempList = new ArrayList<RKey>();
		ConvertUtil.convertByteToString(connection, keysSet, tempList);
		Collections.sort(tempList);
		CopyOnWriteArrayList<RKey> redisKeysList = new CopyOnWriteArrayList<RKey>(tempList);
		if(redisKeysList.size()>0) {
			redisKeysListMap.put(serverName+DEFAULT_SEPARATOR+dbIndex, redisKeysList);
		}
	}
	
	protected static void logCurrentTime(String code) {
		log.debug("code:"+code+"当前时间:" + System.currentTimeMillis());
	}
}
