package cn.edu.xsyu.dorm.action;

import java.io.IOException;
import java.io.OutputStream;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONObject;
import cn.edu.xsyu.dorm.dao.impl.ReplyDaoImpl;
import cn.edu.xsyu.dorm.domain.Reply;
import cn.edu.xsyu.dorm.utils.JsonTools;

public class ReplyAddServlet extends HttpServlet {

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
		
		ReplyDaoImpl replyDaoImpl = new ReplyDaoImpl();
		Reply reply = new Reply();
		reply.setRepContent(recvJo.getString("content"));
		reply.setReplier(ID);
		reply.setMessageID(recvJo.getInt("messageID"));
		
		String sendJS = null;
		OutputStream os = response.getOutputStream();
		
		if(replyDaoImpl.insertReply(reply) < 1){
			sendJS = JsonTools.createJsonString("res", "fail");
			os.write(sendJS.getBytes());
			return;
		}
		
		sendJS = JsonTools.createJsonString("res", "ok");
		os.write(sendJS.getBytes());
	}
}
