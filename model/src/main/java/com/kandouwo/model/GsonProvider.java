package com.kandouwo.model;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

/**
 * Created by foxcoder on 14-9-18.
 */
public class GsonProvider {
    private static GsonProvider provider;
    private final Gson gson;

    private GsonProvider(){
        gson = new Gson();//这里对比看豆窝没看明白
    }

    public synchronized static GsonProvider getInstance() {
        if (provider == null) {
            provider = new GsonProvider();
        }
        return provider;
    }

    public Gson get() {
        return gson;
    }
}
