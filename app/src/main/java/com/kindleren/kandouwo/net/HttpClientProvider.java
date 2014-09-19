package com.kindleren.kandouwo.net;

import android.app.Application;
import android.content.pm.PackageInfo;
import android.net.SSLCertificateSocketFactory;
import android.os.Build;

import com.google.inject.Inject;
import com.google.inject.Provider;

import org.apache.http.HttpResponse;
import org.apache.http.HttpVersion;
import org.apache.http.conn.ClientConnectionManager;
import org.apache.http.conn.ConnectionKeepAliveStrategy;
import org.apache.http.conn.scheme.PlainSocketFactory;
import org.apache.http.conn.scheme.Scheme;
import org.apache.http.conn.scheme.SchemeRegistry;
import org.apache.http.conn.ssl.SSLSocketFactory;
import org.apache.http.impl.client.AbstractHttpClient;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.impl.conn.tsccm.ThreadSafeClientConnManager;
import org.apache.http.params.BasicHttpParams;
import org.apache.http.params.HttpConnectionParams;
import org.apache.http.params.HttpParams;
import org.apache.http.params.HttpProtocolParams;
import org.apache.http.protocol.HTTP;
import org.apache.http.protocol.HttpContext;

import roboguice.util.Ln;

/**
 * Created by foxcoder on 14-9-19.
 */
public class HttpClientProvider implements Provider<AbstractHttpClient> {
    private static final int TIMEOUT = 60 * 1000;
    @Inject
    protected Application context;
    @Inject
    protected GzipHttpInterceptor gzip;
    @Inject
    protected PackageInfo info;
    @Inject
    protected LoggingHttpInterceptor log;
    @Inject
    protected ApiRequestRetryHandler retry;

    protected SSLSocketFactory getSSLSocketFactory() {
        SSLSocketFactory sslSocketFactory = SSLSocketFactory.getSocketFactory();
        try {
            // 创建信任所有ssl的ssl socket。
            sslSocketFactory = MySSLSocketFactory.getInstance();
        } catch (Exception e1) {
            Ln.e(e1);
        }
        sslSocketFactory
                .setHostnameVerifier(SSLSocketFactory.ALLOW_ALL_HOSTNAME_VERIFIER);
        return sslSocketFactory;
    }

    @Override
    public AbstractHttpClient get() {
        HttpParams params = new BasicHttpParams();

        // Turn off stale checking. Our connections break all the time anyway,
        // and it's not worth it to pay the penalty of checking every time.
        HttpConnectionParams.setStaleCheckingEnabled(params, false);

        HttpProtocolParams.setVersion(params, HttpVersion.HTTP_1_1);
        HttpProtocolParams.setContentCharset(params, HTTP.UTF_8);

        // Default connection and socket timeout of 20 seconds. Tweak to taste.
        HttpConnectionParams.setConnectionTimeout(params, TIMEOUT);
        HttpConnectionParams.setSoTimeout(params, TIMEOUT);
        HttpConnectionParams.setSocketBufferSize(params, 8192);

        // Set the specified user agent and register standard protocols.
        SchemeRegistry schemeRegistry = new SchemeRegistry();
        schemeRegistry.register(new Scheme("http", PlainSocketFactory
                .getSocketFactory(), 80));

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.FROYO) {
            // android2.2以及以上版本走下面的逻辑
            android.net.SSLSessionCache sessionCache = context == null ? null
                    : new android.net.SSLSessionCache(context);
            schemeRegistry.register(new Scheme("https",
                    SSLCertificateSocketFactory.getHttpSocketFactory(TIMEOUT,
                            sessionCache), 443));
        } else {
            // android2.1版本走这个逻辑
            SSLSocketFactory sslSocketFactory = getSSLSocketFactory();
            if (sslSocketFactory != null) {
                schemeRegistry.register(new Scheme("https", sslSocketFactory,
                        443));
            }
        }

        ClientConnectionManager manager = new ThreadSafeClientConnManager(
                params, schemeRegistry);
        AbstractHttpClient httpClient = new DefaultHttpClient(manager, params);

        httpClient.setKeepAliveStrategy(new ConnectionKeepAliveStrategy() {
            public long getKeepAliveDuration(HttpResponse httpResponse,
                                             HttpContext httpContext) {
                return 120000L;
            }
        });

        httpClient.setHttpRequestRetryHandler(retry);
        httpClient.addRequestInterceptor(log);
        httpClient.addRequestInterceptor(gzip);
        httpClient.addResponseInterceptor(gzip);
        httpClient.addResponseInterceptor(log);

        return httpClient;
    }
}
