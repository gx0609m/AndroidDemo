package com.example.administrator.androiddemo.recycleview;

import android.view.View;

/**
 * 自定义回调接口
 * Created by gx on 2018/3/15 0015
 */

public interface OnRecyclerViewClickListener {
    /**
     * 自定义需要的方法，需要的参数
     *
     * 回调方法的参数可根据需要添加，同时因为是在RecyclerView的onBindViewHolder()中回调此方法，
     * 所以要看需要的参数能否在onBindViewHolder()中获取到
     */
    void onItemClick(View view, int position);
    void onItemLongClick();
}
