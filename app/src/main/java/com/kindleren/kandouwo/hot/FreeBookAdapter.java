package com.kindleren.kandouwo.hot;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.kindleren.kandouwo.R;


/**
 * Created by foxcoder on 14-11-10.
 */
public class FreeBookAdapter extends ArrayAdapter<FreeBookData> {
    private LayoutInflater mInflater;

    public FreeBookAdapter(Context context, FreeBookData[] values) {
        super(context, R.layout.free_book_view, values);
        mInflater = (LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        Holder holder;
        if(convertView == null){
            convertView = mInflater.inflate(R.layout.free_book_view, parent, false);
            holder = new Holder();
            holder.bookImage = (ImageView) convertView.findViewById(R.id.book_image);
            holder.bookName = (TextView) convertView.findViewById(R.id.book_name);
            convertView.setTag(holder);
        } else {
            holder = (Holder) convertView.getTag();
        }

        holder.bookImage.setBackgroundResource(getItem(position).getBookImageResource());
        holder.bookName.setText(getItem(position).getBookName());

        return convertView;
    }

    private static class Holder {
        public ImageView bookImage;
        public TextView bookName;
    }
}
