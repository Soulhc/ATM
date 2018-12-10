package database;

import java.beans.PropertyVetoException;
import java.io.FileInputStream;
import java.io.IOException;
import java.net.URLDecoder;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Properties;

import com.mchange.v2.c3p0.ComboPooledDataSource;

/**
 * 使用c3p0连接池连接
 * 
 * @author 梁木  
 * @date 2018年12月10日
 */
public class C3p0Utils {

	private static Properties p = new Properties();

	public static ComboPooledDataSource getDataSource() throws PropertyVetoException, IOException {
		// web项目下jdbc.properties中的路径
		String path = Thread.currentThread().getContextClassLoader().getResource("jdbc.properties").getPath();
		//如果路径出现20%则换掉它
		path = URLDecoder.decode(path, "utf-8");
		FileInputStream fis = new FileInputStream(path);
		p.load(fis);
		// 第一步：创建连接池核心工具类
		ComboPooledDataSource dataSource = new ComboPooledDataSource();
		// 第二步：连接池，url，驱动，账号，密码，初始连接数，最大连接数
		dataSource.setJdbcUrl(p.getProperty("jdbcUrl"));// 设置url
		dataSource.setDriverClass(p.getProperty("driver"));// 设置驱动
		dataSource.setUser(p.getProperty("username"));// mysql的账号
		dataSource.setPassword(p.getProperty("password"));// mysql的密码
		dataSource.setInitialPoolSize(6);// 初始连接数，即初始化6个连接
		dataSource.setMaxPoolSize(50);// 最大连接数，即最大的连接数是50
		dataSource.setMaxIdleTime(60);// 最大空闲时间
		fis.close();
		return dataSource;
	}

	// 获取连接的方法
	public static Connection getConnection() {
		try {
			return getDataSource().getConnection();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			throw new RuntimeException("服务器出错");
		}
	}

	// 释放资源
	public static void release(Connection conn, PreparedStatement prsmt, ResultSet rs) {
		if (conn != null) {
			try {
				conn.close();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			conn = null;
		}

		if (prsmt != null) {
			try {
				prsmt.close();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			prsmt = null;
		}

		if (rs != null) {
			try {
				rs.close();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			rs = null;
		}

	}
	
	//关闭连接
		public static void close(Connection conn,Statement stmt,ResultSet rs){
			if (conn != null) {
				try {
					conn.close();
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				conn = null;
			}

			if (stmt != null) {
				try {
					stmt.close();
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				stmt = null;
			}

			if (rs != null) {
				try {
					rs.close();
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				rs = null;
			}
		}
		
	//关闭连接
	public static void close(Connection conn,Statement stmt){
		if(conn!=null){
			try {
				conn.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
			conn = null;
		}
		if(stmt!=null){
			try {
				stmt.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
			stmt = null;
		}
	}

}
