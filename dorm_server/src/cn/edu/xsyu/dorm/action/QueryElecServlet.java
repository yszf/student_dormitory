package cn.edu.xsyu.dorm.action;

import java.io.IOException;
import java.io.OutputStream;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONObject;
import cn.edu.xsyu.dorm.dao.impl.BuildDormDaoImpl;
import cn.edu.xsyu.dorm.dao.impl.ElectricityDaoImpl;
import cn.edu.xsyu.dorm.dao.impl.StudentDaoImpl;
import cn.edu.xsyu.dorm.domain.BuildDorm;
import cn.edu.xsyu.dorm.domain.Electricity;
import cn.edu.xsyu.dorm.domain.Student;
import cn.edu.xsyu.dorm.utils.JsonTools;

public class QueryElecServlet extends HttpServlet {

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
		
		StudentDaoImpl studentDaoImpl = new StudentDaoImpl();
		Student student = studentDaoImpl.queryStudentByID(ID);
		
		BuildDormDaoImpl buildDormDaoImpl = new BuildDormDaoImpl();
		BuildDorm buildDorm = buildDormDaoImpl.queryBuildDormByID(student.getBdID());
		
		ElectricityDaoImpl electricityDaoImpl = new ElectricityDaoImpl();
		Electricity electricity = electricityDaoImpl.queryElectrictyByBdID(student.getBdID());
		
		JSONObject sendObject = new JSONObject();
		sendObject.put("buildingID", buildDorm.getBuildingID());
		sendObject.put("dormitoryID", buildDorm.getDormitoryID());
		sendObject.put("remainElec", electricity.getRemainElec());
		sendObject.put("remainMoney", electricity.getRemainMoney());
		sendObject.put("rechargeMoney", electricity.getRechargeMoney());
		sendObject.put("rechargeTime", electricity.getRechargeTime());
		
		String sendJS = String.valueOf(sendObject);
		System.out.println("=======json is===========" + sendJS);
		OutputStream os = response.getOutputStream();
		os.write(sendJS.getBytes());
	}

}
