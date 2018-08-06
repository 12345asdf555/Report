package th.app.report;

import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Timer;
import java.util.TimerTask;

import org.apache.commons.codec.binary.Hex;
import org.apache.commons.codec.digest.DigestUtils;
import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Looper;
import android.view.KeyEvent;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.TextView;

public class Firstpage2 extends Activity {
	
	private java.sql.Connection conn = null;
	private java.sql.Statement stmt =null;
	public Dialog progressDialog;
	public Button bt;
	public EditText ed1;
	public EditText ed2;
	public String user;
	public String code;
	public float fontScale;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		Intent t1 = new Intent(Firstpage2.this,Firstpage.class);
		startActivity(t1);

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
		        	conn = DriverManager.getConnection("jdbc:mysql://121.196.222.216:3306/XMWeld", "db_admin", "PIJXmcLRa0QgOw2c");
					stmt= conn.createStatement();
					
					user="'"+user+"'";
					String Command1="SELECT tb_users.users_login_name,tb_users.users_password,tb_users.users_insframework from tb_users where tb_users.users_login_name="+user;
					
					rs = stmt.executeQuery(Command1);
					if(!rs.next()){
						Looper.prepare(); 
						progressDialog.dismiss();
						progressDialog = new Dialog(Firstpage2.this,R.style.progress_dialog);  
		            	progressDialog.setContentView(R.layout.dialog2);  
		            	progressDialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);  
		            	TextView msg = (TextView) progressDialog.findViewById(R.id.id_tv_loadingmsg);  
		            	msg.setText("用户名错误");  
		            	progressDialog.show(); 
		            	Looper.loop(); 
		            	
					}else{
						
						String codec = rs.getString("users_password");
						String ins = rs.getString("users_insframework");
						if(code.equals(codec)){
							
							progressDialog.dismiss();
							
							Intent intent1=new Intent(Firstpage2.this,MainActivity.class);
							intent1.putExtra("ins", ins);
							startActivity(intent1);
							
						}else{
							Looper.prepare();
							progressDialog.dismiss();
							progressDialog = new Dialog(Firstpage2.this,R.style.progress_dialog);  
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
