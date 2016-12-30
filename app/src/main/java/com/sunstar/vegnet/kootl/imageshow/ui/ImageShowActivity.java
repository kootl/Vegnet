package com.sunstar.vegnet.kootl.imageshow.ui;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.BottomSheetBehavior;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.widget.TextView;

import com.socks.library.KLog;
import com.sunstar.vegnet.R;
import com.sunstar.vegnet.kootl.comm.base.BaseActivity;
import com.sunstar.vegnet.kootl.comm.rvheaderfooter.RVHeaderFooterAdapter;
import com.sunstar.vegnet.kootl.comm.tabhost.TabHostShowDataInfo;
import com.sunstar.vegnet.kootl.comm.tool.RegexTool;
import com.sunstar.vegnet.kootl.comm.tool.ScreenTool;
import com.sunstar.vegnet.kootl.comm.tool.ToastTool;
import com.sunstar.vegnet.kootl.imageshow.adapter.ImageShowPagerAdapter;
import com.sunstar.vegnet.kootl.imageshow.bean.ImageShowBean;
import com.sunstar.vegnet.kootl.imageshow.bean.ImageShowDataWrapper;
import com.sunstar.vegnet.kootl.imageshow.contract.ImageShowContract;
import com.sunstar.vegnet.kootl.imageshow.helper.DownloadImageHelper;
import com.sunstar.vegnet.kootl.imageshow.presenter.ImageShowPresenterImpl;

import java.util.ArrayList;
import java.util.List;

import static android.support.design.widget.BottomSheetBehavior.STATE_COLLAPSED;

public class ImageShowActivity extends BaseActivity<ImageShowContract.Presenter> implements ImageShowContract.View<ImageShowDataWrapper> {

    private ViewPager mViewPager;
    private TextView id_bottom_sheet_text;
    private BottomSheetBehavior<View> mBottomSheetBehavior;
    private AppBarLayout id_appbar_layout;

    private ImageShowPagerAdapter mImageShowPagerAdapter;
    private List<ImageShowBean> mImageShowBeanList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    protected int setupLayoutId() {
        return R.layout.activity_image_show;
    }

    @Override
    protected int setupToolbarResId() {
        return R.id.id_toolbar;
    }

    @Override
    protected ImageShowContract.Presenter setupPresenter() {
        return new ImageShowPresenterImpl(this);
    }

    @Override
    protected TabHostShowDataInfo setupTabHostDataInfo() {
        return null;
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
        // return R.menu.menu_save;
        return 0;
    }

    @Override
    protected int setupStatusBarColorResId() {
        return R.color.colorPrimary_Black;
    }

    @Override
    protected void begin() {

        initBottomSheet();
        //
        initImageViewPager();
        //
        mPresenter.gainData();
    }


    private void initBottomSheet() {
        id_bottom_sheet_text = (TextView) findViewById(R.id.id_bottom_sheet_text);
     /*   if (contentlistBean.getText()!=null&&!contentlistBean.getText().equals("")) {
            id_bottom_sheet_text.setText(contentlistBean.getText());
        }else{
            id_bottom_sheet_text.setText(contentlistBean.getTitle());
        }*/
        View bottomSheet = findViewById(R.id.id_bottom_sheet);
        mBottomSheetBehavior = BottomSheetBehavior.from(bottomSheet);
        mBottomSheetBehavior.setHideable(true);
        int peekHeight = (int) (ScreenTool.getScreenHeight(mContext) * 1.0f / 5);
        mBottomSheetBehavior.setPeekHeight(peekHeight);
        mBottomSheetBehavior.setBottomSheetCallback(new BottomSheetBehavior.BottomSheetCallback() {
            @Override
            public void onStateChanged(@NonNull View bottomSheet, int newState) {
                /** STATE_COLLAPSED: 关闭Bottom Sheets,显示peekHeight的高度，默认是STATE_COLLAPSED
                 STATE_DRAGGING: 用户拖拽Bottom Sheets时的状态
                 STATE_SETTLING: 当Bottom Sheets view释放时记录的状态。
                 STATE_EXPANDED: 当Bottom Sheets 展开的状态
                 STATE_HIDDEN: 当Bottom Sheets 隐藏的状态*/
                if (newState == BottomSheetBehavior.STATE_COLLAPSED) {
                    //回归显示toolbar
                    id_appbar_layout.setExpanded(true);
                } else if (newState== BottomSheetBehavior.STATE_HIDDEN) {
                    //隐藏toolbar
                    id_appbar_layout.setExpanded(false);
                }
            }

            @Override
            public void onSlide(@NonNull View bottomSheet, float slideOffset) {

            }
        });
    }

    private void initImageViewPager() {
        id_appbar_layout = (AppBarLayout) findViewById(R.id.id_appbar_layout);

        //
        mViewPager = findById(R.id.id_view_pager);
        mViewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                id_bottom_sheet_text.setText(mImageShowBeanList.get(position).getTitle());

                setToolbarTitle((position + 1) + "/" + mImageShowBeanList.size());
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
        mImageShowPagerAdapter = new ImageShowPagerAdapter(mImageShowBeanList);
        mImageShowPagerAdapter.setPhotoViewAttacherListener(new ImageShowPagerAdapter.PhotoViewAttacherListener() {
            @Override
            public void onPhotoViewInited(final View photoView) {
                id_appbar_layout.addOnOffsetChangedListener(new AppBarLayout.OnOffsetChangedListener() {
                    @Override
                    public void onOffsetChanged(AppBarLayout appBarLayout, int verticalOffset) {
                        //  收起 0   ~  -84      展开 -84  ~0
                        KLog.d("!!!onOffsetChanged:verticalOffset" + verticalOffset);
                        /**
                         * 修正当toobar慢慢收起后  mPhotoView相对位置不变化
                         */
                        photoView.setTranslationY(Math.abs(verticalOffset));
                    }
                });
            }

            @Override
            public void onPhotoViewTap(final View photoView, float x, float y) {
                //Toast.makeText(ImageShowActivity.this, "onViewTap", Toast.LENGTH_SHORT).show();
                if (mBottomSheetBehavior.getState() == BottomSheetBehavior.STATE_COLLAPSED) {
                    //设置为隐藏
                    mBottomSheetBehavior.setState(BottomSheetBehavior.STATE_HIDDEN);
                    //设置为隐藏
                    id_appbar_layout.setExpanded(false);
                } else if (mBottomSheetBehavior.getState() == BottomSheetBehavior.STATE_HIDDEN) {
                    //设置为显示
                    mBottomSheetBehavior.setState(STATE_COLLAPSED);//默认半展开
                    //设置为显示
                    id_appbar_layout.setExpanded(true);
                }
            }
        });
        //
        mViewPager.setAdapter(mImageShowPagerAdapter);
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
        return false;
    }


 /*   @Override
    protected boolean onOptionsItemSelectedOutter(MenuItem menuItem,boolean superBackValue) {
        switch (menuItem.getItemId()) {
            case R.id.id_menu_item_save:
                saveImageOperator();
            return true;
        }
        */

    /**
     * superBackValue super.onOptionsItemSelected();方法得返回值
     *//*
        return  superBackValue;
    }*/
    private void saveImageOperator() {
        int pos = mViewPager.getCurrentItem();
        String imageUrl = mImageShowBeanList.get(pos) == null ? "" : mImageShowBeanList.get(pos).getImageUrl().toString();
        if (!imageUrl.equals("") && RegexTool.checkURL(imageUrl)) {
              /*  OkHttpClientSingleton.getInstance().doGetAsyncFile(imageUrl, new SimpleFileOkHttpCallback() {
                    @Override
                    public void OnSuccess(String filePath, int statusCode) {
                       //Toast.makeText(ImageShowActivity.this, "filePath:"+filePath, Toast.LENGTH_SHORT).show();
                        KLog.d("filePath:"+filePath);
                    }
                });*/
            KLog.d("imageUrl:" + imageUrl);
            downloadImage(imageUrl);
        } else {
            ToastTool.showLong("已经是本地图片：" + imageUrl);
        }
    }

    public void downloadImage(String imageUrl) {
        DownloadImageHelper.downLoadFile(imageUrl, new DownloadImageHelper.OnDownLoadImageListener() {
            @Override
            public void onSuccess(String filepath) {
                KLog.d("XXX onSuccess" + filepath);
                ToastTool.showLong("已保存到" + filepath);
            }

            @Override
            public void onError() {
                KLog.d("XXX onError  保存失败");
            }
        });
    }


    @Override
    public void showProgress() {
        //showSmileLoadingDialog();
    }

    @Override
    public void hideProgress() {
        //hideLoadingDialog();
    }

    @Override
    public void showNoDataNotice() {

    }

    @Override
    public void hideNoDataNotice() {

    }

    @Override
    public void showMessage(String msg) {
        showMessageDialog(msg);
    }

    @Override
    public void setupData(ImageShowDataWrapper data) {

        /**
         *
         */
        int nowSeletedPos = data.getNowSeletedPos();
        List<ImageShowBean> imageShowBeanList = data.getImageShowBeanList();

        /**
         *
         */
        mImageShowPagerAdapter.refreshImageShowBeanList(imageShowBeanList);

        /**
         *
         */
        setToolbarTitle((nowSeletedPos + 1) + "/" + imageShowBeanList.size());

        String title = imageShowBeanList.get(nowSeletedPos).getTitle();
        //
        id_bottom_sheet_text.setText(title);
        //
        mViewPager.setCurrentItem(nowSeletedPos);

    }
}
