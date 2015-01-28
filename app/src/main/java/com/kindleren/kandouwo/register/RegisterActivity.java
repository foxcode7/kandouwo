package com.kindleren.kandouwo.register;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.inject.Inject;
import com.google.inject.name.Named;
import com.kandouwo.model.datarequest.login.User;
import com.kandouwo.model.datarequest.register.RegisterRequest;
import com.kindleren.kandouwo.R;
import com.kindleren.kandouwo.base.AbstractModelAsyncTask;
import com.kindleren.kandouwo.base.BaseAuthenticatedActivity;
import com.kindleren.kandouwo.common.config.BaseConfig;
import com.kindleren.kandouwo.login.UserConfigController;

import roboguice.inject.InjectView;

/**
 * Created by foxcoder on 14-12-18.
 */
public class RegisterActivity extends BaseAuthenticatedActivity implements View.OnClickListener {

    @InjectView(R.id.edit_email)
    private AutoCompleteTextView editTextEmail;
    @InjectView(R.id.edit_password)
    private EditText editTextPassword;
    @InjectView(R.id.confirm_password)
    private EditText editConfirmPassword;
    @InjectView(R.id.btn_register)
    private Button buttonRegister;
    @InjectView(R.id.register_progress)
    private ProgressBar progressBarRegister;
    @Inject
    @Named("normal_user")
    private UserConfigController mUserConfigController;

    private User user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        buttonRegister.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.btn_register:
                register();
                break;
        }
    }

    private void register(){
        String email = editTextEmail.getText().toString().trim();
        String password = editTextPassword.getText().toString();
        String passwordConfirm = editConfirmPassword.getText().toString();

        if (TextUtils.isEmpty(email)) {
            editTextEmail.requestFocus();
            Toast.makeText(this,
                    R.string.login_user_name_is_null, Toast.LENGTH_SHORT)
                    .show();
            return;
        }

        if (TextUtils.isEmpty(password)) {
            editTextPassword.requestFocus();
            Toast.makeText(this, R.string.login_password_is_null,
                    Toast.LENGTH_SHORT).show();
            return;
        }

        if (TextUtils.isEmpty(passwordConfirm)) {
            editConfirmPassword.requestFocus();
            Toast.makeText(this, R.string.register_confirm_password_is_null,
                    Toast.LENGTH_SHORT).show();
            return;
        }

        if(!TextUtils.equals(password, passwordConfirm)) {
            editConfirmPassword.requestFocus();
            Toast.makeText(this, R.string.regitster_confirm_false,
                    Toast.LENGTH_SHORT).show();
            return;
        }

        register(email, password);
    }

    private void register(String email, String password) {
        progressBarRegister.setVisibility(View.VISIBLE);
        buttonRegister.setEnabled(false);
        new RegisterAsyncTask(email, password).exe();
    }

    private void onRegisterSuccess(User user) {
        this.user = user;
        userCenter.login(user);

        if (editTextEmail != null) {
            String username = editTextEmail.getText().toString().trim();
            mUserConfigController.storeUser(username);
            mUserConfigController.setLastUser(username);
        }
    }

    private void onRegisterError(User user) {
        Toast.makeText(this, user.getMsg(), Toast.LENGTH_SHORT).show();
    }

    private void onRegisterFinally() {
        if (progressBarRegister != null) {
            progressBarRegister.setVisibility(View.GONE);
        }
        if (buttonRegister != null) {
            buttonRegister.setEnabled(true);
        }
    }

    private class RegisterAsyncTask extends AbstractModelAsyncTask<User> {
        private String email;
        private String password;

        public RegisterAsyncTask(String email, String password) {
            this.email = email;
            this.password = password;
        }

        @Override
        protected User doLoadData() throws Exception {
            return new RegisterRequest(email, password, BaseConfig.deviceId).execute();
        }

        @Override
        public void onException(Exception e) throws RuntimeException {
            super.onException(e);
        }

        @Override
        public void onSuccess(User user) {
            super.onSuccess(user);
            if("0".equals(user.getStatus())){
                onRegisterError(user);
            } else if("1".equals(user.getStatus())){
                onRegisterSuccess(user);
            }

        }

        @Override
        public void onFinally() throws RuntimeException {
            super.onFinally();
            onRegisterFinally();
        }
    }
}
