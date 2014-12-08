package com.kandouwo.model.datarequest;

/**
 * Created by xuezhangbin on 14/12/2.
 */
public interface Pageable<D> {
    int size();

    Pageable<D> append(Pageable<D> d);
}
