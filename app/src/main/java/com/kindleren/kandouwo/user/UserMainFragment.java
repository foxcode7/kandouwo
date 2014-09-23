package com.kindleren.kandouwo.user;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.inject.Inject;
import com.kindleren.kandouwo.R;
import com.kindleren.kandouwo.base.BaseFragment;

/**
 * Created by foxcoder on 14-9-22.
 */
public class UserMainFragment extends BaseFragment {

    @Inject
    private LayoutInflater inflater;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_user_main, container, false);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if(savedInstanceState == null){
            Fragment fragment = new UserMainHeaderFragment();
            getChildFragmentManager().beginTransaction().replace(R.id.user_main_login_container, fragment).commitAllowingStateLoss();
        }
    }
}
