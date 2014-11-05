package com.kindleren.kandouwo;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.kindleren.kandouwo.base.BaseActivity;
import com.kindleren.kandouwo.camera.CameraActivity;
import com.kindleren.kandouwo.home.HomeFragment;
import com.kindleren.kandouwo.hot.HotFragment;
import com.kindleren.kandouwo.search.SearchFragment;
import com.kindleren.kandouwo.user.UserMainFragment;

import java.util.List;

import roboguice.inject.InjectView;

public class MainActivity extends BaseActivity implements View.OnClickListener {
    private static final int[] TAB_IDS = new int[]{R.id.home, R.id.hot, R.id.search, R.id.mine};
    private static final int[] TITLE_STRINGS = new int[]{R.string.main_home, R.string.main_hot, R.string.main_search, R.string.main_mine};
    private static final String FRAGMENT_TAG_PREFIX = "MainActivityFragment_";
    private int currentTabIndex;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        getSupportActionBar().setDisplayHomeAsUpEnabled(false);
        getSupportActionBar().setHomeButtonEnabled(false);
        getSupportActionBar().setDisplayShowCustomEnabled(true);

        for (int id : TAB_IDS) {
            findViewById(id).setOnClickListener(this);
        }
        currentTabIndex = 0;
        changeFragment(0, -1);
        changeTabState(0, -1);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        if (id == R.id.action_kindle_camera) {
            Intent intent = new Intent();
            intent.setClass(this, CameraActivity.class);
            startActivity(intent);
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onClick(View v) {

        int index = -1;
        for (int i = 0; i < TAB_IDS.length; i++) {
            if (v.getId() == TAB_IDS[i]) {
                index = i;
                break;
            }
        }


        if (index != -1) {
            if (index != currentTabIndex) {
                changeTabState(index, currentTabIndex);
                changeFragment(index, currentTabIndex);
                currentTabIndex = index;
            }
        }
    }

    private String genFragmentTag(int index) {
        return FRAGMENT_TAG_PREFIX + index;
    }

    /**
     * 改变底部tab的状态
     * @param newTabIndex 点击后的tab索引
     * @param oldTabIndex 点击前的tab索引
     */
    private void changeTabState(int newTabIndex, int oldTabIndex){
        //更改ActionBar的标题文字
        getSupportActionBar().setTitle(TITLE_STRINGS[newTabIndex]);
        //更改底部Tab的选中状态
        if (oldTabIndex >= 0) {
            findViewById(TAB_IDS[oldTabIndex]).setSelected(false);
        }
        findViewById(TAB_IDS[newTabIndex]).setSelected(true);
    }

    private Fragment createFragment(int index) {
        Bundle args = new Bundle();
        Fragment fragment;
        switch (index) {
            case 0:
                fragment = new HomeFragment();
                break;
            case 1:
                fragment = new HotFragment();
                break;
            case 2:
                fragment = new SearchFragment();
                break;
            case 3:
            default:
                fragment = new UserMainFragment();
        }
        fragment.setArguments(args);
        return fragment;
    }

    private void changeFragment(int newTabIndex, int oldTabIndex){
        Fragment currentFragment = oldTabIndex >= 0 ? getSupportFragmentManager().findFragmentByTag(genFragmentTag(oldTabIndex)) : null;
        Fragment targetFragment = getSupportFragmentManager().findFragmentByTag(genFragmentTag(newTabIndex));
        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
        if (currentFragment != null) {
            ft.detach(currentFragment);
        }
        if (targetFragment == null) {
            targetFragment = createFragment(newTabIndex);
            ft.replace(R.id.main, targetFragment, genFragmentTag(newTabIndex));
        } else {
            ft.attach(targetFragment);
        }
        ft.commitAllowingStateLoss();
        getSupportFragmentManager().executePendingTransactions();
    }

    private long lastBackPressed;
    final Handler handler = new Handler();
    private Toast toast;
    private Runnable runnable = new Runnable() {
        @Override
        public void run() {
            toast.cancel();
        }
    };
    private static final long SHOW_DURATION = 2000l;

    @Override
    public void onBackPressed() {
        if (toast == null) {
            toast = Toast.makeText(this, R.string.exit_message, Toast.LENGTH_SHORT);
        }
        if (System.currentTimeMillis() - lastBackPressed < SHOW_DURATION) {
            toast.cancel();
            handler.removeCallbacks(runnable);
            this.finish();
        } else {
            toast.show();
            lastBackPressed = System.currentTimeMillis();
            handler.postDelayed(runnable, SHOW_DURATION);
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        List<Fragment> fragments = getSupportFragmentManager().getFragments();
        if (fragments != null) {
            for (Fragment fragment : fragments) {
                fragment.onActivityResult(requestCode, resultCode, data);
            }
        }
    }
}
