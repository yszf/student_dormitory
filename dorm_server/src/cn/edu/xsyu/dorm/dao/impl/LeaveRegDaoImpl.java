package cn.edu.xsyu.dorm.dao.impl;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import cn.edu.xsyu.dorm.dao.LeaveRegDao;
import cn.edu.xsyu.dorm.domain.LeaveReg;
import cn.edu.xsyu.dorm.utils.ConnDB;

public class LeaveRegDaoImpl implements LeaveRegDao {

	private ConnDB conn;

	public LeaveRegDaoImpl() {
		conn = new ConnDB();
	}
	
	@Override
	public LeaveReg queryLeaveRegByID(Integer regID) {
		String sql = "select * from leavereg where regID = " + regID;
		System.out.println("queryLeaveRegByID-----------" + sql);
		ResultSet rs = conn.queryDB(sql);
		LeaveReg leaveReg = null;
		try {
			if (rs.next()) {
				leaveReg = new LeaveReg();
				leaveReg.setStudentID(rs.getString("studentID"));
				leaveReg.setBackTime(rs.getString("backTime"));
				leaveReg.setDestination(rs.getString("destination"));
				leaveReg.setEmergenceContact(rs.getString("emergenceContact"));
				leaveReg.setEmergenceTel(rs.getString("emergenceTel"));
				leaveReg.setLeaveTime(rs.getString("leaveTime"));
				leaveReg.setPhone(rs.getString("phone"));
				leaveReg.setReason(rs.getString("reason"));
				leaveReg.setRegTime(rs.getString("regTime"));
				leaveReg.setRegID(rs.getInt("regID"));
				leaveReg.setRegTime(rs.getString("regTime"));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			conn.closeDB();
		}
		return leaveReg;
	}

	@Override
	public List<LeaveReg> queryRegsByStudentID(String studentID) {
		String sql = "select * from leavereg where studentID = "+studentID+" order by regTime desc";
		System.out.println("queryRegsByStudentID-----------" + sql);
		ResultSet rs = conn.queryDB(sql);
		List<LeaveReg> list = new ArrayList<LeaveReg>();
		LeaveReg leaveReg = null;
		try {
			while (rs.next()) {
				leaveReg = new LeaveReg();
				leaveReg.setStudentID(rs.getString("studentID"));
				leaveReg.setBackTime(rs.getString("backTime"));
				leaveReg.setDestination(rs.getString("destination"));
				leaveReg.setEmergenceContact(rs.getString("emergenceContact"));
				leaveReg.setEmergenceTel(rs.getString("emergenceTel"));
				leaveReg.setLeaveTime(rs.getString("leaveTime"));
				leaveReg.setRegTime(rs.getString("regTime"));
				leaveReg.setPhone(rs.getString("phone"));
				leaveReg.setReason(rs.getString("reason"));
				leaveReg.setRegID(rs.getInt("regID"));
				list.add(leaveReg);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			conn.closeDB();
		}
		return list;
	}

	@Override
	public List<LeaveReg> queryRegs() {
		String sql = "select * from leavereg order by regTime desc";
		System.out.println("queryRegs-----------" + sql);
		ResultSet rs = conn.queryDB(sql);
		List<LeaveReg> list = new ArrayList<LeaveReg>();
		LeaveReg leaveReg = null;
		try {
			while (rs.next()) {
				leaveReg = new LeaveReg();
				leaveReg.setStudentID(rs.getString("studentID"));
				leaveReg.setBackTime(rs.getString("backTime"));
				leaveReg.setDestination(rs.getString("destination"));
				leaveReg.setEmergenceContact(rs.getString("emergenceContact"));
				leaveReg.setEmergenceTel(rs.getString("emergenceTel"));
				leaveReg.setLeaveTime(rs.getString("leaveTime"));
				leaveReg.setRegTime(rs.getString("regTime"));
				leaveReg.setPhone(rs.getString("phone"));
				leaveReg.setReason(rs.getString("reason"));
				leaveReg.setRegID(rs.getInt("regID"));
				list.add(leaveReg);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			conn.closeDB();
		}
		return list;
	}

}
