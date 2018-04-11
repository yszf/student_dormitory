package cn.edu.xsyu.dorm.domain;

public class LeaveReg {
	private Integer regID;
	private String reason;
	private String destination;
	private String leaveTime;
	private String backTime;
	private String phone;
	private String emergenceContact;
	private String emergenceTel;
	private String regTime;
	private String studentID;


	public Integer getRegID() {
		return regID;
	}

	public void setRegID(Integer regID) {
		this.regID = regID;
	}

	public String getReason() {
		return reason;
	}

	public void setReason(String reason) {
		this.reason = reason;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public String getDestination() {
		return destination;
	}

	public void setDestination(String destination) {
		this.destination = destination;
	}

	public String getLeaveTime() {
		return leaveTime;
	}

	public void setLeaveTime(String leaveTime) {
		this.leaveTime = leaveTime;
	}

	public String getBackTime() {
		return backTime;
	}

	public void setBackTime(String backTime) {
		this.backTime = backTime;
	}

	public String getEmergenceContact() {
		return emergenceContact;
	}

	public void setEmergenceContact(String emergenceContact) {
		this.emergenceContact = emergenceContact;
	}

	public String getEmergenceTel() {
		return emergenceTel;
	}

	public void setEmergenceTel(String emergenceTel) {
		this.emergenceTel = emergenceTel;
	}

	public String getRegTime() {
		return regTime;
	}

	public void setRegTime(String regTime) {
		this.regTime = regTime;
	}

	public String getStudentID() {
		return studentID;
	}

	public void setStudentID(String studentID) {
		this.studentID = studentID;
	}

}
