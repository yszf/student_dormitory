package cn.edu.xsyu.dorm.dao.impl;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import cn.edu.xsyu.dorm.dao.BuildDormDao;
import cn.edu.xsyu.dorm.domain.BuildDorm;
import cn.edu.xsyu.dorm.utils.ConnDB;

public class BuildDormDaoImpl implements BuildDormDao {
	private ConnDB conn;

	public BuildDormDaoImpl() {
		conn = new ConnDB();
	}
	
	@Override
	public BuildDorm queryBuildDormByID(Integer bdID) {
		String sql = "select * from builddorm where bdID = "+bdID;
		System.out.println("queryBuildDormByID--------" + sql);
		ResultSet rs = conn.queryDB(sql);
		BuildDorm buildDorm = null;
		try {
			if (rs.next()) {
				buildDorm = new BuildDorm();
				buildDorm.setBdID(rs.getInt("bdID"));
				buildDorm.setBuildingID(rs.getInt("buildingID"));
				buildDorm.setDormitoryID(rs.getInt("dormitoryID"));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			conn.closeDB();
		}
		return buildDorm;
	}

	public int updateBuildDorm(BuildDorm buildDorm){
		String sql = "update builddorm set buildingID = "+buildDorm.getBuildingID()+
				",dormitoryID = "+buildDorm.getDormitoryID()+ " where bdID = "+buildDorm.getBdID();
		System.out.println("updateBuildDorm----------------" + sql);
		return conn.updateDB(sql);
	}

	@Override
	public List<BuildDorm> queryBuildDorms(Integer buildingID,Integer level) {
		String sql = "select * from builddorm where buildingID = " + buildingID + " and bdID in " +
				"(select bdID from score where score.level = "+ level + 
				" and weekNum = (select MAX(weekNum) AS M from score where bdID in "+
				"(SELECT builddorm.bdID FROM builddorm WHERE builddorm.buildingID = "
				+buildingID+")))";
		System.out.println("queryBuildDorms--------" + sql);
		List<BuildDorm> list = new ArrayList<BuildDorm>();
		ResultSet rs = conn.queryDB(sql);
		try {
			while (rs.next()) {
				BuildDorm buildDorm = new BuildDorm();
				buildDorm.setBdID(rs.getInt("bdID"));
				buildDorm.setBuildingID(rs.getInt("buildingID"));
				buildDorm.setDormitoryID(rs.getInt("dormitoryID"));
				list.add(buildDorm);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			conn.closeDB();
		}
		return list;
	}

	@Override
	public List<BuildDorm> queryByBuildingID(Integer buildingID) {
		String sql = "select * from builddorm where buildingID = " + buildingID;
		System.out.println("queryByBuildingID--------" + sql);
		List<BuildDorm> list = new ArrayList<BuildDorm>();
		ResultSet rs = conn.queryDB(sql);
		try {
			while (rs.next()) {
				BuildDorm buildDorm = new BuildDorm();
				buildDorm.setBdID(rs.getInt("bdID"));
				buildDorm.setBuildingID(rs.getInt("buildingID"));
				buildDorm.setDormitoryID(rs.getInt("dormitoryID"));
				list.add(buildDorm);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			conn.closeDB();
		}
		return list;
	}

	@Override
	public BuildDorm queryBuildDormByOther(Integer buildingID,
			Integer dormitoryID) {
		String sql = "select * from builddorm where buildingID = "+buildingID + " and dormitoryID = "+ dormitoryID;
		System.out.println("queryBuildDormByOther--------" + sql);
		ResultSet rs = conn.queryDB(sql);
		BuildDorm buildDorm = null;
		try {
			if (rs.next()) {
				buildDorm = new BuildDorm();
				buildDorm.setBdID(rs.getInt("bdID"));
				buildDorm.setBuildingID(rs.getInt("buildingID"));
				buildDorm.setDormitoryID(rs.getInt("dormitoryID"));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			conn.closeDB();
		}
		return buildDorm;
	}
}
