package com.macken.dbsearch;

import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStreamWriter;
import java.util.ArrayList;
import java.util.List;

import com.google.gson.Gson;
import com.macken.dbsearch.entity.Topic;
import com.macken.dbsearch.util.DBUtil;

public class OutputJson {
	public static final boolean isWindows = System.getProperty("os.name") != null
			&& System.getProperty("os.name").toLowerCase().contains("windows");
	public static String oPath = isWindows ? "D:\\output\\json\\" : "/search/dbsearch/html/json/";

	public static int NUM = 30;

	public static void main(String[] args) throws Exception {
		genAllJson();
		genManJson();
		genWomanJson();
		genTopics();
//		Topic t=DBUtil.instance.getTopic("1903717109");
//		genTopicJson(t);
	}
	public static void genTopics() throws Exception{
		List<Topic> all=DBUtil.instance.getAllTopic(NUM);
		for(Topic t:all){
			if(t.getOriginContent()!=null && !t.getOriginContent().equals("")){
				genTopicJson(t);
			}	
		}
	}
	public static void genJson() throws Exception {
		genAllJson();
		genManJson();
		genWomanJson();
		genTopics();
	}
	public static void genAllJson() throws Exception {
		List<Topic> all = DBUtil.instance.getAllTopic(NUM);
		genListJson("all.json", all);
	}
	public static void genManJson() throws Exception {
		List<Topic> man = DBUtil.instance.getManTopic(NUM);
		genListJson("man.json", man);
	}

	public static void genWomanJson() throws Exception {
		List<Topic> woman = DBUtil.instance.getWomenTopic(NUM);
		genListJson("woman.json", woman);
	}
	public static void genListJson(String path,List<Topic> object) throws Exception {
		path=oPath+path;
		
		genJson(path,convertToJsonObject(object));
		
	}
	public static void genJson(String path, Object object) throws Exception {
		//		List<Topic> topic = DBUtil.instance.getAllTopic(NUM);
		Gson gson = new Gson();
		File f = new File(path);
		if (f.exists()) {
			f.mkdirs();
		}
		//		FileWriter write = new FileWriter(new File(oPath));
		OutputStreamWriter out = new OutputStreamWriter(new FileOutputStream(path), "UTF-8");
		//		BufferedWriter out = new BufferedWriter(write);
		out.write(gson.toJson(object));
		out.close();
		//		write.close();
	}
	public static void genTopicJson(Topic t) throws Exception{
		String path=oPath+"data/"+t.id+".json";
		genJson(path, t);
	}
	static class ListTopic {
		String id;
		String title;
		String link;
		String userName;
		long createTime;
	}
	static class TopicJson {
		String title;
		String link;
		String content;
	}
	public static List<ListTopic> convertToJsonObject(List<Topic> topic) {
		List<ListTopic> res = new ArrayList<ListTopic>();
		for (Topic t : topic) {
			ListTopic lt = new ListTopic();
			lt.id=t.id;
			lt.title = t.title;
			lt.link = t.link;
			lt.userName = t.userName;
			lt.createTime = t.createTime;
			res.add(lt);
		}
		return res;
	}
	public static TopicJson convertToJsonTopic(Topic t) {
		TopicJson tj = new TopicJson();
		tj.title = t.title;
		tj.link = t.link;
		tj.content = t.originContent;
		return tj;
	}

}
