package cn.edu.xsyu.dorm.dao;

import java.util.List;

import cn.edu.xsyu.dorm.domain.Message;

public interface MessageDao {
	
	public Message queryMessageByID(Integer messageID);
	
	public List<Message> queryMessages();
	
	public int insertMessage(Message message);
	
	public int deleteMessageByID(Integer messageID);
	
}
