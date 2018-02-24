package com.king.demo.jdjr.base;

import com.king.demo.R;
import com.king.demo.util.DimensionsUtil;

import android.content.Context;

public abstract class BaseBottomView extends BaseView {

	public BaseBottomView(Context context) {
		super(context);
		setPadding(getPaddingLeft(), 0, getPaddingRight(), getPaddingBottom());
	}

	@Override
	public void setPadding(int left, int top, int right, int bottom) {
		int bannerMargin = DimensionsUtil.getDimSize(R.dimen.banner_margin_top_bottom) * 2;
		int topPadding = DimensionsUtil.getDimSize(R.dimen.card_view_height);
		super.setPadding(left, topPadding + bannerMargin + top, right, bottom);
	}
}
