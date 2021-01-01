package r.dao.impl;

import java.sql.Connection;
import java.util.List;

import r.utils.JDBCUtil;
import r.utils.SQLRunner;

public abstract class BaseDao {

	private SQLRunner sqlRunner = new SQLRunner();

	public <T> T queryForOne(Class<T> type, String sql, Object... args) {
		Connection conn = JDBCUtil.getConnection();
		return sqlRunner.queryForOne(conn, sql, type, args);
	}

	public <T> List<T> queryForList(Class<T> type, String sql, Object... args) {
		Connection conn = JDBCUtil.getConnection();
		return sqlRunner.queryForList(conn, sql, type, args);
	}

	public int update(String sql, Object... param) {
		Connection conn = JDBCUtil.getConnection();
		return sqlRunner.update(conn, sql, param);
	}

	public int update(String sql, List<String> paramList, Object... param) {
		Connection conn = JDBCUtil.getConnection();
		return sqlRunner.update(conn, sql, paramList, param);
	}
}
