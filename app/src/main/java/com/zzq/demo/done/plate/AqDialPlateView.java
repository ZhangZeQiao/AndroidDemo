package com.zzq.demo.done.plate;

import android.animation.ValueAnimator;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.PaintFlagsDrawFilter;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.Rect;
import android.graphics.RectF;
import android.graphics.Shader;
import android.graphics.SweepGradient;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.View;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.view.animation.LinearInterpolator;

import androidx.annotation.DrawableRes;
import androidx.annotation.Nullable;

import com.zzq.demo.R;

public class AqDialPlateView extends View {

    private int mWidth;
    private int mHeight;
    private float mRadius;

    private int mDefaultColor;
    private int mDefaultSize;
    private PaintFlagsDrawFilter mPaintFlagsDrawFilter;

    private Paint mCalibrationPaint;
    private Paint mMiddlePaint;
    private Paint mGradientPaint;
    private RectF mArcRectF;

    private Paint mTextPaint;
    private String[] mAqInfo;
    private String[] mAqInfoNum;
    private float[][] mAqInfoPosition;
    private Paint mCenterTextPaint;
    private Rect mTextBounds;
    private Bitmap mBitmap;
    private Canvas mBitmapCanvas;
    private int mBitmapSize;
    private String mCurrentDes;

    private int mAnimatorValue;
    private float mCurrentAngle;
    private float mTotalAngle;
    private int mCurrentValue;
    private int mCurrentResId;

    public AqDialPlateView(Context context) {
        this(context, null);
    }

    public AqDialPlateView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public AqDialPlateView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init() {
        mDefaultColor = Color.parseColor("#80ffffff");
        //设置默认宽高值
        mDefaultSize = dp2px(300);
        //设置图片线条的抗锯齿
        mPaintFlagsDrawFilter = new PaintFlagsDrawFilter(0, Paint.ANTI_ALIAS_FLAG | Paint.FILTER_BITMAP_FLAG);

        //刻度画笔设置
        mCalibrationPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        mCalibrationPaint.setColor(mDefaultColor);
        mCalibrationPaint.setStrokeWidth(3);
        mCalibrationPaint.setStyle(Paint.Style.STROKE);
        //中间圆环画笔设置
        mMiddlePaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        mMiddlePaint.setStyle(Paint.Style.STROKE);
        mMiddlePaint.setStrokeCap(Paint.Cap.ROUND);
        mMiddlePaint.setStrokeWidth(6);
        mMiddlePaint.setColor(mDefaultColor);
        //最外层圆环渐变画笔设置
        mGradientPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        mGradientPaint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.SRC_ATOP));
        int[] colors = {Color.GREEN, Color.YELLOW, Color.RED};
        float[] positions = {0f, 0.35f, 0.93f};
        Shader shader = new SweepGradient(mWidth >> 1, mRadius, colors, positions);
        Matrix matrix = new Matrix();
        matrix.setRotate(-5, 0, 0);//加上旋转去掉最右边总有的一部分多余
        shader.setLocalMatrix(matrix);
        mGradientPaint.setShader(shader);
        mGradientPaint.setStrokeCap(Paint.Cap.ROUND);
        mGradientPaint.setStyle(Paint.Style.STROKE);

        //外层圆环文本画笔设置
        mAqInfo = new String[]{"严重", "健康", "优", "良", "轻度", "中度"};
        mAqInfoNum = new String[]{"300", "0", "50", "100", "150", "200"};
        mAqInfoPosition = new float[6][];
        mTextPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        mTextPaint.setColor(mDefaultColor);
        mTextPaint.setTextSize(dp2px(13));
        //中间文字画笔设置
        mCenterTextPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        mCenterTextPaint.setTextAlign(Paint.Align.CENTER);
        mTextBounds = new Rect();
        mCurrentDes = "空气优";
        //获取指针图片及宽高
        //mBitmap = BitmapFactory.decodeResource(getResources(), R.mipmap.ic_launcher); // 直接这样用在5.0以上会报错
        mBitmapSize = dp2px(20);
        mBitmap = Bitmap.createBitmap(mBitmapSize, mBitmapSize, Bitmap.Config.ARGB_4444);
        mBitmapCanvas = new Canvas(mBitmap);
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
        //确定View宽高
        mWidth = w;
        mHeight = h;
        //圆环半径
        mRadius = mWidth * 0.33f;
        //外层圆环
        float oval = mRadius - mGradientPaint.getStrokeWidth() * 0.5f;
        mArcRectF = new RectF(-oval, -oval, oval, oval);

        int x = mWidth >> 1;
        int y = mHeight >> 1;
        float radius = (mRadius + 30);
        for (int i = 0; i < 6; i++) {
            mAqInfoPosition[i] = new float[]{(float) (x + radius * Math.cos(Math.toRadians(60 * (i + 1)))), (float) (y + radius * Math.sin(Math.toRadians(60 * (i + 1))))};
        }
    }

    @Override
    protected void onDraw(Canvas canvas) {
        //设置画布绘图无锯齿
        canvas.setDrawFilter(mPaintFlagsDrawFilter);
        //绘制刻度线
        drawCalibration(canvas);
        //绘制圆弧
        drawArc(canvas);
        //绘制圆弧中心文字
        drawCenterText(canvas);
        //绘制圆弧上面文字
        drawText(canvas);
    }

    private void drawText(Canvas canvas) {
        for (int i = 0; i < 6; i++) {
            String infoNumText = mAqInfoNum[i];
            float x = mAqInfoPosition[i][0] - ((i > 0 && i < 4) ? mTextPaint.measureText(infoNumText) : 0);
            float y = mAqInfoPosition[i][1] - (i > 1 ? dp2px(10) : 0);
            canvas.drawText(
                    infoNumText,
                    x,
                    y,
                    mTextPaint);
            String infoText = mAqInfo[i];
            canvas.drawText(
                    infoText,
                    x + (mTextPaint.measureText(infoNumText) - mTextPaint.measureText(infoText)) / 2,
                    y + dp2px(16),
                    mTextPaint);
        }
    }

    private void drawCalibration(Canvas canvas) {
        for (int i = 0; i < 6; i++) {
            canvas.save();
            canvas.translate((mWidth >> 1) - mRadius, (mHeight >> 1) - mRadius);
            canvas.rotate(-(-60 * i), mRadius, mRadius);
            canvas.drawLine(2 * mRadius + 2, mRadius, 2 * mRadius - dp2px(8), mRadius, mCalibrationPaint);
            canvas.restore();
        }
    }

    private void drawArc(Canvas canvas) {
        canvas.save();
        canvas.translate(mWidth >> 1, mHeight >> 1);
        //画布旋转
        canvas.rotate(120);
        //绘制中间圆弧
        canvas.drawArc(mArcRectF, 0, 300, false, mMiddlePaint);
        //最外层的渐变圆环
        mGradientPaint.setStrokeWidth(15);
        canvas.drawArc(mArcRectF, 0, mCurrentAngle, false, mGradientPaint);
        //渐变圆环上的进度球
        if (mCurrentValue > 0 && mCurrentValue < 300) {
            mGradientPaint.setStrokeWidth(26);
            canvas.drawArc(mArcRectF, mCurrentAngle - 0.5f, -0.5f, false, mGradientPaint);
        }
        canvas.restore();
    }

    private void drawCenterText(Canvas canvas) {
        canvas.save();
        canvas.translate((mWidth >> 1) - mRadius, (mHeight >> 1) - mRadius);
        //绘制当前数据值
        mCenterTextPaint.setColor(Color.WHITE);
        mCenterTextPaint.setTextSize(dp2px(56));
        mCenterTextPaint.setStyle(Paint.Style.FILL);
        canvas.drawText(String.valueOf(mAnimatorValue), mRadius, mRadius, mCenterTextPaint);
        //绘制当前数据描述
        mCenterTextPaint.setColor(mDefaultColor);
        mCenterTextPaint.setTextSize(dp2px(16));
        canvas.drawText(mCurrentDes, mRadius + (mBitmapSize >> 1) + 5, mRadius + dp2px(30), mCenterTextPaint);
        //图片
        mCenterTextPaint.getTextBounds(mCurrentDes, 0, mCurrentDes.length(), mTextBounds);
        canvas.drawBitmap(mBitmap,
                mRadius - mCenterTextPaint.measureText(mCurrentDes) / 2 - (mBitmapSize >> 1),
                mRadius + dp2px(30) - (mBitmapSize >> 1) - (mTextBounds.height() >> 1) + 5,
                null);
        canvas.restore();
    }

    /**
     * 当前数据对应弧度旋转及当前数据自增动画
     */
    private void startRotateAnim() {
        ValueAnimator angleAnim = ValueAnimator.ofFloat(mCurrentAngle, mTotalAngle);
        angleAnim.setInterpolator(new AccelerateDecelerateInterpolator());
        angleAnim.setDuration(800);
        angleAnim.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator valueAnimator) {
                mCurrentAngle = (float) valueAnimator.getAnimatedValue();
                postInvalidate();
            }
        });
        angleAnim.start();

        ValueAnimator numAnim = ValueAnimator.ofInt(mAnimatorValue, mCurrentValue);
        numAnim.setDuration(800);
        numAnim.setInterpolator(new LinearInterpolator());
        numAnim.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator valueAnimator) {
                mAnimatorValue = (int) valueAnimator.getAnimatedValue();
                postInvalidate();
            }
        });
        numAnim.start();
    }

    /**
     * 设置数据
     */
    public void setValues(int values) {
        if (mCurrentValue == values) {
            return;
        }
        mCurrentValue = values;
        if (values <= 0) {
            mTotalAngle = 0f;
            mCurrentDes = "空气优";
            setDrawable(R.mipmap.ic_launcher);
        } else if (values <= 50) {
            mTotalAngle = values / 50f * 60 - 2;
            mCurrentDes = "空气优";
            setDrawable(R.mipmap.ic_launcher);
        } else if (values <= 100) {
            mCurrentDes = "空气良";
            mTotalAngle = values / 100f * 120 - 2;
            setDrawable(R.mipmap.ic_launcher);
        } else if (values <= 150) {
            mTotalAngle = values / 150f * 180 - 2;
            mCurrentDes = "轻度";
            setDrawable(R.mipmap.ic_launcher);
        } else if (values <= 200) {
            mTotalAngle = values / 200f * 240 - 2;
            mCurrentDes = "中度";
            setDrawable(R.mipmap.ic_launcher);
        } else {
            if (values >= 300) {
                mTotalAngle = 300;
            } else {
                mTotalAngle = 240 + (values - 200) / (float) 100 * 60 - 2;
            }
            mCurrentDes = "严重";
            setDrawable(R.mipmap.ic_launcher);
        }
        startRotateAnim();
    }

    private void setDrawable(@DrawableRes int id) {
        if (mCurrentResId == id) {
            return;
        }
        Drawable vectorDrawable = getResources().getDrawable(id);
        vectorDrawable.setBounds(0, 0, mBitmapCanvas.getWidth(), mBitmapCanvas.getHeight());
        vectorDrawable.draw(mBitmapCanvas);
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
        mBitmap.recycle();
        mBitmap = null;
        mBitmapCanvas = null;
    }
}
