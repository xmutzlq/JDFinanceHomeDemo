package com.king.demo.jdjr.base;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.FrameLayout;

public abstract class BaseView extends FrameLayout implements IViewLef, IViewBindData {

	public BaseView(Context context) {
		super(context);
	}
	
	public BaseView(Context context, AttributeSet attrs) {
		super(context, attrs);
	}

	@Override
	protected void onAttachedToWindow() {
		super.onAttachedToWindow();
		removeAllViews();
		onCreate();
	}
	
	@Override
	protected void onDetachedFromWindow() {
		super.onDetachedFromWindow();
		removeAllViews();
		onDestory();
	}
}
