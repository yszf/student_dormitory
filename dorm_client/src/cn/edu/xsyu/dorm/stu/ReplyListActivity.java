package cn.edu.xsyu.dorm.stu;

import java.util.List;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import cn.edu.xsyu.dorm.R;
import cn.edu.xsyu.dorm.domain.Reply;
import cn.edu.xsyu.dorm.utils.JSONTools;

public class ReplyListActivity extends Activity {
	private List<Reply> replyList;
	private ListView lv_reply;
	private Button bt_add;
	private String ID;
	private int messageID;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_reply);
		
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
				Intent intent = new Intent(ReplyListActivity.this, ReplyAddActivity.class);	
				Bundle extras = new Bundle();
				extras.putString("ID", ID);
				extras.putString("messageID", messageID+"");
				intent.putExtras(extras);
				startActivity(intent);
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
				v = View.inflate(ReplyListActivity.this,R.layout.item_reply, null);
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
