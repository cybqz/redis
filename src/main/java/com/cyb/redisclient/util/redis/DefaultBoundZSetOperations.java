package com.cyb.redisclient.util.redis;

import java.util.Collection;
import java.util.Set;
import org.springframework.data.redis.connection.DataType;
import org.springframework.data.redis.connection.RedisZSetCommands;
import org.springframework.data.redis.core.BoundZSetOperations;
import org.springframework.data.redis.core.Cursor;
import org.springframework.data.redis.core.RedisOperations;
import org.springframework.data.redis.core.ScanOptions;
import org.springframework.data.redis.core.ZSetOperations;
import org.springframework.data.redis.core.ZSetOperations.TypedTuple;

/**
 * Default implementation for {@link BoundZSetOperations}.
 * 
 * @author Costin Leau
 * @author Christoph Strobl
 */
public class DefaultBoundZSetOperations<K, V> extends DefaultBoundKeyOperations<K> implements BoundZSetOperations<K, V> {

	private final ZSetOperations<K, V> ops;

	/**
	 * Constructs a new <code>DefaultBoundZSetOperations</code> instance.
	 * 
	 * @param key
	 * @param operations
	 */
	public DefaultBoundZSetOperations(K key, RedisOperations<K, V> operations) {
		super(key, operations);
		this.ops = operations.opsForZSet();
	}

	public Boolean add(V value, double score) {
		return ops.add(getKey(), value, score);
	}

	public Long add(Set<TypedTuple<V>> tuples) {
		return ops.add(getKey(), tuples);
	}

	public Double incrementScore(V value, double delta) {
		return ops.incrementScore(getKey(), value, delta);
	}

	public RedisOperations<K, V> getOperations() {
		return ops.getOperations();
	}

	public Long intersectAndStore(K otherKey, K destKey) {
		return ops.intersectAndStore(getKey(), otherKey, destKey);
	}

	public Long intersectAndStore(Collection<K> otherKeys, K destKey) {
		return ops.intersectAndStore(getKey(), otherKeys, destKey);
	}

	@Override
	public Long intersectAndStore(Collection<K> collection, K k, RedisZSetCommands.Aggregate aggregate) {
		return null;
	}

	@Override
	public Long intersectAndStore(Collection<K> collection, K k, RedisZSetCommands.Aggregate aggregate, RedisZSetCommands.Weights weights) {
		return null;
	}

	public Set<V> range(long start, long end) {
		return ops.range(getKey(), start, end);
	}

	public Set<V> rangeByScore(double min, double max) {
		return ops.rangeByScore(getKey(), min, max);
	}

	public Set<TypedTuple<V>> rangeByScoreWithScores(double min, double max) {
		return ops.rangeByScoreWithScores(getKey(), min, max);
	}

	public Set<TypedTuple<V>> rangeWithScores(long start, long end) {
		return ops.rangeWithScores(getKey(), start, end);
	}

	public Set<V> reverseRangeByScore(double min, double max) {
		return ops.reverseRangeByScore(getKey(), min, max);
	}

	public Set<TypedTuple<V>> reverseRangeByScoreWithScores(double min, double max) {
		return ops.reverseRangeByScoreWithScores(getKey(), min, max);
	}

	public Set<TypedTuple<V>> reverseRangeWithScores(long start, long end) {
		return ops.reverseRangeWithScores(getKey(), start, end);
	}

	public Long rank(Object o) {
		return ops.rank(getKey(), o);
	}

	public Long reverseRank(Object o) {
		return ops.reverseRank(getKey(), o);
	}

	public Double score(Object o) {
		return ops.score(getKey(), o);
	}

	public Long remove(Object... values) {
		return ops.remove(getKey(), values);
	}

	public Long removeRange(long start, long end) {
		return ops.removeRange(getKey(), start, end);
	}

	public Long removeRangeByScore(double min, double max) {
		return ops.removeRangeByScore(getKey(), min, max);
	}

	public Set<V> reverseRange(long start, long end) {
		return ops.reverseRange(getKey(), start, end);
	}

	public Long count(double min, double max) {
		return ops.count(getKey(), min, max);
	}

	@Override
	public Long size() {
		return zCard();
	}

	@Override
	public Long zCard() {
		return ops.zCard(getKey());
	}

	public Long unionAndStore(K otherKey, K destKey) {
		return ops.unionAndStore(getKey(), otherKey, destKey);
	}

	public Long unionAndStore(Collection<K> otherKeys, K destKey) {
		return ops.unionAndStore(getKey(), otherKeys, destKey);
	}

	@Override
	public Long unionAndStore(Collection<K> collection, K k, RedisZSetCommands.Aggregate aggregate) {
		return null;
	}

	@Override
	public Long unionAndStore(Collection<K> collection, K k, RedisZSetCommands.Aggregate aggregate, RedisZSetCommands.Weights weights) {
		return null;
	}

	public DataType getType() {
		return DataType.ZSET;
	}

	@Override
	public Cursor<TypedTuple<V>> scan(ScanOptions options) {
		return ops.scan(getKey(), options);
	}

	@Override
	public Set<V> rangeByLex(RedisZSetCommands.Range range) {
		return null;
	}

	@Override
	public Set<V> rangeByLex(RedisZSetCommands.Range range, RedisZSetCommands.Limit limit) {
		return null;
	}
}
