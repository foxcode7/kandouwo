package com.kindleren.kandouwo.base;

import android.app.ProgressDialog;
import android.util.Log;

import com.kindleren.kandouwo.actionbar.robo.RoboFragment;

/**
 * Created by foxcoder on 14-9-18.
 */
public class BaseFragment extends RoboFragment {
    protected ProgressDialog progressDialog;
    private boolean contentShown = false;

    protected String getPageContent() {
        return null;
    }

    protected void showProgressDialog(int res) {
        progressDialog = ProgressDialog.show(getActivity(), "", getString(res));
        progressDialog.setIndeterminate(true);
        progressDialog.setCancelable(true);
        progressDialog.setCanceledOnTouchOutside(false);
    }

    protected void hideProgressDialog() {
        if (progressDialog != null && progressDialog.isShowing() && isAdded()) {
            // fragment销毁重建后，再dismiss dialog会出错；如果销毁时dismiss重建后不方便重新showDialog，直接try-catch吧
            try {
                progressDialog.dismiss();
            } catch (IllegalArgumentException e) {
                Log.e("progressDialog", e.getMessage());
            }
        }
    }

    protected void handleException(Exception exception) {
        // TODO
    }

    protected void onLogin() {

    }

    protected void onLogout() {

    }

    protected void onLoginCanceled() {

    }

    protected void setContentShown(boolean contentShown) {
        this.contentShown = contentShown;

    }

    public boolean isContentShown() {
        return contentShown;
    }
}
