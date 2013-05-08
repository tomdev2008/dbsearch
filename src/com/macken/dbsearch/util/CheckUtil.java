package com.macken.dbsearch.util;

public class CheckUtil {
	public static boolean checkWords(String content) {
		String[] keywords = { "青稚","主题歌" };
		for (int i = 0; i < keywords.length; i++) {
			if (content.contains(keywords[i])) {
				return true;
			}
		}
		return false;
	}
}
