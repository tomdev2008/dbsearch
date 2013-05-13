package com.macken.dbsearch.crawler;

import java.util.List;

import com.macken.dbsearch.entity.Group;
import com.macken.dbsearch.util.DBUtil;

public class CrawlerSchedule {
	public static void main(String[] args) {

		// Set<String> urls = RedisUtil.instance.getSet(Config.TOPICSET);
		// Iterator iter=urls.iterator();
		// while(iter.hasNext()){
		// String link=(String)iter.next();
		// // System.out.println(link);
		// CrawlerThread ct=new CrawlerThread(link);
		// ct.run();
		// }
		UploadThread ut=new UploadThread();
		ut.start();
		while(true){
			List<Group> groups = DBUtil.instance.getAllGroup();
			for (Group g : groups) {
				CrawlerThread ct = new CrawlerThread(g.link);
				ct.start();
			}
			try {
				Thread.sleep(120000);//2����
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
		
	}
}
