package com.macken.dbsearch.crawler;

import java.util.Date;

import org.htmlcleaner.TagNode;

import com.google.gson.Gson;
import com.macken.dbsearch.Config;
import com.macken.dbsearch.entity.Group;
import com.macken.dbsearch.entity.Topic;
import com.macken.dbsearch.entity.User;
import com.macken.dbsearch.util.CheckUtil;
import com.macken.dbsearch.util.DBUtil;
import com.macken.dbsearch.util.DateUtil;
import com.macken.dbsearch.util.FMUtil;
import com.macken.dbsearch.util.HashUtil;
import com.macken.dbsearch.util.HttpUtil;

public class CrawlerThread extends Thread {
	private String link;
	private Gson gson;
	private Group g;

	public CrawlerThread(Group g) {
		this.link = g.link;
		this.g = g;
		gson = new Gson();
	}

	@Override
	public void run() {

		Date date = new Date();
		String dateStr = DateUtil.format(date, DateUtil.DATE_FMT_3);
		if (!isGoOn(g)) {
			System.out.println("time is not reach");
			return;
		}

		String content = HttpUtil.getHtmlContent(link, "utf-8");
		if (content == null) {
			return;
		}

		System.out.println("group:" + link);
		// System.out.println(content);
		TagNode root = HttpUtil.getCleanTagNode(content);
		try {
			Object[] nodes = root.evaluateXPath(Config.TABLEXPATH);
			for (int i = 0; i < nodes.length; i++) {
				Topic t = new Topic();
				t.type = 0;
				TagNode n = (TagNode) nodes[i];
				// 获取标题 链接
				Object[] sns = n.evaluateXPath("//td[1]/a");
				if (sns.length > 0) {
					TagNode linkNode = (TagNode) sns[0];
					t.link = linkNode.getAttributeByName("href");
					t.title = linkNode.getAttributeByName("title");
					t.type = CheckUtil.checkTitle(t.title);

				} else {
					continue;
				}
				// 获取用户信息
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
				if (t.type != 0 && !DBUtil.instance.exists(id)
						&& !DBUtil.instance.existsTitle(titleHash)) {
					t.createTime = System.currentTimeMillis();
					t.dateStr = dateStr;
					t.titleHash = titleHash;
					t.id = id;
					DBUtil.instance.add(t);
					User user = new User();
					user.userId = t.userId;
					user.userName = t.userName;
					System.out.println(t.link + "\t" + t.userName + "\t"
							+ t.userId);
					DBUtil.instance.addUser(user);
					Thread.sleep(10 * 1000);
					CleanSchedule.cleanTopic(t);
					CleanSchedule.cleanUser(t);
					rsyncServer(t);
				}

			}
			g.lastCrawler = System.currentTimeMillis();
			DBUtil.instance.updateGroup(g);

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public String getUserId(String link) {
		String[] arr = link.trim().split("/");
		return arr[arr.length - 1];
	}

	public boolean isGoOn(Group g) {
		long low = 10 * 1000;
		long day = 12 * 3600 * 1000;
		long interval = g.crawlerInterval / 2 < low ? low : g.crawlerInterval;
		interval = interval > day ? day : interval;
		long time = g.lastCrawler + interval;

		if (time < System.currentTimeMillis()) {
			return true;
		}

		return false;
	}

	public static void rsyncServer(Topic t) {
		String cmd = "rsync -e \"/usr/bin/ssh\" -vaz /search/dbsearch/html/json/data/" + t.id
				+ ".json root@173.231.52.194:/search/html/json/data/";
		FMUtil.exec(cmd);
	}

	public static void main(String[] args) {
		String link = "http://www.douban.com/group/topic/40409903/";
		// CrawlerThread ct = new CrawlerThread("");
		// ct.getUserId(link);
	}
}
