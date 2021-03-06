package com.sunstar.vegnet.kootl.comm.dialog;

import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatDialogFragment;
import android.view.LayoutInflater;

/**
 * Created by louisgeek on 2016/6/7.
 */
public class OkDialogFragment extends AppCompatDialogFragment {
    private final static String TITLE_KEY = "TitleKey";
    private final static String MESSAGE_KEY = "MessageKey";


    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {

        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        /**
         * 自定义view
         */
        LayoutInflater inflater = getActivity().getLayoutInflater();
        //View myview = inflater.inflate(R.layout.dialogfrag_content_normal, null);

        String title = getArguments().getString(TITLE_KEY);
        String message = getArguments().getString(MESSAGE_KEY);


        builder.setTitle(title).setMessage(message).setPositiveButton("确定", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {

                if (onBtnClickListener != null) {
                    onBtnClickListener.onOkBtnClick(dialogInterface);
                }
            }
        });
        /**
         * 自定义view
         */
        // builder.setView(myview).setTitle(title).setMessage(message).setPositiveButton("确定",this).setNegativeButton("取消",this);
        Dialog dialog = builder.create();
        // return super.onCreateDialog(savedInstanceState);
        return dialog;

    }

    public static OkDialogFragment newInstance(String title, String message) {
        OkDialogFragment dialogFragment = new OkDialogFragment();
        Bundle args = new Bundle();
        // 自定义的标题
        args.putString(TITLE_KEY, title);
        args.putString(MESSAGE_KEY, message);
        dialogFragment.setArguments(args);
        return dialogFragment;
    }

    public interface OnBtnClickListener {
        void onOkBtnClick(DialogInterface dialogInterface);
    }

    public void setOnBtnClickListener(OnBtnClickListener onBtnClickListener) {
        this.onBtnClickListener = onBtnClickListener;
    }

    OnBtnClickListener onBtnClickListener;
}
