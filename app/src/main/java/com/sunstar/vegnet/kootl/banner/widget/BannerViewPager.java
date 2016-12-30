package com.sunstar.vegnet.kootl.banner.widget;

import android.content.Context;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.view.MotionEvent;

import com.sunstar.vegnet.kootl.comm.tool.ThreadTool;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

/**
 * Created by louisgeek on 2016/12/13.
 */

public class BannerViewPager extends ViewPager {
    private boolean mIsTouching;
    private boolean mIsAutoPlay;

    public BannerViewPager(Context context) {
        super(context);
    }

    public BannerViewPager(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    /**
     * May in onResume
     */
    public void startAutoPlay() {
        mIsAutoPlay = true;
    }

    /**
     * May in onStop
     */
    public void stopAutoPlay() {
        mIsAutoPlay = false;
    }

    /**
     *
     */
    @Override
    protected void onAttachedToWindow() {
        super.onAttachedToWindow();
        startPlay();
    }

    /**
     *
     */
    @Override
    protected void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        stopPlay();
    }

    @Override
    public boolean onTouchEvent(MotionEvent ev) {
        switch (ev.getAction()) {
            case MotionEvent.ACTION_DOWN:
            case MotionEvent.ACTION_MOVE:
                mIsTouching = true;
                break;
            case MotionEvent.ACTION_CANCEL:
            case MotionEvent.ACTION_UP:
                mIsTouching = false;
                break;
        }
        return super.onTouchEvent(ev);
    }

    /**
     *
     */
    private ScheduledExecutorService mExecutor;

    private void startPlay() {
        mExecutor = Executors.newSingleThreadScheduledExecutor();
        Runnable runnableCmd = new Runnable() {
            @Override
            public void run() {
                if (!mIsTouching && mIsAutoPlay) {
                    ThreadTool.runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            int NewItemPos = getCurrentItem() + 1;
                            setCurrentItem(NewItemPos);
                        }
                    });
                }
            }
        };
        /**
         * scheduleWithFixedDelay 把任务执行完成后再延迟固定时间后再执行下一次
         */
        mExecutor.scheduleWithFixedDelay(runnableCmd, 3 * 1000, 3 * 1000, TimeUnit.MILLISECONDS);
        /**
         * scheduleAtFixedRate  不受任务执行时间的影响
         */
        ///###mExecutor.scheduleAtFixedRate(runnableCmd, 3 * 1000, 3 * 1000, TimeUnit.MILLISECONDS);

    }

    private void stopPlay() {
        if (mExecutor != null && !mExecutor.isShutdown()) {
            mExecutor.shutdown();
        }
    }
}
