package database;

import java.io.FileInputStream;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Properties;

/**
 * 连接数据库
 * 
 * @author 梁木  
 * @date 2018年12月1日
 */
public class JdbcUtil {
	private static String DRIVER_CLASS;
	private static String URL;
	private static String USERNAME;
	private static String PASSWORD;
	private static Properties p = new Properties();

	static {
		try {
			//web项目下jdbc.properties中的路径
	        String path = Thread.currentThread().getContextClassLoader().getResource  ("jdbc.properties").getPath();
			System.out.println(path);
	        FileInputStream fis = new FileInputStream(path);
			p.load(fis);
			DRIVER_CLASS=p.getProperty("driver");
			URL=p.getProperty("jdbcUrl");
			USERNAME=p.getProperty("username");
			PASSWORD=p.getProperty("password");
            Class.forName(DRIVER_CLASS);
            fis.close();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (ClassNotFoundException e){
			e.printStackTrace();
		}
	}
	
	//创建连接
	public static Connection getConnection(){
		Connection conn = null;
		try{
			conn = DriverManager.getConnection(URL,USERNAME,PASSWORD);
		}catch(Exception e){
			e.printStackTrace();
		}
		return conn;
	}
	
	//关闭连接
	public static void close(Connection conn,Statement stmt,ResultSet rs){
		if(rs!=null){
			try {
				rs.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		if(stmt!=null){
			try {
				stmt.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		if(conn!=null){
			try {
				conn.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}
	
	//关闭连接
		public static void close(Connection conn,Statement stmt){
			if(stmt!=null){
				try {
					stmt.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
			if(conn!=null){
				try {
					conn.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
		}
	
	public static void main(String[] args) {
		Connection conn=JdbcUtil.getConnection();//利用封装好的类名来调用连接方法便可
        System.out.println(conn+",成功连接数据库");
        JdbcUtil.close( conn,null,null);//同样利用类名调用关闭方法即可
	}

}
