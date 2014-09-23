package com.kindleren.kandouwo.login;

import android.os.Bundle;

import com.kindleren.kandouwo.R;
import com.kindleren.kandouwo.base.BaseActivity;

/**
 * Created by foxcoder on 14-9-23.
 */
public class LoginActivity extends BaseActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_base_fragment);

        LoginFragment fragment = new LoginFragment();
        getSupportFragmentManager().beginTransaction().replace(R.id.content, fragment).commitAllowingStateLoss();
    }
}
