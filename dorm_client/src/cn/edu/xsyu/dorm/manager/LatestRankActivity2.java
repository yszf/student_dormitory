package cn.edu.xsyu.dorm.manager;

import java.util.List;

import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import cn.edu.xsyu.dorm.R;
import cn.edu.xsyu.dorm.domain.BuildDorm;
import cn.edu.xsyu.dorm.utils.JSONTools;
import cn.edu.xsyu.dorm.utils.Tools;

public class LatestRankActivity2 extends Activity {

	private RadioGroup rg_rank;
	private TextView tv_dormitory;
	private TextView tv_buildingID;
	private TextView tv_weekNum;
	private Button bt_search;

	private RadioButton rb_first;
	private RadioButton rb_second;
	private RadioButton rb_third;
	private RadioButton rb_fourth;
	
	private int level;
	private String ID;
	private String jsonString1;
	private String jsonString2;

	
	// hander´´½¨
	public Handler handler = new Handler() {
		@Override
		public void handleMessage(Message msg) {
			System.out.println("====handleMessage====" + msg.what);
			Bundle b = msg.getData();
			switch (msg.what) {
			case 0:
				try {
					JSONObject jsonObject = new JSONObject(b.getString("jsonString2"));
					List<BuildDorm> buildDormList = JSONTools.getBuildDorms("buildDorms", jsonString2);
					System.out.println("buildDormList=============="+buildDormList);
					StringBuilder builder = new StringBuilder();
					for(int i=0; i < buildDormList.size(); ++i)
					{
						if(i % 5 == 0 && i != 0){
							builder.append("\n");
						}
						builder.append(buildDormList.get(i).getDormitoryID()+" | ");
					}
					System.out.println("builder================"+builder);
					tv_buildingID.setText(jsonObject.getInt("buildingID")+"");
					tv_weekNum.setText(jsonObject.getInt("weekNum")+"");
					tv_dormitory.setText(builder.toString());
				} catch (JSONException e) {
					e.printStackTrace();
				}
			default:
				break;
			}
		}
	};
		
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_latestrank);
		
		tv_dormitory = (TextView) findViewById(R.id.tv_dormitory);
		tv_buildingID = (TextView) findViewById(R.id.tv_buildingID);
		tv_weekNum = (TextView) findViewById(R.id.tv_weekNum);
		
		rg_rank = (RadioGroup) findViewById(R.id.rg_rank);
		rb_first = (RadioButton) findViewById(R.id.rb_first);
		rb_second = (RadioButton) findViewById(R.id.rb_second);
		rb_third = (RadioButton) findViewById(R.id.rb_third);
		rb_fourth = (RadioButton) findViewById(R.id.rb_fourth);
		bt_search = (Button) findViewById(R.id.bt_search);
		
		Intent intent = getIntent();
		ID = intent.getStringExtra("ID");
		level = Integer.parseInt(intent.getStringExtra("level"));
		
		switch(level){
		case 1:
			rb_first.setChecked(true);
			break;
		case 2:
			rb_second.setChecked(true);
			break;
		case 3:
			rb_third.setChecked(true);
			break;
		case 4:
			rb_fourth.setChecked(true);
			break;
		default:
			break;
		}
		jsonString1 = intent.getStringExtra("jsonString");
		System.out.println("LatestRankActivity=========="+jsonString1);
		
		try {
			JSONObject jsonObject = new JSONObject(jsonString1);
			List<BuildDorm> buildDormList = JSONTools.getBuildDorms("buildDorms", jsonString1);
			System.out.println("buildDormList=============="+buildDormList);
			StringBuilder builder = new StringBuilder();
			for(int i=0; i < buildDormList.size(); ++i)
			{
				if(i % 5 == 0 && i != 0){
					builder.append("\n");
				}
				builder.append(buildDormList.get(i).getDormitoryID()+" | ");
			}
			System.out.println("builder================"+builder);
			tv_buildingID.setText(jsonObject.getInt("buildingID")+"");
			tv_weekNum.setText(jsonObject.getInt("weekNum")+"");
			tv_dormitory.setText(builder.toString());
		} catch (JSONException e) {
			e.printStackTrace();
		}
		
		bt_search.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View arg0) {
				if (rb_first.isChecked()) {
					level = 1;
				}else if(rb_second.isChecked()){
					level = 2;
				}else if(rb_third.isChecked()){
					level = 3;
				}else{
					level = 4;
				}
				System.out.println("level ==== "+level);
				new Thread(){
					public void run() {
						String path = "http://192.168.1.106:8080/dorm_server/queryDormsServlet2";
						System.out.println("path ====== "+path);
						System.out.println("ID-------------"+ID);
						JSONObject js = new JSONObject();
						try {
							js.put("level", level);
							js.put("ID", ID);
						} catch (JSONException e) {
							e.printStackTrace();
						}
						String content = String.valueOf(js);
						jsonString2 = Tools.getJsonContent(path, content);
						
						Message msg = new Message();
						msg.what = 0;
						Bundle bd = new Bundle();
						bd.putString("jsonString2", jsonString2);
						msg.setData(bd);
						handler.sendMessage(msg);
					};
				}.start();
			}
		});
	}
}
