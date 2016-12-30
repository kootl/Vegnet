package com.sunstar.vegnet.kootl.userinfo.ui;

import android.content.Intent;
import android.graphics.Bitmap;
import android.view.MenuItem;
import android.view.View;

import com.makeramen.roundedimageview.RoundedImageView;
import com.sunstar.vegnet.R;
import com.sunstar.vegnet.kootl.comm.base.BaseActivity;
import com.sunstar.vegnet.kootl.comm.base.BasePresenter;
import com.sunstar.vegnet.kootl.comm.factory.imageloader.ImageLoaderFactory;
import com.sunstar.vegnet.kootl.comm.factory.imageloader.interfaces.ILoadImageCallback;
import com.sunstar.vegnet.kootl.comm.helper.SelectPhotoHelper;
import com.sunstar.vegnet.kootl.comm.listener.OnNotFastClickListener;
import com.sunstar.vegnet.kootl.comm.rvheaderfooter.RVHeaderFooterAdapter;
import com.sunstar.vegnet.kootl.comm.tabhost.TabHostShowDataInfo;

public class UserInfoActivity extends BaseActivity<BasePresenter> {


    @Override
    protected int setupLayoutId() {
        return R.layout.activity_user_info;
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
        return R.menu.menu_edit;
    }

    @Override
    protected int setupStatusBarColorResId() {
        return 0;
    }


    private RoundedImageView mRoundedImageView;

    @Override
    protected void begin() {
        setToolbarTitle(getString(R.string.nav_title_userinfo));

        mRoundedImageView = findById(R.id.id_iv_userface);
        mRoundedImageView.setOnClickListener(new OnNotFastClickListener() {
            @Override
            protected void onNotFastClick(View v) {
                SelectPhotoHelper.initBottomSheetDialog(UserInfoActivity.this);
            }
        });
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        //
        SelectPhotoHelper.callAtOnActivityResult(this, requestCode, resultCode, data, new SelectPhotoHelper.OnUCropBackImagePathListener() {
            @Override
            public void onUCropBackImagePath(String imagePath) {
                 /*  Bitmap bitmap = ImageTool.decodeSampledBitmapFromPath(imagePath,
                            getResources().getDimensionPixelSize(R.dimen.base_userface_width),
                            getResources().getDimensionPixelSize(R.dimen.base_userface_height));*/
            /*    GlideHelper.loadImage(imagePath, new GlideHelper.OnLoadImageBackListener() {
                    @Override
                    public void onLoadImageBack(Bitmap bitmap) {
                        mRoundedImageView.setImageBitmap(bitmap);
                    }
                });*/
                ImageLoaderFactory.getManager().loadImage(imagePath, new ILoadImageCallback() {
                    @Override
                    public void onLoadImageBack(Bitmap bitmap) {
                        mRoundedImageView.setImageBitmap(bitmap);
                    }
                });
            }

            @Override
            public void onUCropBackError(Throwable cropError) {

            }
        });

    }


    private void editOperator() {
        goToUserInfoEdit();
    }

    private void goToUserInfoEdit() {
        //
        UserInfoEditActivity.startMe(this, UserInfoEditActivity.class);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.id_menu_item_edit:
                editOperator();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

}
