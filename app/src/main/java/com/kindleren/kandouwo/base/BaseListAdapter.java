package com.kindleren.kandouwo.base;

import android.content.Context;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

/**
 * Adapter基类
 *
 */
public abstract class BaseListAdapter<T> extends BaseAdapter {
    protected Context mContext;
    protected List<T> mData;
    protected LayoutInflater mInflater;
    protected Picasso picasso;
    protected int deep;


    public BaseListAdapter(Context context) {
        this(context, null);
    }

    public BaseListAdapter(Context context, List<T> data) {
        mContext = context;
//        picasso = RoboGuice.getInjector(context).getInstance(Picasso.class);
        if (data != null) {
            mData = new ArrayList<T>(data);
        }
        mInflater = (LayoutInflater) context
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);

//        RoboGuice.getInjector(context).injectMembersWithoutViews(this);
    }

    @Override
    public void notifyDataSetChanged() {
        super.notifyDataSetChanged();
    }

    public List<T> getData() {
        return mData;
    }

    /**
     * 设置数据
     *
     * @param data
     */
    public void setData(List<T> data) {
        if (data == null) {
            mData = null;
        } else {
            mData = new ArrayList<T>(data);
        }
        notifyDataSetChanged();
    }

    /**
     * 添加数据
     *
     * @param data
     */
    public void appendData(List<T> data) {
        if ( mData == null || mData.isEmpty()) {
            setData(data);
        } else {
            mData.addAll(data);
            notifyDataSetChanged();
        }
    }

    /**
     * 清除数据
     */
    public void clear() {
        mData.clear();
        notifyDataSetChanged();
    }

    @Override
    public int getCount() {
        return mData == null ? 0 : mData.size();
    }

    @Override
    public T getItem(int position) {
        return mData.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    protected String getText(int textRes) {
        return mContext.getString(textRes);
    }

    protected String getText(int res, Object... args) {
        return mContext.getString(res, args);
    }

    protected int getColor(int colorRes) {
        return mContext.getResources().getColor(colorRes);
    }

    public void setDeep(int position) {
        if (deep < position) {
            deep = position;
        }
    }

    public int getDeep() {
        return deep;
    }

    public void initDeep() {
        deep = 0;
    }

    public static void fillText(View root, int textRes, String textStr) {
        TextView textView = (TextView) root.findViewById(textRes);
        if (!TextUtils.isEmpty(textStr)) {
            textView.setVisibility(View.VISIBLE);
            textView.setText(textStr);
        } else {
            textView.setVisibility(View.GONE);
        }
    }

}
