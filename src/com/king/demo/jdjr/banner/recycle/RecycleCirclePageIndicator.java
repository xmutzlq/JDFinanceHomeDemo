package com.king.demo.jdjr.banner.recycle;

import com.king.demo.jdjr.banner.CirclePageIndicator;

import android.content.Context;
import android.util.AttributeSet;

public class RecycleCirclePageIndicator extends CirclePageIndicator{

	public RecycleCirclePageIndicator(Context context) {
		super(context);
	}
	
	public RecycleCirclePageIndicator(Context context, AttributeSet attrs) {
		super(context, attrs);
	}

	@Override
	protected boolean isNotScrollRecycle() {
		return false;
	}
}
