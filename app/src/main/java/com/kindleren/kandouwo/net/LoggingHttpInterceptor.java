package com.kindleren.kandouwo.net;

import com.kindleren.kandouwo.utils.IOUtils;

import org.apache.http.*;
import org.apache.http.entity.BufferedHttpEntity;
import org.apache.http.protocol.HttpContext;
import roboguice.util.Ln;

import java.io.IOException;
import java.io.InputStream;

/**
 * Created by IntelliJ IDEA. User: liang Date: 11-1-30 To change this template
 * use File | Settings | File Templates.
 */
public class LoggingHttpInterceptor implements HttpRequestInterceptor,
        HttpResponseInterceptor {

    @Override
    public void process(HttpRequest request, HttpContext context)
            throws HttpException, IOException {
        if (Ln.isDebugEnabled()) {
            // print out the request line and header items
            Ln.d(request.getRequestLine());
            Header[] arrayOfHeader = request.getAllHeaders();
            for (Header header : arrayOfHeader) {
                Ln.d(header);
            }

            try {
                if ((request instanceof HttpEntityEnclosingRequest)) {
                    final HttpEntityEnclosingRequest enclosingRequest = (HttpEntityEnclosingRequest) request;
                    if (enclosingRequest.getEntity() != null) {
                        HttpEntity entity;
                        if (enclosingRequest.getEntity().isRepeatable()) {
                            entity = enclosingRequest.getEntity();
                        } else {
                            entity = new BufferedHttpEntity(enclosingRequest.getEntity());
                            enclosingRequest.setEntity(entity);
                        }

                        InputStream inputStream = null;
                        try {
                            inputStream = entity.getContent();
                            Ln.d(inputStream);
                        } finally {
                            IOUtils.close(inputStream);
                        }
                    }
                }
            } catch (Exception e) {
                //ignored
            }
        }

    }

    @Override
    public void process(HttpResponse httpResponse, HttpContext httpContext)
            throws HttpException, IOException {
        if (Ln.isDebugEnabled()) {
            Ln.d(httpResponse.getStatusLine());
            Header[] arrayOfHeader = httpResponse.getAllHeaders();
            for (Header header : arrayOfHeader) {
                Ln.d(header);
            }
            final HttpEntity entity = httpResponse.getEntity();
            try {
                if (entity != null && entity.getContentType() != null) {
                    for (HeaderElement element : entity.getContentType()
                            .getElements()) {
                        if (element.getName().equalsIgnoreCase("application/json")) {
                            final HttpEntity repeatableEntity;
                            if (entity.isRepeatable()) {
                                repeatableEntity = entity;
                            } else {
                                repeatableEntity = new BufferedHttpEntity(entity);
                                httpResponse.setEntity(repeatableEntity);
                            }
                            InputStream inputStream = null;
                            try {
                                inputStream = repeatableEntity.getContent();
                                Ln.d(inputStream);
                            } finally {
                                IOUtils.close(inputStream);
                            }
                            return;
                        }
                    }
                }
            } catch (Exception e) {
                // ignored
            }
        }
    }
}
