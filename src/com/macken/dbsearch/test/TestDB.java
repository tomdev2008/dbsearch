package com.macken.dbsearch.test;

import java.util.List;

import com.macken.dbsearch.entity.Topic;
import com.macken.dbsearch.util.DBUtil;
import com.macken.dbsearch.util.HashUtil;

public class TestDB {
	public static void main(String[] args){
		List<Topic> topics=DBUtil.instance.getTotalTopic();
		for(Topic t:topics){
			t.titleHash=HashUtil.getHash(t.title);
			DBUtil.instance.update(t);
		}
	}
}
