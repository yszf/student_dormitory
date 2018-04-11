package cn.edu.xsyu.dorm.domain;

public class Score {
	private Integer scoreID;
	private Integer weekNum;
	private Integer level;
	private Integer isIllegal;
	private String description;
	private Integer bdID;

	public Integer getScoreID() {
		return scoreID;
	}

	public void setScoreID(Integer scoreID) {
		this.scoreID = scoreID;
	}

	public Integer getWeekNum() {
		return weekNum;
	}

	public void setWeekNum(Integer weekNum) {
		this.weekNum = weekNum;
	}

	public Integer getLevel() {
		return level;
	}

	public void setLevel(Integer level) {
		this.level = level;
	}

	public Integer getIsIllegal() {
		return isIllegal;
	}

	public void setIsIllegal(Integer isIllegal) {
		this.isIllegal = isIllegal;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public Integer getBdID() {
		return bdID;
	}

	public void setBdID(Integer bdID) {
		this.bdID = bdID;
	}
	
}
