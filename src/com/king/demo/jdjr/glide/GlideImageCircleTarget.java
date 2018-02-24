package com.king.demo.jdjr.glide;

import java.lang.ref.WeakReference;

import com.bumptech.glide.request.target.BitmapImageViewTarget;
import com.king.demo.MyApplication;

import android.graphics.Bitmap;
import android.support.v4.graphics.drawable.RoundedBitmapDrawable;
import android.support.v4.graphics.drawable.RoundedBitmapDrawableFactory;
import android.widget.ImageView;

public class GlideImageCircleTarget extends BitmapImageViewTarget {

	private WeakReference<ImageView> iv;
	
	public GlideImageCircleTarget(ImageView view) {
		super(view);
		iv = new WeakReference<ImageView>(view);
	}

	@Override
	protected void setResource(Bitmap resource) {
		if (iv != null && iv.get() != null) {
			RoundedBitmapDrawable circularBitmapDrawable = RoundedBitmapDrawableFactory
					.create(MyApplication.getInstance().getResources(), resource);
			circularBitmapDrawable.setCircular(true);
			circularBitmapDrawable.setAntiAlias(true);
			iv.get().setImageDrawable(circularBitmapDrawable);
		}
	}
}
