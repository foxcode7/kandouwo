package com.kindleren.kandouwo.net;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.zip.GZIPInputStream;

import org.apache.http.Header;
import org.apache.http.HeaderElement;
import org.apache.http.HttpEntity;
import org.apache.http.HttpException;
import org.apache.http.HttpRequest;
import org.apache.http.HttpRequestInterceptor;
import org.apache.http.HttpResponse;
import org.apache.http.HttpResponseInterceptor;
import org.apache.http.entity.HttpEntityWrapper;
import org.apache.http.protocol.HttpContext;

/**
 * Created by IntelliJ IDEA. User: liang Date: 11-1-30 To change this template
 * use File | Settings | File Templates.
 */
public class GzipHttpInterceptor implements HttpRequestInterceptor,
        HttpResponseInterceptor {

    @Override
    public void process(HttpRequest httpRequest, HttpContext httpContext)
            throws HttpException, IOException {
        if (!httpRequest.containsHeader("Accept-Encoding")) {
            httpRequest.addHeader("Accept-Encoding", "gzip");
        }
    }

    @Override
    public void process(HttpResponse httpResponse, HttpContext httpContext)
            throws HttpException, IOException {
        HttpEntity entity = httpResponse.getEntity();
        if (entity == null)
            return;
        Header ceheader = entity.getContentEncoding();
        if (ceheader != null) {
            HeaderElement[] codecs = ceheader.getElements();
            for (int i = 0; i < codecs.length; i++) {
                if (codecs[i].getName().equalsIgnoreCase("gzip")) {
                    httpResponse.setEntity(new GzipDecompressingEntity(
                            httpResponse.getEntity()));
                    return;
                }
            }
        }
    }

    static class GzipDecompressingEntity extends HttpEntityWrapper {

        public GzipDecompressingEntity(final HttpEntity entity) {
            super(entity);
        }

        @Override
        public InputStream getContent() throws IOException,
                IllegalStateException {
            // the wrapped entity's getContent() decides about repeatability
            InputStream wrappedin = wrappedEntity.getContent();
            return new GZIPInputStream(wrappedin);
        }

        @Override
        public long getContentLength() {
            // length of ungzipped content is not known
            return -1;
        }

        @Override
        public void writeTo(OutputStream outstream) throws IOException {
            if (outstream == null) {
                throw new IllegalArgumentException("Output stream may not be null");
            }
            InputStream instream = getContent();
            int l;
            byte[] tmp = new byte[2048];
            while ((l = instream.read(tmp)) != -1) {
                outstream.write(tmp, 0, l);
            }
        }
    }
}
