package cn.edu.xsyu.dorm.dao;

import java.util.List;

import cn.edu.xsyu.dorm.domain.BuildDorm;

public interface BuildDormDao {
	public BuildDorm queryBuildDormByID(Integer bdID);
	
	public int updateBuildDorm(BuildDorm buildDorm);
	
	public List<BuildDorm> queryBuildDorms(Integer buildingID,Integer level);
	
	public List<BuildDorm> queryByBuildingID(Integer buildingID);
	
	public BuildDorm queryBuildDormByOther(Integer buildingID,Integer dormitoryID);
}
