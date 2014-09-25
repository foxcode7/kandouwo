package com.kindleren.kandouwo.user;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.google.inject.Inject;
import com.kandouwo.model.datarequest.login.User;
import com.kindleren.kandouwo.R;
import com.kindleren.kandouwo.base.BaseFragment;
import com.kindleren.kandouwo.login.LoginActivity;
import com.kindleren.kandouwo.login.LoginFragment;

/**
 * Created by foxcoder on 14-9-23.
 */
public class UserMainHeaderFragment extends BaseFragment implements View.OnClickListener {
    @Inject
    private LayoutInflater inflater;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_user_main_header, container, false);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        if(getView() != null) {
            Button userLoginBtn = (Button) getView().findViewById(R.id.user_login_btn);
            userLoginBtn.setOnClickListener(this);
        }
    }

    @Override
    public void onClick(View v) {
        if(v.getId() == R.id.user_login_btn){
            Intent intent = new Intent();
            intent.setClass(getActivity(), LoginActivity.class);
            startActivityForResult(intent, 0);
            return;
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(resultCode == getActivity().RESULT_OK){
            User user = (User) data.getSerializableExtra(LoginFragment.USER_INFO);
            int a =0;
        }
    }
}
