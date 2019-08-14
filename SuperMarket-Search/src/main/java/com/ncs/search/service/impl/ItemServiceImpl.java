package com.ncs.search.service.impl;

import java.util.List;

import org.apache.solr.client.solrj.SolrQuery;
import org.apache.solr.client.solrj.impl.HttpSolrClient;
import org.apache.solr.client.solrj.response.QueryResponse;
import org.apache.solr.common.SolrDocument;
import org.apache.solr.common.SolrDocumentList;
import org.apache.solr.common.SolrInputDocument;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ncs.common.utils.pojo.SmResult;
import com.ncs.pojo.TbItem;
import com.ncs.search.mapper.ItemMapper;
import com.ncs.search.pojo.SearchItem;
import com.ncs.search.service.ItemService;

@Service
public class ItemServiceImpl implements ItemService {

	@Autowired
	private HttpSolrClient httpSolrClient;

	@Autowired
	private ItemMapper itemMapper;

	@Override
	public SmResult importAllData() throws Exception {

		// 获取所有的商品数据
		List<SearchItem> itemList = itemMapper.selectItemList();

		// 循环遍历结果集，将数据写入到solr索引库
		for (SearchItem item : itemList) {
			// 创建solr文档对象
			SolrInputDocument document = new SolrInputDocument();
			document.setField("id", item.getId());
			document.setField("item_title", item.getTitle());
			document.setField("item_sell_point", item.getSell_point());
			document.setField("item_price", item.getPrice());
			document.setField("item_image", item.getImage());
			document.setField("item_category_name", item.getCategory_name());
			document.setField("item_desc", item.getItem_desc());
			// 添加到索引库
			httpSolrClient.add(document);
		}

		// 提交
		httpSolrClient.commit();

		return SmResult.ok();

	}

	@Override
	public SmResult delItemIndex(Long itemId) throws Exception {

		// 创建solr查询对象
		SolrQuery query = new SolrQuery();
		// 设置查询条件,这个条件是查询所有
		query.setQuery("id:" + itemId);
		
		QueryResponse response = httpSolrClient.query(query);
		SolrDocumentList results = response.getResults();
		for (SolrDocument document : results) {
			httpSolrClient.deleteById((String) document.getFieldValue("id"));
		}

		httpSolrClient.commit();
		return SmResult.ok();
	}

	@Override
	public SmResult addItemIndex(TbItem item) throws Exception {

		// 创建一个文档对象
		SolrInputDocument document = new SolrInputDocument();
		// 添加域
		document.setField("id", item.getId());
		document.setField("item_title", item.getTitle());
		document.setField("item_sell_point", item.getSellPoint());
		document.setField("item_price", item.getPrice());
		document.setField("item_image", item.getImage());
		// 添加到索引库
		httpSolrClient.add(document);
		// 提交
		httpSolrClient.commit();
		return SmResult.ok();
	}

}
