package com.king.demo.jdjr;

import java.util.List;

import com.bumptech.glide.Glide;
import com.king.demo.R;
import com.king.demo.jdjr.glide.GlideImageRoundTarget;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

public class BottomAdapter extends BaseAdapter {
	
	private Context mContext;
	private List<BottomItemBean> bottomList;
	
	public BottomAdapter(Context context, List<BottomItemBean> list) {
		mContext = context;
		bottomList = list;
	}
	
	public void refreshData(List<BottomItemBean> list) {
		bottomList = list;
		notifyDataSetChanged();
	}
	
	@Override
	public int getCount() {
		return bottomList.size();
	}

	@Override
	public BottomItemBean getItem(int position) {
		return bottomList.get(position);
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		final ViewHolder holder;
		if(convertView == null) {
			holder = new ViewHolder();
			convertView = LayoutInflater.from(mContext).inflate(R.layout.item_bottom_lv, null);
			holder.icon = (ImageView) convertView.findViewById(R.id.bottom_left_icon_iv);
			holder.text = (TextView) convertView.findViewById(R.id.bottom_left_text_tv);
			convertView.setTag(holder);
		} else {
			holder = (ViewHolder) convertView.getTag();
		}
		
		BottomItemBean bean = bottomList.get(position);
		
		Glide.with(mContext).load(bean.getIconUrl()).asBitmap().centerCrop().override(60, 60)
			.into(new GlideImageRoundTarget(holder.icon, 5));
		
		holder.text.setText(bean.getText());
		
		return convertView;
	}
	
	public static class ViewHolder {
		public ImageView icon;
		public TextView text;
	}
}
