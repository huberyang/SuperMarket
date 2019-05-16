package com.ncs.common.utils;
import java.io.IOException;
import java.net.URI;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.http.NameValuePair;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.utils.URIBuilder;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;

public class HttpClientUtils {

	// Get method
	public static String doGet(String url, Map<String, String> param) {
		// 第一步：创建一个HttpClient对象
		CloseableHttpClient httpClient = HttpClients.createDefault();
		String result = "";
		CloseableHttpResponse response = null;
		try {
			URIBuilder builder = new URIBuilder(url);
			if (param != null) {
				for (String key : param.keySet()) {
					builder.addParameter(key, param.get(key));
				}
			}
			URI uri = builder.build();
			// 第二步：创建一个HttpGet对象，需要制定一个请求的url
			HttpGet get = new HttpGet(uri);
			// 第三步：执行请求
			response = httpClient.execute(get);
			// 判断响应结果的状态、
			if (response.getStatusLine().getStatusCode() == 200) {
				// 第四步：接收返回结果。HttpEntity对象,获取响应的内容
				result = EntityUtils.toString(response.getEntity(), "UTF-8");
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			// 第五步：关闭response、HttpClient
			try {
				if (response != null) {
					response.close();
				}
				httpClient.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		return result;
	}

	public static String doGet(String url) {
		return doGet(url, null);
	}

	public static String doPost(String url) {
		return doPost(url, null);
	}

	// POST method
	public static String doPost(String url, Map<String, String> param) {
		// 第一步：创建一个httpClient对象
		CloseableHttpClient httpClient = HttpClients.createDefault();
		CloseableHttpResponse response = null;
		String result = "";

		try {
			// 第二步：创建一个HttpPost对象。需要指定一个url
			HttpPost post = new HttpPost(url);
			if (param != null) {

				// 第三步：创建一个list模拟表单，list中每个元素是一个NameValuePair对象
				List<NameValuePair> list = new ArrayList<>();
				for (String key : param.keySet()) {
					list.add(new BasicNameValuePair(key, param.get(key)));
				}
				// 第四步：需要把表单包装到Entity对象中。StringEntity
				UrlEncodedFormEntity entity = new UrlEncodedFormEntity(list, "UTF-8");
				post.setEntity(entity);
			}
			// 第五步：执行请求。
			response = httpClient.execute(post);
			// 第六步：接收返回结果
			result = EntityUtils.toString(response.getEntity(), "UTF-8");
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			// 第七步：关闭流。
			try {
				if (response != null) {
					response.close();
				}
				httpClient.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		return result;
	}

	// POST method
	public static String doPostJson(String url, String Json) {
		// 第一步：创建一个httpClient对象
		CloseableHttpClient httpClient = HttpClients.createDefault();
		CloseableHttpResponse response = null;
		String result = "";
		try {
			// 第二步：创建一个HttpPost对象。需要指定一个url
			HttpPost post = new HttpPost(url);
			// 第三步：创建请求内容
			StringEntity entity = new StringEntity(Json, ContentType.APPLICATION_JSON);
			// 第四步：需要把表单包装到Entity对象中。StringEntity
			post.setEntity(entity);
			// 第五步：执行请求。
			response = httpClient.execute(post);
			// 第六步：接收返回结果
			result = EntityUtils.toString(response.getEntity(), "UTF-8");
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			// 第七步：关闭流。
			try {
				if (response != null) {
					response.close();
				}
				httpClient.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		return result;
	}
}
