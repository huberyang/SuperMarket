package com.ncs.portal.service;

import com.ncs.portal.pojo.SearchResult;

public interface SearchService {
	
	public SearchResult search(String keyword, int page, int rows) throws Exception;

}
