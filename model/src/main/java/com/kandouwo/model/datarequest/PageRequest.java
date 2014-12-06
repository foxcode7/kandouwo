package com.kandouwo.model.datarequest;

/**
 * Created by xuezhangbin on 14/12/2.
 */
public interface PageRequest<T> extends Request<T> {
    String PAGING = "paging";
    String OFFSET = "offset";
    String LIMIT = "limit";

    void setStart(int start);

    void setLimit(int limit);

    void setTotal(int total);

    int getTotal();
}
