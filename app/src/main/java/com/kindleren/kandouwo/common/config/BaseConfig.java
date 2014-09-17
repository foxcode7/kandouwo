package com.kindleren.kandouwo.common.config;

import android.content.Context;
import android.util.DisplayMetrics;

/**
 * Created by foxcoder on 14-9-17.
 */
public class BaseConfig {

    public static int width;
    public static int height;
    public static float density;
    public static int densityDpi;
    private static boolean displayInited;

    public static void initDisplay(Context context) {
        if ((!displayInited) && (context.getResources() != null)) {
            DisplayMetrics metrics = context.getResources().getDisplayMetrics();
            width = metrics.widthPixels;
            height = metrics.heightPixels;
            density = metrics.density;
            densityDpi = metrics.densityDpi;
            displayInited = true;
        }
    }
}
