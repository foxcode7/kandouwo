package com.kindleren.kandouwo.camera;

import java.util.ArrayList;

/**
 * Created by foxcoder on 14-10-27.
 */
public class CameraData {
    public static final int CAMERA_DATA_ITEM_COUNT = 30;

    public static ArrayList<String> generateCameraData() {
        final ArrayList<String> data = new ArrayList<String>(CAMERA_DATA_ITEM_COUNT);

        for (int i = 0; i < CAMERA_DATA_ITEM_COUNT; i++) {
            data.add("Camera #");
        }

        return data;
    }
}
