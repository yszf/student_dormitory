package cn.edu.xsyu.dorm.domain;

public class BuildingManager {

	private String buildingManagerID;
	private String password;
	private String name;
	private String email;
	private String time;
	private Integer buildingID;

	public String getBuildingManagerID() {
		return buildingManagerID;
	}

	public void setBuildingManagerID(String buildingManagerID) {
		this.buildingManagerID = buildingManagerID;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getTime() {
		return time;
	}

	public void setTime(String time) {
		this.time = time;
	}

	public Integer getBuildingID() {
		return buildingID;
	}

	public void setBuildingID(Integer buildingID) {
		this.buildingID = buildingID;
	}

}