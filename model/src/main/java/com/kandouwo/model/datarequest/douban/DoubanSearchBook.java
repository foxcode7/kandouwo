package com.kandouwo.model.datarequest.douban;

import java.util.List;

/**
 * Created by foxcoder on 14-9-18.
 */
public class DoubanSearchBook {
    private int start;
    private int count;
    private int total;

    public List<DoubanBookInfo> getBooks() {
        return books;
    }

    public void setBooks(List<DoubanBookInfo> books) {
        this.books = books;
    }

    private List<DoubanBookInfo> books;
}
