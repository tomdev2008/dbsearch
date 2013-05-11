package com.macken.dbsearch.crawler;

import java.util.Date;

import org.htmlcleaner.TagNode;

import com.google.gson.Gson;
import com.macken.dbsearch.Config;
import com.macken.dbsearch.entity.Topic;
import com.macken.dbsearch.util.CheckUtil;
import com.macken.dbsearch.util.DBUtil;
import com.macken.dbsearch.util.DateUtil;
import com.macken.dbsearch.util.ExtractUtil;
import com.macken.dbsearch.util.HashUtil;
import com.macken.dbsearch.util.HttpUtil;
import com.macken.dbsearch.util.RedisUtil;

public class CrawlerThread extends Thread {
	private String link;
	private Gson gson;

	public CrawlerThread(String link) {
		this.link = link;
		gson = new Gson();
	}

	@Override
	public void run() {
			System.out.println(link);
			Date date = new Date();
			String dateStr = DateUtil.format(date, DateUtil.DATE_FMT_3);

			String content = HttpUtil.getHtmlContent(link, "utf-8");
			TagNode root = HttpUtil.getCleanTagNode(content);
			try {
				Object[] nodes = root.evaluateXPath(Config.TABLEXPATH);
				for (int i = 0; i < nodes.length; i++) {
					TagNode linkNode = (TagNode) nodes[i];
					String href = linkNode.getAttributeByName("href");
					String title = linkNode.getAttributeByName("title");
					String id = HashUtil.getHash(href);
					int type = 0;

					if (CheckUtil.checkWords(title)) {
						type = 1;
					} else if (CheckUtil.checkWomenWords(title)) {
						type = 2;
					}

					if (type != 0 && !DBUtil.instance.exists(id)) {
						Topic t = new Topic();
						t.link = href;
						t.title = title;
						t.createTime = System.currentTimeMillis();
						t.id = id;
						t.type = type;
						t.dateStr = dateStr;
						DBUtil.instance.add(t);
					}
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
	}
}
