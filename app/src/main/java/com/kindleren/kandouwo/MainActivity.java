package com.kindleren.kandouwo;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;

import com.kindleren.kandouwo.base.BaseActivity;
import com.kindleren.kandouwo.guess.GuessBookNameActivity;
import com.kindleren.kandouwo.search.SearchBookKeywordsActivity;

import roboguice.inject.InjectView;

public class MainActivity extends BaseActivity implements View.OnClickListener {

    @InjectView(R.id.search_book_btn)
    Button searchBookBtn;
    @InjectView(R.id.guess_book_btn)
    Button guessBookBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        searchBookBtn.setOnClickListener(this);
        guessBookBtn.setOnClickListener(this);
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
        if (id == R.id.action_settings) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.search_book_btn:
                Intent intent = new Intent();
                intent.setClass(MainActivity.this, SearchBookKeywordsActivity.class);
                startActivity(intent);
                break;
            case R.id.guess_book_btn:
                Intent guessIntent = new Intent();
                guessIntent.setClass(MainActivity.this, GuessBookNameActivity.class);
                startActivity(guessIntent);
        }
    }
}
