package com.macken.dbsearch.util;

public class HashUtil {
	public static String getHash(String content) {
		return String.valueOf(Math.abs(content.hashCode()));
	}
}