package com.sunstar.vegnet.news.ui;


import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.sunstar.vegnet.R;
import com.sunstar.vegnet.data.BaseBeanShowApi;
import com.sunstar.vegnet.kootl.comm.adapter.KooFragmentPagerAdapter;
import com.sunstar.vegnet.kootl.comm.base.BaseFragment;
import com.sunstar.vegnet.kootl.comm.bus.event.EventBackListTop;
import com.sunstar.vegnet.kootl.comm.listener.OnNotFastClickListener;
import com.sunstar.vegnet.kootl.comm.rvheaderfooter.RVHeaderFooterAdapter;
import com.sunstar.vegnet.kootl.rvItemtouch.ui.ItemTouchActivity;
import com.sunstar.vegnet.news.bean.NewsChannelBean;
import com.sunstar.vegnet.news.contract.NewsChannelContract;
import com.sunstar.vegnet.news.presenter.NewsChannelPresenterImpl;

import org.greenrobot.eventbus.EventBus;

import java.util.List;



/**
 * Use the {@link NewsPagerFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class NewsPagerFragment extends BaseFragment<NewsChannelContract.Presenter> implements NewsChannelContract.View<BaseBeanShowApi<NewsChannelBean>> {

    public NewsPagerFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @param param3 Parameter 3.
     * @return A new instance of fragment NewsPagerFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static NewsPagerFragment newInstance(String param1, String param2, int param3) {
        NewsPagerFragment fragment = new NewsPagerFragment();
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
        //  return inflater.inflate(R.layout.fragment_news_pager, container, false);
        return super.onCreateView(inflater, container, savedInstanceState);
    }

    @Override
    protected int setupLayoutId() {
        return R.layout.fragment_news_pager;
    }

    @Override
    protected int setupViewPagerResId() {
        return R.id.id_view_pager;
    }

    @Override
    protected int setupTabLayoutResId() {
        return R.id.id_tab_layout;
    }

    @Override
    protected KooFragmentPagerAdapter setupKooFragmentPagerAdapter() {
        mKooFragmentPagerAdapter = new KooFragmentPagerAdapter(getChildFragmentManager());
        return mKooFragmentPagerAdapter;
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
    protected boolean needNoDataNotice() {
        return true;
    }

    @Override
    protected RVHeaderFooterAdapter setupRVHeaderFooterAdapter() {
        return null;
    }

    @Override
    protected void toLoadMoreData() {

    }

    @Override
    protected void toRefreshData() {
        mPresenter.gainData();
    }

    @Override
    protected NewsChannelContract.Presenter setupPresenter() {
        return new NewsChannelPresenterImpl(this);
    }

    @Override
    protected void begin(View rootLayout) {

        toRefreshData();

        mFloatingActionButton = findById(R.id.id_floating_action_button);
        mFloatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                /**
                 * 通知back top
                 */
                EventBus.getDefault().post(new EventBackListTop());
            }
        });


        LinearLayout id_ll_channel_add = findById(R.id.id_ll_channel_add);
        id_ll_channel_add.setOnClickListener(new OnNotFastClickListener() {
            @Override
            protected void onNotFastClick(View v) {
                startAty(ItemTouchActivity.class);
            }
        });
    }


    FloatingActionButton mFloatingActionButton;


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
        showNoDataNoticeView();
    }

    @Override
    public void hideNoDataNotice() {
        hideNoDataNoticeView();
    }

    @Override
    public void showMessage(String msg) {
        showMessageDialog(msg);
    }

    @Override
    public void setupData(BaseBeanShowApi<NewsChannelBean> data) {
        //KLog.d("mKooFragmentPagerAdapter:"+mKooFragmentPagerAdapter);

        List<NewsChannelBean.ChannelListBean> list = data.getShowapi_res_body().getChannelList();
        for (int i = 0; i < list.size(); i++) {
            if (i > 19) {
                break;
            }
            String pageName = list.get(i).getName();
            pageName = pageName.replace("最新", "").replace("焦点", "");
            mKooFragmentPagerAdapter.addFragment(NewsListFragment.newInstance(list.get(i).getChannelId(), "", 0), pageName);
        }
    }
}
