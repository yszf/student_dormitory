package cn.edu.xsyu.dorm.dao;

import java.util.List;

import cn.edu.xsyu.dorm.domain.Notice;

public interface NoticeDao {
	
	public Notice queryNoticeByID(Integer noticeID);
	
	public List<Notice> queryNotices();
	
	public List<Notice> queryNoticesByTime();
	
	public int changeNotcie(Notice notice);
	
	public int deleteNoticeByID(Integer noticeID);
}
