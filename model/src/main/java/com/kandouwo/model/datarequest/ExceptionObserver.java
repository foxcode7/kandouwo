package com.kandouwo.model.datarequest;

/**
 * Created by foxcoder on 14-9-23.
 */
public interface ExceptionObserver {

    void onHttpsException(Exception exception);
}