package com.ncs.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.ncs.common.pojo.EasyDataGridResult;
import com.ncs.common.pojo.SmResult;
import com.ncs.mapper.TbItemCatMapper;
import com.ncs.mapper.TbItemParamItemMapper;
import com.ncs.mapper.TbItemParamMapper;
import com.ncs.pojo.TbItemCat;
import com.ncs.pojo.TbItemCatExample;
import com.ncs.pojo.TbItemCatExample.Criteria;
import com.ncs.pojo.TbItemParam;
import com.ncs.pojo.TbItemParamExample;
import com.ncs.pojo.TbItemParamItem;
import com.ncs.pojo.TbItemParamItemExample;
import com.ncs.service.ItemParamService;
import com.ncs.vo.TbItemParamCat;

@Service
public class ItemParamServiceImpl implements ItemParamService {

	@Autowired
	private TbItemParamMapper tbItemParamMapper;
	@Autowired
	private TbItemCatMapper tbItemCatMapper;
	@Autowired
	private TbItemParamItemMapper tbItemParamItemMapper;

	@Override
	public EasyDataGridResult selectItemParamByPage(int page, int rows) throws Exception {

		List<TbItemParamCat> list = new ArrayList<TbItemParamCat>();

		TbItemParamExample example = new TbItemParamExample();
		// 设置分页参数
		PageHelper.startPage(page, rows);

		List<TbItemParam> tbItemParamlist = tbItemParamMapper.selectByExample(example);

		/**
		 * 遍历集合 1.关联查询
		 */
		Iterator<TbItemParam> iter = tbItemParamlist.iterator();

		while (iter.hasNext()) {
			TbItemParam itemParam = iter.next();
			// 1.关联查询
			TbItemCatExample example2 = new TbItemCatExample();
			Criteria criteria = example2.createCriteria();
			criteria.andIdEqualTo(itemParam.getItemCatId());
			List<TbItemCat> TbItemCatList = tbItemCatMapper.selectByExample(example2);

			TbItemParamCat paramCat = new TbItemParamCat();

			paramCat.setId(itemParam.getId());
			paramCat.setItemCatId(itemParam.getItemCatId());
			paramCat.setCreated(itemParam.getCreated());
			paramCat.setUpdated(itemParam.getUpdated());
			paramCat.setParamData(itemParam.getParamData());
			paramCat.setItemCatName(TbItemCatList.iterator().next().getName());

			list.add(paramCat);

		}

		EasyDataGridResult result = new EasyDataGridResult();

		result.setRows(list);

		// 注意这里需要放入list
		PageInfo<TbItemParamCat> info = new PageInfo<>(list);

		result.setTotal(info.getTotal());

		return result;

	}

	@Override
	public SmResult deleteItemParamByIds(Long[] ids) throws Exception {

		for (int i = 0; i < ids.length; i++) {
			TbItemParamExample example = new TbItemParamExample();
			com.ncs.pojo.TbItemParamExample.Criteria criteria = example.createCriteria();
			criteria.andIdEqualTo(ids[i]);
			tbItemParamMapper.deleteByExample(example);
		}

		return SmResult.ok();
	}

	@Override
	public SmResult findItemParamByCatId(Long catId) throws Exception {

		TbItemParamExample example = new TbItemParamExample();
		com.ncs.pojo.TbItemParamExample.Criteria criteria = example.createCriteria();
		criteria.andItemCatIdEqualTo(catId);

		List<TbItemParam> list = tbItemParamMapper.selectByExample(example);
		if (list != null && !list.isEmpty()) {
			return SmResult.ok(list.iterator().next());
		}

		return SmResult.ok();
	}

	@Override
	public SmResult saveItemParamForCat(Long cId, String paramData) {

		TbItemParam record = new TbItemParam();
		record.setItemCatId(cId);
		record.setParamData(paramData);
		Date date = new Date();
		record.setCreated(date);
		record.setUpdated(date);
		tbItemParamMapper.insert(record);

		return SmResult.ok();
	}

	@Override
	public SmResult getItemParams(Long itemId) throws Exception {

		TbItemParamItemExample example = new TbItemParamItemExample();
		com.ncs.pojo.TbItemParamItemExample.Criteria criteria = example.createCriteria();
		criteria.andItemIdEqualTo(itemId);
		List<TbItemParamItem> list = tbItemParamItemMapper.selectByExampleWithBLOBs(example);

		if (list != null || !list.isEmpty()) {
			TbItemParamItem tbItemParamItem = list.iterator().next();
			return SmResult.ok(tbItemParamItem.getParamData());
		}

		return SmResult.ok();
	}

	@Override
	public SmResult updateItemParamForCat(Long cId, String paramData) {

		TbItemParamExample example = new TbItemParamExample();
		com.ncs.pojo.TbItemParamExample.Criteria criteria = example.createCriteria();
		criteria.andItemCatIdEqualTo(cId);

		List<TbItemParam> list = tbItemParamMapper.selectByExample(example);

		TbItemParam record = list.iterator().next();
		record.setParamData(paramData);
		Date date = new Date();
		record.setUpdated(date);
		tbItemParamMapper.updateByExampleWithBLOBs(record, example);

		return SmResult.ok();
	}

}
