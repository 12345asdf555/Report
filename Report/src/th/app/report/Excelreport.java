package th.app.report;

import android.app.ActionBar;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.graphics.Color;
import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.HorizontalScrollView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.jeremyfeinstein.slidingmenu.lib.SlidingMenu;


public class Excelreport extends Activity {

    private ListView mListView;

    public HorizontalScrollView mTouchView;
    //装入所有的HScrollView
    protected List<CHTableScrollView> mHScrollViews = new ArrayList<CHTableScrollView>();
    HashMap<String, TextView> mColumnControls = new HashMap<String, TextView>();

	private LayoutInflater inflater;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //this.requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_htable);
        inflater=getLayoutInflater();
        initViews(inflater);
        
        ActionBar actionBar = getActionBar();
		actionBar.setDisplayHomeAsUpEnabled(true);
        
      //设置左侧菜单
      		SlidingMenu slidingMenu;
      		Button button1,button2,button3;
      		slidingMenu = new SlidingMenu(this);
      		slidingMenu.setMode(SlidingMenu.LEFT);  //菜单从左边滑出
      		slidingMenu.setBehindWidth(600);        //菜单的宽度
      		slidingMenu.setTouchModeAbove(SlidingMenu.TOUCHMODE_FULLSCREEN);//菜单全屏都可滑出
      		slidingMenu.attachToActivity(this, SlidingMenu.SLIDING_CONTENT);
      		slidingMenu.setMenu(R.layout.menu_layout);
      		
      		button2=(Button)findViewById(R.id.btweldtop);
      		button3=(Button)findViewById(R.id.btreturn);
      		button2.setOnClickListener(new View.OnClickListener() {
                  @Override
                  public void onClick(View v) {
                      // TODO Auto-generated method stub
                  	 
                  }
              });
      		button3.setOnClickListener(new View.OnClickListener() {
                  @Override
                  public void onClick(View v) {
                      // TODO Auto-generated method stub
                  	Intent intent1=new Intent(Excelreport.this,MainActivity.class);
                      startActivity(intent1);
                  }
              });
        
        Button buttonup = (Button) findViewById(R.id.buttonup);
        buttonup.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent intent1=new Intent(Excelreport.this,ComboLineColumnChartActivity.class);
                startActivity(intent1);
                overridePendingTransition(R.anim.in_from_up, R.anim.out_to_down);
			}
		});

    }

    public boolean onOptionsItemSelected(MenuItem item){
    	if(item.getItemId() == android.R.id.home){
    		Intent intent=new Intent(this,MainActivity.class);
    		startActivity(intent);
    	    finish();
    	}
		return true;
    }
    
    
    @Override
    protected void onResume() {
        if (getRequestedOrientation() != ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE) {
            setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
        }
        super.onResume();
    }

    //网络请求
    private void requestNetData(){

    }

    private void initViews(LayoutInflater inflater) {

        //Column
        String[] cols = {
                "焊机/时间","HJ1", "HJ2", "HJ3", "HJ4",
                "HJ5", "HJ6", "HJ7",
                "HJ8", "HJ9","HJ10" ,};
        String[] name = {"非常短", "短", "一般", "快", "非常快",};

        //Table Title
        LinearLayout titleLinearLayout = (LinearLayout) this.findViewById(R.id.scrollLinearLayout);
        for (int i = 0; i < cols.length; i++) {
            if (i != 0) {
                View linearLay = newView(R.layout.row_title_edit_view, cols[i]);
                TextView et = (TextView) linearLay.findViewById(R.id.tevEditView);
                et.setText(cols[i]);//设置每一列顶表格数据

                titleLinearLayout.addView(linearLay);
            }
        }

        List<Map<String, String>> datas = new ArrayList<Map<String, String>>();
        Map<String, String> data = null;
        CHTableScrollView headerScroll = (CHTableScrollView) findViewById(R.id.item_scroll_title);
        //添加头滑动事件
        mHScrollViews.add(headerScroll);
        mListView = (ListView) findViewById(R.id.scroll_list);
        mListView.setDividerHeight(0);//设置分割线高度
        for (int i = 0; i < name.length; i++) {
            data = new HashMap<String, String>();


            data.put(cols[0], name[i]);//设置每一行头表格数据
            data.put(cols[1], 1 + "_" + i);
            data.put(cols[2], 2 + "_" + i);
            data.put(cols[3], 3 + "_" + i);
            data.put(cols[4], 4 + "_" + i);
            data.put(cols[5], 5 + "_" + i);
            data.put(cols[6], 6 + "_" + i);
            data.put(cols[7], 7 + "_" + i);
            data.put(cols[8], 8 + "_" + i);
            data.put(cols[9], 9 + "_" + i);
            data.put(cols[10], 10 + "_" + i);

            datas.add(data);
        }


        mColumnControls.clear();
        for (int i = 0; i < cols.length; i++) {
            //预留第一列
           
                EditText etItem1 = new EditText(Excelreport.this);
                etItem1.setWidth(2);
                etItem1.setTextColor(Color.RED);
                etItem1.setGravity(Gravity.CENTER);
                //
                mColumnControls.put(cols[i], etItem1);
           
        }

        BaseAdapter adapter = new ScrollAdapter2(inflater, datas, R.layout.row_item_edit, cols);
        mListView.setAdapter(adapter);
    }

    public void addHViews(final CHTableScrollView hScrollView) {
        if (!mHScrollViews.isEmpty()) {
            int size = mHScrollViews.size();
            CHTableScrollView scrollView = mHScrollViews.get(size - 1);
            final int scrollX = scrollView.getScrollX();
            //第一次满屏后，向下滑动，有一条数据在开始时未加入
            if (scrollX != 0) {
                mListView.post(new Runnable() {
                    @Override
                    public void run() {
                        //当listView刷新完成之后，把该条移动到最终位置
                        hScrollView.scrollTo(scrollX, 0);
                    }
                });
            }
        }
        mHScrollViews.add(hScrollView);
    }

    public void onScrollChanged(int l, int t, int oldl, int oldt) {
        for (CHTableScrollView scrollView : mHScrollViews) {
            //防止重复滑动
            if (mTouchView != scrollView)
                scrollView.smoothScrollTo(l, t);
        }
    }

    class ScrollAdapter2 extends BaseAdapter {
        private List<? extends Map<String, ?>> datas;
        private int res;
        private String[] from;
		private LayoutInflater inflater;

        public ScrollAdapter2(LayoutInflater inflater,
                              List<? extends Map<String, ?>> data, int resource,
                              String[] from) {
            this.inflater = inflater;
            this.datas = data;
            this.res = resource;
            this.from = from;
        }

        @Override
        public int getCount() {
            return datas.size();
        }

        @Override
        public Object getItem(int position) {
            return datas.get(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            View v = convertView;
            if (v == null) {
            	v = inflater.inflate(R.layout.row_item_edit, null);
                //第一次初始化的时候装进来

                mColumnControls.put("title", (TextView) v.findViewById(R.id.item_title));
                View chsv = v.findViewById(R.id.item_scroll);
                LinearLayout ll = (LinearLayout) chsv.findViewById(R.id.item_scroll_layout);
                View[] views = new View[from.length];

                for (int i = 0; i < from.length; i++) {
                    if (i == 0) {
                        views[0] = v.findViewById(R.id.item_title);
                        continue;
                    }

                    View linearLay = newView(R.layout.row_item_edit_view, from[i]);
                    TextView td = (TextView) linearLay.findViewById(R.id.ievEditView);

                    td.setOnClickListener(clickListener);
                    ll.addView(linearLay);

                    views[i] = td;
                }
                //
                v.setTag(views);

                addHViews((CHTableScrollView) chsv);
            }

            View[] holders = (View[]) v.getTag();
            int len = holders.length;
            for (int i = 0; i < len; i++) {
                ((TextView) holders[i]).setText(this.datas.get(position).get(from[i]).toString());
            }

            return v;
        }
    }

    private View newView(int res_id, String tag_name) {

        View itemView = LayoutInflater.from(Excelreport.this).inflate(res_id, null);
        itemView.setTag(tag_name);

        return itemView;
    }

    class ScrollAdapter extends SimpleAdapter {

        private List<? extends Map<String, ?>> datas;
        private int res;
        private String[] from;
        private int[] to;
        private Context context;

        public ScrollAdapter(Context context,
                             List<? extends Map<String, ?>> data, int resource,
                             String[] from, int[] to) {
            super(context, data, resource, from, to);
            this.context = context;
            this.datas = data;
            this.res = resource;
            this.from = from;
            this.to = to;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            View v = convertView;
            if (v == null) {
                v = LayoutInflater.from(context).inflate(res, null);
                //第一次初始化的时候装进来
                addHViews((CHTableScrollView) v.findViewById(R.id.item_scroll));
                View[] views = new View[to.length];
                //
                for (int i = 0; i < to.length; i++) {
                    View tv = v.findViewById(to[i]);
                    tv.setOnClickListener(clickListener);
                    views[i] = tv;
                }
                //
                v.setTag(views);
            }
            View[] holders = (View[]) v.getTag();
            int len = holders.length;
            for (int i = 0; i < len; i++) {
                ((EditText) holders[i]).setText(this.datas.get(position).get(from[i]).toString());
            }
            return v;
        }
    }

    //测试点击的事件
    protected View.OnClickListener clickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            Toast.makeText(Excelreport.this, ((TextView) v).getText(), Toast.LENGTH_SHORT).show();
        }
    };
}
