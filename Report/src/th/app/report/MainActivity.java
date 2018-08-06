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
	public Button button1,button2,button3,button4,button5,button6,button7;
	private java.sql.Connection conn = null;
	private java.sql.Statement stmt =null;
	private String sendtype;
	private Dialog progressDialog;
	public String ins;
	public String instype;
	public String ipconfig;
	public String base;
	public String name;
	public String ser;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		
		Intent intent=getIntent();
		ins = intent.getStringExtra("ins");
		ins = intent.getStringExtra("ins");
		instype = intent.getStringExtra("instype");
		ipconfig = intent.getStringExtra("ip");
		base = intent.getStringExtra("base");
		name = intent.getStringExtra("name");
		ser = intent.getStringExtra("ser");
		
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
		button6=(Button)findViewById(R.id.btweldusetop);
		button7=(Button)findViewById(R.id.btweldusebot);
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
            	
            	//焊机工时高排行
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
            	
            	Intent intent1=new Intent(MainActivity.this,Firstpage.class);
            	startActivity(intent1);
            	
            }
        });
		
		button6.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
            	
            	progressDialog = new Dialog(MainActivity.this,R.style.progress_dialog);  
            	progressDialog.setContentView(R.layout.dialog);  
            	progressDialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);  
            	TextView msg = (TextView) progressDialog.findViewById(R.id.id_tv_loadingmsg);  
            	msg.setText("正在查询");  
            	progressDialog.show(); 
            	
            	//设备利用率高排行
        		new Thread(runnable6).start();
            }
        });
		
		button7.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
            	
            	progressDialog = new Dialog(MainActivity.this,R.style.progress_dialog);  
            	progressDialog.setContentView(R.layout.dialog);  
            	progressDialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);  
            	TextView msg = (TextView) progressDialog.findViewById(R.id.id_tv_loadingmsg);  
            	msg.setText("正在查询");  
            	progressDialog.show(); 
            	
            	//设备利用率低排行
        		new Thread(runnable7).start();
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
		        	conn = DriverManager.getConnection("jdbc:mysql://" + ipconfig + ":3306/" + base + "", "" + name + "", "" + ser + "");
					stmt= conn.createStatement();
					
					//String Command1="select count(l.fid) num,fequipment_no from tb_live_data l LEFT JOIN tb_welding_machine m on m.fid = l.fmachine_id WHERE 1=1 and fweldtime >= '2017-12-20' and fweldtime <= '2019-03-01' group by fmachine_id ORDER by num DESC";
					//String Command1="SELECT SUM(tb_work.fworktime),tb_welding_machine.fequipment_no FROM tb_work INNER JOIN tb_welding_machine ON tb_work.fmachine_id = tb_welding_machine.fid WHERE 1=1 GROUP BY tb_welding_machine.fequipment_no ORDER BY SUM(tb_work.fworktime) DESC";
					String Command1="SELECT SUM(tb_work.fworktime),tb_welding_machine.fequipment_no FROM tb_work INNER JOIN tb_welding_machine ON tb_welding_machine.fid = tb_work.fmachine_id INNER JOIN tb_insframework ON tb_work.fitemid = tb_insframework.fid INNER JOIN tb_insframework ins1 ON ins1.fid = tb_insframework.fparent INNER JOIN tb_insframework ins2 ON ins2.fid = tb_insframework.fparent WHERE 1 = 1 AND (ins2.fparent = " + ins + " OR ins1.fparent = " + ins + " OR tb_insframework.fparent = " + ins + " OR tb_insframework.fid = " + ins +") GROUP BY tb_welding_machine.fequipment_no ORDER BY SUM(tb_work.fworktime) DESC";
					
					rs = stmt.executeQuery(Command1);
					
					listarray1.clear();
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
        	sendtype="machtop";
			Intent intent1=new Intent(MainActivity.this,ComboLineColumnChartActivity.class);
			intent1.putStringArrayListExtra("data", listarray1);
			intent1.putExtra("type", sendtype);
			intent1.putExtra("name", "焊机工时TOP");
			intent1.putExtra("ins", ins);
			intent1.putExtra("instype", instype);
			intent1.putExtra("ip", ipconfig);
			intent1.putExtra("base", base);
			intent1.putExtra("name", name);
			intent1.putExtra("ser", ser);
			
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
		        	conn = DriverManager.getConnection("jdbc:mysql://" + ipconfig + ":3306/" + base + "", "" + name + "", "" + ser + "");
					stmt= conn.createStatement();
					
					//String Command1="select COUNT(tb_live_data.fid) num,tb_welder.fname FROM tb_welder left JOIN tb_live_data on tb_welder.fwelder_no = tb_live_data.fwelder_id WHERE 1=1 and fweldtime >= '2017-12-20' and fweldtime <= '2019-03-01' group by tb_welder.fname ORDER by num DESC";
					//String Command1="SELECT SUM(tb_work.fworktime),tb_welder.fname FROM tb_work INNER JOIN tb_welder ON tb_work.fwelder_id = tb_welder.fwelder_no WHERE 1=1 GROUP BY tb_welder.fname ORDER BY SUM(tb_work.fworktime) DESC";
					String Command1="SELECT SUM(tb_work.fworktime),tb_welder.fname FROM tb_work INNER JOIN tb_welder ON tb_work.fwelder_id = tb_welder.fwelder_no INNER JOIN tb_insframework ON tb_work.fitemid = tb_insframework.fid INNER JOIN tb_insframework ins1 ON ins1.fid = tb_insframework.fparent INNER JOIN tb_insframework ins2 ON ins2.fid = tb_insframework.fparent WHERE 1 = 1 AND (ins2.fparent = " + ins + " OR ins1.fparent = " + ins + " OR tb_insframework.fparent = " + ins + " OR tb_insframework.fid = " + ins +") GROUP BY tb_welder.fname ORDER BY SUM(tb_work.fworktime) DESC";
					
					rs = stmt.executeQuery(Command1);
					
					listarray1.clear();
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
        	sendtype="weldtop";
			Intent intent1=new Intent(MainActivity.this,ComboLineColumnChartActivity.class);
			intent1.putStringArrayListExtra("data", listarray1);
			intent1.putExtra("type", sendtype);
			intent1.putExtra("name", "焊工工时TOP");
			intent1.putExtra("ins", ins);
			intent1.putExtra("instype", instype);
			intent1.putExtra("ip", ipconfig);
			intent1.putExtra("base", base);
			intent1.putExtra("name", name);
			intent1.putExtra("ser", ser);
			
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
		        	conn = DriverManager.getConnection("jdbc:mysql://" + ipconfig + ":3306/" + base + "", "" + name + "", "" + ser + "");
					stmt= conn.createStatement();
					
					//String Command1="select count(l.fid) num,fequipment_no from tb_live_data l LEFT JOIN tb_welding_machine m on m.fid = l.fmachine_id WHERE 1=1 and fweldtime >= '2017-12-20' and fweldtime <= '2019-03-01' group by fmachine_id ORDER by num ASC";
					//String Command1="SELECT SUM(tb_work.fworktime),tb_welding_machine.fequipment_no FROM tb_work INNER JOIN tb_welding_machine ON tb_work.fmachine_id = tb_welding_machine.fid WHERE 1=1 GROUP BY tb_welding_machine.fequipment_no ORDER BY SUM(tb_work.fworktime) ASC";
					String Command1="SELECT SUM(tb_work.fworktime),tb_welding_machine.fequipment_no FROM tb_work INNER JOIN tb_welding_machine ON tb_welding_machine.fid = tb_work.fmachine_id INNER JOIN tb_insframework ON tb_work.fitemid = tb_insframework.fid INNER JOIN tb_insframework ins1 ON ins1.fid = tb_insframework.fparent INNER JOIN tb_insframework ins2 ON ins2.fid = tb_insframework.fparent WHERE 1 = 1 AND (ins2.fparent = " + ins + " OR ins1.fparent = " + ins + " OR tb_insframework.fparent = " + ins + " OR tb_insframework.fid = " + ins +") GROUP BY tb_welding_machine.fequipment_no ORDER BY SUM(tb_work.fworktime) ASC";
					
					rs = stmt.executeQuery(Command1);
					
					listarray1.clear();
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
        	sendtype="machbot";
			Intent intent1=new Intent(MainActivity.this,ComboLineColumnChartActivity.class);
			intent1.putStringArrayListExtra("data", listarray1);
			intent1.putExtra("type", sendtype);
			intent1.putExtra("name", "焊机工时BOT");
			intent1.putExtra("ins", ins);
			intent1.putExtra("instype", instype);
			intent1.putExtra("ip", ipconfig);
			intent1.putExtra("base", base);
			intent1.putExtra("name", name);
			intent1.putExtra("ser", ser);
			
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
		        	conn = DriverManager.getConnection("jdbc:mysql://" + ipconfig + ":3306/" + base + "", "" + name + "", "" + ser + "");
					stmt= conn.createStatement();
					
					//String Command1="select COUNT(tb_live_data.fid) num,tb_welder.fname FROM tb_welder left JOIN tb_live_data on tb_welder.fwelder_no = tb_live_data.fwelder_id WHERE 1=1 and fweldtime >= '2017-12-20' and fweldtime <= '2019-03-01' group by tb_welder.fname ORDER by num ASC";
					//String Command1="SELECT SUM(tb_work.fworktime),tb_welder.fname FROM tb_work INNER JOIN tb_welder ON tb_work.fwelder_id = tb_welder.fwelder_no WHERE 1=1 GROUP BY tb_welder.fname ORDER BY SUM(tb_work.fworktime) ASC";
					String Command1 = "SELECT SUM(tb_work.fworktime),tb_welder.fname FROM tb_work INNER JOIN tb_welder ON tb_work.fwelder_id = tb_welder.fwelder_no INNER JOIN tb_insframework ON tb_work.fitemid = tb_insframework.fid INNER JOIN tb_insframework ins1 ON ins1.fid = tb_insframework.fparent INNER JOIN tb_insframework ins2 ON ins2.fid = tb_insframework.fparent WHERE 1 = 1 AND (ins2.fparent = " + ins + " OR ins1.fparent = " + ins + " OR tb_insframework.fparent = " + ins + " OR tb_insframework.fid = " + ins +") GROUP BY tb_welder.fname ORDER BY SUM(tb_work.fworktime) ASC";
					
					rs = stmt.executeQuery(Command1);
					
					listarray1.clear();
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
        	sendtype="weldbot";
			Intent intent1=new Intent(MainActivity.this,ComboLineColumnChartActivity.class);
			intent1.putStringArrayListExtra("data", listarray1);
			intent1.putExtra("type", sendtype);
			intent1.putExtra("name", "焊工工时BOT");
			intent1.putExtra("ins", ins);
			intent1.putExtra("instype", instype);
			intent1.putExtra("ip", ipconfig);
			intent1.putExtra("base", base);
			intent1.putExtra("name", name);
			intent1.putExtra("ser", ser);
			
			progressDialog.dismiss();
			
			startActivity(intent1);
    	}
	};
	
	//设备利用率高
	Runnable runnable6 = new Runnable() {
        ResultSet rs;
        public void run() {
        	
        	try {
				Class.forName("com.mysql.jdbc.Driver");
		        try {
		        	conn = DriverManager.getConnection("jdbc:mysql://" + ipconfig + ":3306/" + base + "", "" + name + "", "" + ser + "");
					stmt= conn.createStatement();
					
					//String Command1="select COUNT(tb_live_data.fid) num,tb_welder.fname FROM tb_welder left JOIN tb_live_data on tb_welder.fwelder_no = tb_live_data.fwelder_id WHERE 1=1 and fweldtime >= '2017-12-20' and fweldtime <= '2019-03-01' group by tb_welder.fname ORDER by num ASC";
					//String Command1="SELECT SUM(tb_work.fworktime),tb_welder.fname FROM tb_work INNER JOIN tb_welder ON tb_work.fwelder_id = tb_welder.fwelder_no WHERE 1=1 GROUP BY tb_welder.fname ORDER BY SUM(tb_work.fworktime) ASC";
					
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
					
		        } catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
        	} catch (ClassNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
        	sendtype="weldusetop";
			Intent intent1=new Intent(MainActivity.this,ComboLineColumnChartActivity.class);
			intent1.putStringArrayListExtra("data", listarray1);
			intent1.putExtra("type", sendtype);
			intent1.putExtra("name", "设备利用率TOP");
			intent1.putExtra("ins", ins);
			intent1.putExtra("instype", instype);
			intent1.putExtra("ip", ipconfig);
			intent1.putExtra("base", base);
			intent1.putExtra("name", name);
			intent1.putExtra("ser", ser);
			
			progressDialog.dismiss();
			
			startActivity(intent1);
    	}
	};
	
	//设备利用率低
	Runnable runnable7 = new Runnable() {
        ResultSet rs;
        public void run() {
        	
        	try {
				Class.forName("com.mysql.jdbc.Driver");
		        try {
		        	conn = DriverManager.getConnection("jdbc:mysql://" + ipconfig + ":3306/" + base + "", "" + name + "", "" + ser + "");
					stmt= conn.createStatement();
					
					//String Command1="select COUNT(tb_live_data.fid) num,tb_welder.fname FROM tb_welder left JOIN tb_live_data on tb_welder.fwelder_no = tb_live_data.fwelder_id WHERE 1=1 and fweldtime >= '2017-12-20' and fweldtime <= '2019-03-01' group by tb_welder.fname ORDER by num ASC";
					//String Command1="SELECT SUM(tb_work.fworktime),tb_welder.fname FROM tb_work INNER JOIN tb_welder ON tb_work.fwelder_id = tb_welder.fwelder_no WHERE 1=1 GROUP BY tb_welder.fname ORDER BY SUM(tb_work.fworktime) ASC";
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
					
		        } catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
        	} catch (ClassNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
        	sendtype="weldusebot";
			Intent intent1=new Intent(MainActivity.this,ComboLineColumnChartActivity.class);
			intent1.putStringArrayListExtra("data", listarray1);
			intent1.putExtra("type", sendtype);
			intent1.putExtra("name", "设备利用率BOT");
			intent1.putExtra("ins", ins);
			intent1.putExtra("instype", instype);
			intent1.putExtra("ip", ipconfig);
			intent1.putExtra("base", base);
			intent1.putExtra("name", name);
			intent1.putExtra("ser", ser);
			
			progressDialog.dismiss();
			
			startActivity(intent1);
    	}
	};
		
	/*@Override
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
	}*/
}
