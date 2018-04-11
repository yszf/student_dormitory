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
									bd.putString("msg", "ɾ���ɹ�!");
									msg.setData(bd);
									handler.sendMessage(msg);
								}else{
									Message msg = new Message();
									msg.what = 1;
									Bundle bd = new Bundle();
									bd.putString("msg", "ɾ��ʧ�ܣ�");
									msg.setData(bd);
									handler.sendMessage(msg);
								}
							}
							else
							{
								Message msg = new Message();
								msg.what = 1;
								Bundle bd = new Bundle();
								bd.putString("msg", "���ʧ�ܣ�");
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
		// ����Ŀ��Ҫʹ�õ������������װ���������
		class ViewHolder {
			TextView tv_replyer;
			TextView tv_replyTime;
			TextView tv_replyContent;
		}

		@Override
		public int getCount() {
			return replyList == null ? 0 : replyList.size();
		}

		// ����һ��View���󣬻���ΪListView��һ����Ŀ��ʾ�ڽ�����
		@Override
		public View getView(int position, View convertView, ViewGroup parent) {
			Reply reply = replyList.get(position);
			View v = null;
			ViewHolder mHolder = null;
			if (convertView == null) {
				// �������
				v = View.inflate(ReplyListActivity2.this,R.layout.item_reply, null);
				// ����viewHolder��װ������Ŀʹ�õ����
				mHolder = new ViewHolder();
				mHolder.tv_replyer = (TextView) v.findViewById(R.id.tv_replyer);
				mHolder.tv_replyTime = (TextView) v.findViewById(R.id.tv_replytime);
				mHolder.tv_replyContent = (TextView) v.findViewById(R.id.tv_replycontent);
				// ��viewHolder��װ��view�����У�����view������ʱ��viewHolderҲ�ͱ�������
				v.setTag(mHolder);
			} else {
				v = convertView;
				// ��view��ȡ�������viewHolder��viewHolder�о������е�������󣬲���Ҫ��ȥfindViewById
				mHolder = (ViewHolder) v.getTag();
			}
			// ����Ŀ�е�ÿ�����������ʾ������
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
