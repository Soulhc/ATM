//******************************************************************************
//
// ATM系统 -  Session.java 
// 参考了 http://www.cs.gordon.edu/courses/cs211/ATMExample/index.html
// 
//******************************************************************************

package atm;

import banking.Account;

/**
* 一个Session对象代表了现实世界里的一个人与ATM机器的对话过程.
* @author  何希
* @version 10/06/2018
*/
public class Session {

	public static final int NOTREADY = 1; // 未开始
	public static final int AUTHENTICATION = 2; // 待验证 
	public static final int CHOOSING = 3; // 交易选择
	public static final int INTRANSACTION = 4; // 交易中
	public static final int QUIT = 5; // 退出
	public static final int QUIT_WITH_CARD = 6; // 拔卡退出
	public static final int QUIT_WITHOUT_CARD = 7; // 留卡退出
	// 账号
	private String cardNo = null;
	// 密码
	private String pwd = null;
	// 会话的状态
	private int state = NOTREADY;
	// 此会话对应的账户
	private Account acct = null;
	// 当前的交易
	private Transaction trans = null;
	// 输入密码错误的次数
	private int time;

	public void setState(int state) {
		this.state = state;
	}
	
	public Account getAccount() {
		return this.acct;
	}
	
	public Transaction getTransaction() {
		return trans;
	}
	
	public void setTransaction(Transaction trans) {
		this.trans = trans;
	}
	
	public int getTime() {
		return time;
	}

	public void setTime(int time) {
		this.time = time;
	}

	/**
	 * 重新开始一个新会话
	 */
	public void startOver() {
		state = NOTREADY;
		cardNo = null;
		pwd = null;
		acct = null;
	}
	
	/**
	 * 确定银行卡是否有效。需要和后台数据库进行比较.
	 * 现在的实现假设银行卡是有效的。
	 * @param cardNo
	 * @return 银行卡是否正确。
	 */
	public boolean verify(String cardNo) {
		boolean isTrue = Account.cardIsTrue(cardNo);
		if(isTrue){
			this.cardNo = cardNo;
			this.state = AUTHENTICATION;
			return true;
		}
		return false;
	}
	
	/**
	 * 确定银行卡是否有效。需要和后台数据库进行比较.
	 * @param cardNo
	 * @return 银行卡是否正确。
	 */
	public boolean verifys(String cardNo) {
		boolean isTrue = Account.cardIsAdminTrue(cardNo);
		if(isTrue){
			this.cardNo = cardNo;
			this.state = AUTHENTICATION;
			return true;
		}
		return false;
	}
	
	/**
	 * 验证银行卡密码
	 */
	public void auth(String pwd) {
		if(state ==  AUTHENTICATION) {
			this.pwd = pwd;
			this.acct =  Account.getAccount( this.cardNo,  this.pwd);
			// 账户密码正确
			if(this.acct != null) {
				state = CHOOSING;
				ATM instance = ATM.getInstance();
				instance.getDisplay().setText("请选择业务 1:取款 2:存款 0:退出 ");
				instance.getDigitButton().stateChange(0, 1, "TransactionServlet");
			}
			// 账户密码不正确
			else {
				ATM instance = ATM.getInstance();
				instance.getDisplay().setText("密码错误,请重新输入密码");
				time++;
				if(time==3){
					instance.getDisplay().setText("对不起,您输错总的次数已达到三次。银行卡已被锁定。请你带上身份证到柜台进行开户办理");
					instance.getDigitButton().stateChange(2, 0, "");
					instance.getIdentity_button().setAppear(true);
				}
			}
		}
	}
	
	/**
	 * 选择交易
	 */
	public void selectTransaction(int options) {
		if(state == CHOOSING) {
			this.trans = Transaction.makeTransaction( this, this.acct, options);
			state = INTRANSACTION;
		}
	}
	
	/**
	 * 获取Session的状态字符串
	 */
	public String toString() {
		String output = "{";
		output += "\"state\":" + this.state;
		output += "}";
		return output;
	}
}
