package cn.edu.xsyu.dorm.stu;

import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;

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
import android.widget.Toast;
import cn.edu.xsyu.dorm.R;
import cn.edu.xsyu.dorm.utils.HttpUtils;
import cn.edu.xsyu.dorm.utils.Tools;

public class RepairAddActivity extends Activity {

	private String ID;
	private EditText et_reason;
	private EditText et_phone;
	private String phone;
	private String reason;

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
						String jsonString = HttpUtils.getJsonContent(path);
						System.out.println("path--------------"+path);
						System.out.println("RepairAddActivity------------"+jsonString);
						Intent intent = new Intent(RepairAddActivity.this,RepairListActivity.class);
						intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
						Bundle extras = new Bundle();
						extras.putString("ID", ID);
						extras.putString("jsonString", jsonString);
						intent.putExtras(extras);
						startActivity(intent);
					};
				}.start();
				break;
			case 1:
				Toast.makeText(RepairAddActivity.this, b.getString("msg"),Toast.LENGTH_SHORT).show();
			default:
				break;
			}
		}
	};

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_repairadd);

		Intent intent = getIntent();
		ID = intent.getStringExtra("ID");

		et_phone = (EditText) findViewById(R.id.et_phone);
		et_reason = (EditText) findViewById(R.id.et_reason);

		Button bt_submit = (Button) findViewById(R.id.bt_submit);
		bt_submit.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				phone = et_phone.getText().toString().trim();
				reason = et_reason.getText().toString();
				if (phone==null || phone.length()<=0) {
					et_phone.requestFocus();
					et_phone.setError("联系方式不能为空");
					return;
				}
				
				if (reason==null || reason.length()<=0)   
				{         
					et_reason.requestFocus();  
					et_reason.setError("报修内容不能为空");  
				    return;  
				}
				
				new Thread(){
					public void run() {
						try {
							JSONObject js = new JSONObject();
							js.put("ID", ID);
							js.put("contacts", phone);
							js.put("reason", reason);
							
							String content = String.valueOf(js);
							
							String path = "http://192.168.1.106:8080/dorm_server/repairAddServlet";
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
									bd.putString("msg", "添加成功!");
									msg.setData(bd);
									handler.sendMessage(msg);
								}else{
									Message msg = new Message();
									msg.what = 1;
									Bundle bd = new Bundle();
									bd.putString("msg", "添加失败！");
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
