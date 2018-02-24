/*
 * Copyright 2014 trinea.cn All right reserved. This software is the confidential and proprietary information of
 * trinea.cn ("Confidential Information"). You shall not disclose such Confidential Information and shall use it only in
 * accordance with the terms of the license agreement you entered into with trinea.cn.
 */
package com.king.demo.jdjr.banner.recycle;

import java.util.ArrayList;
import java.util.List;

import com.bumptech.glide.Glide;
import com.king.demo.R;
import com.king.demo.jdjr.BannerItemBean;

import android.content.Context;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ImageView;

/**
 * AdvertImagePagerAdapter
 */
public class AdvertImagePagerAdapter extends RecyclingPagerAdapter {

	private Context context;
	private List<BannerItemBean> imageIdList;

	private int size;
	private boolean isInfiniteLoop;

	private IBannerClickListener mBannerClickListener;

	public AdvertImagePagerAdapter(Context context, ArrayList<BannerItemBean> imageIdList) {
		this.context = context;
		this.imageIdList = imageIdList;
		this.size = imageIdList.size();
		isInfiniteLoop = false;
	}

	public AdvertImagePagerAdapter setBannerClickListener(IBannerClickListener listener) {
		mBannerClickListener = listener;
		return this;
	}

	@Override
	public int getCount() {
		// Infinite loop
		return isInfiniteLoop ? Integer.MAX_VALUE : imageIdList.size();
	}

	/**
	 * get really position
	 * 
	 * @param position
	 * @return
	 */
	private int getPosition(int position) {
		return position % size;
	}

	@Override
	public View getView(final int position, View view, ViewGroup container) {
		ViewHolder holder;
		if (view == null) {
			holder = new ViewHolder();
			view = holder.imageView = new ImageView(context);
			holder.imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
			view.setTag(holder);
		} else {
			holder = (ViewHolder) view.getTag();
		}

		Glide.with(context).load(imageIdList.get(getPosition(position)).getImg_path()).dontAnimate()
				.skipMemoryCache(true).into(holder.imageView);

		holder.imageView.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				if (mBannerClickListener != null) {
					mBannerClickListener.onBannerClickListener(getPosition(position));
				}
			}
		});
		return view;
	}

	private static class ViewHolder {
		ImageView imageView;
	}

	/**
	 * @return the isInfiniteLoop
	 */
	public boolean isInfiniteLoop() {
		return isInfiniteLoop;
	}

	/**
	 * @param isInfiniteLoop
	 *            the isInfiniteLoop to set
	 */
	public AdvertImagePagerAdapter setInfiniteLoop(boolean isInfiniteLoop) {
		this.isInfiniteLoop = isInfiniteLoop;
		return this;
	}

	public static interface IBannerClickListener {
		public void onBannerClickListener(int position);
	}
}
