package com.macken.dbsearch.util;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.htmlcleaner.HtmlCleaner;
import org.htmlcleaner.TagNode;

public class HttpUtil {
	public static HttpClient client;
	public static HtmlCleaner htmlCleaner;
	static {
		htmlCleaner = new HtmlCleaner();
	}
	static {
		client = new DefaultHttpClient();
		client.getParams()
				.setParameter(
						"User-Agent",
						"Mozilla/5.0 (Windows NT 6.1) AppleWebKit/535.7 (KHTML, like Gecko) Chrome/16.0.912.63 Safari/535.7 360EE");
	}
	private static int cache = 1024;

	public synchronized static String getHtmlContent(String url, String charset) {
		HttpGet httpget = new HttpGet(url);
		httpget.setHeader(
				"User-Agent",
				"Mozilla/5.0 (Windows NT 6.1) AppleWebKit/535.7 (KHTML, like Gecko) Chrome/16.0.912.63 Safari/535.7 360EE");
		try {
			HttpResponse response = client.execute(httpget);
			HttpEntity entity = response.getEntity();
			if(response.getStatusLine().getStatusCode()==HttpStatus.SC_FORBIDDEN){
				httpget.abort();
				return null;
			}
			if (entity != null) {
				StringBuffer sb = new StringBuffer();
				InputStream is = entity.getContent();

				InputStreamReader isr = new InputStreamReader(is, charset);
				BufferedReader br = new BufferedReader(isr);
				String tmp;
				while ((tmp = br.readLine()) != null) {
					sb.append(tmp);
					sb.append("\r\n");
				}
				isr.close();
				is.close();
				httpget.abort();
				return sb.toString();

				/*
				 * 这段代码 特殊中文字符会出现乱码 byte[] buffer = new byte[cache]; int ch = 0;
				 * while ((ch = is.read(buffer)) != -1) { sb.append(new
				 * String(buffer, 0, ch, charset)); } return sb.toString();
				 */
			}
			httpget.abort();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	public static TagNode getCleanTagNode(String content){
		return htmlCleaner.clean(content);
	}
	public static void main(String[] args){
		String url = "http://www.douban.com/group/neverinlove/";
		System.out.println(getHtmlContent(url, "utf-8"));
	}
}
