package com.example.Brilliance;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.appcompat.widget.PopupMenu;
import androidx.appcompat.widget.Toolbar;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.annotation.TargetApi;
import android.net.NetworkInfo;
import android.os.Build;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.webkit.WebResourceRequest;
import android.webkit.WebView;
import android.webkit.WebSettings;
import android.webkit.WebViewClient;
import android.webkit.WebResourceError;
import android.widget.Toast;



public class MainActivity extends AppCompatActivity {
    WebView webV;
    View view;
    PopupMenu popupMenu;
    Intent intent_settings;
    Intent intent_notes;
    Intent intent_about;

    public boolean isOnline() {
        ConnectivityManager cm = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo netInfo = cm.getActiveNetworkInfo();
        if (netInfo != null && netInfo.isConnectedOrConnecting()) {
            return true;
        } else {
            return false;
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        SharedPreferences sharedPreferences = getSharedPreferences("MyPrefs", MODE_PRIVATE);
        if(sharedPreferences.getBoolean("nightMode", false)){
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES);
        }else{
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
        }
        setContentView(R.layout.activity_main);
        webV = findViewById(R.id.Web);
        intent_settings = new Intent(this, SettingsActivity.class);
        intent_about = new Intent(this, AboutActivity.class);
        intent_notes = new Intent(this, NotesActivity.class);

        webV.getSettings().setJavaScriptEnabled(true);
        webV.getSettings().setDomStorageEnabled(true);
        webV.getSettings().setDatabaseEnabled(true);
        webV.getSettings().setAllowFileAccess(true);
        webV.getSettings().setSupportZoom(true);
        webV.getSettings().setBuiltInZoomControls(true);
        webV.getSettings().setDisplayZoomControls(false);
        webV.getSettings().setLoadWithOverviewMode(true);
        webV.getSettings().setUseWideViewPort(true);


        if(sharedPreferences.getBoolean("cacheSaving", false)){
            webV.getSettings().setCacheMode(WebSettings.LOAD_DEFAULT);
        }else{
            webV.getSettings().setCacheMode(WebSettings.LOAD_NO_CACHE);
            webV.clearCache(true);
        }


        WebViewClient webViewClient=new WebViewClient() {
            @Override
            public void onReceivedError(WebView view, WebResourceRequest request, WebResourceError error) {
                super.onReceivedError(view, request, error);
                //Toast.makeText(getApplicationContext(), "Ошибка загрузки: " + error.getDescription(), Toast.LENGTH_SHORT).show();
            }

            @TargetApi(Build.VERSION_CODES.N) @Override
            public boolean shouldOverrideUrlLoading(WebView view, WebResourceRequest request) {
                view.loadUrl(request.getUrl().toString());
                return true;
            }

        };

        webV.setWebViewClient(webViewClient);
        webV.loadUrl("https://eios.s-vfu.ru/WebApp/#/Rasp");

        Toolbar toolbar = findViewById(R.id.toolbar2);
        setSupportActionBar(toolbar);

        if (getSupportActionBar() != null){
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowHomeEnabled(true);
        }

    }

    @Override
    public void onBackPressed() {
        if (webV.canGoBack()) {
            webV.goBack();
        }else{
            super.onBackPressed();
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            showPopupMenu();
        }
        return super.onOptionsItemSelected(item);
    }

    private void showPopupMenu() {
        view = findViewById(R.id.toolbar2);
        popupMenu = new PopupMenu(this, view);
        popupMenu.getMenuInflater().inflate(R.menu.popup_menu, popupMenu.getMenu());
        popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.settings:
                        startActivity(intent_settings);
                        return true;
                    case R.id.notes:
                        startActivity(intent_notes);
                        return true;
                    case R.id.about:
                        startActivity(intent_about);
                        return true;
                    default:
                        return false;
                }
            }
        });
        popupMenu.show();
    }



}