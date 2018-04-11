package cn.edu.xsyu.dorm.stu;

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
import android.widget.RadioButton;
import android.widget.Toast;
import cn.edu.xsyu.dorm.R;
import cn.edu.xsyu.dorm.utils.Tools;

import com.loopj.android.image.SmartImageView;

public class EditStuInfoActivity extends Activity{
	private SmartImageView siv_image;
	private EditText et_name;
	private EditText et_email;
	private EditText et_phone;
	private EditText et_buildingid;
	private EditText et_dormitoryid;
	private EditText et_bedNum;
	private EditText et_roomheader;
	private RadioButton rb_male;
	private RadioButton rb_female;
	
	private String studentID;
	private String sex;
	private String name;
	private String email;
	private String phone;
	private int buildingID;
	private int dormitoryID;
	private int bedNum;
	private String roomheader;
	private String jsonString;
	private String jsonString2;

	public Handler handler = new Handler() {
		@Override
		public void handleMessage(Message msg) {
			System.out.println("====handleMessage====" + msg.what);
			switch (msg.what) {
			case 0:
				String path = "http://192.168.1.106:8080/dorm_server/stuInfoServlet";
		        Intent intent = new Intent(EditStuInfoActivity.this,
						StuInfoActivity.class);
		        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
		        Bundle extras = new Bundle();
				extras.putString("jsonString", jsonString2);
				intent.putExtras(extras);
				startActivity(intent);
				break;
			case 1:
				Toast.makeText(EditStuInfoActivity.this,"修改失败", Toast.LENGTH_LONG).show();
			default:
				break;
			}
		}
	};


	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_editstuinfo);
		
		Intent intent = getIntent();
		
		siv_image = (SmartImageView) findViewById(R.id.siv_image);
		//请求网络图片
		siv_image.setImageUrl("http://192.168.1.106:8080/stu.png");
		
		et_name = (EditText) findViewById(R.id.et_name);
		rb_male = (RadioButton)findViewById(R.id.rb_male);
		rb_female = (RadioButton)findViewById(R.id.rb_female);
		et_email = (EditText) findViewById(R.id.et_email);
		et_phone = (EditText) findViewById(R.id.et_phone);
		et_buildingid = (EditText) findViewById(R.id.et_buildingid);
		et_dormitoryid = (EditText) findViewById(R.id.et_domitoryid);
		et_bedNum = (EditText) findViewById(R.id.et_bedNum);
		et_roomheader = (EditText) findViewById(R.id.et_roomheader);
		
		jsonString = intent.getStringExtra("jsonString");
    	System.out.println("EditstuinfoActivity1====="+jsonString);
		try {
			JSONObject jo = new JSONObject(jsonString);
			studentID = jo.getString("studentID");
			et_name.setText(jo.getString("name"));
			if(jo.getString("sex").equals("女")){
				sex = "女";
				rb_female.setChecked(true);
			}else{
				sex = "男";
				rb_male.setChecked(true);
			}
			et_email.setText(jo.getString("email"));
			et_phone.setText(jo.getString("phone"));
			et_buildingid.setText(jo.getString("buildingID"));
			et_dormitoryid.setText(jo.getString("dormitoryID"));
			et_bedNum.setText(jo.getString("bedNum"));
			et_roomheader.setText(jo.getString("roomheader"));
		} catch (JSONException e) {
			e.printStackTrace();
		}
	}
	
	public void click(View v){

		name = et_name.getText().toString().trim();
		
		if(rb_male.isChecked()){
			sex = "男";
		}
		else{
			sex = "女";
		}
		System.out.println("sex = "+sex);
		email = et_email.getText().toString().trim();
		phone = et_phone.getText().toString().trim();
		buildingID = Integer.parseInt(et_buildingid.getText().toString().trim());
		dormitoryID = Integer.parseInt(et_dormitoryid.getText().toString().trim());
		bedNum = Integer.parseInt(et_bedNum.getText().toString().trim());
		roomheader = et_roomheader.getText().toString().trim();
		
		new Thread() {
            @Override
			public void run() {
				JSONObject js = new JSONObject();
				// 封装子对象
				try
				{
					js.put("studentID", studentID);
					js.put("name",name);
					js.put("sex", sex);
					js.put("email", email);
					js.put("phone", phone);
					js.put("buildingID", buildingID);
					js.put("dormitoryID", dormitoryID);
					js.put("bedNum", bedNum);
					js.put("roomheader", roomheader);
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
					String path = "http://192.168.1.106:8080/dorm_server/updateStuInfoServlet";
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
							JSONObject jo = new JSONObject(jsonString);
							js.put("academy", jo.get("academy"));
							js.put("className", jo.get("className"));
							jsonString2 = js.toString();
							System.out.println("EditStuInfoActivity2======="+jsonString2);
							Bundle bd = new Bundle();
							bd.putString("msg", "修改成功!");
							msg.setData(bd);
							handler.sendMessage(msg);
						}
						else{
							Message msg = new Message();
							msg.what = 1;
							handler.sendMessage(msg);
						}
					}
					else
					{
						handler.sendEmptyMessage(1);
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
