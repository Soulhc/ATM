//******************************************************************************
//
// ATM系统 -  Transaction.java 
// 参考了 http://www.cs.gordon.edu/courses/cs211/ATMExample/index.html
// 
//******************************************************************************

package atm;

import banking.Account;

/**
* 这个Transaction类是所有交易类的父类。当用户选择一个交易（取款，存款，查询）时，
* 会实例化相应的交易类。 
* @author  何希
* @version 10/06/2018
*/
public class Transaction {
	
	public static final int TRANS_UNSTART = 1; // 未开始
	public static final int TYPE_EXIT = 0; // 退出交易
	public static final int TYPE_WITHDRAW = 1; // 取款交易
	public static final int TYPE_DEPOSIT = 2; // 存款交易
	public static final int TYPE_TRANSFER = 3; //转账交易
	public static final int TYPE_QUERY = 4; //查询余额
	public static final int TYPE_RETURN = 5; //返回业务界面
    public static final String BALANCE_IN = "存款";
	public static final String BALANCE_OUT = "取款";
	public static final String BALANCE_Tran = "收到";
	
	// 会话的状态
	private int state = TRANS_UNSTART;
	//相关的账户
	private Account acct = null;
	// 对应的对话
	private Session session;
	// 金额的大小
	private double amount = 0.0;
	
	private String cardNo;
	
	public String getCardNo() {
		return cardNo;
	}

	/**
	 * 获取金额
	 * @return
	 */
	public double getAmount() {
		return this.amount;
	}
	
	/**
	 * 
	 * @param amount
	 */
	
	
	public void setAmount(double amount) {
		this.amount = amount;
	}
	

	public Session getSession() {
		return this.session;
	}
	
	public Account getAccount() {
		return this.acct;
	}
	
	public int getState() {
		return this.state;
	}
	
	public void setState(int state) {
		this.state = state;
	}
	
	public Transaction(Session session, Account acct) {
		this.session = session;
		this.acct = acct;
	}
	
	public Transaction(String cardNo){
		this.cardNo = cardNo;
	}
	/**
	 * 根据用户的选择，生成一个交易子类
	 * @param session
	 * @param acct
	 * @param options
	 * @return
	 */
	public static Transaction makeTransaction(Session session, Account acct, int options) {
		Transaction tmp = null;
		switch(options) {
			case TYPE_WITHDRAW: 
				tmp = new Withdraw(session,acct,options);
				break;
			case TYPE_EXIT:
				// 插卡孔状态要变 开关按钮状态要变 显示屏状态要变 数字键盘状态要变
				ATM machine = ATM.getInstance();
				machine.setState(ATM.IDLE);
				machine.getCardSlot().eject();
				machine.getSwitchButton().stateChange(ATM.IDLE);
				machine.getDisplay().setText("请插入你的银行卡");
				machine.getSession().setTime(0);
				machine.getDigitButton().stateChange(2, 0, "");
				machine.getPrint().setText("");
				machine.getIdentity_button().setAppear(false);
				break;
			case TYPE_DEPOSIT:
				tmp = new Withdraw(session,acct,options);
				break;
			case TYPE_TRANSFER:
				tmp = new Withdraw(session,acct,options);
				break;
			case TYPE_QUERY:
				tmp = new Withdraw(session,acct,options);
				break;
			case TYPE_RETURN:
				tmp = new Withdraw(session,acct,options);
				break;
		}
		return tmp;
	}
	
	/**
	 * 选择交易
	 */
	public void TransferTransaction(int cardNo) {
		ATM machine = ATM.getInstance();
		String card_no = String.valueOf(cardNo);
		if(!card_no.equals(this.acct.getCard_no())){
			boolean isYouXiao = this.getSession().verify(card_no);
			if(isYouXiao){
				this.cardNo = card_no;
				machine.getDisplay().setText("请输入金额");
				machine.getPrint().setText("");
				machine.getDigitButton().stateChange(1, 0, "WithdrawInfoServlet");
				machine.getStateJudgement().setTransactionState(4);
			}
		}
	}
	
	/**
	 * 把执行用户的请求
	 */
	public void execute()throws Exception {
		
	}
	
	/**
	 * 处理打印
	 * @param flag 0:打印 1:不打印
	 */
	public void print(int flag) {
		
	}
	
	/**
	 * 把执行用户的存款请求
	 */
	public void deposit() {
		
	}
	
	/**
	 * 执行用户转账请求
	 */
	public void transfer(){
		
	}
	
}
