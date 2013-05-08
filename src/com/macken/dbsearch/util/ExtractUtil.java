package com.macken.dbsearch.util;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.htmlcleaner.CleanerProperties;
import org.htmlcleaner.HtmlCleaner;
import org.htmlcleaner.TagNode;

public class ExtractUtil {
	public static HtmlCleaner htmlCleaner;
	static {
		htmlCleaner = new HtmlCleaner();
		CleanerProperties props = htmlCleaner.getProperties();
		// props.setUseEmptyElementTags(true);
		// props.setAdvancedXmlEscape(true);
		props.setTransSpecialEntitiesToNCR(true);// 将special实体转换为数字形式 可以使用
		props.setBooleanAttributeValues("empty");
		props.setOmitComments(true);
	}
	public static String cleanXPath = "//script|//iframe";

	public static String getInnerHtml(TagNode node) {
		return htmlCleaner.getInnerHtml(node);
	}

	public static String getInnerHtml(String content, String xpath) {
		return getInnerHtml(content, xpath, 0);
	}

	/**
	 * 根据xpath提取页面节点 返回html内容
	 * 
	 * @param content
	 * @param xpath
	 * @param index
	 * @return
	 */
	public static String getInnerHtml(String content, String xpath, int index) {
		TagNode node = getInnerNode(content, xpath, index);
		if (node != null) {
			return htmlCleaner.getInnerHtml(node);
		}
		return null;
	}

	public static TagNode getInnerNode(String content) {
		return htmlCleaner.clean(content);
	}

	public static TagNode getInnerNode(TagNode node, String xpath) {
		return getInnerNode(node, xpath, 0);
	}

	public static TagNode getInnerNode(TagNode node, String xpath, int index) {
		try {
			Object[] sons = node.evaluateXPath(xpath);
			if (sons.length < index) {
				return null;
			}
			TagNode res = (TagNode) sons[index];
			res = cleanNode(res);
			return res;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	public static TagNode getInnerNode(String content, String xpath) {
		return getInnerNode(content, xpath, 0);
	}

	/**
	 * 根据xpath提取页面节点
	 * 
	 * @param content
	 * @param xpath
	 * @param index
	 * @return
	 */
	public static TagNode getInnerNode(String content, String xpath, int index) {
		TagNode node = htmlCleaner.clean(content);
		return getInnerNode(node, xpath, index);
	}

	/**
	 * 删除页面中的script、iframe节点
	 * 
	 * @param node
	 * @return
	 */
	private static TagNode cleanNode(TagNode node) {
		String[] xpaths = cleanXPath.split("\\|");
		for (String path : xpaths) {
			try {
				Object[] sons = node.evaluateXPath(path);

				for (Object son : sons) {
					TagNode n = (TagNode) son;
					n.removeFromTree();
				}

			} catch (Exception e) {
				e.printStackTrace();
				continue;
			}
		}
		return node;
	}

	/**
	 * 获取html源码中的title值
	 * @param content
	 * @return
	 */
	public static String getHtmlTitle(String content) {
		TagNode node = htmlCleaner.clean(content);
		TagNode[] titleNodes = node.getElementsByName("title", true);
		if (titleNodes.length > 0) {
			return titleNodes[0].getText().toString();
		}
		return null;
	}
	public static String cleanContent(String content) {

		// String httpregex="(http://"+domain+")";
		// String httpregex = "^http://([w-]+.)+[w-]+(/[w-\\./\\?%&=]*)?$";
		// String httpregex =
		// "((http://)?([a-z]+[.])|(www.))(\\w+[.])?([a-z]{2,4})?[[.]([a-z]{2,4})]+((/[\\S&&[^,;\"<>\u4E00-\u9FA5]]+)+)?([.][a-z]{2,4}+|/?)";
		// String
		// httpregex="(http://([a-z]+[.])|(www.))(\\w+[.])?([a-z]{2,4})?[[.][a-z]{2,4}]+((/[\\S&&[^,;\"<>\u4E00-\u9FA5]]+)+)?([.][a-z]{2,4}+|/?)";
		// String
		// httpregex="http://(\\w+[.]|www.)(\\w+[.])?([a-z]{2,4})?[[.][a-z]{2,4}]+((/[\\S&&[^,;\"<>\u4E00-\u9FA5]]+)+)?([.][a-z]{2,4}+|/?)";
		// String
		// httpregex="http://(\\w+[.]|www.)(\\w+[.])+?[[.][a-z]{2,4}]+((/[\\S&&[^,;\"<>\u4E00-\u9FA5]]+)+)?([.][a-z]{2,4}+|/?)";
		// String
		// httpregex="(http://(\\w+[.]|www.)(\\w+[.])+[[.][a-z]{2,4}]+((/[\\S&&[^,;\"<>\u4E00-\u9FA5]]+)+)?([.][a-z]{2,4}+|/?))";
		String httpregex = "(http://(\\w+[.]|www.)(\\w+[.])+[[.][a-z]{2,4}]+(:([0-9])+)?((/[\\S&&[^,;\"<>\u4E00-\u9FA5]]+)+)?([.][a-z]{2,4}+|/?))";
//		String httpregex = "(http://(\\w+[.]|www.)(\\w+[.])+[[.][a-z]{2,4}]+(:([0-9])+)?)";
		Pattern pattern = Pattern.compile(httpregex, Pattern.CASE_INSENSITIVE | Pattern.DOTALL);
		Matcher m = pattern.matcher(content);
		StringBuffer sb = new StringBuffer();
		while (m.find()) {
			String url = m.group(1).trim();
			 System.out.println(m.group());

			if (!isPicture(url)) {
				if (url.contains("xunlei")) {
					m.appendReplacement(sb, url);
				} else {
					m.appendReplacement(sb, "http://www.dy1000.com");
				}
			} else {
				m.appendReplacement(sb, url);
			}
		}
		m.appendTail(sb);
		return sb.toString();
	}
	public static boolean isPicture(String link) {
		int sindex = link.lastIndexOf(".");
		String suffixstr = link.substring(sindex + 1, link.length()).toLowerCase();
		if ("jpg".equals(suffixstr) || "jpeg".equals(suffixstr) || "bmp".equals(suffixstr)||"png".equals(suffixstr)||"gif".equals(suffixstr)) {
			return true;
		}
		return false;
	}
	public static void main(String[] args){
		String content="http://pic.66vod.net/tupian/2012/00253.jpg";
		System.out.println(cleanContent(content));
	}
}
