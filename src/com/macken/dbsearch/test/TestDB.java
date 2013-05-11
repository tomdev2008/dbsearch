package com.macken.dbsearch.test;

import com.macken.dbsearch.util.DaoSupport;

public class TestDB {
	public static void main(String[] args){
		int a=DaoSupport.db.queryForInt("select count(*) from group_info");
		System.out.println(a);
	}
}
