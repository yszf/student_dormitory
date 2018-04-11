package cn.edu.xsyu.dorm.manager;

import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import cn.edu.xsyu.dorm.ManifestActivity;
import cn.edu.xsyu.dorm.R;

public class ManagerInfoActivity extends Activity {
	private EditText et_name;
	private EditText et_email;
	private EditText et_buildingID;
	private EditText et_time;
	private EditText et_buildingManagerID;
	private String jsonString;
	private String ID;
	private String role;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_managerinfo);

		Intent intent = getIntent();
		ID = intent.getStringExtra("ID");
		role = intent.getStringExtra("role");

		et_name = (EditText) findViewById(R.id.et_name);
		et_email = (EditText) findViewById(R.id.et_email);
		et_buildingID = (EditText) findViewById(R.id.et_buildingID);
		et_time = (EditText) findViewById(R.id.et_time);
		et_buildingManagerID = (EditText) findViewById(R.id.et_buildingManagerID);

		jsonString = intent.getStringExtra("jsonString");
		System.out.println("ManagerInfoActivity------------" + jsonString);
		
		try {
			JSONObject jo = new JSONObject(jsonString);
			et_name.setText(jo.getString("name"));
			et_email.setText(jo.getString("email"));
			et_buildingID.setText(jo.getString("buildingID"));
			et_time.setText(jo.getString("time"));
			et_buildingManagerID.setText(jo.getString("buildingManagerID"));
		} catch (JSONException e) {
			e.printStackTrace();
		}
		
		// ±‡º≠
		Button bt_edit = (Button) findViewById(R.id.bt_edit);
		bt_edit.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View arg0) {
				new Thread() {
					public void run() {
						Intent intent = new Intent(ManagerInfoActivity.this,EditManagerInfoActivity.class);
						Bundle extras = new Bundle();
						extras.putString("jsonString", jsonString);
						extras.putString("ID", ID);
						extras.putString("role", role);
						intent.putExtras(extras);
						startActivity(intent);
					}
				}.start();
			}
		});
		
		// …Ë÷√
		Button change = (Button) findViewById(R.id.bt_change);
		change.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View arg0) {
				Intent chge = new Intent(ManagerInfoActivity.this,ManifestActivity.class);
				Bundle extras = new Bundle();
				extras.putString("ID", ID);
				extras.putString("role", role);
				chge.putExtras(extras);
				ManagerInfoActivity.this.startActivity(chge);
			}
		});
		
		// ∑µªÿ ◊“≥
		Button sys = (Button) findViewById(R.id.bt_main);
		sys.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View arg0) {
				Intent intent = new Intent(ManagerInfoActivity.this,
						SysPageActivity2.class);
				Bundle extras = new Bundle();
				extras.putString("ID", ID);
				extras.putString("role", role);
				intent.putExtras(extras);
				startActivity(intent);
			}
		});
	}

}
