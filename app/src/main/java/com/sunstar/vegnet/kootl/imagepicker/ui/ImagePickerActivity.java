package com.sunstar.vegnet.kootl.imagepicker.ui;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.ObjectAnimator;
import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.MenuItem;
import android.view.View;

import com.socks.library.KLog;
import com.sunstar.vegnet.R;
import com.sunstar.vegnet.kootl.comm.base.BaseActivity;
import com.sunstar.vegnet.kootl.comm.rvheaderfooter.RVHeaderFooterAdapter;
import com.sunstar.vegnet.kootl.comm.tabhost.TabHostShowDataInfo;
import com.sunstar.vegnet.kootl.comm.tool.ViewTool;
import com.sunstar.vegnet.kootl.imagepicker.adapter.ImagePickerDirListRecyclerAdapter;
import com.sunstar.vegnet.kootl.imagepicker.adapter.ImagePickerListRVHFAdapter;
import com.sunstar.vegnet.kootl.imagepicker.bean.ImagePickerBean;
import com.sunstar.vegnet.kootl.imagepicker.bean.ImagePickerDataWrapper;
import com.sunstar.vegnet.kootl.imagepicker.bean.ImagePickerDirBean;
import com.sunstar.vegnet.kootl.imagepicker.bean.ImagePickerDirDataWrapper;
import com.sunstar.vegnet.kootl.imagepicker.contract.ImagePickerContract;
import com.sunstar.vegnet.kootl.imagepicker.presenter.ImagePickerPresenterImpl;
import com.sunstar.vegnet.kootl.imageshow.bean.ImageShowBean;
import com.sunstar.vegnet.kootl.imageshow.helper.ImageShowDataHelper;
import com.yqritc.recyclerviewflexibledivider.HorizontalDividerItemDecoration;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class ImagePickerActivity extends BaseActivity<ImagePickerContract.Presenter> implements ImagePickerContract.View<ImagePickerDirDataWrapper> {

    /**
     * 图片适配器
     */
    private ImagePickerListRVHFAdapter mImagePickerListRVHFAdapter;
    /**
     * 背景view  黑色蒙版
     */
    private View mViewBackground;
    private int mRecyclerviewDirHeight;

    /**
     * 当前文件夹下的的图片  adapter主用数据源
     */
    private List<ImagePickerBean> mNowDirImageList = new ArrayList<>();
    /**
     * 所有的图片
     */
    private List<ImagePickerBean> mAllImageList = new ArrayList<>();
    /**
     * 所有的文件夹
     */
    private List<ImagePickerDirBean> mImageDirBeanList = new ArrayList<>();
    /**
     * 虚拟的存放所有图片的文件夹
     */
    private final String ALL_IMAGES_DIR_PATH_KEY = "所有图片";
    /**
     * 当前
     */
    private String mNowDirPathKey = ALL_IMAGES_DIR_PATH_KEY;//默认是所有图片

    /**
     * 已选择的图片
     */
    ///private List<String> mSelectedPhotoList = new ArrayList<>();

    /**
     * raw  data
     */
    private Map<String, List<String>> mGroupMap = new HashMap<>();


    @Override
    protected int setupLayoutId() {
        return R.layout.activity_image_picker;
    }

    @Override
    protected int setupToolbarResId() {
        return R.id.id_toolbar;
    }

    @Override
    protected ImagePickerContract.Presenter setupPresenter() {
        return new ImagePickerPresenterImpl(this);
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
        return R.id.id_recycler_view;
    }

    @Override
    protected RVHeaderFooterAdapter setupRVHeaderFooterAdapter() {
        /**
         * 覆盖baseAty的LinearLayoutManager
         */
        mRecyclerView.setLayoutManager(new GridLayoutManager(this, 3));
        /**
         * 因为NestedScrollView
         * 初始化时候防止测量   回调ALL 卡死和OOM
         */
        mRecyclerView.getLayoutManager().setAutoMeasureEnabled(false);
        /**
         *
         */
        mImagePickerListRVHFAdapter = new ImagePickerListRVHFAdapter(mNowDirImageList, mRecyclerView);
        mImagePickerListRVHFAdapter.setOnItemClickListener(new RVHeaderFooterAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View itemView, int position, Object data) {
                ImagePickerBean imagePickerBean = (ImagePickerBean) data;

                String path=imagePickerBean.getPath();
                List<String> selectedImageList=mImagePickerListRVHFAdapter.getSelectedImageList();
                if (selectedImageList.contains(path)) {
                    selectedImageList.remove(path);
                    /**
                     * notifyDataSetChanged刷新会让图片闪烁   不采用adapter刷新
                     * 直接找在当前itemView上找R.id.id_iv_selectbtn 设置状态
                     */
                    itemView.findViewById(R.id.id_iv_selectbtn).setSelected(false);
                } else {
                    selectedImageList.add(path);
                    /**
                     * notifyDataSetChanged刷新会让图片闪烁   不采用adapter刷新
                     * 直接找在当前itemView上找R.id.id_iv_selectbtn 设置状态
                     */
                    itemView.findViewById(R.id.id_iv_selectbtn).setSelected(true);
                }

                //更新标题栏
                setToolbarTitle("已选" +mImagePickerListRVHFAdapter.getSelectedImageList().size() + "张");
            }
        });
        mImagePickerListRVHFAdapter.setOnItemLongClickListener(new RVHeaderFooterAdapter.OnItemLongClickListener() {
            @Override
            public void onItemLongClick(View itemView, int position, Object data) {
                /**
                 * 长按查看图片
                 */
                ImagePickerBean imagePickerBean = (ImagePickerBean) data;
                String pathNow = imagePickerBean.getPath();
                int nowSelectedPos = 0;
                List<ImageShowBean> imageShowBeanList = new ArrayList<>();
                for (int i = 0; i < mNowDirImageList.size(); i++) {
                    ImageShowBean isb = new ImageShowBean();
                    String imagePath = mNowDirImageList.get(i).getPath();
                    isb.setImageUrl(imagePath);
                    isb.setTitle("图片" + (i + 1));
                    if (pathNow.equals(imagePath)) {
                        nowSelectedPos = i;
                    }
                    imageShowBeanList.add(isb);
                }
                ImageShowDataHelper.setDataAndToImageShow(mContext, imageShowBeanList, nowSelectedPos);

            }
        });
        return mImagePickerListRVHFAdapter;
    }

    @Override
    protected void toLoadMoreData() {

    }

    @Override
    protected int setupMenuResId() {
        return R.menu.menu_image_picker;
    }

    @Override
    protected int setupStatusBarColorResId() {
        return 0;
    }

    @Override
    protected void begin() {


        initViewBackground();

        iniRecyclerViewDir();

        mPresenter.gainData();

        setToolbarTitle("");
    }

    private void initViewBackground() {
        mViewBackground = findById(R.id.id_view_background);
        mViewBackground.setVisibility(View.GONE);
        mViewBackground.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                /**
                 * 相当于点击相册的外部   隐藏相册
                 */
                hideImageDir();
            }
        });
    }


    private RecyclerView mRecyclerViewDir;
    private ImagePickerDirListRecyclerAdapter mImagesPickerDirListRecyclerAdapter;

    private void iniRecyclerViewDir() {
        mRecyclerViewDir = findById(R.id.id_recycler_view_dir);
        /**
         *
         */
        mRecyclerViewDir.setLayoutManager(new LinearLayoutManager(this));
        mRecyclerViewDir.setHasFixedSize(true);
        mRecyclerViewDir.setItemAnimator(new DefaultItemAnimator());
        mRecyclerViewDir.addItemDecoration(new HorizontalDividerItemDecoration.Builder(this).build());
        //初始化
        // mRecyclerViewDir.setNestedScrollingEnabled(false);

        mImagesPickerDirListRecyclerAdapter = new ImagePickerDirListRecyclerAdapter(mImageDirBeanList,
                mRecyclerViewDir);

        mImagesPickerDirListRecyclerAdapter.setOnItemClickListener(new RVHeaderFooterAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View itemView, int position, Object data) {

                if (mImagesPickerDirListRecyclerAdapter.getLastClickDirItemChildView() != null) {
                    mImagesPickerDirListRecyclerAdapter.getLastClickDirItemChildView().setVisibility(View.GONE);
                }
                View imv = itemView.findViewById(R.id.id_tv_item_image_more);
                imv.setVisibility(View.VISIBLE);
                mImagesPickerDirListRecyclerAdapter.setLastClickDirItemChildView(imv);
                /**
                 *
                 */
                ImagePickerDirBean imageDirBean = (ImagePickerDirBean) data;
                /**
                 *
                 */
                imageDirOperator();
                if (mNowDirPathKey.equals(imageDirBean.getDirPath())) {
                    return;
                }
                mNowDirPathKey = imageDirBean.getDirPath();
                /**
                 *
                 */
                refreshImageData();


            }
        });


        mRecyclerViewDir.setAdapter(mImagesPickerDirListRecyclerAdapter);

    }

    private void refreshImageData() {

        mNowDirImageList.clear();
        if (mNowDirPathKey.equals(ALL_IMAGES_DIR_PATH_KEY)) {
            //
            mNowDirImageList.addAll(mAllImageList);
        } else {
            List<String> stringList = mGroupMap.get(mNowDirPathKey);
            if (stringList != null && stringList.size() > 0) {
                for (int i = 0; i < stringList.size(); i++) {
                    String pathTemp = mNowDirPathKey + File.separator + stringList.get(i);
                    ImagePickerBean imagePickerBean = new ImagePickerBean();
                    imagePickerBean.setPath(pathTemp);
                    mNowDirImageList.add(imagePickerBean);
                }
            }
        }
        //KLog.d("ASD 333");
        mImagePickerListRVHFAdapter.notifyDataSetChanged();

    }


    @Override
    protected boolean notNeedSwipeBack() {
        return false;
    }

    @Override
    protected boolean setupToolbarTitleCenter() {
        return true;
    }

    @Override
    protected boolean setupHideHomeAsUp() {
        return false;
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.id_menu_item_picker_dir:
                imageDirOperator();
                return true;
            case R.id.id_menu_item_picker:
                imagePickFinishOperator();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }


    private void imagePickFinishOperator() {
        List<String> selectedImageList= mImagePickerListRVHFAdapter.getSelectedImageList();
        ImagePickerDataWrapper imagePickerDataWrapper=new ImagePickerDataWrapper();
        imagePickerDataWrapper.setSelectedImageList(selectedImageList);
        Intent intent = new Intent();
        intent.putExtra("imagePickerDataWrapper",imagePickerDataWrapper);
        //
        setResult(RESULT_OK,intent);
        //###finish();
        finishAtyAfterTransition();
    }



    /**
     * 是否正在显示
     */
    private boolean isAnimationIng = false;

    private void imageDirOperator() {
        if (isAnimationIng) {
            return;
        }
        if (mViewBackground.getVisibility() == View.VISIBLE) {
            hideImageDir();
        } else {
            showImageDir();
        }
    }

    private void showImageDir() {
        mViewBackground.setVisibility(View.VISIBLE);
      /*  WindowManager.LayoutParams params=getWindow().getAttributes();
        params.alpha=0.4f;
        getWindow().setAttributes(params);*/
        isAnimationIng = true;
        /**
         * 坐标0
         */
        mRecyclerViewDir.animate().translationY(0).setListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator animation) {
                super.onAnimationEnd(animation);
                isAnimationIng = false;

            }
        });

        /**
         * 改变蒙版
         */
        ObjectAnimator objectAnimator = ObjectAnimator.ofFloat(mViewBackground, "alpha", 0f, 0.5f);
        //objectAnimator.setDuration(duration);
        objectAnimator.start();

       /*view_background.animate().alpha(0.5f);*/

    }

    private void hideImageDir() {
        isAnimationIng = true;
      /*  WindowManager.LayoutParams params=getWindow().getAttributes();
        params.alpha=1f;
        getWindow().setAttributes(params);*/

        mRecyclerViewDir.animate().translationY(-mRecyclerviewDirHeight).setListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator animation) {
                super.onAnimationEnd(animation);
                isAnimationIng = false;
                mViewBackground.setVisibility(View.GONE);
            }
        });

        /**
         * 改变蒙版
         */
        ObjectAnimator objectAnimator = ObjectAnimator.ofFloat(mViewBackground, "alpha", 0.5f, 0f);
        //objectAnimator.setDuration(duration);
        objectAnimator.start();

       /*view_background.animate().alpha(0f);*/

    }


    @Override
    public Context getMyContext() {
        return mContext;
    }

    @Override
    public void showProgress() {
        showSmileLoadingDialog();
    }

    @Override
    public void hideProgress() {
        hideLoadingDialog();
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
    public void setupData(ImagePickerDirDataWrapper data) {
        KLog.d("groupMap size " + data.getGroupMap().size());
        mGroupMap = data.getGroupMap();
        /**
         * 刷新文件夹数据
         */
        mImageDirBeanList.clear();
        mAllImageList.clear();

        ImagePickerDirBean showAllImageItemBean = null;//虚拟一个存放所有图片的文件夹
        for (String dirPathKey : mGroupMap.keySet()) {

            /**
             * 存放所有图片
             */
            List<String> nowDirImageList = mGroupMap.get(dirPathKey);
            if (nowDirImageList != null && nowDirImageList.size() > 0) {
                for (int i = 0; i < nowDirImageList.size(); i++) {
                    String path = dirPathKey + File.separator + nowDirImageList.get(i);
                    ImagePickerBean imagePickerBean = new ImagePickerBean();
                    imagePickerBean.setPath(path);
                    mAllImageList.add(imagePickerBean);
                }
            }
            /**
             * 单独存放图片
             */
            ImagePickerDirBean imageDirBean = new ImagePickerDirBean();
            imageDirBean.setDirPath(dirPathKey);
            KLog.d("dir path" + dirPathKey);
            String first = nowDirImageList.get(0);
            KLog.d("first Image" + first);
            imageDirBean.setDirImageIconPath(dirPathKey + File.separator + first);
            imageDirBean.setImageCount(nowDirImageList.size());
            mImageDirBeanList.add(imageDirBean);

            if (showAllImageItemBean == null) {
                //所有图片的文件夹
                showAllImageItemBean = new ImagePickerDirBean();
                showAllImageItemBean.setDirImageIconPath(dirPathKey + File.separator + first);
            }
        }
        KLog.d("ASD 222");

        /**
         * 添加到mImageDirBeanList的最前面
         */
        showAllImageItemBean.setDirPath(ALL_IMAGES_DIR_PATH_KEY);
        showAllImageItemBean.setImageCount(mAllImageList.size());
        mImageDirBeanList.add(0, showAllImageItemBean);

        /**
         * 刷新文件夹List
         */
        mImagesPickerDirListRecyclerAdapter.notifyDataSetChanged();

        /**
         * 数据初始化完后 测量高度   当然 mRecyclerViewDir 是 wrap-content
         */
        mRecyclerviewDirHeight = ViewTool.getMeasuredHeightMy(mRecyclerViewDir);

        /**
         * 偏移 到看不见的顶部
         */
        mRecyclerViewDir.setTranslationY(-mRecyclerviewDirHeight);


        /**
         * 刷新图片数据
         */

        refreshImageData();


    }
}
