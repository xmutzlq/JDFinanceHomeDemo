package com.king.demo.jdjr;

import java.util.List;

import android.support.v4.view.PagerAdapter;
import android.view.View;
import android.view.ViewGroup;

public class MyBottomPagerAdapter extends PagerAdapter {
	private List<BottomViewBean> mViews;
	
	public MyBottomPagerAdapter(List<BottomViewBean> mViews) {
		this.mViews = mViews;
	}

	@Override
	public boolean isViewFromObject(View arg0, Object arg1) {
		return arg0 == arg1;
	}

	@Override
	public CharSequence getPageTitle(int position) {
		if(mViews != null && mViews.size() > 0) {
			return mViews.get(position).getTitle();
		}
		return super.getPageTitle(position);
	}
	
	@Override
	public int getCount() {
		return mViews.size();
	}

	@Override
	public void destroyItem(ViewGroup container, int position, Object object) {
		container.removeView(mViews.get(position).getContentView());
	}

	@Override
	public Object instantiateItem(ViewGroup container, int position) {
		container.addView(mViews.get(position).getContentView());
		return mViews.get(position).getContentView();
	}
}
