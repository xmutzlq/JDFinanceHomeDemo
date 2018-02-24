package com.king.demo;

import com.king.demo.util.DimensionsUtil;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.LinearGradient;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.Shader;
import android.graphics.SurfaceTexture;
import android.support.v4.content.ContextCompat;
import android.util.AttributeSet;
import android.view.Surface;
import android.view.TextureView;

/**
 * @author zlq
 */
public class WaveBgTextureView extends TextureView implements TextureView.SurfaceTextureListener {

	/** 每N帧刷新一次屏�? **/
	public static final int TIME_IN_FRAME = 35;

	private WaveViewBySinCos mWaveViewBySinCos1;
	private WaveViewBySinCos mWaveViewBySinCos2;

	private Rect mSurfaceRect;  
	private Surface mDrawingSurface;
	private DrawThread thread;

	//获取控件宽高
    private int mDrawingWidth;
    private int mDrawingHeight;
    
    private Paint mCanvasPaint;
	
	public WaveBgTextureView(Context context, AttributeSet attrs) {
		super(context, attrs);
		mWaveViewBySinCos1 = new WaveViewBySinCos(context, attrs);
		mWaveViewBySinCos2 = new WaveViewBySinCos(context, attrs);
		mWaveViewBySinCos2.setWaveAmplitude(DimensionsUtil.dip2px(getContext(), 10));
		mWaveViewBySinCos2.setWaveColor(-1);
		mWaveViewBySinCos2.setWaveFillType(WaveViewBySinCos.BOTTOM);
		mWaveViewBySinCos2.setWaveSpeed(2f);
		mWaveViewBySinCos2.setWaveStart(true);
		mWaveViewBySinCos2.setWaveStartPeriod(1.5f);
		mWaveViewBySinCos2.setWaveType(WaveViewBySinCos.SIN);
		mCanvasPaint = new Paint();
		mCanvasPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
		mCanvasPaint.setAntiAlias(true);
		mCanvasPaint.setStyle(Paint.Style.FILL_AND_STROKE);
		setFocusable(true);
		setSurfaceTextureListener(this);
		thread = new DrawThread();
	}

	@Override
	public void onSurfaceTextureAvailable(SurfaceTexture surface, int width, int height) {
		Shader mShader = new LinearGradient(0, height - height / 3, 0, height, 
				new int[] {ContextCompat.getColor(getContext(), R.color.color_FD644B), Color.WHITE}, null, Shader.TileMode.CLAMP); //渐变效果
		mCanvasPaint.setShader(mShader);
		mDrawingSurface = new Surface(surface);
		updateSize(width, height);
		//创建完TextureView后立即刷新一帧防止黑屏出现
		Canvas canvas = mDrawingSurface.lockCanvas(mSurfaceRect);
		canvas.drawRect(mSurfaceRect, mCanvasPaint);
		mDrawingSurface.unlockCanvasAndPost(canvas);
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
	public void onSurfaceTextureSizeChanged(SurfaceTexture surface, int width, int height) {
		updateSize(width, height);
	}

	private void updateSize(int width, int height) {  
		mDrawingWidth = width;  
		mDrawingHeight = height;  
		if(mSurfaceRect == null) {
			mSurfaceRect = new Rect();
		}
        mSurfaceRect.set(0, 0, mDrawingWidth, mDrawingHeight);  
    } 
	
	@Override
	public boolean onSurfaceTextureDestroyed(SurfaceTexture surface) {
		stop();
		return true;
	}

	@Override
	public void onSurfaceTextureUpdated(SurfaceTexture surface) {
		
	}
	
	@Override
	protected void onDetachedFromWindow() {
		stop();
		super.onDetachedFromWindow();
	}
	
	class DrawThread extends Thread {
		boolean flag = true;// 线程标识
		boolean isPause = false; // 是否暂停 
		@Override
		public void run() {
			super.run();
			while (flag) {
				if(!isPause) {
					long startTime = System.currentTimeMillis();
					Canvas canvas = null;
					try {
						synchronized (mDrawingSurface) {
							canvas = mDrawingSurface.lockCanvas(mSurfaceRect);
							if(canvas != null) {
								canvas.drawRect(mSurfaceRect, mCanvasPaint);
								mWaveViewBySinCos1.drawSin(canvas, mDrawingWidth, mDrawingHeight);
								mWaveViewBySinCos2.drawSin(canvas, mDrawingWidth, mDrawingHeight);
							}
						}
						long endTime = System.currentTimeMillis();
						int diffTime = (int) (endTime - startTime);
						/** 确保每次更新时间为N�? **/
						while (diffTime <= TIME_IN_FRAME) {
							diffTime = (int) (System.currentTimeMillis() - startTime);
							/** 线程等待 **/
							Thread.yield();
						}
					} catch (Exception e) {
						e.printStackTrace();
					} finally {
						if (canvas != null) {
							if(mDrawingSurface != null && mDrawingSurface.isValid()) {
								mDrawingSurface.unlockCanvasAndPost(canvas);
							}
						}
					}
				}
			}
		}
	}

	public void pause() {
		thread.isPause = true;
	}
	
	public void resume() {
		thread.isPause = false;
	}
	
	private void stop() {
		if (thread != null) {
			thread.flag = false;
			thread = null;
		}
		if(mDrawingSurface != null && mDrawingSurface.isValid()) {
			mDrawingSurface.release();
			mDrawingSurface = null;
		}
	}
	
}
