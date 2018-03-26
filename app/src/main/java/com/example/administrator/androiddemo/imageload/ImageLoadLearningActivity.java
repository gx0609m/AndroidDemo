package com.example.administrator.androiddemo.imageload;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import com.example.administrator.androiddemo.R;
import com.example.administrator.androiddemo.imageload.fresco.FrescoLearningActivity;
import com.example.administrator.androiddemo.imageload.glide.GlideLearningActivity;
import com.example.administrator.androiddemo.imageload.picasso.PicassoLearningActivity;
import com.example.administrator.androiddemo.imageload.universalimageloader.UniversalImageLoaderLearningActivity;

/**
 * Created by gx on 2018/3/15 0015
 */

public class ImageLoadLearningActivity extends AppCompatActivity implements View.OnClickListener {

    private Button fresco;
    private Button glide;
    private Button picasso;
    private Button imageLoader;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_image_load_learning);

        initView();
    }

    private void initView() {
        fresco = (Button) findViewById(R.id.fresco);
        fresco.setOnClickListener(this);

        glide = (Button) findViewById(R.id.glide);
        glide.setOnClickListener(this);

        picasso = (Button) findViewById(R.id.picasso);
        picasso.setOnClickListener(this);

        imageLoader = (Button) findViewById(R.id.imageLoader);
        imageLoader.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        Intent intent;
        switch (v.getId()) {
            case R.id.fresco:
                intent = new Intent(this, FrescoLearningActivity.class);
                startActivity(intent);
                break;
            case R.id.glide:
                intent = new Intent(this, GlideLearningActivity.class);
                startActivity(intent);
                break;
            case R.id.picasso:
                intent = new Intent(this, PicassoLearningActivity.class);
                startActivity(intent);
                break;
            case R.id.imageLoader:
                intent = new Intent(this, UniversalImageLoaderLearningActivity.class);
                startActivity(intent);
                break;
        }
    }
}
