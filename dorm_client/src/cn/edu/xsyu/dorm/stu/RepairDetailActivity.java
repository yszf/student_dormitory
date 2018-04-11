package cn.edu.xsyu.dorm.stu;

import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import cn.edu.xsyu.dorm.R;

public class RepairDetailActivity extends Activity{
	private EditText et_startTime;
	private EditText et_lastTime;
	private EditText et_contacts;
	private EditText et_buildingID;
	private EditText et_dormitoryID;
	private EditText et_issuer;
	private EditText et_serviceManID;
	private RadioGroup rg_state;
	private EditText et_reason;
	private RadioButton rb_state1;
	private RadioButton rb_state2;
	private RadioButton rb_state3;
	private RadioButton rb_state4;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_repairdetail);
		
		Intent intent = getIntent();
		String ID = intent.getStringExtra("ID");
		
		et_startTime = (EditText) findViewById(R.id.et_startTime);
		et_lastTime = (EditText) findViewById(R.id.et_lastTime);
		et_contacts = (EditText) findViewById(R.id.et_contacts);
		et_buildingID = (EditText) findViewById(R.id.et_buildingID);
		et_dormitoryID = (EditText) findViewById(R.id.et_dormitoryID);
		et_issuer = (EditText) findViewById(R.id.et_issuer);
		et_serviceManID = (EditText) findViewById(R.id.et_serviceManID);
		et_reason = (EditText) findViewById(R.id.et_reason);
		
		rg_state = (RadioGroup)findViewById(R.id.rg_state);
		rb_state1 = (RadioButton)findViewById(R.id.rb_state1);
		rb_state2 = (RadioButton)findViewById(R.id.rb_state2);
		rb_state3 = (RadioButton)findViewById(R.id.rb_state3);
		rb_state4 = (RadioButton)findViewById(R.id.rb_state4);
				
		
		String jsonString = intent.getStringExtra("jsonString");
		System.out.println("jsonString-------------"+jsonString);
		JSONObject jsonObject;
		try {
			jsonObject = new JSONObject(jsonString);
			et_startTime.setText(jsonObject.getString("startTime"));
			et_lastTime.setText(jsonObject.getString("lastTime"));
			et_contacts.setText(jsonObject.getString("contacts"));
			et_buildingID.setText(jsonObject.getString("buildingID"));
			et_dormitoryID.setText(jsonObject.getString("dormitoryID"));
			et_issuer.setText(jsonObject.getString("issuer"));
			et_serviceManID.setText(jsonObject.getString("serviceManID"));
			et_reason.setText(jsonObject.getString("reason"));
			int state = Integer.parseInt(jsonObject.getString("dealState"));
			switch (state) {
			case 1:
				rb_state1.setChecked(true);
				break;
			case 2:
				rb_state2.setChecked(true);
				break;
			case 3:
				rb_state3.setChecked(true);
				break;
			case 4:
				rb_state4.setChecked(true);
				break;
			default:
				break;
			}
		} catch (JSONException e) {
			e.printStackTrace();
		}
		
	}

}
