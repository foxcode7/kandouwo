package com.kindleren.kandouwo;

import com.google.inject.AbstractModule;
import com.google.inject.Singleton;
import com.google.inject.name.Names;
import com.kindleren.kandouwo.net.HttpClientProvider;

import org.apache.http.client.HttpClient;

/**
 * Created by foxcoder on 14-9-19.
 */
public class GuiceModule extends AbstractModule {
    @Override
    protected void configure() {
        bind(HttpClient.class).toProvider(HttpClientProvider.class).in(Singleton.class);
    }
}
