package com.macken.dbsearch.crawler;

import com.macken.dbsearch.util.CleanUtil;
import com.macken.dbsearch.util.FMUtil;

public class UploadThread extends Thread {
	@Override
	public void run() {
		while(true){
			try {
				FMUtil.gen();
				FMUtil.exec("scp -r /search/dbsearch/html/ root@173.231.52.194:/search/");
				this.sleep(5 * 60 * 1000);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}

	}
}
