package com.ncs.service.impl;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.ncs.common.pojo.EasyDataGridResult;
import com.ncs.common.pojo.SmResult;
import com.ncs.common.utils.IDUtils;
import com.ncs.mapper.TbItemDescMapper;
import com.ncs.mapper.TbItemMapper;
import com.ncs.mapper.TbItemParamItemMapper;
import com.ncs.pojo.TbItem;
import com.ncs.pojo.TbItemDesc;
import com.ncs.pojo.TbItemDescExample;
import com.ncs.pojo.TbItemExample;
import com.ncs.pojo.TbItemExample.Criteria;
import com.ncs.pojo.TbItemParamExample;
import com.ncs.pojo.TbItemParamItem;
import com.ncs.pojo.TbItemParamItemExample;
import com.ncs.service.ItemService;

@Service
public class ItemServiceImpl implements ItemService {

	@Autowired
	private TbItemMapper itemMapper;
	@Autowired
	private TbItemDescMapper itemDescMapper;
	@Autowired
	private TbItemParamItemMapper tbItemParamItemMapper;

	@Override
	public TbItem selectItemById(long itemId) throws Exception {

		TbItemExample example = new TbItemExample();
		Criteria criteria = example.createCriteria();
		criteria.andIdEqualTo(itemId);

		List<TbItem> list = itemMapper.selectByExample(example);

		if (list != null && list.size() > 0) {
			return list.get(0);
		}

		return null;

	}

	@Override
	public EasyDataGridResult selectItemByPage(int page, int rows) throws Exception {

		TbItemExample example = new TbItemExample();

		// 设置分页参数
		PageHelper.startPage(page, rows);

		List<TbItem> list = itemMapper.selectByExample(example);

		EasyDataGridResult result = new EasyDataGridResult();

		result.setRows(list);

		// 注意这里需要放入list
		PageInfo<TbItem> info = new PageInfo<>(list);

		result.setTotal(info.getTotal());

		return result;
	}

	@Override
	public SmResult createItem(TbItem item, String desc, String itemParams) throws Exception {

		// 在保存商品信息之前，先为商品补全id，status，updated，created
		long itemId = IDUtils.genItemId();
		item.setId(itemId);
		item.setCid(item.getCid());
		// 商品状态 1,正常 2删除，3下架
		item.setStatus((byte) 1);
		// 创建修改，更新时间
		Date date = new Date();
		item.setCreated(date);
		item.setUpdated(date);
		// 保存商品
		itemMapper.insert(item);

		TbItemParamItem itemParamItem = new TbItemParamItem();
		itemParamItem.setItemId(itemId);
		itemParamItem.setParamData(itemParams);
		itemParamItem.setCreated(date);
		itemParamItem.setUpdated(date);
		// 保存商品规格参数信息
		tbItemParamItemMapper.insert(itemParamItem);

		// 在保存商品描述之前，先为商品补全id，desc，updated，created
		TbItemDesc itemDesc = new TbItemDesc();
		itemDesc.setItemId(itemId);
		itemDesc.setItemDesc(desc);
		itemDesc.setCreated(date);
		itemDesc.setUpdated(date);
		// 保存商品描述
		itemDescMapper.insert(itemDesc);

		return SmResult.ok();
	}

	@Override
	public SmResult deleteItem(Long[] ids) throws Exception {
		// 商品状态 1,正常 2删除，3下架
		TbItem record = new TbItem();
		record.setStatus((byte) 3);

		for (int i = 0; i < ids.length; i++) {
			TbItemExample example = new TbItemExample();
			Criteria criteria = example.createCriteria();
			criteria.andIdEqualTo(ids[i]);
			itemMapper.updateByExampleSelective(record, example);
		}

		return SmResult.ok();
	}

	@Override
	public SmResult outstockItem(Long[] ids) throws Exception {
		// 商品状态 1,正常 2删除，3下架
		TbItem record = new TbItem();
		record.setStatus((byte) 2);

		for (int i = 0; i < ids.length; i++) {
			TbItemExample example = new TbItemExample();
			Criteria criteria = example.createCriteria();
			criteria.andIdEqualTo(ids[i]);
			itemMapper.updateByExampleSelective(record, example);
		}

		return SmResult.ok();
	}

	@Override
	public SmResult reshelfItem(Long[] ids) throws Exception {
		// 商品状态 1,正常 2删除，3下架
		TbItem record = new TbItem();
		record.setStatus((byte) 1);

		for (int i = 0; i < ids.length; i++) {
			TbItemExample example = new TbItemExample();
			Criteria criteria = example.createCriteria();
			criteria.andIdEqualTo(ids[i]);
			itemMapper.updateByExampleSelective(record, example);
		}

		return SmResult.ok();
	}

	@Override
	public SmResult queryItemDesc(Long itemId) {

		TbItemDescExample example = new TbItemDescExample();
		com.ncs.pojo.TbItemDescExample.Criteria criteria = example.createCriteria();
		criteria.andItemIdEqualTo(itemId);
		List<TbItemDesc> list = itemDescMapper.selectByExampleWithBLOBs(example);
		if (list != null && !list.isEmpty()) {
			return SmResult.ok(list.iterator().next());
		}
		return SmResult.ok();
	}

	@Override
	public SmResult queryItemParam(Long itemId) {

		TbItemParamItemExample example = new TbItemParamItemExample();
		com.ncs.pojo.TbItemParamItemExample.Criteria criteria = example.createCriteria();
		criteria.andItemIdEqualTo(itemId);
		List<TbItemParamItem> list = tbItemParamItemMapper.selectByExampleWithBLOBs(example);
		if (list != null && !list.isEmpty()) {
			return SmResult.ok(list.iterator().next());
		}
		return SmResult.ok();
	}

	@Override
	public SmResult updateItem(TbItem item, String desc, String itemParams) {

		Date date = new Date();
		// 先更新商品
		item.setUpdated(date);

		TbItemExample example = new TbItemExample();
		Criteria criteria = example.createCriteria();
		criteria.andIdEqualTo(item.getId());
		itemMapper.updateByExampleSelective(item, example);

		// 更新商品规格参数信息
		TbItemParamItem itemParamItem = new TbItemParamItem();
		itemParamItem.setItemId(item.getId());
		itemParamItem.setParamData(itemParams);
		itemParamItem.setUpdated(date);

		TbItemParamItemExample itemParamItemExample = new TbItemParamItemExample();
		com.ncs.pojo.TbItemParamItemExample.Criteria criteria2 = itemParamItemExample.createCriteria();
		criteria2.andItemIdEqualTo(item.getId());
		int result = tbItemParamItemMapper.updateByExampleSelective(itemParamItem, itemParamItemExample);
		// 商品规格参数信息为首次添加
		if (result == 0) {
			itemParamItem.setCreated(date);
			// 保存商品规格参数信息
			tbItemParamItemMapper.insert(itemParamItem);
		}

		// 更新商品描述
		TbItemDesc itemDesc = new TbItemDesc();
		itemDesc.setItemId(item.getId());
		itemDesc.setItemDesc(desc);
		itemDesc.setUpdated(date);

		TbItemDescExample itemDescExample = new TbItemDescExample();
		com.ncs.pojo.TbItemDescExample.Criteria criteria3 = itemDescExample.createCriteria();
		criteria3.andItemIdEqualTo(item.getId());
		int result2 = itemDescMapper.updateByExampleSelective(itemDesc, itemDescExample);
		//商品描述为首次添加
		if (result2 == 0) {
			itemDesc.setCreated(date);
			// 保存商品描述
			itemDescMapper.insert(itemDesc);
		}

		return SmResult.ok();
	}
}
