package com.king.demo.jdjr.scrollable;

public interface OnFlingOverListener {
	/**
     * This method will be called, when ScrollableLayout completely collapses,
     * but initial fling event had big velocity. So this method will be called with
     * theoretical vertical scroll value to scroll the underlining child
     * @param y the final scroll y (theoretical) for underlining scrolling child
     * @param duration theoretical duration of the scroll event based on velocity value
     *                 of touch event
     */
    void onFlingOver(int y, long duration);
}
