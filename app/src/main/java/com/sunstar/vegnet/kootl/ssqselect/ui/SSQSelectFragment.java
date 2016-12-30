package com.sunstar.vegnet.kootl.ssqselect.ui;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.SearchView;
import android.widget.TextView;

import com.baidu.location.BDLocation;
import com.jakewharton.rxbinding.widget.RxSearchView;
import com.socks.library.KLog;
import com.sunstar.baidulbslib.LocationClientHelper;
import com.sunstar.vegnet.R;
import com.sunstar.vegnet.kootl.comm.app.DataHolderSingleton;
import com.sunstar.vegnet.kootl.comm.adapter.KooFragmentPagerAdapter;
import com.sunstar.vegnet.kootl.comm.base.BaseFragment;
import com.sunstar.vegnet.kootl.comm.bus.event.EventAreaSelected;
import com.sunstar.vegnet.kootl.comm.config.CommFinalData;
import com.sunstar.vegnet.kootl.comm.rvheaderfooter.RVHeaderFooterAdapter;
import com.sunstar.vegnet.kootl.comm.tool.ToastTool;
import com.sunstar.vegnet.kootl.ssqselect.adapter.AreaSelectRVHFAdapter;
import com.sunstar.vegnet.kootl.ssqselect.bean.AreaDataWrapper;
import com.sunstar.vegnet.kootl.ssqselect.bean.Province;
import com.sunstar.vegnet.kootl.ssqselect.contract.SSQSelectContract;
import com.sunstar.vegnet.kootl.ssqselect.helper.SSQHelper;
import com.sunstar.vegnet.kootl.ssqselect.presenter.SSQSelectPresenterImpl;
import com.yqritc.recyclerviewflexibledivider.HorizontalDividerItemDecoration;

import org.greenrobot.eventbus.EventBus;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link SSQSelectFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class SSQSelectFragment extends BaseFragment<SSQSelectContract.Presenter> implements SSQSelectContract.View<List<AreaDataWrapper>> {

    private List<Province> mProvinceList = new ArrayList<>();
    private String mQueryText;

    public SSQSelectFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment SSQSelectFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static SSQSelectFragment newInstance(String param1, String param2, int param3) {
        SSQSelectFragment fragment = new SSQSelectFragment();
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
        // return inflater.inflate(R.layout.fragment_province_select, container, false);
        return super.onCreateView(inflater, container, savedInstanceState);
    }

    @Override
    protected int setupLayoutId() {
        return R.layout.fragment_ssq_select;
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
        return 0;
    }

    @Override
    protected int setupRecyclerViewResId() {
        return R.id.id_recycler_view;
    }

    @Override
    protected boolean needNoDataNotice() {
        return false;
    }

    AreaSelectRVHFAdapter mAreaSelectRVHFAdapter;

    @Override
    protected RVHeaderFooterAdapter setupRVHeaderFooterAdapter() {
        List<AreaDataWrapper> areaDataWrapperList = new ArrayList();
        mRecyclerView.addItemDecoration(new HorizontalDividerItemDecoration.Builder(mContext).build());
        mAreaSelectRVHFAdapter = new AreaSelectRVHFAdapter(areaDataWrapperList, mRecyclerView);
        /**
         * item 选择
         */
        mAreaSelectRVHFAdapter.setOnItemClickListener(
                new RVHeaderFooterAdapter.OnItemClickListener() {
                    @Override
                    public void onItemClick(View itemView, int position, Object data) {
                        //搜索时候    position 不准确  或者说  没意义
                        AreaDataWrapper areaDataWrapper = (AreaDataWrapper) data;
                        int selectedProvincePos = areaDataWrapper.getHoldSelectedProvincePos();
                        int selectedCityPos = areaDataWrapper.getHoldSelectedCityPos();
                        int selectedAreaPos = areaDataWrapper.getHoldSelectedAreaPos();
                        //
                        DataHolderSingleton.getInstance().putData(CommFinalData.HOLD_SELECTED_PROVINCE_POS, selectedProvincePos);
                        DataHolderSingleton.getInstance().putData(CommFinalData.HOLD_SELECTED_CITY_POS, selectedCityPos);
                        DataHolderSingleton.getInstance().putData(CommFinalData.HOLD_SELECTED_AREA_POS, selectedAreaPos);
                        //
                        int nowShowWhere = areaDataWrapper.getNowShowWhere();
                        switch (nowShowWhere) {
                            case SSQSelectActivity.SHOW_PROVINCE:
                                //跳转
                                startAty(SSQSelectActivity.class, createBundleExtraInt1(SSQSelectActivity.SHOW_CITY));
                                break;
                            case SSQSelectActivity.SHOW_CITY:
                                //跳转
                                startAty(SSQSelectActivity.class, createBundleExtraInt1(SSQSelectActivity.SHOW_AREA));
                                break;
                            case SSQSelectActivity.SHOW_AREA:
                                //选择 所选
                             /*   ToastTool.showShort(mProvinceList.get(selectedProvincePos).getProvinceName() + "-"
                                        + mProvinceList.get(selectedProvincePos).getCites().get(selectedCityPos).getCityName() + "-"
                                        + areaDataWrapper.getName() + "|" + areaDataWrapper.getAreaId()
                                );*/
                                EventBus.getDefault().post(new EventAreaSelected(areaDataWrapper.getAreaId(), areaDataWrapper.getName()));
                                break;
                        }


                    }
                }

        );
        return mAreaSelectRVHFAdapter;
    }

    @Override
    protected void toLoadMoreData() {

    }

    @Override
    protected void toRefreshData() {

    }

    @Override
    protected SSQSelectContract.Presenter setupPresenter() {
        return new SSQSelectPresenterImpl(this);
    }

    int mNeedShowWhere;

    @Override
    protected void begin(View rootLayout) {
        mNeedShowWhere = mParam3;
        mPresenter.gainData();

        //initLocation();

        initSearchView();

    }

    String mLocationIngStr = "正在定位中...";
    String mNowAreaID;
    String mNowAreaName;

    private void configProvinceShowLocation_CityOrAreaShowAllHeadView(final int provincePos, final int cityPos) {
        /**
         *
         */
        View showAllHeadView = LayoutInflater.from(mContext).inflate(R.layout.item_list_select_has_more, null, false);
        final TextView id_tv_item_title = (TextView) showAllHeadView.findViewById(R.id.id_tv_item_title);
        ImageView id_tv_item_image = (ImageView) showAllHeadView.findViewById(R.id.id_tv_item_image);
        id_tv_item_image.setVisibility(View.INVISIBLE);
        //点击全部按钮
        showAllHeadView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                switch (mParam3) {
                    case SSQSelectActivity.SHOW_PROVINCE:
                      /* do nothing */
                        //省没有全部按钮 用来显示 定位item的按钮
                        if (id_tv_item_title.getText().equals(mLocationIngStr)) {
                            ToastTool.showImageInfo(mLocationIngStr + "，请稍后");
                            return;
                        }
                        //ToastTool.showShort("定位的mAreaID是：" + mNowAreaID);
                        //ToastTool.showShort("定位的mNowAreaName是：" + mNowAreaName);
                        //
                        EventBus.getDefault().post(new EventAreaSelected(mNowAreaID, mNowAreaName));
                        break;
                    case SSQSelectActivity.SHOW_CITY:
                        String provinceID = mProvinceList.get(provincePos).getProvinceID();
                        String provinceName = mProvinceList.get(provincePos).getProvinceName();
                        //ToastTool.showShort(provinceName + "-全部城市");
                        //
                        EventBus.getDefault().post(new EventAreaSelected(provinceID, provinceName));
                        break;
                    case SSQSelectActivity.SHOW_AREA:
                        //
                        String provinceName2 = mProvinceList.get(provincePos).getProvinceName();
                        String cityID = mProvinceList.get(provincePos).getCites().get(cityPos).getCityID();
                        String cityName = mProvinceList.get(provincePos).getCites().get(cityPos).getCityName();
                        //ToastTool.showShort(provinceName2 + "-" + cityName + "-全部地区");
                        //
                        //
                        EventBus.getDefault().post(new EventAreaSelected(cityID, cityName));
                        break;
                }

            }
        });
        /**
         *
         */
        switch (mParam3) {
            case SSQSelectActivity.SHOW_PROVINCE:
                //省没有全部按钮 是定位显示item的按钮
                //mAreaSelectRVHFAdapter.setHeaderView(null);
                if (DataHolderSingleton.getInstance().getData(CommFinalData.HOLD_BDLOCATION) != null) {
                    BDLocation bdLocation = (BDLocation) DataHolderSingleton.getInstance().getData(CommFinalData.HOLD_BDLOCATION);
                    id_tv_item_title.setText("当前定位:" + bdLocation.getDistrict());
                    //
                    mNowAreaID = SSQHelper.getAreaID(bdLocation.getProvince(), bdLocation.getCity(), bdLocation.getDistrict());
                    mNowAreaName = bdLocation.getDistrict();
                } else {
                    id_tv_item_title.setText(mLocationIngStr);
                    LocationClientHelper.startLocation(new LocationClientHelper.OnLocationCallBack() {
                        @Override
                        public void onLocation(BDLocation bdLocation) {
                            // id_tv_item_title.setText(bdLocation.getProvince() + "-" + bdLocation.getCity() + "-" + bdLocation.getDistrict() + "-" + areaID);
                            id_tv_item_title.setText("当前定位:" + bdLocation.getDistrict());
                            //
                            mNowAreaID = SSQHelper.getAreaID(bdLocation.getProvince(), bdLocation.getCity(), bdLocation.getDistrict());
                            mNowAreaName = bdLocation.getDistrict();
                            DataHolderSingleton.getInstance().putData(CommFinalData.HOLD_BDLOCATION, bdLocation);
                        }
                    });
                }


                /**
                 *
                 */

                mAreaSelectRVHFAdapter.setHeaderView(showAllHeadView);
                break;
            case SSQSelectActivity.SHOW_CITY:
                id_tv_item_title.setText("全部城市");
                mAreaSelectRVHFAdapter.setHeaderView(showAllHeadView);
                break;
            case SSQSelectActivity.SHOW_AREA:
                id_tv_item_title.setText("全部地区");
                mAreaSelectRVHFAdapter.setHeaderView(showAllHeadView);
                break;
        }

    }

    private void initSearchView() {
        final SearchView searchView = findById(R.id.id_search_view);
        //设置搜索图标是否显示在搜索框内
        searchView.setIconifiedByDefault(false);//The default value is true   ，设置为false直接展开显示 左侧有放大镜  右侧无叉叉   有输入内容后有叉叉
        //!!! searchView.setIconified(false);//true value will collapse the SearchView to an icon, while a false will expand it. 左侧无放大镜 右侧直接有叉叉
        //  searchView.onActionViewExpanded();//直接展开显示 左侧无放大镜 右侧无叉叉 有输入内容后有叉叉 内部调用了setIconified(false);
        //searchView.setImeOptions(EditorInfo.IME_ACTION_SEARCH);
        searchView.setQueryHint("请输入地区");//设置查询提示字符串
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
               /* mQueryText = query;
                mPresenter.querySSQData();*/
                KLog.d("onQueryTextSubmit:" + query);
                return false;
            }

            @Override
            public boolean onQueryTextChange(final String newText) {

                RxSearchView.queryTextChanges(searchView)
                        .debounce(400, TimeUnit.MILLISECONDS)
                        .observeOn(AndroidSchedulers.mainThread())
                        //对用户输入的关键字进行过滤
                        /*.filter(new Func1<CharSequence, Boolean>() {
                            @Override
                            public Boolean call(CharSequence charSequence) {
                                return charSequence.toString().trim().length() > 0;
                            }
                        })*/
                        .subscribe(new Action1<CharSequence>() {
                            @Override
                            public void call(CharSequence charSequence) {
                                mQueryText = charSequence.toString();
                                if (mQueryText.trim().length() > 0) {
                                    mPresenter.querySSQData();
                                } else {//空白
                                    mPresenter.gainData();
                                }
                                KLog.d("queryTextChanges:" + mQueryText);
                            }
                        });

                return false;
            }
        });
        //
        int goToWhere = mParam3;
        switch (goToWhere) {
            case SSQSelectActivity.SHOW_PROVINCE:
                searchView.setVisibility(View.VISIBLE);
                break;
            case SSQSelectActivity.SHOW_CITY:
            case SSQSelectActivity.SHOW_AREA:
                searchView.setVisibility(View.GONE);
                break;
        }
    }


    @Override
    public int readyDataNeedShowWhere() {
        return mNeedShowWhere;
    }


    @Override
    public void backQueryResultData(List<AreaDataWrapper> data) {
        mAreaSelectRVHFAdapter.refreshDataList(data);
    }

    @Override
    public List<Province> readyDataNeedQueryData() {
        return mProvinceList;
    }

    @Override
    public String readyDataQueryText() {
        return mQueryText;
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
    public void setupData(List<AreaDataWrapper> data) {
        //
        mProvinceList = (List<Province>) DataHolderSingleton.getInstance().getData(CommFinalData.HOLD_SORTED_PROVINCE_LIST);
        //
        mAreaSelectRVHFAdapter.refreshDataList(data);
        //
        changeTitle();
    }

    private void changeTitle() {
        SSQSelectActivity ssqSelectActivity = (SSQSelectActivity) getActivity();
        switch (mParam3) {
            case SSQSelectActivity.SHOW_PROVINCE:
                String titleStr = getActivity().getString(R.string.nav_title_area);
                ssqSelectActivity.resetToolbarTitle(titleStr);
                configProvinceShowLocation_CityOrAreaShowAllHeadView(-1, -1);
                break;
            case SSQSelectActivity.SHOW_CITY:
                int selectedProvincePos = (int) DataHolderSingleton.getInstance().getData(CommFinalData.HOLD_SELECTED_PROVINCE_POS);
                ssqSelectActivity.resetToolbarTitle(mProvinceList.get(selectedProvincePos).getProvinceName());
                configProvinceShowLocation_CityOrAreaShowAllHeadView(selectedProvincePos, -1);
                break;
            case SSQSelectActivity.SHOW_AREA:
                int selectedProvincePos2 = (int) DataHolderSingleton.getInstance().getData(CommFinalData.HOLD_SELECTED_PROVINCE_POS);
                int selectedCityPos = (int) DataHolderSingleton.getInstance().getData(CommFinalData.HOLD_SELECTED_CITY_POS);
                ssqSelectActivity.resetToolbarTitle(mProvinceList.get(selectedProvincePos2).getProvinceName() + "-"
                        + mProvinceList.get(selectedProvincePos2).getCites().get(selectedCityPos).getCityName());
                configProvinceShowLocation_CityOrAreaShowAllHeadView(selectedProvincePos2, selectedCityPos);
                break;
        }
    }

}
