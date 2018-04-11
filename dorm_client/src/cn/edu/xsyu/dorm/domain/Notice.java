package cn.edu.xsyu.dorm.domain;

public class Notice {

	private Integer noticeID;
	private String time;
	private String title;
	private String content;
	private String issuer;


	public Integer getNoticeID() {
		return noticeID;
	}

	public void setNoticeID(int noticeID) {
		this.noticeID = noticeID;
	}

	public String getTime() {
		return time;
	}

	public void setTime(String time) {
		this.time = time;
	}


	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public String getIssuer() {
		return issuer;
	}

	public void setIssuer(String issurer) {
		this.issuer = issurer;
	}

	@Override
	public String toString() {
		return "Notice [noticeID=" + noticeID + ", time=" + time + ", title="
				+ title + ", content=" + content + ", issuer=" + issuer + "]";
	}

}