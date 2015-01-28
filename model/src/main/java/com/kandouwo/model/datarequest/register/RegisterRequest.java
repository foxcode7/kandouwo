package com.kandouwo.model.datarequest.register;

import android.net.Uri;

import com.google.gson.JsonElement;
import com.kandouwo.model.ApiConfig;
import com.kandouwo.model.datarequest.RequestBaseAdapter;
import com.kandouwo.model.datarequest.login.User;

import org.apache.http.client.methods.HttpUriRequest;
import org.apache.http.message.BasicNameValuePair;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by foxcoder on 14-12-18.
 */
public class RegisterRequest extends RequestBaseAdapter<User> {
    private static final String URL_PATH = "/api/register";
    private String email;
    private String password;
    private String deviceId;

    public RegisterRequest(String email, String password, String deviceId){
        this.email = email;
        this.password = password;
        this.deviceId = deviceId;
    }

    @Override
    protected User local() throws IOException {
        return null;
    }

    @Override
    protected String getUrl() {
        return Uri.parse(ApiConfig.baseKandouwoApiUrl + URL_PATH).buildUpon().toString();
    }

    @Override
    protected void store(User data) {

    }

    @Override
    public User convert(JsonElement rootElement) throws IOException {
        return super.convert(rootElement);

//        JsonObject root = rootElement.getAsJsonObject();
//        if(root.has("success")){
//            //TODO:等一下鬼哥看怎么说
//            ......
//        }
    }

    @Override
    public HttpUriRequest getHttpUriRequest() {
        List<BasicNameValuePair> params = new ArrayList<BasicNameValuePair>();
        params.add(new BasicNameValuePair("email", email));
        params.add(new BasicNameValuePair("password", password));
        params.add(new BasicNameValuePair("uuid", deviceId));
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
