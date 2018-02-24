package com.king.demo.util;

import android.os.Build;
import android.view.View;
import android.view.ViewTreeObserver;

public class ViewUtils {
	private ViewUtils() {}

    public static void removeGlobalLayoutListener(View view, ViewTreeObserver.OnGlobalLayoutListener onGlobalLayoutListener) {

        final ViewTreeObserver observer = view.getViewTreeObserver();
        if (!observer.isAlive()) {
            return;
        }

        if (Build.VERSION.SDK_INT >= 16) {
            observer.removeOnGlobalLayoutListener(onGlobalLayoutListener);
        } else {
            //noinspection deprecation
            observer.removeGlobalOnLayoutListener(onGlobalLayoutListener);
        }
    }
}
