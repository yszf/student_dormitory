package cn.edu.xsyu.dorm.action;

import java.io.IOException;
import java.io.OutputStream;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONObject;
import cn.edu.xsyu.dorm.dao.impl.RepairDaoImpl;
import cn.edu.xsyu.dorm.dao.impl.StudentDaoImpl;
import cn.edu.xsyu.dorm.domain.Repair;
import cn.edu.xsyu.dorm.domain.Student;
import cn.edu.xsyu.dorm.utils.JsonTools;

public class RepairAddServlet extends HttpServlet {

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
		
		RepairDaoImpl repairDaoImpl = new RepairDaoImpl();
		Repair repair = new Repair();
		repair.setBdID(student.getBdID());
		repair.setIssuer(student.getName());
		repair.setContacts(recvJo.getString("contacts"));
		repair.setReason(recvJo.getString("reason"));
		repair.setDealState(1);
		repair.setServicemanID("3001");
		
		String sendJS = null;
		OutputStream os = response.getOutputStream();
		
		if(repairDaoImpl.insertRepair(repair) < 1){
			sendJS = JsonTools.createJsonString("res", "fail");
			os.write(sendJS.getBytes());
			return;
		}
		
		sendJS = JsonTools.createJsonString("res", "ok");
		os.write(sendJS.getBytes());
	}

}
