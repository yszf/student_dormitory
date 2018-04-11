package cn.edu.xsyu.dorm.domain;

public class Student {
	private String studentID;
	private String password;
	private String name;
	private String sex;
	private Integer bdID;
	private Integer bedNum;
	private String className;
	private String academy;
	private String email;
	private String phone;

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public Integer getBedNum() {
		return bedNum;
	}

	public void setBedNum(Integer bedNum) {
		this.bedNum = bedNum;
	}

	public String getClassName() {
		return className;
	}

	public void setClassName(String className) {
		this.className = className;
	}

	public String getStudentID() {
		return studentID;
	}

	public void setStudentID(String studentID) {
		this.studentID = studentID;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getSex() {
		return sex;
	}

	public void setSex(String sex) {
		this.sex = sex;
	}

	public Integer getBdID() {
		return bdID;
	}

	public void setBdID(Integer bdID) {
		this.bdID = bdID;
	}

	public String getAcademy() {
		return academy;
	}

	public void setAcademy(String academy) {
		this.academy = academy;
	}

	@Override
	public String toString() {
		return "Student [studentID=" + studentID + ", password=" + password
				+ ", name=" + name + ", sex=" + sex + ", bdID=" + bdID
				+ ", bedNum=" + bedNum + ", className=" + className
				+ ", academy=" + academy + ", email=" + email + ", phone="
				+ phone + "]";
	}

}
