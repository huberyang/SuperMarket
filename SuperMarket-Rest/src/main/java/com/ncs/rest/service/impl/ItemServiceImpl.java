package com.ncs.rest.service.impl;

import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.ncs.common.utils.JsonUtils;
import com.ncs.common.utils.pojo.SmResult;
import com.ncs.mapper.TbItemDescMapper;
import com.ncs.mapper.TbItemMapper;
import com.ncs.mapper.TbItemParamItemMapper;
import com.ncs.pojo.TbItem;
import com.ncs.pojo.TbItemDesc;
import com.ncs.pojo.TbItemDescExample;
import com.ncs.pojo.TbItemExample;
import com.ncs.pojo.TbItemExample.Criteria;
import com.ncs.pojo.TbItemParamItem;
import com.ncs.pojo.TbItemParamItemExample;
import com.ncs.rest.component.JedisClient;
import com.ncs.rest.service.ItemService;

@Service
public class ItemServiceImpl implements ItemService {

	@Value("${redis_tbItem_key}")
	private String redis_tbItem_key;
	@Value("${redis_tbItemDesc_key}")
	private String redis_tbItemDesc_key;
	@Value("${redis_tbItemParamItem_key}")
	private String redis_tbItemParamItem_key;
	@Value("${item_expire_second}")
	private Integer item_expire_second;

	@Autowired
	private JedisClient jedisClient;
	@Autowired
	private TbItemMapper itemMapper;
	@Autowired
	private TbItemDescMapper itemDescMapper;
	@Autowired
	private TbItemParamItemMapper itemParamItemMapper;

	@Override
	public TbItem findItem(Long itemId) throws Exception {

		// 从redis缓存中查询数据
		String result = jedisClient.hget(redis_tbItem_key, itemId + "");
		if (StringUtils.isNotBlank(result)) {
			return JsonUtils.jsonToPojo(result, TbItem.class);
		}

		TbItemExample itemExample = new TbItemExample();
		Criteria criteria = itemExample.createCriteria();
		criteria.andIdEqualTo(itemId);
		List<TbItem> resultList = itemMapper.selectByExample(itemExample);

		if (!resultList.isEmpty()) {
			// 在返回数据之前将数据保存到redis缓存中
			jedisClient.hset(redis_tbItem_key, itemId + "", JsonUtils.objectToJson(resultList.iterator().next()));
			// 注意对于这种数据量庞大，容易占缓存服务器内存的数据，我们需要设置商品的缓存时间，达到释放缓存的效果
			jedisClient.expire(redis_tbItem_key, item_expire_second);

			return resultList.iterator().next();
		}

		return null;
	}

	@Override
	public TbItemDesc findItemDesc(Long itemId) throws Exception {

		// 从redis缓存中查询数据
		String result = jedisClient.hget(redis_tbItemDesc_key, itemId + "");
		if (result != null) {
			return JsonUtils.jsonToPojo(result, TbItemDesc.class);
		}

		TbItemDescExample itemDescExample = new TbItemDescExample();
		com.ncs.pojo.TbItemDescExample.Criteria criteria = itemDescExample.createCriteria();
		criteria.andItemIdEqualTo(itemId);
		List<TbItemDesc> resultList = itemDescMapper.selectByExampleWithBLOBs(itemDescExample);

		if (!resultList.isEmpty()) {
			// 在返回数据之前将数据保存到redis缓存中
			jedisClient.hset(redis_tbItemDesc_key, itemId + "", JsonUtils.objectToJson(resultList.iterator().next()));
			// 注意对于这种数据量庞大，容易占缓存服务器内存的数据，我们需要设置商品的缓存时间，达到释放缓存的效果
			jedisClient.expire(redis_tbItemDesc_key, item_expire_second);
			return resultList.iterator().next();
		}

		return null;
	}

	@Override
	public TbItemParamItem findItemParamItem(Long itemId) throws Exception {

		// 从redis缓存中查询数据
		String result = jedisClient.hget(redis_tbItemParamItem_key, itemId + "");
		if (result != null) {
			return JsonUtils.jsonToPojo(result, TbItemParamItem.class);
		}

		TbItemParamItemExample itemParamItemExample = new TbItemParamItemExample();
		com.ncs.pojo.TbItemParamItemExample.Criteria criteria = itemParamItemExample.createCriteria();
		criteria.andItemIdEqualTo(itemId);
		List<TbItemParamItem> resultList = itemParamItemMapper.selectByExampleWithBLOBs(itemParamItemExample);

		if (!resultList.isEmpty()) {
			// 在返回数据之前将数据保存到redis缓存中
			jedisClient.hset(redis_tbItemParamItem_key, itemId + "",
					JsonUtils.objectToJson(resultList.iterator().next()));
			// 注意对于这种数据量庞大，容易占缓存服务器内存的数据，我们需要设置商品的缓存时间，达到释放缓存的效果
			jedisClient.expire(redis_tbItemParamItem_key, item_expire_second);
			return resultList.iterator().next();
		}

		return null;
	}

	@Override
	public SmResult syncItem(Long itemId) throws Exception {
		jedisClient.hdel(redis_tbItem_key, itemId + "");
		return SmResult.ok();
	}

	@Override
	public SmResult syncItemDesc(Long itemId) throws Exception {
		jedisClient.hdel(redis_tbItemDesc_key, itemId + "");
		return SmResult.ok();
	}

	@Override
	public SmResult syncItemParamIten(Long itemId) throws Exception {
		jedisClient.hdel(redis_tbItemParamItem_key, itemId + "");
		return SmResult.ok();
	}

}
