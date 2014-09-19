package com.kandouwo.model.datarequest;

import android.content.SharedPreferences;

import com.kandouwo.model.dao.DaoSession;

import org.apache.http.client.HttpClient;

/**
 * Created by foxcoder on 14-9-18.
 * RequestBase的工厂,简化RequestBase的构造
 */
public interface RequestFactory {
    DaoSession getDaoSession();

    HttpClient getHttpClient();

    SharedPreferences getSharedPreferences();
}
