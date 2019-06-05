package com.ncs.rest.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.ncs.common.pojo.SmResult;
import com.ncs.common.utils.JsonUtils;
import com.ncs.mapper.TbContentMapper;
import com.ncs.pojo.TbContent;
import com.ncs.pojo.TbContentExample;
import com.ncs.pojo.TbContentExample.Criteria;
import com.ncs.rest.component.JedisClient;
import com.ncs.rest.service.ContentService;
@Service
public class ContentServiceImpl implements ContentService {
	
	@Value("${redis_content_key}")
	private String redis_content_key;
	@Autowired
	private TbContentMapper tbContentMapper;
	@Autowired
	private JedisClient jedisClient;

	@Override
	public List<TbContent> getContentList(Long categoryId) throws Exception {
		
		//去jedis缓存中查询数据
		String result = jedisClient.hget(redis_content_key, categoryId+"");
		if(result!=null) {
			return JsonUtils.jsonToList(result, TbContent.class);
		}
		
		TbContentExample example=new TbContentExample();
		Criteria criteria = example.createCriteria();
		criteria.andCategoryIdEqualTo(categoryId);
		
		List<TbContent> list = tbContentMapper.selectByExampleWithBLOBs(example);
		
		//返回结果前，将数据保存到缓存中
		jedisClient.hset(redis_content_key, categoryId+"", JsonUtils.objectToJson(list));
		
		return list;
	}

	@Override
	public SmResult syncContent(Long categoryId) {
		
		jedisClient.hdel(redis_content_key, categoryId+"");
		return SmResult.ok();
	}

}
