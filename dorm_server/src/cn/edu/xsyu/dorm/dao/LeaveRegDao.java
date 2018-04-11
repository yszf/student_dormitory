package cn.edu.xsyu.dorm.dao;

import java.util.List;

import cn.edu.xsyu.dorm.domain.LeaveReg;

public interface LeaveRegDao {
	public LeaveReg queryLeaveRegByID(Integer regID);
	
	public List<LeaveReg> queryRegsByStudentID(String studentID);

	public List<LeaveReg> queryRegs();
}
