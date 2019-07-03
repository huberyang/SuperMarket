package com.ncs.search.service;

import com.ncs.search.pojo.SearchResult;

public interface SearchService {
	
	//根据查询参数，页数，每页行数，发布查询服务
	public SearchResult queryData(String queryParam, int page, int rows) throws Exception; 

}
