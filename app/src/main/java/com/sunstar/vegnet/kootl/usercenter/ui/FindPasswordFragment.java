package com.sunstar.vegnet.kootl.usercenter.ui;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.sunstar.vegnet.R;
import com.sunstar.vegnet.kootl.comm.adapter.KooFragmentPagerAdapter;
import com.sunstar.vegnet.kootl.comm.base.BaseFragment;
import com.sunstar.vegnet.kootl.comm.base.BasePresenter;
import com.sunstar.vegnet.kootl.comm.rvheaderfooter.RVHeaderFooterAdapter;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link FindPasswordFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class FindPasswordFragment extends BaseFragment<BasePresenter> {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;


    public FindPasswordFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment FindPasswordFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static FindPasswordFragment newInstance(String param1, String param2) {
        FindPasswordFragment fragment = new FindPasswordFragment();
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
        //return inflater.inflate(R.layout.fragment_find_password, container, false);
        return super.onCreateView(inflater, container, savedInstanceState);
    }

    @Override
    protected int setupLayoutId() {
        return R.layout.fragment_find_password;
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
        return 0;
    }

    @Override
    protected boolean needNoDataNotice() {
        return false;
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

    }

    @Override
    protected BasePresenter setupPresenter() {
        return null;
    }

    @Override
    protected void begin(View rootLayout) {

    }

}
