package cn.edu.xsyu.dorm.action;

import java.io.IOException;
import java.io.OutputStream;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONObject;
import cn.edu.xsyu.dorm.dao.impl.BuildDormDaoImpl;
import cn.edu.xsyu.dorm.dao.impl.DormitoryDaoImpl;
import cn.edu.xsyu.dorm.dao.impl.StudentDaoImpl;
import cn.edu.xsyu.dorm.domain.BuildDorm;
import cn.edu.xsyu.dorm.domain.Dormitory;
import cn.edu.xsyu.dorm.domain.Student;
import cn.edu.xsyu.dorm.utils.JsonTools;

public class UpdateStuInfoServlet extends HttpServlet {

	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		doPost(request,response);
	}

	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		request.setCharacterEncoding("utf-8");
		response.setContentType("text/json;utf-8");
		
		String recvJS = JsonTools.getTextFromStream(request.getInputStream());
		System.out.println("=======json is==========="+recvJS);
		JSONObject recvJO = JSONObject.fromObject(recvJS);
		
		String sendJS = null;
		OutputStream os = response.getOutputStream();

		int dormitoryID = recvJO.getInt("dormitoryID");
		DormitoryDaoImpl dormitoryDaoImpl = new DormitoryDaoImpl();
		Dormitory dormitory = dormitoryDaoImpl.queryDormitory(dormitoryID);
		System.out.println(dormitory);
		if(dormitory == null)
		{
			sendJS = JsonTools.createJsonString("res", "fail");
			os.write(sendJS.getBytes());
			return;
		}
		
		dormitory.setRoomheader(recvJO.getString("roomheader"));
		if(dormitoryDaoImpl.updateDormitory(dormitory) < 1){
			sendJS = JsonTools.createJsonString("res", "fail");
			os.write(sendJS.getBytes());
			return;
		}
		
		String studentID = recvJO.getString("studentID");
		StudentDaoImpl studentDaoImpl = new StudentDaoImpl();
		Student student = studentDaoImpl.queryStudentByID(studentID);
		student.setName(recvJO.getString("name"));
		student.setSex(recvJO.getString("sex"));
		student.setEmail(recvJO.getString("email"));
		student.setPhone(recvJO.getString("phone"));
		student.setBedNum(recvJO.getInt("bedNum"));
		if(studentDaoImpl.updateStudent(student) < 1){
			sendJS = JsonTools.createJsonString("res", "fail");
			os.write(sendJS.getBytes());
			return;
		}
		
		BuildDormDaoImpl buildDormDaoImpl = new BuildDormDaoImpl();
		BuildDorm buildDorm = new BuildDorm();
		buildDorm.setBdID(student.getBdID());
		buildDorm.setBuildingID(recvJO.getInt("buildingID"));
		buildDorm.setDormitoryID(recvJO.getInt("dormitoryID"));
		if(buildDormDaoImpl.updateBuildDorm(buildDorm) < 1){
			sendJS = JsonTools.createJsonString("res", "fail");
			os.write(sendJS.getBytes());
			return;
		}
		
		sendJS = JsonTools.createJsonString("res", "ok");
		os.write(sendJS.getBytes());
	}

}
