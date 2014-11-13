package com.kindleren.kandouwo.search;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.kindleren.kandouwo.R;
import com.kindleren.kandouwo.base.BaseListAdapter;

import java.util.List;

/**
 * Created by xuezhangbin on 14-11-12.
 */
public class SearchHistoryAdapter extends BaseListAdapter<String> {

    public SearchHistoryAdapter(Context context, List<String> data) {
        super(context, data);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            convertView = mInflater.inflate(R.layout.search_history_list_item, null);
        }
        ((TextView)convertView).setText(getItem(position));
        return convertView;
}

    @Override
    public int getCount() {
        return super.getCount();
    }

}
