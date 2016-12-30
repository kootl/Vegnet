package com.sunstar.vegnet.kootl.comm.helper;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.view.View;
import android.view.ViewGroup;

import com.sunstar.vegnet.R;
import com.sunstar.vegnet.kootl.comm.tool.ViewTool;

/**
 * Created by louisgeek on 2016/12/16.
 */

public class ViewHelper {
    private static final int VIEW_STATE_DEFAULT = 0;
    private static final int VIEW_STATE_HIDED = 11;
    private static final int VIEW_STATE_SHOW = 22;
    private static final int VIEW_STATE_ANIMING = 33;

    /**
     *
     */
    public static void showView(final View view) {
        int viewState = VIEW_STATE_DEFAULT;
        if (view.getTag(R.id.id_view_state_holder) != null) {
            viewState = (int) view.getTag(R.id.id_view_state_holder);
        }
        switch (viewState) {
            case VIEW_STATE_ANIMING:
            case VIEW_STATE_SHOW:
            case VIEW_STATE_DEFAULT:
                /* do nothing */
                break;
            case VIEW_STATE_HIDED:
                /**
                 *
                 */
                view.setTag(R.id.id_view_state_holder, VIEW_STATE_ANIMING);
                //
                int startHeight = 0;
                int endHeight = ViewTool.getMeasuredHeightMy(view);
                //KLog.d("showView startHeight:" + startHeight);
                final ViewGroup.LayoutParams vlp = view.getLayoutParams();
                //KLog.d("showView !!!!");
                ValueAnimator valueAnimator = ValueAnimator.ofInt(startHeight, endHeight);

                valueAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
                    @Override
                    public void onAnimationUpdate(ValueAnimator valueAnimator) {
                        int nowValue = (int) valueAnimator.getAnimatedValue();
                        vlp.height = nowValue;
                        view.setLayoutParams(vlp);
                    }
                });
                valueAnimator.addListener(new AnimatorListenerAdapter() {
                    @Override
                    public void onAnimationEnd(Animator animation) {
                        super.onAnimationEnd(animation);
                        view.setTag(R.id.id_view_state_holder, VIEW_STATE_SHOW);
                    }
                });
                ObjectAnimator objectAnimator = ObjectAnimator.ofFloat(view, "alpha", 0f, 1f);

                AnimatorSet animatorSet = new AnimatorSet();
                animatorSet.playTogether(valueAnimator, objectAnimator);
                animatorSet.setDuration(500);
                animatorSet.start();
                //
                break;
        }
    }


    /**
     * @param view
     */
    public static void hideView(final View view) {
        int viewState = VIEW_STATE_DEFAULT;
        if (view.getTag(R.id.id_view_state_holder) != null) {
            viewState = (int) view.getTag(R.id.id_view_state_holder);
        }
        switch (viewState) {
            case VIEW_STATE_ANIMING:
            case VIEW_STATE_HIDED:
                    /* do nothing */
                break;
            case VIEW_STATE_DEFAULT:
            case VIEW_STATE_SHOW:
                /**
                 *
                 */
                view.setTag(R.id.id_view_state_holder, VIEW_STATE_ANIMING);
                //
                int startHeight = ViewTool.getMeasuredHeightMy(view);
                int endHeight = 0;
                //KLog.d("hideView startHeight:" + startHeight);
                final ViewGroup.LayoutParams vlp = view.getLayoutParams();
                //KLog.d("hideView !!!!");
                ValueAnimator valueAnimator = ValueAnimator.ofInt(startHeight, endHeight);

                valueAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
                    @Override
                    public void onAnimationUpdate(ValueAnimator valueAnimator) {
                        int nowValue = (int) valueAnimator.getAnimatedValue();
                        vlp.height = nowValue;
                        view.setLayoutParams(vlp);
                    }
                });
                valueAnimator.addListener(new AnimatorListenerAdapter() {
                    @Override
                    public void onAnimationEnd(Animator animation) {
                        super.onAnimationEnd(animation);
                        view.setTag(R.id.id_view_state_holder, VIEW_STATE_HIDED);
                    }
                });
                ObjectAnimator objectAnimator = ObjectAnimator.ofFloat(view, "alpha", 1f, 0f);

                AnimatorSet animatorSet = new AnimatorSet();
                animatorSet.playTogether(valueAnimator, objectAnimator);
                animatorSet.setDuration(500);
                animatorSet.start();
                //
                break;
        }
    }


}
