package com.sunstar.vegnet.kootl.comm.app;

import android.app.Activity;
import android.app.ActivityManager;
import android.content.Context;

import java.util.Stack;

/**
 * Created by louisgeek on 2016/12/30.
 */

public class ActivityStackSingleton {

    private static Stack<Activity> mActivityStack;

    /**
     * ====================================================
     */
    private static volatile ActivityStackSingleton mInstance;

    /* 私有构造方法，防止被实例化 */
    private ActivityStackSingleton() {
        mActivityStack = new Stack<>();
    }

    public static ActivityStackSingleton getInstance() {
        if (mInstance == null) {
            synchronized (ActivityStackSingleton.class) {
                if (mInstance == null) {
                    mInstance = new ActivityStackSingleton();
                }
            }
        }
        return mInstance;
    }

    /**
     * ==========================================
     */

    /**
     * 添加Activity到堆栈
     */
    public void addActivity(Activity activity) {
        if (activity != null) {
            mActivityStack.add(activity);
        }
    }

    /**
     * 移除指定的Activity
     */
    public void removeActivity(Activity activity) {
        if (activity != null) {
            mActivityStack.remove(activity);
        }
    }

    /**
     * 结束当前Activity（堆栈中最后一个压入的）
     */
    public void finishCurrentActivity() {
        Activity activity = getCurrentActivity();
        if (activity != null) {
            finishActivity(activity);
        }
    }

    /**
     * 结束指定的Activity
     */
    public void finishActivity(Activity activity) {
        if (activity != null) {
            mActivityStack.remove(activity);
            activity.finish();
        }
    }

    /**
     * 结束指定类名的Activity
     */
    public void finishActivity(Class<?> cls) {
        try {
            for (Activity activity : mActivityStack) {
                if (activity.getClass().equals(cls)) {
                    finishActivity(activity);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    /**
     * 结束所有Activity
     */
    public void finishAllActivity() {
        for (int i = 0, size = mActivityStack.size(); i < size; i++) {
            if (null != mActivityStack.get(i)) {
                mActivityStack.get(i).finish();
            }
        }
        mActivityStack.clear();
    }

    //============================================

    /**
     * 获取当前Activity（堆栈中最后一个压入的）
     */
    public Activity getCurrentActivity() {
        try {
            Activity activity = mActivityStack.lastElement();
            return activity;
        } catch (Exception e) {
            // e.printStackTrace();
            return null;
        }
    }

    /**
     * 获取当前Activity的前一个Activity
     */
    public Activity preActivity() {
        int index = mActivityStack.size() - 2;
        if (index < 0) {
            return null;
        }
        Activity activity = mActivityStack.get(index);
        return activity;
    }

    /**
     * 是否已经打开指定的activity
     *
     * @param cls
     * @return
     */
    public boolean isOpenActivity(Class<?> cls) {
        if (mActivityStack != null) {
            for (int i = 0, size = mActivityStack.size(); i < size; i++) {
                if (cls == mActivityStack.peek().getClass()) {
                    return true;
                }
            }
        }
        return false;
    }

    /**
     * 返回到指定的activity
     *
     * @param cls
     */
    public void returnToActivity(Class<?> cls) {
        while (mActivityStack.size() != 0)
            if (mActivityStack.peek().getClass() == cls) {
                break;
            } else {
                finishActivity(mActivityStack.peek());
            }
    }

    /**
     * 退出应用程序
     *
     * @param context                上下文
     * @param isKeepBackgroundRuning 是否开启后台运行
     */
    public void AppExit(Context context, boolean isKeepBackgroundRuning) {
        try {
            finishAllActivity();
            //
            ActivityManager activityManager = (ActivityManager) context
                    .getSystemService(Context.ACTIVITY_SERVICE);
            activityManager.killBackgroundProcesses(context.getPackageName());
        } catch (Exception e) {
            // e.printStackTrace();
        } finally {
            // 注意，如果您有后台程序运行，请不要执行这个
            if (!isKeepBackgroundRuning) {
                System.exit(0);
            }
        }
    }
}
