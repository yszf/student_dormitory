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
import android.widget.EditText;
import android.widget.Toast;
import cn.edu.xsyu.dorm.R;
import cn.edu.xsyu.dorm.utils.Tools;

public class EditManagerInfoActivity extends Activity{
	private EditText et_name;
	private EditText et_email;
	private EditText et_buildingID;
	private EditText et_time;
	private EditText et_buildingManagerID;
	private String jsonString;
	private String ID;
	
	private String name;
	private String time;
	private int buildingID;
	private String buildingManagerID;
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
						String path = "http://192.168.1.106:8080/dorm_server/managerInfoServlet";
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
				        System.out.println("EditManagerInfoActivity-------------"+jsonString);
				        Intent intent = new Intent(EditManagerInfoActivity.this,
								ManagerInfoActivity.class);
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
				Toast.makeText(EditManagerInfoActivity.this,"�޸�ʧ��", Toast.LENGTH_LONG).show();
			default:
				break;
			}
		}
	};


	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_editmanagerinfo);
		
		et_name = (EditText) findViewById(R.id.et_name);
		et_email = (EditText) findViewById(R.id.et_email);
		et_buildingID = (EditText) findViewById(R.id.et_buildingID);
		et_time = (EditText) findViewById(R.id.et_time);
		et_buildingManagerID = (EditText) findViewById(R.id.et_buildingManagerID);

		Intent intent = getIntent();
		ID = intent.getStringExtra("ID");
		role = intent.getStringExtra("role");
		
		jsonString = intent.getStringExtra("jsonString");
		try {
			JSONObject jo = new JSONObject(jsonString);
			et_buildingManagerID.setText(jo.getString("buildingManagerID"));
			et_name.setText(jo.getString("name"));
			et_email.setText(jo.getString("email"));
			et_time.setText(jo.getString("time"));
			et_buildingID.setText(jo.getString("buildingID"));
		} catch (JSONException e) {
			e.printStackTrace();
		}
	}
	
	public void click(View v){

		name = et_name.getText().toString().trim();
		email = et_email.getText().toString().trim();
		time = et_time.getText().toString().trim();
		buildingID = Integer.parseInt(et_buildingID.getText().toString().trim());
		buildingManagerID = et_buildingManagerID.getText().toString().trim();
		
		new Thread() {
            @Override
			public void run() {
				JSONObject js = new JSONObject();
				// ��װ�Ӷ���
				try
				{
					js.put("buildingManagerID", buildingManagerID);
					js.put("name",name);
					js.put("time", time);
					js.put("email", email);
					js.put("buildingID", buildingID);
					System.out.println(js.toString());
				}
				catch (JSONException e)
				{
					e.printStackTrace();
				}
				// ��Json����ת����String���ͣ�ʹ��������������д
				String content = String.valueOf(js);
				try
				{
					String path = "http://192.168.1.106:8080/dorm_server/updateManagerInfoServlet";
					System.out.println("path:"+path);
					URL url = new URL(path);
					HttpURLConnection conn = (HttpURLConnection) url.openConnection();
					conn.setConnectTimeout(5000);
					conn.setReadTimeout(5000);
					conn.setDoOutput(true);// �����������
					conn.setRequestMethod("POST");
					conn.setRequestProperty("Content-Type","application/json; charset=UTF-8"); // ��������
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
							bd.putString("msg", "�޸ĳɹ�!");
							msg.setData(bd);
							handler.sendMessage(msg);
						}
						else{
							Message msg = new Message();
							msg.what = 1;
							Bundle bd = new Bundle();
							bd.putString("msg", "�޸�ʧ��!");
							msg.setData(bd);
							handler.sendMessage(msg);
						}
					}
					else
					{
						Message msg = new Message();
						msg.what = 1;
						Bundle bd = new Bundle();
						bd.putString("msg", "�޸�ʧ��!");
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
