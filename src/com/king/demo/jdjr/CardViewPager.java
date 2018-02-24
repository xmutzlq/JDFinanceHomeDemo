package com.king.demo.jdjr;

import android.content.Context;
import android.support.v4.view.MotionEventCompat;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.ViewConfiguration;

/**
 * @author zlq
 */
public class CardViewPager extends ViewPager {
	
	private boolean canScrollhorizon;
	private int mTouchSlop;
	private float mDownPosX, mDownPosY;
	
	public CardViewPager(Context context) {
		super(context);
		mTouchSlop = ViewConfiguration.get(context).getScaledTouchSlop();
	}
	
	public CardViewPager(Context context, AttributeSet attrs) {
		super(context, attrs);
	}
	
	@Override
	protected void onFinishInflate() {
		super.onFinishInflate();
	}
	
	@Override
	public boolean onTouchEvent(MotionEvent ev) {
		final int x = (int) ev.getX();
		final int y = (int) ev.getY();
		
		boolean isDown = ev.getAction() == MotionEvent.ACTION_DOWN; 
		if(isDown) {
			mDownPosX = x;
			mDownPosY = y;
		}
		
		boolean isMove = ev.getAction() == MotionEvent.ACTION_MOVE;
		if(isMove) {
			final float deltaX = Math.abs(x - mDownPosX);
			final float deltaY = Math.abs(y - mDownPosY);
			// 判断是否左右滑动
			if (!canScrollhorizon && deltaX > mTouchSlop && deltaX > deltaY) {
				canScrollhorizon = true;
			}
		}
		
		final int action = MotionEventCompat.getActionMasked(ev);  
        switch (action) {  
            case MotionEvent.ACTION_UP:  
            case MotionEvent.ACTION_CANCEL:  
            	canScrollhorizon = false;
                break;  
        }  
        
		return super.onTouchEvent(ev);
	}
	
	public boolean canScrollhorizon() {
		return canScrollhorizon;
	}
}
