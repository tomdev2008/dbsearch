package com.macken.dbsearch.crawler;

import java.util.List;

import com.macken.dbsearch.entity.Group;
import com.macken.dbsearch.util.DBUtil;

public class CrawlerSchedule {
	public static void main(String[] args) {

		List<Group> groups = DBUtil.instance.getAllGroup();
		for (Group g : groups) {
			CrawlerThread ct = new CrawlerThread(g);
			ct.start();
		}


	}
}
