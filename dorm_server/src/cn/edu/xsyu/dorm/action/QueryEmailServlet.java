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

public class QueryEmailServlet extends HttpServlet {

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
			String role = recvJo.getString("role");
			String email = recvJo.getString("email");
			if(role.equals("student")){
				StudentDaoImpl studentDaoImpl = new StudentDaoImpl();
				Student student = studentDaoImpl.queryStudentByEmail(email);
				System.out.println("student==============="+student);
				if(student == null){
					sendJS = JsonTools.createJsonString("res", "fail");
				}
				else{
					sendJS = JsonTools.createJsonString("res", "ok");
				}
			}else if(role.equals("buildingManager")){
				BuildingManagerDaoImpl buildingManagerDaoImpl = new BuildingManagerDaoImpl();
				BuildingManager buildingManager = buildingManagerDaoImpl.queryBuildingManagerByEmail(email);
				System.out.println("buildingManager==============="+buildingManager);
				if(buildingManager == null){
					sendJS = JsonTools.createJsonString("res", "fail");
				}
				else{
					sendJS = JsonTools.createJsonString("res", "ok");
				}
			}else if(role.equals("serviceMan")){
				ServiceManDaoImpl serviceManDaoImpl = new ServiceManDaoImpl();
				ServiceMan serviceMan = serviceManDaoImpl.queryServiceManByEmail(email);
				System.out.println("buildingManager==============="+serviceMan);
				if(serviceMan == null){
					sendJS = JsonTools.createJsonString("res", "fail");
				}
				else{
					sendJS = JsonTools.createJsonString("res", "ok");
				}
			}
		} else {
			sendJS = JsonTools.createJsonString("res", "fail");
		}
		os.write(sendJS.getBytes());
	}

}
