package com.macken.dbsearch.entity;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

public class User {
	public String userId;
	public String userName;
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

	public static final RowMapper<User> rowMapper = new RowMapper<User>() {

		public User mapRow(ResultSet rs, int row) throws SQLException {
			User user = new User();
			user.userId = rs.getString("user_id");
			user.userName = rs.getString("user_name");
			return user;
		}
	};
}
