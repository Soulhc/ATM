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
	 */
	public int withdraw(double amount) {
		if(amount == 0)
			amount=amount+1;
		if(amount%100!=0 && amount<=20000 && 0<=amount){
			return 2;
		}else if(amount>20000 || amount==0){
			return 3;
		}
		else  if(this.balance >= amount) {
			this.balance = this.balance - amount;
		    AccountExecute.updateAccount(this.balance,this.card_no);
			TransactionExecute.InsertTransaction(this.card_no,Transaction.BALANCE_OUT,amount);
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
	public void deposit(double amount) {
		this.balance += amount;
		AccountExecute.updateAccount(this.balance,this.card_no);
		TransactionExecute.InsertTransaction(this.card_no,Transaction.BALANCE_IN,amount);
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
