package com.cyb.redisclient.service;

import java.util.Set;

import com.cyb.redisclient.util.Pagination;
import com.cyb.redisclient.util.RKey;
import com.cyb.redisclient.util.ztree.ZNode;

public interface ViewService {

	Set<ZNode> getLeftTree();

	Set<RKey> getRedisKeys(Pagination pagination, String serverName, String dbIndex,
                           String[] keyPrefixs, String queryKey, String queryValue);

	Set<ZNode> refresh();

	void changeRefreshMode(String mode);

	void changeShowType(String state);

	void refreshAllKeys();

}
