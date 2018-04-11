package cn.edu.xsyu.dorm.dao;

import java.util.List;

import cn.edu.xsyu.dorm.domain.Score;

public interface ScoreDao {
	
	public List<Score> queryScoreByDormitoryID(Integer dormitoryID);
	
	public Score queryScoreByID(Integer scoreID);
	
	public Integer queryMaxWeekByBuildingID(int buildingID);
	
	public Integer queryMaxWeekByBdID(Integer bdID);
	
	public Integer insertScore(Score score);
	
}
