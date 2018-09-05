package com.example.administrator.myme.gif;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.example.administrator.myme.R;

public class GifAct extends AppCompatActivity {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.gif_view);
        ImageView imageView = findViewById(R.id.img);


        Glide.with( this).load(R.drawable.abc)
                .diskCacheStrategy(DiskCacheStrategy.ALL).into(imageView);



    }
}
