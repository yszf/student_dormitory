package cn.edu.xsyu.dorm;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import cn.edu.xsyu.dorm.R;
import cn.edu.xsyu.dorm.stu.AboutusActivity;
import cn.edu.xsyu.dorm.stu.ChangepwdActivity;

public class ManifestActivity extends Activity {

	private String role;
	private String ID;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_manifest);
		Intent intent = getIntent();
		role = intent.getStringExtra("role");
		System.out.println("ManifestActivity role ======="+role);
		ID = intent.getStringExtra("ID");
		
		//修改密码
		Button changepwd = (Button)findViewById(R.id.manifest_changepwd);
		changepwd.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				Intent pwd = new Intent(ManifestActivity.this,ChangepwdActivity.class);
				Bundle extras = new Bundle();
				extras.putString("ID", ID);
				extras.putString("role", role);
				pwd.putExtras(extras);
				startActivity(pwd);
			}
		});
		
		//退出
		Button  exit = (Button)findViewById(R.id.manifest_exit);
		exit.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				Intent intent = new Intent(ManifestActivity.this, LoginActivity.class);
				intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
				startActivity(intent);
			}
		});
		
		//关于我们
		Button aboutus = (Button)findViewById(R.id.manifest_aboutus);
		aboutus.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				Intent about = new Intent(ManifestActivity.this,AboutusActivity.class);
				startActivity(about);
			}
		});	
	}


}
