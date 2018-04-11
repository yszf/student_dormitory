package cn.edu.xsyu.dorm.manager;

import java.util.List;

import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TableLayout;
import android.widget.TableRow;
import cn.edu.xsyu.dorm.R;
import cn.edu.xsyu.dorm.domain.BuildDorm;
import cn.edu.xsyu.dorm.utils.JSONTools;
import cn.edu.xsyu.dorm.utils.Tools;

public class DormInfoActivity2 extends Activity {

	private EditText et_buildingID;
	private String ID;
	private String jsonString;
	private List<BuildDorm> buildDorms;
	private TableLayout table;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_dorminfo2);
		
		Intent intent = getIntent();
		ID = intent.getStringExtra("ID");
		System.out.println("DormInfoActivity2 ID ====="+ID);
		jsonString = intent.getStringExtra("jsonString");
		System.out.println("DormInfoActivity2 jsonString ====="+jsonString);
		
		et_buildingID = (EditText) findViewById(R.id.et_buildingID);
		try {
			JSONObject jsonObject = new JSONObject(jsonString);
			et_buildingID.setText(jsonObject.getInt("buildingID")+"");
		} catch (JSONException e) {
			e.printStackTrace();
		}
		
		buildDorms = JSONTools.getBuildDorms("buildDorms", jsonString);
		System.out.println("buildDorms----------"+buildDorms);
		
		table = (TableLayout)findViewById(R.id.tablelayout);
		
		TableRow.LayoutParams params = new TableRow.LayoutParams(0,150);//表格行属性
        params.weight = 1;//每个按钮的宽度一样，高度为100px
        params.gravity = Gravity.CENTER_VERTICAL;//按钮竖直居中
        params.setMargins(2, 2, 2, 2);//按钮的上下左右间距均为2px
        
        int size = buildDorms.size();
        int left = size % 5;
        System.out.println("size ======="+size+"======left====="+left);
        int row = 0;
        if(size / 5 == 0){
        	row = 1;
        }else{
        	row = (left == 0) ? left : left + 1;   
        }
        
        for(int i = 0; i<row; i++){
            TableRow tr = new TableRow(this);//创建表格行
            for(int j = 0;j < 5 ; j++){
            	if(i == row - 1 && j == left){
            		break;
            	}
                final Button btn  = new Button(this);
                btn.setText(buildDorms.get(i*5+j).getDormitoryID()+"");
                btn.setOnClickListener(new OnClickListener(){
                    @Override
                    public void onClick(View v){
                        //文字点击事件
                    	new Thread(){
        					public void run() {
        						String path = "http://192.168.1.106:8080/dorm_server/queryDormInfoServlet3";
        						System.out.println("path ====== "+path);
        						System.out.println("ID-------------"+ID);
        						JSONObject js = new JSONObject();
        						
        						try {
        							js.put("ID", ID);
        							js.put("buildingID", et_buildingID.getText().toString());
        							js.put("dormitoryID", btn.getText().toString());
        						} catch (JSONException e) {
        							e.printStackTrace();
        						}
        						String content = String.valueOf(js);
        						String jsonString = Tools.getJsonContent(path, content);
        						Intent intent = new Intent(DormInfoActivity2.this, DormInfoActivity3.class);	
        						Bundle extras = new Bundle();
        						extras.putString("ID", ID);
        						extras.putString("buildingID", et_buildingID.getText().toString());
        						extras.putString("dormitoryID", btn.getText().toString());
        						extras.putString("jsonString",jsonString);
        						System.out.println("DormInfoActivity2============"+jsonString);
        						intent.putExtras(extras);
        						startActivity(intent);
        					}
        				}.start();
                    }
                });      
                tr.addView(btn,params);//向行中添加按钮
            }
            table.addView(tr);//将行添加到表格中
        }
      //最新排名
  		Button bt_search = (Button)findViewById(R.id.bt_search);
  		bt_search.setOnClickListener(new OnClickListener() {
  			@Override
  			public void onClick(View arg0) {
  				new Thread(){
  					private int level;

  					public void run() {
  						String path = "http://192.168.1.106:8080/dorm_server/queryDormsServlet2";
  						System.out.println("path ====== "+path);
  						System.out.println("ID-------------"+ID);
  						level = 1;
  						JSONObject js = new JSONObject();
  						try {
  							js.put("level", level);
  							js.put("ID", ID);
  						} catch (JSONException e) {
  							e.printStackTrace();
  						}
  						String content = String.valueOf(js);
  						String jsonString2 = Tools.getJsonContent(path, content);
  						
  						Intent intent = new Intent(DormInfoActivity2.this, LatestRankActivity2.class);
  						Bundle extras = new Bundle();
  						extras.putString("jsonString",jsonString2);
  						extras.putString("ID", ID);
  						extras.putString("level",level+"");
  						System.out.println("DormInfoActivity2============"+jsonString2);
  						intent.putExtras(extras);
  						startActivity(intent);
  					};
  				}.start();
  			
  			}
  		});
	}
}
