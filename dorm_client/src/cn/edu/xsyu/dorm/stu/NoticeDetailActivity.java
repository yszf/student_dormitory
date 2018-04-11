package cn.edu.xsyu.dorm.stu;

import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;
import cn.edu.xsyu.dorm.R;

import com.loopj.android.image.SmartImageView;

public class NoticeDetailActivity extends Activity {
	private SmartImageView siv;
	private TextView tv_title;
	private TextView tv_issuer;
	private TextView tv_issueTime;
	private TextView tv_noticeContent;
	private String ID;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_noticedetail);

		Intent intent = getIntent();
		ID = intent.getStringExtra("ID");

		siv = (SmartImageView) findViewById(R.id.siv);
		tv_title = (TextView) findViewById(R.id.tv_title);
		tv_issuer = (TextView) findViewById(R.id.tv_issuer);
		tv_issueTime = (TextView) findViewById(R.id.tv_issuetime);
		tv_noticeContent = (TextView) findViewById(R.id.tv_noticecontent);

		String jsonString = intent.getStringExtra("jsonString");
		JSONObject jsonObject;
		try {
			jsonObject = new JSONObject(jsonString);
			//siv.setImageUrl("http://192.168.72.1:8080/dd.jpg");
			tv_title.setText(jsonObject.getString("title"));
			tv_issuer.setText(jsonObject.getString("issuer"));
			tv_issueTime.setText(jsonObject.getString("time"));
			tv_noticeContent.setText(jsonObject.getString("content"));
		} catch (JSONException e) {
			e.printStackTrace();
		}

	}

}
