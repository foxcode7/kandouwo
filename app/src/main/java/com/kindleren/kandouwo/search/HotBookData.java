package com.kindleren.kandouwo.search;

/**
 * Created by xuezhangbin on 14/11/10.
 */
public class HotBookData {

    private int bookImageResource;
    private String bookName;

    public HotBookData(int bookImageResource, String bookName) {
        this.bookImageResource = bookImageResource;
        this.bookName = bookName;
    }

    public int getBookImageResource() {
        return bookImageResource;
    }

    public String getBookName() {
        return bookName;
    }
}
