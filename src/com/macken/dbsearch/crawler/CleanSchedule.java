package com.macken.dbsearch.crawler;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.htmlcleaner.TagNode;

import com.macken.dbsearch.OutputJson;
import com.macken.dbsearch.entity.Topic;
import com.macken.dbsearch.util.DBUtil;
import com.macken.dbsearch.util.ExtractUtil;
import com.macken.dbsearch.util.HttpUtil;

public class CleanSchedule {
	public static String xpath = "//div[@class=\"topic-content\"]";
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		//		CleanUtil.updateScheduleTopic();
//		cleanTopic();
//		Topic t = DBUtil.instance.getTopic("2034772394");
//		cleanUser(t);
	}
	public static void cleanTopic(Topic topic) {
		if (topic != null) {
			String content = HttpUtil.getHtmlContent(topic.link, "utf-8");
			if (content != null) {
				String title = ExtractUtil.getHtmlTitle(content);
				if (title.equals("\u9875\u9762\u4e0d\u5b58\u5728")) {
					System.out.println("linknotexist:" + topic.link);
					DBUtil.instance.delete(topic);
				} else {
					TagNode root = HttpUtil.getCleanTagNode(content);
					try {
						Object[] nodes = root.evaluateXPath(xpath);
						if (nodes.length > 0) {
							TagNode node = (TagNode) nodes[0];
							String topicContent = node.getText().toString();
							String originContent = ExtractUtil.getInnerHtml(node);
							topic.topicContent = topicContent;
							topic.originContent = originContent;
							DBUtil.instance.updateTopicContent(topic);
							OutputJson.genTopicJson(topic);
						} else {
							System.out.println("node content");
						}
					} catch (Exception e) {
						e.printStackTrace();
					}
				}

			} else {
				System.out.println("node content");
			}
		}
	}
	public static void cleanUser(Topic topic) {
		String xpath="//*[@id=\"profile\"]/div/div[2]/div[1]/a";
		
		if(topic != null){
			String peopleLink = "http://www.douban.com/people/"+topic.getUserId();
			System.out.println(peopleLink);
			String content=HttpUtil.getHtmlContent(peopleLink,"utf-8");
			if(content!=null){
				TagNode root=HttpUtil.getCleanTagNode(content);
				try{
					Object[] nodes = root.evaluateXPath(xpath);
					if(nodes.length>0){
						TagNode node=(TagNode)nodes[0];
						String location=node.getText().toString();
						String locationUrl=node.getAttributeByName("href");
						DBUtil.instance.updateUserInfo(location, locationUrl, topic.getUserId());
					}
				}catch(Exception e){
					e.printStackTrace();
				}
			}
		}
		
	}
	public static void cleanTopic() {
		Topic topic = DBUtil.instance.getNoContentTopic();
		cleanTopic(topic);
	}
	public static void checkAvaiable(String content) {

	}
	public static String replaceBlank(String content) {
		Pattern p = Pattern.compile("\\s*\n");
		Matcher m = p.matcher(content);
		String dest = m.replaceAll("");
		return dest;
	}
	public static void testReplace() {
		Topic t = DBUtil.instance.getTopic("1903717109");
		System.out.println(replaceBlank(t.getTopicContent()));

	}
}
