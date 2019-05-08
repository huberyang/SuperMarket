package com.ncs.controller;


import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.ncs.pojo.TbItemCat;
import com.ncs.service.ItemCatService;

@Controller
@RequestMapping("/item/cat")
public class ItemCatController {

	@Autowired
	private ItemCatService service;

	@RequestMapping("/list")
	@ResponseBody
	public List categoryList(@RequestParam(value="id",defaultValue="0")Long parentId) throws Exception{
		
		List cateList=new ArrayList();
		
		List<TbItemCat> itemCatList = service.getItemCatList(parentId);
		
		for(TbItemCat itemCat:itemCatList) {
			//节点定义
			Map node =new HashMap();
			
			node.put("id", itemCat.getId());
			node.put("text", itemCat.getName());
			node.put("state", itemCat.getIsParent()?"closed":"open");
			
			cateList.add(node);
		}
		
		return cateList;
	}

}
