package cn.edu.xsyu.dorm.stu;

import java.util.List;

import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import cn.edu.xsyu.dorm.R;
import cn.edu.xsyu.dorm.domain.Message;
import cn.edu.xsyu.dorm.utils.JSONTools;
import cn.edu.xsyu.dorm.utils.Tools;



public class MessageListActivity extends Activity implements OnItemClickListener{
	private ListView lv_message;
	private List<Message> messageList;
	private String ID;
	private Button bt_add;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_message);
		
		Intent intent = getIntent();
		ID = intent.getStringExtra("ID");
		lv_message = (ListView) findViewById(R.id.lv_message);
		
		getMessageInfo();
		
		if(messageList.size()!=0)
		{
			lv_message.setAdapter(new MyAdapter());
			lv_message.setOnItemClickListener(this);
		}
		
		bt_add = (Button) findViewById(R.id.bt_add);
		bt_add.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				Intent intent = new Intent(MessageListActivity.this, MessageAddActivity.class);	
				Bundle extras = new Bundle();
				extras.putString("ID", ID);
				intent.putExtras(extras);
				startActivity(intent);
			}
		});

	}

	private void getMessageInfo() {
		Intent intent = getIntent();
		String jsonString = intent.getStringExtra("jsonString");
		System.out.println("MessageListActivity-----------" + jsonString);
		messageList = JSONTools.getMessages("messages",jsonString);
		System.out.println("messageList============="+ messageList);
	}

	
	class MyAdapter extends BaseAdapter {
		// 把条目需要使用到的所有组件封装在这个类中
		class ViewHolder {
			TextView tv_issuer;
			TextView tv_time;
			TextView tv_content;
		}

		@Override
		public int getCount() {
			return messageList == null ? 0 : messageList.size();
		}

		// 返货一个View对象，会作为ListView的一个条目显示在界面上
		@Override
		public View getView(int position, View convertView, ViewGroup parent) {
			Message message = messageList.get(position);
			View v = null;
			ViewHolder mHolder = null;
			if (convertView == null) {
				// 如何填充的
				v = View.inflate(MessageListActivity.this,R.layout.item_message,null);
				// 创建viewHolder封装所有条目使用的组件
				mHolder = new ViewHolder();
				mHolder.tv_issuer = (TextView) v.findViewById(R.id.tv_issuer);
				mHolder.tv_time = (TextView) v.findViewById(R.id.tv_time);
				mHolder.tv_content = (TextView) v.findViewById(R.id.tv_content);
				// 把viewHolder封装至view对象中，这样view被缓存时，viewHolder也就被缓存了
				v.setTag(mHolder);
			} else {
				v = convertView;
				// 从view中取出保存的viewHolder，viewHolder中就有所有的组件对象，不需要再去findViewById
				mHolder = (ViewHolder) v.getTag();
			}
			// 给条目中的每个组件设置显示的内容
			mHolder.tv_issuer.setText(message.getStudentID());
			mHolder.tv_time.setText(message.getTime());
			mHolder.tv_content.setText(message.getContent());
			
			return v;
		}

		@Override
		public Object getItem(int position) {
			return messageList.get(position);
		}

		@Override
		public long getItemId(int position) {
			return position;
		}
	}
	
	@Override
	public void onItemClick(AdapterView<?> parent, View view, int position,
			long id) {
		final int pos = position;
		new Thread(){
			public void run() {
				String path = "http://192.168.1.106:8080/dorm_server/queryReplyServlet";
				System.out.println("path ====== "+path);
				System.out.println("ID-------------"+ID);
				JSONObject js = new JSONObject();
				Message message = messageList.get(pos);
				try {
					js.put("messageID", message.getMessageID());
				} catch (JSONException e) {
					e.printStackTrace();
				}
				String content = String.valueOf(js);
				String jsonString = Tools.getJsonContent(path, content);
				Intent intent = new Intent(MessageListActivity.this, ReplyListActivity.class);	
				Bundle extras = new Bundle();
				extras.putString("messageID",message.getMessageID()+"");
				extras.putString("ID", ID);
				extras.putString("jsonString",jsonString);
				System.out.println("MessageListActivity============"+jsonString);
				intent.putExtras(extras);
				startActivity(intent);
			}
		}.start();
	}
	
}
