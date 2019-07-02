package com.ncs.test;

import java.util.Formatter;
import java.util.HashSet;
import java.util.Set;

import org.apache.commons.lang3.StringUtils;
import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.github.pagehelper.StringUtil;
import com.ncs.rest.component.JedisClient;

import redis.clients.jedis.HostAndPort;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisCluster;
import redis.clients.jedis.JedisPool;

public class JedisTest {
	
	//单机版测试,最简单的测试使用
	@Test
	public void singleTest() throws Exception {
		
		Jedis jedis=new Jedis("192.168.28.130",6379);
		//jedis.set("name", "jack");
		//简单的String set
		
		jedis.auth("123456");
		
		String result = jedis.get("name");
		//list set，应为list是有序的所以需要设置元素在list集合的索引
		//jedis.lset(key, index, value)
		//hash set 由于是存储在映射表，所以我们需要指定field域
		//jedis.hset(key, field, value)
		System.out.println(result);
		
		//关闭jedis连接对象
		jedis.close();
	}
	
	
	//使用连接池测试，单机版
	@Test
	public void dataSourceTest() throws Exception {
		//创建jedis连接池
		JedisPool jedisPool=new JedisPool("192.168.28.130",6379);
		//从连接池中拿到jedis对象
		Jedis jedis=jedisPool.getResource();
		jedis.auth("123456");
		
		jedis.set("pass", "123456");
		String result = jedis.get("pass");
		
		System.out.println(result);
		
		//关闭jedis连接对象
		jedis.close();
	
		//当系统关闭时需要关闭jedis连接池
		jedisPool.close();
	}
	
	//jedis集群版测试
	@Test
	public void clusterTest() throws Exception {
		
		//定义集群中所有的节点
		Set<HostAndPort> nodes=new HashSet<HostAndPort>();
		nodes.add(new HostAndPort("192.168.28.129",7001));
		nodes.add(new HostAndPort("192.168.28.129",7002));
		nodes.add(new HostAndPort("192.168.28.129",7003));
		nodes.add(new HostAndPort("192.168.28.129",7004));
		nodes.add(new HostAndPort("192.168.28.129",7005));
		nodes.add(new HostAndPort("192.168.28.129",7006));
	
		//创建jedisCluster对象
	    //注意JedisCluster在项目中是单例存在的
		JedisCluster cluster=new JedisCluster(nodes);
		//cluster.set("name", "jack");
		String result = cluster.get("name");
		System.out.println(result);
		//在系统关闭时需要关闭jedis集群
		//cluster.close();		
	}
	
	@Test
	public void TestJedisWithSpring() throws Exception{
		//创建Spring容器对象                                                                                                                                
		ApplicationContext context=new ClassPathXmlApplicationContext("classpath:spring/applicationContext.xml");
        //从容器中获取JedisClient对象
		JedisClient client = context.getBean(JedisClient.class);
		String result = client.hget("redis_tbItemParamItem_key", "155620608526913");
		System.out.println(result);
	}
	
	@Test 
	public void testStringBlank() {
		System.out.println(StringUtils.isBlank(null));
		System.out.println(StringUtils.isBlank(""));
		System.out.println(StringUtils.isBlank("111"));
		
		System.out.println(Long.parseLong("999900000000"));
		
	}
	
	

}
