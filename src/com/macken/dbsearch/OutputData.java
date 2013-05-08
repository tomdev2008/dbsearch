package com.macken.dbsearch;

import java.util.Iterator;
import java.util.Set;

import com.macken.dbsearch.util.RedisUtil;

public class OutputData {
	public static void main(String[] args){
		Set<String> keys=RedisUtil.instance.keys(Config.LINKPRE+"*");
		Iterator iter=keys.iterator();
		String content="";
		while(iter.hasNext()){
			String key=(String)iter.next();
			String value=RedisUtil.instance.get(key);
			if(value.equals("0")){
				RedisUtil.instance.set(key, "1");
				content+=key.replace(Config.LINKPRE, "")+"\n";
			}
		}
		System.out.println(content);
	}
}
