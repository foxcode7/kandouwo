package com.kandouwo.model.datarequest.search;

import android.net.Uri;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.kandouwo.model.ApiConfig;
import com.kandouwo.model.datarequest.douban.DoubanBookInfo;

import java.io.IOException;
import java.util.List;

/**
 * Created by xuezhangbin on 14/12/5.
 */
public class SearchBookResultRequest extends AbstractBookListRequest {
    private static final String URL_PATH = "v2/book/search";
    private final String keyword;

    public SearchBookResultRequest(String keyword) {
        this.keyword = keyword;
    }

    @Override
    protected List<DoubanBookInfo> local() throws IOException {
        return null;//暂不做本地化
    }

    @Override
    public List<DoubanBookInfo> execute(Origin origin) throws IOException {
        return super.execute(Origin.NET);
    }

    @Override
    public boolean isLocalValid() {
        return false;
    }

    @Override
    protected String getBaseUrl() {
        Uri.Builder builder = Uri.parse(ApiConfig.baseDoubanApiUrl).buildUpon();
        builder.appendEncodedPath(URL_PATH)
                .appendQueryParameter("q", keyword)
                .toString();
        return builder.toString();
    }

    @Override
    protected String dataElementName() {
        return "books";
    }

    @Override
    public List<DoubanBookInfo> convert(JsonElement rootElement) throws IOException {
        JsonObject rootObject = rootElement.getAsJsonObject();
        int total = rootObject.has("total") ? rootObject.get("total")
                .getAsInt() : 0;
        setTotal(total);
        return super.convert(rootElement);
    }
}
