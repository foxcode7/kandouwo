package com.kindleren.kandouwo.hot;

/**
 * Created by foxcoder on 14-11-10.
 */
public class FreeBookData {
    private int bookImageResource;
    private String bookName;

    public FreeBookData(int bookImageResource, String bookName) {
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
