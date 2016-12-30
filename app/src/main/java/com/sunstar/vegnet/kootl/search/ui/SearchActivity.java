package com.sunstar.vegnet.kootl.search.ui;

import android.support.v4.view.MenuItemCompat;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.SearchView;

import com.socks.library.KLog;
import com.sunstar.vegnet.R;
import com.sunstar.vegnet.data.BaseBeanShowApi;
import com.sunstar.vegnet.kootl.comm.base.BaseActivity;
import com.sunstar.vegnet.kootl.comm.rvheaderfooter.RVHeaderFooterAdapter;
import com.sunstar.vegnet.kootl.comm.tabhost.TabHostShowDataInfo;
import com.sunstar.vegnet.kootl.search.adapter.SearchListRVHFAdapter;
import com.sunstar.vegnet.kootl.search.bean.SearchListBean;
import com.sunstar.vegnet.kootl.search.contract.SearchListContract;
import com.sunstar.vegnet.kootl.search.presenter.SearchListPresenterImpl;

import java.util.ArrayList;
import java.util.List;

public class SearchActivity extends BaseActivity<SearchListContract.Presenter> implements SearchListContract.View<BaseBeanShowApi<SearchListBean>> {

    private List<SearchListBean.PagebeanBean.ContentlistBean> mContentlistBeanList = new ArrayList<>();

    private SearchView mSearchView;


    @Override
    protected int setupLayoutId() {
        return R.layout.activity_search;
    }

    @Override
    protected int setupToolbarResId() {
        return R.id.id_toolbar;
    }

    @Override
    protected SearchListContract.Presenter setupPresenter() {
        return new SearchListPresenterImpl(this);
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
        return R.id.id_recycler_view;
    }

    SearchListRVHFAdapter mSearchListRVHFAdapter;

    @Override
    protected RVHeaderFooterAdapter setupRVHeaderFooterAdapter() {
        mSearchListRVHFAdapter = new SearchListRVHFAdapter(mContentlistBeanList, mRecyclerView);
        mSearchListRVHFAdapter.setOnItemClickListener(new RVHeaderFooterAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View itemView, int position, Object data) {

            }
        });
        return mSearchListRVHFAdapter;
    }

    @Override
    protected void toLoadMoreData() {

    }

    @Override
    protected int setupMenuResId() {
        return R.menu.menu_search;
    }

    @Override
    protected int setupStatusBarColorResId() {
        return 0;
    }

    @Override
    protected void begin() {
        setToolbarTitle("");
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_search, menu);
        MenuItem menuItem = menu.findItem(R.id.id_menu_item_search);
        mSearchView = (SearchView) MenuItemCompat.getActionView(menuItem);
        //mSearchView = (SearchView) menuItem.getActionView();
        mSearchView.setMaxWidth(Integer.MAX_VALUE);
        //设置搜索图标是否显示在搜索框内
        // mSearchView.setIconifiedByDefault(false);//The default value is true   ，设置为false直接展开显示 左侧有放大镜  右侧无叉叉   有输入内容后有叉叉
        //!!! mSearchView.setIconified(false);//true value will collapse the SearchView to an icon, while a false will expand it. 左侧无放大镜 右侧直接有叉叉
        mSearchView.onActionViewExpanded();//直接展开显示 左侧无放大镜 右侧无叉叉 有输入内容后有叉叉 内部调用了setIconified(false);
        mSearchView.setImeOptions(EditorInfo.IME_ACTION_SEARCH);//设置输入法搜索选项字段，默认是搜索，可以是：下一页、发送、完成等
        mSearchView.setQueryHint("请输入关键字");//设置查询提示字符串
        mSearchView.setSubmitButtonEnabled(true);//提交按钮
        mSearchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                KLog.d("onQueryTextSubmit:" + query);
                mQueryKey = query;
                mPresenter.gainData();
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                KLog.d("onQueryTextChange:" + newText);
                return false;
            }
        });
        mSearchView.setOnQueryTextFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                KLog.d("onFocusChange:" + v + hasFocus);
            }
        });
        return true;
    }

    String mQueryKey = "";

    @Override
    public String getQueryKey() {
        return mQueryKey;
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
    public void setupData(BaseBeanShowApi<SearchListBean> data) {
        /**
         * 页码置1
         */
        mSearchListRVHFAdapter.resetPageNum();
        //KLog.d(data);
        List<SearchListBean.PagebeanBean.ContentlistBean> list = data.getShowapi_res_body().getPagebean().getContentlist();

        KLog.d("XXX" + list.size());
        mSearchListRVHFAdapter.refreshDataList(list);
    }
}
