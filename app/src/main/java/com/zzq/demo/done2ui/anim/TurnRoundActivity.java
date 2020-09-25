package com.zzq.demo.done2ui.anim;

import android.animation.Animator;
import android.os.Bundle;
import android.view.View;
import android.view.ViewPropertyAnimator;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;

import com.zzq.demo.R;


/*
动画：
⼀张图⽚的中⼼点匀速直线移动，移动轨迹在⼀个给定半径的圆内，圆⼼基于 图⽚初始的中⼼点。
每次当图⽚的中⼼点在到达圆形边缘的的时候，需要随机改变⽅向。
*/
public class TurnRoundActivity extends AppCompatActivity {

    private ImageView mIv;
    private ViewPropertyAnimator mAnimate;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_anim_turn_round);

        mIv = (ImageView) findViewById(R.id.iv);
        mIv.post(new Runnable() {
            @Override
            public void run() {
                computeAnimData(computeCircleData(mIv));
            }
        });
        mAnimate = mIv.animate();
    }

    static class CenterData {
        int centerX;
        int centerY;

        CenterData(int centerX, int centerY) {
            this.centerX = centerX;
            this.centerY = centerY;
        }
    }

    private CenterData computeCircleData(View view) {
        int top = view.getTop();
        int left = view.getLeft();
        int right = view.getRight();
        int bottom = view.getBottom();
        int viewWidth = right - left;
        int viewHeight = bottom - top;
        // 中心点坐标
        int centerX = left + viewWidth / 2;
        int centerY = top + viewHeight / 2;
        return new CenterData(centerX, centerY);
    }

    private void computeAnimData(final CenterData centerData) {
        int centerX = centerData.centerX;
        int centerY = centerData.centerY;
        int R = 50;
        int angle = (int) (Math.random() * 360);
        float x = (float) (centerX + R * Math.cos(angle * 3.14 / 180));
        float y = (float) (centerY + R * Math.sin(angle * 3.14 / 180));
        CenterData currentCenterData = computeCircleData(mIv);
        startAnim(centerData, x - currentCenterData.centerX, y - currentCenterData.centerY);
    }

    private void startAnim(final CenterData centerData, float x, float y) {
        mAnimate.translationX(x).translationY(y).setDuration(1000)
                .setListener(new Animator.AnimatorListener() {
                    @Override
                    public void onAnimationStart(Animator animation) {

                    }

                    @Override
                    public void onAnimationEnd(Animator animation) {
                        computeAnimData(centerData);
                    }

                    @Override
                    public void onAnimationCancel(Animator animation) {

                    }

                    @Override
                    public void onAnimationRepeat(Animator animation) {

                    }
                }).start();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (mAnimate != null) {
            mAnimate.cancel();
            mAnimate = null;
        }
    }

    /*
    在上面的动画中再增加⼀个条件：每次直线移动的距离不能⼩于某个值。
    这题的两种方案：
    一是通过随机生成的角度，计算view中心点到圆上的距离是否小于指定的值，如果是，就重新生成一个角度，再计算，一直到超过指定的值再开始动效；
    二是当计算的距离小于指定的值，允许view的中心点超过圆范围之外，以满足这个值，通过后期补值，让这个view的中心点基本围绕圆的范围运动，但是这个只是理论，实现比较难。
    （附上方案一的大概实现方式）
    */

    /*private ImageView mIv;
    private ViewPropertyAnimator mAnimate;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_anim_turn_round);

        mIv = (ImageView) findViewById(R.id.iv);
        mIv.post(new Runnable() {
            @Override
            public void run() {
                computeAnimData(computeCircleData(mIv));
            }
        });
        mAnimate = mIv.animate();
    }

    static class CenterData {
        int centerX;
        int centerY;

        CenterData(int centerX, int centerY) {
            this.centerX = centerX;
            this.centerY = centerY;
        }
    }

    private CenterData computeCircleData(View view) {
        int top = view.getTop();
        int left = view.getLeft();
        int right = view.getRight();
        int bottom = view.getBottom();
        int viewWidth = right - left;
        int viewHeight = bottom - top;
        // 中心点坐标
        int centerX = left + viewWidth / 2;
        int centerY = top + viewHeight / 2;
        return new CenterData(centerX, centerY);
    }

    private void computeAnimData(final CenterData centerData) {
        final int R = 50;
        final int DISTANCE = 30;

        // 此处为测试，实际开发要在退出时取消thread
        new Thread(new Runnable() {
            @Override
            public void run() {
                int distance = 0;
                do {
                    distance = computeDistance(centerData, R, DISTANCE);
                } while (distance < DISTANCE);
            }
        }).start();
    }

    private int computeDistance(final CenterData centerData, int r, int distance) {
        final int centerX = centerData.centerX;
        final int centerY = centerData.centerY;
        int angle = (int) (Math.random() * 360);
        final float x = (float) (centerX + r * Math.cos(angle * 3.14 / 180));
        final float y = (float) (centerY + r * Math.sin(angle * 3.14 / 180));
        final CenterData currentCenterData = computeCircleData(mIv);

        float temX = x - currentCenterData.centerX;
        float temY = y - currentCenterData.centerY;
        int dist = (int) Math.sqrt(temX * temX + temY * temY);
        if (dist >= distance) {
            this.runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    startAnim(centerData, x - currentCenterData.centerX, y - currentCenterData.centerY);
                }
            });
        }
        return dist;
    }

    private void startAnim(final CenterData centerData, float x, float y) {
        mAnimate.translationX(x).translationY(y).setDuration(1000)
                .setListener(new Animator.AnimatorListener() {
                    @Override
                    public void onAnimationStart(Animator animation) {

                    }

                    @Override
                    public void onAnimationEnd(Animator animation) {
                        computeAnimData(centerData);
                    }

                    @Override
                    public void onAnimationCancel(Animator animation) {

                    }

                    @Override
                    public void onAnimationRepeat(Animator animation) {

                    }
                }).start();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (mAnimate != null) {
            mAnimate.cancel();
            mAnimate = null;
        }
    }*/

}
