package com.example.administrator.androiddemo.view.customview;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.Toast;

import com.example.administrator.androiddemo.R;
import com.example.administrator.androiddemo.base.BaseActivity;

/**
 * Created by gx on 2018/5/24 0024
 */

public class CustomViewLearningActivity extends BaseActivity implements View.OnClickListener {

    private CircleView circleView;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_custom_view_learning);

        initView();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.circleView:
                Toast.makeText(this, "You clicked circleView ~", Toast.LENGTH_SHORT).show();
                break;
        }
    }

    private void initView() {
        circleView = (CircleView) findViewById(R.id.circleView);
        circleView.setOnClickListener(this);
    }

}
