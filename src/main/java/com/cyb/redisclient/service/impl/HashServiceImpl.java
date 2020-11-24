package com.cyb.redisclient.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cyb.redisclient.dao.RedisDao;
import com.cyb.redisclient.service.HashService;

@Service
public class HashServiceImpl implements HashService {

	@Autowired
	private RedisDao redisDao;
	
	@Override
	public void delHashField(String serverName, int dbIndex, String key, String field) {
		redisDao.delRedisHashField(serverName, dbIndex, key, field);
	}

	@Override
	public void updateHashField(String serverName, int dbIndex, String key, String field, String value) {
		redisDao.updateHashField(serverName, dbIndex, key, field, value);
	}
	
}
