package com.cyb.redisclient.dao;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ZSetOperations.TypedTuple;
import org.springframework.stereotype.Service;
import com.cyb.redisclient.util.RValue;
import com.cyb.redisclient.config.RedisConfig;

@Service
public class RedisDao extends RedisConfig {
	
	@Autowired
	RedisTemplateFactory redisTemplateFactory;

	public void addSTRING(String serverName, int dbIndex, String key, String value) {
		RedisTemplate<String, Object> redisTemplate = redisTemplateFactory.getRedisTemplate(serverName);
		redisConnectionDbIndex.set(dbIndex);
		redisTemplate.opsForValue().set(key, value);
	}
	
	public Long addLIST(String serverName, int dbIndex, String key, String[] values) {
		RedisTemplate<String, Object> redisTemplate = redisTemplateFactory.getRedisTemplate(serverName);
		redisConnectionDbIndex.set(dbIndex);
		return redisTemplate.opsForList().rightPushAll(key, values);
	}
	
	public Long addSET(String serverName, int dbIndex, String key,
			String[] values) {
		RedisTemplate<String, Object> redisTemplate = redisTemplateFactory.getRedisTemplate(serverName);
		redisConnectionDbIndex.set(dbIndex);
		return redisTemplate.opsForSet().add(key, values);
	}

	public void addZSET(String serverName, int dbIndex, String key,
			double[] scores, String[] members) {
		RedisTemplate<String, Object> redisTemplate = redisTemplateFactory.getRedisTemplate(serverName);
		redisConnectionDbIndex.set(dbIndex);
		Set<TypedTuple<Object>> zset = new HashSet<TypedTuple<Object>>();
		for(int i=0;i<members.length;i++) {
			final Object ob = members[i];
			final double sc = scores[i];
			zset.add(new TypedTuple () {
				private Object v;
				private double score;
				{
					v = ob;
					score = sc;
				}
				@Override
				public int compareTo(Object o) {
					if(o == null) return 1;
					if(o instanceof TypedTuple) {
						TypedTuple tto = (TypedTuple) o;
						return this.getScore()-tto.getScore() >= 0?1:-1;
					}
					return 1;
				}
				@Override
				public Object getValue() {
					return v;
				}
				@Override
				public Double getScore() {
					return score;
				}
			});
		}
		redisTemplate.opsForZSet().add(key, zset);
	}
	
	public void addHASH(String serverName, int dbIndex, String key,
			String[] fields, String[] values) {
		RedisTemplate<String, Object> redisTemplate = redisTemplateFactory.getRedisTemplate(serverName);
		redisConnectionDbIndex.set(dbIndex);
		Map<String, String> hashmap = new HashMap<String, String>();
		
		for(int i=0;i<fields.length;i++) {
			String field = fields[i];
			String value = values[i];
			hashmap.put(field, value);
		}
		redisTemplate.opsForHash().putAll(key, hashmap);
	}

	public Object getSTRING(String serverName, int dbIndex, String key) {
		RedisTemplate<String, Object> redisTemplate = redisTemplateFactory.getRedisTemplate(serverName);
		redisConnectionDbIndex.set(dbIndex);
		final Object value = redisTemplate.opsForValue().get(key);
		List list = new ArrayList();
		list.add(new RValue(value));
		return list;
	}
	
	public Object getLIST(String serverName, int dbIndex, String key) {
		RedisTemplate<String, Object> redisTemplate = redisTemplateFactory.getRedisTemplate(serverName);
		redisConnectionDbIndex.set(dbIndex);
		List<Object> values = redisTemplate.opsForList().range(key, 0, 1000);
		return RValue.creatListValue(values);
	}

	public Object getSET(String serverName, int dbIndex, String key) {
		RedisTemplate<String, Object> redisTemplate = redisTemplateFactory.getRedisTemplate(serverName);
		redisConnectionDbIndex.set(dbIndex);
		List<Object> values = redisTemplate.opsForSet().randomMembers(key, 1000);
		//Set<Object> values = redisTemplate.opsForSet().members(key);
		return RValue.creatSetValue(new HashSet(values));
	}

	public Object getZSET(String serverName, int dbIndex, String key) {
		RedisTemplate<String, Object> redisTemplate = redisTemplateFactory.getRedisTemplate(serverName);
		redisConnectionDbIndex.set(dbIndex);
		Set<TypedTuple<Object>> values = redisTemplate.opsForZSet().rangeWithScores(key, 0, 1000);
		return RValue.creatZSetValue(values);
	}

	public Object getHASH(String serverName, int dbIndex, String key) {
		RedisTemplate<String, Object> redisTemplate = redisTemplateFactory.getRedisTemplate(serverName);
		redisConnectionDbIndex.set(dbIndex);
		Map<Object, Object> values = redisTemplate.opsForHash().entries(key);
		return RValue.creatHashValue(values);
	}

	//--- delete
	public Long delRedisKeys(String serverName, int dbIndex, String deleteKeys) {
		RedisTemplate<String, Object> redisTemplate = redisTemplateFactory.getRedisTemplate(serverName);
		redisConnectionDbIndex.set(dbIndex);
		String[] keys = deleteKeys.split(",");
		return redisTemplate.delete(Arrays.asList(keys));
	}
	
	public Long delRedisHashField(String serverName, int dbIndex, String key, String field) {
		RedisTemplate<String, Object> redisTemplate = redisTemplateFactory.getRedisTemplate(serverName);
		redisConnectionDbIndex.set(dbIndex);
		List<String> hashKeys = new ArrayList<String>();
		hashKeys.add(field);
		return redisTemplate.opsForHash().delete(key, hashKeys.toArray());
	}

	public void updateHashField(String serverName, int dbIndex, String key, String field, String value) {
		RedisTemplate<String, Object> redisTemplate = redisTemplateFactory.getRedisTemplate(serverName);
		redisConnectionDbIndex.set(dbIndex);
		String hashKey = field;
		redisTemplate.opsForHash().put(key, hashKey, value);
		return;
	}

	public Long delSetValue(String serverName, int dbIndex, String key, String value) {
		RedisTemplate<String, Object> redisTemplate = redisTemplateFactory.getRedisTemplate(serverName);
		redisConnectionDbIndex.set(dbIndex);
		return redisTemplate.opsForSet().remove(key, value);
	}

	public Long updateSetValue(String serverName, int dbIndex, String key, String value) {
		RedisTemplate<String, Object> redisTemplate = redisTemplateFactory.getRedisTemplate(serverName);
		redisConnectionDbIndex.set(dbIndex);
		return redisTemplate.opsForSet().add(key, value);
	}
	
	public Long delZSetValue(String serverName, int dbIndex, String key, String member) {
		RedisTemplate<String, Object> redisTemplate = redisTemplateFactory.getRedisTemplate(serverName);
		redisConnectionDbIndex.set(dbIndex);
		String value = member;
		return redisTemplate.opsForZSet().remove(key, value);
	}

	public Boolean updateZSetValue(String serverName, int dbIndex, String key, double score, String member) {
		RedisTemplate<String, Object> redisTemplate = redisTemplateFactory.getRedisTemplate(serverName);
		redisConnectionDbIndex.set(dbIndex);
		String value = member;
		return redisTemplate.opsForZSet().add(key, value, score);
	}
	
	public Object ldelListValue(String serverName, int dbIndex, String key) {
		RedisTemplate<String, Object> redisTemplate = redisTemplateFactory.getRedisTemplate(serverName);
		redisConnectionDbIndex.set(dbIndex);
		return redisTemplate.opsForList().leftPop(key);
	}
	
	public Object rdelListValue(String serverName, int dbIndex, String key) {
		RedisTemplate<String, Object> redisTemplate = redisTemplateFactory.getRedisTemplate(serverName);
		redisConnectionDbIndex.set(dbIndex);
		return redisTemplate.opsForList().rightPop(key);
	}

	public Long lupdateListValue(String serverName, int dbIndex, String key, String value) {
		RedisTemplate<String, Object> redisTemplate = redisTemplateFactory.getRedisTemplate(serverName);
		redisConnectionDbIndex.set(dbIndex);
		return redisTemplate.opsForList().leftPush(key, value);
	}
	
	public Long rupdateListValue(String serverName, int dbIndex, String key, String value) {
		RedisTemplate<String, Object> redisTemplate = redisTemplateFactory.getRedisTemplate(serverName);
		redisConnectionDbIndex.set(dbIndex);
		return redisTemplate.opsForList().rightPush(key, value);
	}

	public void delRedisValue(String serverName, int dbIndex, String key) {
		RedisTemplate<String, Object> redisTemplate = redisTemplateFactory.getRedisTemplate(serverName);
		redisConnectionDbIndex.set(dbIndex);
		redisTemplate.opsForValue().set(key, "");
		return;
	}

	public void updateValue(String serverName, int dbIndex, String key, String value) {
		RedisTemplate<String, Object> redisTemplate = redisTemplateFactory.getRedisTemplate(serverName);
		redisConnectionDbIndex.set(dbIndex);
		redisTemplate.opsForValue().set(key, value);
		return;
	}
}
