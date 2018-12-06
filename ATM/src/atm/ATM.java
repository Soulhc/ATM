//******************************************************************************
//
// ATM系统 -  ATM.java 
// 参考了http://www.cs.gordon.edu/courses/cs211/ATMExample/index.html
// 
//******************************************************************************

package atm;

import java.security.Identity;

/**
* 一个ATM对象代表了现实世界里的一个ATM.
* @author  何希
* @version 10/06/2018
*/
public class ATM {
	
	public static final int IDLE = 0; // 空闲状态
	public static final int SHUTDOWN = 1; // 关闭状态
	public static final int PROCESSING = 2; // 处理状态

	// ATM对象
	private static ATM machine = null;
	// 状态变量
	private int state = IDLE;
	// 会话对象，当用户插入银行卡时，会话对象的状态会从未开始变为待验证
	private Session session = null;
	// 显示屏对象
	private Display monitor = null;
	// ATM开关按钮对象
	private SwitchButton btn_switch = null;
	// 插卡孔对象
	private CardSlot card_slot = null;
	// 数字键盘对象
	private DigitButton bton_digit = null;
	// 打印机对象
	private Print print = null;
	// 管理员按钮对象
	private IdentityButton identity_button = null;
	
	public Session getSession() {
		return this.session;
	}
	
	public Display getDisplay() {
		return this.monitor;
	}
	
	public SwitchButton getSwitchButton() {
		return this.btn_switch;
	}
	
	public CardSlot getCardSlot() {
		return this.card_slot;
	}
	
	public DigitButton getDigitButton() {
		return this.bton_digit;
	}
	
	public void setState(int state) {
		this.state = state;	
	}
	
	public Print getPrint() {
		return print;
	}

	/**
	 * 一个私有的构造器。之所以把这个构造器定义为私有，是不想外部可以使用new
	 * 来实例化一个实例。唯一的实例化方法是通过getInstance方法。
	 * 
	 */
	private ATM() {
		// 实例化对象
		// 系统启动时，默认ATM状态为空闲
		session = new Session();
		monitor = new Display();
		btn_switch = new SwitchButton();
		card_slot = new CardSlot();
		bton_digit = new DigitButton();
		print = new Print();
		identity_button = new IdentityButton();
	}

	/**
	 * 使用了单例模式。只允许系统里有一个ATM的实例。
	 * @return 一个ATM的实例
	 */
	public static ATM getInstance() {
		if(machine == null) {
			machine = new ATM();
		}
		return machine;
	}

	/**
	 * 开启ATM机
	 */
	public void turnon() {
		if(state == SHUTDOWN) {
			// 关机时需要改变ATM的状态，改变显示屏的显示，改变开关按钮的状态
			state = IDLE;
			this.monitor.setText("请插入你的银行卡");
			this.btn_switch.stateChange(ATM.IDLE);
		}
	}
	
	/**
	 * 关闭ATM机
	 */
	public void turnoff() {
		if(state == IDLE) {
			// 开机时需要改变ATM的状态，改变显示屏的显示，改变开关按钮的状态
			state = SHUTDOWN;
			this.monitor.setText("");
			this.btn_switch.stateChange(ATM.SHUTDOWN);
		}
	}
	
	/**
	 * 插入银行卡
	 * 
	 */
	public void cardInserted(String cardNo) {
		if(state == IDLE) {
			// 当银行卡有效，改变显示屏文字，开关按钮状态，插卡孔状态，数字键盘状态.
			if(session.verify(cardNo)) {
				state = PROCESSING;
				this.monitor.setText("请输入密码");
				this.btn_switch.stateChange(PROCESSING);
				this.card_slot.insert();
				this.bton_digit.stateChange(1, 1, "AuthServlet");
			}
			// 当银行卡无效时，会话结束
			else {
				this.session.startOver();
				state = IDLE;
			}
		}
	}
	
	/**
	 * 插入银行卡
	 * 
	 */
	public void cardInsertedAdmin(String cardNo) {
			// 当银行卡有效，改变显示屏文字，开关按钮状态，插卡孔状态，数字键盘状态.
			if(session.verifys(cardNo)) {
				ATM machine = ATM.getInstance();
				machine.setState(ATM.IDLE);
				machine.getCardSlot().eject();
				machine.getSwitchButton().stateChange(ATM.IDLE);
				machine.getDisplay().setText("请插入你的银行卡");
				machine.getSession().setTime(0);
				machine.getDigitButton().stateChange(2, 0, "");
				machine.getPrint().setText("");
				machine.getIdentity_button().setAppear(false);
			}
			// 当银行卡无效时，会话结束
			else {
				this.session.startOver();
				state = IDLE;
			}
		}
	
	/**
	 * 获取整个ATM系统各个对象的状态字符串. Json格式
	 * 如果想了解这个函数到底返回什么内容，可以在浏览器中访问 
	 * http://localhost:8081/ATM/GetStatusServlet
	 */
	public String getResponse() {
		String output = "{";
		output += "\"ATM\":" + this.toString();
		output += ",";
		output += "\"session\":" + this.session.toString();
		output += ",";
		output += "\"display\":" + this.monitor.toString();
		output += ",";
		output += "\"switchbutton\":" + this.btn_switch.toString();
		output += ",";
		output += "\"cardslot\":" + this.card_slot.toString();
		output += ",";
		output += "\"digitbutton\":" + this.bton_digit.toString();
		output += ",";
		output += "\"print\":" + this.print.toString();
		output += ",";
		output += "\"identity_button\":" + this.identity_button.toString();
		output += "}";
		return output;
	}
	
	/**
	 * 获取ATM的状态字符串. Json格式
	 */
	public String toString() {
		String output = "{";
		output += "\"state\":" + this.state;
		output += "}";
		return output;
	}

	public IdentityButton getIdentity_button() {
		return identity_button;
	}

	public void setIdentity_button(IdentityButton identity_button) {
		this.identity_button = identity_button;
	}
	
	
}
