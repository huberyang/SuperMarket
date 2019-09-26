package com.ncs.test;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.apache.http.HttpEntity;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;
import org.junit.Test;

public class HttpClientTest {
	
	// HttpClient Get 请求
	@Test
	public void testHttpClientGet() throws ClientProtocolException, IOException {
		
		// 第一步：创建一个HttpClient对象
		CloseableHttpClient httpClient=HttpClients.createDefault();
		// 第二步：创建一个HttpGet对象，需要制定一个请求的url
		HttpGet get = new HttpGet("http://192.168.28.128:8083/rest/content/89");
		// 第三步：执行请求
		CloseableHttpResponse response = httpClient.execute(get);
		// 四步：接收返回结果。HttpEntity对象
		HttpEntity entity = response.getEntity();
		// 第五步：取响应的内容
		String msg = EntityUtils.toString(entity);
		
		System.out.println(msg);
		
		// 第九步：关闭response、HttpClient
		response.close();
		httpClient.close();
	}
	
	
	//HttpClient POST 请求
	@Test
	public void testHttpClientPost() throws ClientProtocolException, IOException {
		// 第一步：创建一个httpClient对象
		CloseableHttpClient httpClient=HttpClients.createDefault();
		// 第二步：创建一个HttpPost对象。需要指定一个url
		HttpPost post = new HttpPost("http://192.168.28.132:8080/");
		// 第三步：创建一个list模拟表单，list中每个元素是一个NameValuePair对象
		List<NameValuePair> list=new ArrayList<>();
		list.add(new BasicNameValuePair("key1","value1"));
		list.add(new BasicNameValuePair("key2","value2"));
		// 第四步：需要把表单包装到Entity对象中。StringEntity
		StringEntity entity=new UrlEncodedFormEntity(list,"UTF-8");
		// 第五步：执行请求。
		CloseableHttpResponse response = httpClient.execute(post);
		// 第六步：接收返回结果
		HttpEntity respEntity = response.getEntity();
		String msg = EntityUtils.toString(respEntity);
		System.out.println(msg);
		// 第七步：关闭流。
		response.close();
		httpClient.close();
	}

}
