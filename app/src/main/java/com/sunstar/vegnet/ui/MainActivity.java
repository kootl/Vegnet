package com.sunstar.vegnet.ui;

import android.content.Intent;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.sunstar.vegnet.R;
import com.sunstar.vegnet.kootl.comm.base.BaseActivity;
import com.sunstar.vegnet.kootl.comm.base.BasePresenter;
import com.sunstar.vegnet.kootl.comm.config.CommFinalData;
import com.sunstar.vegnet.kootl.comm.rvheaderfooter.RVHeaderFooterAdapter;
import com.sunstar.vegnet.kootl.comm.tabhost.TabHostShowDataInfo;
import com.sunstar.vegnet.kootl.imagepicker.bean.ImagePickerDataWrapper;
import com.sunstar.vegnet.kootl.itemselect.bean.ItemSelectBean;
import com.sunstar.vegnet.kootl.itemselect.bean.ItemSelectDataWrapper;
import com.sunstar.vegnet.news.ui.NewsActivity;

import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.TimeUnit;

import rx.Observable;
import rx.functions.Action1;

public class MainActivity extends BaseActivity<BasePresenter> {

    @Override
    protected int setupLayoutId() {
        return R.layout.activity_main;
    }

    @Override
    protected int setupToolbarResId() {
        return R.id.id_toolbar;
    }

    @Override
    protected BasePresenter setupPresenter() {
        return null;
    }

    @Override
    protected TabHostShowDataInfo setupTabHostDataInfo() {
        return null;
    }

    @Override
    protected boolean notNeedSwipeBack() {
        return true;
    }

    @Override
    protected boolean setupToolbarTitleCenter() {
        return true;
    }

    @Override
    protected boolean setupHideHomeAsUp() {
        return true;
    }

    @Override
    protected int setupCollapsingToolbarLayoutResId() {
        return 0;
    }

    @Override
    protected int setupSwipeRefreshLayoutResId() {
        return 0;
    }

    @Override
    protected int setupRecyclerViewResId() {
        return 0;
    }

    @Override
    protected RVHeaderFooterAdapter setupRVHeaderFooterAdapter() {
        return null;
    }

    @Override
    protected void toLoadMoreData() {

    }

    @Override
    protected int setupMenuResId() {
        return 0;
    }

    @Override
    protected int setupStatusBarColorResId() {
        return 0;
    }

    private boolean mIsMultiSelect = true;

    @Override
    protected void begin() {


        final Button id_tv_go = findById(R.id.id_tv_go);

        final Button id_tv_go2 = findById(R.id.id_tv_go2);
        final Button id_vtn = findById(R.id.id_vtn);
        id_tv_go2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // ViewHelper.hideView(mToolbar);

            }
        });
        setClickListener(id_tv_go, new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //startAtyForResult(ItemSelectActivity.class, CommFinalData.REQUEST_CODE_ITEM_SELECT_1, mIsMultiSelect ? "1" : "0");
                // startAtyForResult(ImagePickerActivity.class, CommFinalData.REQUEST_CODE_IMAGE_SELECT_1);
                // startAty(NewsActivity.class);
                // NewsActivity.startMe(mContext, NewsActivity.class);
                // ViewHelper.showViewFromTop(mToolbar);
                //   ViewHelper.showView(mToolbar);
                //ViewHelper.hideViewToBottom(id_vtn);

            }
        });
        //startAty(NewsActivity.class);
        NewsActivity.startMe(mContext, NewsActivity.class);
        //###  ImagePickerActivity.startMe(mContext,ImagePickerActivity.class);
        finishAtyAfterTransition();
        //  startAty(ItemTouchActivity.class);
        //  showSmileLoadingDialog();
    }




    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        //
        switch (requestCode) {
            case CommFinalData.REQUEST_CODE_ITEM_SELECT_1:
                if (resultCode == RESULT_OK) {
                    ItemSelectDataWrapper itemSelectDataWrapper = (ItemSelectDataWrapper) data.getSerializableExtra("itemSelectDataWrapper");
                    StringBuilder stringBuilder = new StringBuilder();
                    for (ItemSelectBean itemSelectBean :
                            itemSelectDataWrapper.getItemSelectBeanList()) {
                        if (itemSelectBean.isSelected()) {
                            stringBuilder.append(itemSelectBean.getItemTitle());
                        }
                    }
                    showMessageDialog(stringBuilder.toString());
                }
                break;
            case CommFinalData.REQUEST_CODE_IMAGE_SELECT_1:
                if (resultCode == RESULT_OK) {
                    ImagePickerDataWrapper imagePickerDataWrapper = (ImagePickerDataWrapper) data.getSerializableExtra("imagePickerDataWrapper");
                    StringBuilder stringBuilder = new StringBuilder();
                    for (String path :
                            imagePickerDataWrapper.getSelectedImageList()) {
                        stringBuilder.append(path);
                        stringBuilder.append(",");
                    }
                    showMessageDialog(stringBuilder.toString());
                }
                break;

        }
    }

    @Override
    public void onBackPressed() {
        exitApp(true);
    }

    private void exitApp(boolean useRx) {
        if (useRx) {
            exitAppByTwoClickByRx();
        } else {
            exitAppByTwoClick();
        }
    }

    /**
     * 双击退出App
     */
    private boolean isExit;

    private void exitAppByTwoClick() {
        Timer timer;
        if (!isExit) {
            isExit = true; // 准备退出
            Toast.makeText(mContext, "再按一次退出程序", Toast.LENGTH_SHORT).show();
            timer = new Timer();
            timer.schedule(new TimerTask() {
                @Override
                public void run() {
                    isExit = false; // 重置成false 取消退出
                }
            }, 2000); // 如果2秒钟内没有按下返回键，则启动定时器取消掉刚才执行的任务

        } else {
            super.onBackPressed();
            //System.exit(0);
        }
    }

    private boolean isExitByRx;

    private void exitAppByTwoClickByRx() {
        if (!isExitByRx) {
            isExitByRx = true;
            Toast.makeText(mContext, "再按一次退出程序", Toast.LENGTH_SHORT).show();
            Observable.timer(2000, TimeUnit.MILLISECONDS).subscribe(new Action1<Long>() {
                @Override
                public void call(Long aLong) {
                    isExitByRx = false; // 重置成false 取消退出
                }
            });
        } else {
            super.onBackPressed();
            //System.exit(0);
        }

    }
}
