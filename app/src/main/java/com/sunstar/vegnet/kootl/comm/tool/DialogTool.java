package com.sunstar.vegnet.kootl.comm.tool;

import android.os.Handler;
import android.os.Looper;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.FragmentManager;

import com.sunstar.vegnet.kootl.comm.dialog.LoadingDialogFragment;
import com.sunstar.vegnet.kootl.comm.dialog.LoadingDialogSmileFragment;
import com.sunstar.vegnet.kootl.comm.dialog.OkCancelDialogFragment;
import com.sunstar.vegnet.kootl.comm.dialog.OkDialogFragment;

/**
 * Created by louisgeek on 2016/12/15.
 */

public class DialogTool {
    /**
     * 共用
     */
    private static DialogFragment mLoadingDialogFragment;

    public static void showLoadingSmileDialog(FragmentManager fragmentManager, String msg) {
        if (msg == null) {
            return;
        }
        if (mLoadingDialogFragment != null) {
            if (mLoadingDialogFragment instanceof LoadingDialogSmileFragment
                    && mLoadingDialogFragment.getDialog() != null
                    ) {
                if (!mLoadingDialogFragment.getDialog().isShowing()) {
                    mLoadingDialogFragment.getDialog().show();
                }
                //都更新text
                ((LoadingDialogSmileFragment) mLoadingDialogFragment).setMessage(msg);
            } else {
                //
                //#####mLoadingDialogFragment.dismiss();
                mLoadingDialogFragment.dismissAllowingStateLoss();
                //
                mLoadingDialogFragment = LoadingDialogSmileFragment.newInstance(msg);
                mLoadingDialogFragment.show(fragmentManager, "loading");
            }
        } else {
            //为null
            mLoadingDialogFragment = LoadingDialogSmileFragment.newInstance(msg);
            mLoadingDialogFragment.show(fragmentManager, "loading");
        }
    }

    public static void showLoadingDialog(FragmentManager fragmentManager, String msg) {

        if (msg == null) {
            return;
        }
        if (mLoadingDialogFragment != null) {
            if (mLoadingDialogFragment instanceof LoadingDialogFragment
                    && mLoadingDialogFragment.getDialog() != null
                    ) {
                if (!mLoadingDialogFragment.getDialog().isShowing()) {
                    mLoadingDialogFragment.getDialog().show();
                }
                //都更新text
                ((LoadingDialogFragment) mLoadingDialogFragment).setMessage(msg);
            } else {
                //
                //#####mLoadingDialogFragment.dismiss();
                mLoadingDialogFragment.dismissAllowingStateLoss();
                //
                mLoadingDialogFragment = LoadingDialogFragment.newInstance(msg);
                mLoadingDialogFragment.show(fragmentManager, "loading");
            }
        } else {
            //为null
            mLoadingDialogFragment = LoadingDialogFragment.newInstance(msg);
            mLoadingDialogFragment.show(fragmentManager, "loading");
        }
    }

    public static void hideDialogFragment() {
        hideDialogFragment(900);
    }

    public static void hideDialogFragment(long delayMillis) {
        new Handler(Looper.getMainLooper()).postDelayed(new Runnable() {
            @Override
            public void run() {
                if (mLoadingDialogFragment != null) {
                    //#####mLoadingDialogFragment.dismiss();
                    mLoadingDialogFragment.dismissAllowingStateLoss();
                }
            }
        }, delayMillis);
    }


    /**
     * ======================================================
     */
    private static OkDialogFragment mOkDialogFragment;
    private static String mLastOkMessage;

    public static void showMsgDialog(FragmentManager fragmentManager, String message) {
        if (message == null) {
            return;
        }
        if (mOkDialogFragment != null) {
            if (mOkDialogFragment.getDialog() != null && message.equals(mLastOkMessage)) {
                if (!mOkDialogFragment.getDialog().isShowing()) {
                    //#####mOkDialogFragment.dismiss();
                    mOkDialogFragment.dismissAllowingStateLoss();
                    //
                    mOkDialogFragment = OkDialogFragment.newInstance(null, message);
                    mOkDialogFragment.show(fragmentManager, "ok");
                }
            } else {
                //#####mOkDialogFragment.dismiss();
                mOkDialogFragment.dismissAllowingStateLoss();
                //
                mOkDialogFragment = OkDialogFragment.newInstance(null, message);
                mOkDialogFragment.show(fragmentManager, "ok");
            }
        } else {
            mOkDialogFragment = OkDialogFragment.newInstance(null, message);
            mOkDialogFragment.show(fragmentManager, "ok");
        }
        //存值
        mLastOkMessage = message;

    }

    /**
     * ======================================================
     */
    private static OkCancelDialogFragment mOkCancelDialogFragment;
    private static String mLastOkCancleMessage;

    public static void showAskDialog(FragmentManager fragmentManager, String message,OkCancelDialogFragment.OnBtnClickListener onBtnClickListener) {
        if (message == null) {
            return;
        }
        if (mOkCancelDialogFragment != null) {
            if (mOkCancelDialogFragment.getDialog() != null && message.equals(mLastOkCancleMessage)) {
                if (!mOkCancelDialogFragment.getDialog().isShowing()) {
                    //#####mOkCancelDialogFragment.dismiss();
                    mOkCancelDialogFragment.dismissAllowingStateLoss();
                    //
                    mOkCancelDialogFragment = OkCancelDialogFragment.newInstance(null, message);
                    mOkCancelDialogFragment.setOnBtnClickListener(onBtnClickListener);
                    mOkCancelDialogFragment.show(fragmentManager, "ok");
                }
            } else {
                //#####mOkCancelDialogFragment.dismiss();
                mOkCancelDialogFragment.dismissAllowingStateLoss();
                //
                mOkCancelDialogFragment = OkCancelDialogFragment.newInstance(null, message);
                mOkCancelDialogFragment.setOnBtnClickListener(onBtnClickListener);
                mOkCancelDialogFragment.show(fragmentManager, "okcancel");
            }
        } else {
            mOkCancelDialogFragment = OkCancelDialogFragment.newInstance(null, message);
            mOkCancelDialogFragment.setOnBtnClickListener(onBtnClickListener);
            mOkCancelDialogFragment.show(fragmentManager, "okcancel");
        }
        //存值
        mLastOkCancleMessage = message;

    }
}
