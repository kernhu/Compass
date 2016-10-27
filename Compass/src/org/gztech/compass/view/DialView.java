package org.gztech.compass.view;

import android.content.Context;
import android.graphics.Camera;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.Path;
import android.util.AttributeSet;
import android.view.View;

/**
 * @author 作者:
 * @date 2016-4-12 下午4:51:15
 * @version 1.0
 * @parameter
 * @since
 * @return
 */
public class DialView extends View {

	private Paint mPaint;
	private Camera mCamera;
	private Matrix mMatrix;
	private Path mPath;
	private float mCenterX;
	private float mCenterY;
	private float mRadius;
	private int mBgColor;

	public DialView(Context context) {
		super(context);
		// TODO Auto-generated constructor stub
	}

	public DialView(Context context, AttributeSet attrs) {
		super(context, attrs);
		// TODO Auto-generated constructor stub

		// 得到画笔
		mPaint = new Paint();
		// 抗锯齿
		mPaint.setAntiAlias(true);
		// 设置画笔颜色
		//Color.parseColor("227BAE");
		mPaint.setColor(Color.GREEN);

		//mPath = new Path();

		mCamera = new Camera();
		mMatrix = new Matrix();
	}

	public DialView(Context context, AttributeSet attrs, int defStyleAttr) {
		super(context, attrs, defStyleAttr);
		// TODO Auto-generated constructor stub
	}

	@Override
	protected void onDraw(Canvas canvas) {
		// TODO Auto-generated method stub
		super.onDraw(canvas);

		mCenterX = getWidth() / 2;
		mCenterY = getHeight() / 2;
		// 圆的半径取决于宽和高中最小的那个值
		mRadius = mCenterX > mCenterY ? mCenterY : mCenterX;

		// 设置画布的颜色
		canvas.drawColor(Color.TRANSPARENT);
		// 画个圆盘
		canvas.drawCircle(mCenterX, mCenterY, mRadius, mPaint);

		// 保存坐标系
		canvas.save();
		// 画个刻度盘
		mPaint.setColor(Color.YELLOW);
		for (int i = 0; i < 360; i++) {

			canvas.rotate(3, mCenterX, mCenterY);
			canvas.drawLine(mCenterX, 150, mCenterX, 170, mPaint);
		}
		// 恢复坐标系
		canvas.restore();
	}
}
