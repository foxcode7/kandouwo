package com.kindleren.kandouwo;

import android.content.Context;

import com.google.inject.AbstractModule;
import com.google.inject.Provider;
import com.google.inject.Provides;
import com.google.inject.Singleton;
import com.google.inject.name.Names;
import com.kindleren.kandouwo.login.UserConfigController;
import com.kindleren.kandouwo.net.HttpClientProvider;
import com.kindleren.kandouwo.user.AccountProvider;
import com.kindleren.kandouwo.user.UserCenter;

import org.apache.http.client.HttpClient;

/**
 * Created by foxcoder on 14-9-19.
 */
public class GuiceModule extends AbstractModule {
    private final Context context;

    public GuiceModule(Context context) {
        this.context = context;
    }

    @Override
    protected void configure() {
        bind(AccountProvider.class).to(UserCenter.class).in(Singleton.class);
        bind(HttpClient.class).toProvider(HttpClientProvider.class).in(Singleton.class);
        bind(UserConfigController.class).annotatedWith(Names.named("normal_user")).toProvider(new UserConfigProvider(context, UserConfigController.AccountType.NORMAL_USER)).in(Singleton.class);
    }

    private static class UserConfigProvider implements Provider {
        private UserConfigController.AccountType type;
        private Context context;

        public UserConfigProvider(Context context, UserConfigController.AccountType type) {
            this.context = context;
            this.type = type;
        }

        @Override
        public Object get() {
            return new UserConfigController(context, type);
        }
    }

    @Provides
    @Singleton
    UserCenter userCenter() {
        return new UserCenter(context);
    }
}
