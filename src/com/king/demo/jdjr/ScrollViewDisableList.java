package com.king.demo.jdjr;

import com.king.demo.R;

import android.content.Context;
import android.graphics.Rect;
import android.support.v4.view.MotionEventCompat;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.ViewConfiguration;

/**
 * @author zlq
 */
public class ScrollViewDisableList extends ObservableScrollView implements ObservableScrollViewCallbacks, OnPageChangeListener {

	private CardViewPager mChildViewPager;
	private ScrollViewPager mBottomViewPager;
	private boolean isCardViewOnTouch, canScrollvertically;
	private Rect mTouchRect = new Rect();
	private int mTouchSlop;
	private float mDownPosX, mDownPosY;
	private int mScrollY;
	
	public ScrollViewDisableList(Context context, AttributeSet attrs) {
		super(context, attrs);
		mTouchSlop = ViewConfiguration.get(context).getScaledTouchSlop();
		addScrollViewCallbacks(this);
	}

	@Override
	protected void onFinishInflate() {
		super.onFinishInflate();
		mChildViewPager = (CardViewPager) findViewById(R.id.cardViewPager);
		mBottomViewPager = (ScrollViewPager) findViewById(R.id.bottomViewPager);
		mBottomViewPager.addOnPageChangeListener(this);
	}

	@Override
	protected int computeScrollDeltaToGetChildRectOnScreen(Rect rect) {
		return 0;
	}

	@Override
	public boolean onInterceptTouchEvent(MotionEvent ev) {
		final int x = (int) ev.getX();
		final int y = (int) ev.getY();

		boolean isDown = ev.getAction() == MotionEvent.ACTION_DOWN;
		if (isDown) {
			mTouchRect.setEmpty();
			mChildViewPager.getHitRect(mTouchRect);
			mTouchRect.top -= getScrollY();
			mTouchRect.bottom -= getScrollY();
			// 判断触点是否落在CardView上
			if (mTouchRect.contains(x, y)) isCardViewOnTouch = true;
		}
		return isCardViewOnTouch ? isCardViewOnTouch : super.onInterceptTouchEvent(ev);
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
			if (!canScrollvertically && deltaY > mTouchSlop && deltaY > deltaX) { 
				// 判断是否已经上下滑动中
				if(!mChildViewPager.canScrollhorizon()) {
					canScrollvertically = true;
				}
			}
		}
		
		final int action = MotionEventCompat.getActionMasked(ev);  
        switch (action) {  
            case MotionEvent.ACTION_UP:  
            case MotionEvent.ACTION_CANCEL:  
            	canScrollvertically = false;
                break;  
        }  
		
		if (isCardViewOnTouch) {
			boolean detectedUp = ev.getAction() == MotionEvent.ACTION_UP;
			if (detectedUp) {
				isCardViewOnTouch = false;
			}
			
			// 如果不在上下滑动中，那么就可以左右滑动
			if(!canScrollvertically) {
				mChildViewPager.onTouchEvent(ev);
			}
		}
		
		//ScrollView是否消费掉touch事件
		return mChildViewPager.canScrollhorizon() ? mChildViewPager.canScrollhorizon() : super.onTouchEvent(ev);
	}

	@Override
	public void onScrollChanged(int scrollY, boolean firstScroll, boolean dragging) {
		mScrollY = scrollY;
	}

	@Override
	public void onDownMotionEvent() {
		
	}

	@Override
	public void onUpOrCancelMotionEvent(ScrollState scrollState) {
		
	}

	@Override
	public void onPageScrollStateChanged(int arg0) {
		
	}

	@Override
	public void onPageScrolled(int position, float arg1, int arg2) {
		paddingChild(mBottomViewPager.getCurrentItem(), mScrollY);
	}

	@Override
	public void onPageSelected(int position) {
		if(getScrollY() != 0) {
			smoothScrollTo(0, 0);
			for (int i = 0; i < mBottomViewPager.getChildCount(); i++) {
				mBottomViewPager.getChildAt(i).setPadding(0, 0, 0, 0);
			}
		}
	}
	
	private void paddingChild(int position, int top) {
		if(mBottomViewPager == null) return; 
		int rPos = position + 1;
		int lPos = position - 1;
		if(rPos < mBottomViewPager.getChildCount()) { // 右边
			mBottomViewPager.getChildAt(rPos).setPadding(0, top, 0, 0);
		}
		if(lPos >= 0) { // 左边
			mBottomViewPager.getChildAt(lPos).setPadding(0, top, 0, 0);
		}
	}
	
}
