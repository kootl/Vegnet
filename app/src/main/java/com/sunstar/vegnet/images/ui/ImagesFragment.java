package com.sunstar.vegnet.images.ui;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.socks.library.KLog;
import com.sunstar.vegnet.R;
import com.sunstar.vegnet.data.BaseBeanShowApi;
import com.sunstar.vegnet.images.adapter.ImagesRVHFAdapter;
import com.sunstar.vegnet.images.bean.ImagesAndTextListBean;
import com.sunstar.vegnet.images.contract.ImagesContract;
import com.sunstar.vegnet.images.presenter.ImagesPresenterImpl;
import com.sunstar.vegnet.kootl.comm.adapter.KooFragmentPagerAdapter;
import com.sunstar.vegnet.kootl.comm.base.BaseFragment;
import com.sunstar.vegnet.kootl.comm.bus.event.EventShowHideTabHost;
import com.sunstar.vegnet.kootl.comm.listener.OnScrollWithUpDownListener;
import com.sunstar.vegnet.kootl.comm.rvheaderfooter.RVHeaderFooterAdapter;
import com.sunstar.vegnet.kootl.imageshow.bean.ImageShowBean;
import com.sunstar.vegnet.kootl.imageshow.helper.ImageShowDataHelper;

import org.greenrobot.eventbus.EventBus;

import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link ImagesFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ImagesFragment extends BaseFragment<ImagesContract.Presenter> implements ImagesContract.View<BaseBeanShowApi<ImagesAndTextListBean>> {


    public ImagesFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment ImagesFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static ImagesFragment newInstance(String param1, String param2) {
        ImagesFragment fragment = new ImagesFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        //return inflater.inflate(R.layout.fragment_images, container, false);
        return super.onCreateView(inflater, container, savedInstanceState);
    }

    @Override
    protected int setupLayoutId() {
        return R.layout.fragment_images;
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

    ImagesRVHFAdapter mImagesRVHFAdapter;

    @Override
    protected RVHeaderFooterAdapter setupRVHeaderFooterAdapter() {
        List<ImagesAndTextListBean.ContentlistBean> contentlist = new ArrayList<>();
        mImagesRVHFAdapter = new ImagesRVHFAdapter(contentlist, mRecyclerView);
        mImagesRVHFAdapter.setOnItemClickListener(new RVHeaderFooterAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View itemView, int position, Object data) {
                ImagesAndTextListBean.ContentlistBean contentlistBean = (ImagesAndTextListBean.ContentlistBean) data;
                ImageShowBean imageShowBean = new ImageShowBean();
                imageShowBean.setTitle(contentlistBean.getTitle());
                imageShowBean.setImageUrl(contentlistBean.getImg());
                ImageShowDataHelper.setDataAndToImageShow(mContext, imageShowBean);
            }
        });
        return mImagesRVHFAdapter;
    }

    @Override
    protected void toLoadMoreData() {
        /**
         * 取下一页数据
         */
        mPresenter.gainMoreData(mImagesRVHFAdapter.getNextPageNum());
    }

    @Override
    protected void toRefreshData() {
        mPresenter.gainData();
    }

    @Override
    protected ImagesContract.Presenter setupPresenter() {
        return new ImagesPresenterImpl(this);
    }

    @Override
    protected void begin(View rootLayout) {
        /**
         *
         */
        toRefreshData();

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
    public void showMoreDataLoading() {

    }

    @Override
    public void hideMoreDataLoading() {

    }

    @Override
    public void showMoreDataLoadComplete() {

    }


    @Override
    public void onPause() {
        super.onPause();
        /**
         * 记录
         */
               /* LinearLayoutManager linearLayoutManager = (LinearLayoutManager) mRecyclerView.getLayoutManager();
                int position = linearLayoutManager.findFirstVisibleItemPosition();
                View firstVisibleItemView = mRecyclerView.getChildAt(position);
               ///### View firstVisibleItemView = linearLayoutManager.getChildAt(0);          //获取可视的第一个view
                int lastStayPosition = linearLayoutManager.getPosition(firstVisibleItemView);
                int lastStayOffset_Top = firstVisibleItemView.getTop();
                SharedPreferencesTool.put(mContext,"IMAGES_SAVE_POS",lastStayPosition);
                SharedPreferencesTool.put(mContext,"IMAGES_SAVE_OFFSET",lastStayOffset_Top);

                KLog.d("save_re 1:lastStayPosition:"+lastStayPosition);
                KLog.d("save_re 1:lastStayOffset_Top:"+lastStayOffset_Top);*/

      /*  int lastFirstVisiblePosition = ((LinearLayoutManager) mRecyclerView.getLayoutManager()).findFirstCompletelyVisibleItemPosition();
        SharedPreferencesTool.put(mContext, "IMAGES_SAVE_POS", lastFirstVisiblePosition);
        KLog.d("XXQQ onPause");*/
    }

    @Override
    public void onResume() {
        super.onResume();
        /**
         * 恢复
         */
       /* int lastStayPosition = (int) SharedPreferencesTool.get(mContext, "IMAGES_SAVE_POS", 0);
        int lastStayOffset_Top = (int) SharedPreferencesTool.get(mContext, "IMAGES_SAVE_OFFSET", 0);
        LinearLayoutManager linearLayoutManager = (LinearLayoutManager) mRecyclerView.getLayoutManager();
        //  linearLayoutManager.scrollToPositionWithOffset(lastStayPosition, lastStayOffset_Top);

        KLog.d("save_re 2:lastStayPosition:" + lastStayPosition);
        KLog.d("save_re 2:lastStayOffset_Top:" + lastStayOffset_Top);*/
      /*  KLog.d("XXQQ onResume");
        int lastFirstVisiblePosition = (int) SharedPreferencesTool.get(mContext, "IMAGES_SAVE_POS", 0);
        ((LinearLayoutManager) mRecyclerView.getLayoutManager()).scrollToPosition(lastFirstVisiblePosition);*/
    }

    @Override
    public void setupMoreData(BaseBeanShowApi<ImagesAndTextListBean> data) {
        KLog.d("第几页：" + mImagesRVHFAdapter.getPageNum());
        mImagesRVHFAdapter.appendDataList(data.getShowapi_res_body().getContentlist());
        mImagesRVHFAdapter.turnNextPageNum();
    }

    @Override
    public void showProgress() {
        showSwipeRefreshLayout();
    }

    @Override
    public void hideProgress() {
        hideSwipeRefreshLayout();
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
    public void setupData(BaseBeanShowApi<ImagesAndTextListBean> data) {
        /**
         * 页面置1
         */
        mImagesRVHFAdapter.resetPageNum();
        mImagesRVHFAdapter.refreshDataList(data.getShowapi_res_body().getContentlist());
    }
}
