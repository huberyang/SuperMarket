package com.ncs.portal.pojo;

import java.io.Serializable;
import java.util.List;

public class SearchResult implements Serializable {
	
	
	//查询结果集合
	private List<SearchItem> itemList;
	//查询结果总数
	private Long numFound;
	//当前页数
	private int start;
	//总页数
	private int pageCount;
	public List<SearchItem> getItemList() {
		return itemList;
	}
	public void setItemList(List<SearchItem> itemList) {
		this.itemList = itemList;
	}
	public Long getNumFound() {
		return numFound;
	}
	public void setNumFound(Long numFound) {
		this.numFound = numFound;
	}
	public int getStart() {
		return start;
	}
	public void setStart(int start) {
		this.start = start;
	}
	public int getPageCount() {
		return pageCount;
	}
	public void setPageCount(int pageCount) {
		this.pageCount = pageCount;
	}
	
	

}
