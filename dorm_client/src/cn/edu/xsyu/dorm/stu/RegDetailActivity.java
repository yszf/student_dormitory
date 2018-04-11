package cn.edu.xsyu.dorm.stu;

import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;
import cn.edu.xsyu.dorm.R;

public class RegDetailActivity extends Activity {

	private TextView tv_reason;
	private TextView tv_destination;
	private TextView tv_regTime;
	private TextView tv_leaveTime;
	private TextView tv_backTime;
	private TextView tv_emergenceContact;
	private TextView tv_emergenceTel;
	private TextView tv_phone;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_regdetail);
		
		Intent intent = getIntent();
		String ID = intent.getStringExtra("ID");
		
		tv_reason = (TextView) findViewById(R.id.tv_reason);
		tv_destination = (TextView) findViewById(R.id.tv_destination);
		tv_regTime = (TextView) findViewById(R.id.tv_regTime);
		tv_phone = (TextView) findViewById(R.id.tv_phone);
		tv_leaveTime = (TextView) findViewById(R.id.tv_leaveTime);
		tv_backTime = (TextView) findViewById(R.id.tv_backTime);
		tv_emergenceContact = (TextView) findViewById(R.id.tv_emergenceContact);
		tv_emergenceTel = (TextView) findViewById(R.id.tv_emergenceTel);
		
		String jsonString = intent.getStringExtra("jsonString");
		
		JSONObject jsonObject;
		try {
			jsonObject = new JSONObject(jsonString);
			tv_reason.setText(jsonObject.getString("reason"));
			tv_destination.setText(jsonObject.getString("destination"));
			tv_regTime.setText(jsonObject.getString("regTime"));
			tv_phone.setText(jsonObject.getString("phone"));
			tv_leaveTime.setText(jsonObject.getString("leaveTime"));
			tv_backTime.setText(jsonObject.getString("backTime"));
			tv_emergenceContact.setText(jsonObject.getString("emergenceContact"));
			tv_emergenceTel.setText(jsonObject.getString("emergenceTel"));
		} catch (JSONException e) {
			e.printStackTrace();
		}
	}

}
