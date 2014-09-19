package com.kindleren.kandouwo.actionbar.robo;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.view.ActionMode;
import android.view.View;

import roboguice.RoboGuice;

/**
 * Created by foxcoder on 14-9-18.
 */
public class RoboFragment extends Fragment {
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        RoboGuice.getInjector(getActionBarActivity()).injectMembersWithoutViews(this);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        RoboGuice.getInjector(getActionBarActivity()).injectViewMembers(this);
    }


    public ActionBarActivity getActionBarActivity() {
        return (ActionBarActivity) getActivity();
    }

    public ActionBar getActionBar() {
        return getActionBarActivity().getSupportActionBar();
    }

    public void startActionMode(ActionMode.Callback callback) {
        getActionBarActivity().startSupportActionMode(callback);
    }

    public void setProgressBarIndeterminateVisibility(boolean visibility) {
        getActionBarActivity().setSupportProgressBarIndeterminateVisibility(visibility);
    }

    public void setTitle(String title) {
        getActionBar().setTitle(title);
    }

    public void setTitle(int resId) {
        getActionBar().setTitle(resId);
    }

    public void invalidateOptionsMenu() {
        getActionBarActivity().supportInvalidateOptionsMenu();
    }
}
