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
		// ����Ŀ��Ҫʹ�õ������������װ���������
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

		// ����һ��View���󣬻���ΪListView��һ����Ŀ��ʾ�ڽ�����
		@Override
		public View getView(int position, View convertView, ViewGroup parent) {
			Repair repair = repairList.get(position);
			View v = null;
			ViewHolder mHolder = null;
			if (convertView == null) {
				// �������
				v = View.inflate(RepairListActivity.this, R.layout.item_repair,null);
				// ����viewHolder��װ������Ŀʹ�õ����
				mHolder = new ViewHolder();
				mHolder.tv_reason = (TextView) v.findViewById(R.id.tv_reason);
				mHolder.tv_issuer = (TextView) v.findViewById(R.id.tv_issuer);
				mHolder.tv_startTime = (TextView) v.findViewById(R.id.tv_startTime);
				mHolder.tv_dealState = (TextView) v.findViewById(R.id.tv_dealState);
				// ��viewHolder��װ��view�����У�����view������ʱ��viewHolderҲ�ͱ�������
				v.setTag(mHolder);
			} else {
				v = convertView;
				// ��view��ȡ�������viewHolder��viewHolder�о������е�������󣬲���Ҫ��ȥfindViewById
				mHolder = (ViewHolder) v.getTag();
			}
			// ����Ŀ�е�ÿ�����������ʾ������
			mHolder.tv_reason.setText(repair.getReason());
			mHolder.tv_issuer.setText(repair.getIssuer());
			mHolder.tv_startTime.setText(repair.getStartTime());
			switch (repair.getDealState()) {
			case 1:
				mHolder.tv_dealState.setText("δ�鿴");
				break;
			case 2:
				mHolder.tv_dealState.setText("�Ѳ鿴");
				break;
			case 3:
				mHolder.tv_dealState.setText("���ڴ���");
				break;
			case 4:
				mHolder.tv_dealState.setText("�����");
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
