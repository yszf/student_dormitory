package cn.edu.xsyu.dorm.manager;

import java.util.List;

import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import cn.edu.xsyu.dorm.R;
import cn.edu.xsyu.dorm.domain.Student;
import cn.edu.xsyu.dorm.utils.JSONTools;

public class DormInfoActivity3 extends Activity {

	private EditText et_buildingID;
	private EditText et_dormitoryID;
	private EditText et_roomheader;
	private EditText et_peopleNum;
	private EditText et_bedSum;
	private ListView lv_roommate;
	private List<Student> studentList;
	private Button bt_rank;
	private String ID;
	private JSONObject jsonObject;
	private String jsonString;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_dorminfo3);
		
		Intent intent = getIntent();
		ID = intent.getStringExtra("ID");
		System.out.println("DormInfoActivity====="+ID);
		
		et_buildingID = (EditText) findViewById(R.id.et_buildingID);
		et_dormitoryID = (EditText) findViewById(R.id.et_dormitoryID);
		et_roomheader = (EditText) findViewById(R.id.et_roomheader);
		et_peopleNum = (EditText) findViewById(R.id.et_peopleNum);
		et_bedSum = (EditText) findViewById(R.id.et_bedSum);
		
		jsonString = intent.getStringExtra("jsonString");
		System.out.println("DormInfoActivity3=========="+jsonString);
		try {
			jsonObject = new JSONObject(jsonString);
			studentList = JSONTools.getStudents("students", jsonString);
			System.out.println("studentList=============="+studentList);
			et_buildingID.setText(jsonObject.getInt("buildingID")+"");
			et_dormitoryID.setText(jsonObject.getInt("dormitoryID")+"");
			et_roomheader.setText(jsonObject.getString("roomheader"));
			et_peopleNum.setText(jsonObject.getInt("peopleNum")+"");
			et_bedSum.setText(""+jsonObject.getInt("bedSum"));
			
		} catch (JSONException e) {
			e.printStackTrace();
		}
		
		lv_roommate = (ListView) findViewById(R.id.lv_roommate);
		lv_roommate.setAdapter(new MyAdapter());
		
		bt_rank = (Button) findViewById(R.id.bt_rank);
		bt_rank.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				Intent intent = new Intent(DormInfoActivity3.this,DormRankActivity.class);
				Bundle extras = new Bundle();
				System.out.println("haahahahahahha");
				extras.putString("ID", ID);
				try {
					extras.putString("dormitoryID", jsonObject.getString("dormitoryID"));
					extras.putString("buildingID", jsonObject.getString("buildingID"));
				} catch (JSONException e) {
					e.printStackTrace();
				}
				intent.putExtras(extras);
				System.out.println("DormInfoActivity3============"+jsonString);
				startActivity(intent);
			}
		});

	}
	
	class MyAdapter extends BaseAdapter {
		// 把条目需要使用到的所有组件封装在这个类中
		class ViewHolder {
			TextView tv_name;
			TextView tv_bedNum;
		}

		@Override
		public int getCount() {
			return studentList.size();
		}

		// 返货一个View对象，会作为ListView的一个条目显示在界面上
		@Override
		public View getView(int position, View convertView, ViewGroup parent) {
			Student student = studentList.get(position);
			View v = null;
			ViewHolder mHolder = null;
			if (convertView == null) {
				// 如何填充的
				v = View.inflate(DormInfoActivity3.this, R.layout.item_roommate,
						null);
				// 创建viewHolder封装所有条目使用的组件
				mHolder = new ViewHolder();
				mHolder.tv_name = (TextView) v.findViewById(R.id.tv_name);
				mHolder.tv_bedNum = (TextView) v.findViewById(R.id.tv_bedNum);
				// 把viewHolder封装至view对象中，这样view被缓存时，viewHolder也就被缓存了
				v.setTag(mHolder);
			} else {
				v = convertView;
				// 从view中取出保存的viewHolder，viewHolder中就有所有的组件对象，不需要再去findViewById
				mHolder = (ViewHolder) v.getTag();
			}
			// 给条目中的每个组件设置显示的内容
			mHolder.tv_name.setText(student.getName());
			mHolder.tv_bedNum.setText(student.getBedNum()+"");
			
			return v;
		}

		@Override
		public Object getItem(int position) {
			return studentList.get(position);
		}

		@Override
		public long getItemId(int position) {
			return position;
		}
	}

}
