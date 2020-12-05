package com.cyb.redisclient.util;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CopyOnWriteArrayList;
import com.cyb.redisclient.util.ztree.ZNode;
import org.springframework.data.redis.core.RedisTemplate;

@SuppressWarnings("rawtypes")
public interface Constant {
	
	public static final Map<String, RedisTemplate> REDIS_TEMPLATES_MAP = new HashMap<String, RedisTemplate>();
	public static final Map<String, CopyOnWriteArrayList<RKey>> REDIS_KEYS_LIST_MAP = new HashMap<String, CopyOnWriteArrayList<RKey>>();
	public static final Map<RKey, Object> REDIS_VM_CACHE = new ConcurrentHashMap<RKey, Object>();
	public static final CopyOnWriteArrayList<ZNode> REDIS_NAVIGATE_ZTREE = new CopyOnWriteArrayList<ZNode>();
	public static final CopyOnWriteArrayList<Map<String, Object>> REDIS_SERVER_CACHE = new CopyOnWriteArrayList<Map<String, Object>>();

	public static final int DEFAULT_ITEMS_PER_PAGE = 10;
	public static final String DEFAULT_REDISKEY_SEPARATOR = ":";
	public static final int REDIS_DEFAULT_DB_SIZE = 15;
	public static final String DEFAULT_SEPARATOR = "_";
	public static final String UTF_8 = "utf-8";

	/** default **/
	public static final int DEFAULT_DBINDEX = 0;
	
	/** query key **/
	public static final String MIDDLE_KEY = "middle";
	public static final String HEAD_KEY = "head";
	public static final String TAIL_KEY = "tail";
	public static final String EMPTY_STRING = "";
	
	/** operator for log **/
	public static final String GETKV = "GETKV";
}
