package com.sunstar.vegnet.news.ui;

import android.widget.ImageView;
import android.widget.TextView;

import com.sunstar.vegnet.R;
import com.sunstar.vegnet.kootl.comm.base.BaseActivity;
import com.sunstar.vegnet.kootl.comm.factory.imageloader.ImageLoaderFactory;
import com.sunstar.vegnet.kootl.comm.rvheaderfooter.RVHeaderFooterAdapter;
import com.sunstar.vegnet.kootl.comm.tabhost.TabHostShowDataInfo;
import com.sunstar.vegnet.news.bean.NewsListBean;
import com.sunstar.vegnet.news.contract.NewsDetailContract;
import com.sunstar.vegnet.news.presenter.NewsDetailPresenterImpl;


public class NewsDetailActivity extends BaseActivity<NewsDetailContract.Presenter> implements NewsDetailContract.View<NewsListBean.PagebeanBean.ContentlistBean>{


    @Override
    protected int setupLayoutId() {
        return R.layout.activity_news_detail;
    }

    @Override
    protected int setupToolbarResId() {
        return R.id.id_toolbar;
    }

    @Override
    protected NewsDetailContract.Presenter setupPresenter() {
        return new NewsDetailPresenterImpl(this);
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
        return 0;
    }

    @Override
    protected int setupStatusBarColorResId() {
        return 0;
    }

    TextView id_tv_content;
    ImageView id_iv_pic;
    @Override
    protected void begin() {
        id_tv_content=findById(R.id.id_tv_content);
        id_iv_pic=findById(R.id.id_iv_pic);
        //
        mPresenter.gainData();
    }

    @Override
    public void showProgress() {
        showSmileLoadingDialog();
    }

    @Override
    public void hideProgress() {
        hideLoadingDialog(500);
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
    public void setupData(NewsListBean.PagebeanBean.ContentlistBean data) {
        setToolbarTitle(data.getTitle());

        id_tv_content.setText(data.getDesc());
        if (data.isHavePic()){
          //  GlideHelper.displayImage(id_iv_pic,data.getImageurls().get(0).toString());
            ImageLoaderFactory.getManager().displayImage(id_iv_pic,data.getImageurls().get(0).toString());
        }
    }
}
