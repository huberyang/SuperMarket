package com.ncs.SolrJTest;

import java.util.HashMap;
import java.util.Map;

import org.apache.solr.client.solrj.SolrQuery;
import org.apache.solr.client.solrj.impl.HttpSolrClient;
import org.apache.solr.client.solrj.response.QueryResponse;
import org.apache.solr.common.SolrDocument;
import org.apache.solr.common.SolrDocumentList;
import org.apache.solr.common.SolrInputDocument;
import org.apache.solr.common.params.MapSolrParams;
import org.junit.Test;



public class SolrJTest {

	private String solrUrl = "http://192.168.28.130:8080/solr/new_core";

	/**
	 * 这个添加方法，会在添加之前遍历filed，如果已经存在就去update，如果不存在就新添加！
	 * 
	 * @throws Exception
	 */
	@Test
	public void DataImportTest() throws Exception {
		// create connection
		// solr4创建方式: SolrServer solrServer = new HttpSolrServer(solrUrl);
		// solr5创建方式,在url中指定core名称: new_core HttpSolrClient solrClient = new HttpSolrClient(solrUrl);
		// solr7创建方式,在url中指定core名称: new_core
		HttpSolrClient solrClient = new HttpSolrClient.Builder(solrUrl).build();
		// 创建一个文档对象
		SolrInputDocument document = new SolrInputDocument();
		// 添加域
		document.addField("id", "1");
		document.addField("item_title", "apple 8");
		document.addField("item_sell_point", "only 1500 dollar!");
		// 添加到索引库
		solrClient.add(document);
		// 提交
		solrClient.commit();
	}

	@Test
	public void FieldDel() throws Exception {

		// create connection
		// solr4创建方式: SolrServer solrServer = new HttpSolrServer(solrUrl);
		// solr5创建方式,在url中指定core名称: new_core HttpSolrClient solrClient = new
		// HttpSolrClient(solrUrl);
		// solr7创建方式,在url中指定core名称: new_core
		HttpSolrClient solrClient = new HttpSolrClient.Builder(solrUrl).withConnectionTimeout(10000)
				.withSocketTimeout(60000).build();

		// 删除指定索引的document对象
		solrClient.deleteById("1");

		// 创建一个文档对象
		// 删除document对象的对应的field
		/*
		 * SolrInputDocument document = new SolrInputDocument(); 
		 * document.setField("id", "1"); 
		 * document.removeField("*"); 
		 * solrClient.add(document);
		 */
		// 提交
		solrClient.commit();
	}

	@Test
	public void DataQuery() throws Exception {
		// create connection
		// solr4创建方式: SolrServer solrServer = new HttpSolrServer(solrUrl);
		// solr5创建方式,在url中指定core名称: new_core HttpSolrClient solrClient = new HttpSolrClient(solrUrl);
		// solr7创建方式,在url中指定core名称: new_core
		HttpSolrClient solrClient = new HttpSolrClient.Builder(solrUrl).withConnectionTimeout(10000)
				.withSocketTimeout(60000).build();
		// 创建solr查询对象
		SolrQuery query = new SolrQuery();
		// 设置查询条件,这个条件是查询所有
		query.setQuery("*:*");
		// 设置查询每页显示多少条数据
		query.setRows(10);
		// 发起搜索请求
		QueryResponse response = solrClient.query(query);
		// 遍历查询结果
		SolrDocumentList results = response.getResults();
		// 查询结果总数
		long numResult = results.getNumFound();
		System.out.println("总条数为" + numResult + "条");
		for (SolrDocument doc : results) {
			System.out.println(doc.toString());
		}
		solrClient.close();
	}

	@Test
	public void DateQueryWithCondition() throws Exception {
		// solr4创建方式: SolrServer solrServer = new HttpSolrServer(solrUrl);
		// solr5创建方式,在url中指定core名称: new_core HttpSolrClient solrClient = new HttpSolrClient(solrUrl);
		// solr7创建方式,在url中指定core名称: new_core
		HttpSolrClient solrClient = new HttpSolrClient.Builder(solrUrl).withConnectionTimeout(10000)
				.withSocketTimeout(60000).build();
		Map<String, String> map=new HashMap<>();
		map.put("q", "item_title:三星");
		MapSolrParams params=new MapSolrParams(map);
		QueryResponse response = solrClient.query(params);
		// 遍历查询结果
		SolrDocumentList results = response.getResults();
		//查询结果总数
		long numResult = results.getNumFound();
		System.out.println("总条数为："+numResult+"条");
		for(SolrDocument doc: results) {
			System.out.println(doc.toString());
		}
		//关闭连接
		solrClient.close();

	}

}
