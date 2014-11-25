package com.kindleren.kandouwo.utils;

import android.content.Context;
import android.content.SharedPreferences;

/**
 * Created by foxcoder on 14-11-25.
 */
public class SetAndGetDataUtils {
    private static SharedPreferences sp;

    @SuppressWarnings("static-access")
    public static void SetData(Context context, String filename, String key,
                               String value) {
        sp = context.getSharedPreferences(filename, context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sp.edit();
        editor.putString(key, value);
        editor.commit();
    }

    @SuppressWarnings("static-access")
    public static String GetData(Context context, String filename, String key) {
        String value = "";
        sp = context.getSharedPreferences(filename, context.MODE_PRIVATE);
        value = sp.getString(key, "");
        return value;
    }
}
