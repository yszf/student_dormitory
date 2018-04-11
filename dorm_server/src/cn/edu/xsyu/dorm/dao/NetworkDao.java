package cn.edu.xsyu.dorm.dao;

import cn.edu.xsyu.dorm.domain.Network;

public interface NetworkDao {

	public Network queryNetworkByStudentID(String studentID);
	
}
