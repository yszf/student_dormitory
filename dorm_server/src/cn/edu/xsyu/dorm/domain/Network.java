package cn.edu.xsyu.dorm.domain;

public class Network {
	private Integer networkID;
	private Double remainMoney;
	private Integer remainTime;
	private Double rechargeMoney;
	private String rechargeTime;
	private String studentID;

	public Integer getNetworkID() {
		return networkID;
	}

	public void setNetworkID(Integer networkID) {
		this.networkID = networkID;
	}

	public Double getRemainMoney() {
		return remainMoney;
	}

	public void setRemainMoney(Double remainMoney) {
		this.remainMoney = remainMoney;
	}

	public Integer getRemainTime() {
		return remainTime;
	}

	public void setRemainTime(Integer remainTime) {
		this.remainTime = remainTime;
	}

	public Double getRechargeMoney() {
		return rechargeMoney;
	}

	public void setRechargeMoney(Double rechargeMoney) {
		this.rechargeMoney = rechargeMoney;
	}

	public String getRechargeTime() {
		return rechargeTime;
	}

	public void setRechargeTime(String rechargeTime) {
		this.rechargeTime = rechargeTime;
	}

	public String getStudentID() {
		return studentID;
	}

	public void setStudentID(String studentID) {
		this.studentID = studentID;
	}

	@Override
	public String toString() {
		return "Network [networkID=" + networkID
				+ ", remainMoney=" + remainMoney + ", remainTime=" + remainTime
				+ ", rechargeMoney=" + rechargeMoney + ", rechargeTime="
				+ rechargeTime + ", studentID=" + studentID + "]";
	}

}
