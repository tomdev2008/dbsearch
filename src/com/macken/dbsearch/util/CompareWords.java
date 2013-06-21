package com.macken.dbsearch.util;

import java.util.Comparator;

public class CompareWords implements Comparator<String>{

	public int compare(String o1, String o2) {
		if(o1.length()>o2.length())
			return 0;
		return 1;
	}

}
