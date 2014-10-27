package com.kindleren.kandouwo.login;

import java.util.ArrayList;

/**
 * Created by foxcoder on 14-9-25.
 *
 * 登录状态的观察者，用来监听登录状态的变化
 */
public class LoginObservable {

    private ArrayList<LoginObserver> mObservers = new ArrayList<LoginObserver>();

    /**
     * 注册一个登录状态监听
     */
    public void register(LoginObserver observer) {
        synchronized (mObservers) {
            if (!mObservers.contains(observer)) {
                mObservers.add(0, observer);
            }
        }
    }

    /**
     * 注销一个登录状态监听
     */
    public void unregister(LoginObserver observer) {
        if (observer != null) {
            synchronized (mObservers) {
                int index = mObservers.indexOf(observer);
                if (-1 != index) {
                    mObservers.remove(index);
                }
            }
        }
    }

    /**
     * 登录状态发生了变化
     */
    public void notifyChanged(LoginObserver.LoginStatus status) {
        synchronized (mObservers) {
            for (LoginObserver observer : mObservers) {
                observer.onChanged(status);
            }
        }
    }
}
