package com.example.administrator.androiddemo.recycleview;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.example.administrator.androiddemo.R;

/**
 * Created by gx on 2018/3/14 0014
 */

public class RecycleViewLearningActivity extends AppCompatActivity implements View.OnClickListener {

    private static final String TAG = "RecycleViewLearning";

    private Button useRecycleViewAchieveListView;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recycleview_learning);

        initView();
        Log.e(TAG,"onCreate() executed");
    }

    @Override
    public void onClick(View v) {
        Intent intent;
        switch (v.getId()){
            case R.id.useRecycleViewAchieveListView:
                intent = new Intent(this,RecycleViewAchieveListViewActivity.class);
                startActivity(intent);
                break;
        }
    }

    private void initView(){
        useRecycleViewAchieveListView = (Button) findViewById(R.id.useRecycleViewAchieveListView);
        useRecycleViewAchieveListView.setOnClickListener(this);
    }
}
