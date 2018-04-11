package cn.edu.xsyu.dorm.action;

import java.io.IOException;
import java.io.OutputStream;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONObject;
import cn.edu.xsyu.dorm.dao.impl.ServiceManDaoImpl;
import cn.edu.xsyu.dorm.domain.ServiceMan;
import cn.edu.xsyu.dorm.utils.JsonTools;

public class ServiceManInfoServlet extends HttpServlet {

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
		
		ServiceManDaoImpl serviceManDaoImpl = new ServiceManDaoImpl();
		ServiceMan serviceMan = serviceManDaoImpl.queryServiceManByID(ID);
		
		JSONObject sendObject = new JSONObject();
		sendObject.put("serviceManID", serviceMan.getServiceManID());
		sendObject.put("name", serviceMan.getName());
		sendObject.put("email", serviceMan.getEmail());
		sendObject.put("time", serviceMan.getTime());
		
		String sendJS = String.valueOf(sendObject);
		System.out.println("=======json is===========" + sendJS);
		OutputStream os = response.getOutputStream();
		os.write(sendJS.getBytes());
	}

}
