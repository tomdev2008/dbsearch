package com.macken.dbsearch.util;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.util.List;

import org.springframework.ui.ModelMap;

import com.macken.dbsearch.entity.Topic;

import freemarker.template.Configuration;
import freemarker.template.DefaultObjectWrapper;
import freemarker.template.Template;
import freemarker.template.TemplateException;

public class FMUtil {
	public static final boolean isWindows = System.getProperty("os.name") != null
			&& System.getProperty("os.name").toLowerCase().contains("windows");

	public static void main(String[] args) throws IOException {

		/* 而以下代码你通常会在一个应用生命周期中执行多次 */
		/* 获取或创建一个模版 */

		gen();

	}

	public static void gen() throws IOException {
		List<Topic> topics = DBUtil.instance.getAllTopic();
		ModelMap map = new ModelMap();
		map.put("topics", topics);
		genHtml(map, "index.tpl", "index.html");

		topics = DBUtil.instance.getManTopic();
		map.put("topics", topics);
		genHtml(map, "man.tpl", "man.html");

		topics = DBUtil.instance.getWomenTopic();
		map.put("topics", topics);
		genHtml(map, "women.tpl", "women.html");
	}

	public static void genHtml(ModelMap map, String tplPath, String outPath)
			throws IOException {
		Configuration cfg = new Configuration();
		String tPath = isWindows ? "D:\\tpl" : "/search/dbsearch/tpl";
		String oPath = isWindows ? "D:\\output" : "/search/dbsearch/html";
		cfg.setDirectoryForTemplateLoading(new File(tPath));
		cfg.setObjectWrapper(new DefaultObjectWrapper());
		try {
			Template temp = cfg.getTemplate(tplPath);
			String filePath = oPath + "/" + outPath;
			System.out.println("filePath:" + filePath);
			File file = new File(filePath);
			if (!file.getParentFile().exists()) {
				file.getParentFile().mkdirs();
			}

			Writer writer = new OutputStreamWriter(new FileOutputStream(
					filePath), "utf-8");
			temp.process(map, writer);
			writer.flush();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (TemplateException e) {
			e.printStackTrace();
		}
		return;
	}

	/** linux下执行，返回stdout,stderr */
	public static String exec(String command) {
		StringBuilder stdout = new StringBuilder("run: " + command + "\n");
		StringBuilder stderr = new StringBuilder();
		try {
			String[] cmdarray = isWindows ? new String[] { "cmd", "/c", command } //
					: new String[] { "/bin/sh", "-c", command };
			Runtime runtime = Runtime.getRuntime();
			Process process = runtime.exec(cmdarray);

			BufferedReader readerOut = new BufferedReader(
					new InputStreamReader(process.getInputStream()));
			BufferedReader readerErr = new BufferedReader(
					new InputStreamReader(process.getErrorStream()));
			String line;
			while ((line = readerOut.readLine()) != null) {
				stdout.append(line).append('\n');
			}
			readerOut.close();
			while ((line = readerErr.readLine()) != null) {
				stderr.append(line).append('\n');
			}
			readerErr.close();

			process.waitFor();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return stdout.toString().trim()
				+ (stderr.length() > 0 ? "\n" + stderr.toString().trim() : "");
	}
}
