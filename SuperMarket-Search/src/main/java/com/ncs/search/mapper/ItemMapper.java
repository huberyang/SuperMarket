package com.ncs.search.mapper;

import java.util.List;

import com.ncs.search.pojo.SearchItem;


public interface ItemMapper {

	List<SearchItem>  selectItemList();
	
}
