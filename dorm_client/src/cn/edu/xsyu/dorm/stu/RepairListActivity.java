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
import cn.edu.xsyu.dorm.domain.BuildDorm;
import cn.edu.xsyu.dorm.domain.Repair;
import cn.edu.xsyu.dorm.utils.JSONTools;

public class RepairListActivity extends Activity implements OnItemClickListener{

	private List<Repair> repairList;
	private List<BuildDorm> buildDormList;
	private ListView lv_repair;
	private String ID;
	private Button bt_add;
	private String jsonString;
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_repairlist);

		Intent intent = getIntent();
		ID = intent.getStringExtra("ID");

		getRepairInfo();

		lv_repair = (ListView) findViewById(R.id.lv_repair);
		lv_repair.setAdapter(new MyAdapter());
		lv_repair.setOnItemClickListener(this);
		
		bt_add = (Button) findViewById(R.id.bt_add);
		
		
		bt_add.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				Intent intent = new Intent(RepairListActivity.this,RepairAddActivity.class);
				Bundle extras = new Bundle();
				extras.putString("ID", ID);
				intent.putExtras(extras);
				startActivity(intent);
			}
		});
	}

	private void getRepairInfo() {
		Intent intent = getIntent();
		jsonString = intent.getStringExtra("jsonString");
		repairList = JSONTools.getRepairs("repairs", jsonString);
		buildDormList = JSONTools.getBuildDorms("buildDorms", jsonString);
		System.out.println("repairList =============== "+repairList);
		System.out.println("buildDormList =============== "+buildDormList);
	}

	class MyAdapter extends BaseAdapter {
		// 把条目需要使用到的所有组件封装在这个类中
		class ViewHolder {
			TextView tv_reason;
			TextView tv_issuer;
			TextView tv_startTime;
			TextView tv_dealState;
		}
		@Override
		public int getCount() {
			return repairList == null ? 0 : repairList.size();
		}

		// 返货一个View对象，会作为ListView的一个条目显示在界面上
		@Override
		public View getView(int position, View convertView, ViewGroup parent) {
			Repair repair = repairList.get(position);
			View v = null;
			ViewHolder mHolder = null;
			if (convertView == null) {
				// 如何填充的
				v = View.inflate(RepairListActivity.this, R.layout.item_repair,null);
				// 创建viewHolder封装所有条目使用的组件
				mHolder = new ViewHolder();
				mHolder.tv_reason = (TextView) v.findViewById(R.id.tv_reason);
				mHolder.tv_issuer = (TextView) v.findViewById(R.id.tv_issuer);
				mHolder.tv_startTime = (TextView) v.findViewById(R.id.tv_startTime);
				mHolder.tv_dealState = (TextView) v.findViewById(R.id.tv_dealState);
				// 把viewHolder封装至view对象中，这样view被缓存时，viewHolder也就被缓存了
				v.setTag(mHolder);
			} else {
				v = convertView;
				// 从view中取出保存的viewHolder，viewHolder中就有所有的组件对象，不需要再去findViewById
				mHolder = (ViewHolder) v.getTag();
			}
			// 给条目中的每个组件设置显示的内容
			mHolder.tv_reason.setText(repair.getReason());
			mHolder.tv_issuer.setText(repair.getIssuer());
			mHolder.tv_startTime.setText(repair.getStartTime());
			switch (repair.getDealState()) {
			case 1:
				mHolder.tv_dealState.setText("未查看");
				break;
			case 2:
				mHolder.tv_dealState.setText("已查看");
				break;
			case 3:
				mHolder.tv_dealState.setText("正在处理");
				break;
			case 4:
				mHolder.tv_dealState.setText("已完成");
				break;
			default:
				break;
			}
			return v;
		}

		@Override
		public Object getItem(int position) {
			return repairList.get(position);
		}

		@Override
		public long getItemId(int position) {
			return position;
		}
	}

	@Override
	public void onItemClick(AdapterView<?> parent, View view, int position,
			long id) {
		int pos = position;
		Repair repair = repairList.get(pos);
		JSONObject jsonObject = new JSONObject();
		try {
			JSONObject recvJo = new JSONObject(jsonString);
			buildDormList = JSONTools.getBuildDorms("buildDorms", jsonString);
			System.out.println("buildDormList =============== "+buildDormList);
			for (BuildDorm b : buildDormList) {
				if(b.getBdID() == repair.getBdID()){
					jsonObject.put("buildingID",b.getBuildingID());
					jsonObject.put("dormitoryID",b.getDormitoryID());
				}
			}
			jsonObject.put("issuer", repair.getIssuer());
			jsonObject.put("serviceManID", repair.getServicemanID());
			jsonObject.put("startTime", repair.getStartTime());
			jsonObject.put("lastTime", repair.getLastTime());
			jsonObject.put("contacts", repair.getContacts());
			jsonObject.put("reason", repair.getReason());
			jsonObject.put("dealState", repair.getDealState());
			
			String jsonString = String.valueOf(jsonObject);
			Intent intent = new Intent(RepairListActivity.this,RepairDetailActivity.class);
			Bundle extras = new Bundle();
			extras.putString("ID", ID);
			extras.putString("jsonString", jsonString);
			System.out.println("jsonString==========="+jsonString);
			intent.putExtras(extras);
			startActivity(intent);
		} catch (JSONException e) {
			e.printStackTrace();
		}
	}

}
