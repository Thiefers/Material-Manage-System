package r.utils;

import java.lang.reflect.Field;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class SQLRunner {
	/**
	 * @param conn
	 *            连接
	 * @param sql
	 *            执行的SQL
	 * @param clazz
	 *            实体类的class
	 * @param args
	 *            可变长参数
	 * @return 单个JavaBean
	 */
	public <T> T queryForOne(Connection conn, String sql, Class<T> clazz, Object... args) {
		PreparedStatement pStatement = null;
		ResultSet rSet = null;
		int argsLen = args.length;
		try {
			pStatement = conn.prepareStatement(sql);
			for (int i = 0; i < argsLen; i++) {
				if (args[i] instanceof String) {
					pStatement.setString(i + 1, (String) args[i]);
				} else if (args[i] instanceof Integer) {
					pStatement.setInt(i + 1, Integer.parseInt((String) args[i]));
				}
			}
			// 查询结果集
			rSet = pStatement.executeQuery();
			Field[] fields = clazz.getDeclaredFields();
			T instance = null;
			while (rSet.next()) {
				instance = clazz.newInstance();
				for (Field field : fields) {
					String fieldName = MapUtil.getColumnName(field);
					Object value = rSet.getObject(fieldName);
					field.setAccessible(true);
					field.set(instance, value);
				}
			}
			return instance;
		} catch (SQLException | SecurityException | InstantiationException | IllegalAccessException e) {
			e.printStackTrace();
		} finally {
			if (rSet != null) {
				try {
					rSet.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
			if (pStatement != null) {
				try {
					pStatement.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
		}
		return null;
	}

	/**
	 * @param conn
	 *            连接
	 * @param sql
	 *            执行的SQL
	 * @param clazz
	 *            实体类的class
	 * @param args
	 *            可变长参数
	 * @return JavaBean集合
	 */
	public <T> List<T> queryForList(Connection conn, String sql, Class<T> clazz, Object... args) {
		// List集合存放查询结果集对象
		List<T> list = new ArrayList<T>();
		PreparedStatement pStatement = null;
		ResultSet rSet = null;
		int argsLen = args.length;
		try {
			// 预编译SQL语句
			pStatement = conn.prepareStatement(sql);
			// TODO select * from tableName where xxx=? and xxx=? and ...
			for (int i = 0; i < argsLen; i++) {
				if (args[i] instanceof String) {
					pStatement.setString(i + 1, (String) args[i]);
				} else if (args[i] instanceof Integer) {
					pStatement.setInt(i + 1, Integer.parseInt((String) args[i]));
				}
			}
			// 查询结果集
			rSet = pStatement.executeQuery();
			Field[] fields = clazz.getDeclaredFields();
			while (rSet.next()) {
				T instance = clazz.newInstance();
				for (Field field : fields) {
					String fieldName = MapUtil.getColumnName(field);
					Object value = rSet.getObject(fieldName);
					field.setAccessible(true);
					field.set(instance, value);
				}
				list.add(instance);
			}
		} catch (SQLException | SecurityException | InstantiationException | IllegalAccessException e) {
			e.printStackTrace();
		} finally {
			if (rSet != null) {
				try {
					rSet.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
			if (pStatement != null) {
				try {
					pStatement.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
		}
		return list;
	}

	public int update(Connection conn, String sql, Object... param) {
		int count = 0;
		PreparedStatement pStatement = null;
		try {
			pStatement = conn.prepareStatement(sql);
			for (int i = 0; i < param.length; i++) {
				if (param[i] instanceof String) {
					pStatement.setString(i + 1, (String) param[i]);
				} else if (param[i] instanceof Integer) {
					pStatement.setInt(i + 1, (int) param[i]);
				}
			}
			count = pStatement.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			if (pStatement != null) {
				try {
					pStatement.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
		}
		return count;
	}

	public int update(Connection conn, String sql, List<String> paramList, Object... param) {
		int count = 0;
		PreparedStatement pStatement = null;
		try {
			pStatement = conn.prepareStatement(sql);
			if (param.length != 0) {
				int paramLen = param.length;
				for (int i = 0; i < paramLen; i++) {
					pStatement.setString(i + 1, param[i].toString());
				}
				for (int i = 0; i < paramList.size(); i++) {
					pStatement.setString(paramLen + i + 1, paramList.get(i));
				}
			} else {
				for (int i = 0; i < paramList.size(); i++) {
					pStatement.setString(i + 1, paramList.get(i));
				}
			}
			count = pStatement.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			if (pStatement != null) {
				try {
					pStatement.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
		}
		return count;
	}

}
