package com.king.demo.jdjr.scrollable;

public interface CloseUpIdleAnimationTime {
	/**
     * @param layout {@link ScrollableLayout}
     * @param nowY current scroll y of the *layout*
     * @param endY scroll y value to which *layout* would scroll to
     * @param maxY current max scroll y value of the *layout*
     * @return animation duration for a close-up animation
     */
    long compute(ScrollableLayout layout, int nowY, int endY, int maxY);
}
