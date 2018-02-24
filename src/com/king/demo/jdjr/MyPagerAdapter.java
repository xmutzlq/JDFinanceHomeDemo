package com.king.demo.jdjr;

import android.content.Context;
import android.os.Build;
import android.support.v4.view.PagerAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

import com.bumptech.glide.Glide;
import com.king.demo.R;
import com.king.demo.jdjr.glide.GlideImageRoundTarget;
import com.king.demo.util.DimensionsUtil;

public class MyPagerAdapter extends PagerAdapter {

    private List<ViewPagerItemBean> mData;
    private Context mContext;
    private float cornerRadius;
    
    public MyPagerAdapter(List<ViewPagerItemBean> data, Context context) {
        mData = data;
        mContext = context;
        cornerRadius = DimensionsUtil.getDimSize(R.dimen.card_view_corner_radius);
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        View inflate = LayoutInflater.from(container.getContext()).inflate(R.layout.cardviewpager_item, container, false);
        ImageView imageView1 = (ImageView) inflate.findViewById(R.id.img_card_item);
        TextView textView = (TextView) inflate.findViewById(R.id.title_card_item);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
        	Glide.with(mContext).load(mData.get(position).getImg_url()).asBitmap().centerCrop()
    			.into(imageView1);
        } else {
        	Glide.with(mContext).load(mData.get(position).getImg_url()).asBitmap().centerCrop()
        		.into(new GlideImageRoundTarget(imageView1, cornerRadius));
        }
        
        textView.setText(mData.get(position).getTilte_text() + "");
        container.addView(inflate);
        return inflate;
    }

    @Override
	public CharSequence getPageTitle(int position) {
		if(mData != null && mData.size() > 0) {
			return mData.get(position).tilte_text;
		}
		return super.getPageTitle(position);
	}
    
    @Override
    public int getCount() {
        return mData.size();
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view == object;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        container.removeView(((View) object));
    }
}
