package cn.edu.xsyu.dorm.dao.impl;

import java.sql.ResultSet;
import java.sql.SQLException;

import cn.edu.xsyu.dorm.dao.NetworkDao;
import cn.edu.xsyu.dorm.domain.Network;
import cn.edu.xsyu.dorm.utils.ConnDB;

public class NetworkDaoImpl implements NetworkDao {
	private ConnDB conn;
	
	public NetworkDaoImpl() {
		conn = new ConnDB();
	}

	@Override
	public Network queryNetworkByStudentID(String studentID) {
		String sql = "select * from network where studentID = '"+ studentID+"'";
		System.out.println("queryNetworkByStudentID--------------"+sql);
		ResultSet rs = conn.queryDB(sql);
		Network network = null;
		try {
			if(rs.next()){
				network = new Network();
				network.setNetworkID(rs.getInt("networkID"));
				network.setRechargeMoney(rs.getDouble("rechargeMoney"));
				network.setRechargeTime(rs.getString("rechargeTime"));
				network.setRemainMoney(rs.getDouble("remainMoney"));
				network.setRemainTime(rs.getInt("remainTime"));
				network.setStudentID(rs.getString("studentID"));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}finally{
			conn.closeDB();
		}
		return network;
	}

}
