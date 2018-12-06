package atm;

/**
 * 打印机对象
 * 
 * @author 梁木  
 * @date 2018年12月3日
 */
public class Print {
	//打印收据信息
	private String text = "";
	
	public String getText() {
		return text;
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
