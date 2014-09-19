package com.kandouwo.model.datarequest;

import android.database.ContentObserver;
import android.net.Uri;

import com.google.gson.JsonElement;

import org.apache.http.client.ResponseHandler;
import org.apache.http.client.methods.HttpUriRequest;

import java.io.IOException;

/**
 * Created by foxcoder on 14-9-18.
 *
 * 数据请求的抽象，表达请求的数据，同时具有取得数据的方法
 */
public interface Request<T> extends ResponseHandler<T> {
    /**
     * JSON => Object
     */
    T convert(JsonElement rootElement) throws IOException;

    /**
     * 从网络获得数据后的处理
     *
     * @param data 从网络获得的数据
     */

    void onDataUpdate(T data);

    /**
     * @return 数据请求的网络请求
     */
    HttpUriRequest getHttpUriRequest();

    /**
     * @return 数据请求的本地资源描述
     */
    Uri getDataUri();

    /**
     * @return 本地数据是否有效
     */
    boolean isLocalValid();

    /**
     * 执行请求，获得数据
     *
     * @param origin 数据源
     * @throws IOException
     * @returns 所请求的数据
     */
    T execute(Origin origin) throws IOException;

    /**
     * 得到请求的数据后
     *
     * @param data
     */
    void onDataGot(T data);

    /**
     * 设置发起数据请求的observer,以防发起者收到变化通知,陷入循环
     *
     * @param observer
     */
    void setContentObserver(ContentObserver observer);

    public static enum Origin {
        NET, LOCAL, UNSPECIFIED, NET_PREFERED
    }
}
