package com.cyb.redisclient.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.connection.DataType;
import org.springframework.data.redis.connection.RedisConnection;
import org.springframework.data.redis.core.RedisConnectionUtils;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import com.cyb.redisclient.dao.RedisDao;
import com.cyb.redisclient.service.RedisService;
import com.cyb.redisclient.config.RedisConfig;

import com.cyb.redisclient.workcenter.WorkcenterCodeEnum;
import com.cyb.redisclient.workcenter.WorkcenterResult;
import com.cyb.redisclient.common.WebConstant;

@Service
public class RedisServiceImpl extends RedisConfig implements RedisService, WebConstant  {
	
	@Autowired
	private RedisDao redisDao;
	
	@Override
	public Long addKV(
			String serverName, int dbIndex, String dataType,
			String key, String[] values, double[] scores,
			String[] members, String[] fields) {

		Long result = null;
		switch(dataType) {
		case "STRING":
			result = redisDao.addSTRING(serverName, dbIndex, key, values[0]);
			break;
		case "LIST":
			result = redisDao.addLIST(serverName, dbIndex, key, values);
			break;
		case "SET":
			result = redisDao.addSET(serverName, dbIndex, key, values);
			break;
		case "ZSET":
			result = redisDao.addZSET(serverName, dbIndex, key, scores, members);
			break;
		case "HASH":
			result = redisDao.addHASH(serverName, dbIndex, key, fields, values);
			break;
		}
		return result;
	}
	@Override
	public WorkcenterResult getKV(String serverName, int dbIndex, String dataType, String key) {
		
		Object values = null;
		switch(dataType) {
		case "STRING":
			values = redisDao.getSTRING(serverName, dbIndex, key);
			break;
		case "LIST":
			values = redisDao.getLIST(serverName, dbIndex, key);
			break;
		case "SET":
			values = redisDao.getSET(serverName, dbIndex, key);
			break;
		case "ZSET":
			values = redisDao.getZSET(serverName, dbIndex, key);
			break;
		case "HASH":
			values = redisDao.getHASH(serverName, dbIndex, key);
			break;
		case "NONE":
			//if showType = ShowTypeEnum.hide
			dataType = getDataType(serverName, dbIndex, key);
			values = getKV(serverName, dbIndex, key);
			break;
		}
		
		final String dataType1 = dataType;
		final Object values1 = values;
		return WorkcenterResult.custom().setOK(WorkcenterCodeEnum.valueOf(OK_REDISKV_UPDATE), new Object() {
				public String dataType;
				public Object values;
				{
					dataType = dataType1;
					values = values1;
				}
			}).build();
	}

	private String getDataType(String serverName, int dbIndex, String key) {
		RedisTemplate redisTemplate = RedisConfig.redisTemplatesMap.get(serverName);
		RedisConnection connection = RedisConnectionUtils.getConnection(redisTemplate.getConnectionFactory());
		connection.select(dbIndex);
		DataType dataType = connection.type(key.getBytes());
		connection.close();
		return dataType.name().toUpperCase();
	}
	
	private Object getKV(String serverName, int dbIndex, String key) {
		RedisTemplate redisTemplate = RedisConfig.redisTemplatesMap.get(serverName);
		RedisConnection connection = RedisConnectionUtils.getConnection(redisTemplate.getConnectionFactory());
		connection.select(dbIndex);
		DataType dataType = connection.type(key.getBytes());
		connection.close();
		Object values = null;
		switch(dataType) {
		case STRING:
			values = redisDao.getSTRING(serverName, dbIndex, key);
			break;
		case LIST:
			values = redisDao.getLIST(serverName, dbIndex, key);
			break;
		case SET:
			values = redisDao.getSET(serverName, dbIndex, key);
			break;
		case ZSET:
			values = redisDao.getZSET(serverName, dbIndex, key);
			break;
		case HASH:
			values = redisDao.getHASH(serverName, dbIndex, key);
			break;
		case NONE:
			//never be here
			values = null;
		}
		return values;
	}
	@Override
	public Long delKV(String serverName, int dbIndex, String deleteKeys) {
		return redisDao.delRedisKeys(serverName, dbIndex, deleteKeys);
	}
	
}
