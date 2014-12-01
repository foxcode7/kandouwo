package com.kindleren.kandouwo.user;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.inject.Inject;
import com.kindleren.kandouwo.R;
import com.kindleren.kandouwo.base.BaseFragment;
import com.kindleren.kandouwo.camera.CameraActivity;
import com.kindleren.kandouwo.guess.GuessBookNameActivity;
import com.kindleren.kandouwo.settings.SettingsActivity;

import java.util.List;

/**
 * Created by foxcoder on 14-9-22.
 */
public class UserMainFragment extends BaseFragment implements View.OnClickListener{

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

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        view.findViewById(R.id.user_settings).setOnClickListener(this);
        view.findViewById(R.id.user_settings_game).setOnClickListener(this);
        view.findViewById(R.id.user_settings_photo).setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        Intent intent;
        switch(v.getId()){
            case R.id.user_settings:
                intent = new Intent(getActivity(), SettingsActivity.class);
                startActivity(intent);
                break;
            case R.id.user_settings_game:
                intent = new Intent(getActivity(), GuessBookNameActivity.class);
                startActivity(intent);
                break;
            case R.id.user_settings_photo:
                intent = new Intent(getActivity(), CameraActivity.class);
                startActivity(intent);
                break;
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        List<Fragment> fragments = getChildFragmentManager().getFragments();
        if (fragments != null) {
            for (Fragment fragment : fragments) {
                fragment.onActivityResult(requestCode, resultCode, data);
            }
        }
    }
}
