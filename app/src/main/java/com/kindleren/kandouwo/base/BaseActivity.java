package com.kindleren.kandouwo.base;

import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;

import com.kindleren.kandouwo.R;
import com.kindleren.kandouwo.actionbar.robo.RoboActionBarActivity;
import com.kindleren.kandouwo.common.config.BaseConfig;

/**
 * Created by foxcoder on 14-9-17.
 */
public class BaseActivity extends RoboActionBarActivity {
    protected ProgressDialog progressDialog;
    private ActivityResultListner activityResultListner;
    private boolean isActive = false;

    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        setIntent(intent);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            onBackPressed();
            return true;
        } else {
            return super.onOptionsItemSelected(item);
        }
    }

    @Override
    protected void onStart() {
        super.onStart();
        isActive = true;
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    protected String getPageContent() {
        return null;
    }

    @Override
    protected void onStop() {
        super.onStop();
        isActive = false;
    }

    protected void showProgressDialog(int res) {
        if (!isFinishing()) {
            progressDialog = ProgressDialog.show(this, "", getString(res));
            progressDialog.setIndeterminate(true);
            progressDialog.setCancelable(true);
            progressDialog.setCanceledOnTouchOutside(false);
            if (getProgressOnCancelListener() != null) {
                progressDialog.setOnCancelListener(getProgressOnCancelListener());
            }
        }
    }

    protected void hideProgressDialog() {
        if (!isFinishing()) {
            if (progressDialog != null) {
                progressDialog.dismiss();
            }
        }
    }

    protected DialogInterface.OnCancelListener getProgressOnCancelListener() {
        return null;
    }

    /**
     * 在actionbar右边添加一个button
     *
     * @param resId
     * @param listener
     */
    protected void addActionBarRightButton(int resId, View.OnClickListener listener) {
        addActionBarRightButton(getText(resId), listener);
    }

    /**
     * 在actionbar右边添加一个button
     *
     * @param res
     * @param listener
     */
    protected void addActionBarRightButton(CharSequence res, View.OnClickListener listener) {
        final LayoutInflater inflater = getLayoutInflater();
        View v = inflater.inflate(R.layout.actionbar_button, null);
        Button actionBarRightButton = (Button) v.findViewById(R.id.text);
        actionBarRightButton.setText(res);
        actionBarRightButton.setOnClickListener(listener);

        final ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayShowCustomEnabled(true);
        ActionBar.LayoutParams params = new ActionBar.LayoutParams(Gravity.RIGHT);
        actionBar.setCustomView(v, params);

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (activityResultListner != null) {
            activityResultListner.onActivityResult(requestCode, resultCode, data);
        }
    }

    protected boolean isActive() {
        return isActive;
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_MENU) {
            // do nothing
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        BaseConfig.initDisplay(getApplicationContext());
    }

}
