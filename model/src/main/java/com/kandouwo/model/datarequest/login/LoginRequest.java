package com.kandouwo.model.datarequest.login;

import android.net.Uri;

import com.google.gson.JsonElement;
import com.kandouwo.model.ApiConfig;
import com.kandouwo.model.datarequest.RequestBaseAdapter;

import org.apache.http.client.methods.HttpUriRequest;
import org.apache.http.message.BasicNameValuePair;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by foxcoder on 14-9-23.
 */
public class LoginRequest extends RequestBaseAdapter<User> {
    private static final String URL_PATH = "/mobile.php?act=log";
    private String username;
    private String password;

    public LoginRequest(String username, String password){
        this.username = username;
        this.password = password;
    }

    @Override
    protected User local() throws IOException {
        return null;
    }

    @Override
    protected String getUrl() {
        return Uri.parse(ApiConfig.baseKindlerenApiUrl + URL_PATH).buildUpon().toString();
    }

    @Override
    public User convert(JsonElement rootElement) throws IOException {
        return convertDataElement(rootElement);
    }

    @Override
    protected void store(User data) {

    }

    @Override
    public HttpUriRequest getHttpUriRequest() {
        List<BasicNameValuePair> params = new ArrayList<BasicNameValuePair>();
        params.add(new BasicNameValuePair("username", username));
        params.add(new BasicNameValuePair("password", password));
        return buildFormEntityRequest(getUrl(), params);
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
