package cn.edu.xsyu.dorm.utils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.json.JSONArray;
import org.json.JSONObject;

import cn.edu.xsyu.dorm.domain.BuildDorm;
import cn.edu.xsyu.dorm.domain.DormRule;
import cn.edu.xsyu.dorm.domain.Electricity;
import cn.edu.xsyu.dorm.domain.LeaveReg;
import cn.edu.xsyu.dorm.domain.Message;
import cn.edu.xsyu.dorm.domain.Network;
import cn.edu.xsyu.dorm.domain.Notice;
import cn.edu.xsyu.dorm.domain.Repair;
import cn.edu.xsyu.dorm.domain.Reply;
import cn.edu.xsyu.dorm.domain.Score;
import cn.edu.xsyu.dorm.domain.Student;

/**
 * 完成对从服务端请求获得的JSON数据的解析成指定的对象.
 */
public class JSONTools {

	public JSONTools() {
	}

	public static Student getStudent(String key, String jsonString) {
		Student student = new Student();
		try {
			// 在Android官方文档中，org.json 这是Android提供给我们的解析json数据格式的包，
			// 我们比较常用的是JSONArray 和 JSONObject这个两个类
			JSONObject jsonObject = new JSONObject(jsonString);
			JSONObject studentObject = jsonObject.getJSONObject(key);
			student.setStudentID(studentObject.getString("studentID"));
			student.setName(studentObject.getString("name"));
			student.setSex(studentObject.getString("sex"));
			student.setEmail(studentObject.getString("email"));
			student.setBedNum(studentObject.getInt("bedNum"));
			student.setPhone(studentObject.getString("phone"));
			student.setAcademy(studentObject.getString("academy"));
			student.setBdID(studentObject.getInt("bdID"));
			student.setClassName(studentObject.getString("className"));

		} catch (Exception e) {
			e.printStackTrace();
		}
		return student;
	}

	public static List<Student> getStudents(String key, String jsonString) {
		List<Student> list = new ArrayList<Student>();
		try {
			JSONObject jsonObject = new JSONObject(jsonString);
			// 返回json的数组
			JSONArray jsonArray = jsonObject.getJSONArray(key);
			for (int i = 0; i < jsonArray.length(); i++) {
				JSONObject studentObject = jsonArray.getJSONObject(i);
				Student student = new Student();
				student.setStudentID(studentObject.getString("studentID"));
				student.setName(studentObject.getString("name"));
				student.setSex(studentObject.getString("sex"));
				student.setEmail(studentObject.getString("email"));
				student.setPhone(studentObject.getString("phone"));
				student.setBedNum(studentObject.getInt("bedNum"));
				student.setAcademy(studentObject.getString("academy"));
				student.setBdID(studentObject.getInt("bdID"));
				student.setClassName(studentObject.getString("className"));
				list.add(student);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return list;
	}
	
	public static List<Message> getMessages(String key, String jsonString) {
		List<Message> list = new ArrayList<Message>();
		try {
			JSONObject jsonObject = new JSONObject(jsonString);
			// 返回json的数组
			JSONArray jsonArray = jsonObject.getJSONArray(key);
			for (int i = 0; i < jsonArray.length(); i++) {
				JSONObject messageObject = jsonArray.getJSONObject(i);
				Message message = new Message();
				message.setStudentID(messageObject.getString("studentID"));
				message.setContent(messageObject.getString("content"));
				message.setMessageID(messageObject.getInt("messageID"));
				message.setTime(messageObject.getString("time"));
				list.add(message);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return list;
	}
	
	public static List<Reply> getReplys(String key, String jsonString) {
		List<Reply> list = new ArrayList<Reply>();
		try {
			JSONObject jsonObject = new JSONObject(jsonString);
			// 返回json的数组
			JSONArray jsonArray = jsonObject.getJSONArray(key);
			for (int i = 0; i < jsonArray.length(); i++) {
				JSONObject replyObject = jsonArray.getJSONObject(i);
				Reply reply = new Reply();
				reply.setReplyID(replyObject.getInt("replyID"));
				reply.setRepContent(replyObject.getString("repContent"));
				reply.setMessageID(replyObject.getInt("messageID"));
				reply.setReplier(replyObject.getString("replier"));
				reply.setTime(replyObject.getString("time"));
				list.add(reply);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return list;
	}
	
	public static List<BuildDorm> getBuildDorms(String key, String jsonString) {
		List<BuildDorm> list = new ArrayList<BuildDorm>();
		try {
			JSONObject jsonObject = new JSONObject(jsonString);
			// 返回json的数组
			JSONArray jsonArray = jsonObject.getJSONArray(key);
			for (int i = 0; i < jsonArray.length(); i++) {
				JSONObject buildDormObject = jsonArray.getJSONObject(i);
				BuildDorm buildDorm = new BuildDorm();
				buildDorm.setBdID(buildDormObject.getInt("bdID"));
				buildDorm.setBuildingID(buildDormObject.getInt("buildingID"));
				buildDorm.setDormitoryID(buildDormObject.getInt("dormitoryID"));
				list.add(buildDorm);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return list;
	}

	public static List<String> getListString(String key, String jsonString) {
		List<String> listString = new ArrayList<String>();
		try {
			JSONObject jsonObject = new JSONObject(jsonString);
			// 返回JSON的数组
			JSONArray jsonArray = jsonObject.getJSONArray(key);
			for (int i = 0; i < jsonArray.length(); i++) {
				String msg = jsonArray.getString(i);
				listString.add(msg);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return listString;
	}

	// 此时从服务端取下来的数据是：{"listMap":[{"id":1,"color":"red","name":"Polu"},{"id":7,"color":"green","name":"Zark"}]}
	public static List<Map<String, Object>> getListMaps(String key,
			String jsonString) {
		List<Map<String, Object>> listMap = new ArrayList<Map<String, Object>>();
		try {
			JSONObject jsonObject = new JSONObject(jsonString);
			JSONArray jsonArray = jsonObject.getJSONArray(key);
			for (int i = 0; i < jsonArray.length(); i++) {
				JSONObject jsonObject2 = jsonArray.getJSONObject(i);
				Map<String, Object> map = new HashMap<String, Object>();
				// 通过org.json中的迭代器来取Map中的值。
				Iterator<String> iterator = jsonObject2.keys();
				while (iterator.hasNext()) {
					String jsonKey = iterator.next();
					Object jsonValue = jsonObject2.get(jsonKey);
					// JSON的值是可以为空的，所以我们也需要对JSON的空值可能性进行判断。
					if (jsonValue == null) {
						jsonValue = "";
					}
					map.put(jsonKey, jsonValue);
				}
				listMap.add(map);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return listMap;
	}

	public static Notice getNotice(String key, String jsonString) {
		Notice notice = new Notice();
		try {
			JSONObject jsonObject = new JSONObject(jsonString);
			JSONObject noticeObject = jsonObject.getJSONObject(key);
			notice.setNoticeID(noticeObject.getInt("noticeID"));
			notice.setTime(noticeObject.getString("time"));
			notice.setTitle(noticeObject.getString("title"));
			notice.setContent(noticeObject.getString("content"));
			notice.setIssuer(noticeObject.getString("issuer"));
		} catch (Exception e) {
			e.printStackTrace();
		}
		return notice;
	}
	
	public static List<Notice> getNotices(String key, String jsonString) {
		List<Notice> list = new ArrayList<Notice>();
		try {
			JSONObject jsonObject = new JSONObject(jsonString);
			// 返回json的数组
			JSONArray jsonArray = jsonObject.getJSONArray(key);
			for (int i = 0; i < jsonArray.length(); i++) {
				JSONObject noticeObject = jsonArray.getJSONObject(i);
				Notice notice = new Notice();
				notice.setNoticeID(noticeObject.getInt("noticeID"));
				notice.setTime(noticeObject.getString("time"));
				notice.setTitle(noticeObject.getString("title"));
				notice.setContent(noticeObject.getString("content"));
				notice.setIssuer(noticeObject.getString("issuer"));
				list.add(notice);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return list;
	}
	
	public static List<DormRule> getRules(String key, String jsonString) {
		List<DormRule> list = new ArrayList<DormRule>();
		try {
			JSONObject jsonObject = new JSONObject(jsonString);
			// 返回json的数组
			JSONArray jsonArray = jsonObject.getJSONArray(key);
			for (int i = 0; i < jsonArray.length(); i++) {
				JSONObject ruleObject = jsonArray.getJSONObject(i);
				DormRule dormRule = new DormRule();
				dormRule.setRuleID(ruleObject.getInt("ruleID"));
				dormRule.setTime(ruleObject.getString("time"));
				dormRule.setTitle(ruleObject.getString("title"));
				dormRule.setContent(ruleObject.getString("content"));
				dormRule.setIssuer(ruleObject.getString("issuer"));
				list.add(dormRule);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return list;
	}
	
	public static List<Score> getScores(String key, String jsonString) {
		List<Score> list = new ArrayList<Score>();
		try {
			JSONObject jsonObject = new JSONObject(jsonString);
			// 返回json的数组
			JSONArray jsonArray = jsonObject.getJSONArray(key);
			System.out.println("List<Score>---------jsonArray");
			for (int i = 0; i < jsonArray.length(); i++) {
				JSONObject scoreObject = jsonArray.getJSONObject(i);
				Score score = new Score();
				score.setScoreID(scoreObject.getInt("scoreID"));
				score.setWeekNum(scoreObject.getInt("weekNum"));
				score.setLevel(scoreObject.getInt("level"));
				score.setIsIllegal(scoreObject.getInt("isIllegal"));
				score.setDescription(scoreObject.getString("description"));
				score.setBdID(scoreObject.getInt("bdID"));
				System.out.println(score);
				list.add(score);
			}
		} catch (Exception e) {
			System.out.println("失败了呀！！！");
			e.printStackTrace();
		}
		return list;
	}
	
	public static List<Repair> getRepairs(String key, String jsonString) {
		List<Repair> list = new ArrayList<Repair>();
		try {
			JSONObject jsonObject = new JSONObject(jsonString);
			// 返回json的数组
			JSONArray jsonArray = jsonObject.getJSONArray(key);
			System.out.println("List<Score>---------jsonArray");
			for (int i = 0; i < jsonArray.length(); i++) {
				JSONObject repairObject = jsonArray.getJSONObject(i);
				Repair repair = new Repair();
				repair.setRepairID(repairObject.getInt("repairID"));
				repair.setReason(repairObject.getString("reason"));
				repair.setIssuer(repairObject.getString("issuer"));
				repair.setContacts(repairObject.getString("contacts"));
				repair.setDealState(repairObject.getInt("dealState"));
				repair.setStartTime(repairObject.getString("startTime"));
				repair.setLastTime(repairObject.getString("lastTime"));
				repair.setServicemanID(repairObject.getString("servicemanID"));
				repair.setBdID(repairObject.getInt("bdID"));
				list.add(repair);
			}
		} catch (Exception e) {
			System.out.println("当前没有数据");
			e.printStackTrace();
		}
		return list;
	}
	
	public static List<LeaveReg> getRegs(String key, String jsonString) {
		List<LeaveReg> list = new ArrayList<LeaveReg>();
		try {
			JSONObject jsonObject = new JSONObject(jsonString);
			// 返回json的数组
			JSONArray jsonArray = jsonObject.getJSONArray(key);
			System.out.println("List<LeaveReg>---------jsonArray");
			for (int i = 0; i < jsonArray.length(); i++) {
				JSONObject regObject = jsonArray.getJSONObject(i);
				LeaveReg reg = new LeaveReg();
				reg.setReason(regObject.getString("reason"));
				reg.setDestination(regObject.getString("destination"));
				reg.setRegTime(regObject.getString("regTime"));
				reg.setBackTime(regObject.getString("backTime"));
				reg.setEmergenceContact(regObject.getString("emergenceContact"));
				reg.setLeaveTime(regObject.getString("leaveTime"));
				reg.setRegID(regObject.getInt("regID"));
				reg.setEmergenceTel(regObject.getString("emergenceTel"));
				reg.setStudentID(regObject.getString("studentID"));
				reg.setPhone(regObject.getString("phone"));
				list.add(reg);
			}
		} catch (Exception e) {
			System.out.println("当前没有数据");
			e.printStackTrace();
		}
		return list;
	}
	
	public static LeaveReg getReg(String key, String jsonString) {
		LeaveReg reg = new LeaveReg();
		try {
			JSONObject jsonObject = new JSONObject(jsonString);
			JSONObject regObject = jsonObject.getJSONObject(key);
			reg.setDestination(regObject.getString("destination"));
			reg.setLeaveTime(regObject.getString("leaveTime"));
			reg.setBackTime(regObject.getString("backTime"));
			reg.setPhone(regObject.getString("phone"));
			reg.setEmergenceContact(regObject.getString("emergenceContact"));
			reg.setEmergenceTel(regObject.getString("emergenceTel"));
		} catch (Exception e) {
			e.printStackTrace();
		}
		return reg;
	}
	
	public static Electricity getElectricity(String key, String jsonString) {
		Electricity elec = new Electricity();
		try {
			JSONObject jsonObject = new JSONObject(jsonString);
			JSONObject elecObject = jsonObject.getJSONObject(key);
			elec.setElecID(elecObject.getInt("elecID"));
			elec.setBdID(elecObject.getInt("BdID"));
			elec.setRemainElec(elecObject.getDouble("remainElec"));
			elec.setRemainMoney(elecObject.getDouble("remainMoney"));
			elec.setRechargeMoney(elecObject.getDouble("rechargeMoney"));
			elec.setRechargeTime(elecObject.getString("rechargeTime"));
		} catch (Exception e) {
			e.printStackTrace();
		}
		return elec;
	}
	
	public static Network getNetwork(String key, String jsonString) {
		Network net = new Network();
		try {
			JSONObject jsonObject = new JSONObject(jsonString);
			JSONObject elecObject = jsonObject.getJSONObject(key);
			net.setNetworkID(elecObject.getInt("networkID"));
			net.setStudentID(elecObject.getString("studentID"));
			net.setRemainTime(elecObject.getInt("remainTime"));
			net.setRemainMoney(elecObject.getDouble("remainMoney"));
			net.setRechargeMoney(elecObject.getDouble("rechargeMoney"));
			net.setRechargeTime(elecObject.getString("rechargeTime"));
		} catch (Exception e) {
			e.printStackTrace();
		}
		return net;
	}
	
}
