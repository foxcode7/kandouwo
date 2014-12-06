package com.kindleren.kandouwo.base;

import android.content.Context;
import android.os.Handler;
import android.util.AttributeSet;
import android.widget.TextView;

/**
 * 实现点循环
 * 
 * @author luodandan
 * 
 */
public class PointsLoopView extends TextView {

    int stage = 0;
    private static int LOOP_TIME = 600;
    private String mText = "";
    private Handler mHandler;
    private Runnable mRunnable;

    public PointsLoopView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        init();
    }

    public PointsLoopView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public PointsLoopView(Context context) {
        super(context);
        init();
    }

    /**
     * 停止循环，不需要点循环的时候需要调用
     */
    public void stopLoop() {
        if (mHandler != null) {
            mHandler.removeCallbacks(mRunnable);
        }
    }

    /**
     * 开始循环
     */
    public void startLoop() {
        if (mHandler != null) {
            mText = getText().toString();
            mHandler.postDelayed(mRunnable, LOOP_TIME);
        }
    }

    public void restartLoop() {
        stopLoop();
        startLoop();
    }

    /**
     * 初始化一些东西
     */
    private void init() {
        mText = getText().toString();
        setWidth(getWidth() + 150); // 使宽度变宽一点，插入点的时候文本不会跳动
        mHandler = new Handler();
        mRunnable = new Runnable() {

            @Override
            public void run() {
                stage = stage % 4;
                next();
                stage++;
                if (mHandler != null) {
                    mHandler.postDelayed(mRunnable, LOOP_TIME);
                }
            }
        };
    }

    /**
     * 循环点的操作
     */
    private void next() {
        switch (stage) {
        case 0:
            setText(mText + ".");
            break;
        case 1:
            setText(mText + "..");
            break;
        case 2:
            setText(mText + "...");
            break;
        default:
            setText(mText);
            break;
        }
    }
}
