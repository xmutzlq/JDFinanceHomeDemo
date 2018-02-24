package com.king.demo.util;

import com.king.demo.MyApplication;

import android.content.res.Resources;
import android.util.DisplayMetrics;

public final class DeviceUtil {
	public static DisplayMetrics getScreenParams() {
		Resources resources = MyApplication.getInstance().getResources();
		DisplayMetrics dm = resources.getDisplayMetrics();
		return dm;
	}
}
