package com.king.demo.jdjr;

import android.view.View;

public class BottomViewBean {
	private View contentView;
	private String title;

	public BottomViewBean(View view, String title) {
		this.contentView = view;
		this.title = title;
	}
	
	public View getContentView() {
		return contentView;
	}

	public void setContentView(View contentView) {
		this.contentView = contentView;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

}
