package com.king.demo;

import com.king.demo.util.ViewTreeUtil;

import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.EditText;

public class MainActivity extends Activity implements OnClickListener, SlideRulerDataInterface, ViewTreeUtil.IViewTreeDispatch {
	private static final int MaxValue = 10000000;
	
	private SlideRuler mRuler;
	private EditText mInputEt;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		
		ViewTreeUtil.setupUI(getView(R.id.root_view), this);
		getView(R.id.right_label_tv).setOnClickListener(this);
		
		mInputEt = getView(R.id.toubiao_avaliblemoney);
		mRuler = getView(R.id.slideruler);
		mRuler.setSlideRulerDataInterface(this);
		// 最小单位值
		mRuler.setMinUnitValue(100);
		// 最小当前值
		mRuler.setMinCurrentValue(100);
		// 最大值
		mRuler.setMaxValue(MaxValue);
		// 当前值
		mRuler.setCurrentValue(10000);
		
		new Handler(Looper.getMainLooper()).postDelayed(new Runnable() {
			
			@Override
			public void run() {
				mRuler.invalidate();
			}
		}, 300);
	}

	@Override
	public void getText(String data) {
		setInputValue(data);
	}

	@Override
	public void onViewTreeDispatch(View v) {
		//TODO close something
		mInputEt.clearFocus();
		ViewTreeUtil.closeSoftKeyboard(v);
	}
	
	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.right_label_tv:
			setInputValue(String.valueOf(MaxValue));
			mRuler.setCurrentValue(MaxValue);
			mRuler.invalidate();
			break;
		default:
			break;
		}
	}
	
	private void setInputValue(String value) {
		mInputEt.setText(value);
		mInputEt.setSelection(mInputEt.length());
	}
	
	@SuppressWarnings("unchecked")
	private final <E extends View> E getView(int id) {
		try {
			return (E) findViewById(id);
		} catch (ClassCastException ex) {
			throw ex;
		}
	}
}
