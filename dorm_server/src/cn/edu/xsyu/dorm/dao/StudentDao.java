package cn.edu.xsyu.dorm.dao;

import java.util.List;

import cn.edu.xsyu.dorm.domain.Student;

public interface StudentDao {
	
	public Student queryStudentByID(String ID);
	
	public Student queryStudentByEmail(String email);
	
	public int updateStudent(Student student);
	
	public int updateStudentPass(String studentID,String newPass);
	
	public int updateStudentPass2(String email,String newPass);
	
	public List<Student> queryStudentsByBdID(Integer bdID);
	
}
