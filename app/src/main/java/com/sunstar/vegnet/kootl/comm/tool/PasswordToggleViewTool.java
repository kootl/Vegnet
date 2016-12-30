package com.sunstar.vegnet.kootl.comm.tool;

import android.support.design.widget.CheckableImageButton;
import android.support.design.widget.TextInputLayout;

/**
 * Created by louisgeek on 2016/12/15.
 */

public class PasswordToggleViewTool {
    public static void fixPasswordToggleViewInCenter(TextInputLayout id_til_password, int translationY) {
        try {
            /**当padding  textSize不同设置时候
             背景和右边的眼睛不对齐
             通过反射获取TextInputLayout的mPasswordToggleView
             然后用setTranslationY(-5f);位移来fix达到需求
             android:padding="10dp"
             android:textSize="14dp"
             */
            CheckableImageButton mPasswordToggleView = ReflectTool.getFieldValue(id_til_password, "mPasswordToggleView");
            mPasswordToggleView.setTranslationY(translationY);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void fixPasswordToggleViewInCenter(TextInputLayout id_til_password) {
        fixPasswordToggleViewInCenter(id_til_password, SizeTool.dp2px(id_til_password.getContext(), -4));
    }
}
