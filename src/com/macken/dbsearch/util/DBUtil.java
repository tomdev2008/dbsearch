package com.macken.dbsearch.util;

import java.util.List;

import org.springframework.jdbc.core.SingleColumnRowMapper;
import org.springframework.jdbc.core.namedparam.BeanPropertySqlParameterSource;

import com.macken.dbsearch.entity.Group;
import com.macken.dbsearch.entity.Topic;
import com.macken.dbsearch.entity.User;

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
	public boolean existsTitle(String titleHash) {
		String sql = "select count(*) from topic_info where title_hash=?";
		return DaoSupport.db.queryForInt(sql, titleHash) > 0;
	}

	public List<Topic> getAllTopic() {
		String sql = "select * from topic_info order by create_time desc limit 0,100";
		return DaoSupport.db.query(sql, Topic.rowMapper);
	}

	public List<Topic> getAllTopic(int number) {
		String sql = "select * from topic_info order by create_time desc limit 0," + number;
		return DaoSupport.db.query(sql, Topic.rowMapper);
	}


	public List<Topic> getTotalTopic() {
		String sql = "select * from topic_info order by create_time";
		return DaoSupport.db.query(sql, Topic.rowMapper);
	}

	public List<Topic> getManTopic() {
		String sql = "select * from topic_info where type=1 order by create_time desc limit 0,100";
		return DaoSupport.db.query(sql, Topic.rowMapper);
	}
	public List<Topic> getManTopic(int number) {
		String sql = "select * from topic_info where type=1 order by create_time desc limit 0," + number;
		return DaoSupport.db.query(sql, Topic.rowMapper);
	}
	public List<Topic> getWomenTopic() {
		String sql = "select * from topic_info where type=2 order by create_time desc limit 0,100";
		return DaoSupport.db.query(sql, Topic.rowMapper);
	}
	public List<Topic> getWomenTopic(int number) {
		String sql = "select * from topic_info where type=2 order by create_time desc limit 0," + number;
		return DaoSupport.db.query(sql, Topic.rowMapper);
	}
	public boolean add(Topic t) {
		String sql = "insert into topic_info(id,type,link,title,title_hash,create_time,date_str,user_id,user_name) values(:id,:type,:link,:title,:titleHash,:createTime,:dateStr,:userId,:userName)";
		int res = DaoSupport.db.update(sql, new BeanPropertySqlParameterSource(t));
		return res > 0;
	}
	public boolean delete(Topic t) {
		String sql = "delete from topic_info where id=?";
		return DaoSupport.db.update(sql, t.id) > 0;
	}
	public boolean update(Topic t) {
		String sql = "update topic_info set title_hash=? where id=?";
		return DaoSupport.db.update(sql, t.titleHash, t.id) > 0;
	}
	public String[] getValues(String key) {
		String sql = "select value from dict where dkey=?";
		List<String> res = DaoSupport.db.query(sql, new SingleColumnRowMapper<String>(String.class), key);
		if (res.size() > 0) {
			return res.get(0).split(",");
		}
		return null;
	}
	public boolean addUser(User user) {
		if (isExist(user)) {
			return false;
		}
		String sql = "insert into user_info(user_id,user_name)values(:userId,:userName)";
		int res = DaoSupport.db.update(sql, new BeanPropertySqlParameterSource(user));
		return res > 0;
	}
	public boolean isExist(User user) {
		String sql = "select * from user_info where user_id=?";
		List<User> users = DaoSupport.db.query(sql, User.rowMapper, user.userId);
		if (users.size() > 0) {
			return true;
		}
		return false;
	}
	public boolean updateGroup(Group g) {
		String sql = "update group_info set last_crawler=:lastCrawler,crawler_interval=:crawlerInterval,interval_hour=:intervalHour where link=:link";
		int res = DaoSupport.db.update(sql, new BeanPropertySqlParameterSource(g));
		return res > 0;
	}
	public boolean updateTopicId(Topic t) {
		String sql = "update topic_info set id=? where link=?";
		int res = DaoSupport.db.update(sql, t.id, t.link);
		return res > 0;
	}
	public List<Topic> getNoIdTopics() {
		String sql = "select * from topic_info where id is null";
		List<Topic> list = DaoSupport.db.query(sql, Topic.rowMapper);
		return list;
	}
	public Topic getNoContentTopic() {
		String sql = "select * from topic_info where topic_content is null order by create_time desc limit 0,10";
		List<Topic> list = DaoSupport.db.query(sql, Topic.rowMapper);
		if (list.size() > 0) {
			return list.get(0);
		}
		return null;
	}
	public boolean updateTopicContent(Topic t) {
		String sql = "update topic_info set topic_content=?,origin_content=? where id=?";
		int rows = DaoSupport.db.update(sql, t.topicContent, t.originContent, t.id);
		return rows > 0;
	}
	public Topic getTopic(String id){
		String sql="select * from topic_info where id=?";
		List<Topic> t=DaoSupport.db.query(sql, Topic.rowMapper, id);
		if(t!=null && t.size()>0){
			return t.get(0);
		}
		return null;
	}

}
