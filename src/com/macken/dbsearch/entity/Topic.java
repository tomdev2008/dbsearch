package com.macken.dbsearch.entity;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Date;

import org.springframework.jdbc.core.RowMapper;

import com.macken.dbsearch.util.DateUtil;

public class Topic {
	public String id;
	public int type;
	public String link;
	public String title;
	public String titleHash;
	public String dateStr;
	public long createTime;
	public String createTimeStr;
	public long topicTime;
	public String userId;
	public String userName;
	public String topicContent;
	public String originContent;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public int getType() {
		return type;
	}

	public void setType(int type) {
		this.type = type;
	}

	public String getLink() {
		return link;
	}

	public void setLink(String link) {
		this.link = link;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}
	public String getTitleHash() {
		return this.titleHash;
	}
	public void setTitleHash(String titleHash) {
		this.titleHash = titleHash;
	}
	public String getDateStr() {
		return dateStr;
	}

	public void setDateStr(String dateStr) {
		this.dateStr = dateStr;
	}

	public long getCreateTime() {
		return createTime;
	}

	public void setCreateTime(long createTime) {
		this.createTime = createTime;
	}
	
	public String getCreateTimeStr(){
		return DateUtil.format(new Date(createTime), "yyyy-MM-dd HH:mm");
	}
	
	

	public long getTopicTime() {
		return topicTime;
	}

	public void setTopicTime(long topicTime) {
		this.topicTime = topicTime;
	}

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public void setCreateTimeStr(String createTimeStr) {
		this.createTimeStr = createTimeStr;
	}
	
	
	public String getTopicContent() {
		return topicContent;
	}

	public void setTopicContent(String topicContent) {
		this.topicContent = topicContent;
	}

	public String getOriginContent() {
		return originContent;
	}

	public void setOriginContent(String originContent) {
		this.originContent = originContent;
	}




	public static RowMapper<Topic> rowMapper = new RowMapper<Topic>() {

		public Topic mapRow(ResultSet rs, int rowNum) throws SQLException {
			Topic t = new Topic();
			t.id = rs.getString("id");
			t.type = rs.getInt("type");
			t.link = rs.getString("link");
			t.title = rs.getString("title");
			t.dateStr = rs.getString("date_str");
			t.createTime = rs.getLong("create_time");
			t.topicTime=rs.getLong("topic_time");
			t.userId=rs.getString("user_id");
			t.userName=rs.getString("user_name");
			t.topicContent=rs.getString("topic_content");
			t.originContent=rs.getString("origin_content");
			return t;
		}
	};
}
