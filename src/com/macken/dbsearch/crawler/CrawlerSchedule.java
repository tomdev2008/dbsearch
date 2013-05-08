package com.macken.dbsearch.crawler;

import java.util.Iterator;
import java.util.Set;

import com.macken.dbsearch.Config;
import com.macken.dbsearch.util.RedisUtil;

public class CrawlerSchedule {
	public static void main(String[] args) {
		Set<String> urls = RedisUtil.instance.getSet(Config.TOPICSET);
		Iterator iter=urls.iterator();
		while(iter.hasNext()){
			String link=(String)iter.next();
//			System.out.println(link);
			CrawlerThread ct=new CrawlerThread(link);
			ct.run();
		}
	}
}
