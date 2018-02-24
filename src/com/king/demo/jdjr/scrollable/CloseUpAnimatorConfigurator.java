package com.king.demo.jdjr.scrollable;

import android.animation.ValueAnimator;

public interface CloseUpAnimatorConfigurator {
	/**
     * Note that {@link android.animation.ValueAnimator#setDuration(long)} would erase current value set by {@link CloseUpIdleAnimationTime} if any present
     * @param animator current {@link android.animation.ValueAnimator} object to animate close-up animation of a {@link ScrollableLayout}
     */
    void configure(ValueAnimator animator);
}
