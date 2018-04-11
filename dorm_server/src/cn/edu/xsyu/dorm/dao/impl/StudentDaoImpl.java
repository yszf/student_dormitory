package cn.edu.xsyu.dorm.dao.impl;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import cn.edu.xsyu.dorm.dao.StudentDao;
import cn.edu.xsyu.dorm.domain.Student;
import cn.edu.xsyu.dorm.utils.ConnDB;

public class StudentDaoImpl implements StudentDao {
	private ConnDB conn;

	public StudentDaoImpl() {
		conn = new ConnDB();
	}

	@Override
	public Student queryStudentByID(String ID) {
		String sql = "select * from student where studentID = '" + ID + "'";
		System.out.println("queryStudentByID--------" + sql);
		ResultSet rs = conn.queryDB(sql);
		Student student = null;
		try {
			if (rs.next()) {
				student = new Student();
				student.setStudentID(rs.getString("studentID"));
				student.setPassword(rs.getString("password"));
				student.setName(rs.getString("name"));
				student.setSex(rs.getString("sex"));
				student.setBdID(rs.getInt("bdID"));
				student.setAcademy(rs.getString("academy"));
				student.setBedNum(rs.getInt("bedNum"));
				student.setClassName(rs.getString("className"));
				student.setEmail(rs.getString("email"));
				student.setPhone(rs.getString("phone"));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			conn.closeDB();
		}
		return student;
	}

	@Override
	public int updateStudent(Student student) {
		String sql = "update student set email = '" + student.getEmail()
				+ "',name = '" + student.getName() + "',sex = '"
				+ student.getSex() + "',phone = '" + student.getPhone()
				+ "',bdID = " + student.getBdID() + ",bedNum = "
				+ student.getBedNum() + " where studentID = '"+ student.getStudentID()+"'";
		System.out.println("updateStudent----------------" + sql);
		return conn.updateDB(sql);
	}

	@Override
	public int updateStudentPass(String studentID,String newPass) {
		String sql = "update student set password = '"+newPass+"' where studentID = '"+studentID+"'";
		System.out.println("updateStudentPass-------------"+sql);
		return conn.updateDB(sql);
	}

	@Override
	public List<Student> queryStudentsByBdID(Integer bdID) {
		String sql = "select * from student where bdID = "+bdID;
		System.out.println("queryStudentsByBdID---------"+sql);
		List<Student> list = new ArrayList<Student>();
		Student student = null;
		
		ResultSet rs = conn.queryDB(sql);
		try {
			while(rs.next()){
				student = new Student();
				student.setStudentID(rs.getString("studentID"));
				student.setPassword(rs.getString("password"));
				student.setName(rs.getString("name"));
				student.setSex(rs.getString("sex"));
				student.setBdID(rs.getInt("bdID"));
				student.setAcademy(rs.getString("academy"));
				student.setBedNum(rs.getInt("bedNum"));
				student.setClassName(rs.getString("className"));
				student.setEmail(rs.getString("email"));
				student.setPhone(rs.getString("phone"));
				list.add(student);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}finally{
			conn.closeDB();
		}
		return list;
	}

	@Override
	public Student queryStudentByEmail(String email) {
		String sql = "select * from student where email = '" + email + "'";
		System.out.println("queryStudentByEmail--------" + sql);
		ResultSet rs = conn.queryDB(sql);
		Student student = null;
		try {
			if (rs.next()) {
				student = new Student();
				student.setStudentID(rs.getString("studentID"));
				student.setPassword(rs.getString("password"));
				student.setName(rs.getString("name"));
				student.setSex(rs.getString("sex"));
				student.setBdID(rs.getInt("bdID"));
				student.setAcademy(rs.getString("academy"));
				student.setBedNum(rs.getInt("bedNum"));
				student.setClassName(rs.getString("className"));
				student.setEmail(rs.getString("email"));
				student.setPhone(rs.getString("phone"));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			conn.closeDB();
		}
		return student;
	}

	@Override
	public int updateStudentPass2(String email, String newPass) {
		String sql = "update student set password = '"+newPass+"' where email = '"+email+"'";
		System.out.println("updateStudentPass2-------------"+sql);
		return conn.updateDB(sql);
	}

}
