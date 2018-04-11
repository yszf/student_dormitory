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

public class StuInfoServlet extends HttpServlet {

	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

	}

	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		request.setCharacterEncoding("utf-8");
		response.setContentType("text/json;utf-8");

		String recvJS = JsonTools.getTextFromStream(request.getInputStream());
		System.out.println("=======json is===========" + recvJS);
		JSONObject recvJo = JSONObject.fromObject(recvJS);
		String ID = recvJo.getString("ID");
		
		StudentDaoImpl studentDaoImpl = new StudentDaoImpl();
		Student student = studentDaoImpl.queryStudentByID(ID);
		
		BuildDormDaoImpl buildDormDaoImpl = new BuildDormDaoImpl();
		BuildDorm buildDorm = buildDormDaoImpl.queryBuildDormByID(student.getBdID());
		
		int dormitoryID = buildDorm.getDormitoryID();
		DormitoryDaoImpl dormitoryDaoImpl = new DormitoryDaoImpl();
		Dormitory dormitory = dormitoryDaoImpl.queryDormitory(dormitoryID);
		
		JSONObject sendObject = new JSONObject();
		sendObject.put("studentID", student.getStudentID());
		sendObject.put("name", student.getName());
		sendObject.put("sex", student.getSex());
		sendObject.put("email", student.getEmail());
		sendObject.put("phone", student.getPhone());
		sendObject.put("bedNum", student.getBedNum());
		sendObject.put("academy", student.getAcademy());
		sendObject.put("className", student.getClassName());
		sendObject.put("dormitoryID", dormitory.getDormitoryID());
		sendObject.put("buildingID", buildDorm.getBuildingID());
		sendObject.put("roomheader", dormitory.getRoomheader());
		
		String sendJS = String.valueOf(sendObject);
		System.out.println("=======json is===========" + sendJS);
		OutputStream os = response.getOutputStream();

		os.write(sendJS.getBytes());
	}

}
