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
import cn.edu.xsyu.dorm.utils.JsonTools;

public class ChangepwdServlet2 extends HttpServlet {

	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		doPost(request, response);
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
		
		String role = recvJO.getString("role");
		String email = recvJO.getString("email");
		String newpwd = recvJO.getString("newpwd");
		
		if(role.equals("student")){
			StudentDaoImpl studentDaoImpl = new StudentDaoImpl();
			if(studentDaoImpl.updateStudentPass2(email, newpwd) < 1){
				sendJS = JsonTools.createJsonString("res", "fail");
				os.write(sendJS.getBytes());
				return;
			}
		}else if(role.equals("buildingManager")){
			BuildingManagerDaoImpl managerDaoImpl = new BuildingManagerDaoImpl();
			
			if(managerDaoImpl.updateBuildingManagerPass2(email, newpwd) < 1){
				sendJS = JsonTools.createJsonString("res", "fail");
				os.write(sendJS.getBytes());
				return;
			}
		}else if(role.equals("serviceMan")){
			ServiceManDaoImpl serviceManDaoImpl = new ServiceManDaoImpl();
			
			if(serviceManDaoImpl.updateServiceManPass2(email, newpwd) < 1){
				sendJS = JsonTools.createJsonString("res", "fail");
				os.write(sendJS.getBytes());
				return;
			}
		}
		
		sendJS = JsonTools.createJsonString("res", "ok");
		os.write(sendJS.getBytes());
	}

}
