package com.kindleren.kandouwo.login;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.inject.Inject;
import com.kandouwo.model.datarequest.login.LoginRequest;
import com.kandouwo.model.datarequest.login.User;
import com.kindleren.kandouwo.R;
import com.kindleren.kandouwo.base.AbstractModelAsyncTask;
import com.kindleren.kandouwo.base.BaseFragment;

import roboguice.inject.InjectView;

/**
 * Created by foxcoder on 14-9-23.
 */
public class LoginFragment extends BaseFragment implements View.OnClickListener {

    @InjectView(R.id.edit_username)
    private AutoCompleteTextView editTextUsername;
    @InjectView(R.id.edit_password)
    private EditText editTextPassword;
    @InjectView(R.id.btn_login)
    private Button buttonLogin;
    @InjectView(R.id.login_progress)
    private ProgressBar progressBarLogin;
    @Inject
    private LayoutInflater inflater;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_login, container, false);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        buttonLogin.setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.btn_login:
                login();
                break;

        }
    }

    private void login(){
        String userName = editTextUsername.getText().toString().trim();
        String password = editTextPassword.getText().toString();

        if (TextUtils.isEmpty(userName)) {
            editTextUsername.requestFocus();
            Toast.makeText(getActivity(),
                    R.string.login_user_name_is_null, Toast.LENGTH_SHORT)
                    .show();
            return;
        }

        if (TextUtils.isEmpty(password)) {
            editTextPassword.requestFocus();
            Toast.makeText(getActivity(), R.string.login_password_is_null,
                    Toast.LENGTH_SHORT).show();
            return;
        }

        login(userName, password);
    }

    public void login(String username, String password){
        progressBarLogin.setVisibility(View.VISIBLE);
        buttonLogin.setEnabled(false);
        new LoginAsyncTask(username, password).exe();
    }

    private class LoginAsyncTask extends AbstractModelAsyncTask<User> {

        private String userName;
        private String password;

        public LoginAsyncTask(String userName, String password) {
            this.userName = userName;
            this.password = password;
        }

        @Override
        public User doLoadData() throws Exception {
            return new LoginRequest(userName, password).execute();
        }

        @Override
        public void onException(Exception e) throws RuntimeException {
            super.onException(e);
//            onLoginError(e);
        }

        @Override
        public void onSuccess(User user) {
            super.onSuccess(user);
//            onLoginSuccess(user);
        }

        @Override
        public void onFinally() throws RuntimeException {
            super.onFinally();
//            onLoginFinally();
        }
    }
}
