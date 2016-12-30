package com.sunstar.vegnet.kootl.comm.dialog;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatDialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.TextView;

import com.sunstar.vegnet.R;

/**
 * Created by louisgeek on 2016/6/7.
 */
public class LoadingDialogFragment extends AppCompatDialogFragment {
    private final static String MESSAGE_KEY = "messageKey";
    private String mMessage;
    private TextView mTextView;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mMessage = getArguments().getString(MESSAGE_KEY);
        setCancelable(false);
        //设置自定义主题
        //setStyle(DialogFragment.STYLE_NORMAL, R.style.BaseLoadingDialogTheme);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        //隐藏标题
        getDialog().requestWindowFeature(Window.FEATURE_NO_TITLE);

        View view = inflater.inflate(R.layout.dialog_loading_normal, null);
        mTextView = (TextView) view.findViewById(R.id.id_tv_loading_message);
        if (mMessage != null&&!mMessage.equals("")) {
            mTextView.setText(mMessage);
        }
     /*   view.findViewById(R.id.id_iv_loading_close).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
                if (onBtnClickListener != null) {
                    onBtnClickListener.onCloseBtnClick(v);
                }
            }
        });*/
        //return super.onCreateView(inflater, container, savedInstanceState);
        return view;
    }

    public static LoadingDialogFragment newInstance(String message) {
        LoadingDialogFragment dialogFragment = new LoadingDialogFragment();
        Bundle args = new Bundle();
        //message
        args.putString(MESSAGE_KEY, message);
        dialogFragment.setArguments(args);
        return dialogFragment;
    }

    public void setMessage(String message) {
        if (mMessage != null&&!mMessage.equals("")) {
            mMessage = message;
            mTextView.setText(mMessage);
        }
    }

   /* public interface OnBtnClickListener {
        void onCloseBtnClick(View view);
    }

    public void setOnBtnClickListener(OnBtnClickListener onBtnClickListener) {
        this.onBtnClickListener = onBtnClickListener;
    }

    public OnBtnClickListener onBtnClickListener;*/
}
