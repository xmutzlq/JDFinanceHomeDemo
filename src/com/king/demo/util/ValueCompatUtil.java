package com.king.demo.util;

import android.os.Build;

public final class ValueCompatUtil {
	public static int getViewPagerPageMarginValue(int marginValue, int cardViewElevation) {
		if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
			return marginValue;
		} 
		return (marginValue - cardViewElevation) / 2;
	}
}
