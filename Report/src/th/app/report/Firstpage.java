package th.app.report;

import java.io.ByteArrayOutputStream;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Arrays;
import java.util.Timer;
import java.util.TimerTask;

import org.apache.commons.codec.binary.Hex;
import org.apache.commons.codec.digest.DigestUtils;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.Looper;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.TextView;

public class Firstpage extends Activity {
	
	private java.sql.Connection conn = null;
	private java.sql.Statement stmt =null;
	public Dialog progressDialog;
	public Button bt;
	public EditText ed1;
	public EditText ed2;
	public String user;
	public String code;
	public float fontScale;
	public String config = "";
	public String ip = "";
	public String base = "";
	public String name = "";
	public String ser = "";
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_firstpage);
		
		WindowManager wm = (WindowManager) this
				.getSystemService(Context.WINDOW_SERVICE);
		
		int widthpx = wm.getDefaultDisplay().getWidth();
		int heightpx = wm.getDefaultDisplay().getHeight();
		
		int widthsp = px2sp(getBaseContext(),widthpx);
		int heightsp = px2sp(getBaseContext(),heightpx);
		
		//有横杠
		int ems = widthpx/300;
	    
	    double widthn1 = widthpx/1.85;
	    int widthnt1 = (int)widthn1;
	    double heightn1 = heightpx/2.7;
	    int heightn = (int)heightn1;
	    
	    double widthn2 = widthpx/1.65;
	    int widthne1 = (int)widthn2;
	    double heightn2 = heightpx/2.85;
	    int heighe1 = (int)heightn2;
	    
	    double heightn3 = heightpx/2.2;
	    int heighn3 = (int)heightn3;
	    
	    double heightn4 = heightpx/2.35;
	    int heighe2 = (int)heightn4;
	    
	    double widthn3 = widthpx/1.58;
	    int widthne3 = (int)widthn3;
	    double heightn5 = heightpx/1.8;
	    int heighe3 = (int)heightn5;
		
	    //无横杠
		/*int ems = widthpx/300;
		
		double widthn1 = widthpx/2.00;
		int widthnt1 = (int)widthn1;
		double heightn1 = heightpx/2.7;
		int heightn = (int)heightn1;
		
		double widthn2 = widthpx/1.80;
		int widthne1 = (int)widthn2;
		double heightn2 = heightpx/2.85;
		int heighe1 = (int)heightn2;
		
		double heightn3 = heightpx/2.2;
		int heighn3 = (int)heightn3;
		
		double heightn4 = heightpx/2.40;
		int heighe2 = (int)heightn4;
		
		double widthn3 = widthpx/1.73;
		int widthne3 = (int)widthn3;
		double heightn5 = heightpx/1.8;
		int heighe3 = (int)heightn5;*/
		
		TextView textview1 = (TextView)findViewById(R.id.textView1);
		RelativeLayout.LayoutParams params1 = new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.WRAP_CONTENT, RelativeLayout.LayoutParams.WRAP_CONTENT);
		params1.setMargins(widthnt1, heightn, 0, 0);
		textview1.setLayoutParams(params1);
		
		ed1 = (EditText)findViewById(R.id.editText1);
		RelativeLayout.LayoutParams params2 = new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.WRAP_CONTENT, RelativeLayout.LayoutParams.WRAP_CONTENT);
		params2.setMargins(widthne1, heighe1, 0, 0);
		ed1.setLayoutParams(params2);
		ed1.setEms(ems);
		
		TextView textview2 = (TextView)findViewById(R.id.textView2);
		RelativeLayout.LayoutParams params3 = new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.WRAP_CONTENT, RelativeLayout.LayoutParams.WRAP_CONTENT);
		params3.setMargins(widthnt1, heighn3, 0, 0);
		textview2.setLayoutParams(params3);
		
		ed2 = (EditText)findViewById(R.id.editText2);
		RelativeLayout.LayoutParams params4 = new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.WRAP_CONTENT, RelativeLayout.LayoutParams.WRAP_CONTENT);
		params4.setMargins(widthne1, heighe2, 0, 0);
		ed2.setLayoutParams(params4);
		ed2.setEms(ems);
		
		bt = (Button)findViewById(R.id.button1);
		RelativeLayout.LayoutParams params5 = new RelativeLayout.LayoutParams(widthpx/10, heightpx/15);
		params5.setMargins(widthne3, heighe3, 0, 0);
		bt.setLayoutParams(params5);
		bt.setTextSize((float)widthpx/180);

		bt.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				
				user = ed1.getText().toString();
				code = ed2.getText().toString();
				
				if(user.isEmpty()){
					progressDialog = new Dialog(Firstpage.this,R.style.progress_dialog);  
	            	progressDialog.setContentView(R.layout.dialog2);  
	            	progressDialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);  
	            	TextView msg = (TextView) progressDialog.findViewById(R.id.id_tv_loadingmsg);  
	            	msg.setText("请填写用户名");  
	            	progressDialog.show(); 
				}else if(code.isEmpty()){
					progressDialog = new Dialog(Firstpage.this,R.style.progress_dialog);  
	            	progressDialog.setContentView(R.layout.dialog2);  
	            	progressDialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);  
	            	TextView msg = (TextView) progressDialog.findViewById(R.id.id_tv_loadingmsg);  
	            	msg.setText("请填写密码");  
	            	progressDialog.show(); 
				}else{
					
					ed1.setText("");
					ed2.setText("");
					
					code = new String(Hex.encodeHex(DigestUtils.md5(code)));
					
					try {  
		            	String releasepath = getApplicationContext().getFilesDir().getAbsolutePath()+"/ipconfig.txt";
		            	FileInputStream inputStream = new FileInputStream(releasepath);
		            	byte[] bytes = new byte[100];  
		                ByteArrayOutputStream arrayOutputStream = new ByteArrayOutputStream();  
		                while (inputStream.read(bytes) != -1) {  
		                    arrayOutputStream.write(bytes, 0, bytes.length);  
		                }
		                inputStream.close();  
		                arrayOutputStream.close(); 
		                
		                config = new String(bytes,"UTF-8");
		                
		                String[] configbuf = config.split(",");
		                ip = configbuf[0];
		                base = configbuf[1];
		                name = configbuf[2];
		                ser = configbuf[3];
		                
		            } catch (IOException e) {  
		                e.printStackTrace();  
		            }
					
	            	if(ip.equals("") || base.equals("") || name.equals("") || ser.equals("")){
	            		progressDialog = new Dialog(Firstpage.this,R.style.progress_dialog);  
		            	progressDialog.setContentView(R.layout.dialog2);  
		            	progressDialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);  
		            	TextView msg = (TextView) progressDialog.findViewById(R.id.id_tv_loadingmsg); 
		            	
	            		msg.setText("请先配置参数");  
	            		progressDialog.show();
	            	}else{
	            		progressDialog = new Dialog(Firstpage.this,R.style.progress_dialog);  
		            	progressDialog.setContentView(R.layout.dialog);  
		            	progressDialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);  
		            	TextView msg = (TextView) progressDialog.findViewById(R.id.id_tv_loadingmsg);
		            	
		            	msg.setText("登录中");  
		            	progressDialog.show(); 
		            	
		            	new Thread(runnable1).start();
	            	}
				}
				
			}
		});
		
		/*Timer timer=new Timer();
	    TimerTask timerTask=new TimerTask() {
	        @Override
	        public void run() {
	            Intent intent1=new Intent(Firstpage.this,MainActivity.class);
	            startActivity(intent1);
	            Firstpage.this.finish();
	        }
	    };
	    timer.schedule(timerTask,1000*3);*/

	}

	private int px2sp(Context context,float pxValue){
        fontScale=context.getResources().getDisplayMetrics().scaledDensity;
        return (int) (pxValue/fontScale+0.5f);
    }
	
	Runnable runnable1 = new Runnable() {
        ResultSet rs;
        public void run() { 
        	
        	try {
				Class.forName("com.mysql.jdbc.Driver");
		        try {
					conn = DriverManager.getConnection("jdbc:mysql://" + ip + ":3306/" + base + "", "" + name + "", "" + ser + "");
		        	//conn = DriverManager.getConnection("jdbc:mysql://" + ip + ":3306/Weld", "db_admin", "PIJXmcLRa0QgOw2c");
		        	stmt= conn.createStatement();
					
					user="'"+user+"'";
					String Command1="SELECT tb_users.users_login_name,tb_users.users_password,tb_users.users_insframework,tb_insframework.ftype from tb_users INNER JOIN tb_insframework ON tb_users.users_insframework = tb_insframework.fid where tb_users.users_login_name="+user;
					
					rs = stmt.executeQuery(Command1);
					if(!rs.next()){
						Looper.prepare(); 
						progressDialog.dismiss();
						progressDialog = new Dialog(Firstpage.this,R.style.progress_dialog);  
		            	progressDialog.setContentView(R.layout.dialog2);  
		            	progressDialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);  
		            	TextView msg = (TextView) progressDialog.findViewById(R.id.id_tv_loadingmsg);  
		            	msg.setText("用户名错误");  
		            	progressDialog.show(); 
		            	Looper.loop(); 
		            	
					}else{
						
						String codec = rs.getString("users_password");
						String ins = rs.getString("users_insframework");
						String instype = rs.getString("ftype");
						if(code.equals(codec)){
							
							progressDialog.dismiss();
							
							Intent intent1=new Intent(Firstpage.this,MainActivity.class);
							intent1.putExtra("ins", ins);
							intent1.putExtra("instype", instype);
							intent1.putExtra("ip", ip);
							intent1.putExtra("base", base);
							intent1.putExtra("name", name);
							intent1.putExtra("ser", ser);
							startActivity(intent1);
							
						}else{
							Looper.prepare();
							progressDialog.dismiss();
							progressDialog = new Dialog(Firstpage.this,R.style.progress_dialog);  
			            	progressDialog.setContentView(R.layout.dialog2);  
			            	progressDialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);  
			            	TextView msg = (TextView) progressDialog.findViewById(R.id.id_tv_loadingmsg);  
			            	msg.setText("密码错误");  
			            	progressDialog.show(); 
			            	Looper.loop();
			            	
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
    	}
	};
	
	@Override  
	public boolean onKeyDown(int keyCode, KeyEvent event) {  
	    // TODO Auto-generated method stub  
	    if(keyCode == KeyEvent.KEYCODE_BACK)  
	       {    
	           exitBy2Click();      //调用双击退出函数
	       }  
	    return false;  
	}  
	/** 
	 * ˫���˳����� 
	 */  
	private static Boolean isExit = false;  
	  
	private void exitBy2Click() {  
	    Timer tExit = null;  
	    if (isExit == false) {  
	        isExit = true; // 准备退出  
	        tExit = new Timer();  
	        tExit.schedule(new TimerTask() {  
	            @Override  
	            public void run() {  
	                isExit = false; // 取消退出 
	            }  
	        }, 20); // 如果2秒钟内没有按下返回键，则启动定时器取消掉刚才执行的任务
	  
	    } else {  
	        finish();  
	        System.exit(0);  
	    }  
	}  


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
			
			//多输入框
			LayoutInflater factory = LayoutInflater.from(this);
			final View textEntryView = factory.inflate(R.layout.dialogset, null);
			
			final EditText inputip = (EditText) textEntryView.findViewById(R.id.editTextIP); 
			final EditText inputbase = (EditText) textEntryView.findViewById(R.id.editTextBASE); 
			final EditText inputname = (EditText) textEntryView.findViewById(R.id.editTextNAME); 
			final EditText inputser = (EditText) textEntryView.findViewById(R.id.editTextSER); 
			
			try {  
            	String releasepath = getApplicationContext().getFilesDir().getAbsolutePath()+"/ipconfig.txt";
            	FileInputStream inputStream = new FileInputStream(releasepath);
            	byte[] bytes = new byte[100];  
                ByteArrayOutputStream arrayOutputStream = new ByteArrayOutputStream();  
                while (inputStream.read(bytes) != -1) {  
                    arrayOutputStream.write(bytes, 0, bytes.length);  
                } 
                inputStream.close();  
                arrayOutputStream.close(); 
                
                config = new String(bytes,"UTF-8");
                
                String[] configbuf = config.split(",");
                ip = configbuf[0];
                base = configbuf[1];
                name = configbuf[2];
                ser = configbuf[3];
                
            } catch (IOException e) {  
                e.printStackTrace();  
            }
			
			inputip.setText(ip);
			inputbase.setText(base);
			inputname.setText(name);
			
            AlertDialog.Builder builder = new AlertDialog.Builder(Firstpage.this);  
            builder.setTitle("参数配置").setView(textEntryView)  
                    .setNegativeButton("取消", null);  
            builder.setPositiveButton("确定", new DialogInterface.OnClickListener() {  
  
				public void onClick(DialogInterface dialog, int which) {  
					String ip = inputip.getText().toString(); 
					String base = inputbase.getText().toString();
					String name = inputname.getText().toString();
					String ser = inputser.getText().toString();
					ip = ip + "," + base + "," + name + "," + ser + ",";
                	try {
                		String releasepath = getApplicationContext().getFilesDir().getAbsolutePath()+"/ipconfig.txt";
                		FileOutputStream outputStream = new FileOutputStream(releasepath);
                		outputStream.write(ip.getBytes());  
                        outputStream.flush();  
                        outputStream.close();  
                		
						/*FileOutputStream outStream = new FileOutputStream(file,true);
						FileOutputStream outStream = getResources().getAssets().openFd("IPconfig.txt").createOutputStream();
						outStream.write(ipconfig.getBytes());
						outStream.flush();
			            outStream.close();*/
					} catch (FileNotFoundException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
                 }  
            });  
            builder.show();  
		}
		return super.onOptionsItemSelected(item);
	}
}
