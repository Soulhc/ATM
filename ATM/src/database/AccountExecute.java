package database;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import banking.Account;

/**
 * 和账户表交互的类
 * 
 * @author 梁木  
 * @date 2018年12月1日
 */
public class AccountExecute {

	//查询数据库中所有数据
	public static List<Account>resultList(){
		List<Account>accountList = new ArrayList<>();
		Connection conn = null;
		Statement stmt = null;
		ResultSet rs = null;
		String sql = "select * from account";
		conn=JdbcUtil.getConnection();
		try {
			stmt=conn.createStatement();
			rs = stmt.executeQuery(sql);
			while(rs.next()){
				Account account = new Account();
				account.setCard_no(rs.getString("card_no"));
				account.setPwd(rs.getString("pwd"));
				account.setBalance(rs.getDouble("balance"));
				account.setIdentity(rs.getString("identity"));
				accountList.add(account);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		JdbcUtil.close(conn, stmt, rs);
		return accountList;
	}
	
	//更新数据库
	public static int updateAccount(double blance,String cardNo){
		Connection conn = null;
	    PreparedStatement pstmt = null;
	    conn = JdbcUtil.getConnection();
	    int result = 0;
	    String sql = "update account set balance=? where card_no=?";
	    try {
	    	pstmt = conn.prepareStatement(sql);
			pstmt.setDouble(1,blance);
		    pstmt.setString(2,cardNo);
		    result = pstmt.executeUpdate();
		    JdbcUtil.close(conn, pstmt);
		} catch (SQLException e) {
			e.printStackTrace();
		}
	    return result;
	}
}
