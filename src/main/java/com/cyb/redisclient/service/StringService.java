package com.cyb.redisclient.service;

public interface StringService {
	
	public void delValue(String serverName, int dbIndex, String key) ;
	
	public void updateValue(String serverName, int dbIndex, String key, String value) ;
}
