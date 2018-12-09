package atm;

/**
 * 根据状态选择业务
 * 
 * @author 梁木  
 * @date 2018年12月8日
 */
public class StateJudgement {
	
	//交易状态
	public int transactionState = 5;
	
	//身份状态
	public int identityState = 5;

	public int getTransactionState() {
		return transactionState;
	}

	public void setTransactionState(int transactionState) {
		this.transactionState = transactionState;
	}

	public int getIdentityState() {
		return identityState;
	}

	public void setIdentityState(int identityState) {
		this.identityState = identityState;
	}
	
	/**
	 * 获取交易状态字符串
	 */
	public String toString() {
		String output = "{";
		output += "\"transactionState\":" + this.transactionState;
		output += ",";
		output += "\"identityState\":" + this.identityState;
		output += "}";
		return output;
	}
	

}
