package com.macken.dbsearch.entity;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

public class Group {
	public String link;
	public String name;
	public static RowMapper<Group> rowMapper = new RowMapper<Group>() {

		public Group mapRow(ResultSet rs, int rowNum) throws SQLException {
			Group g = new Group();
			g.link = rs.getString("link");
			g.name = rs.getString("name");
			return g;

		}
	};
}
