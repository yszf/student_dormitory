package cn.edu.xsyu.dorm.manager;

import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.List;

import org.json.JSONObject;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;
import cn.edu.xsyu.dorm.R;
import cn.edu.xsyu.dorm.domain.Reply;
import cn.edu.xsyu.dorm.utils.HttpUtils;
import cn.edu.xsyu.dorm.utils.JSONTools;
import cn.edu.xsyu.dorm.utils.Tools;

public class ReplyListActivity2 extends Activity {
	private List<Reply> replyList;
	private ListView lv_reply;
	private Button bt_add;
	private Button bt_delete;
	private String ID;
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
						String path = "http://192.168.1.106:8080/dorm_server/queryMessageServlet";
						String jsonString = HttpUtils.getJsonContent(path);
						System.out.println("path--------------"+path);
						System.out.println("ReplyListActivity2------------"+jsonString);
						Intent intent = new Intent(ReplyListActivity2.this,MessageListActivity2.class);
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
				Toast.makeText(ReplyListActivity2.this, b.getString("msg"),Toast.LENGTH_SHORT).show();
			default:
				break;
			}
		}
	};
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_reply2);
		
		Intent intent = getIntent();
		messageID = Integer.parseInt(intent.getStringExtra("messageID"));
		ID = intent.getStringExtra("ID");
		
		System.out.println("messageID ====== "+messageID);
		System.out.println("ID----------------"+ID);
		

		lv_reply = (ListView) findViewById(R.id.lv_reply);
		
		getReplyInfo();
		
		if(replyList.size()!=0)
		{
			lv_reply.setAdapter(new MyAdapter());
		}
		
		bt_add = (Button) findViewById(R.id.bt_add);
		bt_add.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				Intent intent = new Intent(ReplyListActivity2.this, ReplyAddActivity2.class);	
				Bundle extras = new Bundle();
				extras.putString("ID", ID);
				extras.putString("messageID", messageID+"");
				intent.putExtras(extras);
				startActivity(intent);
			}
		});
		
		bt_delete = (Button) findViewById(R.id.bt_delete);
		bt_delete.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				
				new Thread(){
					public void run() {
						try {
							JSONObject js = new JSONObject();
							js.put("messageID", messageID);
							
							String content = String.valueOf(js);
							
							String path = "http://192.168.1.106:8080/dorm_server/deleteMessageServlet";
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
	
	private void getReplyInfo() {
		Intent intent = getIntent();
		String jsonString = intent.getStringExtra("jsonString");
		System.out.println("ReplyListActivity-----------" + jsonString);
		replyList = JSONTools.getReplys("replys",jsonString);
		System.out.println("replyList============="+ replyList);
	}
	
	class MyAdapter extends BaseAdapter {
		// 把条目需要使用到的所有组件封装在这个类中
		class ViewHolder {
			TextView tv_replyer;
			TextView tv_replyTime;
			TextView tv_replyContent;
		}

		@Override
		public int getCount() {
			return replyList == null ? 0 : replyList.size();
		}

		// 返货一个View对象，会作为ListView的一个条目显示在界面上
		@Override
		public View getView(int position, View convertView, ViewGroup parent) {
			Reply reply = replyList.get(position);
			View v = null;
			ViewHolder mHolder = null;
			if (convertView == null) {
				// 如何填充的
				v = View.inflate(ReplyListActivity2.this,R.layout.item_reply, null);
				// 创建viewHolder封装所有条目使用的组件
				mHolder = new ViewHolder();
				mHolder.tv_replyer = (TextView) v.findViewById(R.id.tv_replyer);
				mHolder.tv_replyTime = (TextView) v.findViewById(R.id.tv_replytime);
				mHolder.tv_replyContent = (TextView) v.findViewById(R.id.tv_replycontent);
				// 把viewHolder封装至view对象中，这样view被缓存时，viewHolder也就被缓存了
				v.setTag(mHolder);
			} else {
				v = convertView;
				// 从view中取出保存的viewHolder，viewHolder中就有所有的组件对象，不需要再去findViewById
				mHolder = (ViewHolder) v.getTag();
			}
			// 给条目中的每个组件设置显示的内容
			mHolder.tv_replyer.setText(reply.getReplier());
			mHolder.tv_replyTime.setText(reply.getTime());
			mHolder.tv_replyContent.setText(reply.getRepContent());
			return v;
		}

		@Override
		public Object getItem(int position) {
			return replyList.get(position);
		}

		@Override
		public long getItemId(int position) {
			return position;
		}
	}
}
