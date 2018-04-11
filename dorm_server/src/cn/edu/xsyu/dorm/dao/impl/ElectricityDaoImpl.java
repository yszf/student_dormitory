package cn.edu.xsyu.dorm.dao.impl;

import java.sql.ResultSet;
import java.sql.SQLException;

import cn.edu.xsyu.dorm.dao.ElectricityDao;
import cn.edu.xsyu.dorm.domain.Electricity;
import cn.edu.xsyu.dorm.utils.ConnDB;

public class ElectricityDaoImpl implements ElectricityDao {
	private ConnDB conn;
	
	public ElectricityDaoImpl() {
		conn = new ConnDB();
	}

	@Override
	public Electricity queryElectrictyByBdID(Integer bdID) {
		String sql = "select * from electricity where bdID = "+bdID;
		System.out.println("queryElectrictyByDormitoryID--------------"+sql);
		ResultSet rs = conn.queryDB(sql);
		Electricity elec = null;
		try {
			if(rs.next()){
				elec = new Electricity();
				elec.setBdID(rs.getInt("bdID"));
				elec.setElecID(rs.getInt("elecID"));
				elec.setRechargeMoney(rs.getDouble("rechargeMoney"));
				elec.setRechargeTime(rs.getString("rechargeTime"));
				elec.setRemainElec(rs.getDouble("remainElec"));
				elec.setRemainMoney(rs.getDouble("remainMoney"));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}finally{
			conn.closeDB();
		}
		return elec;
	}

}
