package com.kandouwo.model.datarequest;

import android.content.SharedPreferences;

import com.kandouwo.model.dao.DaoSession;

import org.apache.http.client.HttpClient;

/**
 * Created by foxcoder on 14-9-18.
 */
public class DefaultRequestFactory implements RequestFactory {
    private static final DefaultRequestFactory instance = new DefaultRequestFactory();
    private static DaoSession daoSession;
    private static HttpClient httpClient;
    private static SharedPreferences preferences;

    private DefaultRequestFactory() {
    }

    public static DefaultRequestFactory getInstance() {
        return instance;
    }

    public static void setPreferences(SharedPreferences preferences) {
        DefaultRequestFactory.preferences = preferences;
    }

    @Override
    public DaoSession getDaoSession() {
        return daoSession;
    }

    public static void setDaoSession(DaoSession daoSession) {
        DefaultRequestFactory.daoSession = daoSession;
    }

    @Override
    public HttpClient getHttpClient() {
        return httpClient;
    }

    public static void setHttpClient(HttpClient httpClient) {
        DefaultRequestFactory.httpClient = httpClient;
    }

    @Override
    public SharedPreferences getSharedPreferences() {
        return null;
    }
}
