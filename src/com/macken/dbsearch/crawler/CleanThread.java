package com.macken.dbsearch.crawler;

import com.macken.dbsearch.util.CleanUtil;

public class CleanThread extends Thread{
	@Override
	public void run(){
		while(true){
			try {
				CleanUtil.updateScheduleTopic();
				this.sleep(60 * 60 * 1000);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}
}
