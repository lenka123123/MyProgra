package com.example.administrator.myme.kuanzhao;

import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Picture;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.administrator.myme.R;

public class KuaizhaoAct extends Activity {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.kuang_zhao);
        Button button = findViewById(R.id.button);

        final ImageView imageView = findViewById(R.id.img);
        final WebView webView = findViewById(R.id.webview);
        webView.setDrawingCacheEnabled(true);
        webView.loadUrl("https://www.sina.com.cn/");
        WebSettings settings = webView.getSettings();
        // 如果访问的页面中要与Javascript交互，则webview必须设置支持Javascript
        settings.setJavaScriptEnabled(true);
        webView.setWebViewClient(new WebViewClient() {
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                view.loadUrl(url);
                return true;
            }
        });

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                imageView.setImageBitmap(captureWebView(webView));
                webView.setVisibility(View.GONE);
            }
        });

        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(KuaizhaoAct.this, "imageView点击", Toast.LENGTH_LONG).show();
            }
        });


    }


    /**
     * 截取webView快照(webView加载的整个内容的大小)
     *
     * @param webView
     * @return
     */
    private Bitmap captureWebView(WebView webView) {
        Picture snapShot = webView.capturePicture();
        Bitmap bmp = Bitmap.createBitmap(snapShot.getWidth(), snapShot.getHeight(), Bitmap.Config.RGB_565);
        Canvas canvas = new Canvas(bmp);
        snapShot.draw(canvas);
        return bmp;
    }
}
