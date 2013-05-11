package com.macken.dbsearch.crawler;

import com.macken.dbsearch.util.FMUtil;

public class UploadThread extends Thread {
	@Override
	public void run() {
		try {
			FMUtil.gen();
			this.sleep(10 * 60 * 1000);
		} catch (Exception e) {
			e.printStackTrace();
		}

	}
}
