package com.example.administrator.androiddemo.tips;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.Button;

import com.example.administrator.androiddemo.R;
import com.example.administrator.androiddemo.base.BaseActivity;
import com.example.administrator.androiddemo.tips.toolsnamespace.ToolsNameSpaceLearningActivity;

/**
 * Created by gx on 2018/4/25 0025
 */

public class TipsLearningActivity extends BaseActivity implements View.OnClickListener {

    private Button toolsNameSpace;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tips_learning);

        initView();
    }

    private void initView() {
        toolsNameSpace = (Button) findViewById(R.id.toolsNameSpace);
        toolsNameSpace.setText("ToolsNameSpace~");
        toolsNameSpace.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        Intent intent;
        switch (v.getId()) {
            case R.id.toolsNameSpace:
                intent = new Intent(TipsLearningActivity.this, ToolsNameSpaceLearningActivity.class);
                startActivity(intent);
                break;
        }
    }
}
