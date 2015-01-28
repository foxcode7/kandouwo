package com.kindleren.kandouwo.user;

/**
 * Created by foxcoder on 14-9-25.
 */
public interface AccountProvider {
    public long getUserId();

    public String getToken();
}
