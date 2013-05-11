package com.macken.dbsearch.util;

public class CheckUtil {
	public static boolean checkWords(String content) {
		String[] keywords =DBUtil.instance.getValues("man");
		for (int i = 0; i < keywords.length; i++) {
			if (content.contains(keywords[i])) {
				return true;
			}
		}
		return false;
	}
	
	public static boolean checkWomenWords(String content) {
		String[] keywords = DBUtil.instance.getValues("women");
		for (int i = 0; i < keywords.length; i++) {
			if (content.contains(keywords[i])) {
				return true;
			}
		}
		return false;
	}
	
}
