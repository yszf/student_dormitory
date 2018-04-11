package cn.edu.xsyu.dorm.dao.impl;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import cn.edu.xsyu.dorm.dao.DormRuleDao;
import cn.edu.xsyu.dorm.domain.DormRule;
import cn.edu.xsyu.dorm.utils.ConnDB;

public class DormRuleDaoImpl implements DormRuleDao {

	private ConnDB conn;

	public DormRuleDaoImpl() {
		conn = new ConnDB();
	}
	@Override
	public DormRule queryDormRuleByID(Integer ruleID) {
		String sql = "select * from dormrule where ruleID = " + ruleID;
		System.out.println("queryDormRuleByID-----------" + sql);
		ResultSet rs = conn.queryDB(sql);
		DormRule dormRule = null;
		try {
			if (rs.next()) {
				dormRule = new DormRule();
				dormRule.setContent(rs.getString("content"));
				dormRule.setIssuer(rs.getString("issuer"));
				dormRule.setRuleID(rs.getInt("ruleID"));
				dormRule.setTime(rs.getString("time"));
				dormRule.setTitle(rs.getString("title"));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			conn.closeDB();
		}
		return dormRule;
	}

	@Override
	public List<DormRule> queryDormRules() {
		String sql = "select * from dormrule";
		System.out.println("queryDormRules-----------" + sql);
		ResultSet rs = conn.queryDB(sql);
		List<DormRule> list = new ArrayList<DormRule>();
		DormRule dormRule = null;
		try {
			while (rs.next()) {
				dormRule = new DormRule();
				dormRule.setContent(rs.getString("content"));
				dormRule.setIssuer(rs.getString("issuer"));
				dormRule.setRuleID(rs.getInt("ruleID"));
				dormRule.setTime(rs.getString("time"));
				dormRule.setTitle(rs.getString("title"));
				list.add(dormRule);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			conn.closeDB();
		}
		return list;
	}

}
