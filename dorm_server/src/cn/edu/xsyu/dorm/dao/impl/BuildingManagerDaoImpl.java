package cn.edu.xsyu.dorm.dao.impl;

import java.sql.ResultSet;
import java.sql.SQLException;

import cn.edu.xsyu.dorm.dao.BuildingManagerDao;
import cn.edu.xsyu.dorm.domain.BuildingManager;
import cn.edu.xsyu.dorm.utils.ConnDB;

public class BuildingManagerDaoImpl implements BuildingManagerDao {
	private ConnDB conn;

	public BuildingManagerDaoImpl() {
		conn = new ConnDB();
	}
	@Override
	public BuildingManager queryBuildingManagerByID(String ID) {
		String sql = "select * from buildingManager where buildingManagerID = '" + ID + "'";
		System.out.println("queryBuildingManagerByID--------" + sql);
		ResultSet rs = conn.queryDB(sql);
		BuildingManager buildingManager = null;
		try {
			if (rs.next()) {
				buildingManager = new BuildingManager();
				buildingManager.setBuildingManagerID(rs.getString("buildingManagerID"));
				buildingManager.setPassword(rs.getString("password"));
				buildingManager.setName(rs.getString("name"));
				buildingManager.setTime(rs.getString("time"));
				buildingManager.setEmail(rs.getString("email"));
				buildingManager.setBuildingID(rs.getInt("buildingID"));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			conn.closeDB();
		}
		return buildingManager;
	}

	@Override
	public int updateBuildingManagerPass(String buildingManagerID,
			String newPass) {
		String sql = "update buildingManager set password = '"+newPass+"' where buildingManagerID = '"+buildingManagerID+"'";
		System.out.println("updateBuildingManagerPass-------------"+sql);
		return conn.updateDB(sql);
	}
	@Override
	public int updateBuildingManager(BuildingManager buildingManager) {
		String sql = "update buildingManager set buildingID = "+buildingManager.getBuildingID()
				+",name = '"+buildingManager.getName()
				+"',email = '"+ buildingManager.getEmail()+"' where buildingManagerID = '"+buildingManager.getBuildingManagerID()+"'";
		System.out.println("updateBuildingManager-------------"+sql);
		return conn.updateDB(sql);
	}
	@Override
	public BuildingManager queryBuildingManagerByEmail(String email) {
		String sql = "select * from buildingManager where email = '" + email + "'";
		System.out.println("queryBuildingManagerByEmail--------" + sql);
		ResultSet rs = conn.queryDB(sql);
		BuildingManager buildingManager = null;
		try {
			if (rs.next()) {
				buildingManager = new BuildingManager();
				buildingManager.setBuildingManagerID(rs.getString("buildingManagerID"));
				buildingManager.setPassword(rs.getString("password"));
				buildingManager.setName(rs.getString("name"));
				buildingManager.setTime(rs.getString("time"));
				buildingManager.setEmail(rs.getString("email"));
				buildingManager.setBuildingID(rs.getInt("buildingID"));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			conn.closeDB();
		}
		return buildingManager;
	}
	@Override
	public int updateBuildingManagerPass2(String email, String newPass) {
		String sql = "update buildingManager set password = '"+newPass+"' where email = '"+email+"'";
		System.out.println("updateBuildingManagerPass2-------------"+sql);
		return conn.updateDB(sql);
	}

}
