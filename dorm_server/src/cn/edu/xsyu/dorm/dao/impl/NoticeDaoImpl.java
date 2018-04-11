package cn.edu.xsyu.dorm.dao.impl;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import cn.edu.xsyu.dorm.dao.NoticeDao;
import cn.edu.xsyu.dorm.domain.Notice;
import cn.edu.xsyu.dorm.utils.ConnDB;

public class NoticeDaoImpl implements NoticeDao {

	private ConnDB conn;

	public NoticeDaoImpl() {
		conn = new ConnDB();
	}

	@Override
	public Notice queryNoticeByID(Integer noticeID) {
		String sql = "select * from notice where noticeID = " + noticeID;
		System.out.println("queryNotice-----------" + sql);
		ResultSet rs = conn.queryDB(sql);
		Notice notice = null;
		try {
			if (rs.next()) {
				notice = new Notice();
				notice.setContent(rs.getString("content"));
				notice.setIssuer(rs.getString("issuer"));
				notice.setNoticeID(rs.getInt("noticeID"));
				notice.setTime(rs.getString("time"));
				notice.setTitle(rs.getString("title"));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			conn.closeDB();
		}
		return notice;
	}

	@Override
	public List<Notice> queryNotices() {
		String sql = "select * from notice";
		System.out.println("queryNotices-----------" + sql);
		ResultSet rs = conn.queryDB(sql);
		List<Notice> list = new ArrayList<Notice>();
		Notice notice = null;
		try {
			while (rs.next()) {
				notice = new Notice();
				notice.setContent(rs.getString("content"));
				notice.setIssuer(rs.getString("issuer"));
				notice.setNoticeID(rs.getInt("noticeID"));
				notice.setTime(rs.getString("time"));
				notice.setTitle(rs.getString("title"));
				list.add(notice);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			conn.closeDB();
		}
		return list;
	}

	@Override
	public List<Notice> queryNoticesByTime() {
		String sql = "select * from notice order by time desc limit 0,3";
		System.out.println("queryNoticesByTime-----------" + sql);
		ResultSet rs = conn.queryDB(sql);
		List<Notice> list = new ArrayList<Notice>();
		Notice notice = null;
		try {
			while (rs.next()) {
				notice = new Notice();
				notice.setContent(rs.getString("content"));
				notice.setIssuer(rs.getString("issuer"));
				notice.setNoticeID(rs.getInt("noticeID"));
				notice.setTime(rs.getString("time"));
				notice.setTitle(rs.getString("title"));
				list.add(notice);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			conn.closeDB();
		}
		return list;
	}

	@Override
	public int changeNotcie(Notice notice) {
		String sql = "update notice set title = '"+notice.getTitle()
				+"',content = '"+notice.getContent()
				+"',issuer = '"+notice.getIssuer()
				+"',time = NOW()"
				+" where noticeID = "+notice.getNoticeID();
		System.out.println("changeNotcie-------------"+sql);
		return conn.updateDB(sql);
	}

	@Override
	public int deleteNoticeByID(Integer noticeID) {
		String sql = "delete from notice where noticeID = " + noticeID;
		System.out.println("deleteNoticeByID***********" + sql);
		return conn.updateDB(sql);
	}
	
}
