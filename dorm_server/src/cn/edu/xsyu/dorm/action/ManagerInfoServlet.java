package cn.edu.xsyu.dorm.action;

import java.io.IOException;
import java.io.OutputStream;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONObject;
import cn.edu.xsyu.dorm.dao.impl.BuildingManagerDaoImpl;
import cn.edu.xsyu.dorm.domain.BuildingManager;
import cn.edu.xsyu.dorm.utils.JsonTools;

public class ManagerInfoServlet extends HttpServlet {

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
		JSONObject recvJo = JSONObject.fromObject(recvJS);
		String ID = recvJo.getString("ID");
		
		BuildingManagerDaoImpl buildingManagerDaoImpl = new BuildingManagerDaoImpl();
		BuildingManager buildingManager = buildingManagerDaoImpl.queryBuildingManagerByID(ID);
		
		JSONObject sendObject = new JSONObject();
		sendObject.put("buildingManagerID", buildingManager.getBuildingManagerID());
		sendObject.put("name", buildingManager.getName());
		sendObject.put("email", buildingManager.getEmail());
		sendObject.put("time", buildingManager.getTime());
		sendObject.put("buildingID", buildingManager.getBuildingID());
		
		String sendJS = String.valueOf(sendObject);
		System.out.println("=======json is===========" + sendJS);
		OutputStream os = response.getOutputStream();
		os.write(sendJS.getBytes());
	}

}
