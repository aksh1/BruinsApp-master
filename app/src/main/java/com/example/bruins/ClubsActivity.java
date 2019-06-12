package com.example.bruins;

import android.app.Activity;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.webkit.WebSettings;
import android.webkit.WebView;

import java.util.Objects;

public class ClubsActivity extends AppCompatActivity {

    WebView mWebView;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_clubs);

        setup();


        loadWebPage();

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        goHome();
        return true;
    }

    public void goHome() {
        Intent intent1 = new Intent(getApplicationContext(), MainActivity.class);
        startActivity(intent1);
    }

    public void loadWebPage() {
        WebSettings webSettings = mWebView.getSettings();
        webSettings.setJavaScriptEnabled(true);
        mWebView.loadUrl("https://docs.google.com/spreadsheets/d/e/2PACX-1vTGZ0qvwFJy1vKAS-5LWjIV5S4_-Vf8HiugGk9n7cjd83xX1RqbUBfOLf557wZs72uS1Ylr1U9oVW7d/pubhtml");

    }

    public void setup() {

        Toolbar toolbar = findViewById(R.id.toolbar);

        setSupportActionBar(toolbar);

        toolbar.setTitle("Clubs");

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(true);
        }

        mWebView = (WebView) findViewById(R.id.webView);


    }


}
