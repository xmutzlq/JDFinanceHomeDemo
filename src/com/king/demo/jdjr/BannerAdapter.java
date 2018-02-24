package com.king.demo.jdjr;

import java.util.List;

import com.bumptech.glide.Glide;
import com.king.demo.R;
import com.king.demo.jdjr.banner.recycle.RecyclingPagerAdapter;
import com.king.demo.jdjr.glide.GlideImageRoundTarget;
import com.king.demo.util.DimensionsUtil;

import android.content.Context;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ImageView;

public class BannerAdapter extends RecyclingPagerAdapter {

	private Context context;
	private List<BannerItemBean> imageIdList;

	private float bannerCornerRadius;
	private int size;
	private boolean isInfiniteLoop;

	private IBannerClickListener mBannerClickListener;

	public BannerAdapter(Context context, List<BannerItemBean> imageIdList) {
		this.context = context;
		this.imageIdList = imageIdList;
		this.size = imageIdList.size();
		isInfiniteLoop = false;
		bannerCornerRadius = DimensionsUtil.getDimSize(R.dimen.banner_height) / 2;
	}

	public BannerAdapter setBannerClickListener(IBannerClickListener listener) {
		mBannerClickListener = listener;
		return this;
	}

	public void refreshData(List<BannerItemBean> datas) {
		imageIdList = datas;
		notifyDataSetChanged();
	}
	
	@Override
	public int getItemPosition(Object object) {
		return POSITION_NONE;
	}

	@Override
	public int getCount() {
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
		final ViewHolder holder;
		if (view == null) {
			holder = new ViewHolder();
			view = holder.imageView = new ImageView(context);
			view.setTag(holder);
		} else {
			holder = (ViewHolder) view.getTag();
		}

		Glide.with(context).load(imageIdList.get(getPosition(position)).img_path).asBitmap().centerCrop()
				.into(new GlideImageRoundTarget(holder.imageView, bannerCornerRadius));

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
	public BannerAdapter setInfiniteLoop(boolean isInfiniteLoop) {
		this.isInfiniteLoop = isInfiniteLoop;
		return this;
	}

	public static interface IBannerClickListener {
		public void onBannerClickListener(int position);
	}
}
