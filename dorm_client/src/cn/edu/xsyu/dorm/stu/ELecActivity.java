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
import cn.edu.xsyu.dorm.R;
import cn.edu.xsyu.dorm.utils.Tools;

public class ELecActivity extends Activity {
	private EditText et_buildingID;
	private EditText et_dormitoryID;
	private EditText et_remainElec;
	private EditText et_remainMoney;
	private EditText et_rechargeMoney;
	private EditText et_rechargeTime;
	private String ID;
	private String jsonString;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_elec);
		
		et_buildingID = (EditText) findViewById(R.id.et_buildingID);
		et_dormitoryID = (EditText) findViewById(R.id.et_dormitoryID);
		et_remainElec = (EditText) findViewById(R.id.et_remainElec);
		et_remainMoney = (EditText) findViewById(R.id.et_remainMoney);
		et_rechargeMoney = (EditText) findViewById(R.id.et_rechargeMoney);
		et_rechargeTime = (EditText) findViewById(R.id.et_rechargeTime);
		
		Intent intent = getIntent();
		ID = intent.getStringExtra("ID");
		jsonString = intent.getStringExtra("jsonString");
			
		try {
			JSONObject jsonObject = new JSONObject(jsonString);
			et_buildingID.setText(""+jsonObject.getInt("buildingID"));
			et_dormitoryID.setText(""+jsonObject.getInt("dormitoryID"));
			et_remainElec.setText(""+jsonObject.getDouble("remainElec"));
			et_remainMoney.setText(""+jsonObject.getDouble("remainMoney"));
			et_rechargeMoney.setText(""+jsonObject.getDouble("rechargeMoney"));
			et_rechargeTime.setText(jsonObject.getString("rechargeTime"));
		} catch (JSONException e) {
			e.printStackTrace();
		}
			
		Button bt_elec = (Button)findViewById(R.id.bt_elec);
		bt_elec.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View arg0) {
				
			}
		});
		
		Button bt_network = (Button)findViewById(R.id.bt_network);
		bt_network.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View arg0) {
				new Thread(){
					public void run() {
						String path = "http://192.168.1.106:8080/dorm_server/queryNetworkServlet";
						System.out.println("path ====== "+path);
						System.out.println("ID-------------"+ID);
						JSONObject js = new JSONObject();
						try {
							js.put("ID", ID);
						} catch (JSONException e) {
							e.printStackTrace();
						}
						String content = String.valueOf(js);
						String jsonString = Tools.getJsonContent(path, content);
						
				        System.out.println("ELecActivity-------------"+jsonString);
				        Intent intent = new Intent(ELecActivity.this,NetworkActivity.class);
						Bundle extras = new Bundle();
						extras.putString("ID", ID);
						extras.putString("jsonString", jsonString);
						intent.putExtras(extras);
						startActivity(intent);
					}
				}.start();
			}
		});
	}

}
