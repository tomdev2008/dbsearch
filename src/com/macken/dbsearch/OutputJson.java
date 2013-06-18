package com.macken.dbsearch;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;

import com.google.gson.Gson;
import com.macken.dbsearch.entity.Topic;
import com.macken.dbsearch.util.DBUtil;

public class OutputJson {
	public static final boolean isWindows = System.getProperty("os.name") != null
			&& System.getProperty("os.name").toLowerCase().contains("windows");
	public static String oPath = isWindows ? "D:\\output\\json\\all.json" : "/search/dbsearch/html/json/all.json";

	public static int NUM = 30;

	public static void main(String[] args) throws Exception {
		genJson();
	}
	public static void genJson() throws Exception {
		List<Topic> topic = DBUtil.instance.getAllTopic(NUM);
		Gson gson = new Gson();
		File f = new File(oPath);
		if (f.exists()) {
			f.mkdirs();
		}
		FileWriter write = new FileWriter(new File(oPath));
		BufferedWriter out = new BufferedWriter(write);
		out.write(gson.toJson(topic));
		out.close();
		write.close();
	}

}
