package com.macken.dbsearch.util;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class WordsCache {
	public static final WordsCache instance = new WordsCache();

	private WordsCache() {}

	public List<String> prefixAction;
	public List<String> suffixAction;
	public List<String> manNoun;
	public List<String> womenNoun;
	public List<String> middleNoun;
	public List<String> manSingle;
	public List<String> womenSingle;
	public List<String> middleSingle;
	
	{
		loadCache();
	}
	private List<String> getCache(String key){
		String[] values=DBUtil.instance.getValues(key);
		List<String> tmp=new ArrayList();
		for(int i=0;i<values.length;i++){
			tmp.add(values[i]);
		}
		CompareWords comparator=new CompareWords();
		Collections.sort(tmp,comparator);
		return tmp;
	}
	private void loadCache(){
		prefixAction=getCache("prefix_action");
		suffixAction=getCache("suffix_action");
		manNoun=getCache("man_noun");
		womenNoun=getCache("women_noun");
		middleNoun=getCache("middle_noun");
		manSingle=getCache("man_single");
		womenSingle=getCache("women_single");
		middleSingle=getCache("middle_single");
	}
	public static void main(String[] args){
		System.out.println(WordsCache.instance.prefixAction.size());
		System.out.println(WordsCache.instance.suffixAction.size());
		System.out.println(WordsCache.instance.manNoun.size());
		System.out.println(WordsCache.instance.womenNoun.size());
		System.out.println(WordsCache.instance.middleNoun.size());
		System.out.println(WordsCache.instance.manSingle.size());
		System.out.println(WordsCache.instance.womenSingle.size());
		System.out.println(WordsCache.instance.middleSingle.size());
	}

}
