package com.king.demo.jdjr.banner;

import android.view.View;
import android.view.View.OnClickListener;

public class BannerClick implements OnClickListener {
	private final View.OnClickListener mListener;
	int id;
	int position;

	public BannerClick(View.OnClickListener mListener, int id, int position) {
		this.id = id;
		this.mListener = mListener;
		this.position = position;
	}

	@Override
	public void onClick(View v) {
		v.setId(id);
		v.setTag(position);
		mListener.onClick(v);
	}

}
