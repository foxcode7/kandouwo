package com.kindleren.kandouwo.base;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;

import com.google.inject.Inject;
import com.kindleren.kandouwo.login.LoginActivity;
import com.kindleren.kandouwo.login.LoginObserver;
import com.kindleren.kandouwo.user.UserCenter;

/**
 * Created by foxcoder on 14-9-25.
 */
public class BaseAuthenticatedFragment extends BaseFragment implements LoginObserver {

    @Inject
    protected UserCenter userCenter;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        userCenter.register(this);

    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        userCenter.unregister(this);
    }

    protected void requestLogin() {
        Intent intent = new Intent(getActivity(), LoginActivity.class);
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
