package cn.edu.xsyu.dorm.stu;

import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;
import cn.edu.xsyu.dorm.R;

public class RuleDetailActivity extends Activity {
	private TextView tv_title;
	private TextView tv_issuer;
	private TextView tv_time;
	private TextView tv_content;
	private String ID;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_ruledetail);

		Intent intent = getIntent();
		ID = intent.getStringExtra("ID");

		tv_title = (TextView) findViewById(R.id.tv_title);
		tv_issuer = (TextView) findViewById(R.id.tv_issuer);
		tv_time = (TextView) findViewById(R.id.tv_time);
		tv_content = (TextView) findViewById(R.id.tv_content);

		String jsonString = intent.getStringExtra("jsonString");
		JSONObject jsonObject;
		try {
			jsonObject = new JSONObject(jsonString);
			tv_title.setText(jsonObject.getString("title"));
			tv_issuer.setText(jsonObject.getString("issuer"));
			tv_time.setText(jsonObject.getString("time"));
			tv_content.setText(jsonObject.getString("content"));
		} catch (JSONException e) {
			e.printStackTrace();
		}

	}

}
