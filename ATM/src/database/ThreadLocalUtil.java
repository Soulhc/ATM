package database;

import java.sql.Connection;
import java.sql.SQLException;

public class ThreadLocalUtil {
	
//线程池获取connection
private static ThreadLocal<Connection> tl=new ThreadLocal<Connection>();
	
	/**
	 * 
	 * @return 从线程池中获取一个连接
	 */
	public static Connection getConnection(){
		Connection conn=tl.get();//从线程中获取连接
		//如果conn为空，则从线程池中取出 一个，如果不为空则直接返回原来那个
		//这样做的目的是为了确保事务操作时线程不被其他线程干扰
		if(conn==null){
			conn=C3p0Utils.getConnection();
			tl.set(conn);//将连接放到tl中去
		}
			return conn;
	}
	
	/**
	 * 开启事务
	 */
	public static void startTranscation(){
	    try {
			getConnection().setAutoCommit(false);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	/**
	 * 提交事务
	 */
	public static void commit(){
		try {
			getConnection().commit();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	/**
	 * 回滚事务
	 */
	public static void rollback(){
		try {
			getConnection().rollback();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	/**
	 * 释放资源
	 */
	public static void release(){
		try {
			getConnection().close();
			tl.remove();//关闭连接之后要将该线程移除
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
