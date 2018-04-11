package cn.edu.xsyu.dorm.action;

import java.io.IOException;
import java.io.OutputStream;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONObject;
import cn.edu.xsyu.dorm.dao.impl.RepairDaoImpl;
import cn.edu.xsyu.dorm.domain.Repair;
import cn.edu.xsyu.dorm.utils.JsonTools;

public class ChangeRepairServlet extends HttpServlet {

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
		
		int repairID = recvJO.getInt("repairID");
		String ID = recvJO.getString("ID");
		int state = recvJO.getInt("state");
		
		RepairDaoImpl repairDaoImpl = new RepairDaoImpl();
		Repair repair = new Repair();
		repair.setDealState(state);
		repair.setRepairID(repairID);
		repair.setServicemanID(ID);
		
		if(repairDaoImpl.changeRepair(repair) < 1){
			sendJS = JsonTools.createJsonString("res", "fail");
		}else{
			sendJS = JsonTools.createJsonString("res", "ok");
		}
		os.write(sendJS.getBytes());
	}

}
