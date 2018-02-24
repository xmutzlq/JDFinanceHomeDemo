package com.king.demo.util;

import android.content.Context;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnTouchListener;
import android.view.inputmethod.InputMethodManager;
import android.view.ViewGroup;
import android.widget.EditText;

public final class ViewTreeUtil {
	
	public static void setupUI(final View view) {
		setupUI(view, null);
	}
	
	public static void setupUI(final View view, final IViewTreeDispatch dispatch) {
        //如果不是EditText就关闭键盘
        if(!(view instanceof EditText)) {
            view.setOnTouchListener(new OnTouchListener() {
                public boolean onTouch(View v, MotionEvent event) {
                	if(dispatch != null) {
                		dispatch.onViewTreeDispatch(view);
                	} else {
                		closeSoftKeyboard(view);
                	}
                    return false;
                }
            });
        }

        // 递归判断
        if (view instanceof ViewGroup) {
            for (int i = 0; i < ((ViewGroup) view).getChildCount(); i++) {
                View innerView = ((ViewGroup) view).getChildAt(i);
                setupUI(innerView, dispatch);
            }
        }
    } 
	
	/**
	 * 隐藏软键盘
	 *
	 * @param v
	 */
	public static void closeSoftKeyboard(View v) {
		InputMethodManager imm = (InputMethodManager) v.getContext().getSystemService(Context.INPUT_METHOD_SERVICE);
		if (imm.isActive()) {
			imm.hideSoftInputFromWindow(v.getApplicationWindowToken(), 0);
		}
	}
	
	public static interface IViewTreeDispatch {
		public void onViewTreeDispatch(View v);
	}
}
