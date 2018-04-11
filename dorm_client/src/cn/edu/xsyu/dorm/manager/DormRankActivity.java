package cn.edu.xsyu.dorm.manager;


import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;

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
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;
import cn.edu.xsyu.dorm.R;
import cn.edu.xsyu.dorm.utils.Tools;

public class DormRankActivity extends Activity {

	private String ID;
	private String buildingID;
	private String dormitoryID;
	private Button bt_submit;
	private RadioButton rb_first;
	private RadioButton rb_second;
	private RadioButton rb_third;
	private RadioButton rb_fourth;
	private RadioGroup rg_rank;
	private RadioButton rb_yes;
	private RadioButton rb_no;
	private RadioGroup rg_illegal;
	private EditText et_desc;
	
	private int level;
	private int isIllegal;
	private String description;
	
	public Handler handler = new Handler() {
		@Override
		public void handleMessage(Message msg) {
			final Bundle b = msg.getData();
			System.out.println("====handleMessage====" + msg.what);
			switch (msg.what) {
			case 0:
				new Thread(){
					public void run() {
						String path = "http://192.168.1.106:8080/dorm_server/queryDormInfoServlet2";
						System.out.println("path------------------"+path);
						System.out.println("ID-------------"+ID);
						JSONObject js = new JSONObject();
						try {
							js.put("ID", ID);
						} catch (JSONException e) {
							e.printStackTrace();
						}
						String content = String.valueOf(js);
						String jsonString = Tools.getJsonContent(path,content);
						System.out.println("DormRankActivity-------------"+jsonString);
				        
						Intent intent = new Intent(DormRankActivity.this,DormInfoActivity2.class);
						intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
						Bundle extras = new Bundle();
						extras.putString("ID", ID);
						extras.putString("jsonString", jsonString);
						intent.putExtras(extras);
						startActivity(intent);
					}
				}.start();
				break;
			case 1:
				Toast.makeText(DormRankActivity.this, b.getString("msg"),Toast.LENGTH_SHORT).show();
			default:
				break;
			}
		}
	};

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_rank);
		
		Intent intent = getIntent();
		ID = intent.getStringExtra("ID");
		buildingID = intent.getStringExtra("buildingID");
		dormitoryID = intent.getStringExtra("dormitoryID");
		System.out.println("hahahahah");
		
		rg_rank = (RadioGroup) findViewById(R.id.rg_rank);
		rb_first = (RadioButton) findViewById(R.id.rb_first);
		rb_second = (RadioButton) findViewById(R.id.rb_second);
		rb_third = (RadioButton) findViewById(R.id.rb_third);
		rb_fourth = (RadioButton) findViewById(R.id.rb_fourth);
		
		rb_first.setChecked(true);
		
		rg_illegal = (RadioGroup) findViewById(R.id.rg_illegal);
		rb_yes = (RadioButton) findViewById(R.id.rb_yes);
		rb_no = (RadioButton) findViewById(R.id.rb_no);
		rb_no.setChecked(true);
		
		et_desc = (EditText) findViewById(R.id.et_desc);
		
		bt_submit = (Button) findViewById(R.id.bt_submit);
		
		bt_submit.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				level = 1;
				if(rb_second.isChecked()){
					level = 2;
				}else if(rb_third.isChecked()){
					level = 3;
				}else if(rb_third.isChecked()){
					level = 4;
				}
				
				isIllegal = 0;
				if (rb_yes.isChecked()) {
					isIllegal = 1;
				}
				
				description = et_desc.getText().toString();
				if (description==null || description.length()<=0)   
				{         
					et_desc.requestFocus();  
					et_desc.setError("评价内容不能为空");  
				    return;  
				}
				
				new Thread(){
					public void run() {
						try {
							JSONObject js = new JSONObject();
							js.put("ID", ID);
							js.put("buildingID", buildingID);
							js.put("dormitoryID", dormitoryID);
							js.put("level", level);
							js.put("isIllegal", isIllegal);
							js.put("description", description);
							
							String content = String.valueOf(js);
							
							
							String path = "http://192.168.1.106:8080/dorm_server/scoreAddServlet";
							System.out.println("path:"+path);
							
							URL url = new URL(path);
							HttpURLConnection conn = (HttpURLConnection) url.openConnection();
							conn.setConnectTimeout(5000);
							conn.setReadTimeout(5000);
							conn.setDoOutput(true);// 设置允许输出
							conn.setRequestMethod("POST");
							conn.setRequestProperty("Content-Type","application/json; charset=UTF-8"); // 内容类型
							OutputStream os = conn.getOutputStream();
							os.write(content.getBytes());
							os.close();
							
							if (conn.getResponseCode() == 200)
							{
								InputStream is = conn.getInputStream();
								String recvJS = Tools.getTextFromStream(is);
								JSONObject jsonObject = new JSONObject(recvJS);
								String val = jsonObject.getString("res");
								
								System.out.println("======response info:" + val);
								
								if(val.equalsIgnoreCase("ok")){	
									Message msg = new Message();
									msg.what = 0;
									Bundle bd = new Bundle();
									bd.putString("msg", "打分成功!");
									msg.setData(bd);
									handler.sendMessage(msg);
								}else{
									Message msg = new Message();
									msg.what = 1;
									Bundle bd = new Bundle();
									bd.putString("msg", "打分失败！");
									msg.setData(bd);
									handler.sendMessage(msg);
								}
							}
							else
							{
								Message msg = new Message();
								msg.what = 1;
								Bundle bd = new Bundle();
								bd.putString("msg", "添加失败！");
								handler.sendMessage(msg);
								msg.setData(bd);
							}
						}
						catch (Exception e)
						{
							e.printStackTrace();
						}
					};
				}.start();
			}
		});
		
	}
}
