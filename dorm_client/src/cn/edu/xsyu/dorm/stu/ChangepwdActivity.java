package cn.edu.xsyu.dorm.stu;

import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicHeader;
import org.apache.http.protocol.HTTP;
import org.apache.http.util.EntityUtils;
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
import cn.edu.xsyu.dorm.manager.SysPageActivity2;
import cn.edu.xsyu.dorm.serviceman.SysPageActivity3;
import cn.edu.xsyu.dorm.utils.HttpUtils;

public class ChangepwdActivity extends Activity{

	private EditText et_oldpwd;
	private EditText et_newpwd;
	private EditText et_redefpwd;
	private String oldpwd;
	private String newpwd;
	private String redefpwd;
	private String ID;
	private String role;
	
	public Handler handler = new Handler() {
		@Override
		public void handleMessage(Message msg) {
			final Bundle b = msg.getData();
			System.out.println("====handleMessage====" + msg.what);
			Toast.makeText(ChangepwdActivity.this, b.getString("msg"), Toast.LENGTH_SHORT).show();
			switch (msg.what) {
			case 0:
				new Thread(){
					public void run() {
						String path = "http://192.168.1.106:8080/dorm_server/queryPartNoticeServlet";
						String jsonString = HttpUtils.getJsonContent(path);
						Intent intent = null;
						if(role.equalsIgnoreCase("student")){
							intent = new Intent(ChangepwdActivity.this,SysPageActivity.class);
						}else if(role.equalsIgnoreCase("buildingManager")){
							intent = new Intent(ChangepwdActivity.this,SysPageActivity2.class);
						}else if(role.equalsIgnoreCase("serviceMan")){
							intent = new Intent(ChangepwdActivity.this,SysPageActivity3.class);
						}
						
						intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
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
				Toast.makeText(ChangepwdActivity.this, b.getString("msg"),
						Toast.LENGTH_SHORT).show();
			default:
				break;
			}
		}
	};

	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_changepwd);
		
		Intent intent = getIntent();
		ID = intent.getStringExtra("ID");
		role = intent.getStringExtra("role");
		System.out.println("ChangepwdActivity role ======="+role);
	
		et_oldpwd = (EditText) findViewById(R.id.oldpwd);
		et_newpwd = (EditText) findViewById(R.id.newpwd);
		et_redefpwd = (EditText)findViewById(R.id.redefpwd);
		
	}
	
	public void click(View v){
		
		oldpwd = et_oldpwd.getText().toString().trim();
		newpwd = et_newpwd.getText().toString().trim();
		redefpwd = et_redefpwd.getText().toString().trim();
		
		System.out.println("oldpwd:"+oldpwd+",newpwd:"+newpwd+",redefpwd:"+redefpwd);
		
		if (oldpwd == null || oldpwd.length() <= 0) {
			et_oldpwd.requestFocus();
			et_oldpwd.setError("对不起，原密码不能为空");
			return;
		}
		
		if (newpwd==null || newpwd.length() <= 0)   
		{         
			et_newpwd.requestFocus();  
			et_newpwd.setError("对不起，新密码不能为空");  
		    return;  
		}  
		
		if (redefpwd==null || redefpwd.length() <= 0)   
		{         
			et_redefpwd.requestFocus();  
			et_redefpwd.setError("对不起，确认密码不能为空");  
		    return;  
		}  
		
		if(!redefpwd.equals(newpwd)){
			et_redefpwd.requestFocus();
			et_redefpwd.setError("对不起，两次密码不一致");
			return;
		}
		
		new Thread(){
			@Override
			public void run() {
				super.run();
				String path = "http://192.168.1.106:8080/dorm_server/changepwdServlet";
				// 创建HttpPost对象
				HttpPost httpPostRequest = new HttpPost(path);
				httpPostRequest.addHeader(HTTP.CONTENT_TYPE, "application/json");
				
				JSONObject js = new JSONObject();
				try {
					js.put("ID", ID);
					js.put("role", role);
					js.put("oldpwd", oldpwd);
					js.put("newpwd", newpwd);
					System.out.println("js==="+js);
					
					StringEntity se = new StringEntity(js.toString());
					se.setContentType("text/json");
					se.setContentEncoding(new BasicHeader(HTTP.CONTENT_TYPE,"application/json"));
					httpPostRequest.setEntity(se);
					
					HttpResponse httpResponse = new DefaultHttpClient().execute(httpPostRequest);
					
					int code = httpResponse.getStatusLine().getStatusCode();
					System.out.println("======code:" + code);
					String result = EntityUtils.toString(httpResponse.getEntity());	//获得资源
					System.out.println("======response info:" + result);
					
					if(code == 200){
						JSONObject jsonObject = new JSONObject(result);
						String val = jsonObject.getString("res");
						if(val.equalsIgnoreCase("ok")){
							Message msg = new Message();
							msg.what = 0;
							Bundle bd = new Bundle();
							bd.putString("msg", "修改成功!");
							msg.setData(bd);
							handler.sendMessage(msg);
						}else if(val.equalsIgnoreCase("old password is wrong")){
							Message msg = new Message();
							msg.what = 1;
							Bundle bd = new Bundle();
							bd.putString("msg", "旧密码错误！");
							msg.setData(bd);
							handler.sendMessage(msg);
						}else if(val.equalsIgnoreCase("fail")){
							Message msg = new Message();
							msg.what = 1;
							Bundle bd = new Bundle();
							bd.putString("msg", "修改失败！");
							handler.sendMessage(msg);
							msg.setData(bd);
						}
						
					}else{
						Message msg = new Message();
						msg.what = 1;
						Bundle bd = new Bundle();
						bd.putString("msg", "修改失败!");
						handler.sendMessage(msg);
					}
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		}.start();
	}
	
	

}
