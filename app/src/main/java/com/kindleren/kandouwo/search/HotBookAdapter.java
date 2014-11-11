package com.kindleren.kandouwo.search;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.kindleren.kandouwo.R;

/**
 * Created by xuezhangbin on 14/11/10.
 */
public class HotBookAdapter extends ArrayAdapter<HotBookData> {
    private LayoutInflater mInflater;
    private static final int VIEW_TYPE_BOOK = 0;
    private static final int VIEW_TYPE_ALL = 1;
    private static final int VIEW_TYPE_NUM = 2;


    public HotBookAdapter(Context context, HotBookData[] hotBookDatas) {
        super(context, R.layout.hot_book_view, hotBookDatas);
        mInflater = (LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public int getViewTypeCount() {
        return VIEW_TYPE_NUM;
    }

    @Override
    public int getItemViewType(int position) {
        return (position == (getCount() - 1) ? VIEW_TYPE_ALL : VIEW_TYPE_BOOK);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        Holder holder;
        if(getItemViewType(position) == VIEW_TYPE_BOOK) {
            if (convertView == null) {
                convertView = mInflater.inflate(R.layout.hot_book_view, parent, false);
                holder = new Holder();
                holder.bookImage = (ImageView) convertView.findViewById(R.id.book_image);
                holder.bookName = (TextView) convertView.findViewById(R.id.book_name);
                convertView.setTag(holder);
            } else {
                holder = (Holder) convertView.getTag();
            }

            holder.bookImage.setBackgroundResource(getItem(position).getBookImageResource());
            holder.bookName.setText(getItem(position).getBookName());
        }

        else {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.listitem_all_book, null);
        }

        return convertView;
    }

    private static class Holder {
        public ImageView bookImage;
        public TextView bookName;
    }
}
