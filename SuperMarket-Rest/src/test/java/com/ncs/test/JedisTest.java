package com.ncs.test;

import java.util.HashSet;
import java.util.Set;

import redis.clients.jedis.HostAndPort;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisCluster;
import redis.clients.jedis.JedisPool;

public class JedisTest {
	
	//单机版测试,最简单的测试使用
	public void singleTest() throws Exception {
		
		Jedis jedis=new Jedis("127.0.0.1",7000);
		jedis.set("name", "jack");
		//简单的String set
		String result = jedis.get("name");
		//list set，应为list是有序的所以需要设置元素在list集合的索引
		//jedis.lset(key, index, value)
		//hash set 由于是存储在映射表，所以我们需要指定field域
		//jedis.hset(key, field, value)
		System.out.println(result);
	}
	
	
	//使用连接池测试，单机版
	public void dataSourceTest() throws Exception {
		//创建jedis连接池
		JedisPool jedisPool=new JedisPool("127.0.0.1",7000);
		//从连接池中拿到jedis对象
		Jedis jedis=jedisPool.getResource();
		
		jedis.set("name", "jack");
		String result = jedis.get("name");
		
		System.out.println(result);
		
		//关闭jedis连接对象
		jedis.close();
	
		//当系统关闭时需要关闭jedis连接池
		jedisPool.close();
	}
	
	//jedis集群版测试
	public void clusterTest() throws Exception {
		
		//定义集群中所有的节点
		Set<HostAndPort> nodes=new HashSet<HostAndPort>();
		nodes.add(new HostAndPort("127.0.0.1",7001));
		nodes.add(new HostAndPort("127.0.0.1",7002));
		nodes.add(new HostAndPort("127.0.0.1",7003));
		nodes.add(new HostAndPort("127.0.0.1",7004));
		nodes.add(new HostAndPort("127.0.0.1",7005));
		nodes.add(new HostAndPort("127.0.0.1",7006));
		
		//创建jedisCluster对象
	    //注意JedisCluster在项目中是单例存在的
		JedisCluster cluster=new JedisCluster(nodes);
		cluster.set("name", "jack");
		String result = cluster.get("name");
		
		//在系统关闭时需要关闭jedis集群
		cluster.close();
		
	}

}
