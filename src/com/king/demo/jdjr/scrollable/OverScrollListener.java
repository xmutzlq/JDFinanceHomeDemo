package com.king.demo.jdjr.scrollable;

public interface OverScrollListener {
	void onOverScrolled(ScrollableLayout layout, int overScrollY);
    boolean hasOverScroll(ScrollableLayout layout, int overScrollY);
    void onCancelled(ScrollableLayout layout);
    void clear();
}
