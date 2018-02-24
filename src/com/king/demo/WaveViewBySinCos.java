package com.king.demo;

import android.animation.ValueAnimator;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.LinearGradient;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.Shader;
import android.support.v4.content.ContextCompat;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.View;
import android.view.animation.LinearInterpolator;

/**
 * y=A*sin(ωx+φ)+k
 * <p>
 * A—振幅越大，波形在y轴上最大与最小值的差值越大 ω—角速度， 控制正弦周期(单位角度内震动的次数)
 * φ—初相，反映在坐标系上则为图像的左右移动。这里通过不断改变φ,达到波浪移动效果 k—偏距，反映在坐标系上则为图像的上移或下移。
 */
public class WaveViewBySinCos extends View {

	/**
	 * 调整周期
	 */
	private static final float WAVE_CYCLE = 3.5f;
	private static final int margin_bottom = 20;
	
	private Context mContext;
	/**
	 * 振幅
	 */
	private int A;
	/**
	 * 偏距
	 */
	private int K;

	/**
	 * 波形的颜�?
	 */
	private int waveColor = ContextCompat.getColor(MyApplication.getInstance(), R.color.color_FD644B);

	/**
	 * 初相
	 */
	private float φ;

	/**
	 * 波形移动的�?�度
	 */
	private float waveSpeed = 3f;

	/**
	 * 角�?�度
	 */
	private double ω;

	/**
	 * �?始位置相差多少个周期
	 */
	private double startPeriod;

	/**
	 * 是否直接�?启波�?
	 */
	private boolean waveStart;

	private Path path;
	private Paint paint;

	public static final int SIN = 0;
	public static final int COS = 1;

	private int waveType;

	public static final int TOP = 0;
	public static final int BOTTOM = 1;

	private int waveFillType;

	private ValueAnimator valueAnimator;

	private int width;
	private int height;

	public WaveViewBySinCos(Context context, AttributeSet attrs) {
		super(context, attrs);
		mContext = context;
		getAttr(attrs);
		K = A;
		// initAnimation();
	}

	private void getAttr(AttributeSet attrs) {
		TypedArray typedArray = mContext.obtainStyledAttributes(attrs, R.styleable.RadarWaveView);

		waveType = typedArray.getInt(R.styleable.RadarWaveView_waveType, SIN);
		waveFillType = typedArray.getInt(R.styleable.RadarWaveView_waveFillType, TOP);
		A = typedArray.getDimensionPixelOffset(R.styleable.RadarWaveView_waveAmplitude, dp2px(18));
		waveColor = typedArray.getColor(R.styleable.RadarWaveView_waveColor, waveColor);
		waveSpeed = typedArray.getFloat(R.styleable.RadarWaveView_waveSpeed, waveSpeed);
		startPeriod = typedArray.getFloat(R.styleable.RadarWaveView_waveStartPeriod, 1);
		waveStart = typedArray.getBoolean(R.styleable.RadarWaveView_waveStart, true);

		typedArray.recycle();
	}

	public void setWaveType(int waveType) {
		this.waveType = waveType;
	}

	public void setWaveFillType(int waveFillType) {
		this.waveFillType = waveFillType;
	}

	public void setWaveAmplitude(int waveAmplitude) {
		this.A = waveAmplitude;
	}

	public void setWaveColor(int color) {
		this.waveColor = color;
	}

	public void setWaveSpeed(float waveSpeed) {
		this.waveSpeed = waveSpeed;
	}

	public void setWaveStartPeriod(float waveStartPeriod) {
		this.startPeriod = waveStartPeriod;
	}

	public void setWaveStart(boolean waveStart) {
		this.waveStart = waveStart;
	}

	private void initPaint() {
		path = new Path();
		paint = new Paint(Paint.ANTI_ALIAS_FLAG);
		paint.setAntiAlias(true);
		paint.setStyle(Paint.Style.FILL_AND_STROKE);
		if(waveColor != -1) {
			paint.setColor(waveColor);
		}
	}

	@Override
	protected void onSizeChanged(int w, int h, int oldw, int oldh) {
		super.onSizeChanged(w, h, oldw, oldh);
		initPaint();
		if (width == 0) {
			width = getWidth();
		}
		if(height == 0) {
			height = getHeight();
		}
		ω = WAVE_CYCLE * Math.PI / width;
		if(waveFillType == BOTTOM) {
//			paint.setShadowLayer(5, 0, 0, ContextCompat.getColor(getContext(), R.color.color_88FD644B));
			Shader mShader = new LinearGradient(0, height / 3, 0, height, 
					new int[] {ContextCompat.getColor(getContext(), R.color.color_55FD644B), ContextCompat.getColor(getContext(), R.color.half_apha_white)}, 
					null, Shader.TileMode.MIRROR); //渐变效果
			paint.setShader(mShader);
		}
	}

	@Override
	protected void onDraw(Canvas canvas) {

		switch (waveType) {
		case SIN:
			drawSin(canvas, getWidth(), getHeight());
			break;
		case COS:
			drawCos(canvas, getWidth(), getHeight());
			break;
		}
	}

	public void setWH(int width, int height) {
		this.width = width;
		this.height = height;
	}

	/**
	 * 根据cos函数绘制波形
	 *
	 * @param canvas
	 */
	public void drawCos(Canvas canvas, int width, int height) {

		switch (waveFillType) {
		case TOP:
			fillTop(canvas, width, height);
			break;
		case BOTTOM:
			fillTopCross(canvas, width, height);
//			fillBottom(canvas);
			break;
		}
	}

	/**
	 * 根据sin函数绘制波形
	 *
	 * @param canvas
	 */
	public void drawSin(Canvas canvas, int width, int height) {

		switch (waveFillType) {
		case TOP:
			fillTop(canvas, width, height);
			break;
		case BOTTOM:
			fillTopCross(canvas, width, height);
//			fillBottom(canvas);
			break;
		}

	}

	/**
	 * 填充波浪上面部分
	 */
	private void fillTop(Canvas canvas) {
		fillTop(canvas, getWidth(), getHeight());
	}

	private void fillTop(Canvas canvas, int width, int height) {
		
		int newHeight = height - margin_bottom;
		
		φ -= waveSpeed / 100;
		float y = 0;

		path.reset();
		path.moveTo(0, newHeight);

		for (float x = 0; x <= width; x += 20) {
			y = (float) (A * Math.sin(ω * x + φ + Math.PI * startPeriod) + K);
			path.lineTo(x, newHeight - y);
		}

		path.lineTo(width, 0);
		path.lineTo(0, 0);
		path.close();

		canvas.drawPath(path, paint);
	}

	private void fillTopCross(Canvas canvas, int width, int height) {
		
		int newHeight = height;
		
		φ -= waveSpeed / 100;
		float y = 0;

		path.reset();
		path.moveTo(0, newHeight);

		for (float x = 0; x <= width; x += 20) {
			y = (float) (A * Math.sin(ω * x + φ + Math.PI * startPeriod) + K);
			path.lineTo(x, newHeight - margin_bottom - y);
		}

		path.lineTo(width, newHeight);
		path.lineTo(0, newHeight);
		path.close();

		canvas.drawPath(path, paint);
	}
	
	/**
	 * 填充波浪下面部分
	 */
	private void fillBottom(Canvas canvas) {

		φ -= waveSpeed / 100;
		float y;

		path.reset();
		path.moveTo(0, 0);

		for (float x = 0; x <= getWidth(); x += 20) {
			y = (float) (A * Math.sin(ω * x + φ + Math.PI * startPeriod) + K);
			path.lineTo(x, y);
		}

		// 填充矩形
		path.lineTo(getWidth(), getHeight());
		path.lineTo(0, getHeight());
		path.close();

		canvas.drawPath(path, paint);

	}

	private void initAnimation() {
		valueAnimator = ValueAnimator.ofInt(0, getWidth());
		valueAnimator.setDuration(1000);
		valueAnimator.setRepeatCount(ValueAnimator.INFINITE);
		valueAnimator.setInterpolator(new LinearInterpolator());
		valueAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
			@Override
			public void onAnimationUpdate(ValueAnimator animation) {
				/**
				 * 刷新页面调取onDraw方法，�?�过变更φ 达到移动效果
				 */
				invalidate();
			}
		});
		if (waveStart) {
			valueAnimator.start();
		}
	}

	public void startAnimation() {
		if (valueAnimator != null) {
			valueAnimator.start();
		}
	}

	public void stopAnimation() {
		if (valueAnimator != null) {
			valueAnimator.cancel();
		}
	}

	public void pauseAnimation() {
		if (valueAnimator != null) {
			valueAnimator.pause();
		}
	}

	public void resumeAnimation() {
		if (valueAnimator != null) {
			valueAnimator.resume();
		}
	}

	/**
	 * dp 2 px
	 *
	 * @param dpVal
	 */
	protected int dp2px(int dpVal) {
		return (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dpVal, getResources().getDisplayMetrics());
	}
}