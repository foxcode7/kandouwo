package com.kandouwo.model.datarequest.search;

import android.net.Uri;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.kandouwo.model.ApiConfig;
import com.kandouwo.model.datarequest.RequestBase;

import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpUriRequest;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;


public class SearchHotwordRequest extends RequestBase<List<HotWord>> {
    private static final String URL_PATH = "";

    public SearchHotwordRequest() {

    }

    @Override
    protected List<HotWord> local() throws IOException {
        return null;
    }

    @Override
    protected void store(List<HotWord> data) {
    }

    @Override
    public HttpUriRequest getHttpUriRequest() {
        return new HttpGet(getUrl());
    }

    @Override
    public Uri getDataUri() {
        return null;
    }

    @Override
    public boolean isLocalValid() {
        return false;
    }

    @Override
    protected List<HotWord> convertDataElement(JsonElement data) {
        JsonObject jsonObject = data.getAsJsonObject();
        if (jsonObject.has("hotwords")) {
            List<HotWord> results = new ArrayList<HotWord>();
            if (jsonObject.has("title")) {
                HotWord hotWord = new HotWord();
                hotWord.name = jsonObject.get("title").getAsString();
                hotWord.isHot = true;
                results.add(hotWord);
            }
            JsonArray array = jsonObject.getAsJsonArray("hotwords");
            for (int i = 0; i < array.size(); i++) {
                HotWord hotWord = new HotWord();
                hotWord.name = array.get(i).getAsString();
                results.add(hotWord);
            }
            return results;
        }
        return null;
    }

    @Override
    protected String getUrl() {
        Uri.Builder builder = Uri.parse(ApiConfig.baseKindlerenApiUrl + URL_PATH).buildUpon();
        return builder.toString();
    }
}
