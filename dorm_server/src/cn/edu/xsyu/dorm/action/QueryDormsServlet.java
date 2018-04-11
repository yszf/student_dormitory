package cn.edu.xsyu.dorm.action;

import java.io.IOException;
import java.io.OutputStream;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONObject;
import cn.edu.xsyu.dorm.dao.impl.BuildDormDaoImpl;
import cn.edu.xsyu.dorm.dao.impl.ScoreDaoImpl;
import cn.edu.xsyu.dorm.dao.impl.StudentDaoImpl;
import cn.edu.xsyu.dorm.domain.BuildDorm;
import cn.edu.xsyu.dorm.domain.Student;
import cn.edu.xsyu.dorm.utils.JsonTools;

public class QueryDormsServlet extends HttpServlet {

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
		int level = recvJo.getInt("level");
		String ID = recvJo.getString("ID");
		
		StudentDaoImpl studentDaoImpl = new StudentDaoImpl();
		Student student = studentDaoImpl.queryStudentByID(ID);
		
		BuildDormDaoImpl buildDormDaoImpl = new BuildDormDaoImpl();
		BuildDorm buildDorm = buildDormDaoImpl.queryBuildDormByID(student.getBdID());	
		List<BuildDorm> buildDorms = buildDormDaoImpl.queryBuildDorms(buildDorm.getBuildingID(),level);
		
		
		ScoreDaoImpl scoreDaoImpl = new ScoreDaoImpl();
		int weekNum = scoreDaoImpl.queryMaxWeekByBuildingID(buildDorm.getBuildingID());
		
		JSONObject sendObject = new JSONObject();
		sendObject.put("buildDorms",buildDorms);
		sendObject.put("buildingID", buildDorm.getBuildingID());
		sendObject.put("weekNum", weekNum);
		
		String sendJS = String.valueOf(sendObject);
		System.out.println("=======json is===========" + sendJS);
		OutputStream os = response.getOutputStream();
		os.write(sendJS.getBytes());
	}

}
