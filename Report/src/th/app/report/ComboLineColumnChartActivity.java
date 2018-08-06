package th.app.report;


import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import com.jeremyfeinstein.slidingmenu.lib.SlidingMenu;

import android.app.ActionBar;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.DatePickerDialog.OnDateSetListener;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.text.InputType;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.view.View.OnClickListener;
import android.view.View.OnTouchListener;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import lecho.lib.hellocharts.formatter.ColumnChartValueFormatter;
import lecho.lib.hellocharts.formatter.SimpleColumnChartValueFormatter;
import lecho.lib.hellocharts.listener.ComboLineColumnChartOnValueSelectListener;
import lecho.lib.hellocharts.model.Axis;
import lecho.lib.hellocharts.model.AxisValue;
import lecho.lib.hellocharts.model.Column;
import lecho.lib.hellocharts.model.ColumnChartData;
import lecho.lib.hellocharts.model.ComboLineColumnChartData;
import lecho.lib.hellocharts.model.Line;
import lecho.lib.hellocharts.model.LineChartData;
import lecho.lib.hellocharts.model.PointValue;
import lecho.lib.hellocharts.model.SubcolumnValue;
import lecho.lib.hellocharts.util.ChartUtils;
import lecho.lib.hellocharts.view.ComboLineColumnChartView;


public class ComboLineColumnChartActivity extends FragmentActivity {

	private String connet = "jdbc:mysql://121.196.222.216:3306/THWeld?"
		    + "user=root&password=123456&useUnicode=true&characterEncoding=UTF8"; 
	private EditText et1;
	private EditText et2;
	public String aa;
	public String bb;
	public String aa1="2017-12-20";
	public String bb1="2019-03-01";
	public ArrayList<String> listarray1 = new ArrayList<String>();
	public Button button1,button2,button3,button4,button5,button6,button7;
	public String sendtype;
	public boolean entertype=false;
	public boolean checktype=false;
	public boolean waittype=false;
	public String sendname;
	public SlidingMenu slidingMenu;
	private java.sql.Connection conn = null;
	private java.sql.Statement stmt =null;
	private Dialog progressDialog;
	
	private String Command1;

	private String command11="SELECT SUM(tb_work.fworktime),tb_welding_machine.fequipment_no FROM tb_work INNER JOIN tb_welding_machine ON tb_welding_machine.fid = tb_work.fmachine_id INNER JOIN tb_insframework ON tb_work.fitemid = tb_insframework.fid INNER JOIN tb_insframework ins1 ON ins1.fid = tb_insframework.fparent INNER JOIN tb_insframework ins2 ON ins2.fid = tb_insframework.fparent WHERE 1 = 1 AND (ins2.fparent = ";
	private String command12=" OR ins1.fparent = ";
	private String command13=" OR tb_insframework.fparent = ";
	private String command14=" OR tb_insframework.fid = ";
	private String command15=") AND tb_work.fstarttime BETWEEN '";
	private String command16="' AND '";
	private String command17="'GROUP BY tb_welding_machine.fequipment_no ORDER BY SUM(tb_work.fworktime) DESC";
	
	private String command21="SELECT SUM(tb_work.fworktime),tb_welder.fname FROM tb_work INNER JOIN tb_welder ON tb_work.fwelder_id = tb_welder.fwelder_no INNER JOIN tb_insframework ON tb_work.fitemid = tb_insframework.fid INNER JOIN tb_insframework ins1 ON ins1.fid = tb_insframework.fparent INNER JOIN tb_insframework ins2 ON ins2.fid = tb_insframework.fparent WHERE 1 = 1 AND (ins2.fparent = ";
	private String command22=" OR ins1.fparent = ";
	private String command23=" OR tb_insframework.fparent = ";
	private String command24=" OR tb_insframework.fid = ";
	private String command25=") AND tb_work.fstarttime BETWEEN '";
	private String command26="' AND '";
	private String command27="'GROUP BY tb_welder.fname ORDER BY SUM(tb_work.fworktime) DESC";
	
	private String command31="SELECT SUM(tb_work.fworktime),tb_welding_machine.fequipment_no FROM tb_work INNER JOIN tb_welding_machine ON tb_welding_machine.fid = tb_work.fmachine_id INNER JOIN tb_insframework ON tb_work.fitemid = tb_insframework.fid INNER JOIN tb_insframework ins1 ON ins1.fid = tb_insframework.fparent INNER JOIN tb_insframework ins2 ON ins2.fid = tb_insframework.fparent WHERE 1 = 1 AND (ins2.fparent = ";
	private String command32=" OR ins1.fparent = ";
	private String command33=" OR tb_insframework.fparent = ";
	private String command34=" OR tb_insframework.fid = ";
	private String command35=") AND tb_work.fstarttime BETWEEN '";
	private String command36="' AND '";
	private String command37="'GROUP BY tb_welding_machine.fequipment_no ORDER BY SUM(tb_work.fworktime) ASC";
	
	private String command41="SELECT SUM(tb_work.fworktime),tb_welder.fname FROM tb_work INNER JOIN tb_welder ON tb_work.fwelder_id = tb_welder.fwelder_no INNER JOIN tb_insframework ON tb_work.fitemid = tb_insframework.fid INNER JOIN tb_insframework ins1 ON ins1.fid = tb_insframework.fparent INNER JOIN tb_insframework ins2 ON ins2.fid = tb_insframework.fparent WHERE 1 = 1 AND (ins2.fparent = ";
	private String command42=" OR ins1.fparent = ";
	private String command43=" OR tb_insframework.fparent = ";
	private String command44=" OR tb_insframework.fid = ";
	private String command45=") AND tb_work.fstarttime BETWEEN '";
	private String command46="' AND '";
	private String command47="'GROUP BY tb_welder.fname ORDER BY SUM(tb_work.fworktime) ASC";
	
	private String command51 = "SELECT fid,caustid,itemid,fname,max(machinenum) total,weldTime,worktime from (SELECT fid,caustid,itemid,fname,COUNT(fmachine_id) machinenum,weldTime,sum(num) worktime from (SELECT insf.fid,ins.fid caustid,i.fid itemid,";
	private String command52 = ".fname,m.fid fmachine_id,DATE_FORMAT(fstarttime, '%Y-%m-%d') weldTime,sum(fworktime)/60/60 num FROM tb_work l INNER JOIN tb_welding_machine m on m.fid = l.fmachine_id INNER JOIN tb_insframework i on i.fid = l.fitemid INNER JOIN tb_insframework ins on ins.fid = i.fparent INNER JOIN tb_insframework insf on insf.fid = ins.fparent where 1=1 and fstarttime >= '";
	private String command53 = "' and fstarttime <= '";
	private String command54 = "' and (i.fid =";
	private String command55 = " or ins.fid =";
	private String command56 = " or insf.fid =";
	private String command57 = " or insf.fparent =";
	private String command58 = ") group by i.fid,fmachine_id,weldTime UNION SELECT insf.fid,ins.fid caustid,i.fid itemid,";
	private String command59 = ".fname,m.fid fmachine_id,DATE_FORMAT(fstarttime, '%Y-%m-%d') weldTime,sum(fstandbytime)/60/60 num FROM tb_standby l INNER JOIN tb_welding_machine m on m.fid = l.fmachine_id INNER JOIN tb_insframework i on i.fid = l.fitemid INNER JOIN tb_insframework ins on ins.fid = i.fparent INNER JOIN tb_insframework insf on insf.fid = ins.fparent where 1=1 and fstarttime >= '";
	private String command510 = "' and fstarttime <= '";
	private String command511 = "' and (i.fid =";
	private String command512 = " or ins.fid =";
	private String command513 = " or insf.fid =";
	private String command514 = " or insf.fparent =";
	private String command515 = ") group by i.fid,fmachine_id,weldTime )temp group by ";
	private String command516 = ",weldTime)A group by ";
	private String command517 = " ORDER BY total DESC";
	
	private String command61 = "SELECT fid,caustid,itemid,fname,max(machinenum) total,weldTime,worktime from (SELECT fid,caustid,itemid,fname,COUNT(fmachine_id) machinenum,weldTime,sum(num) worktime from (SELECT insf.fid,ins.fid caustid,i.fid itemid,";
	private String command62 = ".fname,m.fid fmachine_id,DATE_FORMAT(fstarttime, '%Y-%m-%d') weldTime,sum(fworktime)/60/60 num FROM tb_work l INNER JOIN tb_welding_machine m on m.fid = l.fmachine_id INNER JOIN tb_insframework i on i.fid = l.fitemid INNER JOIN tb_insframework ins on ins.fid = i.fparent INNER JOIN tb_insframework insf on insf.fid = ins.fparent where 1=1 and fstarttime >= '";
	private String command63 = "' and fstarttime <= '";
	private String command64 = "' and (i.fid =";
	private String command65 = " or ins.fid =";
	private String command66 = " or insf.fid =";
	private String command67 = " or insf.fparent =";
	private String command68 = ") group by i.fid,fmachine_id,weldTime UNION SELECT insf.fid,ins.fid caustid,i.fid itemid,";
	private String command69 = ".fname,m.fid fmachine_id,DATE_FORMAT(fstarttime, '%Y-%m-%d') weldTime,sum(fstandbytime)/60/60 num FROM tb_standby l INNER JOIN tb_welding_machine m on m.fid = l.fmachine_id INNER JOIN tb_insframework i on i.fid = l.fitemid INNER JOIN tb_insframework ins on ins.fid = i.fparent INNER JOIN tb_insframework insf on insf.fid = ins.fparent where 1=1 and fstarttime >= '";
	private String command610 = "' and fstarttime <= '";
	private String command611 = "' and (i.fid =";
	private String command612 = " or ins.fid =";
	private String command613 = " or insf.fid =";
	private String command614 = " or insf.fparent =";
	private String command615 = ") group by i.fid,fmachine_id,weldTime )temp group by ";
	private String command616 = ",weldTime)A group by ";
	private String command617 = " ORDER BY total ASC";
	
	private String ins;
	private String instype;
	private String ipconfig;
	public String base;
	public String name;
	public String ser;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		this.requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_combo_line_column_chart);

		/*ActionBar actionBar = getActionBar();
		actionBar.setDisplayHomeAsUpEnabled(true);*/
		
		//连接数据库
		new Thread(runnable).start();
		
		//获取初始数据
		Intent intent=getIntent();
		listarray1=intent.getStringArrayListExtra("data");
		sendtype=intent.getStringExtra("type");
		sendname=intent.getStringExtra("name");
		ins = intent.getStringExtra("ins");
		instype = intent.getStringExtra("instype");
		ipconfig = intent.getStringExtra("ip");
		base = intent.getStringExtra("base");
		name = intent.getStringExtra("name");
		ser = intent.getStringExtra("ser");
		
		//设置左侧菜单
		slidingMenu = new SlidingMenu(this);
		slidingMenu.setMode(SlidingMenu.LEFT);  //菜单从左边滑出
		slidingMenu.setBehindWidth(600);        //菜单的宽度
		slidingMenu.setTouchModeAbove(SlidingMenu.TOUCHMODE_FULLSCREEN);//菜单全屏都可滑出
		slidingMenu.attachToActivity(this, SlidingMenu.SLIDING_CONTENT);
		slidingMenu.setMenu(R.layout.menu_layout);
		
		//显示标题
		TextView tv=(TextView)findViewById(R.id.textView2);
		tv.setText(sendname);
		
		//菜单按钮
		button1=(Button)findViewById(R.id.btmachtop);
		button2=(Button)findViewById(R.id.btweldtop);
		button3=(Button)findViewById(R.id.btmachbot);
		button4=(Button)findViewById(R.id.btweldbot);
		button6=(Button)findViewById(R.id.btweldusetop);
		button7=(Button)findViewById(R.id.btweldusebot);
		button5=(Button)findViewById(R.id.btreturn);
		
		button1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
            	
            	if(sendtype!="machtop"){
	            	progressDialog = new Dialog(ComboLineColumnChartActivity.this,R.style.progress_dialog);  
	            	progressDialog.setContentView(R.layout.dialog);  
	            	progressDialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);  
	            	TextView msg = (TextView) progressDialog.findViewById(R.id.id_tv_loadingmsg);  
	            	msg.setText("正在查询");  
	            	progressDialog.show();
            	
            		listarray1.clear();
					entertype=true;
					TextView tv=(TextView)findViewById(R.id.textView2);
					tv.setText("焊机工时TOP");
            		new Thread(runnable1).start();
            	}
            }
        });
		button2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
            	
            	if(sendtype!="weldtop"){
	            	progressDialog = new Dialog(ComboLineColumnChartActivity.this,R.style.progress_dialog);  
	            	progressDialog.setContentView(R.layout.dialog);  
	            	progressDialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);  
	            	TextView msg = (TextView) progressDialog.findViewById(R.id.id_tv_loadingmsg);  
	            	msg.setText("正在查询");  
	            	progressDialog.show();
            	
					listarray1.clear();
					entertype=true;
					TextView tv=(TextView)findViewById(R.id.textView2);
					tv.setText("焊工工时TOP");
					new Thread(runnable2).start();  
				}
            }
        });
		button3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
            	
            	if(sendtype!="machbot"){
	            	progressDialog = new Dialog(ComboLineColumnChartActivity.this,R.style.progress_dialog);  
	            	progressDialog.setContentView(R.layout.dialog);  
	            	progressDialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);  
	            	TextView msg = (TextView) progressDialog.findViewById(R.id.id_tv_loadingmsg);  
	            	msg.setText("正在查询");  
	            	progressDialog.show();
            	
            		listarray1.clear();
					entertype=true;
					TextView tv=(TextView)findViewById(R.id.textView2);
					tv.setText("焊机工时BOT");
            		new Thread(runnable3).start();
				}
            }
        });
		button4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
            	
            	if(sendtype!="weldbot"){
	            	progressDialog = new Dialog(ComboLineColumnChartActivity.this,R.style.progress_dialog);  
	            	progressDialog.setContentView(R.layout.dialog);  
	            	progressDialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);  
	            	TextView msg = (TextView) progressDialog.findViewById(R.id.id_tv_loadingmsg);  
	            	msg.setText("正在查询");  
	            	progressDialog.show();
            	
            		listarray1.clear();
					entertype=true;
					TextView tv=(TextView)findViewById(R.id.textView2);
					tv.setText("焊工工时BOT");
            		new Thread(runnable4).start();
				}
            }
        });
		button6.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
            	
            	if(sendtype!="weldusetop"){
	            	progressDialog = new Dialog(ComboLineColumnChartActivity.this,R.style.progress_dialog);  
	            	progressDialog.setContentView(R.layout.dialog);  
	            	progressDialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);  
	            	TextView msg = (TextView) progressDialog.findViewById(R.id.id_tv_loadingmsg);  
	            	msg.setText("正在查询");  
	            	progressDialog.show();
            	
            		listarray1.clear();
					entertype=true;
					TextView tv=(TextView)findViewById(R.id.textView2);
					tv.setText("设备利用率TOP");
            		new Thread(runnable6).start();
				}
            }
        });
		button7.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
            	
            	if(sendtype!="weldusebot"){
            		sendtype="weldusebot";
	            	progressDialog = new Dialog(ComboLineColumnChartActivity.this,R.style.progress_dialog);  
	            	progressDialog.setContentView(R.layout.dialog);  
	            	progressDialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);  
	            	TextView msg = (TextView) progressDialog.findViewById(R.id.id_tv_loadingmsg);  
	            	msg.setText("正在查询");  
	            	progressDialog.show();
            	
            		listarray1.clear();
					entertype=true;
					TextView tv=(TextView)findViewById(R.id.textView2);
					tv.setText("设备利用率BOT");
            		new Thread(runnable7).start();
				}
            }
        });
		button5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
            	Intent intent2=new Intent(ComboLineColumnChartActivity.this,MainActivity.class);
            	intent2.putExtra("ins", ins);
            	intent2.putExtra("ip", ipconfig);
            	intent2.putExtra("base", base);
				intent2.putExtra("name", name);
				intent2.putExtra("ser", ser);
            	intent2.putExtra("instype", instype);
            	startActivity(intent2);
            }
        });

		//选择日期
		et1=(EditText) findViewById(R.id.editText1);
		et1.setInputType(InputType.TYPE_NULL); 
		et1.setOnTouchListener(new OnTouchListener() {  
			  
            @Override  
            public boolean onTouch(View v, MotionEvent event) {  
                if (event.getAction() == MotionEvent.ACTION_DOWN) {  
                    showDatePickDlg1(); 
                    
                    return true;  
                }  
                return false;  
            }  
        });  
		
		et2=(EditText) findViewById(R.id.editText2);
		et2.setInputType(InputType.TYPE_NULL); 
		et2.setOnTouchListener(new OnTouchListener() {  
			  
            @Override  
            public boolean onTouch(View v, MotionEvent event) {  
                if (event.getAction() == MotionEvent.ACTION_DOWN) {  
                    showDatePickDlg2();
                    
                    return true;  
                }  
                return false;  
            }  
        });  
		
		//确认日期
		Button button=(Button) findViewById(R.id.buttonsure);
		button.setOnClickListener(new View.OnClickListener(){

			@Override
			public void onClick(View v) {

				progressDialog = new Dialog(ComboLineColumnChartActivity.this,R.style.progress_dialog);  
            	progressDialog.setContentView(R.layout.dialog);  
            	progressDialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);  
            	TextView msg = (TextView) progressDialog.findViewById(R.id.id_tv_loadingmsg);  
            	msg.setText("正在查询");  
            	progressDialog.show();
				
				aa = et1.getText().toString();
				bb = et2.getText().toString();
				/*et1.getText().clear();
				et2.getText().clear();*/
				
				if(aa.length()!=0 && bb.length()!=0){
					
					if(sendtype.equals("machtop")){
						listarray1.clear();
						new Thread(runnable1).start();
					}else if(sendtype.equals("weldtop")){
						listarray1.clear();
						new Thread(runnable2).start();
					}else if(sendtype.equals("machbot")){
						listarray1.clear();
						new Thread(runnable3).start();
					}else if(sendtype.equals("weldbot")){
						listarray1.clear();
						new Thread(runnable4).start();
					}else if(sendtype.equals("weldusetop")){
						listarray1.clear();
						new Thread(runnable6).start();
					}else if(sendtype.equals("weldusebot")){
						listarray1.clear();
						new Thread(runnable7).start();
					}
				}else{
					progressDialog.dismiss();
					progressDialog = new Dialog(ComboLineColumnChartActivity.this,R.style.progress_dialog);  
	            	progressDialog.setContentView(R.layout.dialog2);  
	            	progressDialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);  
	            	TextView msg1 = (TextView) progressDialog.findViewById(R.id.id_tv_loadingmsg);  
	            	msg1.setText("请选择时间");  
	            	progressDialog.show();
				}
			}
		});
		
		
		if(savedInstanceState==null){
			getSupportFragmentManager().beginTransaction()
			.add(R.id.container, new PlaceholderFragment()).commit();
		}
	
	}
	
	//连接数据库
	Runnable runnable = new Runnable() {
        ResultSet rs;
        public void run() {
			try {
				Class.forName("com.mysql.jdbc.Driver");
				try {
					conn = DriverManager.getConnection("jdbc:mysql://" + ipconfig + ":3306/" + base + "", "" + name + "", "" + ser + "");
					stmt= conn.createStatement();
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			} catch (ClassNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
        }
	};
	
	//焊机工时高排行
		Runnable runnable1 = new Runnable() {
	        ResultSet rs;
	        public void run() {
	        	
	        	try {
					Class.forName("com.mysql.jdbc.Driver");
			        try {
			        	if(entertype){
			        		Command1="SELECT SUM(tb_work.fworktime),tb_welding_machine.fequipment_no FROM tb_work INNER JOIN tb_welding_machine ON tb_welding_machine.fid = tb_work.fmachine_id INNER JOIN tb_insframework ON tb_work.fitemid = tb_insframework.fid INNER JOIN tb_insframework ins1 ON ins1.fid = tb_insframework.fparent INNER JOIN tb_insframework ins2 ON ins2.fid = tb_insframework.fparent WHERE 1 = 1 AND (ins2.fparent = " + ins + " OR ins1.fparent = " + ins + " OR tb_insframework.fparent = " + ins + " OR tb_insframework.fid = " + ins +") GROUP BY tb_welding_machine.fequipment_no ORDER BY SUM(tb_work.fworktime) DESC";
			        	}else{
			        		Command1=command11+ins+command12+ins+command13+ins+command14+ins+command15+aa+command16+bb+command17;
			        	}
						rs = stmt.executeQuery(Command1);
						
						while (rs.next()) {
						  String num = Integer.toString(Integer.valueOf(rs.getString("SUM(tb_work.fworktime)"))/3600);
				      	  String fequipment_no = rs.getString("fequipment_no");
				      	  listarray1.add(num);
				      	  listarray1.add(fequipment_no);
				        }
						
			        } catch (SQLException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
	        	} catch (ClassNotFoundException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
	        	
	        	getSupportFragmentManager().beginTransaction()
				.replace(R.id.container, new PlaceholderFragment()).commit();
	        	
	        	try {
					Thread.sleep(50);
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
	        	
	        	entertype=false;
	        	
				progressDialog.dismiss(); 
	        	
	        	sendtype="machtop";
	    	}
		};
		
		//焊工工时高排行
		Runnable runnable2 = new Runnable() {
	        ResultSet rs;
	        public void run() {
	        	
	        	try {
					Class.forName("com.mysql.jdbc.Driver");
			        try {
			        	if(entertype){
			        		Command1="SELECT SUM(tb_work.fworktime),tb_welder.fname FROM tb_work INNER JOIN tb_welder ON tb_work.fwelder_id = tb_welder.fwelder_no INNER JOIN tb_insframework ON tb_work.fitemid = tb_insframework.fid INNER JOIN tb_insframework ins1 ON ins1.fid = tb_insframework.fparent INNER JOIN tb_insframework ins2 ON ins2.fid = tb_insframework.fparent WHERE 1 = 1 AND (ins2.fparent = " + ins + " OR ins1.fparent = " + ins + " OR tb_insframework.fparent = " + ins + " OR tb_insframework.fid = " + ins +") GROUP BY tb_welder.fname ORDER BY SUM(tb_work.fworktime) DESC";
			        	}else{
			        		Command1=command21+ins+command22+ins+command23+ins+command24+ins+command25+aa+command26+bb+command27;
			        	}
						rs = stmt.executeQuery(Command1);
						
						while (rs.next()) {
						  String num = Integer.toString(Integer.valueOf(rs.getString("SUM(tb_work.fworktime)"))/3600);
				      	  String welder = rs.getString("fname");
				      	  listarray1.add(num);
				      	  listarray1.add(welder);
				        }
						
			        } catch (SQLException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
	        	} catch (ClassNotFoundException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
	        	
	        	getSupportFragmentManager().beginTransaction()
				.replace(R.id.container, new PlaceholderFragment()).commit();

	        	try {
					Thread.sleep(50);
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
	        	
        		entertype=false;
        		
				progressDialog.dismiss();
	        	
	        	sendtype="weldtop";
	    	}
		};
		
		//焊机工时低排行
			Runnable runnable3 = new Runnable() {
		        ResultSet rs;
		        public void run() {
		        	
		        	try {
						Class.forName("com.mysql.jdbc.Driver");
				        try {
				        	if(entertype){
				        		Command1="SELECT SUM(tb_work.fworktime),tb_welding_machine.fequipment_no FROM tb_work INNER JOIN tb_welding_machine ON tb_welding_machine.fid = tb_work.fmachine_id INNER JOIN tb_insframework ON tb_work.fitemid = tb_insframework.fid INNER JOIN tb_insframework ins1 ON ins1.fid = tb_insframework.fparent INNER JOIN tb_insframework ins2 ON ins2.fid = tb_insframework.fparent WHERE 1 = 1 AND (ins2.fparent = " + ins + " OR ins1.fparent = " + ins + " OR tb_insframework.fparent = " + ins + " OR tb_insframework.fid = " + ins +") GROUP BY tb_welding_machine.fequipment_no ORDER BY SUM(tb_work.fworktime) ASC";
				        	}else{
				        		Command1=command31+ins+command32+ins+command33+ins+command34+ins+command35+aa+command36+bb+command37;
				        	}
							rs = stmt.executeQuery(Command1);
							
							while (rs.next()) {
							  String num = Integer.toString(Integer.valueOf(rs.getString("SUM(tb_work.fworktime)"))/3600);
					      	  String fequipment_no = rs.getString("fequipment_no");
					      	  listarray1.add(num);
					      	  listarray1.add(fequipment_no);
					        }
							
				        } catch (SQLException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
		        	} catch (ClassNotFoundException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
		        	
		        	getSupportFragmentManager().beginTransaction()
					.replace(R.id.container, new PlaceholderFragment()).commit();

		        	try {
						Thread.sleep(50);
					} catch (InterruptedException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
		        	
	        		entertype=false;
	        		
					progressDialog.dismiss();
		        	
		        	sendtype="machbot";
		    	}
			};
		
			//焊工工时低排行
			Runnable runnable4 = new Runnable() {
		        ResultSet rs;
		        public void run() {
		        	
		        	try {
						Class.forName("com.mysql.jdbc.Driver");
				        try {
				        	if(entertype){
				        		Command1 = "SELECT SUM(tb_work.fworktime),tb_welder.fname FROM tb_work INNER JOIN tb_welder ON tb_work.fwelder_id = tb_welder.fwelder_no INNER JOIN tb_insframework ON tb_work.fitemid = tb_insframework.fid INNER JOIN tb_insframework ins1 ON ins1.fid = tb_insframework.fparent INNER JOIN tb_insframework ins2 ON ins2.fid = tb_insframework.fparent WHERE 1 = 1 AND (ins2.fparent = " + ins + " OR ins1.fparent = " + ins + " OR tb_insframework.fparent = " + ins + " OR tb_insframework.fid = " + ins +") GROUP BY tb_welder.fname ORDER BY SUM(tb_work.fworktime) ASC";
				        	}else{
				        		Command1=command41+ins+command42+ins+command43+ins+command44+ins+command45+aa+command46+bb+command47;
				        	}
							rs = stmt.executeQuery(Command1);
							
							while (rs.next()) {
							  String num = Integer.toString(Integer.valueOf(rs.getString("SUM(tb_work.fworktime)"))/3600);
					      	  String welder = rs.getString("fname");
					      	  listarray1.add(num);
					      	  listarray1.add(welder);
					        }
							
				        } catch (SQLException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
		        	} catch (ClassNotFoundException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
		        	
		        	getSupportFragmentManager().beginTransaction()
					.replace(R.id.container, new PlaceholderFragment()).commit();

		        	try {
						Thread.sleep(50);
					} catch (InterruptedException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
		        	
	        		entertype=false;
	        		
					progressDialog.dismiss();
		        	
		        	sendtype="weldbot";
		    	}
			};
			
			//设备利用率高
			Runnable runnable6 = new Runnable() {
		        ResultSet rs;
		        public void run() {
		        	
		        	try {
						Class.forName("com.mysql.jdbc.Driver");
				        try {
				        	if(entertype){
				        		String status = "";
								String inssta = "";
								String inssta2 = "";
								String num = "";
						      	String welder = "";
								if(instype.equals("20")){
							      status = "fid";
							      inssta = "insf";
							      inssta2 = "ins2";
							    }else if(instype.equals("21")){
							      status = "caustid";
							      inssta = "ins";
							      inssta2 = "ins1";
							    }else if(instype.equals("22")){
							      status = "itemid";
							      inssta = "i";
							      inssta2 = "tb_insframework";
							    }else if(instype.equals("23")){
							      status = "itemid";
							      inssta = "i";
							      inssta2 = "tb_insframework";
							    }
								
								String Command1 = "SELECT fid,caustid,itemid,fname,max(machinenum) total,weldTime,worktime from "
										+ "(SELECT fid,caustid,itemid,fname,COUNT(fmachine_id) machinenum,weldTime,sum(num) worktime from "
										+ "(SELECT insf.fid,ins.fid caustid,i.fid itemid," + inssta + ".fname,m.fid fmachine_id,DATE_FORMAT(fstarttime, '%Y-%m-%d') weldTime,sum(fworktime)/60/60 num FROM tb_work l "
										+ "INNER JOIN tb_welding_machine m on m.fid = l.fmachine_id "
										+ "INNER JOIN tb_insframework i on i.fid = l.fitemid "
										+ "INNER JOIN tb_insframework ins on ins.fid = i.fparent "
										+ "INNER JOIN tb_insframework insf on insf.fid = ins.fparent "
										+ "where 1=1 and fstarttime >= '2016-05-01' and fstarttime <= '2020-07-01' "
										+ "and (i.fid =" + ins + " or ins.fid =" + ins + " or insf.fid =" + ins + " or insf.fparent =" + ins + ") "
										+ "group by i.fid,fmachine_id,weldTime "
										+ "UNION "
										+ "SELECT insf.fid,ins.fid caustid,i.fid itemid," + inssta + ".fname,m.fid fmachine_id,DATE_FORMAT(fstarttime, '%Y-%m-%d') weldTime,sum(fstandbytime)/60/60 num FROM tb_standby l "
										+ "INNER JOIN tb_welding_machine m on m.fid = l.fmachine_id "
										+ "INNER JOIN tb_insframework i on i.fid = l.fitemid "
										+ "INNER JOIN tb_insframework ins on ins.fid = i.fparent "
										+ "INNER JOIN tb_insframework insf on insf.fid = ins.fparent "
										+ "where 1=1 and fstarttime >= '2016-05-01' and fstarttime <= '2020-07-01' "
										+ "and (i.fid =" + ins + " or ins.fid =" + ins + " or insf.fid =" + ins + " or insf.fparent =" + ins + ") "
										+ "group by i.fid,fmachine_id,weldTime )temp "
										+ "group by " + status + ",weldTime)A group by " + status + " ORDER BY total DESC";
								
								rs = stmt.executeQuery(Command1);
								
								listarray1.clear();
								while (rs.next()) {
								  num = rs.getString("total");
						      	  welder = rs.getString("fname");
						      	  listarray1.add(num);
						      	  listarray1.add(welder);
						        }
								
								String Command2 = "SELECT " + inssta2 + ".fname,COUNT(tb_welding_machine.fequipment_no) FROM tb_insframework "
										+ "INNER JOIN tb_welding_machine ON tb_insframework.fid = tb_welding_machine.finsframework_id "
										+ "INNER JOIN tb_insframework ins1 ON ins1.fid = tb_insframework.fparent "
										+ "INNER JOIN tb_insframework ins2 ON ins2.fid = ins1.fparent "
										+ "WHERE (ins1.fid = " + ins + " OR ins1.fparent = " + ins + " OR ins2.fparent = " + ins + ") "
										+ "GROUP BY " + inssta2 + ".fid";
								
								rs = stmt.executeQuery(Command2);
								
								ArrayList<String> listarray3 = new ArrayList<String>();
								while (rs.next()) {
								  num = rs.getString("COUNT(tb_welding_machine.fequipment_no)");
						      	  welder = rs.getString("fname");
						      	  listarray3.add(num);
						      	  listarray3.add(welder);
						        }
								
								for(int i=0;i<listarray1.size();i+=2){
									for(int j=0;j<listarray3.size();j+=2){
										if(listarray1.get(i+1).equals(listarray3.get(j+1))){
											String a = String.valueOf(((double)Integer.valueOf(listarray1.get(i)))/((double) Integer.valueOf(listarray3.get(j))));
											listarray1.set(i, a.substring(0,4));
										}
									}
								}
								
							}else{
				        		
								String status = "";
								String inssta = "";
								String inssta2 = "";
								String num = "";
						      	String welder = "";
								if(instype.equals("20")){
							      status = "fid";
							      inssta = "insf";
							      inssta2 = "ins2";
							    }else if(instype.equals("21")){
							      status = "caustid";
							      inssta = "ins";
							      inssta2 = "ins1";
							    }else if(instype.equals("22")){
							      status = "itemid";
							      inssta = "i";
							      inssta2 = "tb_insframework";
							    }else if(instype.equals("23")){
							      status = "itemid";
							      inssta = "i";
							      inssta2 = "tb_insframework";
							    }
								Command1=command51+inssta+command52+aa+command53+bb+command54+ins+command55+ins+command56+ins+command57+ins+command58+inssta+command59+aa+command510+bb+command511+ins+command512+ins+command513+ins+command514+ins+command515+status+command516+status+command517;
								
								rs = stmt.executeQuery(Command1);
								
								listarray1.clear();
								while (rs.next()) {
								  num = rs.getString("total");
						      	  welder = rs.getString("fname");
						      	  listarray1.add(num);
						      	  listarray1.add(welder);
						        }
								
								String Command2 = "SELECT " + inssta2 + ".fname,COUNT(tb_welding_machine.fequipment_no) FROM tb_insframework "
										+ "INNER JOIN tb_welding_machine ON tb_insframework.fid = tb_welding_machine.finsframework_id "
										+ "INNER JOIN tb_insframework ins1 ON ins1.fid = tb_insframework.fparent "
										+ "INNER JOIN tb_insframework ins2 ON ins2.fid = ins1.fparent "
										+ "WHERE (ins1.fid = " + ins + " OR ins1.fparent = " + ins + " OR ins2.fparent = " + ins + ") "
										+ "GROUP BY " + inssta2 + ".fid";
								
								rs = stmt.executeQuery(Command2);
								
								ArrayList<String> listarray3 = new ArrayList<String>();
								while (rs.next()) {
								  num = rs.getString("COUNT(tb_welding_machine.fequipment_no)");
						      	  welder = rs.getString("fname");
						      	  listarray3.add(num);
						      	  listarray3.add(welder);
						        }
								
								for(int i=0;i<listarray1.size();i+=2){
									for(int j=0;j<listarray3.size();j+=2){
										if(listarray1.get(i+1).equals(listarray3.get(j+1))){
											String a = String.valueOf(((double)Integer.valueOf(listarray1.get(i)))/((double) Integer.valueOf(listarray3.get(j))));
											listarray1.set(i, a.substring(0,4));
										}
									}
								}
								
							}
							
							
				        } catch (SQLException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
		        	} catch (ClassNotFoundException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
		        	
		        	getSupportFragmentManager().beginTransaction()
					.replace(R.id.container, new PlaceholderFragment()).commit();

		        	try {
						Thread.sleep(50);
					} catch (InterruptedException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
		        	
	        		entertype=false;
	        		
					progressDialog.dismiss();
		        	
		        	sendtype="weldusetop";
		    	}
			};
			
			//设备利用率低
			Runnable runnable7 = new Runnable() {
		        ResultSet rs;
		        public void run() {
		        	
		        	try {
						Class.forName("com.mysql.jdbc.Driver");
				        try {
				        	if(entertype){
				        		String status = "";
								String inssta = "";
								String inssta2 = "";
								String num = "";
						      	String welder = "";
								if(instype.equals("20")){
							      status = "fid";
							      inssta = "insf";
							      inssta2 = "ins2";
							    }else if(instype.equals("21")){
							      status = "caustid";
							      inssta = "ins";
							      inssta2 = "ins1";
							    }else if(instype.equals("22")){
							      status = "itemid";
							      inssta = "i";
							      inssta2 = "tb_insframework";
							    }else if(instype.equals("23")){
							      status = "itemid";
							      inssta = "i";
							      inssta2 = "tb_insframework";
							    }
								
								String Command1 = "SELECT fid,caustid,itemid,fname,max(machinenum) total,weldTime,worktime from "
										+ "(SELECT fid,caustid,itemid,fname,COUNT(fmachine_id) machinenum,weldTime,sum(num) worktime from "
										+ "(SELECT insf.fid,ins.fid caustid,i.fid itemid," + inssta + ".fname,m.fid fmachine_id,DATE_FORMAT(fstarttime, '%Y-%m-%d') weldTime,sum(fworktime)/60/60 num FROM tb_work l "
										+ "INNER JOIN tb_welding_machine m on m.fid = l.fmachine_id "
										+ "INNER JOIN tb_insframework i on i.fid = l.fitemid "
										+ "INNER JOIN tb_insframework ins on ins.fid = i.fparent "
										+ "INNER JOIN tb_insframework insf on insf.fid = ins.fparent "
										+ "where 1=1 and fstarttime >= '2016-05-01' and fstarttime <= '2020-07-01' "
										+ "and (i.fid =" + ins + " or ins.fid =" + ins + " or insf.fid =" + ins + " or insf.fparent =" + ins + ") "
										+ "group by i.fid,fmachine_id,weldTime "
										+ "UNION "
										+ "SELECT insf.fid,ins.fid caustid,i.fid itemid," + inssta + ".fname,m.fid fmachine_id,DATE_FORMAT(fstarttime, '%Y-%m-%d') weldTime,sum(fstandbytime)/60/60 num FROM tb_standby l "
										+ "INNER JOIN tb_welding_machine m on m.fid = l.fmachine_id "
										+ "INNER JOIN tb_insframework i on i.fid = l.fitemid "
										+ "INNER JOIN tb_insframework ins on ins.fid = i.fparent "
										+ "INNER JOIN tb_insframework insf on insf.fid = ins.fparent "
										+ "where 1=1 and fstarttime >= '2016-05-01' and fstarttime <= '2020-07-01' "
										+ "and (i.fid =" + ins + " or ins.fid =" + ins + " or insf.fid =" + ins + " or insf.fparent =" + ins + ") "
										+ "group by i.fid,fmachine_id,weldTime )temp "
										+ "group by " + status + ",weldTime)A group by " + status + " ORDER BY total ASC";
								
								rs = stmt.executeQuery(Command1);
								
								listarray1.clear();
								while (rs.next()) {
								  num = rs.getString("total");
						      	  welder = rs.getString("fname");
						      	  listarray1.add(num);
						      	  listarray1.add(welder);
						        }
								
								String Command2 = "SELECT " + inssta2 + ".fname,COUNT(tb_welding_machine.fequipment_no) FROM tb_insframework "
										+ "INNER JOIN tb_welding_machine ON tb_insframework.fid = tb_welding_machine.finsframework_id "
										+ "INNER JOIN tb_insframework ins1 ON ins1.fid = tb_insframework.fparent "
										+ "INNER JOIN tb_insframework ins2 ON ins2.fid = ins1.fparent "
										+ "WHERE (ins1.fid = " + ins + " OR ins1.fparent = " + ins + " OR ins2.fparent = " + ins + ") "
										+ "GROUP BY " + inssta2 + ".fid";
								
								rs = stmt.executeQuery(Command2);
								
								ArrayList<String> listarray3 = new ArrayList<String>();
								while (rs.next()) {
								  num = rs.getString("COUNT(tb_welding_machine.fequipment_no)");
						      	  welder = rs.getString("fname");
						      	  listarray3.add(num);
						      	  listarray3.add(welder);
						        }
								
								for(int i=0;i<listarray1.size();i+=2){
									for(int j=0;j<listarray3.size();j+=2){
										if(listarray1.get(i+1).equals(listarray3.get(j+1))){
											String a = String.valueOf(((double)Integer.valueOf(listarray1.get(i)))/((double) Integer.valueOf(listarray3.get(j))));
											listarray1.set(i, a.substring(0,4));
										}
									}
								}
								
							}else{
				        		
								String status = "";
								String inssta = "";
								String inssta2 = "";
								String num = "";
						      	String welder = "";
								if(instype.equals("20")){
							      status = "fid";
							      inssta = "insf";
							      inssta2 = "ins2";
							    }else if(instype.equals("21")){
							      status = "caustid";
							      inssta = "ins";
							      inssta2 = "ins1";
							    }else if(instype.equals("22")){
							      status = "itemid";
							      inssta = "i";
							      inssta2 = "tb_insframework";
							    }else if(instype.equals("23")){
							      status = "itemid";
							      inssta = "i";
							      inssta2 = "tb_insframework";
							    }
								Command1=command61+inssta+command62+aa+command63+bb+command64+ins+command65+ins+command56+ins+command67+ins+command68+inssta+command69+aa+command610+bb+command611+ins+command612+ins+command613+ins+command614+ins+command615+status+command616+status+command617;
								
								rs = stmt.executeQuery(Command1);
								
								listarray1.clear();
								while (rs.next()) {
								  num = rs.getString("total");
						      	  welder = rs.getString("fname");
						      	  listarray1.add(num);
						      	  listarray1.add(welder);
						        }
								
								String Command2 = "SELECT " + inssta2 + ".fname,COUNT(tb_welding_machine.fequipment_no) FROM tb_insframework "
										+ "INNER JOIN tb_welding_machine ON tb_insframework.fid = tb_welding_machine.finsframework_id "
										+ "INNER JOIN tb_insframework ins1 ON ins1.fid = tb_insframework.fparent "
										+ "INNER JOIN tb_insframework ins2 ON ins2.fid = ins1.fparent "
										+ "WHERE (ins1.fid = " + ins + " OR ins1.fparent = " + ins + " OR ins2.fparent = " + ins + ") "
										+ "GROUP BY " + inssta2 + ".fid";
								
								rs = stmt.executeQuery(Command2);
								
								ArrayList<String> listarray3 = new ArrayList<String>();
								while (rs.next()) {
								  num = rs.getString("COUNT(tb_welding_machine.fequipment_no)");
						      	  welder = rs.getString("fname");
						      	  listarray3.add(num);
						      	  listarray3.add(welder);
						        }
								
								for(int i=0;i<listarray1.size();i+=2){
									for(int j=0;j<listarray3.size();j+=2){
										if(listarray1.get(i+1).equals(listarray3.get(j+1))){
											String a = String.valueOf(((double)Integer.valueOf(listarray1.get(i)))/((double) Integer.valueOf(listarray3.get(j))));
											listarray1.set(i, a.substring(0,4));
										}
									}
								}
								
							}
							
							
				        } catch (SQLException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
		        	} catch (ClassNotFoundException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
		        	
		        	getSupportFragmentManager().beginTransaction()
					.replace(R.id.container, new PlaceholderFragment()).commit();

		        	try {
						Thread.sleep(50);
					} catch (InterruptedException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
		        	
	        		entertype=false;
	        		
					progressDialog.dismiss();
		        	
		        	sendtype="weldusebot";
		    	}
			};
			
			private float x1;
			private float y1;
			private float x2;
			private float y2;
	
	protected void showDatePickDlg1() {
		// TODO Auto-generated method stub
		Calendar calendar = Calendar.getInstance();  
        DatePickerDialog datePickerDialog = new DatePickerDialog(ComboLineColumnChartActivity.this, new OnDateSetListener() {  
  
            @Override  
            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
            	monthOfYear=monthOfYear+1;
            	String month = Integer.toString(monthOfYear);
            	String day = Integer.toString(dayOfMonth);
            	if(month.length()==2 && day.length()==2){
            		ComboLineColumnChartActivity.this.et1.setText(year + "-" + monthOfYear + "-" + dayOfMonth);  
            	}else if(month.length()==1 && day.length()==2){
            		ComboLineColumnChartActivity.this.et1.setText(year + "-" + "0" + monthOfYear + "-" + dayOfMonth);  
            	}else if(month.length()==2 && day.length()==1){
            		ComboLineColumnChartActivity.this.et1.setText(year + "-" + monthOfYear + "-" + "0" + dayOfMonth);  
            	}else if(month.length()==1 && day.length()==1){
            		ComboLineColumnChartActivity.this.et1.setText(year + "-" + "0" + monthOfYear + "-" + "0" + dayOfMonth);  
            	}
            }  
        }, calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH));  
        datePickerDialog.show();
	}
	
	protected void showDatePickDlg2() {
		// TODO Auto-generated method stub
		Calendar calendar = Calendar.getInstance();  
        DatePickerDialog datePickerDialog = new DatePickerDialog(ComboLineColumnChartActivity.this, new OnDateSetListener() {  
  
            @Override  
            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
            	monthOfYear=monthOfYear+1;
            	String month = Integer.toString(monthOfYear);
            	String day = Integer.toString(dayOfMonth);
            	if(month.length()==2 && day.length()==2){
            		ComboLineColumnChartActivity.this.et2.setText(year + "-" + monthOfYear + "-" + dayOfMonth);  
            	}else if(month.length()==1 && day.length()==2){
            		ComboLineColumnChartActivity.this.et2.setText(year + "-" + "0" + monthOfYear + "-" + dayOfMonth);  
            	}else if(month.length()==2 && day.length()==1){
            		ComboLineColumnChartActivity.this.et2.setText(year + "-" + monthOfYear + "-" + "0" + dayOfMonth);  
            	}else if(month.length()==1 && day.length()==1){
            		ComboLineColumnChartActivity.this.et2.setText(year + "-" + "0" + monthOfYear + "-" + "0" + dayOfMonth);  
            	}
            }  
        }, calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH));  
        datePickerDialog.show();
	}

	/*public boolean onOptionsItemSelected(MenuItem item){
    	if(item.getItemId() == android.R.id.home){
    		Intent intent=new Intent(this,MainActivity.class);
    		startActivity(intent);
    	    finish();
    	}
		return true;
    }*/
	
	
	/*private final OnClickListener change = new OnClickListener()
    {
        @Override
        public void onClick(View v)
        {
        	Intent intent1=new Intent(ComboLineColumnChartActivity.this,Excelreport.class);
            startActivity(intent1);
        	overridePendingTransition(R.anim.in_from_down, R.anim.out_to_up);
        }
    };*/
	
	
	/*@Override  
    public boolean onTouchEvent(MotionEvent event) {  
		//�̳���Activity��onTouchEvent������ֱ�Ӽ�������¼�  
        if(event.getAction() == MotionEvent.ACTION_DOWN) {  
            //����ָ���µ�ʱ��  
            x1 = event.getX();  
            y1 = event.getY();  
        }  
        if(event.getAction() == MotionEvent.ACTION_MOVE) {
        	Intent intent1=new Intent(ComboLineColumnChartActivity.this,Excelreport.class);
            startActivity(intent1);
        	overridePendingTransition(R.anim.in_from_down, R.anim.out_to_up);
        }
        if(event.getAction() == MotionEvent.ACTION_UP) {  
            //����ָ�뿪��ʱ��  
            x2 = event.getX();  
            y2 = event.getY();  
            if(y1 - y2 > 50) {  
                
            } else if(y2 - y1 > 50) {  
            } else if(x1 - x2 > 50) {  
               
            } else if(x2 - x1 > 50) {  
                
            }  
        }  
        return super.onTouchEvent(event);  
    }*/ 

	/**
	 * A fragment containing a combo line/column chart view.
	 */
	public class PlaceholderFragment extends Fragment {

		private ComboLineColumnChartView chart;
		private ComboLineColumnChartData data;

		private int numberOfLines = 1;
		private int maxNumberOfLines = 1;
		private int numberOfPoints = 10;
		private int numColumns = 10;
		public String[] name;
		public String[] value;

		float[][] randomNumbersTab = new float[maxNumberOfLines][numberOfPoints];

		private boolean hasAxes = true;
		private boolean hasAxesNames = true;
		private boolean hasPoints = true;
		private boolean hasLines = true;
		private boolean isCubic = false;
		private boolean hasLabels = false;

		public PlaceholderFragment() {
		}

		
		@Override
		public View onCreateView(LayoutInflater inflater, ViewGroup container,
				Bundle savedInstanceState) {
			setHasOptionsMenu(true);
			View rootView = inflater
					.inflate(R.layout.fragment_combo_line_column_chart,
							container, false);
			
			int size=listarray1.size();
			if(size>20){
				name=new String[10];
				value=new String[10];
				numberOfPoints=10;
				numColumns=10;
				for(int i=0;i<10;i++){
					value[i]=listarray1.get(i*2);
					name[i]=listarray1.get(i*2+1);
				}
			}else{
				name=new String[size/2];
				value=new String[size/2];
				numberOfPoints=size/2;
				numColumns=size/2;
				for(int i=0;i<size/2;i++){
					value[i]=listarray1.get(i*2);
					name[i]=listarray1.get(i*2+1);
				}
			}
			
			/*for(int i=0;i<10;i++){
				if(size<20){
					int add=(20-size)/2;
					for(int j=0;j<add;j++){
						listarray1.add("0");
						listarray1.add("��");
					}
				}
				value[i]=listarray1.get(i*2);
				name[i]=listarray1.get(i*2+1);	
			}*/
			
			chart = (ComboLineColumnChartView) rootView
					.findViewById(R.id.chart);
			chart.setValueTouchEnabled(false);//禁止点击
			chart.setZoomEnabled(false);//禁止手势缩放
			//chart.setOnValueTouchListener(new ValueTouchListener());

			generateValues();
			generateData();

			if(entertype){
				slidingMenu.toggle();
			}
			
			/*Button buttoncha=(Button) rootView.findViewById(R.id.buttoncha);
			buttoncha.setOnClickListener(change);*/
			
			return rootView;
		}


		/*// MENU
		@Override
		public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
			inflater.inflate(R.menu.combo_line_column_chart, menu);
		}

		@Override
		public boolean onOptionsItemSelected(MenuItem item) {
			int id = item.getItemId();
			if (id == R.id.action_reset) {
				reset();
				generateData();
				return true;
			}
			if (id == R.id.action_add_line) {
				addLineToData();
				return true;
			}
			if (id == R.id.action_toggle_lines) {
				toggleLines();
				return true;
			}
			if (id == R.id.action_toggle_points) {
				togglePoints();
				return true;
			}
			if (id == R.id.action_toggle_cubic) {
				toggleCubic();
				return true;
			}
			if (id == R.id.action_toggle_labels) {
				toggleLabels();
				return true;
			}
			if (id == R.id.action_toggle_axes) {
				toggleAxes();
				return true;
			}
			if (id == R.id.action_toggle_axes_names) {
				toggleAxesNames();
				return true;
			}
			if (id == R.id.action_animate) {
				prepareDataAnimation();
				chart.startDataAnimation();
				return true;
			}
			return super.onOptionsItemSelected(item);
		}*/



		private void generateValues() {
			float number;
			for (int i = 0; i < maxNumberOfLines; ++i) {
				for (int j = 0; j < numberOfPoints; ++j) {
					if(sendtype.equals("weldusetop") || sendtype.equals("weldusebot")){
						number=Float.parseFloat(value[j]);
					}else{
						number=Float.parseFloat(value[j]);
					}
					randomNumbersTab[i][j] = number;
				}
			}
		}

		private void reset() {
			numberOfLines = 1;

			hasAxes = true;
			hasAxesNames = true;
			hasLines = true;
			hasPoints = true;
			hasLabels = false;
			isCubic = false;

		}

		private void generateData() {
			// Chart looks the best when line data and column data have similar
			// maximum viewports.
			List<AxisValue> axisValues = new ArrayList<AxisValue>();
			for(int i=0;i<numColumns;i++){
				axisValues.add(new AxisValue(i).setLabel(name[i]));
			}
			data = new ComboLineColumnChartData(generateColumnData(),
					generateLineData());

			if (hasAxes) {
				Axis axisX = new Axis(axisValues).setHasLines(true);
				Axis axisY = new Axis().setHasLines(true);
				if (hasAxesNames) {
					axisX.setTextSize(12);
					axisY.setName("    ");
					axisY.setTextSize(12);
				}
				data.setAxisXBottom(axisX);
				//data.setAxisYLeft(new Axis().setHasLines(true).setMaxLabelChars(4));
				data.setAxisYLeft(axisY);
				
			} else {
				data.setAxisXBottom(null);
				data.setAxisYLeft(null);
			}

			chart.setComboLineColumnChartData(data);
		}

		private LineChartData generateLineData() {

			List<Line> lines = new ArrayList<Line>();
			for (int i = 0; i < numberOfLines; ++i) {

				List<PointValue> values = new ArrayList<PointValue>();
				for (int j = 0; j < numberOfPoints; ++j) {
					values.add(new PointValue(j, randomNumbersTab[i][j]));
				}

				Line line = new Line(values);
				line.setColor(ChartUtils.COLOR_RED);
				line.setCubic(isCubic);
				line.setHasLabels(hasLabels);
				line.setHasLines(hasLines);
				line.setHasPoints(hasPoints);
				lines.add(line);
			}

			LineChartData lineChartData = new LineChartData(lines);

			return lineChartData;

		}

		private ColumnChartData generateColumnData() {
			int numSubcolumns = 1;
			
			// Column can have many subcolumns, here by default I use 1
			// subcolumn in each of 8 columns.
			List<Column> columns = new ArrayList<Column>();
			List<SubcolumnValue> values;
			float number=0;
			for (int i = 0; i < numColumns; ++i) {
				values = new ArrayList<SubcolumnValue>();
				for (int j = 0; j < numSubcolumns; ++j) {
					if(sendtype.equals("weldusetop") || sendtype.equals("weldusebot")){
						values.add(new SubcolumnValue(
								Float.parseFloat(value[i]),ChartUtils.COLOR_BLUE));
					}else{
						values.add(new SubcolumnValue(
								Float.parseFloat(value[i]),ChartUtils.COLOR_BLUE));
					}
				}
				Column column = new Column(values);
				//显示小数
				ColumnChartValueFormatter chartValueFormatter = new SimpleColumnChartValueFormatter(2);
	            column.setFormatter(chartValueFormatter);
				column.setHasLabels(true);//显示数值
				columns.add(new Column(column));
			}

			ColumnChartData columnChartData = new ColumnChartData(columns);
			if(numColumns!=10){
				columnChartData.setFillRatio(numColumns/10F);
			}
			return columnChartData;
		}

		private void addLineToData() {
			if (data.getLineChartData().getLines().size() >= maxNumberOfLines) {
				Toast.makeText(getActivity(), "Samples app uses max 4 lines!",
						Toast.LENGTH_SHORT).show();
				return;
			} else {
				++numberOfLines;
			}

			generateData();
		}

		private void toggleLines() {
			hasLines = !hasLines;

			generateData();
		}

		private void togglePoints() {
			hasPoints = !hasPoints;

			generateData();
		}

		private void toggleCubic() {
			isCubic = !isCubic;

			generateData();
		}

		private void toggleLabels() {
			hasLabels = !hasLabels;

			generateData();
		}

		private void toggleAxes() {
			hasAxes = !hasAxes;

			generateData();
		}

		private void toggleAxesNames() {
			hasAxesNames = !hasAxesNames;

			generateData();
		}

		private void prepareDataAnimation() {

			// Line animations
			for (Line line : data.getLineChartData().getLines()) {
				for (PointValue value : line.getValues()) {
					// Here I modify target only for Y values but it is OK to
					// modify X targets as well.
					value.setTarget(value.getX(),
							(float) Math.random() * 50 + 5);
				}
			}

			// Columns animations
			for (Column column : data.getColumnChartData().getColumns()) {
				for (SubcolumnValue value : column.getValues()) {
					value.setTarget((float) Math.random() * 50 + 5);
				}
			}
		}

		/*private class ValueTouchListener implements
				ComboLineColumnChartOnValueSelectListener {

			@Override
			public void onValueDeselected() {
				// TODO Auto-generated method stub

			}

			@Override
			public void onColumnValueSelected(int columnIndex,
					int subcolumnIndex, SubcolumnValue value) {
				Toast.makeText(getActivity(), "Selected column: " + value,
						Toast.LENGTH_SHORT).show();
			}

			@Override
			public void onPointValueSelected(int lineIndex, int pointIndex,
					PointValue value) {
				Toast.makeText(getActivity(), "Selected line point: " + value,
						Toast.LENGTH_SHORT).show();
			}

		}*/
	}
}





/*public class PlaceholderFragment1 extends Fragment {

	private ComboLineColumnChartView chart;
	private ComboLineColumnChartData data;

	private int numberOfLines = 1;
	private int maxNumberOfLines = 4;
	private int numberOfPoints = 10;
	float[][] randomNumbersTab = new float[maxNumberOfLines][numberOfPoints];

	private boolean hasAxes = true;
	private boolean hasAxesNames = true;
	private boolean hasPoints = true;
	private boolean hasLines = true;
	private boolean isCubic = false;
	private boolean hasLabels = false;
	public String[] name = new String[10];
	public String[] value=new String[10];

	public PlaceholderFragment1() {
	}

	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		setHasOptionsMenu(true);
		View rootView = inflater
				.inflate(R.layout.fragment_combo_line_column_chart,
						container, false);

		for(int i=0;i<10;i++){
			
			name[i]=listarray1.get(i*2);
			value[i]=listarray1.get(i*2+1);
			
		}
		
		chart = (ComboLineColumnChartView) rootView
				.findViewById(R.id.chart);
		chart.setOnValueTouchListener(new ValueTouchListener());

		generateValues();
		generateData();

		Button buttoncha=(Button) rootView.findViewById(R.id.buttoncha);
		buttoncha.setOnClickListener(change);
		
		return rootView;
	}


	// MENU
	@Override
	public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
		inflater.inflate(R.menu.combo_line_column_chart, menu);
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		int id = item.getItemId();
		if (id == R.id.action_reset) {
			reset();
			generateData();
			return true;
		}
		if (id == R.id.action_add_line) {
			addLineToData();
			return true;
		}
		if (id == R.id.action_toggle_lines) {
			toggleLines();
			return true;
		}
		if (id == R.id.action_toggle_points) {
			togglePoints();
			return true;
		}
		if (id == R.id.action_toggle_cubic) {
			toggleCubic();
			return true;
		}
		if (id == R.id.action_toggle_labels) {
			toggleLabels();
			return true;
		}
		if (id == R.id.action_toggle_axes) {
			toggleAxes();
			return true;
		}
		if (id == R.id.action_toggle_axes_names) {
			toggleAxesNames();
			return true;
		}
		if (id == R.id.action_animate) {
			prepareDataAnimation();
			chart.startDataAnimation();
			return true;
		}
		return super.onOptionsItemSelected(item);
	}



	private void generateValues() {
		float number=0;
		for (int i = 0; i < maxNumberOfLines; ++i) {
			for (int j = 0; j < numberOfPoints; ++j) {
				randomNumbersTab[i][j] = number;
				number++;
			}
		}
	}

	private void reset() {
		numberOfLines = 1;

		hasAxes = true;
		hasAxesNames = true;
		hasLines = true;
		hasPoints = true;
		hasLabels = false;
		isCubic = false;

	}

	private void generateData() {
		// Chart looks the best when line data and column data have similar
		// maximum viewports.
		List<AxisValue> axisValues = new ArrayList<AxisValue>();
		for(int i=0;i<10;i++){
			axisValues.add(new AxisValue(i).setLabel(name[i]));
		}
		data = new ComboLineColumnChartData(generateColumnData(),
				generateLineData());

		if (hasAxes) {
			Axis axisX = new Axis();
			Axis axisY = new Axis().setHasLines(true);
			if (hasAxesNames) {
				axisX.setName("����");
				axisY.setName("ʱ��");
			}
			
			data.setAxisXBottom(new Axis(axisValues).setHasLines(true));
			data.setAxisYLeft(new Axis().setHasLines(true)
					.setMaxLabelChars(5));
			
		} else {
			data.setAxisXBottom(null);
			data.setAxisYLeft(null);
		}

		chart.setComboLineColumnChartData(data);
	}

	private LineChartData generateLineData() {

		List<Line> lines = new ArrayList<Line>();
		for (int i = 0; i < numberOfLines; ++i) {

			List<PointValue> values = new ArrayList<PointValue>();
			for (int j = 0; j < numberOfPoints; ++j) {
				values.add(new PointValue(j, randomNumbersTab[i][j]));
			}

			Line line = new Line(values);
			line.setColor(ChartUtils.COLOR_RED);
			line.setCubic(isCubic);
			line.setHasLabels(hasLabels);
			line.setHasLines(hasLines);
			line.setHasPoints(hasPoints);
			lines.add(line);
		}

		LineChartData lineChartData = new LineChartData(lines);

		return lineChartData;

	}

	private ColumnChartData generateColumnData() {
		int numSubcolumns = 1;
		int numColumns = 10;
		// Column can have many subcolumns, here by default I use 1
		// subcolumn in each of 8 columns.
		List<Column> columns = new ArrayList<Column>();
		List<SubcolumnValue> values;
		float number=0;
		for (int i = 0; i < numColumns; ++i) {

			values = new ArrayList<SubcolumnValue>();
			for (int j = 0; j < numSubcolumns; ++j) {
				values.add(new SubcolumnValue(
						number,ChartUtils.COLOR_BLUE));
			}
			number++;
			columns.add(new Column(values));
		}

		ColumnChartData columnChartData = new ColumnChartData(columns);
		return columnChartData;
	}

	private void addLineToData() {
		if (data.getLineChartData().getLines().size() >= maxNumberOfLines) {
			Toast.makeText(getActivity(), "Samples app uses max 4 lines!",
					Toast.LENGTH_SHORT).show();
			return;
		} else {
			++numberOfLines;
		}

		generateData();
	}

	private void toggleLines() {
		hasLines = !hasLines;

		generateData();
	}

	private void togglePoints() {
		hasPoints = !hasPoints;

		generateData();
	}

	private void toggleCubic() {
		isCubic = !isCubic;

		generateData();
	}

	private void toggleLabels() {
		hasLabels = !hasLabels;

		generateData();
	}

	private void toggleAxes() {
		hasAxes = !hasAxes;

		generateData();
	}

	private void toggleAxesNames() {
		hasAxesNames = !hasAxesNames;

		generateData();
	}

	private void prepareDataAnimation() {

		// Line animations
		for (Line line : data.getLineChartData().getLines()) {
			for (PointValue value : line.getValues()) {
				// Here I modify target only for Y values but it is OK to
				// modify X targets as well.
				value.setTarget(value.getX(),
						(float) Math.random() * 50 + 5);
			}
		}

		// Columns animations
		for (Column column : data.getColumnChartData().getColumns()) {
			for (SubcolumnValue value : column.getValues()) {
				value.setTarget((float) Math.random() * 50 + 5);
			}
		}
	}

	private class ValueTouchListener implements
			ComboLineColumnChartOnValueSelectListener {

		@Override
		public void onValueDeselected() {
			// TODO Auto-generated method stub

		}

		@Override
		public void onColumnValueSelected(int columnIndex,
				int subcolumnIndex, SubcolumnValue value) {
			Toast.makeText(getActivity(), "Selected column: " + value,
					Toast.LENGTH_SHORT).show();
		}

		@Override
		public void onPointValueSelected(int lineIndex, int pointIndex,
				PointValue value) {
			Toast.makeText(getActivity(), "Selected line point: " + value,
					Toast.LENGTH_SHORT).show();
		}

	}
}*/
