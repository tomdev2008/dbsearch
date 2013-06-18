package com.macken.dbsearch.crawler;

import com.macken.dbsearch.OutputJson;
import com.macken.dbsearch.util.FMUtil;

public class UploadSchedule {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		try {
			FMUtil.gen();
			OutputJson.genJson();
			FMUtil.exec("scp -r /search/dbsearch/html/ root@173.231.52.194:/search/");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
