//******************************************************************************
//
// ATM系统 -  SwitchButton.java 
// 参考了http://www.cs.gordon.edu/courses/cs211/ATMExample/index.html
// 
//******************************************************************************

package atm;

/**
* 一个SwitchButton对象代表了现实世界里的ATM开关按钮.
* @author  何希
* @version 10/06/2018
*/
public class SwitchButton {
	// 开关按钮的显示文字
	private String text = "关机";
	// 开关按钮是否可用。ATM在处理状态时，不允许关机
	private boolean disable = false;
	
	/**
	 * ATM状态改变时，改变开关按钮对象的属性
	 * @param state
	 */
	public void stateChange(int state) {
		switch(state) {
		case ATM.PROCESSING:
			this.text = "关机";
			this.disable = false;
			break;
		case ATM.IDLE:
			this.text ="关机";
			this.disable = true;
			break;
		case ATM.SHUTDOWN:
			this.text = "开机";
			this.disable = true;
			break;
		}
	}
	
	/**
	 * 获取开关按钮的状态字符串
	 */
	public String toString() {
		String output = "{";
		output += "\"text\":\"" + this.text + "\"";
		output += ",";
		output += "\"disable\":" + this.disable;
		output += "}";
		return output;
	}
	
}
