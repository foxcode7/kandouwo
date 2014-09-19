package com.kindleren.kandouwo;

import android.app.Application;

import com.kandouwo.model.SharedPreferencesUtils;
import com.kandouwo.model.dao.DaoSession;
import com.kandouwo.model.datarequest.DefaultRequestFactory;

import org.apache.http.client.HttpClient;
import org.apache.http.impl.client.DefaultHttpClient;

import roboguice.RoboGuice;
import roboguice.inject.RoboInjector;

/**
 * Created by foxcoder on 14-9-19.
 */
public class KandouwoApplication extends Application {
    @Override
    public void onCreate() {
        super.onCreate();

        initModel();
    }

    private void initModel(){
        RoboInjector injector = RoboGuice.getInjector(this);

//        DefaultRequestFactory.setDaoSession(injector
//                .getInstance(DaoSession.class));

        DefaultRequestFactory.setPreferences(SharedPreferencesUtils
                .getDataSharedPreferences(this));

        HttpClient httpClient = injector.getInstance(HttpClient.class);
        DefaultRequestFactory.setHttpClient(httpClient);
    }
}
