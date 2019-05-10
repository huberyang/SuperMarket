package com.ncs.rest.pojo;

import java.io.Serializable;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;

public class CatNode implements Serializable {

	/**
	 * 节点的url
	 */
	@JsonProperty("u")
	private String url;
	/**
	 * 节点的name
	 */
	@JsonProperty("n")
	private String name;
	/**
	 * 节点项
	 */
	@JsonProperty("i")
	private List items;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public List getItems() {
		return items;
	}

	public void setItems(List items) {
		this.items = items;
	}

}
