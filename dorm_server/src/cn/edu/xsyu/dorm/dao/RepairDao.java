package cn.edu.xsyu.dorm.dao;

import java.util.List;

import cn.edu.xsyu.dorm.domain.Repair;

public interface RepairDao {
	
	public Repair queryRepairByID(Integer repairID);
	
	public List<Repair> queryRepairs();
	
	public int insertRepair(Repair repair);
	
	public int changeRepair(Repair repair);
	
}
