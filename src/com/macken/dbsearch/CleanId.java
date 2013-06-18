package com.macken.dbsearch;

import java.util.List;

import com.macken.dbsearch.entity.Topic;
import com.macken.dbsearch.util.DBUtil;
import com.macken.dbsearch.util.HashUtil;

public class CleanId {
	public static void main(String[] args){
		List<Topic> topics=DBUtil.instance.getNoIdTopics();
		System.out.println(topics.size());
		for(Topic t:topics){
			String id=HashUtil.getHash(t.link);
			if(!DBUtil.instance.exists(id)){
				t.id=id;
				DBUtil.instance.updateTopicId(t);
			}
		}
	}
}
