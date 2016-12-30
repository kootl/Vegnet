package com.sunstar.vegnet.kootl.itemselect.ui;

import android.content.Intent;
import android.view.MenuItem;
import android.view.View;

import com.sunstar.vegnet.R;
import com.sunstar.vegnet.kootl.comm.base.BaseActivity;
import com.sunstar.vegnet.kootl.comm.rvheaderfooter.RVHeaderFooterAdapter;
import com.sunstar.vegnet.kootl.comm.tabhost.TabHostShowDataInfo;
import com.sunstar.vegnet.kootl.itemselect.adapter.ItemSelectRVHFAdapter;
import com.sunstar.vegnet.kootl.itemselect.bean.ItemSelectBean;
import com.sunstar.vegnet.kootl.itemselect.bean.ItemSelectDataWrapper;
import com.sunstar.vegnet.kootl.itemselect.contract.ItemSelectContract;
import com.sunstar.vegnet.kootl.itemselect.presenter.ItemSelectPresenterImpl;

import java.util.ArrayList;
import java.util.List;

public class ItemSelectActivity extends BaseActivity<ItemSelectContract.Presenter> implements ItemSelectContract.View<ItemSelectDataWrapper> {

    private boolean mIsMultiSelect;

    @Override
    protected int setupLayoutId() {
        return R.layout.activity_item_select;
    }

    @Override
    protected int setupToolbarResId() {
        return R.id.id_toolbar;
    }

    @Override
    protected ItemSelectContract.Presenter setupPresenter() {
        return new ItemSelectPresenterImpl(this);
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
        return R.id.id_swipe_refresh_layout;
    }

    @Override
    protected int setupRecyclerViewResId() {
        return R.id.id_recycler_view;
    }


    @Override
    protected RVHeaderFooterAdapter setupRVHeaderFooterAdapter() {
        mItemSelectRVHFAdapter = new ItemSelectRVHFAdapter(mItemSelectBeanList, mRecyclerView);
        mItemSelectRVHFAdapter.setOnItemClickListener(new RVHeaderFooterAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View itemView, int position, Object data) {
                ItemSelectBean itemSelectBean = (ItemSelectBean) data;
                mItemSelectBeanList.get(position).setSelected(!itemSelectBean.isSelected());
                if (mIsMultiSelect) {
                    mItemSelectRVHFAdapter.notifyDataSetChanged();
                } else {
                    finishSelectOperator();
                }
            }
        });
        return mItemSelectRVHFAdapter;
    }

    @Override
    protected void toLoadMoreData() {

    }

    @Override
    protected int setupMenuResId() {
        return mIsMultiSelect ? R.menu.menu_finish : 0;
    }

    @Override
    protected int setupStatusBarColorResId() {
        return 0;
    }

    private ItemSelectRVHFAdapter mItemSelectRVHFAdapter;
    private List<ItemSelectBean> mItemSelectBeanList = new ArrayList<>();

    @Override
    protected void begin() {
        mSwipeRefreshLayout.setEnabled(false);
        setToolbarTitle(R.string.nav_title_itemselect);

        String isMultiSelect =getBundleExtraStr1();
        if (isMultiSelect != null && isMultiSelect.equals("1")) {
            mIsMultiSelect = true;
        }
        mPresenter.gainData();
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

    @Override
    public void setupData(ItemSelectDataWrapper data) {
        mItemSelectRVHFAdapter.refreshDataList(data.getItemSelectBeanList());
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.id_menu_item_ok:
                if (!mIsMultiSelect) {
                    item.setVisible(false);
                }
                finishSelectOperator();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    private void finishSelectOperator() {
        ItemSelectDataWrapper itemSelectDataWrapper = new ItemSelectDataWrapper();
        itemSelectDataWrapper.setItemSelectBeanList(mItemSelectBeanList);
        Intent intent = new Intent();
        intent.putExtra("itemSelectDataWrapper", itemSelectDataWrapper);
        setResult(RESULT_OK, intent);
        //###finish();
        finishAtyAfterTransition();
    }
}
