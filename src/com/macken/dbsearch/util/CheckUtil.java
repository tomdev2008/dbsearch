package com.macken.dbsearch.util;

import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.macken.dbsearch.entity.Topic;

public class CheckUtil {
	public static final int MAN = 1;
	public static final int WOMEN = 2;
	public static final int DEFAULT = -1;

	public static void main(String[] args) {
		testB();
	}

	public static void testB() {
		List<Topic> topic = DBUtil.instance.getAllTopic(500);
		for (Topic t : topic) {
			int type = checkTitle(t.title);
			if (type > 0) {
				System.out.println("yes " + t.title);
			} else {
				System.out.println("no  " + t.title);
			}
		}
	}

	public static void testA() {
		String[] test = { "蒸 友", "我要 老公", "老公我要", "找一个女朋友做对象" };
		// String[] test = { "征友" };
		for (int i = 0; i < test.length; i++) {
			String title = replaceBlank(test[i]);
			int type = checkTitle(title);
			switch (type) {
			case 0:
				System.out.println("no " + title);
				break;
			case MAN:
				System.out.println("man " + title);
				break;
			case WOMEN:
				System.out.println("women " + title);
				break;
			default:
				System.out.println("default " + title);
				break;
			}
		}
	}

	public static String replaceBlank(String title) {
		Pattern p = Pattern.compile("\\s*");
		Matcher m = p.matcher(title);
		String dest = m.replaceAll("");
		return dest;
	}

	public static int checkTitle(String title) {
		WordsCache cache = WordsCache.instance;
//		System.out.println("prefix");
		// 前置+名词
		for (String s : cache.prefixAction) {
			if (title.contains(s)) {
				int index = title.indexOf(s);
				String suffixTitle = title.substring(index + s.length());
				// System.out.println("suffixTitle:" + suffixTitle);
				int m = getPrefixIndex(cache.manNoun, suffixTitle);
				int w = getPrefixIndex(cache.womenNoun, suffixTitle);
				if (m != DEFAULT && w != DEFAULT) {
					if (m <= w) {
						return MAN;
					} else {
						return WOMEN;
					}
				} else if (m == DEFAULT && w != DEFAULT) {
					return WOMEN;
				} else if (m != DEFAULT && w == DEFAULT) {
					return MAN;
				}
			}
		}
//		System.out.println("suffix");
		// 名词+后置
		for (String s : cache.suffixAction) {
			if (title.contains(s)) {
				int index = title.indexOf(s);
				String prefixTitle = title.substring(0, index);
				int m = getSuffixIndex(cache.manNoun, prefixTitle);
				int w = getSuffixIndex(cache.womenNoun, prefixTitle);
				if (m != DEFAULT && w != DEFAULT) {
					if (m >= w) {
						return MAN;
					} else {
						return WOMEN;
					}
				} else if (m != DEFAULT && w == DEFAULT) {
					return MAN;
				} else if (m == DEFAULT && w != DEFAULT) {
					return WOMEN;
				}
			}
		}

		// 前置+中性
		for (String s : cache.prefixAction) {
			if (title.contains(s)) {
				int index = title.indexOf(s);
				String suffixTitle = title.substring(index);
				int m = getPrefixIndex(cache.middleNoun, suffixTitle);
				if (m != DEFAULT) {
					return MAN;
				}
			}
		}

//		System.out.println("single");

		// 男单独
		if (checkSingle(cache.manSingle, title)) {
			return MAN;
		}

		// 女单独
		if (checkSingle(cache.womenSingle, title)) {
			return WOMEN;
		}

		// 中单独
		if (checkSingle(cache.middleSingle, title)) {
			return MAN;
		}
		return 0;
	}

	public static int getPrefixIndex(List<String> list, String title) {
		int index = title.length();
		for (String s : list) {
			int tmp = title.indexOf(s);
			if (tmp != -1 && tmp < index) {
				index = tmp;
			}
		}
		return index == title.length() ? DEFAULT : index;
	}

	public static int getSuffixIndex(List<String> list, String title) {
		int index = DEFAULT;
		for (String s : list) {
			int tmp = title.indexOf(s);
			if (tmp != -1 && tmp > index) {
				index = tmp;
			}
		}
		return index;
	}

	public static boolean checkSingle(List<String> list, String title) {
		for (String s : list) {
			if (title.contains(s)) {
				return true;
			}
		}
		return false;
	}

	@Deprecated
	public static boolean checkWords(String content) {

		String[] except = DBUtil.instance.getValues("except");

		for (int i = 0; i < except.length; i++) {
			if (content.contains(except[i])) {
				return false;
			}
		}

		String[] keywords = DBUtil.instance.getValues("man");
		for (int i = 0; i < keywords.length; i++) {
			if (content.contains(keywords[i])) {
				return true;
			}
		}
		return false;
	}

	@Deprecated
	public static boolean checkWomenWords(String content) {

		String[] except = DBUtil.instance.getValues("except");

		for (int i = 0; i < except.length; i++) {
			if (content.contains(except[i])) {
				return false;
			}
		}

		String[] keywords = DBUtil.instance.getValues("women");
		for (int i = 0; i < keywords.length; i++) {
			if (content.contains(keywords[i])) {
				return true;
			}
		}
		return false;
	}

}
