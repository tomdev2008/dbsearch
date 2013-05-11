package com.macken.dbsearch.util;

import java.beans.PropertyVetoException;
import java.io.IOException;
import java.util.List;
import java.util.Properties;

import javax.sql.DataSource;

import org.springframework.dao.DataAccessException;
import org.springframework.dao.IncorrectResultSizeDataAccessException;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.simple.SimpleJdbcTemplate;

import com.mchange.v2.c3p0.ComboPooledDataSource;


public class DaoSupport {
	public static final SimpleJdbcTemplate db;
	public static final boolean isWindows;
	static {
		try {
			isWindows = System.getProperty("os.name") != null
					&& System.getProperty("os.name").toLowerCase().contains("windows");
			Properties prop = getConfig();
			DataSource dbSource = getDb(prop);
			db = new ModifiedSimpleJdbcTemplate(dbSource);
		} catch (Exception e) {
			throw new IllegalStateException("init dbpool failed.", e);
		}
	}
	static class ModifiedSimpleJdbcTemplate extends SimpleJdbcTemplate {
		public ModifiedSimpleJdbcTemplate(DataSource dataSource) {
			super(dataSource);
		}
		public <T> T queryForObject(String sql, RowMapper<T> rm, Object... args) throws DataAccessException {
			List<T> results = query(sql, rm, args);
			int size = results.size();
			if (size == 1)
				return results.get(0);
			if (size == 0)
				return null;
			throw new IncorrectResultSizeDataAccessException(1, size);
		}
	}
	private static Properties getConfig() throws IOException {
		Properties prop = new Properties();
		prop.load(DaoSupport.class.getClassLoader().getResourceAsStream("jdbc.properties"));
		return prop;
	}
	static final String driver = "com.mysql.jdbc.Driver";
	private static DataSource getDb(Properties prop) throws PropertyVetoException {
		ComboPooledDataSource master = new ComboPooledDataSource();
		master.setDriverClass(driver);
		master.setJdbcUrl(prop.getProperty(isWindows ? "jdbc.url_win" : "jdbc.url"));
		master.setUser(prop.getProperty("jdbc.user"));
		master.setPassword(prop.getProperty("jdbc.password"));
		master.setAutoCommitOnClose(true);
		// 
		if (isWindows) {
			master.setMinPoolSize(1);
			master.setMaxPoolSize(1);
			master.setInitialPoolSize(1);
		} else {
			master.setMinPoolSize(Integer.valueOf(prop.getProperty("jdbc.min.poolsize")));
			master.setMaxPoolSize(Integer.valueOf(prop.getProperty("jdbc.max.poolsize")));
			master.setInitialPoolSize(Integer.valueOf(prop.getProperty("jdbc.init.poolsize")));
		}
		master.setPreferredTestQuery("select 1");
		master.setMaxIdleTime(600);
		master.setIdleConnectionTestPeriod(60);
		master.setNumHelperThreads(10);
		return master;
	}
}
