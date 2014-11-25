package com.kindleren.kandouwo.settings;

import android.os.Bundle;

import com.kindleren.kandouwo.R;
import com.kindleren.kandouwo.base.BaseActivity;

/**
 * Created by foxcoder on 14-11-24.
 */
public class SettingsActivity extends BaseActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_base_fragment);

        SettingsFragment settingsFragment = new SettingsFragment();
        getSupportFragmentManager().beginTransaction().replace(R.id.content, settingsFragment).commitAllowingStateLoss();
    }
}
