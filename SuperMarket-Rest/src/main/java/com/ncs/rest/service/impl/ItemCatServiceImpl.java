package com.ncs.rest.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ncs.mapper.TbItemCatMapper;
import com.ncs.pojo.TbItemCat;
import com.ncs.pojo.TbItemCatExample;
import com.ncs.pojo.TbItemCatExample.Criteria;
import com.ncs.rest.pojo.CatNode;
import com.ncs.rest.pojo.ItemCatResult;
import com.ncs.rest.service.ItemCatService;

/**
 * 
 * @Title: ItemCatServiceImpl.java
 * @Package com.ncs.rest.service.impl
 * @Description: TODO(商品分类服务)
 * @author: Hubery Yang
 * @date: May 9, 2019 5:14:30 PM
 * @version V1.0
 * @Copyright: 2019 Inc. All rights reserved.
 *
 */

@Service
public class ItemCatServiceImpl implements ItemCatService {

	@Autowired
	private TbItemCatMapper tbItemCatMapper;

	/**
	 * 查询商品分类信息
	 */
	@Override
	public ItemCatResult findItemCatResult() {

		ItemCatResult result = new ItemCatResult();
		List<CatNode> list = findItemCat(0l);
		result.setData(list);

		return result;
	}

	/**
	 * 查询商品分类节点集合
	 * 
	 * @return
	 */
	private List<CatNode> findItemCat(Long parentId) {
		// 根据父节点查询子节点
		TbItemCatExample example = new TbItemCatExample();
		Criteria criteria = example.createCriteria();
		criteria.andParentIdEqualTo(parentId);
		List<TbItemCat> list = tbItemCatMapper.selectByExample(example);

		List resultList = new ArrayList<>();
		int index = 0;
		// 按照指定格式封装数据
		for (TbItemCat itemCat : list) {
			if(index<14)
			// 判断子节点是不是还存在子节点
			if (itemCat.getIsParent()) {
				CatNode node = new CatNode();
				node.setUrl("/products" + itemCat.getId() + ".html");
				// 如果当前为一级节点
				if (itemCat.getParentId() == 0) {
					node.setName("<a href='/products/'" + itemCat.getId() + ".html>" + itemCat.getName() + "</a>");
					index++;
				} else {
					node.setName(itemCat.getName());
				}
				node.setItems(findItemCat(itemCat.getId()));
				// 将node添加到列表中
				resultList.add(node);
			} else {
				// 如果是叶子节点
				String item = "/products/" + itemCat.getId() + ".html|" + itemCat.getName();
				// 将node添加到列表中
				resultList.add(item);
			}
		}

		return resultList;
	}

}
