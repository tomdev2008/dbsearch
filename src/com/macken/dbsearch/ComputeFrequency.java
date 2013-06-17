package com.macken.dbsearch;

import java.util.Date;
import java.util.List;

import org.htmlcleaner.TagNode;

import com.macken.dbsearch.entity.Group;
import com.macken.dbsearch.util.DBUtil;
import com.macken.dbsearch.util.DateUtil;
import com.macken.dbsearch.util.HttpUtil;


public class ComputeFrequency {
	public static void main(String[] args) throws Exception {
		List<Group> groups = DBUtil.instance.getAllGroup();
						Group g=groups.get(0);
						System.out.println(g.link);
						compute(g);
//		for (Group g : groups) {
//			compute(g);
//			Thread.sleep(2000);
//		}
		//		String text = "2013-01-01";
		//		System.out.println(convertTime(text).toString());
	}
	public static void compute(Group g) {
		String path = "//table[@class='olt']/tbody/tr/td[4]";
		String content = HttpUtil.getHtmlContent(g.link, "utf-8");
		//		System.out.println(content);
		TagNode root = HttpUtil.getCleanTagNode(content);
		try {
			Object[] nodes = root.evaluateXPath(path);
			Date max = new Date(0);
			Date min = new Date();

			for (int i = 0; i < nodes.length && i < nodes.length - 10; i++) {
				TagNode time = (TagNode) nodes[i];
				Date t = convertTime(time.getText().toString());
				if (t != null && t.after(max)) {
					max = t;
				}
			}
			int start = nodes.length > 10 ? 10 : 0;
			for (int i = nodes.length - start; i < nodes.length; i++) {
				TagNode time = (TagNode) nodes[i];
				Date t = convertTime(time.getText().toString());
				if (t.before(min)) {
					min = t;
				}
			}
			System.out.println(max.toLocaleString());
			System.out.println(min.toLocaleString());
			if (max.after(min)) {
				long interval = max.getTime() - min.getTime();
				int hour = (int) (interval / 1000 / 3600);
				g.crawlerInterval = interval;
				g.intervalHour = hour;
				//				g.lastCrawler=0;
				DBUtil.instance.updateGroup(g);
			}


		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	public static Date convertTime(String text) {
		text = text.trim();
		if (text.matches("[0-9]{2}-[0-9]{2}.*")) {
			text = "2013-" + text + ":00";
			return DateUtil.parse(text, DateUtil.DATE_FMT_2);
		} else if (text.matches("[0-9]{4}.*")) {
			return DateUtil.parse(text, DateUtil.DATE_FMT_3);
		} else {
			return null;
		}

	}

}
