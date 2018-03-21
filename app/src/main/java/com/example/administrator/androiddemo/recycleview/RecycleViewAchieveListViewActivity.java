package com.example.administrator.androiddemo.recycleview;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.example.administrator.androiddemo.R;

import java.util.ArrayList;
import java.util.List;

/**
 * RecycleView实现ListView的效果
 * Created by gx on 2018/3/14 0014
 */

public class RecycleViewAchieveListViewActivity extends AppCompatActivity {

    private static final String TAG = "RecycleViewAchieve";

    // 使用RecycleView 要单独引入recyclerview-v7包
    private RecyclerView recyclerView;

    private MyRecycleViewAdapter myRecycleViewAdapter = new MyRecycleViewAdapter();

    private List<String> datas;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recycleview_achieve_listview);

        initData();
        initView();
        Log.e(TAG, "onCreate() executed");
    }

    private void initView() {
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
         *
         *      更多关于RecyclerView的布局、分割线、item增删动画的
         *      可以参考：http://blog.csdn.net/lmj623565791/article/details/45059587
         */
        recyclerView = (RecyclerView) findViewById(R.id.recycleview);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(myRecycleViewAdapter);
        myRecycleViewAdapter.setOnRecyclerViewClickListener(new OnRecyclerViewClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                Toast.makeText(RecycleViewAchieveListViewActivity.this, datas.get(position) + position + " click",
                        Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onItemLongClick() {
                Toast.makeText(RecycleViewAchieveListViewActivity.this, "Long Click 监听方法回调了~", Toast.LENGTH_SHORT).show();
            }
        });
    }

    class MyRecycleViewAdapter extends RecyclerView.Adapter<MyRecycleViewAdapter.MyViewHolder> {

        /**
         * RecyclerView并没有提供Click、Long Click的监听回调方法，
         * （继承一个RecyclerView只必须重写它的：
         * onCreateViewHolder(),
         * onBindViewHolder(),
         * getItemCount(), 三个方法 ）
         * <p>
         * 因此，如果我们想实现这样的监听效果，就需要自己去编码实现，
         * <p>
         * 实现方式有多种，例如：
         * 1.recyclerView.addOnItemTouchListener去监听，然后去判断手势；
         * 2.在Adapter中自己去添加回调；
         * 这里，我们采用后者来实现下
         */

        /**
         * 原本准备用下面的代码，即用内部类形式来写自定义接口，即监听Listener,但是报了
         * “inner classes cannot have static declarations”
         * 这个错误，因此单独写了个类，没有用内部类的形式
         * <p>
         * public interface OnRecyclerViewClickListener{
         *      void onItemClick();
         *      void onItemLongClick();
         * }
         */

        // 定义 自定义接口的全局变量
        private OnRecyclerViewClickListener onRecyclerViewClickListener;

        /**
         * 在adapter中提供注册监听的方法
         *
         * 并通过这个方法，对上面自定义接口的全局变量进行初始化，
         * 这样，我们就可以在这个adapter中的其他地方使用这个全局变量了
         *
         */
        public void setOnRecyclerViewClickListener(OnRecyclerViewClickListener onRecyclerViewClickListener) {
            this.onRecyclerViewClickListener = onRecyclerViewClickListener;
        }

        /**
         * 根据字面意思即可理解 ——— 创建ViewHolder
         * <p>
         * 需要加载RecyclerView的布局，即其中item的布局
         * <p>
         * 主要是构建item布局的
         */
        @Override
        public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            MyViewHolder myViewHolder = new MyViewHolder(LayoutInflater
                    .from(RecycleViewAchieveListViewActivity.this)
                    .inflate(R.layout.item_recycleview_achieve_listview, parent, false));
            return myViewHolder;
        }

        /**
         * 字面意思可以理解为 ——— 绑定什么到ViewHolder
         * <p>
         * 所以，此方法主要是为item布局中的控件设置文字内容、添加监听事件等
         */
        @Override
        public void onBindViewHolder(final MyViewHolder holder, int position) {

            holder.tv.setText(datas.get(position));

            if (onRecyclerViewClickListener != null) { // 如果添加了监听
                holder.tv.setOnClickListener(new View.OnClickListener() { // item布局中的TextView的点击监听事件
                    @Override
                    public void onClick(View v) {
                        /**
                         * 回调我们设置的监听Listener中的方法
                         */
                        int pos = holder.getLayoutPosition();
                        onRecyclerViewClickListener.onItemClick(holder.itemView, pos);
                    }
                });
                /**
                 * 思考下，
                 *      我们完全可以在判断完(onRecyclerViewClickListener != null)后就直接回调，
                 *      为什么要嵌套一个itemView（此处是tv）的监听事件呢？
                 *      为什么要在itemView的监听事件中区回调我们设置的监听方法呢？
                 *
                 * 原因是，
                 *      我们设置的回调方法需要某些参数，而这些参数可以通过itemView的监听事件来提供，
                 *      如果我们不需要参数，或是需要的参数与itemView无关，接可以不用为itemView设置监听；
                 *
                 *      同样的，如果itemView包含其他的控件，如Spinner、Button之类的，
                 *      我们也可以去调用Spinner、Button的监听方法来获取我们想要的参数
                 */
                holder.tv.setOnLongClickListener(new View.OnLongClickListener() { // item布局中的TextView的长点击事件
                    @Override
                    public boolean onLongClick(View v) {
//                        int pos = holder.getLayoutPosition();
                        onRecyclerViewClickListener.onItemLongClick(); // 回调我们设置的监听Listener中的方法
                        return false;
                    }
                });
            }
        }

        @Override
        public int getItemCount() {
            return datas.size();
        }

        /**
         * 这里的ViewHolder与ListView中的差不多，
         * 不同的是，RecyclerView会强制编写ViewHolder
         */
        class MyViewHolder extends RecyclerView.ViewHolder {

            /**
             * item中的子控件，此处表示：
             * item中只有一个子控件；
             * 或是，item中只有这一个子控件需要用到，如设置它的内容，为它添加监听事件等
             */
            TextView tv;

            public MyViewHolder(View view) {
                super(view);
                /**
                 * 将item布局中需要用到的控件 实例化
                 *
                 * 注意不要忘了view.**,不然会报空指针
                 */
//                tv = (TextView) findViewById(R.id.id_num);
                tv = (TextView) view.findViewById(R.id.id_num);
            }
        }
    }

    private void initData() {
        datas = new ArrayList<String>();
        // 注意“ 'A'、'z' ”是int型
        for (int i = 'A'; i < 'z'; i++) {
            datas.add("" + (char) i);
        }
    }
}
