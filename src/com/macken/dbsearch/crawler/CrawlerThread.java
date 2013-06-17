package com.macken.dbsearch.crawler;

import java.util.Date;

import org.htmlcleaner.TagNode;

import com.google.gson.Gson;
import com.macken.dbsearch.Config;
import com.macken.dbsearch.entity.Topic;
import com.macken.dbsearch.entity.User;
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
		System.out.println("group:" + link);
		Date date = new Date();
		String dateStr = DateUtil.format(date, DateUtil.DATE_FMT_3);

		String content = HttpUtil.getHtmlContent(link, "utf-8");
		//			System.out.println(content);
		TagNode root = HttpUtil.getCleanTagNode(content);
		try {
			Object[] nodes = root.evaluateXPath(Config.TABLEXPATH);
			System.out.println(nodes.length);
			for (int i = 0; i < nodes.length; i++) {
				Topic t = new Topic();
				t.type = 0;
				TagNode n = (TagNode) nodes[i];
				//获取标题 链接
				Object[] sns = n.evaluateXPath("//td[1]/a");
				if (sns.length > 0) {
					TagNode linkNode = (TagNode) sns[0];
					t.link = linkNode.getAttributeByName("href");
					t.title = linkNode.getAttributeByName("title");
					if (CheckUtil.checkWords(t.title)) {
						t.type = 1;
					} else if (CheckUtil.checkWomenWords(t.title)) {
						t.type = 2;
					}

				} else {
					continue;
				}
				//获取用户信息
				sns = n.evaluateXPath("//td[2]/a");
				if (sns.length > 0) {
					TagNode userNode = (TagNode) sns[0];
					t.userId = getUserId(userNode.getAttributeByName("href"));
					t.userName = userNode.getText().toString();
				} else {
					continue;
				}
				String titleHash = HashUtil.getHash(t.title);
				String id = HashUtil.getHash(t.link);
				if (t.type != 0 && !DBUtil.instance.exists(id) && !DBUtil.instance.existsTitle(titleHash)) {
					t.createTime = System.currentTimeMillis();
					t.dateStr = dateStr;
					t.titleHash = titleHash;
					DBUtil.instance.add(t);
					User user = new User();
					user.userId = t.userId;
					user.userName = t.userName;
					System.out.println(t.link+"\t"+t.userName+"\t"+t.userId);
					DBUtil.instance.addUser(user);
				}

			}
			//				for (int i = 0; i < nodes.length; i++) {
			//					TagNode linkNode = (TagNode) nodes[i];
			//					String href = linkNode.getAttributeByName("href");
			//					String title = linkNode.getAttributeByName("title");
			//					String titleHash=HashUtil.getHash(title);
			//					String id = HashUtil.getHash(href);
			//					int type = 0;
			//					
			//					if (CheckUtil.checkWords(title)) {
			//						type = 1;
			//					} else if (CheckUtil.checkWomenWords(title)) {
			//						type = 2;
			//					}
			//
			//					if (type != 0 && !DBUtil.instance.exists(id) && !DBUtil.instance.existsTitle(titleHash)) {
			//						Topic t = new Topic();
			//						t.link = href;
			//						t.title = title;
			//						t.createTime = System.currentTimeMillis();
			//						t.id = id;
			//						t.type = type;
			//						t.dateStr = dateStr;
			//						t.titleHash=titleHash;
			////						DBUtil.instance.add(t);
			//						System.out.println("add link:"+t.link);
			//					}
			//				}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	public String getUserId(String link) {
		String[] arr = link.trim().split("/");
		return arr[arr.length - 1];
	}
	public static void main(String[] args) {
		String link = "http://www.douban.com/group/topic/40409903/";
		CrawlerThread ct = new CrawlerThread("");
		ct.getUserId(link);
	}
}
