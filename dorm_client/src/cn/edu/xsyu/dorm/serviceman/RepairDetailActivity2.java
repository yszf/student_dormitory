package cn.edu.xsyu.dorm.serviceman;

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
import cn.edu.xsyu.dorm.utils.HttpUtils;
import cn.edu.xsyu.dorm.utils.Tools;

public class RepairDetailActivity2 extends Activity{
	private EditText et_startTime;
	private EditText et_lastTime;
	private EditText et_contacts;
	private EditText et_buildingID;
	private EditText et_dormitoryID;
	private EditText et_issuer;
	private EditText et_serviceManID;
	private RadioGroup rg_state;
	private EditText et_reason;
	private RadioButton rb_state1;
	private RadioButton rb_state2;
	private RadioButton rb_state3;
	private RadioButton rb_state4;
	private String ID;
	private int state;
	private Button bt_change;
	private int repairID;
	private String role;
	
	public Handler handler = new Handler() {
		@Override
		public void handleMessage(Message msg) {
			final Bundle b = msg.getData();
			System.out.println("====handleMessage====" + msg.what);
			switch (msg.what) {
			case 0:
				new Thread(){
					public void run() {
						String path = "http://192.168.1.106:8080/dorm_server/queryRepairServlet";
						System.out.println("path ====== "+path);
						System.out.println("ID-------------"+ID);
						String jsonString = HttpUtils.getJsonContent(path);
				        System.out.println("RepairDetailActivity2-------------"+jsonString);
				        Intent intent = new Intent(RepairDetailActivity2.this,RepairListActivity2.class);
				        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
				        Bundle extras = new Bundle();
						extras.putString("ID", ID); 
						extras.putString("role", role);
						extras.putString("jsonString", jsonString);
						intent.putExtras(extras);
						startActivity(intent);
					}
				}.start();
				break;
			case 1:
				Toast.makeText(RepairDetailActivity2.this, b.getString("msg"),Toast.LENGTH_SHORT).show();
			default:
				break;
			}
		}
	};

	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_repairdetail2);
		
		Intent intent = getIntent();
		ID = intent.getStringExtra("ID");
		repairID = Integer.parseInt(intent.getStringExtra("repairID"));
		
		et_startTime = (EditText) findViewById(R.id.et_startTime);
		et_lastTime = (EditText) findViewById(R.id.et_lastTime);
		et_contacts = (EditText) findViewById(R.id.et_contacts);
		et_buildingID = (EditText) findViewById(R.id.et_buildingID);
		et_dormitoryID = (EditText) findViewById(R.id.et_dormitoryID);
		et_issuer = (EditText) findViewById(R.id.et_issuer);
		et_serviceManID = (EditText) findViewById(R.id.et_serviceManID);
		et_reason = (EditText) findViewById(R.id.et_reason);
		
		rg_state = (RadioGroup)findViewById(R.id.rg_state);
		rb_state1 = (RadioButton)findViewById(R.id.rb_state1);
		rb_state2 = (RadioButton)findViewById(R.id.rb_state2);
		rb_state3 = (RadioButton)findViewById(R.id.rb_state3);
		rb_state4 = (RadioButton)findViewById(R.id.rb_state4);
		
		role = intent.getStringExtra("role");
		
		String jsonString = intent.getStringExtra("jsonString");
		System.out.println("jsonString-------------"+jsonString);
		JSONObject jsonObject;
		try {
			jsonObject = new JSONObject(jsonString);
			et_startTime.setText(jsonObject.getString("startTime"));
			et_lastTime.setText(jsonObject.getString("lastTime"));
			et_contacts.setText(jsonObject.getString("contacts"));
			et_buildingID.setText(jsonObject.getString("buildingID"));
			et_dormitoryID.setText(jsonObject.getString("dormitoryID"));
			et_issuer.setText(jsonObject.getString("issuer"));
			et_serviceManID.setText(jsonObject.getString("serviceManID"));
			et_reason.setText(jsonObject.getString("reason"));
			state = Integer.parseInt(jsonObject.getString("dealState"));
			
			switch (state) {
			case 1:
				rb_state1.setChecked(true);
				break;
			case 2:
				rb_state2.setChecked(true);
				break;
			case 3:
				rb_state3.setChecked(true);
				break;
			case 4:
				rb_state4.setChecked(true);
				break;
			default:
				break;
			}
		} catch (JSONException e) {
			e.printStackTrace();
		}
		
		bt_change = (Button) findViewById(R.id.bt_change);
		bt_change.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				if(rb_state1.isChecked()){
					state = 1;
				}else if(rb_state2.isChecked()){
					state = 2;
				}else if(rb_state3.isChecked()){
					state = 3;
				}else if(rb_state4.isChecked()){
					state = 4;
				}

				
				new Thread(){
					public void run() {
						try {
							JSONObject js = new JSONObject();
							js.put("ID", ID);
							js.put("state", state);
							js.put("repairID", repairID);
							
							String content = String.valueOf(js);
							
							String path = "http://192.168.1.106:8080/dorm_server/changeRepairServlet";
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
									bd.putString("msg", "修改成功!");
									msg.setData(bd);
									handler.sendMessage(msg);
								}else{
									Message msg = new Message();
									msg.what = 1;
									Bundle bd = new Bundle();
									bd.putString("msg", "修改失败！");
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
