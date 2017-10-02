package com.example.user.nairaland;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.GestureDetector;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.View;
import android.webkit.WebChromeClient;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ProgressBar;
import android.support.v4.widget.SwipeRefreshLayout;

public class MainActivity extends AppCompatActivity {

    WebView wv;
    SwipeRefreshLayout mySwipeRefresh;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mySwipeRefresh = (SwipeRefreshLayout) findViewById(R.id.swipeRefresh);
        wv = (WebView) findViewById(R.id.webView);
        //Allow clicks to load via webview
        wv.setWebViewClient(new WVClient());


        wv.getSettings().setJavaScriptEnabled(true);
        wv.getSettings().setBuiltInZoomControls(true);
        wv.getSettings().setDisplayZoomControls(false);
        wv.loadUrl("http://www.nairaland.com");


        mySwipeRefresh.setOnRefreshListener(
                new SwipeRefreshLayout.OnRefreshListener() {
                    @Override
                    public void onRefresh() {
                        wv.reload();
                    }
                }
        );
      //  if (mySwipeRefresh.isRefreshing()) {
        //    mySwipeRefresh.setRefreshing(false);
       // }
    }


    @Override
    public boolean onKeyDown(int KeyCode, KeyEvent event) {
        if ((KeyCode == KeyEvent.KEYCODE_BACK) && wv.canGoBack()) {
            wv.goBack();
            return true;
        }
        return super.onKeyDown(KeyCode, event);
    }

    private class WVClient extends WebViewClient {
        @Override
    public boolean shouldOverrideUrlLoading(WebView view, String url) {
        if (Uri.parse(url).getHost().equals("www.nairaland.com")) {
            // This is my website, so do not overide; let webview load
            return false;
            }
        Intent intent = new Intent(Intent.ACTION_DEFAULT, Uri.parse(url));
        startActivity(intent);
        //This isnt my site open with default browser
        return true;
    }
        @Override
        public void onPageFinished(WebView view,String url){
            mySwipeRefresh.setRefreshing(false);
        }
    }
}



