//******************************************************************************
//
// ATM系统 -  Display.java 
// 参考了http://www.cs.gordon.edu/courses/cs211/ATMExample/index.html
// 
//******************************************************************************

package atm;

/**
* 一个Display对象代表了现实世界里的一个显示屏.
* @author  何希
* @version 10/06/2018
*/
public class Display {

	// 系统启动时,默认ATM状态为空闲,等待用户插入银行卡
	// 此变量代表显示屏上显示的文字
	private String text = "请插入你的银行卡";
	
	public String getText() {
		return this.text;
	}
	
	public void setText(String text) {
		this.text = text;
	}
	
	/**
	 * 获取显示屏的状态字符串. Json格式
	 */
	public String toString() {
		String output = "{";
		output += "\"text\":\"" + this.text + "\"";
		output += "}";
		return output;
	}

}
