package cn.tx.dongge.pongyouquan;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;

import static cn.tx.dongge.pongyouquan.BitmapSave.saveBitmap;

public class TestActivity extends AppCompatActivity {

    private static final String SD_PATH = "/sdcard/dskqxt/pic/";
    private static final String IN_PATH = "/dskqxt/pic/";
    ImageView imageView;
    ImageView imageView2;
    private Context context;

    String url = "http://b.hiphotos.baidu.com/image/pic/item/7a899e510fb30f24eb4eecc9c195d143ac4b0327.jpg";
    private String path;
    private Button button;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.text_view);
        context = this;
        imageView = (ImageView) findViewById(R.id.imageView);
        imageView2 = (ImageView) findViewById(R.id.imageView2);
        button = (Button) findViewById(R.id.button);


        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                imageView2.setImageBitmap(BitmapSave.read(path));
            }
        });

        MyAsyncTask myAsyncTask = new MyAsyncTask();
        myAsyncTask.execute(url);
    }


    private class MyAsyncTask extends AsyncTask<String, Void, Bitmap> {

        @Override
        protected void onPreExecute() {
            // TODO Auto-generated method stub
            super.onPreExecute();

        }

        @Override
        protected void onPostExecute(Bitmap bitmap) {
            // TODO Auto-generated method stub
            super.onPostExecute(bitmap);
            imageView.setImageBitmap(bitmap);
        }

        @Override
        protected Bitmap doInBackground(String... params) {
            String url = params[0];
            Bitmap bitmap = null;
            try {

                URLConnection connection = new URL(url).openConnection();
                InputStream is = connection.getInputStream();
                BufferedInputStream bis = new BufferedInputStream(is);//封装一下输入流
                bitmap = BitmapFactory.decodeStream(bis);//解析

                path = BitmapSave.saveBitmap(context, bitmap);
                System.out.println("============path=======");
                System.out.println(path);
                is.close();
                bis.close();
            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
            return bitmap;
        }


    }

}