package cn.edu.xsyu.dorm.dao;

import java.util.List;

import cn.edu.xsyu.dorm.domain.Reply;

public interface ReplyDao {
	
	public Reply queryReplyByID(Integer replyID);
	
	public List<Reply> queryReplys(Integer messageID);
	
	public int insertReply(Reply reply);
}
