package cn.edu.xsyu.dorm.action;

import java.io.IOException;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONObject;
import cn.edu.xsyu.dorm.dao.impl.BuildDormDaoImpl;
import cn.edu.xsyu.dorm.dao.impl.RepairDaoImpl;
import cn.edu.xsyu.dorm.domain.BuildDorm;
import cn.edu.xsyu.dorm.domain.Repair;

public class QueryRepairServlet extends HttpServlet {

	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		doPost(request, response);
	}

	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		request.setCharacterEncoding("utf-8");
		response.setContentType("text/json;utf-8");
		
		BuildDormDaoImpl buildDormDaoImpl = new BuildDormDaoImpl();
		
		RepairDaoImpl repairDaoImpl = new RepairDaoImpl();
		List<Repair> repairs = repairDaoImpl.queryRepairs();
		
		List<BuildDorm> buildDorms = new ArrayList<BuildDorm>();
		for (Repair repair : repairs) {
			BuildDorm buildDorm = buildDormDaoImpl.queryBuildDormByID(repair.getBdID());
			buildDorms.add(buildDorm);
		}
		
		JSONObject sendObject = new JSONObject();
		sendObject.put("repairs",repairs);
		sendObject.put("buildDorms", buildDorms);

		String sendJS = String.valueOf(sendObject);
		System.out.println("=======json is===========" + sendJS);
		OutputStream os = response.getOutputStream();
		os.write(sendJS.getBytes());
	}

}
