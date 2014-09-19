package com.kindleren.kandouwo.utils;

import android.database.Cursor;

import java.io.ByteArrayOutputStream;
import java.io.Closeable;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

/**
 * Created by foxcoder on 14-9-19.
 */

public class IOUtils {

    public static final int DEFAULT_BUFFER_SIZE = 4096;

    public static void close(Cursor cursor) {
        if (cursor != null)
            cursor.close();
    }

    public static void close(Closeable closeable) {
        if (closeable != null) {
            try {
                closeable.close();
            } catch (IOException localIOException) {
                //
            }

        }
    }

    public static int copy(InputStream inputStream, OutputStream outputStream)
            throws IOException {
        long l = copyLarge(inputStream, outputStream);
        int i;
        if (l > 2147483647L) {
            i = -1;
        } else {
            i = (int) l;
        }
        return i;
    }

    public static long copyLarge(InputStream inputStream,
                                 OutputStream outputStream) throws IOException {
        byte[] buffer = new byte[DEFAULT_BUFFER_SIZE];
        long len = 0L;
        int i = 0;
        while ((i = inputStream.read(buffer)) > 0) {
            outputStream.write(buffer, 0, i);
            len += i;
        }
        return len;
    }

    public static byte[] toByteArray(InputStream inputStream)
            throws IOException {
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        copy(inputStream, outputStream);
        return outputStream.toByteArray();
    }

    public static void copyString(String input, String outPath)
            throws IOException {
        FileOutputStream fos = null;
        try {
            fos = new FileOutputStream(outPath);
            fos.write(input.getBytes());
        } finally {
            close(fos);
        }
    }
}