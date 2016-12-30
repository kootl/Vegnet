package com.sunstar.vegnet.kootl.comm.base;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.socks.library.KLog;
import com.sunstar.vegnet.R;
import com.sunstar.vegnet.kootl.comm.adapter.KooFragmentPagerAdapter;
import com.sunstar.vegnet.kootl.comm.bus.event.EventBase;
import com.sunstar.vegnet.kootl.comm.config.CommFinalData;
import com.sunstar.vegnet.kootl.comm.listener.OnNotFastClickListener;
import com.sunstar.vegnet.kootl.comm.rvheaderfooter.RVHeaderFooterAdapter;
import com.sunstar.vegnet.kootl.comm.tool.DialogTool;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

/**
 * Created by louisgeek on 2016/10/31.
 */

public abstract class BaseFragment<P extends BasePresenter> extends Fragment {
    //
    protected static final String ARG_PARAM1 = "param1";
    protected static final String ARG_PARAM2 = "param2";
    protected static final String ARG_PARAM3 = "param3";

    protected String mParam1;
    protected String mParam2;
    protected int mParam3;
    //
    protected P mPresenter;

    protected static String mTag;
    protected Context mContext;
    protected View mRootLayout;
    protected ViewPager mViewPager;
    protected TabLayout mTabLayout;
    protected KooFragmentPagerAdapter mKooFragmentPagerAdapter;
    protected SwipeRefreshLayout mSwipeRefreshLayout;
    protected RecyclerView mRecyclerView;

    private RVHeaderFooterAdapter mRVHeaderFooterAdapter;

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        mContext = context;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        mTag = this.getClass().getSimpleName();

        mRootLayout = inflater.inflate(setupLayoutId(), container, false);

        mPresenter = setupPresenter();


        if (setupViewPagerResId() != 0) {
            initViewPager();
            //TabLayout 依托 ViewPager
            if (setupTabLayoutResId() != 0) {
                initTabLayout();
            }
        }

        if (setupSwipeRefreshLayoutResId() != 0) {
            mSwipeRefreshLayout = findById(setupSwipeRefreshLayoutResId());
            // mSwipeRefreshLayout.setColorSchemeResources(android.R.color.holo_blue_light, android.R.color.holo_red_light, android.R.color.holo_orange_light, android.R.color.holo_green_light);
            mSwipeRefreshLayout.setColorSchemeResources(R.color.colorAccent);
            mSwipeRefreshLayout.setVisibility(View.VISIBLE);
            mSwipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
                @Override
                public void onRefresh() {
                    KLog.d("onRefresh刷新数据");
                    toRefreshData();
                }
            });
        }

        initRecyclerViewAndAdapter();

        if (needNoDataNotice()) {
            initNoDataNotice();
        }

        /**
         * last
         */
        begin(mRootLayout);

        return mRootLayout;
        //return super.onCreateView(inflater, container, savedInstanceState);
    }

    @Override
    public void onStart() {
        super.onStart();
        EventBus.getDefault().register(this);
    }

    @Override
    public void onStop() {
        super.onStop();
        EventBus.getDefault().unregister(this);
    }

    /**
     * ===================================protected abstract==================================
     *
     * @return
     */
    protected abstract int setupLayoutId();

    protected abstract int setupViewPagerResId();

    protected abstract int setupTabLayoutResId();

    protected abstract KooFragmentPagerAdapter setupKooFragmentPagerAdapter();

    protected abstract int setupSwipeRefreshLayoutResId();

    protected abstract int setupRecyclerViewResId();

    protected abstract boolean needNoDataNotice();

    protected abstract RVHeaderFooterAdapter setupRVHeaderFooterAdapter();

    protected abstract void toLoadMoreData();

    protected abstract void toRefreshData();

    protected abstract P setupPresenter();

    protected abstract void begin(View rootLayout);


    /**
     * =======================================protected===================================
     */
    protected <V extends View> V findById(int resId) {
        return (V) mRootLayout.findViewById(resId);
    }

    protected void showSwipeRefreshLayout() {
        // mSwipeRefreshLayout.setRefreshing(true);
        mSwipeRefreshLayout.post(new Runnable() {
            @Override
            public void run() {
                mSwipeRefreshLayout.setRefreshing(true);
            }
        });
    }

    protected void hideSwipeRefreshLayout() {
        mSwipeRefreshLayout.postDelayed(new Runnable() {
            @Override
            public void run() {
                mSwipeRefreshLayout.setRefreshing(false);
            }
        }, 600);
    }

    /**
     * 加载中
     */
    protected void showFooterViewDataLoading() {
        mRVHeaderFooterAdapter.setDataLoading(true);
        mRVHeaderFooterAdapter.setLoadComplete(false);
        //
        mRVHeaderFooterAdapter.setFooterView(R.layout.layout_footer_loadmore);
        View footView = mRVHeaderFooterAdapter.getFooterView();
        View id_douban_loading_view = footView.findViewById(R.id.id_douban_loading_view);
        View id_tv_load_finish = footView.findViewById(R.id.id_tv_load_finish);
        id_douban_loading_view.setVisibility(View.VISIBLE);
        id_tv_load_finish.setVisibility(View.GONE);
    }

    /**
     * 一页加载完成
     */
    protected void showFooterViewPageLoadFinish() {
        mRVHeaderFooterAdapter.setDataLoading(false);
        mRVHeaderFooterAdapter.setLoadComplete(false);
        //加载时候设置
        mRVHeaderFooterAdapter.setFooterView(null);

    }

    /**
     * 所有数据加载完成
     */
    protected void showFooterViewLoadComplete() {
        mRVHeaderFooterAdapter.setLoadComplete(true);
        mRVHeaderFooterAdapter.setDataLoading(false);
        //
        mRVHeaderFooterAdapter.setFooterView(R.layout.layout_footer_loadmore);
        View footView = mRVHeaderFooterAdapter.getFooterView();
        View id_douban_loading_view = footView.findViewById(R.id.id_douban_loading_view);
        View id_tv_load_finish = footView.findViewById(R.id.id_tv_load_finish);
        id_douban_loading_view.setVisibility(View.GONE);
        id_tv_load_finish.setVisibility(View.VISIBLE);
    }

    protected void showMessageDialog(String msg) {
        DialogTool.showMsgDialog(getChildFragmentManager(), msg);
    }

    protected void showLoadingDialog() {
        showLoadingDialog("");
    }

    protected void showLoadingDialog(String msg) {
        if (getActivity() == null || getActivity().isFinishing()) {
            return;
        }
        DialogTool.showLoadingDialog(getChildFragmentManager(), msg);
    }

    protected void showSmileLoadingDialog() {
        showSmileLoadingDialog("");
    }

    protected void showSmileLoadingDialog(String msg) {
        if (getActivity() == null || getActivity().isFinishing()) {
            return;
        }
        DialogTool.showLoadingSmileDialog(getChildFragmentManager(), msg);
    }

    protected void hideLoadingDialog() {
        DialogTool.hideDialogFragment();
    }

    protected void hideLoadingDialog(long delayMillis) {
        DialogTool.hideDialogFragment(delayMillis);
    }


    protected void startAty(Class<?> clazz) {
        Intent intent = new Intent(getActivity(), clazz);
        startActivity(intent);
    }

    protected void startAty(Class<?> clazz, Bundle bundle) {
        Intent intent = new Intent(getActivity(), clazz);
        intent.putExtras(bundle);
        startActivity(intent);
    }

    protected Bundle createBundleExtraInt1(int value1) {
        Bundle bundle = new Bundle();
        bundle.putInt(CommFinalData.BUNDLE_EXTRA_KEY_1, value1);
        return bundle;
    }

    protected Bundle createBundleExtraStr1(String value1) {
        Bundle bundle = new Bundle();
        bundle.putString(CommFinalData.BUNDLE_EXTRA_KEY_1, value1);
        return bundle;
    }

    protected void hideNoDataNoticeView() {
        if (mNoDataNoticeView != null) {
            mNoDataNoticeView.setVisibility(View.GONE);
        }
    }

    protected void showNoDataNoticeView() {
        if (mNoDataNoticeView != null) {
            mNoDataNoticeView.setVisibility(View.VISIBLE);
        }
    }

    /**
     * ========================================private=======================================
     */
    protected View mNoDataNoticeView;

    private void initNoDataNotice() {
        mNoDataNoticeView = findById(R.id.id_ll_no_date_notice);
        mNoDataNoticeView.setVisibility(View.GONE);
        mNoDataNoticeView.setOnClickListener(new OnNotFastClickListener() {
            @Override
            protected void onNotFastClick(View v) {
                hideNoDataNoticeView();
                toRefreshData();
            }
        });
    }


    private void initViewPager() {

        mViewPager = findById(setupViewPagerResId());
        /**
         * 常规使用  配合TabLayout   记得setupTabLayoutResId
         mKooFragmentPagerAdapter = new KooFragmentPagerAdapter(getChildFragmentManager());
         //KooFragmentPagerAdapter pagerAdapter = new KooFragmentPagerAdapter(getSupportFragmentManager());
         mKooFragmentPagerAdapter.addFragment(NewsPagerFragment.newInstance("头条", ""), "头条");
         mKooFragmentPagerAdapter.addFragment(NewsPagerFragment.newInstance("娱乐", ""), "娱乐");
         mKooFragmentPagerAdapter.addFragment(NewsPagerFragment.newInstance("手机", ""), "手机");
         */
        mKooFragmentPagerAdapter = setupKooFragmentPagerAdapter();

        if (mKooFragmentPagerAdapter != null) {
          /*  KooFragmentPagerAdapter pagerAdapter = new KooFragmentPagerAdapter(getChildFragmentManager());
            pagerAdapter.addFragment(NewsPagerFragment.newInstance(NEWS_TYPE_TOP + "", ""), getString(R.string.news_title_top));
          */
            mViewPager.setAdapter(mKooFragmentPagerAdapter);
        }

    }

    private void initTabLayout() {
        mTabLayout = findById(setupTabLayoutResId());
        mTabLayout.setVisibility(View.VISIBLE);
        if (mViewPager != null) {
            mTabLayout.setupWithViewPager(mViewPager);
        }
    }

    private void initRecyclerViewAndAdapter() {
        //  KLog.d("initRecyclerViewAndAdapter:setupRecyclerViewResId" + setupRecyclerViewResId());
        if (setupRecyclerViewResId() == 0) {
            return;
        }
        /**
         *设置RecyclerView
         */
        mRecyclerView = findById(setupRecyclerViewResId());
        mRecyclerView.setLayoutManager(new LinearLayoutManager(mContext));
        mRecyclerView.setHasFixedSize(true);
        mRecyclerView.setItemAnimator(new DefaultItemAnimator());
        //mRecyclerView.addItemDecoration(new HorizontalDividerItemDecoration.Builder(mContext).build());

        /**
         *设置Adapter
         */
        mRVHeaderFooterAdapter = setupRVHeaderFooterAdapter();
        if (mRVHeaderFooterAdapter != null) {
            /**
             *RecyclerView设置Adapter
             */
            mRecyclerView.setAdapter(mRVHeaderFooterAdapter);

            /**
             *继续设置RecyclerView
             */
            mRecyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
                @Override
                public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                    super.onScrollStateChanged(recyclerView, newState);

                    /**
                     * 上拉加载更多
                     */
                    if (!mRVHeaderFooterAdapter.isDataLoading()
                            && !mRVHeaderFooterAdapter.isLoadComplete()
                            && RVHeaderFooterAdapter.isReachedBottom(recyclerView)//到达底部 静态方法
                            ) {
                        KLog.d("isReachedBottom");
                        //需要加载更多数据
                        toLoadMoreData();
                    }
                }

                @Override
                public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                    super.onScrolled(recyclerView, dx, dy);
                    /* do nothing */
                }
            });

        }
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onEventMainThread(EventBase event) {

    }
}
