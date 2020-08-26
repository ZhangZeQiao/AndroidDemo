package com.zzq.demo;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.PaintFlagsDrawFilter;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.View;

import androidx.annotation.Nullable;

/*
 * 自定义倒计时效果View：圆形背景，圆环进度条，中间时间数
 * */
public class CountdownView extends View {

    private int mWidth, mHeight, mDefaultSize;
    private int mCircleColor, mAnnulusColor, mTextColor;
    private int mAnnulusStrokeWidth, mTextStrokeWidth;
    private String mTextCurrent;
    private int mAnnulusAngle, mTotalValue, mCurrentValue;
    private float mTextCurrentHalf;

    private RectF mArcRectF;
    private PaintFlagsDrawFilter mPaintFlagsDrawFilter;
    private Paint mPaint;

    public CountdownView(Context context) {
        this(context, null);
    }

    public CountdownView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public CountdownView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init() {
        //设置默认宽高值
        mDefaultSize = dp2px(300);
        mCircleColor = Color.parseColor("#bf000000");
        mAnnulusColor = Color.parseColor("#ffffff");
        mTextColor = Color.parseColor("#ffffff");
        mAnnulusStrokeWidth = dp2px(4);
        mAnnulusAngle = 0;
        mTextStrokeWidth = dp2px(26);
        mTotalValue = 10;
        mCurrentValue = 9;
        mTextCurrent = String.valueOf(mCurrentValue);

        mArcRectF = new RectF(0, 0, 0, 0);
        mPaintFlagsDrawFilter = new PaintFlagsDrawFilter(0, Paint.ANTI_ALIAS_FLAG | Paint.FILTER_BITMAP_FLAG);
        mPaint = new Paint();
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        setMeasuredDimension(remeasure(widthMeasureSpec, mDefaultSize), remeasure(heightMeasureSpec, mDefaultSize));
    }

    /**
     * 根据传入的值进行重新测量
     */
    public int remeasure(int measureSpec, int defaultSize) {
        int result;
        int specSize = MeasureSpec.getSize(measureSpec);
        switch (MeasureSpec.getMode(measureSpec)) {
            case MeasureSpec.UNSPECIFIED:
                //未指定
                result = defaultSize;
                break;
            case MeasureSpec.AT_MOST:
                //设置warp_content时设置默认值
                result = Math.min(specSize, defaultSize);
                break;
            case MeasureSpec.EXACTLY:
                //设置math_parent 和设置了固定宽高值
                result = specSize;
                break;
            default:
                result = defaultSize;
        }
        return result;
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        // 确定View宽高
        mWidth = w;
        mHeight = h;
        float annulusStrokeWidthHalf = (mAnnulusStrokeWidth >> 1);
        mArcRectF.set(
                0 + annulusStrokeWidthHalf,
                0 + annulusStrokeWidthHalf,
                mWidth - annulusStrokeWidthHalf,
                mHeight - annulusStrokeWidthHalf);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        // 设置图片线条的抗锯齿
        canvas.setDrawFilter(mPaintFlagsDrawFilter);

        // 绘制不透明度
        mPaint.setARGB(63, 0, 0, 0);
        // mPaint.setColor(mCircleColor);
        mPaint.setStyle(Paint.Style.FILL);
        canvas.drawCircle(getWidth() >> 1, getHeight() >> 1, getWidth() >> 1, mPaint);

        mPaint.setColor(mTextColor);
        mPaint.setTextSize(mTextStrokeWidth);
        mPaint.setStyle(Paint.Style.FILL);
        mTextCurrentHalf = mPaint.measureText(mTextCurrent) / 2;
        canvas.drawText(mTextCurrent, (getWidth() >> 1) - mTextCurrentHalf, (getHeight() >> 1) + mTextCurrentHalf, mPaint);

        mPaint.setColor(mAnnulusColor);
        mPaint.setStyle(Paint.Style.STROKE);
        mPaint.setStrokeCap(Paint.Cap.ROUND);
        mPaint.setStrokeWidth(mAnnulusStrokeWidth);
        canvas.drawArc(mArcRectF, 0, mAnnulusAngle, false, mPaint);
    }

    /**
     * 设置数据
     */
    public void setCountdownValues(int values) {
        if (values < 0) {
            return;
        }
        mCurrentValue = values;
        mTextCurrent = String.valueOf(mCurrentValue);
        mAnnulusAngle = (360 / mTotalValue) * (mTotalValue - mCurrentValue);
        invalidate();
    }

    private int dp2px(int dpVal) {
        return (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dpVal, getResources().getDisplayMetrics());
    }

    private int sp2px(int spVal) {
        return (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_SP, spVal, getResources().getDisplayMetrics());
    }

    @Override
    protected void onDetachedFromWindow() {
        super.onDetachedFromWindow();
    }
}
