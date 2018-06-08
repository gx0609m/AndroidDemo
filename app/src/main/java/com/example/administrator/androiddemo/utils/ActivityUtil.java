package com.example.administrator.androiddemo.utils;

import android.app.Activity;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by gx on 2018/6/8 0008
 */

public class ActivityUtil {

    private static List<Activity> activityList = new ArrayList<Activity>();

    public static void addActivity(Activity activity) {
        activityList.add(activity);
    }

    public static void removeAcivity(Activity activity) {
        activityList.remove(activity);
    }

    public static void removeAll() {
        for (Activity activity : activityList) {
            if (!activity.isFinishing()) {
                activity.finish();
            }
        }
    }
}
