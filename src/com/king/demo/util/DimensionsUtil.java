package com.king.demo.util;

import com.king.demo.MyApplication;

import android.app.Activity;
import android.content.Context;
import android.content.res.Resources;
import android.util.DisplayMetrics;
import android.view.View;
import android.view.View.MeasureSpec;

/**
 * 尺寸计算工具类
 */
public class DimensionsUtil {
	private static Context getContext() {
		return MyApplication.getInstance();
	}

	private static Resources getResources() {
		return getContext().getResources();
	}

	public static float getDisplayMetric() {
		return getResources().getDisplayMetrics().density;
	}

	public static int DIPToPX(float dipValue) {
		final float scale = getResources().getDisplayMetrics().density;
		return (int) (dipValue * scale + 0.5f);
	}

	public static int PXToDIP(float pxValue) {
		final float scale = getResources().getDisplayMetrics().density;
		return (int) ((pxValue - 0.5f) / scale);
	}

	public static void measureView(View view) {
		final int widthMeasureSpec = MeasureSpec.makeMeasureSpec(0, MeasureSpec.UNSPECIFIED);
		final int heightMeasureSpec = MeasureSpec.makeMeasureSpec(0, MeasureSpec.UNSPECIFIED);
		view.measure(widthMeasureSpec, heightMeasureSpec);
	}

	// 将px值转换为dip或dp值，保证尺寸大小不变
	public static int px2dip(Context context, float pxValue) {
		final float scale = context.getResources().getDisplayMetrics().density;
		return (int) (pxValue / scale + 0.5f);
	}

	// 将dip或dp值转换为px值，保证尺寸大小不变
	public static int dip2px(Context context, float dipValue) {
		final float scale = context.getResources().getDisplayMetrics().density;
		return (int) (dipValue * scale + 0.5f);
	}

	// 将px值转换为sp值，保证文字大小不变
	public static int px2sp(Context context, float pxValue) {
		final float fontScale = context.getResources().getDisplayMetrics().scaledDensity;
		return (int) (pxValue / fontScale + 0.5f);
	}

	// 将sp值转换为px值，保证文字大小不变
	public static int sp2px(Context context, float spValue) {
		final float fontScale = context.getResources().getDisplayMetrics().scaledDensity;
		return (int) (spValue * fontScale + 0.5f);
	}

	// 屏幕宽度（像素）
	public static int getWindowWidth(Activity context) {
		DisplayMetrics metric = new DisplayMetrics();
		context.getWindowManager().getDefaultDisplay().getMetrics(metric);
		return metric.widthPixels;
	}

	// 屏幕高度（像素）
	public static int getWindowHeight(Activity context) {
		DisplayMetrics metric = new DisplayMetrics();
		context.getWindowManager().getDefaultDisplay().getMetrics(metric);
		return metric.heightPixels;
	}
	
	public static int getDimSize(int dimenId) {
		return getContext().getResources().getDimensionPixelSize(dimenId);
	}
}
