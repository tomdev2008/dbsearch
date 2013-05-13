package com.macken.dbsearch.util;

import java.util.Date;
import java.util.List;

import freemarker.template.TemplateMethodModel;
import freemarker.template.TemplateModelException;

public class DateMethod implements TemplateMethodModel {

	public Object exec(List arg) throws TemplateModelException {
		Long date = (Long) arg.get(0);
		Date d = new Date(date);
		return DateUtil.format(d, "yyyy-MM-dd HH:mm");
	}

}
