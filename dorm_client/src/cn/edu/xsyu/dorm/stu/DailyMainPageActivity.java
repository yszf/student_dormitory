package cn.edu.xsyu.dorm.stu;

import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import cn.edu.xsyu.dorm.R;
import cn.edu.xsyu.dorm.utils.HttpUtils;
import cn.edu.xsyu.dorm.utils.Tools;

public class DailyMainPageActivity extends Activity{

	private Button dormInfo;
	private Button rank;
	private Button broadcast;
	private Button messageList;
	private Button rule;
	private Button leaveschool;
	private String ID;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_dailymainpage);
		
		Intent intent = getIntent();
		ID = intent.getStringExtra("ID");
		
		//宿舍信息
		dormInfo = (Button)findViewById(R.id.dormyInfo);
		dormInfo.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View arg0) {
				new Thread(){
					public void run() {
						String path = "http://192.168.1.106:8080/dorm_server/queryDormInfoServlet";
						System.out.println("path ====== "+path);
						System.out.println("ID-------------"+ID);
						JSONObject js = new JSONObject();
						try {
							js.put("ID", ID);
						} catch (JSONException e) {
							e.printStackTrace();
						}
						String content = String.valueOf(js);
						String jsonString = Tools.getJsonContent(path, content);
						Intent intent = new Intent(DailyMainPageActivity.this, DormInfoActivity.class);	
						Bundle extras = new Bundle();
						extras.putString("ID", ID);
						extras.putString("jsonString",jsonString);
						System.out.println("DailymainpageActivity============"+jsonString);
						intent.putExtras(extras);
						startActivity(intent);
					}
				}.start();
				
			}
		});
		
		//最新排名
		rank = (Button)findViewById(R.id.latestrank);
		rank.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View arg0) {
				new Thread(){
					private int level;

					public void run() {
						String path = "http://192.168.1.106:8080/dorm_server/queryDormsServlet";
						System.out.println("path ====== "+path);
						System.out.println("ID-------------"+ID);
						level = 1;
						JSONObject js = new JSONObject();
						try {
							js.put("level", level);
							js.put("ID", ID);
						} catch (JSONException e) {
							e.printStackTrace();
						}
						String content = String.valueOf(js);
						String jsonString = Tools.getJsonContent(path, content);
						
						Intent intent = new Intent(DailyMainPageActivity.this, LatestRankActivity.class);
						Bundle extras = new Bundle();
						extras.putString("jsonString",jsonString);
						extras.putString("ID", ID);
						extras.putString("level",level+"");
						System.out.println("DailymainpageActivity============"+jsonString);
						intent.putExtras(extras);
						startActivity(intent);
					};
				}.start();
			
			}
		});
		
		//宿舍公告
		broadcast = (Button)findViewById(R.id.dormybroadcast);
		broadcast.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View arg0) {
				new Thread(){
					public void run() {
						String path = "http://192.168.1.106:8080/dorm_server/queryNoticeServlet";
						String jsonString = HttpUtils.getJsonContent(path);
						
						Intent intent = new Intent(DailyMainPageActivity.this, DormNoticeActivity.class);
						Bundle extras = new Bundle();
						extras.putString("ID", ID);
						extras.putString("jsonString", jsonString);
						System.out.println("DailymainpageActivity==========="+jsonString);
						intent.putExtras(extras);
						startActivity(intent);
					};
				}.start();
				
			}
		});
		
		//留言板
		messageList = (Button)findViewById(R.id.messagelist);
		messageList.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View arg0) {
				new Thread(){
					public void run() {
						String path = "http://192.168.1.106:8080/dorm_server/queryMessageServlet";
						String jsonString = HttpUtils.getJsonContent(path);
						System.out.println("path--------------"+path);
						Intent intent = new Intent(DailyMainPageActivity.this, MessageListActivity.class);	
						Bundle extras = new Bundle();
						extras.putString("ID", ID);
						extras.putString("jsonString",jsonString);
						System.out.println("DailymainpageActivity============"+jsonString);
						intent.putExtras(extras);
						startActivity(intent);
					}
				}.start();
			}
		});
		//宿舍规章制度
		rule = (Button)findViewById(R.id.dormyrule);
		rule.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				new Thread(){
					public void run() {
						String path = "http://192.168.1.106:8080/dorm_server/queryDormRuleServlet";
						String jsonString = HttpUtils.getJsonContent(path);
						
						Intent intent = new Intent(DailyMainPageActivity.this, DormRuleActivity.class);
						Bundle extras = new Bundle();
						extras.putString("ID", ID);
						extras.putString("jsonString", jsonString);
						System.out.println("DailymainpageActivity==========="+jsonString);
						intent.putExtras(extras);
						startActivity(intent);
					};
				}.start();
			}
		});
		
		//离校登记
		leaveschool = (Button)findViewById(R.id.leaveschool);
		leaveschool.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View arg0) {
				new Thread(){
					public void run() {
						String path = "http://192.168.1.106:8080/dorm_server/queryLeaveRegServlet";
						System.out.println("path ====== "+path);
						System.out.println("ID-------------"+ID);
						JSONObject js = new JSONObject();
						try {
							js.put("ID", ID);
						} catch (JSONException e) {
							e.printStackTrace();
						}
						String content = String.valueOf(js);
						String jsonString = Tools.getJsonContent(path, content);
						
						Intent intent = new Intent(DailyMainPageActivity.this, SchoolRegActivity.class);
						Bundle extras = new Bundle();
						extras.putString("ID", ID);
						extras.putString("jsonString", jsonString);
						System.out.println("DailymainpageActivity==========="+jsonString);
						intent.putExtras(extras);
						startActivity(intent);
					};
				}.start();
			}
		});
	}

}
