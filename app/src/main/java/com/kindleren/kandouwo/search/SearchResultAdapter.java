package com.kindleren.kandouwo.search;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.kandouwo.model.datarequest.douban.DoubanBookInfo;
import com.kindleren.kandouwo.R;
import com.kindleren.kandouwo.base.SelectableListAdapter;

import java.util.List;

/**
 * Created by xuezhangbin on 14/12/2.
 */
public class SearchResultAdapter extends SelectableListAdapter<DoubanBookInfo> {

    private Context context;

    public SearchResultAdapter(Context context) {
        this(context,null);
    }

    public SearchResultAdapter(Context context,List<DoubanBookInfo> data) {
        super(context,data);
        this.context = context;
    }

    @Override
    public int getCount() {
        return mData == null ? 0 : mData.size();
    }

    @Override
    public DoubanBookInfo getItem(int position) {
        return mData.get(position);
    }


    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        Holder holder;
        if (convertView == null) {
            convertView = mInflater.inflate(R.layout.search_result_item, parent, false);
            holder = new Holder();
            holder.bookImage = (ImageView) convertView.findViewById(R.id.book_image);
            holder.authorName = (TextView) convertView.findViewById(R.id.book_author);
            holder.book_content_type = (TextView) convertView.findViewById(R.id.book_content_type);
            holder.bookName = (TextView) convertView.findViewById(R.id.book_name);
            convertView.setTag(holder);
        } else {
            holder = (Holder) convertView.getTag();
        }

        holder.bookName.setText(getItem(position).getTitle().toString());
        picasso.with(context).load(getItem(position).getImages().getMedium()).into(holder.bookImage);
        return convertView;
    }

    private static class Holder {
        public ImageView bookImage;
        public TextView bookName;
        public TextView authorName;
        public TextView book_content_type;
    }
}
