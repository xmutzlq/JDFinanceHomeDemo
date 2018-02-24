package com.king.demo.jdjr.banner.recycle;

import com.king.demo.jdjr.banner.AutoScrollViewPager;

import android.content.Context;
import android.util.AttributeSet;

public class RecycleAutoScrollViewPager extends AutoScrollViewPager{

	public RecycleAutoScrollViewPager(Context paramContext) {
		super(paramContext);
	}
	
	public RecycleAutoScrollViewPager(Context paramContext, AttributeSet paramAttributeSet) {
		super(paramContext, paramAttributeSet);
	}

	@Override
	protected boolean isNotScrollRecycle() {
		return false;
	}
}
