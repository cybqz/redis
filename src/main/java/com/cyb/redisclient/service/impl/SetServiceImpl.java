package com.cyb.redisclient.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cyb.redisclient.dao.RedisDao;
import com.cyb.redisclient.service.SetService;

@Service
public class SetServiceImpl implements SetService {

	@Autowired
	private RedisDao redisDao;
	
	@Override
	public void delSetValue(String serverName, int dbIndex, String key, String value) {
		redisDao.delSetValue(serverName, dbIndex, key, value);
	}

	@Override
	public void updateSetValue(String serverName, int dbIndex, String key, String value) {
		redisDao.updateSetValue(serverName, dbIndex, key, value);		
	}
	
	

}
