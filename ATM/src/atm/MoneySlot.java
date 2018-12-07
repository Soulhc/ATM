package atm;

/**
 * 存钱口和出钱口
 * 
 * @author 梁木
 * @date 2018年12月6日
 */
public class MoneySlot {

	private boolean insertMoney = false;

	private boolean depositMoney = false;

	/**
	 * 获取状态字符串
	 */
	public String toString() {
		String output = "{";
		output += "\"insertMoney\":" + this.insertMoney;
		output += ",";
		output += "\"depositMoney\":" + this.depositMoney;
		output += "}";
		return output;
	}

	public boolean isInsertMoney() {
		return insertMoney;
	}

	public void setInsertMoney(boolean insertMoney) {
		this.insertMoney = insertMoney;
	}

	public boolean isDepositMoney() {
		return depositMoney;
	}

	public void setDepositMoney(boolean depositMoney) {
		this.depositMoney = depositMoney;
	}

}
