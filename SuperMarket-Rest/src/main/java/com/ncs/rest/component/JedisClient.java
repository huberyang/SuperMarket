package com.ncs.rest.component;

import java.util.List;
import java.util.Set;


public interface JedisClient {

	public String set(String key, String value);

	public String get(String key);

	/**
	 * Redis hash 是一个string类型的field和value的映射表，hash特别适合用于存储对象。 Redis 中每个 hash 可以存储
	 * 232 - 1 键值对（40多亿）
	 * 
	 * @param key
	 * @param filed
	 * @param value
	 * @return
	 */
	public Long hset(String key, String filed, String value);

	public String hget(String key, String field);
	
	public Long hdel(String key, String... field);

	/**
	 * Redis Incr 命令将 key 中储存的数字值增一。 如果 key 不存在，那么 key 的值会先被初始化为 0 ，然后再执行 INCR 操作。
	 * 如果值包含错误的类型，或字符串类型的值不能表示为数字，那么返回一个错误。 本操作的值限制在 64 位(bit)有符号数字表示之内
	 * 
	 * @param key
	 * @return
	 */
	public Long incr(String key);
	
	public Long decr(String key);

	/**
	 * Redis列表是简单的字符串列表，按照插入顺序排序。你可以添加一个元素到列表的头部（左边）或者尾部（右边） 一个列表最多可以包含 232 - 1 个元素
	 * (4294967295, 每个列表超过40亿个元素)。
	 * 
	 * @param key
	 * @param values
	 * @return
	 */
	public Long lpush(String key, String... values);

	public List<String> lrange(String key, long begin, long end);

	public Long rpush(String key, String... values);

	/**
	 * Redis 的 Set 是 String 类型的无序集合。集合成员是唯一的，这就意味着集合中不能出现重复的数据。 Redis
	 * 中集合是通过哈希表实现的，所以添加，删除，查找的复杂度都是 O(1)。 集合中最大的成员数为 232 - 1 (4294967295,
	 * 每个集合可存储40多亿个成员)
	 * 
	 * @param key
	 * @param members
	 * @return
	 */
	public Long sadd(String key, String... members);

	// return all the smenmbers
	public Set<String> smembers(String key);

	/**
	 * Redis 有序集合和集合一样也是string类型元素的集合,且不允许重复的成员。
	 * 不同的是每个元素都会关联一个double类型的分数。redis正是通过分数来为集合中的成员进行从小到大的排序。
	 * 有序集合的成员是唯一的,但分数(score)却可以重复。 集合是通过哈希表实现的，所以添加，删除，查找的复杂度都是O(1)。 集合中最大的成员数为 232
	 * - 1 (4294967295, 每个集合可存储40多亿个成员)。
	 * 
	 * @param key
	 * @param score
	 * @param member
	 * @return
	 */
	public Long zadd(String key, double score, String member);

	public Set<String> zrange(String key, Long begin, Long end);

	// 为缓存数据设置过期时间
	public Long expire(String key,int second);

	// 返回过期剩余时间
	public Long ttl(String key);

}
