package cn.edu.xsyu.dorm.dao.impl;

import java.sql.ResultSet;
import java.sql.SQLException;

import cn.edu.xsyu.dorm.dao.ServiceManDao;
import cn.edu.xsyu.dorm.domain.ServiceMan;
import cn.edu.xsyu.dorm.utils.ConnDB;

public class ServiceManDaoImpl implements ServiceManDao{
	private ConnDB conn;

	public ServiceManDaoImpl() {
		conn = new ConnDB();
	}

	@Override
	public ServiceMan queryServiceManByID(String ID) {
		String sql = "select * from serviceMan where serviceManID = '" + ID + "'";
		System.out.println("queryServiceManByID--------" + sql);
		ResultSet rs = conn.queryDB(sql);
		ServiceMan serviceMan = null;
		try {
			if (rs.next()) {
				serviceMan = new ServiceMan();
				serviceMan.setServiceManID(rs.getString("serviceManID"));
				serviceMan.setPassword(rs.getString("password"));
				serviceMan.setName(rs.getString("name"));
				serviceMan.setTime(rs.getString("time"));
				serviceMan.setEmail(rs.getString("email"));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			conn.closeDB();
		}
		return serviceMan;
	}

	@Override
	public int updateServiceManPass(String serviceManID, String newPass) {
		String sql = "update serviceMan set password = '"+newPass+"' where serviceManID = '"+serviceManID+"'";
		System.out.println("updateServiceManPass-------------"+sql);
		return conn.updateDB(sql);
	}

	@Override
	public int updateServiceMan(ServiceMan serviceMan) {
		String sql = "update serviceMan set name = '"+serviceMan.getName()
				+"',email = '"+ serviceMan.getEmail()+"' where serviceManID = '"+serviceMan.getServiceManID()+"'";
		System.out.println("updateServiceMan-------------"+sql);
		return conn.updateDB(sql);
	}

	@Override
	public ServiceMan queryServiceManByEmail(String email) {
		String sql = "select * from serviceMan where email = '" + email + "'";
		System.out.println("queryServiceManByEmail--------" + sql);
		ResultSet rs = conn.queryDB(sql);
		ServiceMan serviceMan = null;
		try {
			if (rs.next()) {
				serviceMan = new ServiceMan();
				serviceMan.setServiceManID(rs.getString("serviceManID"));
				serviceMan.setPassword(rs.getString("password"));
				serviceMan.setName(rs.getString("name"));
				serviceMan.setTime(rs.getString("time"));
				serviceMan.setEmail(rs.getString("email"));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			conn.closeDB();
		}
		return serviceMan;
	}

	@Override
	public int updateServiceManPass2(String email, String newPass) {
		String sql = "update serviceMan set password = '"+newPass+"' where email = '"+email+"'";
		System.out.println("updateServiceManPass2-------------"+sql);
		return conn.updateDB(sql);
	}

}
