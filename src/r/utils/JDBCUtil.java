
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
			// ��ȡjdbc.properties���������ļ�
			InputStream inputStream = JDBCUtil.class.getClassLoader().getResourceAsStream("jdbc.properties");
			// �����м�������
			properties.load(inputStream);
			// �������ݿ����ӳ�
			dataSource = (DruidDataSource) DruidDataSourceFactory.createDataSource(properties);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * ��ȡ���ݿ����ӳ��е�����
	 * 
	 * @return �������null��˵����ȡ����ʧ��,��ֵ���ǻ�ȡ���ӳɹ�
	 */
	public static Connection getConnection() {
		Connection conn = conns.get();
		if (conn == null) {
			try {
				conn = dataSource.getConnection(); // �����ݿ����ӳ��л�ȡ����
				conns.set(conn); // ���浽ThreadLocal�����У��������jdbc����ʹ��
				conn.setAutoCommit(false); // �ֶ��������
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return conn;
	}

	/**
	 * �ύ���񣬲��ر��ͷ�����
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
		// һ��Ҫִ��remove����������ͻ����
		conns.remove();
	}

	/**
	 * �ع����񣬲��ر��ͷ�����
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
