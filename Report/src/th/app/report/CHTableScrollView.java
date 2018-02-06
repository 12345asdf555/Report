package th.app.report;

import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.widget.HorizontalScrollView;

public class CHTableScrollView extends HorizontalScrollView {
	
	Excelreport activity;
	
	public CHTableScrollView(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
		if (isInEditMode()) { return; }
		activity = (Excelreport) context;
	}

	
	public CHTableScrollView(Context context, AttributeSet attrs) {
		super(context, attrs);
		if (isInEditMode()) { return; }
		activity = (Excelreport) context;
	}

	public CHTableScrollView(Context context) {
		super(context);
		if (isInEditMode()) { return; }
		activity = (Excelreport) context;
	}
	
	@Override
	public boolean onTouchEvent(MotionEvent ev) {
		//���д�����ֵ
		activity.mTouchView = this;
		return super.onTouchEvent(ev);
	}
	
	@Override
	protected void onScrollChanged(int l, int t, int oldl, int oldt) {
		if (isInEditMode()) { return; }
		//����ǰ��CHSCrollView������ʱ����������
		if(activity.mTouchView == this) {
			activity.onScrollChanged(l, t, oldl, oldt);
		}else{
			super.onScrollChanged(l, t, oldl, oldt);
		}
	}
}
