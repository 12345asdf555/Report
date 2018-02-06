package th.app.report;

import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Timer;
import java.util.TimerTask;

import com.jeremyfeinstein.slidingmenu.lib.SlidingMenu;

import android.app.Activity;
import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.provider.Settings.System;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends Activity {
	
	public ArrayList<String> listarray1 = new ArrayList<String>();
	public ArrayList<String> listarray2 = new ArrayList<String>();
	public 	SlidingMenu slidingMenu;
	public Button button1,button2,button3,button4,button5;
	private java.sql.Connection conn = null;
	private java.sql.Statement stmt =null;
	private String Command1;
	private String sendtype;
	private Dialog progressDialog;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		slidingMenu = new SlidingMenu(this);
		slidingMenu.setMode(SlidingMenu.LEFT);  //菜单从左边滑出
		slidingMenu.setBehindWidth(600);        //菜单的宽度
		slidingMenu.setTouchModeAbove(SlidingMenu.TOUCHMODE_FULLSCREEN);//菜单全屏都可滑出
		slidingMenu.attachToActivity(this, SlidingMenu.SLIDING_CONTENT);
		slidingMenu.setMenu(R.layout.menu_layout);
		slidingMenu.toggle();
		
		button1=(Button)findViewById(R.id.btmachtop);
		button2=(Button)findViewById(R.id.btweldtop);
		button3=(Button)findViewById(R.id.btmachbot);
		button4=(Button)findViewById(R.id.btweldbot);
		button5=(Button)findViewById(R.id.btreturn);
		button1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
            	
            	progressDialog = new Dialog(MainActivity.this,R.style.progress_dialog);  
            	progressDialog.setContentView(R.layout.dialog);  
            	progressDialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);  
            	TextView msg = (TextView) progressDialog.findViewById(R.id.id_tv_loadingmsg);  
            	msg.setText("正在查询");  
            	progressDialog.show(); 
            	
            	//焊机工时高排行
        		new Thread(runnable1).start();
            }
        });
		
		button2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
            	
            	progressDialog = new Dialog(MainActivity.this,R.style.progress_dialog);  
            	progressDialog.setContentView(R.layout.dialog);  
            	progressDialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);  
            	TextView msg = (TextView) progressDialog.findViewById(R.id.id_tv_loadingmsg);  
            	msg.setText("正在查询");  
            	progressDialog.show(); 
            	
            	//焊工工时高排行
        		new Thread(runnable2).start();
            }
        });
		
		button3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
            	
            	progressDialog = new Dialog(MainActivity.this,R.style.progress_dialog);  
            	progressDialog.setContentView(R.layout.dialog);  
            	progressDialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);  
            	TextView msg = (TextView) progressDialog.findViewById(R.id.id_tv_loadingmsg);  
            	msg.setText("正在查询");  
            	progressDialog.show(); 
            	
            	//焊机工时高排行
        		new Thread(runnable3).start();
            }
        });
		
		button4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
            	
            	progressDialog = new Dialog(MainActivity.this,R.style.progress_dialog);  
            	progressDialog.setContentView(R.layout.dialog);  
            	progressDialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);  
            	TextView msg = (TextView) progressDialog.findViewById(R.id.id_tv_loadingmsg);  
            	msg.setText("正在查询");  
            	progressDialog.show(); 
            	
            	//焊工工时低排行
        		new Thread(runnable4).start();
            }
        });
		
		button5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
            	/*Intent intent2=new Intent(MainActivity.this,ComboLineColumnChartActivity.class);
            	startActivity(intent2);*/
                //MainActivity.this.finish();
            }
        });
	}
		
	//焊机工时高排行
	Runnable runnable1 = new Runnable() {
        ResultSet rs;
        public void run() { 
        	
        	try {
				Class.forName("com.mysql.jdbc.Driver");
		        try {
					conn = DriverManager.getConnection("jdbc:mysql://121.196.222.216:3306/THWeld", "root", "123456");
					stmt= conn.createStatement();
					
					Command1="select count(l.fid) num,fequipment_no from tb_live_data l LEFT JOIN tb_welding_machine m on m.fid = l.fmachine_id WHERE 1=1 and fweldtime >= '2017-12-20' and fweldtime <= '2018-03-01' group by fmachine_id ORDER by num DESC";
					
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
        	sendtype="machtop";
			Intent intent1=new Intent(MainActivity.this,ComboLineColumnChartActivity.class);
			intent1.putStringArrayListExtra("data", listarray1);
			intent1.putExtra("type", sendtype);
			intent1.putExtra("name", "焊机工时top");
			
			progressDialog.dismiss();
			
			startActivity(intent1);
    	}
	};
	
	//焊工工时高排行
	Runnable runnable2 = new Runnable() {
        ResultSet rs;
        public void run() {
        	
        	try {
				Class.forName("com.mysql.jdbc.Driver");
		        try {
					conn = DriverManager.getConnection("jdbc:mysql://121.196.222.216:3306/THWeld", "root", "123456");
					stmt= conn.createStatement();
					
					Command1="select COUNT(tb_live_data.fid) num,tb_welder.fname FROM tb_welder left JOIN tb_live_data on tb_welder.fwelder_no = tb_live_data.fwelder_id WHERE 1=1 and fweldtime >= '2017-12-20' and fweldtime <= '2018-03-01' group by tb_welder.fname ORDER by num DESC";
					
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
        	sendtype="weldtop";
			Intent intent1=new Intent(MainActivity.this,ComboLineColumnChartActivity.class);
			intent1.putStringArrayListExtra("data", listarray1);
			intent1.putExtra("type", sendtype);
			intent1.putExtra("name", "焊工工时top");
			
			progressDialog.dismiss();
			
			startActivity(intent1);
    	}
	};
	
	//焊机工时低排行
		Runnable runnable3 = new Runnable() {
	        ResultSet rs;
	        public void run() {
	        	
	        	try {
					Class.forName("com.mysql.jdbc.Driver");
			        try {
						conn = DriverManager.getConnection("jdbc:mysql://121.196.222.216:3306/THWeld", "root", "123456");
						stmt= conn.createStatement();
						
						Command1="select count(l.fid) num,fequipment_no from tb_live_data l LEFT JOIN tb_welding_machine m on m.fid = l.fmachine_id WHERE 1=1 and fweldtime >= '2017-12-20' and fweldtime <= '2018-03-01' group by fmachine_id ORDER by num ASC";
						
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
	        	sendtype="machbot";
				Intent intent1=new Intent(MainActivity.this,ComboLineColumnChartActivity.class);
				intent1.putStringArrayListExtra("data", listarray1);
				intent1.putExtra("type", sendtype);
				intent1.putExtra("name", "焊机工时bot");
				
				progressDialog.dismiss();
				
				startActivity(intent1);
	    	}
		};
	
		//焊工工时低排行
		Runnable runnable4 = new Runnable() {
	        ResultSet rs;
	        public void run() {
	        	
	        	try {
					Class.forName("com.mysql.jdbc.Driver");
			        try {
						conn = DriverManager.getConnection("jdbc:mysql://121.196.222.216:3306/THWeld", "root", "123456");
						stmt= conn.createStatement();
						
						Command1="select COUNT(tb_live_data.fid) num,tb_welder.fname FROM tb_welder left JOIN tb_live_data on tb_welder.fwelder_no = tb_live_data.fwelder_id WHERE 1=1 and fweldtime >= '2017-12-20' and fweldtime <= '2018-03-01' group by tb_welder.fname ORDER by num ASC";
						
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
	        	sendtype="weldbot";
				Intent intent1=new Intent(MainActivity.this,ComboLineColumnChartActivity.class);
				intent1.putStringArrayListExtra("data", listarray1);
				intent1.putExtra("type", sendtype);
				intent1.putExtra("name", "焊工工时bot");
				
				progressDialog.dismiss();
				
				startActivity(intent1);
	    	}
		};
		
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		int id = item.getItemId();
		if (id == R.id.action_settings) {
			return true;
		}
		return super.onOptionsItemSelected(item);
	}
}
