package com.kindleren.kandouwo.base;

import android.content.Context;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by xuezhangbin on 14/12/5.
 */
public abstract class SelectableListAdapter<T> extends BaseListAdapter<T> implements ItemSelectable<T> {
    List<T> selectedItems = new ArrayList<T>();

    public SelectableListAdapter(Context context) {
        super(context);
    }

    public SelectableListAdapter(Context context, List<T> data) {
        super(context, data);
    }

    @Override
    public boolean anySelectedItem() {
        return !selectedItems.isEmpty();
    }

    @Override
    public void selectItem(T item) {
        selectedItems.add(item);
        notifyDataSetChanged();
    }

    @Override
    public boolean itemSelected(T item) {
        return selectedItems.contains(item);
    }

    @Override
    public void cancelSelectedItem(T item) {
        selectedItems.remove(item);
        notifyDataSetChanged();
    }

    @Override
    public List<T> getSelectedItems() {
        return selectedItems;
    }

    @Override
    public void clearSelectedItems() {
        selectedItems.clear();
        notifyDataSetChanged();
    }
    public void toggleSelectState(int position) {
        if (isItemSelected(position)) {
            cancelSelectedItem(position);
        } else {
            selectItem(position);
        }
    }

    public boolean isItemSelected(int position) {
        return itemSelected(getItem(position));
    }

    public void selectItem(int position) {
        selectItem(getItem(position));
    }

    public void cancelSelectedItem(int position) {
        cancelSelectedItem(getItem(position));
    }

    public int getSelectedItemCount() {
        return getSelectedItems().size();
    }
}
