package com.ncs.rest.component.impl;

import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;

import com.ncs.rest.component.JedisClient;

import redis.clients.jedis.JedisCluster;

public class JedisClientCluster implements JedisClient {

	@Autowired
	private JedisCluster jedisCluster;

	@Override
	public String set(String key, String value) {

		return jedisCluster.set(key, value);
	}

	@Override
	public String get(String key) {

		return jedisCluster.get(key);
	}

	@Override
	public Long hset(String key, String field, String value) {

		return jedisCluster.hset(key, field, value);
	}

	@Override
	public String hget(String key, String field) {

		return jedisCluster.hget(key, field);
	}
	
	@Override
	public Long hdel(String key, String... field) {
		return jedisCluster.hdel(key, field);
	}

	@Override
	public Long incr(String key) {

		return jedisCluster.incr(key);
	}

	@Override
	public Long lpush(String key, String... values) {

		return jedisCluster.lpush(key, values);
	}

	@Override
	public List<String> lrange(String key, long begin, long end) {

		return jedisCluster.lrange(key, begin, end);
	}

	@Override
	public Long rpush(String key, String... values) {

		return jedisCluster.rpush(key, values);
	}

	@Override
	public Long sadd(String key, String... members) {
		return jedisCluster.sadd(key, members);
	}

	@Override
	public Set<String> smembers(String key) {
		return jedisCluster.smembers(key);
	}

	@Override
	public Long zadd(String key, double score, String member) {
		return jedisCluster.zadd(key, score, member);
	}

	@Override
	public Set<String> zrange(String key, Long begin, Long end) {
		return jedisCluster.zrange(key, begin, end);
	}

	@Override
	public Long decr(String key) {
		return jedisCluster.decr(key);
	}

	@Override
	public Long expire(String key, int second) {
		return jedisCluster.expire(key, second);
	}

	@Override
	public Long ttl(String key) {
		return jedisCluster.ttl(key);
	}


}
