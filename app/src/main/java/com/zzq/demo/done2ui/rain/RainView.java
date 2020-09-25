package com.zzq.demo.done2ui.rain;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.View;

/**
 * 雨滴视图, DELAY时间重绘, 绘制 NUM_RAINFLAKES个雨滴
 */
public class RainView extends View {

    private int mWidth;
    private int mHeight;

    private static final int NUM_RAINFLAKES = 20; // 雨滴数量
    private static final int DELAY = 5; // 延迟
    private RainFlake[] mRainFlakes; // 雨滴

    public RainView(Context context) {
        super(context);
    }

    public RainView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public RainView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        //确定View宽高
        mWidth = w;
        mHeight = h;
        if (w != oldw || h != oldh) {
            initRain(w, h);
        }
    }

    private void initRain(int width, int height) {
        Paint paint = new Paint(Paint.ANTI_ALIAS_FLAG); // 抗锯齿
        paint.setColor(Color.parseColor("#64E4F5")); // 雨滴的颜色
        paint.setStyle(Paint.Style.FILL); // 填充;
        mRainFlakes = new RainFlake[NUM_RAINFLAKES];
        //mRainFlakes所有的雨滴都生成放到这里面
        for (int i = 0; i < NUM_RAINFLAKES; ++i) {
            mRainFlakes[i] = RainFlake.create(width, height, paint);
        }
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        if (mRainFlakes != null) {
            //for返回RainFlake
            for (RainFlake s : mRainFlakes) {
                canvas.save();
                canvas.translate(mWidth >> 2, -(mHeight >> 2));
                //画布旋转
                canvas.rotate(30);
                //然后进行绘制
                s.draw(canvas);
                canvas.restore();
            }
            // 隔一段时间重绘一次, 动画效果
            getHandler().postDelayed(runnable, DELAY);
        }
    }

    // 重绘线程
    private Runnable runnable = new Runnable() {
        @Override
        public void run() {
            //自动刷新
            invalidate();
        }
    };
}