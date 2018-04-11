package cn.edu.xsyu.dorm.domain;

public class Building {
	private Integer buildingID;
	private Integer roomSum;
	private Integer freeSum;
	private Integer studentNum;

	public Integer getBuildingID() {
		return buildingID;
	}

	public void setBuildingID(Integer buildingID) {
		this.buildingID = buildingID;
	}

	public Integer getRoomSum() {
		return roomSum;
	}

	public void setRoomSum(Integer roomSum) {
		this.roomSum = roomSum;
	}

	public Integer getFreeSum() {
		return freeSum;
	}

	public void setFreeSum(Integer freeSum) {
		this.freeSum = freeSum;
	}

	public Integer getStudentNum() {
		return studentNum;
	}

	public void setStudentNum(Integer studentNum) {
		this.studentNum = studentNum;
	}

}
