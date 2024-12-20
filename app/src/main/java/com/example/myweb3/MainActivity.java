package com.example.myweb3;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.TargetApi;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.webkit.WebResourceRequest;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {
    String[] sites = { "Бурдж-Халифа", "Башня свободы", "Шанхайская башня", "Бурдж-эль-Араб", "Башни Петронас"};
  //  TextView selection=findViewById(R.id.textView);


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Spinner spin=findViewById(R.id.spinner);
        WebView webV=findViewById(R.id.Web);
        webV.getSettings().setJavaScriptEnabled(true);

        WebViewClient webViewClient=new WebViewClient() {
            @SuppressWarnings("deprecation") @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                view.loadUrl(url);
                return true;
            }

            @TargetApi(Build.VERSION_CODES.N) @Override
            public boolean shouldOverrideUrlLoading(WebView view, WebResourceRequest request) {
                view.loadUrl(request.getUrl().toString());
                return true;
            }

        };
        webV.setWebViewClient(webViewClient);

        ArrayAdapter<String> adapter=new ArrayAdapter(this, android.R.layout.simple_spinner_item,sites);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spin.setAdapter(adapter);
        AdapterView.OnItemSelectedListener itemSelectedListener=new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
              String item=(String) adapterView.getItemAtPosition(i);

              if (i==0) webV.loadUrl("https://wikiway.com/oae/dubay/neboskreb-burdzh-khalifa/");
              if (i==1) webV.loadUrl("https://wikiway.com/usa/new-york/bashnya-svobody/");
              if (i==2) webV.loadUrl("https://www.s-vfu.ru/");
              if (i==3) webV.loadUrl("https://eios.s-vfu.ru");
              if (i==4) webV.loadUrl("https://developer.android.com/develop/ui/views/layout/webapps/webview?hl=ru");
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
            }

        };
        spin.setOnItemSelectedListener(itemSelectedListener);
    }
}