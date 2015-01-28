package com.kandouwo.model.datarequest.login;

import java.io.Serializable;

/**
 * Created by foxcoder on 14-9-23.
 */
public class User implements Serializable{
    private String status;
    private String msg; //错误信息
    private long uid;
    private String nickname;
    private String sex;
    private String signature;
    private String thumbnail;
    private String thumbnail_big;
    private String attend_date;
    private String lastlogin_place;
    private String readed_book_num;
    private String download_book_num;
    private String comment_num;
    private boolean kindleren;
    private long kdou;
    private long kindle_dou;
    private String token;
    private long expired;

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public long getUid() {
        return uid;
    }

    public void setUid(long uid) {
        this.uid = uid;
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }

    public String getSignature() {
        return signature;
    }

    public void setSignature(String signature) {
        this.signature = signature;
    }

    public String getThumbnail() {
        return thumbnail;
    }

    public void setThumbnail(String thumbnail) {
        this.thumbnail = thumbnail;
    }

    public String getThumbnail_big() {
        return thumbnail_big;
    }

    public void setThumbnail_big(String thumbnail_big) {
        this.thumbnail_big = thumbnail_big;
    }

    public String getAttend_date() {
        return attend_date;
    }

    public void setAttend_date(String attend_date) {
        this.attend_date = attend_date;
    }

    public String getLastlogin_place() {
        return lastlogin_place;
    }

    public void setLastlogin_place(String lastlogin_place) {
        this.lastlogin_place = lastlogin_place;
    }

    public String getReaded_book_num() {
        return readed_book_num;
    }

    public void setReaded_book_num(String readed_book_num) {
        this.readed_book_num = readed_book_num;
    }

    public String getDownload_book_num() {
        return download_book_num;
    }

    public void setDownload_book_num(String download_book_num) {
        this.download_book_num = download_book_num;
    }

    public String getComment_num() {
        return comment_num;
    }

    public void setComment_num(String comment_num) {
        this.comment_num = comment_num;
    }

    public boolean isKindleren() {
        return kindleren;
    }

    public void setKindleren(boolean kindleren) {
        this.kindleren = kindleren;
    }

    public long getKdou() {
        return kdou;
    }

    public void setKdou(long kdou) {
        this.kdou = kdou;
    }

    public long getKindle_dou() {
        return kindle_dou;
    }

    public void setKindle_dou(long kindle_dou) {
        this.kindle_dou = kindle_dou;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public long getExpired() {
        return expired;
    }

    public void setExpired(long expired) {
        this.expired = expired;
    }
}
