package cn.edu.xsyu.dorm.domain;

public class Repair {
	private Integer repairID;
	private String startTime;
	private String lastTime;
	private String issuer;
	private String contacts;
	private String reason;
	private Integer dealState;
	private String servicemanID;
	private Integer bdID;

	public String getIssuer() {
		return issuer;
	}

	public void setIssuer(String issuer) {
		this.issuer = issuer;
	}

	public Integer getRepairID() {
		return repairID;
	}

	public void setRepairID(Integer repairID) {
		this.repairID = repairID;
	}

	public String getStartTime() {
		return startTime;
	}

	public void setStartTime(String startTime) {
		this.startTime = startTime;
	}

	public String getLastTime() {
		return lastTime;
	}

	public void setLastTime(String lastTime) {
		this.lastTime = lastTime;
	}

	public String getContacts() {
		return contacts;
	}

	public void setContacts(String contacts) {
		this.contacts = contacts;
	}

	public String getReason() {
		return reason;
	}

	public void setReason(String reason) {
		this.reason = reason;
	}

	public Integer getDealState() {
		return dealState;
	}

	public void setDealState(Integer dealState) {
		this.dealState = dealState;
	}

	public String getServicemanID() {
		return servicemanID;
	}

	public void setServicemanID(String servicemanID) {
		this.servicemanID = servicemanID;
	}

	public Integer getBdID() {
		return bdID;
	}

	public void setBdID(Integer bdID) {
		this.bdID = bdID;
	}

	@Override
	public String toString() {
		return "Repair [repairID=" + repairID + ", startTime=" + startTime
				+ ", lastTime=" + lastTime + ", issuer=" + issuer
				+ ", contacts=" + contacts + ", reason=" + reason
				+ ", dealState=" + dealState + ", servicemanID=" + servicemanID
				+ ", bdID=" + bdID + "]";
	}

}