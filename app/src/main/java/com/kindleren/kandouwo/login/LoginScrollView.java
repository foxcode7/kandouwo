package com.kindleren.kandouwo.login;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.ScrollView;

/**
 * Created by foxcoder on 14-9-23.
 */
public class LoginScrollView extends ScrollView {
    private OnSizeChangedListener listener;
    private int mMaxHight;

    public LoginScrollView(Context context) {
        super(context);
    }

    public LoginScrollView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public LoginScrollView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }

    public void setListener(OnSizeChangedListener listener) {
        this.listener = listener;
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        mMaxHight = mMaxHight > h ? mMaxHight : h;
        mMaxHight = mMaxHight > oldh ? mMaxHight : oldh;
        if (listener != null) {
            listener.onSizeChanged(h, oldh, mMaxHight);
        }
    }

    public interface OnSizeChangedListener {

        public void onSizeChanged(int h, int oldh, int maxh);
    }
}
