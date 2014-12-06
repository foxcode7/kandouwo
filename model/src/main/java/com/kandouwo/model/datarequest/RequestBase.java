package com.kandouwo.model.datarequest;

import android.content.SharedPreferences;
import android.database.ContentObserver;

import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;
import com.google.gson.JsonParser;
import com.kandouwo.model.GsonProvider;
import com.kandouwo.model.dao.DaoSession;

import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.HttpResponseException;
import org.apache.http.client.methods.HttpUriRequest;
import org.apache.http.protocol.HTTP;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Reader;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;

/**
 * Created by foxcoder on 14-9-18.
 *
 * 数据请求的基类，包含网络和本地获取的功能。
 */
public abstract class RequestBase<T> implements Request<T> {
    protected static final JsonParser parser = new JsonParser();
    protected static final Gson gson = GsonProvider.getInstance().get();
    protected final DaoSession daoSession;
    protected final HttpClient httpClient;
    protected final SharedPreferences preferences;
    private ContentObserver observer;

    protected RequestBase() {
        this(DefaultRequestFactory.getInstance());
    }

    protected RequestBase(RequestFactory factory) {
        this.daoSession = factory.getDaoSession();
        this.httpClient = factory.getHttpClient();
        this.preferences = factory.getSharedPreferences();
    }

    @Override
    public void setContentObserver(ContentObserver observer) {
        this.observer = observer;
    }

    /**
     * 获取直接子类的泛型参数
     *
     * @return
     */
    protected Type getType() {
        Type superclass = getClass().getGenericSuperclass();
        while ((superclass instanceof Class) && !superclass.equals(RequestBase.class)) {
            superclass = ((Class) superclass).getGenericSuperclass();
        }
        if (superclass instanceof Class) {
            throw new RuntimeException("Missing type parameter.");
        }
        ParameterizedType parameterized = (ParameterizedType) superclass;
        return parameterized.getActualTypeArguments()[0];
    }

    public T handleResponse(HttpResponse response) throws ClientProtocolException, IOException {
        if (response.getEntity() == null) {
            throw new IOException("Failed to get response's entity");
        }

        BufferedReader reader = new BufferedReader(new InputStreamReader(
                response.getEntity().getContent(), HTTP.UTF_8));
        return convert(reader);
    }

    private T convert(Reader reader) throws IOException {
        try {
            JsonElement rootElement = parser.parse(reader);
            return convert(rootElement);
        } catch (JsonParseException jpe) {
            IOException ioe = new IOException(
                    "Parse exception converting JSON to object");
            ioe.initCause(jpe);
            throw ioe;
        } finally {
            try {
                reader.close();
            } catch (IOException ignored) {
                // Ignored
            }
        }
    }

    @Override
    public T convert(JsonElement rootElement) throws IOException {
        if (!rootElement.isJsonObject()) {
            throw new JsonParseException("Root is not JsonObject");
        }
        JsonObject root = rootElement.getAsJsonObject();

        // give me a chance to convert other json element
        final String otherElementName = otherElementName();
        if (otherElementName != null && root.has(otherElementName)) {
            convertOtherElement(root.get(otherElementName));
        }

        final String dataElementName = dataElementName();
        if (root.has(dataElementName)) {
            JsonElement data = root.get(dataElementName);
            return convertDataElement(data);
        } else {
            if (root.has("error")) {
                JsonElement error = root.get("error");
                convertErrorElement(error);
            }
            throw new IOException("Fail to get books");
        }
    }

    protected void convertErrorElement(JsonElement error) throws HttpResponseException {
        if (error.isJsonObject()) {
            JsonObject errorObject = error.getAsJsonObject();
            int code = errorObject.has("code") ? errorObject.get(
                    "code").getAsInt() : 400;
            String message = errorObject.has("message") ? errorObject
                    .get("message").getAsString() : "";
            throw new HttpResponseException(code, message);
        }
    }

    protected String dataElementName() {
        return "books";
    }


    protected T convertDataElement(JsonElement data) {
        return gson.fromJson(data, getType());
    }

    protected String otherElementName() {
        return null;
    }

    protected void convertOtherElement(JsonElement jsonElement) {

    }

    /**
     * 从本地获取数据
     *
     * @return
     * @throws IOException
     */
    protected abstract T local() throws IOException;

    protected abstract String getUrl();

    /**
     * 从网络获取数据
     *
     * @return
     * @throws IOException
     */
    protected T net() throws IOException {
        HttpUriRequest httpRequest = getHttpUriRequest();
        return httpClient.execute(httpRequest, this);
    }

    protected T performNet() throws IOException {
        T data;
        try {
            data = net();
        } catch (SecurityException e) {
            throw new IOException(e.getMessage(), e);
        }
        onDataUpdate(data);
        onDataGot(data);
        return data;
    }

    protected final T performLocal() throws IOException {
        T data = local();
        onDataGot(data);
        return data;
    }

    @Override
    public void onDataUpdate(T data) {
        store(data);
    }

    /**
     * 将网络获得的数据存到本地
     */
    protected abstract void store(T data);

    @Override
    public T execute(Origin origin) throws IOException {
        switch (origin) {
            case LOCAL:
                return performLocal();
            case NET:
                return performNet();
            case NET_PREFERED:
                T data;
                try {
                    data = net();
                    store(data);
                }
                catch(Exception e){
                }
                data = performLocal();
                return data;
            case UNSPECIFIED:
            default:
                if (isLocalValid()) {
                    return performLocal();
                } else {
                    return performNet();
                }
        }
    }

    @Override
    public void onDataGot(T data) {

    }
}
