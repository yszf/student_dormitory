package cn.edu.xsyu.dorm.dao.impl;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import cn.edu.xsyu.dorm.dao.MessageDao;
import cn.edu.xsyu.dorm.domain.Message;
import cn.edu.xsyu.dorm.utils.ConnDB;

public class MessageDaoImpl implements MessageDao {

	private ConnDB conn;

	public MessageDaoImpl() {
		conn = new ConnDB();
	}

	@Override
	public Message queryMessageByID(Integer messageID) {
		String sql = "select * from message where messageID = " + messageID;
		System.out.println("queryMessageByID-----------" + sql);
		ResultSet rs = conn.queryDB(sql);
		Message message = null;
		try {
			if (rs.next()) {
				message = new Message();
				message.setStudentID(rs.getString("studentID"));
				message.setContent(rs.getString("content"));
				message.setMessageID(rs.getInt("messageID"));
				message.setTime(rs.getString("time"));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			conn.closeDB();
		}
		return message;
	}

	@Override
	public List<Message> queryMessages() {
		String sql = "select * from message order by time desc";
		System.out.println("queryMessages-----------" + sql);
		ResultSet rs = conn.queryDB(sql);
		List<Message> list = new ArrayList<Message>();
		Message message = null;
		try {
			while (rs.next()) {
				message = new Message();
				message.setStudentID(rs.getString("studentID"));
				message.setContent(rs.getString("content"));
				message.setMessageID(rs.getInt("messageID"));
				message.setTime(rs.getString("time"));
				list.add(message);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			conn.closeDB();
		}
		return list;
	}

	@Override
	public int insertMessage(Message message) {
		String sql = "insert into message(content,time,studentID)value('"
				+message.getContent()+"',NOW(),'"
				+message.getStudentID()+"');";
		System.out.println("insertMessage*********" + sql);
		return conn.updateDB(sql);
	}

	@Override
	public int deleteMessageByID(Integer messageID) {
		String sql1 = "delete from reply where messageID = " + messageID;
		System.out.println("deleteReplyByID***********" + sql1);
		conn.updateDB(sql1);
		
		String sql2 = "delete from message where messageID = " + messageID;
		System.out.println("deleteMessageByID***********" + sql2);
		
		return conn.updateDB(sql2);
	}
}
