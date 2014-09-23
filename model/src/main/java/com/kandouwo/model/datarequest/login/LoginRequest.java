package com.kandouwo.model.datarequest.login;

import android.net.Uri;

import com.kandouwo.model.datarequest.RequestBase;

import org.apache.http.client.methods.HttpUriRequest;

import java.io.IOException;

/**
 * Created by foxcoder on 14-9-23.
 */
public class LoginRequest extends RequestBase<User> {
    private static final String URL_PATH = "mobile.php?act=log";

    @Override
    protected User local() throws IOException {
        return null;
    }

    @Override
    protected String getUrl() {
        return null;
    }

    @Override
    protected void store(User data) {

    }

    @Override
    public HttpUriRequest getHttpUriRequest() {
        return null;
    }

    @Override
    public Uri getDataUri() {
        return null;
    }

    @Override
    public boolean isLocalValid() {
        return false;
    }
}
