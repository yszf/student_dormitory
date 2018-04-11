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
import android.widget.EditText;
import android.widget.Toast;
import cn.edu.xsyu.dorm.R;
import cn.edu.xsyu.dorm.utils.Tools;

public class EditServiceManInfoActivity extends Activity{
	private EditText et_name;
	private EditText et_email;
	private EditText et_time;
	private EditText et_serviceManID;
	private String jsonString;
	private String ID;
	
	private String name;
	private String time;
	private String serviceManID;
	private String email;
	private String role;


	public Handler handler = new Handler() {
		@Override
		public void handleMessage(Message msg) {
			System.out.println("====handleMessage====" + msg.what);
			switch (msg.what) {
			case 0:
				new Thread(){
					public void run() {
						String path = "http://192.168.1.106:8080/dorm_server/serviceManInfoServlet";
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
				        System.out.println("EditServiceManInfoActivity-------------"+jsonString);
				        Intent intent = new Intent(EditServiceManInfoActivity.this,
								ServiceManInfoActivity.class);
				        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
						Bundle extras = new Bundle();
						extras.putString("ID", ID);
						extras.putString("role", role);
						extras.putString("jsonString", jsonString);
						intent.putExtras(extras);
						startActivity(intent);
					};
				}.start();
				
				break;
			case 1:
				Toast.makeText(EditServiceManInfoActivity.this,"修改失败", Toast.LENGTH_LONG).show();
			default:
				break;
			}
		}
	};


	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_editservicemaninfo);
		
		et_name = (EditText) findViewById(R.id.et_name);
		et_email = (EditText) findViewById(R.id.et_email);
		et_time = (EditText) findViewById(R.id.et_time);
		et_serviceManID = (EditText) findViewById(R.id.et_serviceManID);

		Intent intent = getIntent();
		ID = intent.getStringExtra("ID");
		role = intent.getStringExtra("role");
		
		jsonString = intent.getStringExtra("jsonString");
		try {
			JSONObject jo = new JSONObject(jsonString);
			et_serviceManID.setText(jo.getString("serviceManID"));
			et_name.setText(jo.getString("name"));
			et_email.setText(jo.getString("email"));
			et_time.setText(jo.getString("time"));
		} catch (JSONException e) {
			e.printStackTrace();
		}
	}
	
	public void click(View v){

		name = et_name.getText().toString().trim();
		email = et_email.getText().toString().trim();
		time = et_time.getText().toString().trim();
		serviceManID = et_serviceManID.getText().toString().trim();
		
		new Thread() {
            @Override
			public void run() {
				JSONObject js = new JSONObject();
				// 封装子对象
				try
				{
					js.put("serviceManID", serviceManID);
					js.put("name",name);
					js.put("time", time);
					js.put("email", email);
					System.out.println(js.toString());
				}
				catch (JSONException e)
				{
					e.printStackTrace();
				}
				// 把Json数据转换成String类型，使用输出流向服务器写
				String content = String.valueOf(js);
				try
				{
					String path = "http://192.168.1.106:8080/dorm_server/updateServiceManInfoServlet";
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
						}
						else{
							Message msg = new Message();
							msg.what = 1;
							Bundle bd = new Bundle();
							bd.putString("msg", "修改失败!");
							msg.setData(bd);
							handler.sendMessage(msg);
						}
					}
					else
					{
						Message msg = new Message();
						msg.what = 1;
						Bundle bd = new Bundle();
						bd.putString("msg", "修改失败!");
						msg.setData(bd);
						handler.sendMessage(msg);
					}
				}
				catch (Exception e)
				{
					e.printStackTrace();
				}
			}
		}.start();
		
	}

}
