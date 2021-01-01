
package r.utils;

import java.io.InputStream;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.Properties;

import com.alibaba.druid.pool.DruidDataSource;
import com.alibaba.druid.pool.DruidDataSourceFactory;

public class JDBCUtil {

	private static DruidDataSource dataSource;
	private static ThreadLocal<Connection> conns = new ThreadLocal<Connection>();

	static {
		try {
			Properties properties = new Properties();
			// 读取jdbc.properties属性配置文件
			InputStream inputStream = JDBCUtil.class.getClassLoader().getResourceAsStream("jdbc.properties");
			// 从流中加载数据
			properties.load(inputStream);
			// 创建数据库连接池
			dataSource = (DruidDataSource) DruidDataSourceFactory.createDataSource(properties);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * 获取数据库连接池中的连接
	 * 
	 * @return 如果返回null，说明获取连接失败,有值就是获取连接成功
	 */
	public static Connection getConnection() {
		Connection conn = conns.get();
		if (conn == null) {
			try {
				conn = dataSource.getConnection(); // 从数据库连接池中获取连接
				conns.set(conn); // 保存到ThreadLocal对象中，供后面的jdbc操作使用
				conn.setAutoCommit(false); // 手动事务管理
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return conn;
	}

	/**
	 * 提交事务，并关闭释放连接
	 */
	public static void commitAndClose() {
		Connection connection = conns.get();
		if (connection != null) {
			try {
				connection.commit();
			} catch (SQLException e) {
				e.printStackTrace();
			} finally {
				try {
					connection.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
		}
		// 一定要执行remove操作，否则就会出错
		conns.remove();
	}

	/**
	 * 回滚事务，并关闭释放连接
	 */
	public static void rollbackAndClose() {
		Connection connection = conns.get();
		if (connection != null) {
			try {
				connection.rollback();
			} catch (SQLException e) {
				e.printStackTrace();
			} finally {
				try {
					connection.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
		}
		conns.remove();
	}

	// public static void close(Connection conn, Statement statement, ResultSet
	// rSet) {
	// if (rSet != null) {
	// try {
	// rSet.close();
	// } catch (SQLException e) {
	// e.printStackTrace();
	// }
	// }
	// if (statement != null) {
	// try {
	// statement.close();
	// } catch (SQLException e) {
	// e.printStackTrace();
	// }
	// }
	// if (conn != null) {
	// try {
	// conn.close();
	// } catch (SQLException e) {
	// e.printStackTrace();
	// }
	// }
	// }
	// public static void main(String[] args) {
	// Perfect
	// String sql = "select * from t_user where loginPwd=?";
	// Connection connection = JDBCUtil.getConnection();
	// SQLRunner sqlRunner = new SQLRunner();
	// String lockState = "1";
	// String expireTime="";
	// String loginPwd="7815696ecbf1c96e6894b779456d330e";
	// List<User> userList = sqlRunner.query(connection, sql, User.class, loginPwd);
	// for(User user : userList) {
	// System.out.println(user);
	// }
	// }

}
