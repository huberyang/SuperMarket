package com.ncs.sso.component.impl;

import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;

import com.ncs.sso.component.JedisClient;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;

public class JedisClientSingle implements JedisClient {
	
	@Value("${redis_single_auth}")
	private String redis_single_auth;

	@Autowired
	private JedisPool jedisPool;


	@Override
	public String set(String key, String value) {

		Jedis jedis = jedisPool.getResource();
		jedis.auth(redis_single_auth);
		String result = jedis.set(key, value);
		// 关闭连接
		jedis.close();
		return result;
	}

	@Override
	public String get(String key) {

		Jedis jedis = jedisPool.getResource();
		jedis.auth(redis_single_auth);
		String result = jedis.get(key);
		jedis.close();
		return result;
	}

	@Override
	public Long hset(String key, String field, String value) {
		Jedis jedis = jedisPool.getResource();
		jedis.auth(redis_single_auth);
		Long result = jedis.hset(key, field, value);
		jedis.close();
		return result;
	}

	@Override
	public String hget(String key, String field) {
		Jedis jedis = jedisPool.getResource();
		jedis.auth(redis_single_auth);
		String result = jedis.hget(key, field);
		jedis.close();
		return result;
	}
	
	@Override
	public Long hdel(String key, String... field) {
		Jedis jedis = jedisPool.getResource();
		jedis.auth(redis_single_auth);
		Long result = jedis.hdel(key, field);
		jedis.close();
		return result;
	}


	@Override
	public Long incr(String key) {
		Jedis jedis = jedisPool.getResource();
		jedis.auth(redis_single_auth);
		Long result = jedis.incr(key);
		jedis.close();
		return result;
	}
	
	@Override
	public Long decr(String key) {
		Jedis jedis = jedisPool.getResource();
		jedis.auth(redis_single_auth);
		Long result = jedis.decr(key);
		jedis.close();
		return result;
	}

	@Override
	public Long lpush(String key, String... values) {
		Jedis jedis = jedisPool.getResource();
		jedis.auth(redis_single_auth);
		Long result = jedis.lpush(key, values);
		jedis.close();
		return result;
	}

	@Override
	public List<String> lrange(String key, long begin, long end) {
		Jedis jedis = jedisPool.getResource();
		jedis.auth(redis_single_auth);
		List<String> result = jedis.lrange(key,begin,end);
		jedis.close();
		return result;
	}

	@Override
	public Long rpush(String key, String... values) {
		Jedis jedis = jedisPool.getResource();
		jedis.auth(redis_single_auth);
		Long result = jedis.rpush(key, values);
		jedis.close();
		return result;
	}

	@Override
	public Long sadd(String key, String... members) {
		Jedis jedis = jedisPool.getResource();
		jedis.auth(redis_single_auth);
		Long result = jedis.sadd(key, members);
		jedis.close();
		return result;
	}

	@Override
	public Set<String> smembers(String key) {
		Jedis jedis = jedisPool.getResource();
		jedis.auth(redis_single_auth);
		Set<String> result = jedis.smembers(key);
		jedis.close();
		return result;
	}

	@Override
	public Long zadd(String key, double score, String member) {
		Jedis jedis = jedisPool.getResource();
		jedis.auth(redis_single_auth);
		Long result = jedis.zadd(key, score, member);
		jedis.close();
		return result;
	}

	@Override
	public Set<String> zrange(String key, Long begin, Long end) {
		Jedis jedis = jedisPool.getResource();
		jedis.auth(redis_single_auth);
		Set<String> result = jedis.zrange(key, begin, end);
		jedis.close();
		return result;
	}

	

	@Override
	public Long expire(String key,int second) {
		Jedis jedis = jedisPool.getResource();
		jedis.auth(redis_single_auth);
		Long result = jedis.expire(key, second);
		jedis.close();
		return result;
	}

	@Override
	public Long ttl(String key) {
		Jedis jedis = jedisPool.getResource();
		jedis.auth(redis_single_auth);
		Long result = jedis.ttl(key);
		jedis.close();
		return result;
	}


}
