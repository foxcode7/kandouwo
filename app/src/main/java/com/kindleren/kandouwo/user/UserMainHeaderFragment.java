package com.kindleren.kandouwo.user;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.google.inject.Inject;
import com.kandouwo.model.datarequest.login.User;
import com.kindleren.kandouwo.R;
import com.kindleren.kandouwo.base.BaseFragment;
import com.kindleren.kandouwo.login.LoginActivity;
import com.kindleren.kandouwo.login.LoginFragment;
import com.squareup.picasso.Picasso;

/**
 * Created by foxcoder on 14-9-23.
 */
public class UserMainHeaderFragment extends BaseFragment implements View.OnClickListener {
    @Inject
    private LayoutInflater inflater;
    private Picasso picasso;

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
            startActivityForResult(intent, 1);
            return;
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(resultCode == Activity.RESULT_OK) {
            User user = (User) data.getExtras().get(LoginFragment.USER_INFO);
            showUserInfo(user);
        }
    }

    private void showUserInfo(User user){
        Holder holder = new Holder();
        holder.userIcon = (ImageView) getView().findViewById(R.id.user_icon);
        holder.userName = (TextView) getView().findViewById(R.id.user_name);
        holder.score = (TextView) getView().findViewById(R.id.score);
        holder.kdou = (TextView) getView().findViewById(R.id.kdou);
        holder.userInfo = (LinearLayout) getView().findViewById(R.id.user_info);
        holder.userLoginBtn = (Button) getView().findViewById(R.id.user_login_btn);

        holder.userLoginBtn.setVisibility(View.GONE);
        holder.userInfo.setVisibility(View.VISIBLE);
        holder.userIcon.setVisibility(View.VISIBLE);

        holder.userName.setText(user.getNickname());
        holder.score.setText(String.valueOf(user.getKindle_dou()));
        holder.kdou.setText(String.valueOf(user.getKindle_dou()));
        picasso.with(getActivity()).load(user.getThumbnail()).into(holder.userIcon);

    }

    public class Holder {
        ImageView userIcon;
        TextView userName;
        TextView score;
        TextView kdou;
        LinearLayout userInfo;
        Button userLoginBtn;
    }
}
