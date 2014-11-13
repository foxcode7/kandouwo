package com.kindleren.kandouwo.common.views;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.ColorFilter;
import android.graphics.drawable.Drawable;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.widget.EditText;

import com.kindleren.kandouwo.R;

/**
 * 带清除按钮的EditText
 *
 * @author yinduo
 *         <p/>
 *         这个view继承自EditText
 *         使用了setCompoundDrawables、setOnTouchListener、addTextChangedListener
 *         、setOnFocusChangeListener这几个方法，如使用中遇到问题请查看具体实现
 *         另外，可以通过setClearButton来设置清除按钮的图标，不调用将使用默认图标
 */
public class EditTextWithClearButton extends EditText {

    /**
     * 清除按钮的图片
     */
    private Drawable mDrawableClearButton = getResources().getDrawable(
            R.drawable.ic_clear);

    /**
     * 在没有清除按钮的情况下，放一个大小相等的空图片，主要是解决清除按钮状态切换时EditText的background闪烁
     */
    private Drawable mDrawableEmpty;

    /**
     * 标记是否需要处理点击事件
     */
    private boolean isClearButtonClickable;

    public EditTextWithClearButton(Context context) {
        super(context);
        init();
    }

    public EditTextWithClearButton(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public EditTextWithClearButton(Context context, AttributeSet attrs,
                                   int defStyle) {
        super(context, attrs, defStyle);
        init();
    }

    private OnTouchListener mMtOnTouchListener;
    private OnFocusChangeListener mMTonFocusChangeListener;
    
    public void setMtOnTouchListener (OnTouchListener listener) {
        mMtOnTouchListener = listener;
    }
    
    public void setMtOnFocusListener (OnFocusChangeListener listener) {
        mMTonFocusChangeListener = listener;
    }

    private void init() {

        // 初始化两种状态下的清除按钮
        mDrawableClearButton.setBounds(0, 0,
                mDrawableClearButton.getIntrinsicWidth(),
                mDrawableClearButton.getIntrinsicHeight());
        mDrawableEmpty = new Drawable() {

            @Override
            public void setColorFilter(ColorFilter cf) {
            }

            @Override
            public void setAlpha(int alpha) {
            }

            @Override
            public int getOpacity() {
                return 0;
            }

            @Override
            public void draw(Canvas canvas) {
            }
        };
        mDrawableEmpty.setBounds(mDrawableClearButton.getBounds());

        handleClearButton();

        // 设置点击事件
        setOnTouchListener(new OnTouchListener() {

            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if (mMtOnTouchListener != null) {
                    mMtOnTouchListener.onTouch(v, event);
                }

                EditTextWithClearButton editText = EditTextWithClearButton.this;

                // 如果清除按钮为空，不处理点击事件
                if (editText.getCompoundDrawables()[2] == null) {
                    return false;
                }

                if (event.getAction() != MotionEvent.ACTION_UP) {
                    return false;
                }

                // 如果清除按钮处在不显示状态，不处理点击事件
                if (!isClearButtonClickable) {
                    return false;
                }

                // 按点击位置处理点击事件
                if (event.getX() > editText.getWidth()
                        - editText.getPaddingRight()
                        - mDrawableClearButton.getIntrinsicWidth()) {
                    editText.setText("");
                    EditTextWithClearButton.this.handleClearButton();
                }

                return false;
            }
        });

        // 添加输入变化的监听
        addTextChangedListener(new TextWatcher() {

            @Override
            public void onTextChanged(CharSequence s, int start, int before,
                                      int count) {
            }

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count,
                                          int after) {
            }

            @Override
            public void afterTextChanged(Editable s) {
                EditTextWithClearButton.this.handleClearButton();
            }
        });

        // 添加焦点变化的监听
        setOnFocusChangeListener(new OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (mMTonFocusChangeListener != null) {
                    mMTonFocusChangeListener.onFocusChange(v, hasFocus);
                }
                EditTextWithClearButton.this.handleClearButton();

            }
        });
    }

    /**
     * 处理清除按钮显示与否的函数
     */
    private void handleClearButton() {
        // 如果当前EditText获取了焦点并且内容不为空，则显示清除按钮；其他情况都不显示清空按钮
        if (isFocused() && !TextUtils.isEmpty(getText().toString())) {
            setCompoundDrawables(getCompoundDrawables()[0],
                    getCompoundDrawables()[1], mDrawableClearButton,
                    getCompoundDrawables()[3]);
            isClearButtonClickable = true;
        } else {
            setCompoundDrawables(getCompoundDrawables()[0],
                    getCompoundDrawables()[1], mDrawableEmpty,
                    getCompoundDrawables()[3]);
            isClearButtonClickable = false;
        }
    }

    /**
     * 此函数只用来替换清除按钮的大小和样式
     *
     * @param id 图片的id
     */
    public void setClearButton(int id) {
        try {
            mDrawableClearButton = getResources().getDrawable(id);
        } catch (Exception e) {
            mDrawableClearButton = getResources().getDrawable(
                    R.drawable.ic_search_clear);
        } finally {
            init();
        }
    }

    /**
     * 搜索页为了hint能显示全，设置不要这个站位
     * */
    public void removeDrawableEmpty() {
        mDrawableEmpty.setBounds(0, 0, 0, 0);
    }

}
