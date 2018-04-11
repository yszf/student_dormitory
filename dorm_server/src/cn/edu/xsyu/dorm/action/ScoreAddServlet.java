package cn.edu.xsyu.dorm.action;

import java.io.IOException;
import java.io.OutputStream;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONObject;
import cn.edu.xsyu.dorm.dao.impl.BuildDormDaoImpl;
import cn.edu.xsyu.dorm.dao.impl.ScoreDaoImpl;
import cn.edu.xsyu.dorm.domain.BuildDorm;
import cn.edu.xsyu.dorm.domain.Score;
import cn.edu.xsyu.dorm.utils.JsonTools;

public class ScoreAddServlet extends HttpServlet {

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
		int buildingID = Integer.parseInt(recvJo.getString("buildingID"));
		int dormitoryID = Integer.parseInt(recvJo.getString("dormitoryID"));
		
		BuildDormDaoImpl buildDormDaoImpl = new BuildDormDaoImpl();
		BuildDorm buildDorm = buildDormDaoImpl.queryBuildDormByOther(buildingID, dormitoryID);
		
		int level = recvJo.getInt("level");
		int isIllegal = recvJo.getInt("isIllegal");
		String description = recvJo.getString("description");
		
		ScoreDaoImpl scoreDaoImpl = new ScoreDaoImpl();
		int weekNum = scoreDaoImpl.queryMaxWeekByBdID(buildDorm.getBdID());
		
		Score score = new Score();
		score.setBdID(buildDorm.getBdID());
		score.setDescription(description);
		score.setLevel(level);
		score.setIsIllegal(isIllegal);
		score.setWeekNum(weekNum+1);
		
		String sendJS = null;
		OutputStream os = response.getOutputStream();
		
		if(scoreDaoImpl.insertScore(score) < 1){
			sendJS = JsonTools.createJsonString("res", "fail");
			os.write(sendJS.getBytes());
			return;
		}
		
		sendJS = JsonTools.createJsonString("res", "ok");
		os.write(sendJS.getBytes());
	}

}
