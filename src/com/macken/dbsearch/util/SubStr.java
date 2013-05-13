package com.macken.dbsearch.util;

import java.util.List;

import freemarker.template.TemplateMethodModel;
import freemarker.template.TemplateModelException;

public class SubStr implements TemplateMethodModel {

	public Object exec(List arg) throws TemplateModelException {
		String s = (String) arg.get(0);
		if (s.length() > 60) {
			return s.substring(0, 60)+"......";
		}
		return s;
	}

}
