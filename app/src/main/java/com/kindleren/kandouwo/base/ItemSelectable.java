package com.kindleren.kandouwo.base;

import java.util.List;

/**
 * Created by xuezhangbin on 14/12/5.
 */
public interface ItemSelectable<T> {
    boolean anySelectedItem();

    void selectItem(T item);

    boolean itemSelected(T item);

    void cancelSelectedItem(T item);

    List<T> getSelectedItems();

    void clearSelectedItems();
}
