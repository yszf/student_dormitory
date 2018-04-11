package cn.edu.xsyu.dorm.stu;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;
import cn.edu.xsyu.dorm.R;
import cn.edu.xsyu.dorm.R.id;
import cn.edu.xsyu.dorm.R.layout;
import cn.edu.xsyu.dorm.domain.Notice;
import cn.edu.xsyu.dorm.utils.HttpUtils;
import cn.edu.xsyu.dorm.utils.JSONTools;
import cn.edu.xsyu.dorm.utils.Tools;

import com.xubo.scrolltextview.ScrollTextView;
import com.xubo.scrolltextview.ScrollTextView.OnScrollClickListener;
import com.youth.banner.Banner;
import com.youth.banner.loader.GlideImageLoader;

public class SysPageActivity extends Activity {
	Banner banner;
	List<String> images;
	List<String> list1 = new ArrayList<String>();
	List<OnScrollClickListener> listener1 = new ArrayList<OnScrollClickListener>();
	ScrollTextView text1_stv;
	private String ID;
	private String role;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_syspage);
		Intent intent = getIntent();
		ID = intent.getStringExtra("ID");
		role = intent.getStringExtra("role");
		
		System.out.println("SysPageActivity role ======="+role);
		System.out.println("SysPageActivity==========="+ID);

		banner = (Banner) findViewById(R.id.banner);

		images = new ArrayList<String>();
		images.add("http://www.kfzimg.com/G00/M00/93/25/ooYBAFZxJHqAQyyVAAIzJzJpCFw148_b.jpg");
		images.add("http://www.kfzimg.com/G00/M00/D0/94/oYYBAFZxJHeAVgvQAAKJUlo7e_M565_b.jpg");
		images.add("http://www.kfzimg.com/G00/M00/93/24/ooYBAFZxJHOAWSxSAAOl3bbTKrI330_b.jpg");

		// 设置图片加载器
		banner.setImageLoader(new GlideImageLoader());
		// 设置图片集合
		banner.setImages(images);

		// banner设置方法全部调用完毕时最后调用
		banner.start();

		// 加载滚动文字
		text1_stv = (ScrollTextView) findViewById(R.id.text1_stv);
		initData();
		text1_stv.setTextContent(list1, listener1);

		// 宿舍卫生
		ImageView dormhealth = (ImageView) findViewById(R.id.sysypage_imageview1);
		dormhealth.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View arg0) {
				new Thread(){
					public void run() {
						String path = "http://192.168.1.106:8080/dorm_server/dormHealthServlet";
						System.out.println("path------------------"+path);
						System.out.println("ID-------------"+ID);
						JSONObject js = new JSONObject();
						try {
							js.put("ID", ID);
						} catch (JSONException e) {
							e.printStackTrace();
						}
						String content = String.valueOf(js);
						String jsonString = Tools.getJsonContent(path,content);
						System.out.println("SysPageActivity-------------"+jsonString);
				        
						Intent intent = new Intent(SysPageActivity.this,DormHealthActivity.class);
						Bundle extras = new Bundle();
						extras.putString("role", role);
						extras.putString("ID", ID);
						extras.putString("jsonString", jsonString);
						intent.putExtras(extras);
						startActivity(intent);
					}
				}.start();
			}
		});
		
		//日常管理
		ImageView dailymanage = (ImageView) findViewById(R.id.sysypage_imageview2);
		dailymanage.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				Intent intent = new Intent(SysPageActivity.this,DailyMainPageActivity.class);
				Bundle extras = new Bundle();
				extras.putString("role", role);
				extras.putString("ID", ID);
				intent.putExtras(extras);
				startActivity(intent);
			} 
		});
		//宿舍报修
		ImageView domrservice = (ImageView) findViewById(R.id.sysypage_imageview3);
		domrservice.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View arg0) {
				new Thread(){
					public void run() {
						String path = "http://192.168.1.106:8080/dorm_server/queryRepairServlet";
						System.out.println("path ====== "+path);
						System.out.println("ID-------------"+ID);
						String jsonString = HttpUtils.getJsonContent(path);
				        System.out.println("SysPageActivity-------------"+jsonString);
				        Intent intent = new Intent(SysPageActivity.this,RepairListActivity.class);
						Bundle extras = new Bundle();
						extras.putString("ID", ID); 
						extras.putString("jsonString", jsonString);
						intent.putExtras(extras);
						startActivity(intent);
					}
				}.start();
			}
		});
		//电网
		ImageView elecnet = (ImageView) findViewById(R.id.sysypage_imageview4);
		elecnet.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View arg0) {
				new Thread(){
					public void run() {
						String path = "http://192.168.1.106:8080/dorm_server/queryElecServlet";
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
				        System.out.println("SysPageActivity-------------"+jsonString);
				        
				        Intent intent = new Intent(SysPageActivity.this,ELecActivity.class);
						Bundle extras = new Bundle();
						extras.putString("ID", ID);
						extras.putString("jsonString", jsonString);
						intent.putExtras(extras);
						startActivity(intent);
					}
				}.start();
			}
		});

		// 更多
		ImageView more = (ImageView) findViewById(R.id.sysypage_imageview5);
		more.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				Toast.makeText(SysPageActivity.this, "请期待我们为您提供更多服务",
						Toast.LENGTH_SHORT).show();
			}
		});
		
		//点击进入个人信息页面
		Button my = (Button) findViewById(R.id.syspage_my);
		my.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View arg0) {
				new Thread(){
					public void run() {
						String path = "http://192.168.1.106:8080/dorm_server/stuInfoServlet";
						System.out.println("path------------------"+path);
						System.out.println("ID-------------"+ID);
						JSONObject js = new JSONObject();
						try {
							js.put("ID", ID);
						} catch (JSONException e) {
							e.printStackTrace();
						}
						String content = String.valueOf(js);
						String jsonString = Tools.getJsonContent(path,content);
				        System.out.println("SysPageActivity-------------"+jsonString);
				        Intent intent = new Intent(SysPageActivity.this,
								StuInfoActivity.class);
						Bundle extras = new Bundle();
						extras.putString("role", role);
						extras.putString("ID", ID);
						extras.putString("jsonString", jsonString);
						intent.putExtras(extras);
						startActivity(intent);
					}
				}.start();

			}
		});

	}
	
	public void click(){
		new Thread(){
			public void run() {
				String path = "http://192.168.1.106:8080/dorm_server/queryNoticeServlet";
				String jsonString = HttpUtils.getJsonContent(path);
				Intent intent = new Intent(SysPageActivity.this, DormNoticeActivity.class);
				Bundle extras = new Bundle();
				extras.putString("ID", ID);
				extras.putString("jsonString", jsonString);
				System.out.println("SysPageActivity==========="+jsonString);
				intent.putExtras(extras);
				startActivity(intent);
			};
		}.start();
	}
	private void initData() {
		Intent intent = getIntent();
		String jsonString = intent.getStringExtra("jsonString");
		
    	List<Notice> list = JSONTools.getNotices("notices", jsonString);
    	
		for(int i=0;i<list.size();++i)
		{
			list1.add(list.get(i).getTitle());
		}
		
		listener1.add(new OnScrollClickListener() {
			@Override
			public void onClick() {
			//	Toast.makeText(SysPageActivity.this, "a1", Toast.LENGTH_SHORT).show();
				click();
			}
		});
		listener1.add(new OnScrollClickListener() {
			@Override
			public void onClick() {
			//	Toast.makeText(SysPageActivity.this, "a2", Toast.LENGTH_SHORT).show();
				click();
			}
		});
		listener1.add(new OnScrollClickListener() {
			@Override
			public void onClick() {
			//	Toast.makeText(SysPageActivity.this, "a3", Toast.LENGTH_SHORT).show();
				click();
			}
		});
	}

	@Override
	protected void onRestart() {
		super.onRestart();
		text1_stv.startTextScroll();
	}

	@Override
	protected void onStart() {
		super.onStart();
		// 开始轮播
		banner.startAutoPlay();
	}

	@Override
	protected void onStop() {
		super.onStop();
		// 结束轮播
		banner.stopAutoPlay();
		text1_stv.stopTextScroll();
	}
	
	 @SuppressWarnings("deprecation")
	@Override  
	 public boolean onKeyDown(int keyCode, KeyEvent event){  
		  if (keyCode == KeyEvent.KEYCODE_BACK )  
		  {  
			   // 创建退出对话框  
			   AlertDialog isExit = new AlertDialog.Builder(this).create();  
			   // 设置对话框标题  
			   isExit.setTitle("系统提示");  
			   // 设置对话框消息  
			   isExit.setMessage("确定要退出吗？");  
			   // 添加选择按钮并注册监听  
			   isExit.setButton("确定", listener);  
			   isExit.setButton2("取消", listener);  
			   // 显示对话框  
			   isExit.show();  
		  
		  }  
		  return false;  
	}  
	 /**监听对话框里面的button点击事件*/  
	 DialogInterface.OnClickListener listener = 
			 new DialogInterface.OnClickListener(){  
		  public void onClick(DialogInterface dialog, int which){  
			   switch (which){  
			   case AlertDialog.BUTTON_POSITIVE:// "确认"按钮退出程序  
				   finish();  
				   break;  
			   case AlertDialog.BUTTON_NEGATIVE:// "取消"第二个按钮取消对话框  
				   break;  
			   default:  
				   break;  
			   }  
		  }  
	 };   
}
