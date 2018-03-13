package com.example.administrator.androiddemo.activity;

import android.content.Intent;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.example.administrator.androiddemo.R;

/**
 * Created by gx on 2018/3/8 0008
 */

public class ActivityLearningActivity extends AppCompatActivity implements View.OnClickListener {

    public static final String TAG = "ActivityLearning";

    private Button jumptonext;

    /**
     * activity创建，进栈
     */
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_activity_learning);
        Log.d(TAG,"onCreate()执行了");

        /**
         *  判断用户有没有在onSaveInstanceState()中进行保存操作
         */
        if (savedInstanceState != null) {
            String oldString = savedInstanceState.getString("Activity");
        }

        jumptonext = (Button) findViewById(R.id.jumptonext);
        jumptonext.setOnClickListener(this);
    }

    /**
     * 当前activity未destroy【该activity仍然在栈中，并不是重新创建】,
     * 从其他activity到当前activity时，
     * 执行onRestart-->onStart-->onResume
     */
    @Override
    protected void onRestart() {
        super.onRestart();
        Log.d(TAG,"onRestart()执行了");
    }

    /**
     * 可见，不可交互
     */
    @Override
    protected void onStart() {
        super.onStart();
        Log.d(TAG,"onStart()执行了");
    }

    /**
     * 可见，可交互
     */
    @Override
    protected void onResume() {
        super.onResume();
        Log.d(TAG,"onResume()执行了");
    }

    /**
     * 可见，不可交互
     * 由于下一个Activity在这个方法返回之前不会执行onResume()，所以这个方法中不能进行耗时操作
     */
    @Override
    protected void onPause() {
        super.onPause();
        Log.d(TAG,"onPause()执行了");
    }

    /**
     * 不可见,因此跳转到ActivityLearningActivity2时，
     * 先执行当前activity的onPause()方法【当前activity可见，但不可交互】，
     * 再执行跳转到的activity的onCreate()-->onStart()-->onResume()【下个activity可见，可交互】，
     * 最后在执行当前activty的onStop()方法【当前activity不可见】
     */
    @Override
    protected void onStop() {
        super.onStop();
        Log.d(TAG,"onStop()执行了");
    }

    /**
     * activity销毁，出栈
     */
    @Override
    protected void onDestroy() {
        super.onDestroy();
        Log.d(TAG,"onDestroy()执行了");
    }

    /**
     * 恢复UI状态数据
     * 恢复activity的现场数据，虽然onRestoreInstanceState的方法名字看上去挺像回事，
     * 但实际上onRestoreInstanceState通常并不被开发者用来做“Restore”数据；
     *
     * 此方法的回调时机有点特殊，一般在：
     * “activity被销毁（onDestroy）然后又重新加载这个activity时，在onStart之后还没有onResume时候”
     * 会调用onRestoreInstanceState，
     * 这种情况的发生场景如：android的横竖屏切换时【注意：横竖屏切换时，也算当前activity的意外kill,即也会调用onSaveInstanceState()】
     */
    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
    }

    /**
     * 即将移出栈顶保留UI状态时调用（当前activity被意外kill时杀掉，如果是用户主动销毁，则不调用）
     * 会再当前activity被回收之前调用，可用来处理某些我们需要的数据等（方便在再次打开时恢复，提高用户体验）
     *
     * onSaveInstanceState方法一定是在onStop方法之前调用，但不保证在onPause方法之前或之后调用，
     * 官方源码解释如下：If called, this method will occur beforeonStop.
     * There are no guarantees about whether it will occur before or afteronPause.
     */
    @Override
    public void onSaveInstanceState(Bundle outState, PersistableBundle outPersistentState) {
        /**
         * 如果我们覆写onSaveInstanceState()方法, 一般会在第一行代码中调用该方法的默认实现:super.onSaveInstanceState(outState);
         * 因为此方法的默认实现会自动保存activity中的某些状态数据, 比如activity中各种UI控件的状态，
         * android应用框架中定义的几乎所有UI控件都恰当的实现了onSaveInstanceState()方法,因此当activity被摧毁和重建时, 这些UI控件会自动保存和恢
         * 复状态数据，开发者只需要为这些控件指定一个唯一的ID(通过设置android:id属性即可), 剩余的事情就可以自动完成了.如果没有为控件指定ID,
         * 则这个控件就不会进行自动的数据保存和恢复操作。（有id的同时，要获取到焦点）
         */
        super.onSaveInstanceState(outState, outPersistentState);
        String string = "activity 被系统回收了怎么办？";
        outState.putString("Activity", string);
    }

    @Override
    public void onClick(View v) {
        Intent intent;
        switch (v.getId()){
            case R.id.jumptonext:
                intent = new Intent(ActivityLearningActivity.this,ActivityLearningActivity2.class);
                startActivity(intent);
                break;
        }
    }
}
