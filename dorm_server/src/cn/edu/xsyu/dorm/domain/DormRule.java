package cn.edu.xsyu.dorm.domain;

public class DormRule {
	private Integer ruleID;
	private String title;
	private String content;
	private String time;
	private String issuer;

	public Integer getRuleID() {
		return ruleID;
	}

	public void setRuleID(Integer ruleID) {
		this.ruleID = ruleID;
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

	public String getTime() {
		return time;
	}

	public void setTime(String time) {
		this.time = time;
	}

	public String getIssuer() {
		return issuer;
	}

	public void setIssuer(String issuer) {
		this.issuer = issuer;
	}
	
}
