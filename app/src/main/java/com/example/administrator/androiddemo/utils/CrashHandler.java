package com.example.administrator.androiddemo.utils;

import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Environment;
import android.os.Process;
import android.util.Log;

import java.io.File;
import java.io.FileOutputStream;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.io.Writer;
import java.lang.reflect.Field;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.Map;

/**
 * 未捕获异常处理
 * <p>
 * Created by gx on 2018/6/11 0011
 */

public class CrashHandler implements Thread.UncaughtExceptionHandler {

    public static final String TAG = "CrashHandler";

    private Context mContext;
    private static CrashHandler INSTANCE;  // crashHandler单例

    private String LOG_PATH;  // log保存路径
    private Map<String, String> mInfoMap;  // 存储设备信息和异常信息
    private SimpleDateFormat mSimpleDateFormat;

    private CrashHandler() {
        mInfoMap = new LinkedHashMap<>();
        mSimpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    }

    /**
     * DCL 获取CrashHandler实例
     */
    public static CrashHandler getInstance() {
        if (INSTANCE == null) {
            synchronized (CrashHandler.class) {
                if (INSTANCE == null) {
                    INSTANCE = new CrashHandler();
                }
            }
        }
        return INSTANCE;
    }

    /**
     * 初始化
     */
    public void init(Context context) {
        // 设置该CrashHandler作为程序的默认未捕获异常处理器
        Thread.setDefaultUncaughtExceptionHandler(this);
        mContext = context;
        //如果外存卡可以读写，放在外部存储器，否则放在内部存储器上
        if (Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED)) {
            LOG_PATH = Environment.getExternalStorageDirectory().getAbsolutePath() + "/crash/";
        } else {
            LOG_PATH = mContext.getFilesDir().getPath();
        }
    }

    /**
     * 此方法是在主线程中回调
     * Log.e("currentThread", t.currentThread().getName());
     */
    @Override
    public void uncaughtException(Thread t, final Throwable e) {
//        ToastUtil.showToast(mContext, "很抱歉，程序出现异常，即将退出...");        // Toast不展示
        collectDeviceInfo();
        ThreadPoolUtil.getInstance().execute(new Runnable() {                     // 子线程中执行 文件和网络相关操作
            @Override
            public void run() {
                /*Looper.prepare();
                ToastUtil.showToast(mContext, "很抱歉，程序出现异常，即将退出...");  // Toast展示，但是会出现近3s的白屏
                Looper.loop();*/
                // 注意这里是将 日志保存本地 与 日志发送到服务器 同时放到 子线程中 操作的
                Log.e("currentThread", Thread.currentThread().getName());         // 子线程名
                uploadLogToServer(saveCrashLog(e));
                Process.killProcess(Process.myPid());
            }
        });
        /*// 当前线程（主线程）中执行
        uploadLogToServer(saveCrashLog(e));
        Process.killProcess(android.os.Process.myPid());*/
    }

    /**
     * 将崩溃日志发送到服务器
     */
    private void uploadLogToServer(String fileName) {
        // TODO: 2018/6/11 0011  主线程中使用 call.enqueue 子线程中使用 call.execute()
    }

    /**
     * 收集崩溃设备参数信息
     */
    private void collectDeviceInfo() {
        PackageManager pm = mContext.getPackageManager();
        try {
            PackageInfo packageInfo = pm.getPackageInfo(mContext.getPackageName(), PackageManager.GET_ACTIVITIES);
            if (packageInfo != null) {
                String appName = packageInfo.applicationInfo.packageName;
                String versionName = packageInfo.versionName + "";
                String versionCode = packageInfo.versionCode + "";
                mInfoMap.put("包名", appName);
                mInfoMap.put("版本名", versionName);
                mInfoMap.put("版本号", versionCode);
            }
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
            Log.e(TAG, "an error occured when collect package info", e);
        }
        Field[] fields = Build.class.getDeclaredFields();
        for (Field field : fields) {
            field.setAccessible(true);
            try {
                mInfoMap.put(field.getName(), field.get(null).toString());
            } catch (IllegalAccessException e) {
                e.printStackTrace();
                Log.e(TAG, "an error occured when collect crash info", e);
            }
        }
    }

    /**
     * 将奔溃信息写入文件 并 返回文件名
     */
    private String saveCrashLog(Throwable ex) {
        Log.e("----", "here");
        StringBuffer msg = new StringBuffer();
        Log.e("----", "fileName");
        for (Map.Entry<String, String> entry : mInfoMap.entrySet()) {
            String key = entry.getKey();
            String value = entry.getValue();
            msg.append(key + "+" + value + "\n");
        }
        // 异常信息写入 writer
        Writer writer = new StringWriter();
        PrintWriter printWriter = new PrintWriter(writer);
        ex.printStackTrace(printWriter);
        // 异常原因写入 writer
        Throwable cause = ex.getCause();
        if (cause != null) {
            cause.printStackTrace(printWriter);
        }
        printWriter.close();
        // 从writer中取出信息放入msg中
        String result = writer.toString();
        msg.append(result);

        // 将msg中的信息写入文件中
        String time = mSimpleDateFormat.format(new Date(System.currentTimeMillis()));
        String fileName = "crash-" + time + ".log";
        try {
            File dir = new File(LOG_PATH);
            if (!dir.exists()) {
                dir.mkdirs();
            }
            FileOutputStream fos = new FileOutputStream(LOG_PATH + fileName);
            fos.write(msg.toString().getBytes());
            fos.close();
        } catch (Exception e) {
            Log.e(TAG, "an error occured while writing file...", e);
        }
        return fileName;
    }
}
