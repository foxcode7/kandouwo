package com.kandouwo.model.datarequest;

import android.net.Uri;
import android.text.TextUtils;

import org.apache.http.HttpEntity;
import org.apache.http.client.HttpResponseException;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpEntityEnclosingRequestBase;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpUriRequest;
import org.apache.http.entity.StringEntity;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.protocol.HTTP;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.List;

import javax.net.ssl.SSLHandshakeException;

/**
 * Created by foxcoder on 14-9-23.
 */
public abstract class RequestBaseAdapter<T> extends RequestBase<T> {

    private static ExceptionObserver exceptionObserver;

    private boolean needRetry = true;//是否重试过，主要是为了支持重发机制

    protected String url; //保存请求的url，主要是为了支持重发机制

    public T execute() throws IOException {
        return execute(Origin.NET);
    }

    @Override
    protected T local() throws IOException {
        return null;
    }

    @Override
    protected void store(T data) {
    }

    @Override
    public boolean isLocalValid() {
        return false;
    }

    @Override
    public Uri getDataUri() {
        return null;
    }

    protected HttpUriRequest buildFormEntityRequest(String uri, List<BasicNameValuePair> params) {
        HttpEntityEnclosingRequestBase request = new HttpPost(uri);
        UrlEncodedFormEntity entity = null;
        try {
            entity = new UrlEncodedFormEntity(params, HTTP.UTF_8);
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        request.setEntity(entity);
        return request;
    }

    protected HttpUriRequest buildStringEntityRequest(String uri, String content) {
        HttpEntityEnclosingRequestBase request = new HttpPost(uri);
        HttpEntity entity = null;
        try {
            entity = new StringEntity(content, HTTP.UTF_8);
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        request.setEntity(entity);
        return request;

    }

    public static void setExceptionObserver(ExceptionObserver observer) {
        exceptionObserver = observer;
    }

    @Override
    public T execute(Origin origin) throws IOException {
        // https请求的重发机制
        try {
            return super.execute(origin);
        } catch (SSLHandshakeException exception) {

            // 中间人攻击出现的错误如下：
            // 02-21 19:14:03.656:
            // E/COM.SANKUAI.MEITUAN/Welcome.java:125(26133): main
            // javax.net.ssl.SSLHandshakeException:
            // java.security.cert.CertPathValidatorException: Trust anchor for
            // certification path not found.
            // 02-21 19:14:03.656:
            // E/COM.SANKUAI.MEITUAN/Welcome.java:125(26133): Caused by:
            // java.security.cert.CertificateException:
            // java.security.cert.CertPathValidatorException: Trust anchor for
            // certification path not found.

            // 根据异常类型进行判断，如果是SSLHandshakeException即中间人攻击出现的错误，就不再补发http请求了
            throw exception;

        } catch (HttpResponseException exception) {

            throw exception;

        } catch (IOException exception) {

            if (needRetry && !TextUtils.isEmpty(url) && url.contains("https://")) {

                if (exceptionObserver != null) {
                    exceptionObserver.onHttpsException(exception);
                }

                needRetry = false;
                url = url.replace("https://", "http://");
                return super.execute(origin);
            } else {
                throw exception;
            }
        }
    }
}
