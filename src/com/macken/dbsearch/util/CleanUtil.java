package com.macken.dbsearch.util;

import java.util.List;

import org.htmlcleaner.TagNode;

import com.macken.dbsearch.entity.Topic;

public class CleanUtil {
	public static void main(String[] args){
		updateTotal();
	}
	
	public static void updateScheduleTopic(){
		List<Topic> topics=DBUtil.instance.getAllTopic();
		updateList(topics);
		
	}
	public static void updateTotal(){
		List<Topic> topics=DBUtil.instance.getTotalTopic();
		updateList(topics);
	}
	public static void updateList(List<Topic> topics){
		for(Topic t:topics){
			if(!checkAvaiable(t.link)){
				DBUtil.instance.delete(t);
			}
			try {
				Thread.sleep(50000);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}
	public static boolean checkAvaiable(String link){
		String content = HttpUtil.getHtmlContent(link, "utf-8");
		String title=ExtractUtil.getHtmlTitle(content);
		if(title.equals("\u9875\u9762\u4e0d\u5b58\u5728")){
			System.out.println("link:"+link);
			return false;
		}
		
		return true;
	}
	
}
