package com.kindleren.kandouwo.user;

import android.content.Context;
import android.content.SharedPreferences;
import android.text.TextUtils;

import com.google.gson.Gson;
import com.kandouwo.model.GsonProvider;
import com.kandouwo.model.SharedPreferencesUtils;
import com.kandouwo.model.datarequest.login.User;
import com.kindleren.kandouwo.login.LoginObservable;
import com.kindleren.kandouwo.login.LoginObserver;

/**
 * Created by foxcoder on 14-9-25.
 */
public class UserCenter extends LoginObservable implements AccountProvider {
    private static final String KEY_USER_NAME = "username";
    private static final String KEY_TOKEN = "token";

    private final SharedPreferences mPreferences;
    private final Gson mGson;
    private final Context context;

    public UserCenter(Context context) {
        this.context = context;
        mPreferences = SharedPreferencesUtils.getUserSharedPreferences(context);
        mGson = GsonProvider.getInstance().get();
    }

    /**
     * 记录看豆窝登录信息
     */
    public void login(User user) {
        if (user == null) {
            throw new IllegalArgumentException("User can not be null");
        }

        updateUserInfo(user);

        // 告知观察者已登录
        notifyChanged(LoginObserver.LoginStatus.LOGIN);
    }

    /**
     * @return 是否已登录
     */
    public boolean isLogin() {
        return getUserId() > 0 && !TextUtils.isEmpty(getToken());
    }

    @Override
    public String getToken() {
        return mPreferences.getString(KEY_TOKEN, "");
    }

    /**
     * 退出看豆窝登录，其实就是清除本地缓存的用户信息
     */
    public void logout() {
        SharedPreferencesUtils.apply(mPreferences.edit().clear());
        // 告知观察者已注销
        notifyChanged(LoginObserver.LoginStatus.LOGOUT);
    }

    public void updateUserInfo(User user) {
        setUserName(user.getNickname());
    }

    public void setUserName(String userName) {
        SharedPreferencesUtils.apply(mPreferences.edit().putString(
                KEY_USER_NAME, userName));
    }

    @Override
    public long getUserId() {
        return 0;
    }
}
