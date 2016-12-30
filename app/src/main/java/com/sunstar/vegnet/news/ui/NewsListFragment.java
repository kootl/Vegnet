package com.sunstar.vegnet.news.ui;


import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.socks.library.KLog;
import com.sunstar.vegnet.R;
import com.sunstar.vegnet.data.BaseBeanShowApi;
import com.sunstar.vegnet.data.ImagesDatas;
import com.sunstar.vegnet.kootl.banner.adapter.ImageBannerPageAdapter;
import com.sunstar.vegnet.kootl.banner.bean.ImageBannerBean;
import com.sunstar.vegnet.kootl.banner.widget.BannerViewPager;
import com.sunstar.vegnet.kootl.comm.app.DataHolderSingleton;
import com.sunstar.vegnet.kootl.comm.adapter.KooFragmentPagerAdapter;
import com.sunstar.vegnet.kootl.comm.base.BaseFragment;
import com.sunstar.vegnet.kootl.comm.bus.event.EventBackListTop;
import com.sunstar.vegnet.kootl.comm.bus.event.EventShowHideTabHost;
import com.sunstar.vegnet.kootl.comm.listener.OnScrollWithUpDownListener;
import com.sunstar.vegnet.kootl.comm.rvheaderfooter.RVHeaderFooterAdapter;
import com.sunstar.vegnet.kootl.imageshow.bean.ImageShowBean;
import com.sunstar.vegnet.kootl.imageshow.helper.ImageShowDataHelper;
import com.sunstar.vegnet.kootl.webview.ui.X5WebViewActivity;
import com.sunstar.vegnet.news.adapter.NewsListRVHFAdapter;
import com.sunstar.vegnet.news.bean.NewsListBean;
import com.sunstar.vegnet.news.contract.NewsListContract;
import com.sunstar.vegnet.news.presenter.NewsListPresenterImpl;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;
import java.util.List;

/**
 * Use the {@link NewsListFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class NewsListFragment extends BaseFragment<NewsListContract.Presenter> implements NewsListContract.View<BaseBeanShowApi<NewsListBean>> {


    List<NewsListBean.PagebeanBean.ContentlistBean> mContentlistBeanList = new ArrayList<>();

    public NewsListFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @param param3 Parameter 3.
     * @return A new instance of fragment NewsListFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static NewsListFragment newInstance(String param1, String param2, int param3) {
        NewsListFragment fragment = new NewsListFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        args.putInt(ARG_PARAM3, param3);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
            mParam3 = getArguments().getInt(ARG_PARAM3);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        //return inflater.inflate(R.layout.fragment_news_list, container, false);
        return super.onCreateView(inflater, container, savedInstanceState);
    }

    @Override
    protected int setupLayoutId() {
        return R.layout.fragment_news_list;
    }

    @Override
    protected int setupViewPagerResId() {
        return 0;
    }

    @Override
    protected int setupTabLayoutResId() {
        return 0;
    }

    @Override
    protected KooFragmentPagerAdapter setupKooFragmentPagerAdapter() {
        return null;
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
    protected boolean needNoDataNotice() {
        return true;
    }

    private NewsListRVHFAdapter mNewsListRVHFAdapter;

    @Override
    protected RVHeaderFooterAdapter setupRVHeaderFooterAdapter() {
        mNewsListRVHFAdapter = new NewsListRVHFAdapter(mContentlistBeanList,
                mRecyclerView);
        //banner图
        mNewsListRVHFAdapter.setHeaderView(R.layout.base_image_banner);
        mNewsListRVHFAdapter.setOnItemClickListener(new RVHeaderFooterAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View itemView, int position, Object data) {
                KLog.d("onItemClick");
                NewsListBean.PagebeanBean.ContentlistBean contentlistBean = (NewsListBean.PagebeanBean.ContentlistBean) data;
                DataHolderSingleton.getInstance().putData("contentlistBean", contentlistBean);
                //startAty(NewsDetailActivity.class);
                String link = contentlistBean.getLink();
                Intent intent = new Intent(getActivity(), X5WebViewActivity.class);
                intent.setData(Uri.parse(link));
                startActivity(intent);
            }
        });
        return mNewsListRVHFAdapter;
    }

    @Override
    protected void toLoadMoreData() {
        /**
         * 取下一页数据
         */
        mPresenter.gainMoreData(mNewsListRVHFAdapter.getNextPageNum());
    }

    @Override
    protected void toRefreshData() {
        //刷新数据
        mPresenter.gainData();
    }

    @Override
    protected NewsListContract.Presenter setupPresenter() {
        return new NewsListPresenterImpl(this);
    }

    @Override
    protected void begin(View rootLayout) {

        //请求数据
        toRefreshData();

        /**
         * banner数据
         */
        for (int i = 0; i < ImagesDatas.imageThumbUrls.length; i++) {
            if (i > 4) {
                break;
            }
            ImageBannerBean imageBannerBean = new ImageBannerBean();
            imageBannerBean.setImageUrl(ImagesDatas.imageThumbUrls[i]);
            imageBannerBean.setImageText("图片xx" + i);
            mImageBannerBeanList.add(imageBannerBean);
        }
        //初始化
        initBannerViewPager();


        mRecyclerView.addOnScrollListener(new OnScrollWithUpDownListener() {
            @Override
            public void onScrollUp() {
                EventBus.getDefault().post(new EventShowHideTabHost(false));
            }

            @Override
            public void onScrollDown() {
                EventBus.getDefault().post(new EventShowHideTabHost(true));
            }
        });

    }


    @Override
    public void showProgress() {
        //KLog.d("showProgress");
        showSwipeRefreshLayout();
    }

    @Override
    public void hideProgress() {
        hideSwipeRefreshLayout();

    }

    @Override
    public void showMessage(String msg) {
        showMessageDialog(msg);
    }

    @Override
    public void hideNoDataNotice() {
        hideNoDataNoticeView();
    }

    @Override
    public void showNoDataNotice() {
        showNoDataNoticeView();
    }

    @Override
    public void setupData(BaseBeanShowApi<NewsListBean> data) {

        /**
         * 页码置1
         */
        mNewsListRVHFAdapter.resetPageNum();
        //KLog.d(data);
        List<NewsListBean.PagebeanBean.ContentlistBean> list = data.getShowapi_res_body().getPagebean().getContentlist();

        //KLog.d("XXX" + list.size());
        mNewsListRVHFAdapter.refreshDataList(list);
    }

    @Override
    public void showMoreDataLoading() {
        KLog.d("showMoreDataLoading");
        showFooterViewDataLoading();
    }

    @Override
    public void hideMoreDataLoading() {
        KLog.d("hideMoreDataLoading");
        showFooterViewPageLoadFinish();
    }

    @Override
    public void showMoreDataLoadComplete() {
        KLog.d("showMoreDataLoadComplete");
        showFooterViewLoadComplete();
    }

    @Override
    public void setupMoreData(BaseBeanShowApi<NewsListBean> data) {
        KLog.d("now page" + mNewsListRVHFAdapter.getPageNum());
        mNewsListRVHFAdapter.appendDataList(data.getShowapi_res_body().getPagebean().getContentlist());
        /**
         * 成功返回数据并且设置后  页码翻页
         */
        mNewsListRVHFAdapter.turnNextPageNum();
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onEventMainThread(EventBackListTop event) {
        if (mRecyclerView != null) {
            mRecyclerView.smoothScrollToPosition(0);
        }
    }


    BannerViewPager mBannerViewPager;
    List<ImageBannerBean> mImageBannerBeanList = new ArrayList<>();

    private void initBannerViewPager() {
        View headerView = mNewsListRVHFAdapter.getHeaderView();
        if (headerView != null) {
            /**
             *
             */
            mBannerViewPager = (BannerViewPager) headerView.findViewById(R.id.id_banner_view_pager);
            View bannerBottomView = headerView.findViewById(R.id.id_rl_banner_bottom);
            ImageBannerPageAdapter pagerAdapter = new ImageBannerPageAdapter(mImageBannerBeanList,
                    mBannerViewPager, bannerBottomView, true);
            pagerAdapter.setOnItemClickListener(new ImageBannerPageAdapter.OnItemClickListener() {
                @Override
                public void onItemClick(View itemView, int position, Object data) {
                    List<ImageShowBean> imageShowBeanList = new ArrayList<>();
                    for (int i = 0; i < mImageBannerBeanList.size(); i++) {
                        ImageShowBean imageShowBean = new ImageShowBean();
                        imageShowBean.setTitle(mImageBannerBeanList.get(i).getImageText());
                        imageShowBean.setContent(mImageBannerBeanList.get(i).getImageText());
                        imageShowBean.setImageUrl(mImageBannerBeanList.get(i).getImageUrl());
                        imageShowBeanList.add(imageShowBean);
                    }
                    ImageShowDataHelper.setDataAndToImageShow(mContext, imageShowBeanList, position);
                }
            });
            mBannerViewPager.setAdapter(pagerAdapter);
        }
    }


    @Override
    public void onResume() {
        super.onResume();
        if (mBannerViewPager != null) {
            mBannerViewPager.startAutoPlay();
        }
    }

    @Override
    public void onStop() {
        super.onStop();
        if (mBannerViewPager != null) {
            mBannerViewPager.stopAutoPlay();
        }
    }

}
