package com.ncs.rest.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ncs.mapper.TbContentMapper;
import com.ncs.pojo.TbContent;
import com.ncs.pojo.TbContentExample;
import com.ncs.pojo.TbContentExample.Criteria;
import com.ncs.rest.service.ContentService;
@Service
public class ContentServiceImpl implements ContentService {
	
	@Autowired
	private TbContentMapper tbContentMapper;

	@Override
	public List<TbContent> getContentList(Long categoryId) throws Exception {
		
		TbContentExample example=new TbContentExample();
		Criteria criteria = example.createCriteria();
		criteria.andCategoryIdEqualTo(categoryId);
		
		List<TbContent> list = tbContentMapper.selectByExampleWithBLOBs(example);
		
		return list;
	}

}
