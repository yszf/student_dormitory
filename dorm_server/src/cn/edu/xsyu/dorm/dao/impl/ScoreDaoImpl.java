package cn.edu.xsyu.dorm.dao.impl;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import cn.edu.xsyu.dorm.dao.ScoreDao;
import cn.edu.xsyu.dorm.domain.Score;
import cn.edu.xsyu.dorm.utils.ConnDB;

public class ScoreDaoImpl implements ScoreDao {
	private ConnDB conn;
	
	public ScoreDaoImpl() {
		conn = new ConnDB();
	}

	@Override
	public List<Score> queryScoreByDormitoryID(Integer bdID) {
		String sql = "select * from score where bdID = "+bdID+" order by weekNum desc";
		System.out.println("queryScoreByDormitoryID-----------"+sql);
		ResultSet rs = conn.queryDB(sql);
		List<Score> list = new ArrayList<Score>();
		Score score = null;
		try {
			while(rs.next()){
				score = new Score();
				score.setDescription(rs.getString("description"));
				score.setBdID(rs.getInt("bdID"));
				score.setIsIllegal(rs.getInt("isIllegal"));
				score.setLevel(rs.getInt("level"));
				score.setScoreID(rs.getInt("scoreID"));
				score.setWeekNum(rs.getInt("weekNum"));
				list.add(score);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}finally{
			conn.closeDB();
		}
		return list;
	}

	@Override
	public Score queryScoreByID(Integer scoreID) {
		String sql = "select * from score where scoreID = "+scoreID;
		System.out.println("queryScoreByID-----------"+sql);
		ResultSet rs = conn.queryDB(sql);
		Score score = null;
		try {
			while(rs.next()){
				score = new Score();
				score.setDescription(rs.getString("description"));
				score.setBdID(rs.getInt("BdID"));
				score.setIsIllegal(rs.getInt("isIllegal"));
				score.setLevel(rs.getInt("level"));
				score.setScoreID(rs.getInt("scoreID"));
				score.setWeekNum(rs.getInt("weekNum"));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}finally{
			conn.closeDB();
		}
		return score;
	}

	@Override
	public Integer queryMaxWeekByBuildingID(int buildingID) {
		String sql = "select MAX(weekNum) AS M from score where bdID in "+
				"(SELECT builddorm.bdID FROM builddorm WHERE builddorm.buildingID = "
				+buildingID+")";
		System.out.println("queryMaxWeekByBuildingID-----------"+sql);
		ResultSet rs = conn.queryDB(sql);
		int weekNum = 0;
		try {
			if(rs.next()){
				weekNum = rs.getInt(1);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}finally{
			conn.closeDB();
		}
		return weekNum;
	}
	@Override
	public Integer queryMaxWeekByBdID(Integer bdID) {
		String sql = "select MAX(weekNum) AS M from score where bdID = "+bdID;
		System.out.println("queryMaxWeekByBdID-----------"+sql);
		ResultSet rs = conn.queryDB(sql);
		int weekNum = 0;
		try {
			if(rs.next()){
				weekNum = rs.getInt(1);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}finally{
			conn.closeDB();
		}
		return weekNum;
	}
	@Override
	public Integer insertScore(Score score) {
		String sql = "insert into score(weekNum,level,isIllegal,description,bdID)value("
				+score.getWeekNum()+","
				+score.getLevel()+","
				+score.getIsIllegal()+",'"
				+score.getDescription()+"',"
				+score.getBdID()+")";
		System.out.println("insertScore*********" + sql);
		return conn.updateDB(sql);
	}

}
