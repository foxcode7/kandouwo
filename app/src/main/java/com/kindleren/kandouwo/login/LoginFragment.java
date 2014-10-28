package com.kindleren.kandouwo.login;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.inject.Inject;
import com.google.inject.name.Named;
import com.kandouwo.model.datarequest.login.LoginRequest;
import com.kandouwo.model.datarequest.login.User;
import com.kindleren.kandouwo.R;
import com.kindleren.kandouwo.base.AbstractModelAsyncTask;
import com.kindleren.kandouwo.base.BaseAuthenticatedFragment;
import com.kindleren.kandouwo.base.BaseFragment;

import roboguice.inject.InjectView;

/**
 * Created by foxcoder on 14-9-23.
 */
public class LoginFragment extends BaseAuthenticatedFragment implements View.OnClickListener {
    public final static String USER_INFO = "user_info";

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
    @Inject
    @Named("normal_user")
    private UserConfigController mUserConfigController;

    private User user;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_login, container, false);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        buttonLogin.setOnClickListener(this);
        initEditText();
    }

    private void initEditText() {

        editTextUsername.setText(mUserConfigController.getLastUser());
        editTextUsername.setSelection(mUserConfigController.getLastUser()
                .length());

        if (!TextUtils.isEmpty(mUserConfigController.getLastUser())) {
            editTextPassword.requestFocus();
        }

        editTextPassword.setText("");

        String[] account = mUserConfigController.fetchUser().split(
                mUserConfigController.USERS_SPLIT_FLAG);

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(
                getActivity().getApplicationContext(),
                R.layout.listitem_username, account);

        editTextUsername.setAdapter(adapter);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.btn_login:
                login();
                break;

        }
    }

    @Override
    protected void onLogin() {
        Intent intent = new Intent();
        Bundle args = new Bundle();
        args.putSerializable(USER_INFO, user);
        intent.putExtras(args);
        getActivity().setResult(Activity.RESULT_OK, intent);
        getActivity().finish();
    }

    @Override
    protected void onLogout() {
        if (progressBarLogin != null) {
            progressBarLogin.setVisibility(View.GONE);
        }
        if (buttonLogin != null) {
            buttonLogin.setEnabled(true);
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

    private void onLoginSuccess(User user) {
        if (progressBarLogin != null) {
            progressBarLogin.setVisibility(View.GONE);
        }
        this.user = user;
        userCenter.login(user);

        if (editTextUsername != null) {
            String username = editTextUsername.getText().toString().trim();
            mUserConfigController.storeUser(username);
            mUserConfigController.setLastUser(username);
        }
    }

    private void onLoginError(User user) {
        Toast.makeText(getActivity(), user.getMsg(), Toast.LENGTH_SHORT).show();
    }

    private void onLoginFinally() {
        if (progressBarLogin != null) {
            progressBarLogin.setVisibility(View.GONE);
        }
        if (buttonLogin != null) {
            buttonLogin.setEnabled(true);
        }
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
            if("0".equals(user.getStatus())){
                onLoginError(user);
            } else if("1".equals(user.getStatus())){
                onLoginSuccess(user);
            }

        }

        @Override
        public void onFinally() throws RuntimeException {
            super.onFinally();
            onLoginFinally();
        }
    }
}
