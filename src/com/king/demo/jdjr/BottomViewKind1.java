package com.king.demo.jdjr;

import java.util.ArrayList;
import java.util.List;

import com.king.demo.R;
import com.king.demo.jdjr.banner.AutoScrollViewPager;
import com.king.demo.jdjr.base.BaseBottomView;
import com.king.demo.jdjr.base.IViewStateChange;
import com.king.demo.util.DimensionsUtil;

import android.content.Context;
import android.view.LayoutInflater;

public class BottomViewKind1 extends BaseBottomView implements IViewStateChange {

	private AutoScrollViewPager mBannerViewPager;
	private BannerAdapter mBannerAdapter;

	private ScrollDisabledListView mListView;
	private BottomAdapter mBottomAdapter;

	public BottomViewKind1(Context context) {
		super(context);
	}

	@Override
	public void onCreate() {
		LayoutInflater.from(getContext()).inflate(R.layout.layout_bottom, this, true);
		initView();
	}

	private void initView() {
		List<BannerItemBean> bannerDatas = getData(CardViewPagerActivity.mData1);
		mBannerAdapter = new BannerAdapter(getContext(), bannerDatas);
		mBannerViewPager = ((AutoScrollViewPager) findViewById(R.id.bannerViewPager));
		mBannerViewPager.setPageMargin(DimensionsUtil.getDimSize(R.dimen.banner_margin_left_right) * 2); // 设置ViewPager之间的间距
		mBannerViewPager.setAdapter(mBannerAdapter.setInfiniteLoop(true));
		mBannerViewPager.setInterval(5000);
		mBannerViewPager.startAutoScroll();
		mBannerViewPager.setCurrentItem(bannerDatas.size() * 500);
		mBannerViewPager.setNoScroll(bannerDatas.size() == 1);
		mBannerViewPager.requestFocus();

		mListView = (ScrollDisabledListView) findViewById(R.id.bottom_lv);
		mBottomAdapter = new BottomAdapter(getContext(), getBottomData(0));
		mListView.setAdapter(mBottomAdapter);
		mBottomAdapter.notifyDataSetChanged();

	}

	@Override
	public void reLoadData() {
		loadData();
	}
	
	private void loadData() {
		mBannerAdapter.refreshData(getData(CardViewPagerActivity.mData1));
		mBottomAdapter.refreshData(getBottomData(0));
	}
	
	private List<BannerItemBean> getData(int[] datas) {
		List<BannerItemBean> pagerItemBeanList = new ArrayList<BannerItemBean>(datas.length);
		for (int i = 0; i < datas.length; i++) {
			pagerItemBeanList.add(new BannerItemBean(datas[i], "狗狗 + " + (i + 1)));
		}
		return pagerItemBeanList;
	}

	private List<BottomItemBean> getBottomData(int position) {
		int size = 7;
		List<BottomItemBean> pagerItemBeanList = new ArrayList<BottomItemBean>(size);
		for (int i = 0; i < size; i++) {
			BottomItemBean bean = new BottomItemBean();
			bean.setIconUrl(CardViewPagerActivity.mBottomIcon[i]);
			bean.setText(CardViewPagerActivity.mBottomText[i] + position);
			pagerItemBeanList.add(bean);
		}
		return pagerItemBeanList;
	}

	@Override
	public void onPagerResume() {
		if (mBannerViewPager != null) {
			mBannerViewPager.startAutoScroll();
		}
	}

	@Override
	public void onPagerPause() {
		if (mBannerViewPager != null) {
			mBannerViewPager.stopAutoScroll();
		}
	}
	
	@Override
	public void onDestory() {

	}
}
