package com.example.myweb3;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.TargetApi;
import android.os.Build;
import android.os.Bundle;
import android.webkit.WebResourceRequest;
import android.webkit.WebView;
import android.webkit.WebSettings;
import android.webkit.WebViewClient;
import android.webkit.WebResourceError;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {
    WebView webV;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        webV = findViewById(R.id.Web);

        webV.getSettings().setJavaScriptEnabled(true);
        webV.getSettings().setDomStorageEnabled(true);
        webV.getSettings().setDatabaseEnabled(true);
        webV.getSettings().setCacheMode(WebSettings.LOAD_DEFAULT);
        webV.getSettings().setAllowFileAccess(true);
        webV.getSettings().setSupportZoom(true);
        webV.getSettings().setBuiltInZoomControls(true);
        webV.getSettings().setDisplayZoomControls(false);
        webV.getSettings().setLoadWithOverviewMode(true); // Для адаптации к экрану
        webV.getSettings().setUseWideViewPort(true);

        WebViewClient webViewClient=new WebViewClient() {
            @Override
            public void onReceivedError(WebView view, WebResourceRequest request, WebResourceError error) {
                super.onReceivedError(view, request, error);
                Toast.makeText(getApplicationContext(), "Ошибка загрузки: " + error.getDescription(), Toast.LENGTH_SHORT).show();
            }

            @TargetApi(Build.VERSION_CODES.N) @Override
            public boolean shouldOverrideUrlLoading(WebView view, WebResourceRequest request) {
                view.loadUrl(request.getUrl().toString());
                return true;
            }

        };

        webV.setWebViewClient(webViewClient);

        webV.loadUrl("https://eios.s-vfu.ru/WebApp/#/Rasp");
    }

    @Override
    public void onBackPressed() {
        if (webV.canGoBack()) {
            webV.goBack();
        } else {
            super.onBackPressed();
        }
    }

}