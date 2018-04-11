package cn.edu.xsyu.dorm.dao;

import cn.edu.xsyu.dorm.domain.ServiceMan;


public interface ServiceManDao {
	
	public ServiceMan queryServiceManByID(String ID);
	
	public int updateServiceManPass(String serviceManID,String newPass);
	
	public int updateServiceMan(ServiceMan serviceMan);
	
	public ServiceMan queryServiceManByEmail(String email);
	
	public int updateServiceManPass2(String email,String newPass);
}
