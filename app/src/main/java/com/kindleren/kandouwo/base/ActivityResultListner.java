package com.kindleren.kandouwo.base;

import android.content.Intent;

/**
 * Created by foxcoder on 14-9-17.
 */
public interface ActivityResultListner {
    public void onActivityResult(int requestCode, int resultCode, Intent data);
}

