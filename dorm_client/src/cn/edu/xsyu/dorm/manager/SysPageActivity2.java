package cn.edu.xsyu.dorm.manager;

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
import cn.edu.xsyu.dorm.R;
import cn.edu.xsyu.dorm.domain.Notice;
import cn.edu.xsyu.dorm.stu.DormRuleActivity;
import cn.edu.xsyu.dorm.stu.SchoolRegActivity;
import cn.edu.xsyu.dorm.utils.HttpUtils;
import cn.edu.xsyu.dorm.utils.JSONTools;
import cn.edu.xsyu.dorm.utils.Tools;

import com.xubo.scrolltextview.ScrollTextView;
import com.xubo.scrolltextview.ScrollTextView.OnScrollClickListener;
import com.youth.banner.Banner;
import com.youth.banner.loader.GlideImageLoader;

public class SysPageActivity2 extends Activity {
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
		setContentView(R.layout.activity_syspage2);
		Intent intent = getIntent();
		ID = intent.getStringExtra("ID");
		role = intent.getStringExtra("role");
		
		System.out.println("SysPageActivity2 role ======="+role);
		System.out.println("SysPageActivity2==========="+ID);

		banner = (Banner) findViewById(R.id.banner);

		images = new ArrayList<String>();
		images.add("http://www.kfzimg.com/G00/M00/93/25/ooYBAFZxJHqAQyyVAAIzJzJpCFw148_b.jpg");
		images.add("http://www.kfzimg.com/G00/M00/D0/94/oYYBAFZxJHeAVgvQAAKJUlo7e_M565_b.jpg");
		images.add("http://www.kfzimg.com/G00/M00/93/24/ooYBAFZxJHOAWSxSAAOl3bbTKrI330_b.jpg");

		// ����ͼƬ������
		banner.setImageLoader(new GlideImageLoader());
		// ����ͼƬ����
		banner.setImages(images);

		// banner���÷���ȫ���������ʱ������
		banner.start();

		// ���ع�������
		text1_stv = (ScrollTextView) findViewById(R.id.text1_stv);
		initData();
		text1_stv.setTextContent(list1, listener1);

		//������Ϣ
		bt_info = (Button) findViewById(R.id.bt_info);
		bt_info.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View arg0) {
				new Thread(){
					public void run() {
						String path = "http://192.168.1.106:8080/dorm_server/queryDormInfoServlet2";
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
						System.out.println("SysPageActivity2-------------"+jsonString);
				        
						Intent intent = new Intent(SysPageActivity2.this,DormInfoActivity2.class);
						Bundle extras = new Bundle();
						extras.putString("ID", ID);
						extras.putString("jsonString", jsonString);
						intent.putExtras(extras);
						startActivity(intent);
					}
				}.start();
			}
		});
		
		//���ṫ��
		Button bt_notice = (Button) findViewById(R.id.bt_notice);
		bt_notice.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View arg0) {
				new Thread(){
					public void run() {
						String path = "http://192.168.1.106:8080/dorm_server/queryNoticeServlet";
						String jsonString = HttpUtils.getJsonContent(path);
						
						Intent intent = new Intent(SysPageActivity2.this, DormNoticeActivity2.class);
						Bundle extras = new Bundle();
						extras.putString("ID", ID);
						extras.putString("jsonString", jsonString);
						System.out.println("SysPageActivity2==========="+jsonString);
						intent.putExtras(extras);
						startActivity(intent);
					};
				}.start();
			} 
		});
		//���Լ�¼
		Button bt_message = (Button) findViewById(R.id.bt_message);
		bt_message.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View arg0) {
				new Thread(){
					public void run() {
						String path = "http://192.168.1.106:8080/dorm_server/queryMessageServlet";
						String jsonString = HttpUtils.getJsonContent(path);
						System.out.println("path--------------"+path);
						Intent intent = new Intent(SysPageActivity2.this, MessageListActivity2.class);	
						Bundle extras = new Bundle();
						extras.putString("ID", ID);
						extras.putString("jsonString",jsonString);
						System.out.println("SysPageActivity2============"+jsonString);
						intent.putExtras(extras);
						startActivity(intent);
					}
				}.start();
			}
		});
		
		//�������
		Button bt_rule = (Button) findViewById(R.id.bt_rule);
		bt_rule.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View arg0) {
				new Thread(){
					public void run() {
						String path = "http://192.168.1.106:8080/dorm_server/queryDormRuleServlet";
						String jsonString = HttpUtils.getJsonContent(path);
						
						Intent intent = new Intent(SysPageActivity2.this, DormRuleActivity.class);
						Bundle extras = new Bundle();
						extras.putString("ID", ID);
						extras.putString("jsonString", jsonString);
						System.out.println("SysPageActivity2==========="+jsonString);
						intent.putExtras(extras);
						startActivity(intent);
					};
				}.start();
			}
		});

		// ��У�Ǽ�
		Button bt_leaveReg = (Button) findViewById(R.id.bt_leaveReg);
		bt_leaveReg.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View arg0) {
				new Thread(){
					public void run() {
						String path = "http://192.168.1.106:8080/dorm_server/queryLeaveRegServlet2";
						System.out.println("path ====== "+path);
						String jsonString = HttpUtils.getJsonContent(path);
						Intent intent = new Intent(SysPageActivity2.this, SchoolRegActivity.class);
						Bundle extras = new Bundle();
						extras.putString("ID", ID);
						extras.putString("jsonString", jsonString);
						System.out.println("SysPageActivity2==========="+jsonString);
						intent.putExtras(extras);
						startActivity(intent);
					};
				}.start();
			}
		});
		// ���ᱨ��
		Button bt_repair = (Button) findViewById(R.id.bt_repair);
		bt_repair.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View arg0) {
				new Thread(){
					public void run() {
						String path = "http://192.168.1.106:8080/dorm_server/queryRepairServlet";
						System.out.println("path ====== "+path);
						System.out.println("ID-------------"+ID);
						String jsonString = HttpUtils.getJsonContent(path);
				        System.out.println("SysPageActivity2-------------"+jsonString);
				        Intent intent = new Intent(SysPageActivity2.this,RepairListActivity3.class);
						Bundle extras = new Bundle();
						extras.putString("ID", ID); 
						extras.putString("jsonString", jsonString);
						intent.putExtras(extras);
						startActivity(intent);
					}
				}.start();
			}
		});
		
		//������Ϣҳ��
		Button my = (Button) findViewById(R.id.bt_my);
		my.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View arg0) {
				new Thread(){
					public void run() {
						String path = "http://192.168.1.106:8080/dorm_server/managerInfoServlet";
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
				        System.out.println("SysPageActivity2-------------"+jsonString);
				        Intent intent = new Intent(SysPageActivity2.this,
								ManagerInfoActivity.class);
						Bundle extras = new Bundle();
						extras.putString("ID", ID);
						extras.putString("role", role);
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
				Intent intent = new Intent(SysPageActivity2.this, DormNoticeActivity2.class);
				Bundle extras = new Bundle();
				extras.putString("ID", ID);
				extras.putString("jsonString", jsonString);
				System.out.println("SysPageActivity2==========="+jsonString);
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
				click();
			}
		});
		listener1.add(new OnScrollClickListener() {
			@Override
			public void onClick() {
				click();
			}
		});
		listener1.add(new OnScrollClickListener() {
			@Override
			public void onClick() {
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
		// ��ʼ�ֲ�
		banner.startAutoPlay();
	}

	@Override
	protected void onStop() {
		super.onStop();
		// �����ֲ�
		banner.stopAutoPlay();
		text1_stv.stopTextScroll();
	}
	
	 @SuppressWarnings("deprecation")
	@Override  
	 public boolean onKeyDown(int keyCode, KeyEvent event){  
		  if (keyCode == KeyEvent.KEYCODE_BACK )  
		  {  
			   // �����˳��Ի���  
			   AlertDialog isExit = new AlertDialog.Builder(this).create();  
			   // ���öԻ������  
			   isExit.setTitle("ϵͳ��ʾ");  
			   // ���öԻ�����Ϣ  
			   isExit.setMessage("ȷ��Ҫ�˳���");  
			   // ���ѡ��ť��ע�����  
			   isExit.setButton("ȷ��", listener);  
			   isExit.setButton2("ȡ��", listener);  
			   // ��ʾ�Ի���  
			   isExit.show();  
		  
		  }  
		  return false;  
	}  
	 /**�����Ի��������button����¼�*/  
	 DialogInterface.OnClickListener listener = 
			 new DialogInterface.OnClickListener(){  
		  public void onClick(DialogInterface dialog, int which){  
			   switch (which){  
			   case AlertDialog.BUTTON_POSITIVE:// "ȷ��"��ť�˳�����  
				   finish();  
				   break;  
			   case AlertDialog.BUTTON_NEGATIVE:// "ȡ��"�ڶ�����ťȡ���Ի���  
				   break;  
			   default:  
				   break;  
			   }  
		  }  
	 };
	private Button bt_info;   
}
