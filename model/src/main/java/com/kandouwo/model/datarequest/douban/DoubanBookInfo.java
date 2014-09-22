package com.kandouwo.model.datarequest.douban;

import java.util.List;

/**
 * Created by foxcoder on 14-9-19.
 */
public class DoubanBookInfo {
    private String id;//
    private String isbn10;
    private String isbn13;
    private String title;
    private String origin_title;
    private String alt_title;
    private String subtitle;
    private String url;
    private String alt;
    private String image;
    private Images images;
    private List<String> author;
    private List<String> translator;
    private String publisher;
    private String pubdate;
    private Rating rating;
    private List<Tags> tags;
    private String binding;
    private String price;
    private String pages;
    private String author_intro;
    private String summary;
    private String catalog;
    private String ebook_url; //豆瓣阅读，该字段只在存在对应电子书时提供
    private String ebook_price;

    public class Images{
        private String small;
        private String large;
        private String medium;
    }

    public class Rating{
        private int max;
        private int numRaters;
        private String average;
        private int min;
    }

    public class Tags{
        private int count;
        private String name;
    }
}
