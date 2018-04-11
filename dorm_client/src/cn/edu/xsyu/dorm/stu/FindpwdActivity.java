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
import android.widget.EditText;
import android.widget.Toast;
import cn.edu.xsyu.dorm.R;
import cn.edu.xsyu.dorm.utils.Tools;

public class FindpwdActivity extends Activity {
	
	private String email;
	private EditText et_email;
	private String role;

	public Handler handler = new Handler() {
		@Override
		public void handleMessage(Message msg) {
			final Bundle b = msg.getData();
			System.out.println("====handleMessage====" + msg.what);
			switch (msg.what) {
			case 0:
				Intent intent = new Intent(FindpwdActivity.this,ChangepwdActivity2.class);
				Bundle extras = new Bundle();
				extras.putString("role", role);
				extras.putString("email", email);
				intent.putExtras(extras);
				startActivity(intent);
				break;
			case 1:
				Toast.makeText(FindpwdActivity.this, b.getString("msg"),
						Toast.LENGTH_SHORT).show();
			default:
				break;
			}
		}
	};

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_findpwd);
		
		Intent intent = getIntent();
		role = intent.getStringExtra("role");
		
		et_email = (EditText) findViewById(R.id.findpwd_email);
	}
	
	public void click(View v){
		email = et_email.getText().toString().trim();
		if(email == null || email.length() == 0){
			et_email.requestFocus();
			et_email.setError("对不起，邮箱不能为空");
			return;
		}
		
		new Thread() {
            @Override
			public void run() {
            	JSONObject js = new JSONObject();
				// 封装子对象
				try
				{
					js.put("role", role);
					js.put("email", email);
					
					// 把Json数据转换成String类型，使用输出流向服务器写
					String content = String.valueOf(js);
					
					String path = "http://192.168.1.106:8080/dorm_server/queryEmailServlet";
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
							handler.sendMessage(msg);
						}else {
							Message msg = new Message();
							msg.what = 1;
							Bundle bd = new Bundle();
							bd.putString("msg", "邮箱不存在或角色选择错误！");
							handler.sendMessage(msg);
							msg.setData(bd);
						}
					}
					else
					{
						Message msg = new Message();
						msg.what = 1;
						Bundle bd = new Bundle();
						bd.putString("msg", "请求失败！");
						handler.sendMessage(msg);
						msg.setData(bd);
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
