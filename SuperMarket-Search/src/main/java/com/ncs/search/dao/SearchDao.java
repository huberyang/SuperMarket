package com.ncs.search.dao;

import org.apache.solr.client.solrj.SolrQuery;

import com.ncs.search.pojo.SearchResult;



public interface SearchDao {

	//接收SolrQuery对象作为查询条件
	public SearchResult queryData(SolrQuery solrQuery) throws Exception;

}
