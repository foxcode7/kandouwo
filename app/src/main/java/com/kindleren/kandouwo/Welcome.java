package com.kindleren.kandouwo;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

import com.kindleren.kandouwo.base.BaseActivity;

import java.lang.ref.WeakReference;

/**
 * Created by foxcoder on 14-9-16.
 */
public class Welcome extends BaseActivity {
    private Handler handler;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        handler = new Handler();

        handler.postDelayed(new JumpRunnable(this), 2000l);
    }

    private void jump(){
        startActivity(new Intent(this, MainActivity.class));
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (handler != null) {
            handler.removeCallbacksAndMessages(null);
            handler = null;
        }
    }

    private static class JumpRunnable implements Runnable {
        final WeakReference<Welcome> activityRef;

        private JumpRunnable(Welcome activity) {
            this.activityRef = new WeakReference<Welcome>(activity);
        }

        @Override
        public void run() {
            Welcome activity = activityRef.get();
            if (activity != null) {
                activity.jump();
            }
        }
    }
}
