package cn.edu.xsyu.dorm.domain;

public class BuildDorm {
	private int bdID;
	private int buildingID;
	private int dormitoryID;
	
	public int getBdID() {
		return bdID;
	}
	public void setBdID(int bdID) {
		this.bdID = bdID;
	}
	public int getBuildingID() {
		return buildingID;
	}
	public void setBuildingID(int buildingID) {
		this.buildingID = buildingID;
	}
	public int getDormitoryID() {
		return dormitoryID;
	}
	public void setDormitoryID(int dormitoryID) {
		this.dormitoryID = dormitoryID;
	}
	@Override
	public String toString() {
		return "BuildDorm [bdID=" + bdID + ", buildingID=" + buildingID
				+ ", dormitoryID=" + dormitoryID + "]";
	}
	
}
