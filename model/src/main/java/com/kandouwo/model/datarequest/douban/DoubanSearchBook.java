package com.kandouwo.model.datarequest.douban;

import java.util.List;

/**
 * Created by foxcoder on 14-9-18.
 */
public class DoubanSearchBook {
    private int start;
    private int count;
    private int total;

    private List<DoubanBookInfo> books;

    public int getStart() {
        return start;
    }

    public void setStart(int start) {
        this.start = start;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public int getTotal() {
        return total;
    }

    public void setTotal(int total) {
        this.total = total;
    }

    public List<DoubanBookInfo> getBooks() {
        return books;
    }

    public void setBooks(List<DoubanBookInfo> books) {
        this.books = books;
    }
}
