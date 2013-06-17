package com.macken.dbsearch.entity;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

public class Group {
	public String link;
	public String name;
	public long lastCrawler;
	public long crawlerInterval;
	public int intervalHour;
	
	public String getLink() {
		return link;
	}

	public void setLink(String link) {
		this.link = link;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public long getLastCrawler() {
		return lastCrawler;
	}

	public void setLastCrawler(long lastCrawler) {
		this.lastCrawler = lastCrawler;
	}

	public long getCrawlerInterval() {
		return crawlerInterval;
	}

	public void setCrawlerInterval(long crawlerInterval) {
		this.crawlerInterval = crawlerInterval;
	}

	public int getIntervalHour() {
		return intervalHour;
	}

	public void setIntervalHour(int intervalHour) {
		this.intervalHour = intervalHour;
	}

	public static RowMapper<Group> rowMapper = new RowMapper<Group>() {

		public Group mapRow(ResultSet rs, int rowNum) throws SQLException {
			Group g = new Group();
			g.link = rs.getString("link");
			g.name = rs.getString("name");
			g.lastCrawler=rs.getLong("last_crawler");
			g.crawlerInterval=rs.getLong("crawler_interval");
			g.intervalHour=rs.getInt("interval_hour");
			return g;

		}
	};
}
