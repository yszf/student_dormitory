package cn.edu.xsyu.dorm.stu;

import java.util.ArrayList;
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
import cn.edu.xsyu.dorm.domain.DormRule;
import cn.edu.xsyu.dorm.utils.JSONTools;

import com.loopj.android.image.SmartImageView;

public class DormRuleActivity extends Activity implements OnItemClickListener{

	private List<DormRule> ruleList;
	private ListView lv_rule;
	private String ID;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_dormrule);
		
		Intent intent = getIntent();
		ID = intent.getStringExtra("ID");
		
		ruleList = new ArrayList<DormRule>();

		getRulesInfo();

		lv_rule = (ListView) findViewById(R.id.lv_rule);
		//设置显示内容
		lv_rule.setAdapter(new MyAdapter());
		lv_rule.setOnItemClickListener(this);
	}
	
	public void onItemClick(AdapterView<?> parent, View view, int position,
			long id) {
		int pos = position;
		DormRule dormRule = ruleList.get(pos);
		JSONObject jsonObject = new JSONObject();
		try {
			jsonObject.put("content", dormRule.getContent());
			jsonObject.put("issuer", dormRule.getIssuer());
			jsonObject.put("ruleID", dormRule.getRuleID());
			jsonObject.put("time", dormRule.getTime());
			jsonObject.put("title", dormRule.getTitle());
			String jsonString = String.valueOf(jsonObject);
			Intent intent = new Intent(DormRuleActivity.this,RuleDetailActivity.class);
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

	private void getRulesInfo() {
		Intent intent = getIntent();
		String jsonString = intent.getStringExtra("jsonString");
		ruleList = JSONTools.getRules("dormRules", jsonString);
		System.out.println("ruleList============="+ruleList);
	}
	
	class MyAdapter extends BaseAdapter {
		// 把条目需要使用到的所有组件封装在这个类中
		class ViewHolder {
			TextView tv_title;
			TextView tv_issuer;
			TextView tv_time;
			SmartImageView rule_siv;
		}

		@Override
		public int getCount() {
			return ruleList.size();
		}

		// 返货一个View对象，会作为ListView的一个条目显示在界面上
		@Override
		public View getView(int position, View convertView, ViewGroup parent) {
			DormRule dormRule = ruleList.get(position);
			View v = null;
			ViewHolder mHolder = null;
			if (convertView == null) {
				// 如何填充的
				v = View.inflate(DormRuleActivity.this, R.layout.item_rule,
						null);
				// 创建viewHolder封装所有条目使用的组件
				mHolder = new ViewHolder();
				mHolder.tv_title = (TextView) v.findViewById(R.id.tv_title);
				mHolder.tv_issuer = (TextView) v.findViewById(R.id.tv_issuer);
				mHolder.tv_time = (TextView) v.findViewById(R.id.tv_time);
				mHolder.rule_siv = (SmartImageView) v.findViewById(R.id.rule_siv);
				// 把viewHolder封装至view对象中，这样view被缓存时，viewHolder也就被缓存了
				v.setTag(mHolder);
			} else {
				v = convertView;
				// 从view中取出保存的viewHolder，viewHolder中就有所有的组件对象，不需要再去findViewById
				mHolder = (ViewHolder) v.getTag();
			}
			// 给条目中的每个组件设置显示的内容
			mHolder.tv_title.setText(dormRule.getTitle());
			mHolder.tv_issuer.setText(dormRule.getIssuer());
			mHolder.tv_time.setText(dormRule.getTime());
			mHolder.rule_siv.setImageUrl("http://192.168.1.106:8080/stu.png");
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
