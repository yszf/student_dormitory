package cn.edu.xsyu.dorm.action;

import java.io.IOException;
import java.io.OutputStream;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONObject;
import cn.edu.xsyu.dorm.dao.impl.BuildingManagerDaoImpl;
import cn.edu.xsyu.dorm.dao.impl.ServiceManDaoImpl;
import cn.edu.xsyu.dorm.dao.impl.StudentDaoImpl;
import cn.edu.xsyu.dorm.domain.BuildingManager;
import cn.edu.xsyu.dorm.domain.ServiceMan;
import cn.edu.xsyu.dorm.domain.Student;
import cn.edu.xsyu.dorm.utils.JsonTools;

public class LoginServlet extends HttpServlet {

	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		doPost(request, response);
	}

	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		request.setCharacterEncoding("utf-8");
		response.setContentType("text/json;utf-8");

		String recvJS = JsonTools.getTextFromStream(request.getInputStream());
		System.out.println("=======json is===========" + recvJS);

		JSONObject recvJo = null;
		String sendJS = null;
		OutputStream os = response.getOutputStream();
		if (recvJS != null && !recvJS.equals("")) {
			recvJo = JSONObject.fromObject(recvJS);
			String ID = recvJo.getString("ID");
			String pass = recvJo.getString("password");
			String role = recvJo.getString("role");
			System.out.println("ID:" + ID + ",password:" + pass+",role:"+role);

			if(role.equals("student")){
				StudentDaoImpl studentDaoImpl = new StudentDaoImpl();
				Student student = studentDaoImpl.queryStudentByID(ID);
				if(student == null){
					sendJS = JsonTools.createJsonString("res", "ID is not exist");
				}else if(!student.getPassword().equals(pass)){
					sendJS = JsonTools.createJsonString("res", "password is wrong");
				}
				else {
					sendJS = JsonTools.createJsonString("res", "ok");
				}
			}else if(role.equals("buildingManager")){
				BuildingManagerDaoImpl buildingManagerDaoImpl = new BuildingManagerDaoImpl();
				BuildingManager buildingManager = buildingManagerDaoImpl.queryBuildingManagerByID(ID);
				if(buildingManager == null){
					sendJS = JsonTools.createJsonString("res", "ID is not exist");
				}else if(!buildingManager.getPassword().equals(pass)){
					sendJS = JsonTools.createJsonString("res", "password is wrong");
				}
				else {
					sendJS = JsonTools.createJsonString("res", "ok");
				}
			}else if(role.equals("serviceMan")){
				ServiceManDaoImpl serviceManDaoImpl = new ServiceManDaoImpl();
				ServiceMan serviceMan = serviceManDaoImpl.queryServiceManByID(ID);
				if(serviceMan == null){
					sendJS = JsonTools.createJsonString("res", "ID is not exist");
				}else if(!serviceMan.getPassword().equals(pass)){
					sendJS = JsonTools.createJsonString("res", "password is wrong");
				}
				else {
					sendJS = JsonTools.createJsonString("res", "ok");
				}
			}
		} else {
			sendJS = JsonTools.createJsonString("res", "fail");
		}
		os.write(sendJS.getBytes());
	}

}
