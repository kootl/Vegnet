package com.sunstar.vegnet.kootl.rvItemtouch.ui;

import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.MenuItem;

import com.sunstar.vegnet.R;
import com.sunstar.vegnet.kootl.comm.base.BaseActivity;
import com.sunstar.vegnet.kootl.comm.base.BasePresenter;
import com.sunstar.vegnet.kootl.comm.rvheaderfooter.RVHeaderFooterAdapter;
import com.sunstar.vegnet.kootl.comm.tabhost.TabHostShowDataInfo;
import com.sunstar.vegnet.kootl.rvItemtouch.adapter.ItemTouchHelperRVAdapter;
import com.sunstar.vegnet.kootl.rvItemtouch.bean.ItemTouchBean;

import java.util.ArrayList;
import java.util.List;

import static com.sunstar.vegnet.R.id.id_recycler_view;

public class ItemTouchActivity extends BaseActivity<BasePresenter> {
    @Override
    protected int setupLayoutId() {
        return R.layout.activity_item_touch;
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
        return R.menu.menu_finish;
    }

    @Override
    protected int setupStatusBarColorResId() {
        return 0;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.id_menu_item_ok:
                StringBuffer sb = new StringBuffer("订阅了：");
                for (int i = 0; i < mItemTouchHelperRVAdapter.getItemTouchBeanList().size(); i++) {
                    if (mItemTouchHelperRVAdapter.getItemTouchBeanList().get(i).getViewType()
                            == ItemTouchHelperRVAdapter.VIEW_TYPE_MORE_TITLE) {
                        break;
                    }
                    sb.append(mItemTouchHelperRVAdapter.getItemTouchBeanList().get(i).getText());
                    sb.append(",");
                }
                // showMessageDialog(sb.toString());

                showSnack(sb.toString());
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    ItemTouchHelperRVAdapter mItemTouchHelperRVAdapter;
    RecyclerView mRecyclerView;

    @Override
    protected void begin() {
        setToolbarTitle(R.string.nav_title_channel);

        List<ItemTouchBean> itemTouchBeanList = new ArrayList<>();
        for (int i = 0; i < 20; i++) {
            ItemTouchBean itemTouchBean = new ItemTouchBean();
            itemTouchBean.setId(i);
            if (i == 0) {
                itemTouchBean.setViewType(ItemTouchHelperRVAdapter.VIEW_TYPE_MY_TITLE);
                itemTouchBean.setText("我的订阅");
                itemTouchBean.setText2("单击增删订阅，长按拖拽排序");
            } else if (i > 0 && i < 12) {
                itemTouchBean.setViewType(ItemTouchHelperRVAdapter.VIEW_TYPE_MY_LIST);
                itemTouchBean.setText("sss" + i);
                itemTouchBean.setText2("fff" + i);
            } else if (i == 12) {
                itemTouchBean.setViewType(ItemTouchHelperRVAdapter.VIEW_TYPE_MORE_TITLE);
                itemTouchBean.setText("更多订阅");
                itemTouchBean.setText2("");
            } else if (i > 12) {
                itemTouchBean.setViewType(ItemTouchHelperRVAdapter.VIEW_TYPE_MORE_LIST);
                itemTouchBean.setText("sss" + i);
                itemTouchBean.setText2("fff" + i);
            }
            itemTouchBeanList.add(itemTouchBean);
        }

        mRecyclerView = (RecyclerView) findViewById(id_recycler_view);
        // id_recycler_view.setLayoutManager(new LinearLayoutManager(this));
        mRecyclerView.setLayoutManager(new GridLayoutManager(this, 4));
        mRecyclerView.setHasFixedSize(true);
        mRecyclerView.setItemAnimator(new DefaultItemAnimator());
        // id_recycler_view.addItemDecoration(new HorizontalDividerItemDecoration.Builder(this).build());

        mItemTouchHelperRVAdapter = new ItemTouchHelperRVAdapter(itemTouchBeanList,
                mRecyclerView, R.layout.item_grid_normal);

        mRecyclerView.setAdapter(mItemTouchHelperRVAdapter);



    }
}
