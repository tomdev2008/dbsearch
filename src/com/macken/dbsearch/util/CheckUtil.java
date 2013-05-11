package com.macken.dbsearch.util;

public class CheckUtil {
	public static boolean checkWords(String content) {
		String[] keywords = { "男朋友", "蓝朋友", "男友", "蓝友", "征友", "蒸友","男闺蜜","蓝闺蜜" };
		for (int i = 0; i < keywords.length; i++) {
			if (content.contains(keywords[i])) {
				return true;
			}
		}
		return false;
	}
	
	public static boolean checkWomenWords(String content) {
		String[] keywords = { "女朋友", "女友", "女闺蜜"};
		for (int i = 0; i < keywords.length; i++) {
			if (content.contains(keywords[i])) {
				return true;
			}
		}
		return false;
	}
	
}
