package cn.edu.xsyu.dorm.dao.impl;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import cn.edu.xsyu.dorm.dao.ReplyDao;
import cn.edu.xsyu.dorm.domain.Reply;
import cn.edu.xsyu.dorm.utils.ConnDB;

public class ReplyDaoImpl implements ReplyDao {

	private ConnDB conn;

	public ReplyDaoImpl() {
		conn = new ConnDB();
	}

	@Override
	public Reply queryReplyByID(Integer replyID) {
		String sql = "select * from reply where replyID = " + replyID;
		System.out.println("queryReplyByID-----------" + sql);
		ResultSet rs = conn.queryDB(sql);
		Reply reply = null;
		try {
			if (rs.next()) {
				reply = new Reply();
				reply.setReplyID(rs.getInt("replyID"));
				reply.setTime(rs.getString("time"));
				reply.setMessageID(rs.getInt("messageID"));
				reply.setRepContent(rs.getString("repContent"));
				reply.setReplier(rs.getString("replier"));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			conn.closeDB();
		}
		return reply;
	}

	public List<Reply> queryReplys(Integer messageID) {
		String sql = "select * from reply where messageID = "+messageID+" order by time desc";
		System.out.println("queryreplys-----------" + sql);
		ResultSet rs = conn.queryDB(sql);
		List<Reply> list = new ArrayList<Reply>();
		Reply reply = null;
		try {
			while (rs.next()) {
				reply = new Reply();
				reply.setReplyID(rs.getInt("replyID"));
				reply.setTime(rs.getString("time"));
				reply.setMessageID(rs.getInt("messageID"));
				reply.setRepContent(rs.getString("content"));
				reply.setReplier(rs.getString("replier"));
				list.add(reply);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			conn.closeDB();
		}
		return list;
	}

	@Override
	public int insertReply(Reply reply) {
		String sql = "insert into reply(content,time,replier,messageID)value('"
				+reply.getRepContent()
				+"',NOW(),'"
				+reply.getReplier()+"',"
				+reply.getMessageID()+");";
		System.out.println("insertReply*********" + sql);
		return conn.updateDB(sql);
	}
}
