package com.king.demo;

import com.king.demo.util.DimensionsUtil;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.support.v4.content.ContextCompat;
import android.util.AttributeSet;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

/**
 * 无法在ScrollView中使用，关系到SurfaceView平移问题，放弃~~~~~~~~~~~~~~~
 * @author zlq
 */
public class WaveBgSurfaceView extends SurfaceView implements SurfaceHolder.Callback {

	/** �?50帧刷新一次屏�? **/
	public static final int TIME_IN_FRAME = 16;

	private WaveViewBySinCos mWaveViewBySinCos1;
	private WaveViewBySinCos mWaveViewBySinCos2;

	private DrawThread thread;
	private SurfaceHolder surfaceHolder;

	private int clearColor = ContextCompat.getColor(MyApplication.getInstance(), R.color.color_ccec6e55);
	//获取控件宽高
    private int width;
    private int height;
	
	public WaveBgSurfaceView(Context context, AttributeSet attrs) {
		super(context, attrs);
		mWaveViewBySinCos1 = new WaveViewBySinCos(context, attrs);
		mWaveViewBySinCos2 = new WaveViewBySinCos(context, attrs);
		mWaveViewBySinCos2.setWaveAmplitude(DimensionsUtil.dip2px(getContext(), 12));
		mWaveViewBySinCos2.setWaveColor(ContextCompat.getColor(getContext(), R.color.color_aaec6e55));
		mWaveViewBySinCos2.setWaveFillType(WaveViewBySinCos.TOP);
		mWaveViewBySinCos2.setWaveSpeed(2);
		mWaveViewBySinCos2.setWaveStart(true);
		mWaveViewBySinCos2.setWaveStartPeriod(0.5f);
		mWaveViewBySinCos2.setWaveType(WaveViewBySinCos.COS);
		setFocusable(true);
		surfaceHolder = getHolder();
		surfaceHolder.addCallback(this);
		thread = new DrawThread();
	}

	@Override
	protected void onSizeChanged(int w, int h, int oldw, int oldh) {
		super.onSizeChanged(w, h, oldw, oldh);
	}

	@Override
	public boolean isHardwareAccelerated() {
		return true;
	}
	
	@Override
	public void surfaceCreated(SurfaceHolder holder) {
		width = getMeasuredWidth();
		height = getMeasuredHeight();
		mWaveViewBySinCos1.setWH(width, height);
		mWaveViewBySinCos1.onSizeChanged(0, 0, 0, 0);
		mWaveViewBySinCos2.setWH(width, height);
		mWaveViewBySinCos2.onSizeChanged(0, 0, 0, 0);
		if (thread == null) {
			thread = new DrawThread();
		}
		thread.start();
	}

	@Override
	public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {

	}

	@Override
	public void surfaceDestroyed(SurfaceHolder holder) {
		if (thread != null) {
			thread.flag = false;
			thread = null;
		}
	}

	class DrawThread extends Thread {
		boolean flag = true;// 线程标识

		@Override
		public void run() {
			super.run();
			while (flag) {
				long startTime = System.currentTimeMillis();
				Canvas canvas = null;
				try {
					synchronized (surfaceHolder) {
						canvas = surfaceHolder.lockCanvas();
						if(canvas != null) {
							canvas.drawColor(Color.WHITE);
							mWaveViewBySinCos1.drawSin(canvas, width, height);
							mWaveViewBySinCos2.drawCos(canvas, width, height);
						}
					}
					long endTime = System.currentTimeMillis();
					int diffTime = (int) (endTime - startTime);
					/** 确保每次更新时间�?50�? **/
					while (diffTime <= TIME_IN_FRAME) {
						diffTime = (int) (System.currentTimeMillis() - startTime);
						/** 线程等待 **/
						Thread.yield();
					}
				} catch (Exception e) {
					e.printStackTrace();
				} finally {
					if (canvas != null) {
						surfaceHolder.unlockCanvasAndPost(canvas);
					}
				}
			}
		}
	}

	public void stop() {
		if (thread != null) {
			thread.flag = false;
			thread = null;
		}
	}

	public void start() {
		if (thread == null) {
			thread = new DrawThread();
			thread.start();
		}
	}
}
