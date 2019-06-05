package com.ncs.service.impl;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.ncs.common.pojo.EasyDataGridResult;
import com.ncs.common.pojo.SmResult;
import com.ncs.mapper.TbContentMapper;
import com.ncs.pojo.TbContent;
import com.ncs.pojo.TbContentExample;
import com.ncs.pojo.TbContentExample.Criteria;
import com.ncs.service.ContentService;
import com.ncs.vo.ContentCateVO;

@Service
public class ContentServiceImpl implements ContentService {
	
	@Autowired
	private TbContentMapper tbContentMapper;

	@Override
	public EasyDataGridResult selectContentByPage(Long categoryId, int pageNum, int pageSize) throws Exception {

		TbContentExample example = new TbContentExample();
		Criteria criteria = example.createCriteria();
		criteria.andCategoryIdEqualTo(categoryId);
		PageHelper.startPage(pageNum, pageSize);

		List<TbContent> list = tbContentMapper.selectByExampleWithBLOBs(example);

		EasyDataGridResult result = new EasyDataGridResult();

		result.setRows(list);

		PageInfo<TbContent> info = new PageInfo<>(list);
		result.setTotal(info.getTotal());

		return result;

	}

	/**
	 * title: 标题 subTitle: 标题 titleDesc: 标题 url: http://www.jd.com pic:
	 * http://localhost:9000/images/2015/07/27/1437979301511057.jpg pic2: content:
	 * 标题1
	 */
	@Override
	public SmResult updateContent(ContentCateVO contentCateVO) throws Exception {

		TbContentExample example = new TbContentExample();
		Criteria criteria = example.createCriteria();
		criteria.andIdEqualTo(contentCateVO.getId());
		criteria.andCategoryIdEqualTo(contentCateVO.getCategoryId());

		TbContent record = new TbContent();
		record.setTitle(contentCateVO.getTitle());
		record.setSubTitle(contentCateVO.getSubTitle());
		record.setTitleDesc(contentCateVO.getTitleDesc());
		record.setUrl(contentCateVO.getUrl());
		record.setPic(contentCateVO.getPic());
		record.setPic2(contentCateVO.getPic2());
		record.setContent(contentCateVO.getContent());
		record.setUpdated(new Date());

		tbContentMapper.updateByExampleSelective(record, example);
		
		return SmResult.ok();
	}

	@Override
	public SmResult saveContent(ContentCateVO contentCateVO) throws Exception {

		TbContent record = new TbContent();
		record.setCategoryId(contentCateVO.getCategoryId());
		record.setTitle(contentCateVO.getTitle());
		record.setSubTitle(contentCateVO.getSubTitle());
		record.setTitleDesc(contentCateVO.getTitleDesc());
		record.setUrl(contentCateVO.getUrl());
		record.setPic(contentCateVO.getPic());
		record.setPic2(contentCateVO.getPic2());
		record.setContent(contentCateVO.getContent());

		Date date = new Date();
		record.setCreated(date);
		record.setUpdated(date);

		tbContentMapper.insertSelective(record);
		return SmResult.ok();

	}

	@Override
	public SmResult deleteContent(Long[] ids) throws Exception {

		TbContentExample example = new TbContentExample();
		Criteria criteria = example.createCriteria();
		for (int i = 0; i < ids.length; i++) {
			criteria.andIdEqualTo(ids[i]);
			tbContentMapper.deleteByExample(example);
		}
		return SmResult.ok();
	}

	@Override
	public TbContent findContentById(Long id) {
		
		TbContentExample example = new TbContentExample();
		Criteria criteria = example.createCriteria();
		criteria.andIdEqualTo(id);
		List<TbContent> result = tbContentMapper.selectByExample(example);
		
		if(result!=null) {
			TbContent content= result.iterator().next();
			return content;
		}
		
		return null;
	}

}
