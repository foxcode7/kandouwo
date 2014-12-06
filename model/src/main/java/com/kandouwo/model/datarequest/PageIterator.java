package com.kandouwo.model.datarequest;

import java.io.IOException;
import java.util.List;

/**
 * Created by xuezhangbin on 14/12/2.
 */
public class PageIterator<T> {
    private final PageRequest<T> request;
    private final Request.Origin origin;

    private int start;
    private int limit;

    private boolean hasNext = true;
    private T resource;
    public PageIterator(PageRequest<T> request, Request.Origin origin, int limit, int start) {
        this.request = request;
        this.origin = origin;
        this.limit = limit;
        this.start = start;
    }

    public PageIterator(PageRequest<T> request, Request.Origin origin, int limit) {
        this(request, origin, limit, 0);
    }

    public T getResource() {
        return resource;
    }

    public void setResource(T resource) {
        this.resource = resource;
    }

    public PageRequest<T> getRequest() {
        return request;
    }

    public boolean hasNext() {
        return hasNext;
    }

    public void setHasNext(boolean hasNext) {
        this.hasNext = hasNext;
    }

    public void offsetStart(int offset) {
        start += offset;
    }

    public int getStart() {
        return start;
    }

    public int getLimit() {
        return limit;
    }

    public Request.Origin getOrigin() {
        return origin;
    }

    public Request.Origin loadFrom() {
        if (origin == Request.Origin.UNSPECIFIED) {
            request.setStart(start);
            request.setLimit(limit);
            return request.isLocalValid() ? Request.Origin.LOCAL : Request.Origin.NET;
        } else {
            return origin;
        }
    }

    public synchronized T next() throws IOException {
        if (!hasNext()) {
            throw new IllegalStateException("Doesn't have next");
        }
        request.setStart(start);
        request.setLimit(limit);
        T data = request.execute(origin);
        int total = request.getTotal();
        if (data == null) {
            hasNext = false;
        } else {
            int size;
            if (data instanceof List) {
                size = ((List) data).size();
                if (resource == null) {
                    resource = data;
                } else {
                    ((List) resource).addAll((List) data);
                }
            } else if (data instanceof Pageable) {
                size = ((Pageable) data).size();
                if (resource == null) {
                    resource = data;
                } else {
                    ((Pageable) resource).append((Pageable) data);
                }
            } else {
                throw new IllegalStateException("D must be a List or Pageable");
            }
            start += size;
            if (total > 0) {
                hasNext = start < total;
            } else if (size < limit) {
                hasNext = false;
            }
        }
        return data;
    }

    public void setLimit(int limit) {
        this.limit = limit;
    }

    public void setStart(int start) {
        this.start = start;
    }
}
