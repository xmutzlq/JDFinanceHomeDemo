package com.king.demo.jdjr;

import java.util.ArrayList;
import java.util.List;

import com.king.demo.R;
import com.king.demo.util.DimensionsUtil;
import com.king.demo.util.ValueCompatUtil;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;

public class CardViewPagerActivity extends Activity implements OnPageChangeListener {

	public final static int[] mData = { R.drawable.img0, R.drawable.img1, R.drawable.img2 };
	public final static int[] mData1 = { R.drawable.img3, R.drawable.img4, R.drawable.img5 };
	public final static int[] mData2 = { R.drawable.img6, R.drawable.img7, R.drawable.img8 };
	public final static int[] mData3 = { R.drawable.img9, R.drawable.img10, R.drawable.img11 };
	public final static int[] mBottomIcon = {R.drawable.ico_b6, R.drawable.ico_b2, R.drawable.ico_b3, 
			R.drawable.ico_b4, R.drawable.ico_b5, R.drawable.ico_b1, R.drawable.ico_b7};
	public static final String[] mBottomText = {"哈士奇", "贵宾犬", "吉娃娃", "秋田犬", "杜宾犬","蝴蝶犬", "博美犬"};
	
	private PagerSlidingTabStrip mScllorTabView;
	private ViewPager mViewPager;
	private ViewPager mBottomViewPager;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_card_view_pager);

		mScllorTabView = (PagerSlidingTabStrip) findViewById(R.id.tabs);
		
		mViewPager = ((ViewPager) findViewById(R.id.cardViewPager));
		mViewPager.setOffscreenPageLimit(3);
		mViewPager.addOnPageChangeListener(this);
		mViewPager.setPageMargin(ValueCompatUtil.getViewPagerPageMarginValue(
				DimensionsUtil.getDimSize(R.dimen.banner_margin_left_right), DimensionsUtil.getDimSize(R.dimen.card_view_elevation)) / 2); // 设置ViewPager之间的间距
		mViewPager.setPageTransformer(true, new AlphaPageTransformer());
		mViewPager.setAdapter(new MyPagerAdapter(getViewPagerData(), this));
		mViewPager.setCurrentItem(0);
		
		mScllorTabView.setViewPager(mViewPager);

		mBottomViewPager = ((ViewPager) findViewById(R.id.bottomViewPager));
		mBottomViewPager.setOffscreenPageLimit(3);
		mBottomViewPager.addOnPageChangeListener(pageListener);
		mBottomViewPager.setAdapter(new MyBottomPagerAdapter(buildContentView()));
		mBottomViewPager.setCurrentItem(0);
		mBottomViewPager.setPageTransformer(true, new AlphaPageTransformer());
	}
	
	private List<ViewPagerItemBean> getViewPagerData() {
		List<ViewPagerItemBean> pagerItemBeanList = new ArrayList<ViewPagerItemBean>(mData.length);
		for (int i = 0; i < mData.length; i++) {
			pagerItemBeanList.add(new ViewPagerItemBean(mData[i], "狗狗品种 - " + i));
		}
		return pagerItemBeanList;
	}
	
	private List<BottomViewBean> buildContentView() {
		List<BottomViewBean> pagerItemBeanList = new ArrayList<BottomViewBean>(mData.length);
		pagerItemBeanList.add(new BottomViewBean(new BottomViewKind1(this), BottomViewKind1.class.getSimpleName()));
		pagerItemBeanList.add(new BottomViewBean(new BottomViewKind2(this), BottomViewKind2.class.getSimpleName()));
		pagerItemBeanList.add(new BottomViewBean(new BottomViewKind3(this), BottomViewKind3.class.getSimpleName()));
		return pagerItemBeanList;
	}
	
	OnPageChangeListener pageListener = new OnPageChangeListener() {
		
		@Override
		public void onPageSelected(int position) {
			if(mViewPager != null) {
				mViewPager.setCurrentItem(position);
			}
		}
		
		@Override
		public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
			
		}
		
		@Override
		public void onPageScrollStateChanged(int state) {
			
		}
	};
	
	@Override
	public void onPageSelected(int position) {
		if(mBottomViewPager != null) {
			mBottomViewPager.setCurrentItem(position);
		}
	}
	
	@Override
	public void onPageScrollStateChanged(int state) {
		
	}


	@Override
	public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
	}
}
