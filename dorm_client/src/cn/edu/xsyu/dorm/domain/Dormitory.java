package cn.edu.xsyu.dorm.domain;

public class Dormitory {

	private Integer dormitoryID;

	private Integer bedSum;

	private Integer peopleNum;

	private String roomheader;

	public Integer getDormitoryID() {
		return dormitoryID;
	}

	public void setDormitoryID(Integer dormitoryID) {
		this.dormitoryID = dormitoryID;
	}

	public Integer getBedSum() {
		return bedSum;
	}

	public void setBedSum(Integer bedSum) {
		this.bedSum = bedSum;
	}

	public Integer getPeopleNum() {
		return peopleNum;
	}

	public void setPeopleNum(Integer peopleNum) {
		this.peopleNum = peopleNum;
	}

	public String getRoomheader() {
		return roomheader;
	}

	public void setRoomheader(String roomheader) {
		this.roomheader = roomheader;
	}

	@Override
	public String toString() {
		return "Dormitory [dormitoryID=" + dormitoryID + ", bedSum=" + bedSum
				+ ", peopleNum=" + peopleNum + ", roomheader=" + roomheader
				+ "]";
	}
}