package com.cyb.redisclient.util.redis;

import java.util.Collection;
import java.util.Collections;
import java.util.Set;
import org.springframework.core.convert.converter.Converter;
import org.springframework.dao.DataAccessException;
import org.springframework.data.redis.connection.RedisConnection;
import org.springframework.data.redis.connection.RedisZSetCommands;
import org.springframework.data.redis.connection.RedisZSetCommands.Tuple;
import org.springframework.data.redis.core.ConvertingCursor;
import org.springframework.data.redis.core.Cursor;
import org.springframework.data.redis.core.RedisCallback;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ScanOptions;
import org.springframework.data.redis.core.ZSetOperations;

/**
 * Default implementation of {@link ZSetOperations}.
 * 
 * @author Costin Leau
 * @author Christoph Strobl
 */
public class DefaultZSetOperations<K, V> extends AbstractOperations<K, V> implements ZSetOperations<K, V> {

	DefaultZSetOperations(RedisTemplate<K, V> template) {
		super(template);
	}
	private volatile int dbIndex;
	
	DefaultZSetOperations(RedisTemplate<K, V> template, int dbIndex) {
		super(template);
		this.dbIndex = dbIndex;
	}
	public Boolean add(final K key, final V value, final double score) {
		final byte[] rawKey = rawKey(key);
		final byte[] rawValue = rawValue(value);

		return execute(new RedisCallback<Boolean>() {

			public Boolean doInRedis(RedisConnection connection) {
				connection.select(dbIndex);
				return connection.zAdd(rawKey, score, rawValue);
			}
		}, true);
	}

	public Long add(K key, Set<TypedTuple<V>> tuples) {
		final byte[] rawKey = rawKey(key);
		final Set<Tuple> rawValues = rawTupleValues(tuples);

		return execute(new RedisCallback<Long>() {

			public Long doInRedis(RedisConnection connection) {
				connection.select(dbIndex);
				return connection.zAdd(rawKey, rawValues);
			}
		}, true);
	}

	public Double incrementScore(K key, V value, final double delta) {
		final byte[] rawKey = rawKey(key);
		final byte[] rawValue = rawValue(value);

		return execute(new RedisCallback<Double>() {

			public Double doInRedis(RedisConnection connection) {
				connection.select(dbIndex);
				return connection.zIncrBy(rawKey, delta, rawValue);
			}
		}, true);
	}

	public Long intersectAndStore(K key, K otherKey, K destKey) {
		return intersectAndStore(key, Collections.singleton(otherKey), destKey);
	}

	public Long intersectAndStore(K key, Collection<K> otherKeys, K destKey) {
		final byte[][] rawKeys = rawKeys(key, otherKeys);
		final byte[] rawDestKey = rawKey(destKey);
		return execute(new RedisCallback<Long>() {

			public Long doInRedis(RedisConnection connection) {
				connection.select(dbIndex);
				return connection.zInterStore(rawDestKey, rawKeys);
			}
		}, true);
	}

	@Override
	public Long intersectAndStore(K k, Collection<K> collection, K k1, RedisZSetCommands.Aggregate aggregate, RedisZSetCommands.Weights weights) {
		return null;
	}

	public Set<V> range(K key, final long start, final long end) {
		final byte[] rawKey = rawKey(key);

		Set<byte[]> rawValues = execute(new RedisCallback<Set<byte[]>>() {

			public Set<byte[]> doInRedis(RedisConnection connection) {
				connection.select(dbIndex);
				return connection.zRange(rawKey, start, end);
			}
		}, true);

		return deserializeValues(rawValues);
	}

	public Set<V> reverseRange(K key, final long start, final long end) {
		final byte[] rawKey = rawKey(key);

		Set<byte[]> rawValues = execute(new RedisCallback<Set<byte[]>>() {

			public Set<byte[]> doInRedis(RedisConnection connection) {
				connection.select(dbIndex);
				return connection.zRevRange(rawKey, start, end);
			}
		}, true);

		return deserializeValues(rawValues);
	}

	public Set<TypedTuple<V>> rangeWithScores(K key, final long start, final long end) {
		final byte[] rawKey = rawKey(key);

		Set<Tuple> rawValues = execute(new RedisCallback<Set<Tuple>>() {

			public Set<Tuple> doInRedis(RedisConnection connection) {
				connection.select(dbIndex);
				return connection.zRangeWithScores(rawKey, start, end);
			}
		}, true);

		return deserializeTupleValues(rawValues);
	}

	public Set<TypedTuple<V>> reverseRangeWithScores(K key, final long start, final long end) {
		final byte[] rawKey = rawKey(key);

		Set<Tuple> rawValues = execute(new RedisCallback<Set<Tuple>>() {

			public Set<Tuple> doInRedis(RedisConnection connection) {
				connection.select(dbIndex);
				return connection.zRevRangeWithScores(rawKey, start, end);
			}
		}, true);

		return deserializeTupleValues(rawValues);
	}

	public Set<V> rangeByScore(K key, final double min, final double max) {
		final byte[] rawKey = rawKey(key);

		Set<byte[]> rawValues = execute(new RedisCallback<Set<byte[]>>() {

			public Set<byte[]> doInRedis(RedisConnection connection) {
				connection.select(dbIndex);
				return connection.zRangeByScore(rawKey, min, max);
			}
		}, true);

		return deserializeValues(rawValues);
	}

	public Set<V> rangeByScore(K key, final double min, final double max, final long offset, final long count) {
		final byte[] rawKey = rawKey(key);

		Set<byte[]> rawValues = execute(new RedisCallback<Set<byte[]>>() {

			public Set<byte[]> doInRedis(RedisConnection connection) {
				connection.select(dbIndex);
				return connection.zRangeByScore(rawKey, min, max, offset, count);
			}
		}, true);

		return deserializeValues(rawValues);
	}

	public Set<V> reverseRangeByScore(K key, final double min, final double max) {
		final byte[] rawKey = rawKey(key);

		Set<byte[]> rawValues = execute(new RedisCallback<Set<byte[]>>() {

			public Set<byte[]> doInRedis(RedisConnection connection) {
				connection.select(dbIndex);
				return connection.zRevRangeByScore(rawKey, min, max);
			}
		}, true);

		return deserializeValues(rawValues);
	}

	public Set<V> reverseRangeByScore(K key, final double min, final double max, final long offset, final long count) {
		final byte[] rawKey = rawKey(key);

		Set<byte[]> rawValues = execute(new RedisCallback<Set<byte[]>>() {

			public Set<byte[]> doInRedis(RedisConnection connection) {
				connection.select(dbIndex);
				return connection.zRevRangeByScore(rawKey, min, max, offset, count);
			}
		}, true);

		return deserializeValues(rawValues);
	}

	public Set<TypedTuple<V>> rangeByScoreWithScores(K key, final double min, final double max) {
		final byte[] rawKey = rawKey(key);

		Set<Tuple> rawValues = execute(new RedisCallback<Set<Tuple>>() {

			public Set<Tuple> doInRedis(RedisConnection connection) {
				connection.select(dbIndex);
				return connection.zRangeByScoreWithScores(rawKey, min, max);
			}
		}, true);

		return deserializeTupleValues(rawValues);
	}

	public Set<TypedTuple<V>> rangeByScoreWithScores(K key, final double min, final double max, final long offset,
			final long count) {
		final byte[] rawKey = rawKey(key);

		Set<Tuple> rawValues = execute(new RedisCallback<Set<Tuple>>() {

			public Set<Tuple> doInRedis(RedisConnection connection) {
				connection.select(dbIndex);
				return connection.zRangeByScoreWithScores(rawKey, min, max, offset, count);
			}
		}, true);

		return deserializeTupleValues(rawValues);
	}

	public Set<TypedTuple<V>> reverseRangeByScoreWithScores(K key, final double min, final double max) {
		final byte[] rawKey = rawKey(key);

		Set<Tuple> rawValues = execute(new RedisCallback<Set<Tuple>>() {

			public Set<Tuple> doInRedis(RedisConnection connection) {
				connection.select(dbIndex);
				return connection.zRevRangeByScoreWithScores(rawKey, min, max);

			}
		}, true);

		return deserializeTupleValues(rawValues);
	}

	public Set<TypedTuple<V>> reverseRangeByScoreWithScores(K key, final double min, final double max, final long offset,
			final long count) {
		final byte[] rawKey = rawKey(key);

		Set<Tuple> rawValues = execute(new RedisCallback<Set<Tuple>>() {

			public Set<Tuple> doInRedis(RedisConnection connection) {
				connection.select(dbIndex);
				return connection.zRevRangeByScoreWithScores(rawKey, min, max, offset, count);

			}
		}, true);

		return deserializeTupleValues(rawValues);
	}

	public Long rank(K key, Object o) {
		final byte[] rawKey = rawKey(key);
		final byte[] rawValue = rawValue(o);

		return execute(new RedisCallback<Long>() {

			public Long doInRedis(RedisConnection connection) {
				connection.select(dbIndex);
				Long zRank = connection.zRank(rawKey, rawValue);
				return (zRank != null && zRank.longValue() >= 0 ? zRank : null);
			}
		}, true);
	}

	public Long reverseRank(K key, Object o) {
		final byte[] rawKey = rawKey(key);
		final byte[] rawValue = rawValue(o);

		return execute(new RedisCallback<Long>() {

			public Long doInRedis(RedisConnection connection) {
				connection.select(dbIndex);
				Long zRank = connection.zRevRank(rawKey, rawValue);
				return (zRank != null && zRank.longValue() >= 0 ? zRank : null);
			}
		}, true);
	}

	public Long remove(K key, Object... values) {
		final byte[] rawKey = rawKey(key);
		final byte[][] rawValues = rawValues(values);

		return execute(new RedisCallback<Long>() {

			public Long doInRedis(RedisConnection connection) {
				connection.select(dbIndex);
				return connection.zRem(rawKey, rawValues);
			}
		}, true);
	}

	public Long removeRange(K key, final long start, final long end) {
		final byte[] rawKey = rawKey(key);
		return execute(new RedisCallback<Long>() {

			public Long doInRedis(RedisConnection connection) {
				connection.select(dbIndex);
				return connection.zRemRange(rawKey, start, end);
			}
		}, true);
	}

	public Long removeRangeByScore(K key, final double min, final double max) {
		final byte[] rawKey = rawKey(key);
		return execute(new RedisCallback<Long>() {

			public Long doInRedis(RedisConnection connection) {
				connection.select(dbIndex);
				return connection.zRemRangeByScore(rawKey, min, max);
			}
		}, true);
	}

	public Double score(K key, Object o) {
		final byte[] rawKey = rawKey(key);
		final byte[] rawValue = rawValue(o);

		return execute(new RedisCallback<Double>() {

			public Double doInRedis(RedisConnection connection) {
				connection.select(dbIndex);
				return connection.zScore(rawKey, rawValue);
			}
		}, true);
	}

	public Long count(K key, final double min, final double max) {
		final byte[] rawKey = rawKey(key);

		return execute(new RedisCallback<Long>() {

			public Long doInRedis(RedisConnection connection) {
				connection.select(dbIndex);
				return connection.zCount(rawKey, min, max);
			}
		}, true);
	}

	@Override
	public Long size(K key) {
		return zCard(key);
	}

	@Override
	public Long zCard(K key) {

		final byte[] rawKey = rawKey(key);
		return execute(new RedisCallback<Long>() {

			public Long doInRedis(RedisConnection connection) {
				connection.select(dbIndex);
				return connection.zCard(rawKey);
			}
		}, true);
	}

	@Override
	public Long unionAndStore(K key, K otherKey, K destKey) {
		return unionAndStore(key, Collections.singleton(otherKey), destKey);
	}

	@Override
	public Long unionAndStore(K key, Collection<K> otherKeys, K destKey) {
		final byte[][] rawKeys = rawKeys(key, otherKeys);
		final byte[] rawDestKey = rawKey(destKey);
		return execute(new RedisCallback<Long>() {

			public Long doInRedis(RedisConnection connection) {
				connection.select(dbIndex);
				return connection.zUnionStore(rawDestKey, rawKeys);
			}
		}, true);
	}

	/**
	 * 需重写
	 * @param key
	 * @param otherKeys
	 * @param destKey
	 * @param aggregate
	 * @param weights
	 * @return
	 */
	@Override
	public Long unionAndStore(K key, Collection<K> otherKeys, K destKey, RedisZSetCommands.Aggregate aggregate, RedisZSetCommands.Weights weights) {

		final byte[][] rawKeys = rawKeys(key, otherKeys);
		final byte[] rawDestKey = rawKey(destKey);
		return execute(new RedisCallback<Long>() {

			public Long doInRedis(RedisConnection connection) {
				connection.select(dbIndex);
				return connection.zUnionStore(rawDestKey, rawKeys);
			}
		}, true);
	}

	@Override
	public Cursor<TypedTuple<V>> scan(K key, final ScanOptions options) {

		final byte[] rawKey = rawKey(key);
		Cursor<Tuple> cursor = execute(new RedisCallback<Cursor<Tuple>>() {

			@Override
			public Cursor<Tuple> doInRedis(RedisConnection connection) throws DataAccessException {
				connection.select(dbIndex);
				return connection.zScan(rawKey, options);
			}
		}, true);

		return new ConvertingCursor<Tuple, TypedTuple<V>>(cursor, new Converter<Tuple, TypedTuple<V>>() {

			@Override
			public TypedTuple<V> convert(Tuple source) {
				return deserializeTuple(source);
			}
		});
	}

	@Override
	public Set<V> rangeByLex(K key, RedisZSetCommands.Range range) {
		return null;
	}

	@Override
	public Set<V> rangeByLex(K key, RedisZSetCommands.Range range, RedisZSetCommands.Limit limit) {
		return null;
	}
}
