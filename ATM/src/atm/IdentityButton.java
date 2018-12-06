package atm;

/**
 * 这个按钮代表了管理员
 * 
 * @author 梁木  
 * @date 2018年12月4日
 */
public class IdentityButton {

	    //管理员按钮是否出现
		private boolean isAppear = false;

		public boolean isAppear() {
			return isAppear;
		}

		public void setAppear(boolean isAppear) {
			this.isAppear = isAppear;
		}
		
		/**
		 * 获取开关按钮的状态字符串
		 */
		public String toString() {
			String output = "{";
			output += "\"isAppear\":" + this.isAppear;
			output += "}";
			return output;
		}
}
