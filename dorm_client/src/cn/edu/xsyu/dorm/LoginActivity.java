package cn.edu.xsyu.dorm;

import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;
import cn.edu.xsyu.dorm.manager.SysPageActivity2;
import cn.edu.xsyu.dorm.serviceman.SysPageActivity3;
import cn.edu.xsyu.dorm.stu.FindpwdActivity;
import cn.edu.xsyu.dorm.stu.SysPageActivity;
import cn.edu.xsyu.dorm.utils.HttpUtils;
import cn.edu.xsyu.dorm.utils.Tools;

public class LoginActivity extends Activity {
	private Spinner spinner;
	private EditText et_name;
	private EditText et_pass;
	private TextView lostpwd;
	private CheckBox savepwd;
	private String id;
	private String pass;
	private String role;
	private String[] arr;
	private ProgressDialog p;
	private Map<String,String> map;

	// hander����
	public Handler handler = new Handler() {
		@Override
		public void handleMessage(Message msg) {
			System.out.println("====handleMessage====" + msg.what);
			Bundle b = msg.getData();
			Toast.makeText(LoginActivity.this, b.getString("msg"),Toast.LENGTH_SHORT).show();
			switch (msg.what) {
			case 0:
				new Thread(){
					public void run() {
						String path = "http://192.168.1.106:8080/dorm_server/queryPartNoticeServlet";
						String jsonString = HttpUtils.getJsonContent(path);
						Intent intent = null;
						System.out.println("role-----------"+role);
						if (role.equalsIgnoreCase("student")) {
							intent = new Intent(LoginActivity.this,SysPageActivity.class);
						}else if(role.equalsIgnoreCase("buildingManager")){
							intent = new Intent(LoginActivity.this,SysPageActivity2.class);
						}else if(role.equalsIgnoreCase("serviceMan")){
							intent = new Intent(LoginActivity.this,SysPageActivity3.class);
						}
						Bundle extras = new Bundle();
						extras.putString("ID", id);
						extras.putString("role", role);
						extras.putString("jsonString", jsonString);
						intent.putExtras(extras);
						startActivity(intent);
						try {
							Thread.sleep(10);
							finish();
						} catch (InterruptedException e) {
							e.printStackTrace();
						}  
						
					};
				}.start();
			default:
				break;
			}
		}
	};

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_login);

		et_name = (EditText) findViewById(R.id.login_et_usrid);
		et_pass = (EditText) findViewById(R.id.login_et_pass);
		
		// ����������
		spinner = (Spinner) this.findViewById(R.id.login_sp_identity);
		map = new HashMap<String,String>();
		map.put("student","ѧ��");
		map.put("buildingManager","�������Ա");
		map.put("serviceMan", "ά����Ա");
		arr = new String[] {
				"ѧ��", 
				"�������Ա", 
				"ά����Ա",
		};
		ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(this,
				android.R.layout.simple_list_item_1, arr);
		spinner.setAdapter(arrayAdapter);
		
		lostpwd = (TextView) findViewById(R.id.login_tv_lostpwd);
		// ��������
		lostpwd.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View arg0) {
				Intent intent = new Intent(LoginActivity.this,FindpwdActivity.class);
				Bundle extras = new Bundle();
				role = (String) spinner.getSelectedItem();
				for (Entry<String, String> entry : map.entrySet()) {
					if(entry.getValue().equals(role)){
						role = entry.getKey();
						break;
					}
				}
				System.out.println("role:"+role);
				extras.putString("role", role);
				intent.putExtras(extras);
				startActivity(intent);
			}
		});

		// ��ȡ�˻���Ϣ
		readAccount();
	}

	// ��½
	public void login(View v) {

		id = et_name.getText().toString().trim();
		if (id==null || id.length()<=0) {
			et_name.requestFocus();
			et_name.setError("�û�������Ϊ��");
			return;
		}
		
		pass = et_pass.getText().toString().trim();
		if (pass==null || pass.length()<=0)   
		{         
			et_pass.requestFocus();  
			et_pass.setError("���벻��Ϊ��");  
		    return;  
		}
		
		// ѡ�����
		spinner.setOnItemSelectedListener(new OnItemSelectedListener() {

			@Override
			public void onItemSelected(AdapterView<?> arg0, View arg1,
					int arg2, long arg3) {
				// �õ���ѡ�����ֵ
				role = (String) spinner.getSelectedItem();
				arg0.setVisibility(View.VISIBLE);

			}

			@Override
			public void onNothingSelected(AdapterView<?> arg0) {
				System.out.println("�ף���ǰ��û��ѡ����ݣ�");
			}
		});
		savepwd = (CheckBox) findViewById(R.id.login_cb_savepwd);
		// �ж�ѡ���Ƿ�ѡ��
		if (savepwd.isChecked()) {
			// ��ȡsharepreference
			SharedPreferences sp = getSharedPreferences("info", MODE_PRIVATE);
			// ��ȡ�༭��
			android.content.SharedPreferences.Editor ed = sp.edit();
			ed.putString("ID", id);
			ed.putString("password", pass);
			// �ύ
			ed.commit();
		}
		role = (String) spinner.getSelectedItem();
		for (Entry<String, String> entry : map.entrySet()) {
			if(entry.getValue().equals(role)){
				role = entry.getKey();
				break;
			}
		}
		System.out.println("role:"+role);
		p = new ProgressDialog(LoginActivity.this);
		p.setTitle("��¼��");
		p.setMessage("��¼�У����Ͼͺ�");
		p.show();
		new Thread(new Runnable() {  
	        @Override  
	        public void run() {  
	            int i = 0;  
	            while (i < 100) {  
	                try {  
	                    Thread.sleep(2);  
	                    // ���½������Ľ���,���������߳��и��½���������  
	                    p.incrementProgressBy(1);  
	                  //  dialog.incrementSecondaryProgressBy(3)//�������������·�ʽ  
	                    i++;  
	                } catch (Exception e) {  
	                    System.out.println(e);
	                }  
	            }  
	            // �ڽ���������ʱɾ��Dialog  
	            p.dismiss();  
	  
	        }  
	    }).start();
		
		new Thread() {
            @Override
			public void run() {
				JSONObject js = new JSONObject();
				// ��װ�Ӷ���
				try
				{
					js.put("ID", id);
					js.put("password",pass);
					js.put("role", role);
				}
				catch (JSONException e)
				{
					e.printStackTrace();
				}
				
				// ��Json����ת����String���ͣ�ʹ��������������д
				String content = String.valueOf(js);

				try
				{
					String path = "http://192.168.1.106:8080/dorm_server/loginServlet";
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
							bd.putString("msg", "��¼�ɹ�!");
							msg.setData(bd);
							handler.sendMessage(msg);
						}else if(val.equalsIgnoreCase("password is wrong")){
							Message msg = new Message();
							msg.what = 1;
							Bundle bd = new Bundle();
							bd.putString("msg", "�������");
							msg.setData(bd);
							handler.sendMessage(msg);
						}else if(val.equalsIgnoreCase("ID is not exist")){
							Message msg = new Message();
							msg.what = 2;
							Bundle bd = new Bundle();
							bd.putString("msg", "�˺Ų����ڣ�");
							handler.sendMessage(msg);
							msg.setData(bd);
						}
					}
					else
					{
						Message msg = new Message();
						msg.what = 3;
						Bundle bd = new Bundle();
						bd.putString("msg", "����ʧ�ܣ�");
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

	// ��ȡ������û���������Ϣ
	private void readAccount() {
		// ��ȡsharedpreference
		SharedPreferences sp = getSharedPreferences("info", MODE_PRIVATE);
		String ID = sp.getString("ID", "");
		String password = sp.getString("password", "");

		et_name.setText(ID);
		et_pass.setText(password);
	}

}
