package cn.edu.xsyu.dorm.domain;

public class Electricity {
	private Integer elecID;
	private Double remainElec;
	private Double remainMoney;
	private Double rechargeMoney;
	private String rechargeTime;
	private Integer bdID;

	public Integer getElecID() {
		return elecID;
	}

	public void setElecID(Integer elecID) {
		this.elecID = elecID;
	}

	public Double getRemainElec() {
		return remainElec;
	}

	public void setRemainElec(Double remainElec) {
		this.remainElec = remainElec;
	}

	public Double getRemainMoney() {
		return remainMoney;
	}

	public void setRemainMoney(Double remainMoney) {
		this.remainMoney = remainMoney;
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

	public Integer getBdID() {
		return bdID;
	}

	public void setBdID(Integer bdID) {
		this.bdID = bdID;
	}

}
