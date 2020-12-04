package com.cyb.redisclient.service;

import com.cyb.redisclient.workcenter.WorkcenterResult;

public interface RedisService {
	
	public void addKV(String serverName, int dbIndex, String dataType, String key, String[] values, double[] scores, String[] members, String[] fields);

	public WorkcenterResult getKV(String serverName, int dbIndex, String dataType, String key);

	public void delKV(String serverName, int dbIndex, String deleteKeys);

}
