package cn.edu.xsyu.dorm.dao.impl;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import cn.edu.xsyu.dorm.dao.DormitoryDao;
import cn.edu.xsyu.dorm.domain.Dormitory;
import cn.edu.xsyu.dorm.utils.ConnDB;

public class DormitoryDaoImpl implements DormitoryDao {
	private ConnDB conn;

	public DormitoryDaoImpl() {
		conn = new ConnDB();
	}

	@Override
	public Dormitory queryDormitory(Integer dormitoryID) {
		String sql = "select * from dormitory where dormitoryID = "
				+ dormitoryID;
		System.out.println("queryDormitory-----------"+sql);
		ResultSet rs = conn.queryDB(sql);
		Dormitory dormitory = null;
		try {
			if (rs.next()) {
				dormitory = new Dormitory();
				dormitory.setBedSum(rs.getInt("bedSum"));
				dormitory.setDormitoryID(rs.getInt("dormitoryID"));
				dormitory.setPeopleNum(rs.getInt("peopleNum"));
				dormitory.setRoomheader(rs.getString("roomheader"));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			conn.closeDB();
		}
		return dormitory;
	}

	@Override
	public List<Dormitory> queryDormitorys() {
		String sql = "select * from dormitory";
		System.out.println("queryDormitorys------"+sql);
		ResultSet rs = conn.queryDB(sql);
		List<Dormitory> list = new ArrayList<Dormitory>();
		Dormitory dormitory = null;
		try {
			while (rs.next()) {
				dormitory = new Dormitory();
				dormitory.setBedSum(rs.getInt("bedSum"));
				dormitory.setDormitoryID(rs.getInt("dormitoryID"));
				dormitory.setPeopleNum(rs.getInt("peopleNum"));
				dormitory.setRoomheader(rs.getString("roomheader"));
				list.add(dormitory);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			conn.closeDB();
		}
		return list;
	}

	@Override
	public List<String> queryDormIDByCondition(Integer buildingID,
			Integer weekNum, Integer level) {
		String sql = "select dormID from dormitory,score where score.dormitoryID = dormitory.dormitoryID and buildingID = "
				+ buildingID
				+ " and weekNum = "
				+ weekNum
				+ " and level = "
				+ level;
		System.out.println("queryDormIDByCondition----------"+sql);
		ResultSet rs = conn.queryDB(sql);
		List<String> list = new ArrayList<String>();
		try {
			while(rs.next()){
				list.add(rs.getString("dormID"));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return list;
	}

	public int updateDormitory(Dormitory dormitory){
		String sql = "update dormitory set bedSum = " + dormitory.getBedSum() + ",roomheader = '"
				+ dormitory.getRoomheader() + "',peopleNum = " + dormitory.getPeopleNum() + " where dormitoryID = " + dormitory.getDormitoryID();
		System.out.println("updateDormitory----------------" + sql);
		return conn.updateDB(sql);
	}
}
