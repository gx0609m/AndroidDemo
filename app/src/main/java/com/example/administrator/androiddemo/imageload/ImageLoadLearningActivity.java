package com.example.administrator.androiddemo.imageload;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

import com.example.administrator.androiddemo.R;
import com.example.administrator.androiddemo.imageload.fresco.FrescoLearningActivity;

/**
 * Created by gx on 2018/3/15 0015
 */

public class ImageLoadLearningActivity extends AppCompatActivity implements View.OnClickListener {

    private Button fresco;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_image_load_learning);

        initView();
    }

    private void initView(){
        fresco = (Button) findViewById(R.id.fresco);
        fresco.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        Intent intent;
        switch (v.getId()){
            case R.id.fresco:
                intent = new Intent(this, FrescoLearningActivity.class);
                startActivity(intent);
                break;
        }
    }
}
