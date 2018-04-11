package cn.edu.xsyu.dorm.dao.impl;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import cn.edu.xsyu.dorm.dao.RepairDao;
import cn.edu.xsyu.dorm.domain.Repair;
import cn.edu.xsyu.dorm.utils.ConnDB;

public class RepairDaoImpl implements RepairDao {

	private ConnDB conn;

	public RepairDaoImpl() {
		conn = new ConnDB();
	}
	
	@Override
	public Repair queryRepairByID(Integer repairID) {
		
		String sql = "select * from repair where repairID = " + repairID;
		System.out.println("queryRepairByID-----------" + sql);
		ResultSet rs = conn.queryDB(sql);
		Repair repair = null;
		try {
			if (rs.next()) {
				repair = new Repair();
				repair.setBdID(rs.getInt("bdID"));
				repair.setContacts(rs.getString("contacts"));
				repair.setDealState(rs.getInt("dealState"));
				repair.setLastTime(rs.getString("lastTime"));
				repair.setReason(rs.getString("reason"));
				repair.setRepairID(rs.getInt("repairID"));
				repair.setServicemanID(rs.getString("servicemanID"));
				repair.setStartTime(rs.getString("startTime"));
				repair.setIssuer(rs.getString("issuer"));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			conn.closeDB();
		}
		return repair;
	}

	@Override
	public List<Repair> queryRepairs() {
		String sql = "select * from repair order by startTime desc";
		System.out.println("queryRepairs-----------" + sql);
		ResultSet rs = conn.queryDB(sql);
		List<Repair> list = new ArrayList<Repair>();
		Repair repair = null;
		try {
			while (rs.next()) {
				repair = new Repair();
				repair.setBdID(rs.getInt("bdID"));
				repair.setContacts(rs.getString("contacts"));
				repair.setDealState(rs.getInt("dealState"));
				repair.setLastTime(rs.getString("lastTime"));
				repair.setReason(rs.getString("reason"));
				repair.setRepairID(rs.getInt("repairID"));
				repair.setServicemanID(rs.getString("servicemanID"));
				repair.setStartTime(rs.getString("startTime"));
				repair.setIssuer(rs.getString("issuer"));
				list.add(repair);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			conn.closeDB();
		}
		return list;
	}

	@Override
	public int insertRepair(Repair repair) {
		String sql = "insert into repair(startTime,lastTime,issuer,contacts,reason,dealState,serviceManID,bdID)value("
				+"curDate(),curDate(),'"
				+repair.getIssuer()+"','"
				+repair.getContacts()+"','"
				+repair.getReason()+"',"
				+repair.getDealState()+",'"
				+repair.getServicemanID()+"',"
				+repair.getBdID()+")";
		System.out.println("insertRepair*********" + sql);
		return conn.updateDB(sql);
	}

	@Override
	public int changeRepair(Repair repair) {
		String sql = "update repair set dealState = "+repair.getDealState()
				+",serviceManID = '"+repair.getServicemanID()
				+"',lastTime = curDate()"
				+" where repairID = "+repair.getRepairID();
		System.out.println("changeRepair-------------"+sql);
		return conn.updateDB(sql);
	}

}
