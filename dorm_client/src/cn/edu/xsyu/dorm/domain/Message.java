package cn.edu.xsyu.dorm.domain;

public class Message {

	private Integer messageID;

	private String content;

	private String time;

	private String studentID;


	public Integer getMessageID() {
		return messageID;
	}

	public void setMessageID(Integer messageID) {
		this.messageID = messageID;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public String getTime() {
		return time;
	}

	public void setTime(String time) {
		this.time = time;
	}

	public String getStudentID() {
		return studentID;
	}

	public void setStudentID(String studentID) {
		this.studentID = studentID;
	}

	@Override
	public String toString() {
		return "Message [messageID=" + messageID + ", content=" + content
				+ ", time=" + time + ", studentID=" + studentID + "]";
	}

}