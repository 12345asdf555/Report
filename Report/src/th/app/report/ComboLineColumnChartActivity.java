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
	public Button button1,button2,button3,button4,button5;
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
	private String command1="select count(l.fid) num,fequipment_no from tb_live_data l LEFT JOIN tb_welding_machine m on m.fid = l.fmachine_id WHERE 1=1 and fweldtime >= '";
	private String command2="' and fweldtime <= '";
	private String command3="' group by fmachine_id ORDER by num DESC";
	
	private String command4="select COUNT(tb_live_data.fid) num,tb_welder.fname FROM tb_welder left JOIN tb_live_data on tb_welder.fwelder_no = tb_live_data.fwelder_id WHERE 1=1 and fweldtime >= '";
	private String command5="' and fweldtime <= '";
	private String command6="' group by tb_welder.fname ORDER by num DESC";
	
	private String command7="select count(l.fid) num,fequipment_no from tb_live_data l LEFT JOIN tb_welding_machine m on m.fid = l.fmachine_id WHERE 1=1 and fweldtime >= '";
	private String command8="' and fweldtime <= '";
	private String command9="' group by fmachine_id ORDER by num ASC";
	
	private String command10="select COUNT(tb_live_data.fid) num,tb_welder.fname FROM tb_welder left JOIN tb_live_data on tb_welder.fwelder_no = tb_live_data.fwelder_id WHERE 1=1 and fweldtime >= '";
	private String command11="' and fweldtime <= '";
	private String command12="' group by tb_welder.fname ORDER by num ASC";
	
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
					tv.setText("焊机工时top");
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
					tv.setText("焊工工时top");
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
					tv.setText("焊机工时bot");
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
					tv.setText("焊工工时bot");
            		new Thread(runnable4).start();
				}
            }
        });
		button5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
            	Intent intent2=new Intent(ComboLineColumnChartActivity.this,MainActivity.class);
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
					conn = DriverManager.getConnection("jdbc:mysql://121.196.222.216:3306/THWeld", "root", "123456");
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
			        		Command1=command1+aa1+command2+bb1+command3;
			        	}else{
			        		Command1=command1+aa+command2+bb+command3;
			        	}
						rs = stmt.executeQuery(Command1);
						
						while (rs.next()) {
						  String num = Integer.toString(Integer.valueOf(rs.getString("num"))/3600);
				      	  String fequipment_no = Integer.toString(Integer.parseInt(rs.getString("fequipment_no"), 16));
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
			        		Command1=command4+aa1+command5+bb1+command6;
			        	}else{
			        		Command1=command4+aa+command5+bb+command6;
			        	}
						rs = stmt.executeQuery(Command1);
						
						while (rs.next()) {
						  String num = Integer.toString(Integer.valueOf(rs.getString("num"))/3600);
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
				        		Command1=command7+aa1+command8+bb1+command9;
				        	}else{
				        		Command1=command7+aa+command8+bb+command9;
				        	}
							rs = stmt.executeQuery(Command1);
							
							while (rs.next()) {
							  String num = Integer.toString(Integer.valueOf(rs.getString("num"))/3600);
					      	  String fequipment_no = Integer.toString(Integer.parseInt(rs.getString("fequipment_no"), 16));
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
				        		Command1=command10+aa1+command11+bb1+command12;
				        	}else{
				        		Command1=command10+aa+command11+bb+command12;
				        	}
							rs = stmt.executeQuery(Command1);
							
							while (rs.next()) {
							  String num = Integer.toString(Integer.valueOf(rs.getString("num"))/3600);
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
		//继承了Activity的onTouchEvent方法，直接监听点击事件  
        if(event.getAction() == MotionEvent.ACTION_DOWN) {  
            //当手指按下的时候  
            x1 = event.getX();  
            y1 = event.getY();  
        }  
        if(event.getAction() == MotionEvent.ACTION_MOVE) {
        	Intent intent1=new Intent(ComboLineColumnChartActivity.this,Excelreport.class);
            startActivity(intent1);
        	overridePendingTransition(R.anim.in_from_down, R.anim.out_to_up);
        }
        if(event.getAction() == MotionEvent.ACTION_UP) {  
            //当手指离开的时候  
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
						listarray1.add("无");
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
					number=Integer.valueOf(value[j]);
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
				Axis axisX = new Axis();
				Axis axisY = new Axis().setHasLines(true);
				if (hasAxesNames) {
					axisX.setName("焊机");
					axisY.setName("时间/小时");
				}
				data.setAxisXBottom(new Axis(axisValues).setHasLines(true));
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
					values.add(new SubcolumnValue(
							(float)Integer.valueOf(value[i]),ChartUtils.COLOR_BLUE));
				}
				Column column = new Column(values);
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
				axisX.setName("焊机");
				axisY.setName("时间");
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
