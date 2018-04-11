package cn.edu.xsyu.dorm.stu;

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

import com.loopj.android.image.SmartImageView;

public class StuInfoActivity extends Activity {
	private EditText et_id;
	private EditText et_name;
	private EditText et_sex;
	private EditText et_email;
	private EditText et_phone;
	private EditText et_buildingid;
	private EditText et_dormitoryid;
	private EditText et_bedNum;
	private EditText et_roomheader;
	private EditText et_academy;
	private EditText et_className;
	private SmartImageView siv;
	private String jsonString;
	private String ID;
	private String role;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_stuinfo);

		Intent intent = getIntent();
		ID = intent.getStringExtra("ID");
		role = intent.getStringExtra("role");
		System.out.println("StuInfoActivity role ======="+role);

		siv = (SmartImageView) findViewById(R.id.siv);
		// «Î«ÛÕ¯¬ÁÕº∆¨
		siv.setImageUrl("http://192.168.1.106:8080/stu.png");

		et_id = (EditText) findViewById(R.id.et_id);
		et_name = (EditText) findViewById(R.id.et_name);
		et_sex = (EditText) findViewById(R.id.et_sex);
		et_email = (EditText) findViewById(R.id.et_email);
		et_phone = (EditText) findViewById(R.id.et_phone);
		et_buildingid = (EditText) findViewById(R.id.et_buildingid);
		et_dormitoryid = (EditText) findViewById(R.id.et_dormitoryid);
		et_bedNum = (EditText) findViewById(R.id.et_bedNum);
		et_roomheader = (EditText) findViewById(R.id.et_roomheader);
		et_academy = (EditText) findViewById(R.id.et_academy);
		et_className = (EditText) findViewById(R.id.et_className);

		jsonString = intent.getStringExtra("jsonString");
		System.out.println("StuInfoActivity------------" + jsonString);
		try {
			JSONObject jo = new JSONObject(jsonString);
			et_id.setText(jo.getString("studentID"));
			et_name.setText(jo.getString("name"));
			et_sex.setText(jo.getString("sex"));
			et_email.setText(jo.getString("email"));
			et_phone.setText(jo.getString("phone"));
			et_buildingid.setText(jo.getString("buildingID"));
			et_dormitoryid.setText(jo.getString("dormitoryID"));
			et_bedNum.setText(jo.getString("bedNum"));
			et_roomheader.setText(jo.getString("roomheader"));
			et_academy.setText(jo.getString("academy"));
			et_className.setText(jo.getString("className"));
		} catch (JSONException e) {
			e.printStackTrace();
		}

		// ±‡º≠
		Button edit = (Button) findViewById(R.id.bt_edit);
		edit.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View arg0) {
				new Thread() {
					public void run() {
						Intent edit = new Intent(StuInfoActivity.this,
								EditStuInfoActivity.class);
						Bundle extras = new Bundle();
						extras.putString("jsonString", jsonString);
						edit.putExtras(extras);
						startActivity(edit);
					}
				}.start();
			}
		});

		// …Ë÷√
		Button change = (Button) findViewById(R.id.bt_change);
		change.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				Intent chge = new Intent(StuInfoActivity.this,
						ManifestActivity.class);
				Bundle extras = new Bundle();
				extras.putString("ID", ID);
				extras.putString("role", role);
				chge.putExtras(extras);
				StuInfoActivity.this.startActivity(chge);
			}
		});
		// ∑µªÿ ◊“≥
		Button sys = (Button) findViewById(R.id.syspage_mainpage);
		sys.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View arg0) {
				Intent intent = new Intent(StuInfoActivity.this,
						SysPageActivity.class);
				Bundle extras = new Bundle();
				extras.putString("ID", ID);
				extras.putString("role", role);
				intent.putExtras(extras);
				startActivity(intent);
			}
		});
	}

}
