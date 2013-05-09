package com.macken.dbsearch.crawler;

import org.htmlcleaner.TagNode;

import com.macken.dbsearch.Config;
import com.macken.dbsearch.util.CheckUtil;
import com.macken.dbsearch.util.ExtractUtil;
import com.macken.dbsearch.util.HttpUtil;
import com.macken.dbsearch.util.RedisUtil;

public class CrawlerThread extends Thread {
	private String link;

	public CrawlerThread(String link) {
		this.link = link;
	}

	@Override
	public void run() {
		while (true) {
			String content = HttpUtil.getHtmlContent(link, "utf-8");

			TagNode root = HttpUtil.getCleanTagNode(content);
			try {
				Object[] nodes = root.evaluateXPath(Config.TABLEXPATH);
				for (int i = 0; i < nodes.length; i++) {
					TagNode linkNode = (TagNode) nodes[i];
					String href = linkNode.getAttributeByName("href");
					String title = linkNode.getAttributeByName("title");
//					System.out.println(title);
					if (CheckUtil.checkWords(title)) {
						String key=Config.LINKPRE+href;
						String value=RedisUtil.instance.get(key);
						if(value==null){
							RedisUtil.instance.set(Config.LINKPRE + href, "0");
						}
					}
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
			
			try {
//				System.out.println("sleep");
				this.sleep(100000);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
		}
	}
}
