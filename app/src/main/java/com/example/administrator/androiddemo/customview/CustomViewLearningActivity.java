package com.example.administrator.androiddemo.customview;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.Button;

import com.example.administrator.androiddemo.R;
import com.example.administrator.androiddemo.base.BaseActivity;
import com.example.administrator.androiddemo.customview.draw.DrawLearningActivity;
import com.example.administrator.androiddemo.customview.layout.LayoutLearningActivity;
import com.example.administrator.androiddemo.customview.layoutInflater.LayoutInflaterLearningActivity;
import com.example.administrator.androiddemo.customview.measure.MeasureLearningActivity;

/**
 * Created by gx on 2018/4/16 0016
 */

public class CustomViewLearningActivity extends BaseActivity implements View.OnClickListener {

    private Button layoutInflater;
    private Button measure;
    private Button layout;
    private Button draw;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_custom_view_learning);

        initView();
    }

    @Override
    public void onClick(View v) {
        Intent intent;
        switch (v.getId()) {
            case R.id.layoutInflater:
                intent = new Intent(CustomViewLearningActivity.this, LayoutInflaterLearningActivity.class);
                startActivity(intent);
                break;
            case R.id.measure:
                intent = new Intent(CustomViewLearningActivity.this, MeasureLearningActivity.class);
                startActivity(intent);
                break;
            case R.id.layout:
                intent = new Intent(CustomViewLearningActivity.this, LayoutLearningActivity.class);
                startActivity(intent);
                break;
            case R.id.draw:
                intent = new Intent(CustomViewLearningActivity.this, DrawLearningActivity.class);
                startActivity(intent);
                break;
        }
    }

    private void initView() {
        layoutInflater = (Button) findViewById(R.id.layoutInflater);
        layoutInflater.setOnClickListener(this);

        measure = (Button) findViewById(R.id.measure);
        measure.setOnClickListener(this);

        layout = (Button) findViewById(R.id.layout);
        layout.setOnClickListener(this);

        draw = (Button) findViewById(R.id.draw);
        draw.setOnClickListener(this);
    }
}
