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
import android.widget.TextView;
import android.widget.Toast;
import cn.edu.xsyu.dorm.R;
import cn.edu.xsyu.dorm.utils.HttpUtils;
import cn.edu.xsyu.dorm.utils.Tools;

import com.loopj.android.image.SmartImageView;

public class NoticeDetailActivity2 extends Activity {
	private SmartImageView siv;
	private EditText et_title;
	private TextView tv_issuer;
	private TextView tv_issueTime;
	private EditText et_noticeContent;
	private String ID;
	private String title;
	private String noticeContent;
	private int noticeID;

	public Handler handler = new Handler() {
		@Override
		public void handleMessage(Message msg) {
			final Bundle b = msg.getData();
			System.out.println("====handleMessage====" + msg.what);
			switch (msg.what) {
			case 0:
				new Thread(){
					public void run() {
						String path = "http://192.168.1.106:8080/dorm_server/queryNoticeServlet";
						String jsonString = HttpUtils.getJsonContent(path);
						System.out.println("path--------------"+path);
						System.out.println("NoticeDetailActivity2------------"+jsonString);
						Intent intent = new Intent(NoticeDetailActivity2.this,DormNoticeActivity2.class);
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
				Toast.makeText(NoticeDetailActivity2.this, b.getString("msg"),Toast.LENGTH_SHORT).show();
			default:
				break;
			}
		}
	};
	
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_noticedetail2);

		Intent intent = getIntent();
		ID = intent.getStringExtra("ID");

		siv = (SmartImageView) findViewById(R.id.siv);
		et_title = (EditText) findViewById(R.id.et_title);
		tv_issuer = (TextView) findViewById(R.id.tv_issuer);
		tv_issueTime = (TextView) findViewById(R.id.tv_issuetime);
		et_noticeContent = (EditText) findViewById(R.id.et_noticecontent);

		String jsonString = intent.getStringExtra("jsonString");
		JSONObject jsonObject;
		try {
			jsonObject = new JSONObject(jsonString);
			//siv.setImageUrl("http://192.168.72.1:8080/dd.jpg");
			et_title.setText(jsonObject.getString("title"));
			tv_issuer.setText(jsonObject.getString("issuer"));
			tv_issueTime.setText(jsonObject.getString("time"));
			et_noticeContent.setText(jsonObject.getString("content"));
			noticeID = jsonObject.getInt("noticeID");
		} catch (JSONException e) {
			e.printStackTrace();
		}
		
		
		Button bt_change = (Button) findViewById(R.id.bt_change);
		bt_change.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				title = et_title.getText().toString();
				noticeContent = et_noticeContent.getText().toString();
				if (title==null || title.length()<=0) {
					et_title.requestFocus();
					et_title.setError("标题不能为空");
					return;
				}
				
				if (noticeContent==null || noticeContent.length()<=0)   
				{         
					et_noticeContent.requestFocus();  
					et_noticeContent.setError("公告内容不能为空");  
				    return;  
				}
				
				new Thread(){
					public void run() {
						try {
							JSONObject js = new JSONObject();
							js.put("ID", ID);
							js.put("noticeID", noticeID);
							js.put("title", title);
							js.put("content", noticeContent);
							
							String content = String.valueOf(js);
							
							String path = "http://192.168.1.106:8080/dorm_server/changeNoticeServlet";
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
		
		Button bt_delete = (Button) findViewById(R.id.bt_delete);
		bt_delete.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				new Thread(){
					public void run() {
						try {
							JSONObject js = new JSONObject();
							js.put("ID", ID);
							js.put("noticeID", noticeID);
							
							String content = String.valueOf(js);
							
							String path = "http://192.168.1.106:8080/dorm_server/deleteNoticeServlet";
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
									bd.putString("msg", "删除成功!");
									msg.setData(bd);
									handler.sendMessage(msg);
								}else{
									Message msg = new Message();
									msg.what = 1;
									Bundle bd = new Bundle();
									bd.putString("msg", "删除失败！");
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
