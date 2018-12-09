//******************************************************************************
//
// ATM系统 -  Account.java 
// 参考了 http://www.cs.gordon.edu/courses/cs211/ATMExample/index.html
// 
//******************************************************************************

package banking;

import java.util.List;

import atm.Transaction;
import database.AccountExecute;
import database.ThreadLocalUtil;
import database.TransactionExecute;

/**
 * 一个Account对象代表了现实世界里的一个银行账户.
 * 
 * @author 何希
 * @version 10/06/2018
 */
public class Account {
	// 账号
	String card_no = "";
	// 密码
	String pwd = "";
	// 余额
	double balance = 0;
	
	String identity = "";
	
	public Account() {
		super();
	}
 
	public Account(String card_no,double balance){
		this.card_no = card_no;
		this.balance = balance;
	}
	public Account(String card_no, String pwd,double balance) {
		this.card_no = card_no;
		this.pwd = pwd;
		this.balance = balance;
	}

	/**
	 * 通过用户名，密码获取一个银行账户对象 实际中应该查询数据库。在当前的实现中，我们只有一个账户。
	 * 
	 * @param card_no
	 * @param pwd
	 * @return
	 */
	public static Account getAccount(String card_no, String pwd) {
		List<Account>accountList = AccountExecute.resultList();
		for(Account account:accountList){
			if (card_no.equals(account.getCard_no()) && pwd.equals(account.getPwd())) {
				Account act = new Account(card_no, pwd,account.balance);
				return act;
			}
		}
		return null;
	}
	
	/**
	 * 通过用户名获取一个银行账户对象 实际中应该查询数据库。在当前的实现中，我们只有一个账户。
	 * 
	 * @param card_no
	 * @param pwd
	 * @return
	 */
	public static Account getAccountByCardNo(String card_no) {
		List<Account>accountList = AccountExecute.resultList();
		for(Account account:accountList){
			if (card_no.equals(account.getCard_no())) {
				Account act = new Account(card_no,account.balance);
				return act;
			}
		}
		return null;
	}
	

	/**
	 * ---------------1-------------------- 
	 * 修改1:进行卡号验证 通过卡号判断这个银行卡是否有效
	 */
	public static boolean cardIsTrue(String cardNo) {
		List<Account>accountList = AccountExecute.resultList();
		for(Account account:accountList){
			if(account.getIdentity().equals("user")){
				if (cardNo.equals(account.getCard_no())) {
					return true;
				}
			}
		}
		return false;
	}
	
	/**
	 * ---------------1-------------------- 
	 * 管理员验证。如果成功则初始化ATM机子
	 */
	public static boolean cardIsAdminTrue(String cardNo) {
		List<Account>accountList = AccountExecute.resultList();
		for(Account account:accountList){
			if(account.getIdentity().equals("admin")){
				if (cardNo.equals(account.getCard_no())) {
					return true;
				}
			}
		}
		return false;
	}

	/**
	 * 取款
	 * 
	 * @param amount
	 * @return 0:成功 1:不成功
	 * @throws Exception 
	 */
	public int withdraw(double amount) throws Exception {
		if(amount == 0)
			amount=amount+1;
		if(amount%100!=0 && amount<=20000 && 0<=amount){
				//throw new Exception("必须为100元钞票");
		return 2;
		}else if(amount>20000 || amount==0){
			return 3;
		}else if(this.balance < amount){
			return 5;
		}
		else  if(this.balance >= amount) {
			this.balance = this.balance - amount;
		    AccountExecute.updateAccount(this.balance,this.card_no);
			TransactionExecute.InsertTransaction(this.card_no,Transaction.BALANCE_OUT,amount,this.balance);
			return 0;
		}
		return 1;
	}
	
	/**
	 * 转账中的扣款
	 * 
	 * @param amount
	 * @return 0:成功 1:不成功
	 */
	public int withdrawInfo(double amount) {
         if(this.balance >= amount) {
			this.balance = this.balance - amount;
		    AccountExecute.updateAccount(this.balance,this.card_no);
			TransactionExecute.InsertTransaction(this.card_no,Transaction.BALANCE_OUT,amount,this.balance);
			return 0;
		}
		return 1;
	}
	

	/**
	 * 获取账户余额
	 * 
	 * @return
	 */
	public double getBalance() {
		return this.balance;
	}

	/**
	 * 存款
	 * 
	 * @param amount
	 */
	public void deposit(double amount,String tran) {
		this.balance += amount;
		AccountExecute.updateAccount(this.balance,this.card_no);
		TransactionExecute.InsertTransaction(this.card_no,tran,amount,this.balance);
	}
	
	/**
	 * 转账
	 * 
	 * @param amount
	 * @throws Exception 
	 */
	public int transfer(double amount,String otherCard,String tran,double ohterBalance){
		ohterBalance += amount;
		ThreadLocalUtil.startTranscation();
		try{
			ThreadLocalUtil.startTranscation();
			int isSuccess = this.withdrawInfo(amount);
			if(isSuccess==5){
				return 0;
			}else{
				AccountExecute.updateAccount(ohterBalance,otherCard);
				Thread.currentThread();
				Thread.sleep(1000);
				TransactionExecute.InsertTransaction(otherCard,tran,amount,ohterBalance);
				ThreadLocalUtil.commit();
				return 1;
			}
			
		}catch(Exception e){
			//如果出现异常，则回滚
			ThreadLocalUtil.rollback();
			try {
				throw new Exception("操作失败");
			} catch (Exception e1) {
				e1.printStackTrace();
			}
		}finally{
			//最后将连接关闭
			ThreadLocalUtil.release();
		}
		return 3;
	}

	public String getCard_no() {
		return card_no;
	}

	public void setCard_no(String card_no) {
		this.card_no = card_no;
	}

	public String getPwd() {
		return pwd;
	}

	public void setPwd(String pwd) {
		this.pwd = pwd;
	}

	public void setBalance(double balance) {
		this.balance = balance;
	}

	public String getIdentity() {
		return identity;
	}

	public void setIdentity(String identity) {
		this.identity = identity;
	}

	
}
