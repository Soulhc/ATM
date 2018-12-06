//******************************************************************************
//
// ATM系统 -  DigitButton.java 
// 参考了http://www.cs.gordon.edu/courses/cs211/ATMExample/index.html
// 
//******************************************************************************

package atm;

/**
* 一个DigitButton对象代表了现实世界里的一组数字键盘.
* @author  何希
* @version 10/06/2018
*/
public class DigitButton {
	
	// 0:单个数字 1:多个数字 2:不允许输入
	// 状态0: 在菜单选择中，用户输入一个数字，不需要回车键，即可提交
	// 状态1: 在输入金额，或者密码时，用户需要输入若干个数字，只有按了回车键，才可以提交
	// 状态2: 不可以输入数字
	// 系统启动时默认不可以输入数字
	private int state = 2; 
	// 0:在显示屏显示输入的数字   1:在显示屏显示星号
	private int visibility = 0; 
	// 当前需要提交的Web服务名字
	private String servletName = "GetStatusServlet";
	
	/**
	 * 改变对象的状态
	 * @param state
	 * @param visibility
	 * @param servletName
	 */
	public void stateChange(int state, int visibility,String servletName) {
		this.state = state;
		this.visibility = visibility;
		this.servletName = servletName;
	}
	
	/**
	 * 获取数字键盘的状态字符串
	 */
	public String toString() {
		String output = "{";
		output += "\"state\":" + this.state;
		output += ",";
		output += "\"visibility\":" + this.visibility;
		output += ",";
		output += "\"servletName\":\"" + this.servletName +  "\"";
		output += "}";
		return output;
	}
	
}
