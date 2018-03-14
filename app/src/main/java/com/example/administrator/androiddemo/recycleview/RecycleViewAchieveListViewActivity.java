package com.example.administrator.androiddemo.recycleview;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.util.Log;

import com.example.administrator.androiddemo.R;

/**
 * RecycleView实现ListView的效果
 * Created by gx on 2018/3/14 0014
 */

public class RecycleViewAchieveListViewActivity extends AppCompatActivity{

    private static final String TAG = "RecycleViewAchieve";

    // 使用RecycleView 要单独引入recyclerview-v7包
    private RecyclerView recyclerView;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recycleview_achieve_listview);

        initView();
        Log.e(TAG,"onCreate() executed");
    }
    private void initView(){
        /**
         * RecycleView的使用一般如下：
         *      mRecyclerView = findView(R.id.id_recyclerview);
         *      //设置布局管理器
         *      mRecyclerView.setLayoutManager(layout);
         *      //设置adapter
         *      mRecyclerView.setAdapter(adapter)
         *      //设置Item增加、移除动画
         *      mRecyclerView.setItemAnimator(new DefaultItemAnimator());
         *      //添加分割线
         *      mRecyclerView.addItemDecoration(new DividerItemDecoration(
         *                                  getActivity(), DividerItemDecoration.HORIZONTAL_LIST));
         */
        recyclerView = (RecyclerView) findViewById(R.id.recycleview);
    }
}
