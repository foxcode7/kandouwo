package com.kindleren.kandouwo.login;

/**
 * Created by foxcoder on 14-9-25.
 */
public interface LoginObserver {

    public enum LoginStatus {
        LOGIN,  //登录
        LOGOUT, //退出
    }

    /**
     * 登录状态发生了变化
     */
    public void onChanged(LoginStatus status);

}

