package com.kindleren.kandouwo.guess;

import android.os.Bundle;

import com.kindleren.kandouwo.R;
import com.kindleren.kandouwo.base.BaseActivity;

/**
 * Created by xuezhangbin on 14-9-22.
 */
public class GuessBookNameActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_base_fragment);

        GuessBookNameFragment guessBookNameFragment = new GuessBookNameFragment();
        getSupportFragmentManager().beginTransaction().replace(R.id.content, guessBookNameFragment).commitAllowingStateLoss();
    }
}
