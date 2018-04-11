package cn.edu.xsyu.dorm.stu;

import java.util.List;

import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;
import cn.edu.xsyu.dorm.R;
import cn.edu.xsyu.dorm.domain.LeaveReg;
import cn.edu.xsyu.dorm.utils.JSONTools;


public class SchoolRegActivity extends Activity implements OnItemClickListener{
	
	private List<LeaveReg> regList;
	private ListView lv_reg;
	private String ID;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_schoolreg);
		
		Intent intent = getIntent();
		ID = intent.getStringExtra("ID");

		getRegsInfo();

		lv_reg = (ListView) findViewById(R.id.lv_reg);
		lv_reg.setAdapter(new MyAdapter());
		lv_reg.setOnItemClickListener(this);
	}
	
	@Override
	public void onItemClick(AdapterView<?> parent, View view, int position,
			long id) {
		int pos = position;
		LeaveReg leaveReg = regList.get(pos);
		JSONObject jsonObject = new JSONObject();
		try {
			jsonObject.put("backTime",leaveReg.getBackTime());
			jsonObject.put("destination", leaveReg.getDestination());
			jsonObject.put("emergenceContact", leaveReg.getEmergenceContact());
			jsonObject.put("emergenceTel",leaveReg.getEmergenceTel());
			jsonObject.put("leaveTime", leaveReg.getLeaveTime());
			jsonObject.put("phone", leaveReg.getPhone());
			jsonObject.put("regID", leaveReg.getRegID());
			jsonObject.put("regTime", leaveReg.getRegTime());
			jsonObject.put("studentID",leaveReg.getStudentID());
			jsonObject.put("reason", leaveReg.getReason());

			String jsonString = String.valueOf(jsonObject);
			Intent intent = new Intent(SchoolRegActivity.this, RegDetailActivity.class);
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

	private void getRegsInfo() {
		Intent intent = getIntent();
		String jsonString = intent.getStringExtra("jsonString");
		regList = JSONTools.getRegs("leaveRegs", jsonString);
		System.out.println("regList ===============" + regList);
	}

	class MyAdapter extends BaseAdapter {
		// ����Ŀ��Ҫʹ�õ������������װ���������
		class ViewHolder {
			TextView tv_reason;
			TextView tv_leaveTime;
			TextView tv_backTime;
		}

		@Override
		public int getCount() {
			return regList == null ? 0 : regList.size();
		}

		// ����һ��View���󣬻���ΪListView��һ����Ŀ��ʾ�ڽ�����
		@Override
		public View getView(int position, View convertView, ViewGroup parent) {
			LeaveReg leaveReg = regList.get(position);
			View v = null;
			ViewHolder mHolder = null;
			if (convertView == null) {
				// �������
				v = View.inflate(SchoolRegActivity.this, R.layout.item_reg,null);
				// ����viewHolder��װ������Ŀʹ�õ����
				mHolder = new ViewHolder();
				mHolder.tv_reason = (TextView) v.findViewById(R.id.tv_reason);
				mHolder.tv_leaveTime = (TextView) v.findViewById(R.id.tv_leaveTime);
				mHolder.tv_backTime = (TextView) v.findViewById(R.id.tv_backTime);
				// ��viewHolder��װ��view�����У�����view������ʱ��viewHolderҲ�ͱ�������
				v.setTag(mHolder);
			} else {
				v = convertView;
				// ��view��ȡ�������viewHolder��viewHolder�о������е�������󣬲���Ҫ��ȥfindViewById
				mHolder = (ViewHolder) v.getTag();
			}
			// ����Ŀ�е�ÿ�����������ʾ������
			mHolder.tv_reason.setText(leaveReg.getReason());
			mHolder.tv_leaveTime.setText(leaveReg.getLeaveTime());
			mHolder.tv_backTime.setText(leaveReg.getBackTime());
			return v;
		}

		@Override
		public Object getItem(int position) {
			return regList.get(position);
		}

		@Override
		public long getItemId(int position) {
			return position;
		}
	}
}
