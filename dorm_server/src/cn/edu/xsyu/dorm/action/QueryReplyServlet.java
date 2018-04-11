package cn.edu.xsyu.dorm.action;

import java.io.IOException;
import java.io.OutputStream;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONObject;
import cn.edu.xsyu.dorm.dao.impl.ReplyDaoImpl;
import cn.edu.xsyu.dorm.domain.Reply;
import cn.edu.xsyu.dorm.utils.JsonTools;

public class QueryReplyServlet extends HttpServlet {

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
		int messageID = recvJo.getInt("messageID");
		
		ReplyDaoImpl replyDaoImpl = new ReplyDaoImpl();
		List<Reply> replys = replyDaoImpl.queryReplys(messageID);
		
		JSONObject sendObject = new JSONObject();
		sendObject.put("replys",replys);
		
		String sendJS = String.valueOf(sendObject);
		System.out.println("=======json is===========" + sendJS);
		OutputStream os = response.getOutputStream();
		os.write(sendJS.getBytes());
	}

}
