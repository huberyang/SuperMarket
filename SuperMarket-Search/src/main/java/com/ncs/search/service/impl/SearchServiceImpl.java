package com.ncs.search.service.impl;

import org.apache.solr.client.solrj.SolrQuery;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ncs.search.dao.SearchDao;
import com.ncs.search.pojo.SearchResult;
import com.ncs.search.service.SearchService;

@Service
public class SearchServiceImpl implements SearchService {

	@Autowired
	private SearchDao searchDao;

	@Override
	public SearchResult queryData(String queryParam, int page, int rows) throws Exception {

		// 创建SolrQuery查询对象
		SolrQuery solrQuery = new SolrQuery();
		// 设置查询条件
		solrQuery.setQuery(queryParam);
		solrQuery.setStart((page - 1) * rows);
		solrQuery.setRows(rows);
		// 设置默认的查询搜索域，即默认的查询
		solrQuery.set("df", "item_keywords");
		// 设置高亮显示
		solrQuery.setHighlight(true);
		// 添加高亮域和效果
		solrQuery.addHighlightField("item_title");
		solrQuery.setHighlightSimplePre("<font class=\"skcolor_ljg\">");
		solrQuery.setHighlightSimplePost("</font>");

		// 价格升序排列
		// solrQuery.setSort("item_price", ORDER.asc);
		// 开始查询
		SearchResult result = searchDao.queryData(solrQuery);
		// 遍历查询结果，计算总页数
		Long numFound = result.getNumFound();
		int pageCount = (int) (numFound / rows);
		if (numFound % rows > 0) {
			pageCount++;
		}
		result.setPageCount(pageCount);
		result.setStart(page);
		return result;

	}

}
