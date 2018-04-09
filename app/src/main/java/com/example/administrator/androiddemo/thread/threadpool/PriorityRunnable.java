package com.example.administrator.androiddemo.thread.threadpool;

import android.support.annotation.NonNull;

/**
 * Created by gx on 2018/4/9 0009
 */

public abstract class PriorityRunnable implements Runnable, Comparable<PriorityRunnable> {

    /*
     * 创建一个实现Runnable接口的类，并向外提供一个抽象方法供我们实现自定义功能，
     * 并实现Comparable接口，实现这个接口主要就是进行优先级的比较
     */

    private int priority;

    public PriorityRunnable(int priority) {
        if (priority < 0)
            throw new IllegalArgumentException();
        this.priority = priority;
    }

    @Override
    public int compareTo(@NonNull PriorityRunnable o) {
        int my = this.getPriority();
        int other = o.getPriority();
        return my < other ? 1 : my > other ? -1 : 0;
    }

    @Override
    public void run() {
        doSth();
    }

    public abstract void doSth();

    public int getPriority() {
        return priority;
    }
}
