package com.sunstar.vegnet.kootl.guide.ui;

import android.support.v4.view.ViewPager;
import android.view.View;
import android.widget.TextView;

import com.sunstar.vegnet.R;
import com.sunstar.vegnet.kootl.comm.base.BaseActivity;
import com.sunstar.vegnet.kootl.comm.base.BasePresenter;
import com.sunstar.vegnet.kootl.comm.config.CommFinalData;
import com.sunstar.vegnet.kootl.comm.viewpager.DepthPageTransformer;
import com.sunstar.vegnet.kootl.comm.rvheaderfooter.RVHeaderFooterAdapter;
import com.sunstar.vegnet.kootl.comm.tabhost.TabHostShowDataInfo;
import com.sunstar.vegnet.kootl.comm.tool.SharedPreferencesTool;
import com.sunstar.vegnet.kootl.guide.adapter.GuidePagerAdapter;
import com.sunstar.vegnet.kootl.guide.bean.GuideBean;
import com.sunstar.vegnet.kootl.guide.contract.GuideContract;
import com.sunstar.vegnet.kootl.guide.presenter.GuidePresenterImpl;
import com.sunstar.vegnet.ui.MainActivity;

import java.util.ArrayList;
import java.util.List;

public class GuideActivity extends BaseActivity<BasePresenter> implements GuideContract.View<List<GuideBean>> {

    @Override
    protected int setupLayoutId() {
        return R.layout.activity_guide;
    }

    @Override
    protected int setupToolbarResId() {
        return 0;
    }

    @Override
    protected BasePresenter setupPresenter() {
        return new GuidePresenterImpl(this);
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
        return false;
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
        return -1;
    }

    @Override
    protected void begin() {

        initGuide();


        mPresenter.gainData();
    }

    private void initGuide() {
        TextView id_tv_jump_over = findById(R.id.id_tv_jump_over);
        id_tv_jump_over.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                goToMain();
               /* OkCancelDialogFragment okCancelDialogFragment=OkCancelDialogFragment.newInstance("XX","ggg");
                okCancelDialogFragment.show(getSupportFragmentManager(),"XXXS");*/
            }
        });
        ViewPager viewPager = findById(R.id.id_view_pager);
        mGuidePagerAdapter = new GuidePagerAdapter(mGuideBeanList);
        mGuidePagerAdapter.setOnGuideClickListener(new GuidePagerAdapter.OnGuideClickListener() {
            @Override
            public void onJumpInClick(View v) {
                goToMain();
            }
        });
        viewPager.setAdapter(mGuidePagerAdapter);
        viewPager.setPageTransformer(true, new DepthPageTransformer());
    }

    private void goToMain() {
        SharedPreferencesTool.put(mContext, CommFinalData.SP_HAS_OPENED_GUIDE, true);
        //startAty(MainActivity.class);
        MainActivity.startMe(mContext, MainActivity.class);
        //##finish();
        finishAtyAfterTransition();
    }

    @Override
    public void showProgress() {

    }

    @Override
    public void hideProgress() {

    }

    @Override
    public void showNoDataNotice() {

    }

    @Override
    public void hideNoDataNotice() {

    }

    @Override
    public void showMessage(String msg) {

    }

    List<GuideBean> mGuideBeanList = new ArrayList<>();
    GuidePagerAdapter mGuidePagerAdapter;

    @Override
    public void setupData(List<GuideBean> data) {
        mGuidePagerAdapter.refreshDataList(data);
    }
}
