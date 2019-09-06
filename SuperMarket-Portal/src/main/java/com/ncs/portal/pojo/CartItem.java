package com.ncs.portal.pojo;

/**
 * 
 * @Title: CateItem.java
 * @Package com.ncs.portal.pojo
 * @Description: TODO(购物车商品项Pojo)
 * @author: Hubery Yang
 * @date: Aug 28, 2019 10:29:47 PM
 * @version V1.0
 * @Copyright: 2019 Inc. All rights reserved.
 *
 */
public class CartItem {
	private Long itemId;
	private String title;
	private Long price;
	private Integer num;
	private String image;// 购物车的商品只需要一张图片的显示

	public Long getItemId() {
		return itemId;
	}

	public void setItemId(Long itemId) {
		this.itemId = itemId;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public Long getPrice() {
		return price;
	}

	public void setPrice(Long price) {
		this.price = price;
	}

	public Integer getNum() {
		return num;
	}

	public void setNum(Integer num) {
		this.num = num;
	}

	public String getImage() {
		return image;
	}

	public void setImage(String image) {
		this.image = image;
	}

}
