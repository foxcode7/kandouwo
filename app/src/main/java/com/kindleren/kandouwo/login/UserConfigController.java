package com.kindleren.kandouwo.login;

import android.content.Context;
import android.content.SharedPreferences;
import android.text.TextUtils;

import com.google.inject.Inject;
import com.google.inject.Singleton;
import com.kandouwo.model.SharedPreferencesUtils;

/**
 * Created by foxcoder on 14-9-25.
 */
@Singleton
public class UserConfigController {

    private static final String KEY_GROWTH_CONFIG = "growthconfig";

    /**
     * 用户登录时提示信息存放的位置
     */
    private SharedPreferences mPreferences;

    /**
     * 这个用来存储所有登录的用户名,在登录页将会根据用户的输入进行联想提示功能
     */
    private static final String ACCOUNTS = "_accounts";
    private static final String LAST_USER = "_last_user";
    public static final String USERS_SPLIT_FLAG = "&";
    private static final String USER_LOGIN_TIP = "user_login_tip";

    public enum AccountType{
        NORMAL_USER ,DYNAMIC_USER;
    }

    private final String PREFIX;

    @Inject
    public UserConfigController(Context context, AccountType accountType) {
        PREFIX = accountType.name();
        mPreferences = context.getSharedPreferences(USER_LOGIN_TIP, Context.MODE_PRIVATE);
    }


    public final String fetchUser() {
        return mPreferences.getString(PREFIX + ACCOUNTS, "");
    }

    /**
     * 存储登录过的用户名，再次登录输入用户名时，自动完成，方便输入
     *
     * @author leidiqiu @ 2012-5-2 10:40:09
     */
    public void storeUser(String addedUser) {
        String user = fetchUser();
        String[] users = user.split(USERS_SPLIT_FLAG);
        for (int i = 0, len = users.length; i < len; i++) {
            if (TextUtils.equals(users[i], addedUser)) {
                return;
            }
        }

        mPreferences.edit().putString(PREFIX + ACCOUNTS, user + USERS_SPLIT_FLAG + addedUser)
                .commit();

    }

    public void removeUser(String removedUser) {
        String user = fetchUser();
        String[] users = user.split(USERS_SPLIT_FLAG);
        String lastUser = "";

        user = "";

        for (int i = 0, len = users.length; i < len; i++) {
            if (!TextUtils.equals(users[i], removedUser)) {
                user = user + USERS_SPLIT_FLAG + users[i];
                lastUser = users[i];
            }
        }
        setLastUser(lastUser);
        mPreferences.edit().putString(PREFIX + ACCOUNTS, user).commit();
    }

    /**
     * 保存登录用户名
     */
    public void setLastUser(String lastUser) {
        mPreferences.edit().putString(PREFIX + LAST_USER, lastUser).commit();
    }

    /**
     * 取上一次登录的用户
     */
    public String getLastUser() {
        return mPreferences.getString(PREFIX + LAST_USER, "");
    }

    public void setGrowthConfig(String growthConfig) {
        SharedPreferencesUtils.apply(mPreferences.edit().putString(KEY_GROWTH_CONFIG, growthConfig));
    }

    public String getGrowthConfig() {
        return mPreferences.getString(KEY_GROWTH_CONFIG,
                "{\"1\":300,\"2\":1000,\"3\":3000,"
                        + "\"4\":10000,\"5\":30000,\"6\":100000}");
    }

}
