package com.kindleren.kandouwo.widget;

/**
 * Created by foxcoder on 14-12-13.
 */
public interface DragGridBaseAdapter {
    /**
     * 重新排列数据
     * @param oldPosition
     * @param newPosition
     */
    public void reorderItems(int oldPosition, int newPosition);


    /**
     * 设置某个item隐藏
     * @param hidePosition
     */
    public void setHideItem(int hidePosition);

    /**
     * 删除某个item
     * @param removePosition
     */
    public void removeItem(int removePosition);
}
