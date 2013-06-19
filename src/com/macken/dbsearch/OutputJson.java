package com.macken.dbsearch;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.OutputStreamWriter;
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
	}
	public static void genJson()throws Exception{
		genAllJson();
		genManJson();
		genWomanJson();
	}
	public static void genAllJson() throws Exception {
		List<Topic> all = DBUtil.instance.getAllTopic(NUM);
		genJson("all.json", all);
	}
	public static void genManJson() throws Exception {
		List<Topic> man = DBUtil.instance.getManTopic(NUM);
		genJson("man.json", man);
	}

	public static void genWomanJson() throws Exception {
		List<Topic> woman = DBUtil.instance.getWomenTopic(NUM);
		genJson("woman.json", woman);
	}
	public static void genJson(String path, List<Topic> topic) throws Exception {
		//		List<Topic> topic = DBUtil.instance.getAllTopic(NUM);
		Gson gson = new Gson();
		File f = new File(oPath);
		if (f.exists()) {
			f.mkdirs();
		}
		//		FileWriter write = new FileWriter(new File(oPath));
		OutputStreamWriter out = new OutputStreamWriter(new FileOutputStream(oPath + path), "UTF-8");
		//		BufferedWriter out = new BufferedWriter(write);
		out.write(gson.toJson(topic));
		out.close();
		//		write.close();
	}

}
