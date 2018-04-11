package cn.edu.xsyu.dorm.dao;

import java.util.List;

import cn.edu.xsyu.dorm.domain.Dormitory;

public interface DormitoryDao {
	
	public Dormitory queryDormitory(Integer dormitoryID);
	
	public List<Dormitory> queryDormitorys();
	
	public List<String> queryDormIDByCondition(Integer buildingID,Integer weekNum,Integer level);

	public int updateDormitory(Dormitory dormitory);
}
