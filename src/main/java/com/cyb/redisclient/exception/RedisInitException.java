package com.cyb.redisclient.exception;


public class RedisInitException extends RuntimeException {

	public RedisInitException(Exception e) {
		super(e);
	}

	public RedisInitException(String msg, Throwable e1) {
		super(msg, e1);
	}
	
}
