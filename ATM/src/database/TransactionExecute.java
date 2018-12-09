package database;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * 和交易信息表交互的类
 * 
 * @author 梁木
 * @date 2018年12月3日
 */
public class TransactionExecute {

	//插入数据库交易信息
	public static void InsertTransaction(String cardNo, String operation, double account,double balance) {
		Connection conn = ThreadLocalUtil.getConnection();
		String sql = "insert into transaction(card_no,operation,account,date,balance) values (?,?,?,?,?)";
		PreparedStatement pstmt = null;
		try {
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, cardNo);
			pstmt.setString(2, operation);
			pstmt.setDouble(3, account);
			Timestamp t = new Timestamp(new Date().getTime());
			pstmt.setTimestamp(4,t);
			pstmt.setDouble(5, balance);
			pstmt.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	//查询数据库中最新的一条数据
		public static List<Object>resultList(){
			List<Object>transateList = new ArrayList<>();
			Connection conn = null;
			Statement stmt = null;
			ResultSet rs = null;
			String sql = "select * from transaction where date=(select max(date) from transaction)";
			conn=C3p0Utils.getConnection();
			try {
				stmt=conn.createStatement();
				rs = stmt.executeQuery(sql);
				while(rs.next()){
					transateList.add(rs.getString("card_no"));
					transateList.add(rs.getString("operation"));
					transateList.add(rs.getString("account"));
					String date = rs.getString("date");
					String newDate = date.substring(0, date.length()-2);
					transateList.add(newDate);
					transateList.add(rs.getString("balance"));
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
			C3p0Utils.close(conn, stmt,rs);
			return transateList;
			
		}
		
		//更新数据库
		public static int updateTransaction(String printText){
			Connection conn = null;
		    PreparedStatement pstmt = null;
		    conn = ThreadLocalUtil.getConnection();
		    int result = 0;
		    String sql = "update transaction set printext=? where date=(select * from (select max(date) from transaction) a)";
		    try {
		    	pstmt = conn.prepareStatement(sql);
				pstmt.setString(1,printText);
			    result = pstmt.executeUpdate();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		    return result;
		}
		
    //测试
	public static void main(String[] args) {
		List<Object>print = TransactionExecute.resultList();
		System.out.println(print.get(0));
		System.out.println(print.get(1));
		System.out.println(print.get(2));
		System.out.println(print.get(3));
		System.out.println(print.get(4));
	}
	

	//将时间类型转为字符串
	public static String CastDate(Date date) {
		SimpleDateFormat sdf = new SimpleDateFormat("yy-MM-dd HH:mm:ss");
		String strDate = sdf.format(date);
		return strDate;
	}

}
