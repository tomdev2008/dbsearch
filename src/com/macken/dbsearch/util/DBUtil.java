package com.macken.dbsearch.util;

import java.util.List;

import org.springframework.jdbc.core.SingleColumnRowMapper;
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
	public boolean existsTitle(String titleHash){
		String sql="select count(*) from topic_info where title_hash=?";
		return DaoSupport.db.queryForInt(sql,titleHash)>0;
	}

	public List<Topic> getAllTopic() {
		String sql = "select * from topic_info order by create_time desc limit 0,100";
		return DaoSupport.db.query(sql, Topic.rowMapper);
	}
	
	public List<Topic> getTotalTopic(){
		String sql = "select * from topic_info order by create_time";
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
		String sql = "insert into topic_info(id,type,link,title,title_hash,create_time,date_str) values(:id,:type,:link,:title,:titleHash,:createTime,:dateStr)";
		int res = DaoSupport.db.update(sql, new BeanPropertySqlParameterSource(t));
		return res > 0;
	}
	public boolean delete(Topic t){
		String sql="delete from topic_info where id=?";
		return DaoSupport.db.update(sql, t.id)>0;
	}
	public boolean update(Topic t){
		String sql="update topic_info set title_hash=? where id=?";
		return DaoSupport.db.update(sql, t.titleHash,t.id)>0;
	}
	public String[] getValues(String key){
		String sql="select value from dict where dkey=?";
		List<String> res=DaoSupport.db.query(sql, new SingleColumnRowMapper<String>(String.class), key);
		if(res.size()>0){
			return res.get(0).split(",");
		}
		return null;
	}
}
