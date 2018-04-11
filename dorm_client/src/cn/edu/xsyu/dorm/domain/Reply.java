package cn.edu.xsyu.dorm.domain;

public class Reply {
	private Integer replyID;
	private String repContent;
	private String time;
	private Integer messageID;
	private String replier;

	public Integer getReplyID() {
		return replyID;
	}

	public void setReplyID(Integer replyID) {
		this.replyID = replyID;
	}

	public String getRepContent() {
		return repContent;
	}

	public void setRepContent(String repContent) {
		this.repContent = repContent;
	}

	public String getTime() {
		return time;
	}

	public void setTime(String time) {
		this.time = time;
	}

	public Integer getMessageID() {
		return messageID;
	}

	public void setMessageID(Integer messageID) {
		this.messageID = messageID;
	}

	public String getReplier() {
		return replier;
	}

	public void setReplier(String replier) {
		this.replier = replier;
	}

	@Override
	public String toString() {
		return "Reply [replyID=" + replyID + ", repContent=" + repContent
				+ ", time=" + time + ", messageID=" + messageID + ", replier="
				+ replier + "]";
	}
	
}
