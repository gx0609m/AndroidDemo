package com.example.administrator.androiddemo.algorithm;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.example.administrator.androiddemo.R;
import com.example.administrator.androiddemo.base.BaseActivity;

/**
 * 排序算法
 * <p>
 * Created by gx on 2018/4/12 0012
 */

public class SortLearningActivity extends BaseActivity implements View.OnClickListener {

    private static final String TAG = "SortLearningActivity";

    private int[] arr = {4, 1, 0, 9, 6, 5, 8, 2, 3, 7};

    private Button bubbleSort; // 冒泡排序
    private Button selectionSort; // 选择排序
    private Button quickSort; // 快速排序

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sort_learning);

        initView();
    }

    /**
     * 冒泡排序 ——— 从小到大排序
     * <p>
     * 原理：比较两个相邻的元素，将值大的元素交换至右端
     */
    private void withBubbleSort() {
        for (int i = 1; i < arr.length; i++) {  // 外层循环控制排序趟数 -- 每趟比较会选出一个最大的数，放在最后
            for (int j = 0; j < arr.length - i; j++) { // 内层循环控制每趟循环的比较次数 -- 由于每趟比较会选出一个最大的数放在最后，因此下一趟的比较次数就会较前一趟 -1；
                if (arr[j] > arr[j + 1]) {
                    int temp = arr[j]; // arr[j] 的值给temp
                    arr[j] = arr[j + 1]; // arr[j+1] 的值给 arr[j]
                    arr[j + 1] = temp; // temp 的值给 arr[j+1]
                }
            }
        }
        for (int i = 0; i < arr.length; i++) {
            Log.e(TAG, "" + arr[i]);
        }
    }

    /**
     * 选择排序
     */
    private void withSelectionSort() {

    }

    /**
     * 快速排序
     */
    private void withQuickSort() {

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.bubbleSort:
                withBubbleSort();
                break;
            case R.id.selectionSort:
                withSelectionSort();
                break;
            case R.id.quickSort:
                withQuickSort();
                break;
        }
    }

    private void initView() {
        bubbleSort = (Button) findViewById(R.id.bubbleSort);
        bubbleSort.setOnClickListener(this);

        selectionSort = (Button) findViewById(R.id.selectionSort);
        selectionSort.setOnClickListener(this);

        quickSort = (Button) findViewById(R.id.quickSort);
        quickSort.setOnClickListener(this);
    }

}
