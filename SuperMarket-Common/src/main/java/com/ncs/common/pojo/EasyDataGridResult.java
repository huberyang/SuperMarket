package com.ncs.common.pojo;

import java.util.List;

/**
 * 
 * @Title:  EasyDataGridResult.java   
 * @Package com.ncs.common.pojo   
 * @Description:    TODO(easyUI DataGrid控件默认返回类型必须包含total和rows参数内容)   
 * @author: Stephen Yang   
 * @date:   Apr 22, 2019 10:53:33 PM   
 * @version V1.0 
 * @Copyright: 2019 Inc. All rights reserved. 
 *
 */
public class EasyDataGridResult {

	private long total;

	// 这里使用？表示任意类型，如果我们在此处使用T(T： 表示的是一种泛型的类型，也是一种独特的类型)
	private List<?> rows;

	public long getTotal() {
		return total;
	}

	public void setTotal(long total) {
		this.total = total;
	}

	public List<?> getRows() {
		return rows;
	}

	public void setRows(List<?> rows) {
		this.rows = rows;
	}

}
