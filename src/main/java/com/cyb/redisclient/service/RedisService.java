package com.cyb.redisclient.service;

import com.cyb.redisclient.workcenter.WorkcenterResult;

public interface RedisService {

	void addRedisServer(String name, String host, int port, String password);
	
	void addKV(String serverName, int dbIndex, String dataType, String key, String[] values, double[] scores, String[] members, String[] fields);

	WorkcenterResult getKV(String serverName, int dbIndex, String dataType, String key);

	void delKV(String serverName, int dbIndex, String deleteKeys);

}
