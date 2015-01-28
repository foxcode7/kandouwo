package com.kindleren.kandouwo.base;

import android.content.Intent;
import android.os.Bundle;

import com.google.inject.Inject;
import com.kindleren.kandouwo.login.LoginActivity;
import com.kindleren.kandouwo.login.LoginObserver;
import com.kindleren.kandouwo.user.UserCenter;

/**
 * Created by foxcoder on 14-12-18.
 */
public class BaseAuthenticatedActivity extends BaseActivity implements LoginObserver {
    @Inject
    protected UserCenter userCenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        userCenter.register(this);
    }

    @Override
    protected void onDestroy() {
        /**
         * super.onDestroy()，调用这个父亲的方法，会把已经注册的injectors都释放掉；
         * 而userCenter是通过@Inject出来的对象，因此，它也会为null。
         */
        if (userCenter != null) {
            userCenter.unregister(this);
        }
        super.onDestroy();
    }

    protected boolean logined() {
        return userCenter.isLogin();
    }

    protected void requestLogin() {
        Intent intent = new Intent(this, LoginActivity.class);
        startActivity(intent);
    }

    protected void onLogin() {

    }

    protected void onLogout() {

    }

    @Override
    public void onChanged(LoginStatus status) {
        switch (status) {
            case LOGIN:
                onLogin();
                break;
            case LOGOUT:
                onLogout();
                break;
        }
    }

}
