package cn.edu.xsyu.dorm.dao;

import cn.edu.xsyu.dorm.domain.BuildingManager;

public interface BuildingManagerDao {
	
	public BuildingManager queryBuildingManagerByID(String ID);
	
	public int updateBuildingManagerPass(String buildingManagerID,String newPass);
	
	public int updateBuildingManager(BuildingManager buildingManager);

	public BuildingManager queryBuildingManagerByEmail(String email);
	
	public int updateBuildingManagerPass2(String email,String newPass);
}
