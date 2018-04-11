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
import android.widget.Toast;
import cn.edu.xsyu.dorm.R;
import cn.edu.xsyu.dorm.utils.Tools;

public class ReplyAddActivity2 extends Activity{

	private String ID;
	private EditText et_content;
	private String repContent;
	private int messageID;

	public Handler handler = new Handler() {
		@Override
		public void handleMessage(Message msg) {
			final Bundle b = msg.getData();
			System.out.println("====handleMessage====" + msg.what);
			switch (msg.what) {
			case 0:
				new Thread(){
					public void run() {
						String path = "http://192.168.1.106:8080/dorm_server/queryReplyServlet";
						JSONObject js = new JSONObject();
						try {
							js.put("messageID", messageID);
						} catch (JSONException e) {
							e.printStackTrace();
						}
						String content = String.valueOf(js);
						String jsonString = Tools.getJsonContent(path,content);
						System.out.println("path--------------"+path);
						System.out.println("ReplyAddActivity------------"+jsonString);
						Intent intent = new Intent(ReplyAddActivity2.this,ReplyListActivity2.class);
						intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
						Bundle extras = new Bundle();
						extras.putString("ID", ID);
						extras.putString("messageID", messageID+"");
						extras.putString("jsonString", jsonString);
						intent.putExtras(extras);
						startActivity(intent);
					};
				}.start();
				break;
			case 1:
				Toast.makeText(ReplyAddActivity2.this, b.getString("msg"),
						Toast.LENGTH_SHORT).show();
			default:
				break;
			}
		}
	};

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_replyadd);
		
		Intent intent = getIntent();
		ID = intent.getStringExtra("ID");
		messageID = Integer.parseInt(intent.getStringExtra("messageID"));
		et_content = (EditText) findViewById(R.id.et_repContent);
		
		Button bt_submit = (Button) findViewById(R.id.bt_submit);
		bt_submit.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View arg0) {
				new Thread(){
					public void run() {
						try {
							repContent = et_content.getText().toString();
							
							JSONObject js = new JSONObject();
							js.put("ID", ID);
							js.put("content", repContent);
							js.put("messageID", messageID);
							
							String content = String.valueOf(js);
							
							String path = "http://192.168.1.106:8080/dorm_server/replyAddServlet";
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
									bd.putString("msg", "��ӻظ��ɹ�!");
									msg.setData(bd);
									handler.sendMessage(msg);
								}else{
									Message msg = new Message();
									msg.what = 1;
									Bundle bd = new Bundle();
									bd.putString("msg", "��ӻظ�ʧ�ܣ�");
									msg.setData(bd);
									handler.sendMessage(msg);
								}
							}
							else
							{
								Message msg = new Message();
								msg.what = 1;
								Bundle bd = new Bundle();
								bd.putString("msg", "��ӻظ�ʧ�ܣ�");
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
