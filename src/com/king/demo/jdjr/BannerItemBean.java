package com.king.demo.jdjr;

public class BannerItemBean {
	Object img_path;
	String title;

	public BannerItemBean(Object img_path, String title) {
		this.img_path = img_path;
		this.title = title;
	}

	public BannerItemBean(Object img_path) {
		this.img_path = img_path;
	}

	public BannerItemBean() {
	}

	public Object getImg_path() {
		return img_path;
	}

	public void setImg_path(Object img_path) {
		this.img_path = img_path;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}
}
