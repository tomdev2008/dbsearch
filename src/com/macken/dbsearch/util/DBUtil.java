package com.macken.dbsearch.util;

import java.util.List;

import org.springframework.jdbc.core.namedparam.BeanPropertySqlParameterSource;

import com.macken.dbsearch.entity.Group;
import com.macken.dbsearch.entity.Topic;

public class DBUtil {
	public static DBUtil instance = new DBUtil();

	private DBUtil() {

	}

	public List<Group> getAllGroup() {
		String sql = "select * from group_info";
		return DaoSupport.db.query(sql, Group.rowMapper);
	}

	public boolean exists(String id) {
		String sql = "select count(*) from topic_info where id=?";
		return DaoSupport.db.queryForInt(sql, id) > 0;
	}

	public List<Topic> getAllTopic() {
		String sql = "select * from topic_info order by create_time desc limit 0,100";
		return DaoSupport.db.query(sql, Topic.rowMapper);
	}

	public List<Topic> getManTopic(){
		String sql = "select * from topic_info where type=1 order by create_time desc limit 0,100";
		return DaoSupport.db.query(sql, Topic.rowMapper);
	}
	public List<Topic> getWomenTopic(){
		String sql = "select * from topic_info where type=2 order by create_time desc limit 0,100";
		return DaoSupport.db.query(sql, Topic.rowMapper);
	}
	public boolean add(Topic t) {
		String sql = "insert into topic_info(id,type,link,title,create_time,date_str) values(:id,:type,:link,:title,:createTime,:dateStr)";
		int res = DaoSupport.db.update(sql, new BeanPropertySqlParameterSource(
				t));
		return res > 0;
	}
}
