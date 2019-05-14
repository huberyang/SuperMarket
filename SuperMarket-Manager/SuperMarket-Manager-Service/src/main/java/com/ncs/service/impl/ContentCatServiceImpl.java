package com.ncs.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ncs.common.pojo.EasyUITreePojo;
import com.ncs.mapper.TbContentCategoryMapper;
import com.ncs.pojo.TbContentCategory;
import com.ncs.pojo.TbContentCategoryExample;
import com.ncs.pojo.TbContentCategoryExample.Criteria;
import com.ncs.service.ContentCatService;

@Service
public class ContentCatServiceImpl implements ContentCatService {

	@Autowired
	private TbContentCategoryMapper TbContentCategoryMapper;

	@Override
	public List<EasyUITreePojo> getContentCatList(Long parentId) throws Exception {

		TbContentCategoryExample example = new TbContentCategoryExample();
		Criteria criteria = example.createCriteria();
		criteria.andParentIdEqualTo(parentId);

		List<TbContentCategory> contentCatList = TbContentCategoryMapper.selectByExample(example);

		List<EasyUITreePojo> resultList = new ArrayList();

		for (TbContentCategory contentCat : contentCatList) {
			// 节点定义
			EasyUITreePojo node = new EasyUITreePojo();

			node.setId(contentCat.getId());
			node.setText(contentCat.getName());
			node.setStatus(contentCat.getIsParent() ? "closed" : "open");
			resultList.add(node);
		}

		return resultList;
	}

}
