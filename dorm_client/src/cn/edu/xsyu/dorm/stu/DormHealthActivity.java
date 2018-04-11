package cn.edu.xsyu.dorm.stu;

import java.util.List;

import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import cn.edu.xsyu.dorm.R;
import cn.edu.xsyu.dorm.domain.Score;
import cn.edu.xsyu.dorm.utils.JSONTools;

public class DormHealthActivity extends Activity {
	private List<Score> scoreList;
	private ListView lv_score;
	private String ID;
	private String jsonString;
	private EditText et_buildingID;
	private EditText et_dormID;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_dormhealth);
		
		et_buildingID = (EditText) findViewById(R.id.et_buildingID);
		et_dormID = (EditText) findViewById(R.id.et_dormID);

		Intent intent = getIntent();
		ID = intent.getStringExtra("ID");

		getHealthInfo();		
		System.out.println("getHealthInfo-----------");
		
		lv_score = (ListView) findViewById(R.id.lv_dormhealth);
		lv_score.setAdapter(new MyAdapter());

	}

	private void getHealthInfo() {
		Intent intent = getIntent();
		jsonString = intent.getStringExtra("jsonString");
		System.out.println("DormitoryHealthActivity=========="+jsonString);
		
		try {
			JSONObject jsonObject = new JSONObject(jsonString);
			scoreList = JSONTools.getScores("scores", jsonString);
			et_buildingID.setText(jsonObject.getInt("buildingID")+"");
			et_dormID.setText(jsonObject.getInt("dormitoryID")+"");
			
		} catch (JSONException e) {
			e.printStackTrace();
		}
	}

	class MyAdapter extends BaseAdapter {
		// ����Ŀ��Ҫʹ�õ������������װ���������
		class ViewHolder {
			TextView tv_weekNum;
			TextView tv_level;
			TextView tv_isIllegal;
			TextView tv_description;
		}

		@Override
		public int getCount() {
			return scoreList.size();
		}

		// ����һ��View���󣬻���ΪListView��һ����Ŀ��ʾ�ڽ�����
		@Override
		public View getView(int position, View convertView, ViewGroup parent) {
			Score score = scoreList.get(position);
			View v = null;
			ViewHolder mHolder = null;
			if (convertView == null) {
				// �������
				v = View.inflate(DormHealthActivity.this, R.layout.item_health,
						null);
				// ����viewHolder��װ������Ŀʹ�õ����
				mHolder = new ViewHolder();
				mHolder.tv_weekNum = (TextView) v.findViewById(R.id.tv_weekNum);
				mHolder.tv_level = (TextView) v.findViewById(R.id.tv_level);
				mHolder.tv_isIllegal = (TextView) v.findViewById(R.id.tv_isIllegal);
				mHolder.tv_description = (TextView) v.findViewById(R.id.tv_description);
				// ��viewHolder��װ��view�����У�����view������ʱ��viewHolderҲ�ͱ�������
				v.setTag(mHolder);
			} else {
				v = convertView;
				// ��view��ȡ�������viewHolder��viewHolder�о������е�������󣬲���Ҫ��ȥfindViewById
				mHolder = (ViewHolder) v.getTag();
			}
			// ����Ŀ�е�ÿ�����������ʾ������
			mHolder.tv_weekNum.setText(score.getWeekNum()+"");

			switch (score.getLevel()) {
			case 1:
				mHolder.tv_level.setText("����");
				break;
			case 2:
				mHolder.tv_level.setText("����");
				break;
			case 3:
				mHolder.tv_level.setText("����");
				break;
			case 4:
				mHolder.tv_level.setText("������");
				break;
			default:
				break;
			}
			
			switch(score.getIsIllegal()){
			case 0:
				mHolder.tv_isIllegal.setText("��Υ��");
				break;
			case 1:
				mHolder.tv_isIllegal.setText("��Υ��");
				break;
			default:
				break;
			}
			mHolder.tv_description.setText(score.getDescription());
			
			return v;
		}

		@Override
		public Object getItem(int position) {
			return null;
		}

		@Override
		public long getItemId(int position) {
			return 0;
		}
	}

}
