package com.zonghe.one;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ImageView;

import com.bumptech.glide.Glide;

public class BigImageActivity extends AppCompatActivity {
private String ImageSrc;
private ImageView mImageView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_big_image);
        mImageView=findViewById(R.id.big_Image);
       ImageSrc=getIntent().getStringExtra("image");
        Glide.with(this).load(ImageSrc).into(mImageView);

    }
}
