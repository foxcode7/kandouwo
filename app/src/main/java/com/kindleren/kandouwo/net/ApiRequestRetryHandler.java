package com.kindleren.kandouwo.net;

import org.apache.http.HttpRequest;
import org.apache.http.NoHttpResponseException;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.impl.client.DefaultHttpRequestRetryHandler;
import org.apache.http.protocol.ExecutionContext;
import org.apache.http.protocol.HttpContext;

import java.io.IOException;

/**
 * Created by foxcoder on 14-9-19.
 */
public class ApiRequestRetryHandler extends DefaultHttpRequestRetryHandler {

    public ApiRequestRetryHandler() {
        super(5, true);
    }

    public ApiRequestRetryHandler(int retryCount) {
        super(retryCount, true);
    }

    @Override
    public boolean retryRequest(IOException e, int i, HttpContext httpContext) {
        // 修复部分手机2g网络下请求不能到达的问题
        if(e instanceof NoHttpResponseException || e instanceof ClientProtocolException) {
            return i < 5;
        }

        HttpRequest request = (HttpRequest) httpContext
                .getAttribute(ExecutionContext.HTTP_REQUEST);
        boolean isGetRequest = request.getRequestLine().getMethod()
                .equalsIgnoreCase("GET");
        boolean retryPermitted = super.retryRequest(e, i, httpContext);
        return (isGetRequest && retryPermitted);
    }
}
