//******************************************************************************
//
// ATM系统 -  CardSlot.java 
// 参考了http://www.cs.gordon.edu/courses/cs211/ATMExample/index.html
// 
//******************************************************************************

package atm;

/**
* 一个CardSlot对象代表了现实世界里的一个插卡孔.
* @author  何希
* @version 10/06/2018
*/
public class CardSlot {
	// 在当前的实现中，我们是用一个按钮来代表插卡孔。
	// 按钮的显示文字
	private String text = "插卡";
	// 是否有银行卡在ATM内
	private boolean inserted = false;
	
	/**
	 * 插入银行卡
	 */
	public void insert() {
		if(!inserted) {
			this.text = "有卡";
			inserted = true;
		}
	}
	
	/**
	 * 弹出银行卡 
	 */
	public void eject() {
		if(inserted) {
			this.text = "插卡";
			inserted = false;
		}
	}
	
	/**
	 * 获取状态字符串
	 */
	public String toString() {
		String output = "{";
		output += "\"text\":\"" + this.text + "\"";
		output += ",";
		output += "\"inserted\":" + this.inserted;
		output += "}";
		return output;
	}
	
}
