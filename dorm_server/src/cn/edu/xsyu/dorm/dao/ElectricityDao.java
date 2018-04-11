package cn.edu.xsyu.dorm.dao;

import cn.edu.xsyu.dorm.domain.Electricity;

public interface ElectricityDao {
	
	public Electricity queryElectrictyByBdID(Integer bdID);

}
