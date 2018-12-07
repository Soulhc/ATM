//******************************************************************************
//
// ATM系统 -  Withdraw.java 
// 参考了 http://www.cs.gordon.edu/courses/cs211/ATMExample/index.html
// 
//******************************************************************************

package atm;

import java.util.List;

import banking.Account;
import database.TransactionExecute;

public class Withdraw extends Transaction {
	
	public static final int TRANS_UNSTART = 1; // 未开始
	public static final int TRANS_GETDATA = 2; // 获取交易请求
	public static final int TRANS_SUCCESS = 3; // 交易成功
	public static final int TRANS_FAILURE = 4; // 交易失败
	public static final int TRANS_EXIT = 4; // 退出
	
	public Withdraw(Session session, Account acct) {
		super(session,acct);
		// 当选择取款交易时,需要改变显示屏的显示,需要改变数字键盘的状态
		ATM machine = ATM.getInstance();
		machine.getDisplay().setText("请输入取款金额");
		machine.getPrint().setText("");
		machine.getDigitButton().stateChange(1, 0, "WithdrawInfoServlet");
	}
	
	public Withdraw(Session session, Account acct,int options) {
		super(session,acct);
		// 当选择取款交易时,需要改变显示屏的显示,需要改变数字键盘的状态
		ATM machine = ATM.getInstance();
		machine.getDisplay().setText("请放入纸币");
		machine.getPrint().setText("");
		machine.getDigitButton().stateChange(2, 1, "WithdepositServlet");
		machine.getMoneySlot().setInsertMoney(true);
	}
	
	/**
	 * 改变屏幕内容
	 * @param text
	 */
	public void SetDisplay(int state,String text,String servlet,boolean dep){
		this.setState(state);
		ATM machine = ATM.getInstance();
		machine.getDisplay().setText(text);
		machine.getDigitButton().stateChange(0, 0, servlet);
		machine.getMoneySlot().setDepositMoney(true);
	}
	
	/**
	 * 从用户账户扣取金额
	 */
	public void execute() {
		int ret = this.getAccount().withdraw(this.getAmount());
		// 扣取成功
		if(ret == 0) {
			// 显示屏更新 数字键盘状态更新 
			SetDisplay(TRANS_SUCCESS,"取款成功。你的余额是"+this.getAccount().getBalance()+"<br>"+"打印:0 不打印:1","WithdrawPrintServlet",true);
		}
		// 扣取不成功
		else if(ret == 2){
			this.getSession().setState(Session.CHOOSING);
			SetDisplay(TRANS_FAILURE,"对不起。您只能取面值为100的钞票。<br><br>请重修选择业务 1:取款 2:存款 0:退出","TransactionServlet",false);
		}else if(ret == 3){
			this.getSession().setState(Session.CHOOSING);
			SetDisplay(TRANS_FAILURE,"对不起。您一天最多只能取20000元。<br><br>请重修选择业务 1:取款 2:存款 0:退出","TransactionServlet",false);
		}else{
			this.getSession().setState(Session.CHOOSING);
			SetDisplay(TRANS_FAILURE,"对不起。取款失败,您的账户余额不足。你的账户存款为"+this.getAccount().getBalance()+"元"+"<br><br>"+"请重修选择业务 1:取款 2:存款 0:退出","TransactionServlet",false);
		}
	}
	
	public void deposit(){
		this.getAccount().deposit(this.getAmount());
		// 显示屏更新 数字键盘状态更新 
		this.setState(TRANS_SUCCESS);
		ATM machine = ATM.getInstance();
		machine.getDisplay().setText("存款成功。你的余额是"+this.getAccount().getBalance()+"<br>"+"打印:0 不打印:1");
		machine.getDigitButton().stateChange(0, 0, "WithdrawPrintServlet");
	}
	
	/**
	 * 处理打印
	 * @param flag 0:打印 1:不打印
	 */
	public void print(int flag) {
		// 显示屏更新 数字键盘状态更新 
		if(flag == 0){
			this.setState(TRANS_EXIT);
			this.getSession().setState(Session.CHOOSING);
			ATM machine = ATM.getInstance();
			machine.getDisplay().setText("请选择业务 1:取款 2:存款 0:退出 ");
			machine.getDigitButton().stateChange(0, 0, "TransactionServlet");
			machine.getPrint().setText(PrintText());
		}
		this.setState(TRANS_EXIT);
		this.getSession().setState(Session.CHOOSING);
		ATM machine = ATM.getInstance();
		machine.getDisplay().setText("请选择业务 1:取款 2:存款 0:退出 ");
		machine.getDigitButton().stateChange(0, 0, "TransactionServlet");
		machine.getMoneySlot().setInsertMoney(false);
		machine.getMoneySlot().setDepositMoney(false);
	}
	
	/**
	 * 打印机显示内容
	 */
	public String PrintText(){
		List<Object>print = TransactionExecute.resultList();
		String printText = "收据:\\n\\n"+"       您的卡号为"+print.get(0)+"已经成功"+print.get(1)+"了"+print.get(2)+"元。"+"\\n\\n"
		            
			+"日期:"+print.get(3)+"\\n\\n"+"-----------------------------------------------\\n";
		return printText;
	}
}
