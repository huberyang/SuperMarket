package com.ncs.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ncs.common.utils.pojo.EasyUITreePojo;
import com.ncs.common.utils.pojo.SmResult;
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

		List<EasyUITreePojo> resultList = new ArrayList<EasyUITreePojo>();

		for (TbContentCategory contentCat : contentCatList) {
			// 节点定义
			EasyUITreePojo node = new EasyUITreePojo();

			node.setId(contentCat.getId());
			node.setText(contentCat.getName());
			// 判断该节点是否存在子节点
			node.setState(contentCat.getIsParent() ? "closed" : "open");
			resultList.add(node);
		}

		return resultList;
	}

	@Override
	public SmResult updateContentCat(Long id, String name) throws Exception {

		TbContentCategory record = new TbContentCategory();
		record.setName(name);
		record.setUpdated(new Date());

		TbContentCategoryExample example = new TbContentCategoryExample();
		Criteria criteria = example.createCriteria();
		criteria.andIdEqualTo(id);

		TbContentCategoryMapper.updateByExampleSelective(record, example);

		return SmResult.ok();
	}

	@Override
	public SmResult deleteContentCat(Long id) throws Exception {

		TbContentCategoryExample example = new TbContentCategoryExample();
		Criteria criteria = example.createCriteria();
		criteria.andIdEqualTo(id);
		/**
		 * 找到父节点
		 */
		List<TbContentCategory> list = TbContentCategoryMapper.selectByExample(example);
		Long parentId = list.iterator().next().getParentId();

		TbContentCategoryMapper.deleteByExample(example);
		updateParentNodestatus(parentId);

		return SmResult.ok();

	}

	@Override
	public SmResult saveContentCat(Long parentId, String name) throws Exception {

		TbContentCategory record = new TbContentCategory();
		record.setParentId(parentId);
		record.setName(name);
		Date date = new Date();
		record.setCreated(date);
		record.setUpdated(date);
		record.setStatus(1);
		record.setSortOrder(1);
		record.setIsParent(false);

		TbContentCategoryMapper.insert(record);
		
		//新增需要返回新增项的id
		Long id = record.getId();
		updateParentNodestatus(parentId);

		return SmResult.ok(id);
	}

	/**
	 * 在当前节点完成添加，删除，修改后都需要更新当前节点是否依旧为叶子节点或父节点
	 * 
	 * @param parentId
	 */
	private void updateParentNodestatus(Long parentId) {

		TbContentCategoryExample example = new TbContentCategoryExample();
		Criteria criteria = example.createCriteria();
		criteria.andParentIdEqualTo(parentId);

		List<TbContentCategory> result = TbContentCategoryMapper.selectByExample(example);

		if (result == null || result.isEmpty()) {
			TbContentCategory record = new TbContentCategory();
			record.setUpdated(new Date());
			record.setIsParent(false);

			TbContentCategoryExample example2 = new TbContentCategoryExample();
			Criteria criteria2 = example2.createCriteria();
			criteria2.andIdEqualTo(parentId);

			TbContentCategoryMapper.updateByExampleSelective(record, example2);
		} else {
			TbContentCategory record = new TbContentCategory();
			record.setUpdated(new Date());
			record.setIsParent(true);

			TbContentCategoryExample example2 = new TbContentCategoryExample();
			Criteria criteria2 = example2.createCriteria();
			criteria2.andIdEqualTo(parentId);

			TbContentCategoryMapper.updateByExampleSelective(record, example2);
		}

	}

}
