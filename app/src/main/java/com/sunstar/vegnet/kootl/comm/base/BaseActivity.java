package com.sunstar.vegnet.kootl.comm.base;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.design.widget.Snackbar;
import android.support.v4.content.ContextCompat;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TabHost;
import android.widget.TextView;

import com.jaeger.library.StatusBarUtil;
import com.jakewharton.rxbinding.view.RxView;
import com.jude.swipbackhelper.SwipeBackHelper;
import com.pgyersdk.crash.PgyCrashManager;
import com.socks.library.KLog;
import com.sunstar.vegnet.R;
import com.sunstar.vegnet.kootl.comm.bus.event.EventBase;
import com.sunstar.vegnet.kootl.comm.config.CommFinalData;
import com.sunstar.vegnet.kootl.comm.rvheaderfooter.RVHeaderFooterAdapter;
import com.sunstar.vegnet.kootl.comm.tabhost.FragmentTabHostUnDestroySupport;
import com.sunstar.vegnet.kootl.comm.tabhost.TabHostShowDataInfo;
import com.sunstar.vegnet.kootl.comm.tool.DialogTool;
import com.sunstar.vegnet.kootl.comm.tool.ToastTool;
import com.yqritc.recyclerviewflexibledivider.HorizontalDividerItemDecoration;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.concurrent.TimeUnit;

import qiu.niorgai.StatusBarCompat;
import rx.functions.Action1;

/**
 * Created by louisgeek on 2016/12/4.
 */

public abstract class BaseActivity<P extends BasePresenter> extends AppCompatActivity {

    protected String mTag;
    protected Context mContext;
    protected static Activity mActivity;
    protected Toolbar mToolbar;
    protected P mPresenter;


    private TextView mToolbarTitleView;
    private CollapsingToolbarLayout mCollapsingToolbarLayout;

    protected SwipeRefreshLayout mSwipeRefreshLayout;
    protected RecyclerView mRecyclerView;
    protected RVHeaderFooterAdapter mRVHeaderFooterAdapter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(setupLayoutId());
        initStatusBarAfterSetContentView(this);
        PgyCrashManager.register(this);
        mContext = this;
        mActivity = this;
        mPresenter = setupPresenter();

        mTag = this.getClass().getSimpleName();

        if (!notNeedSwipeBack()) {
            /**
             * 需要设置style //背景透明，不设滑动关闭时背景就是黑的。
             * <item name="android:windowIsTranslucent">true</item>
             *  侧滑返回
             */
            SwipeBackHelper.onCreate(this);
        }

        if (setupToolbarResId() != 0) {
            initToolbar();
            //DrawerLayout 依托 Toolbar
           /* if (setupDrawerLayoutResId() != 0) {
                initDrawerLayout();
            }*/
        }

        initTabHost();

        if (setupSwipeRefreshLayoutResId() != 0) {
            mSwipeRefreshLayout = findById(setupSwipeRefreshLayoutResId());
            // mSwipeRefreshLayout.setColorSchemeResources(android.R.color.holo_blue_light, android.R.color.holo_red_light, android.R.color.holo_orange_light, android.R.color.holo_green_light);
            mSwipeRefreshLayout.setColorSchemeResources(R.color.colorAccent);
            mSwipeRefreshLayout.setVisibility(View.VISIBLE);
        }

        initRecyclerViewAndAdapter();


        /**
         *last
         */
        begin();
    }

    @Override
    protected void onPostCreate(Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        if (!notNeedSwipeBack()) {
            SwipeBackHelper.onPostCreate(this);
        }
    }

    @Override
    protected void onStart() {
        super.onStart();
        EventBus.getDefault().register(this);
    }

    @Override
    protected void onStop() {
        super.onStop();
        EventBus.getDefault().unregister(this);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        PgyCrashManager.unregister();
        if (!notNeedSwipeBack()) {
            SwipeBackHelper.onDestroy(this);
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        if (setupMenuResId() != 0) {
            getMenuInflater().inflate(setupMenuResId(), menu);
            return true;
        }
        return super.onCreateOptionsMenu(menu);
    }

    /**
     * ===================================protected abstract=============================
     */
    protected abstract int setupLayoutId();

    protected abstract int setupToolbarResId();

    protected abstract P setupPresenter();

    protected abstract TabHostShowDataInfo setupTabHostDataInfo();

    protected abstract boolean notNeedSwipeBack();

    protected abstract boolean setupToolbarTitleCenter();

    protected abstract boolean setupHideHomeAsUp();

    protected abstract int setupCollapsingToolbarLayoutResId();

    protected abstract int setupSwipeRefreshLayoutResId();

    protected abstract int setupRecyclerViewResId();

    protected abstract RVHeaderFooterAdapter setupRVHeaderFooterAdapter();

    protected abstract void toLoadMoreData();

    protected abstract int setupMenuResId();

    protected abstract int setupStatusBarColorResId();

    protected abstract void begin();


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
        mRecyclerView.addItemDecoration(new HorizontalDividerItemDecoration.Builder(this).build());
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

    /**
     * =================================protected===============================
     */
    protected void finishAtyAfterTransition() {
        /**
         *
         */
        supportFinishAfterTransition();
    }

    /**
     * Find View
     */
    protected <V extends View> V findById(int resId) {
        return (V) findViewById(resId);
    }

    protected <V extends View> V findById(View view, int resId) {
        return (V) view.findViewById(resId);
    }

    /**
     * 获取根视图的容器 包含标题栏 content等
     */
    protected View getContentView() {
        //DecorView是一个FrameLayout子类  里面有一个id为content的FrameLayout用来存放我们的布局
        return getWindow().getDecorView().findViewById(android.R.id.content);
    }

    /**
     * 获取根视图  xml根节点 Layout
     */
    protected View getContentViewRootLayout() {
        //DecorView是一个FrameLayout子类  里面有一个id为content的FrameLayout用来存放我们的布局
        ViewGroup viewGroup = (ViewGroup) getContentView();
        return viewGroup.getChildAt(0);
    }


    protected void showMessageDialog(String msg) {
        DialogTool.showMsgDialog(getSupportFragmentManager(), msg);
    }

    protected void showSmileLoadingDialog() {
        showSmileLoadingDialog("");
    }

    protected void showSmileLoadingDialog(String msg) {
        DialogTool.showLoadingSmileDialog(getSupportFragmentManager(), msg);
    }

    protected void showLoadingDialog() {
        showLoadingDialog("");
    }

    protected void showLoadingDialog(String msg) {
        DialogTool.showLoadingDialog(getSupportFragmentManager(), msg);
    }


    protected void hideLoadingDialog() {
        DialogTool.hideDialogFragment();
    }

    protected void hideLoadingDialog(long delayMillis) {
        DialogTool.hideDialogFragment(delayMillis);
    }


    /**
     *  Snackbar将遍历整个view tree来寻找一个合适的view，可能是
     *  一个CoordinatorLayout ,也可能是window decor’s content view
     */
    /**
     * maybe  set  with CoordinatorLayout
     */
    protected void showSnack(View view, String msg) {
        Snackbar.make(view, msg, Snackbar.LENGTH_SHORT).show();
    }

    protected void showSnack(String msg) {
        showSnack(getContentView(), msg);
    }

    protected void showSnackAndAction(String msg, String actionTitle, View.OnClickListener onClickListener) {
        Snackbar.make(getContentView(), msg, Snackbar.LENGTH_SHORT).setAction(actionTitle, onClickListener).show();
    }

    protected void showSnackAndAction(String msg, View.OnClickListener onClickListener) {
        showSnackAndAction(msg, getString(R.string.app_ok), onClickListener);
    }

    /**
     * showToast 及时替换消息的Toast
     *
     * @param msg
     */
    protected void showToastLong(String msg) {
        ToastTool.showLong(msg);
    }

    protected void showToastShort(String msg) {
        ToastTool.showShort(msg);
    }

    protected void showToast(String msg, int duration) {
        ToastTool.showDuration(msg, duration);
    }

    protected void showToastImage(String msg) {
        ToastTool.showImageOk(msg);
    }

    protected void showToastImage(String msg, int imageResId) {
        ToastTool.showImageBase(msg, imageResId);
    }

  /*  protected void startMe(Class<?> clazz) {
        Intent intent = new Intent(this, clazz);
        startActivity(intent);
    }*/

    /* protected void startAty(Class<?> clazz, String extraData1) {
        Intent intent = new Intent(this, clazz);
        intent.putExtra(CommFinalData.EXTRA_DATA_1, extraData1);
        startActivity(intent);
    }*/

    /* protected void startAty(Class<?> clazz, Bundle bundle) {
         Intent intent = new Intent(this, clazz);
         intent.putExtras(bundle);
         startActivity(intent);
     }*/

    protected static void startMe(Context context, Class<?> clazz) {
        Intent intent = new Intent(context, clazz);
        context.startActivity(intent);
    }

    protected static void startMe(Context context, Class<?> clazz, Bundle bundle) {
        Intent intent = new Intent(context, clazz);
        intent.putExtras(bundle);
        context.startActivity(intent);
    }

    protected void startAtyForResult(Class<?> clazz, int requestCode) {
        Intent intent = new Intent(this, clazz);
        startActivityForResult(intent, requestCode);
    }

    protected void startAtyForResult(Class<?> clazz, int requestCode, Bundle bundle) {
        Intent intent = new Intent(this, clazz);
        intent.putExtras(bundle);
        startActivityForResult(intent, requestCode);
    }

    protected String getBundleExtraStr1() {
        String bundleExtra = null;
        if (getIntent() != null) {
            Bundle bundle = getIntent().getExtras();
            bundleExtra = bundle.getString(CommFinalData.BUNDLE_EXTRA_KEY_1, "");
        }
        return bundleExtra;
    }

    protected int getBundleExtraInt1() {
        int bundleExtra = 0;
        Bundle bundle = getBundleExtra();
        if (bundle != null) {
            bundleExtra = bundle.getInt(CommFinalData.BUNDLE_EXTRA_KEY_1);
        }
        return bundleExtra;
    }

    protected Bundle getBundleExtra() {
        Bundle bundle = null;
        if (getIntent() != null) {
            bundle = getIntent().getExtras();
        }
        return bundle;
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

    /**
     * 防止重复点击的监听
     *
     * @param view
     * @param onClickListener
     */
    protected void setClickListener(final View view, final View.OnClickListener onClickListener) {
        if (view == null) {
            return;
        }
        RxView.clicks(view)
                .throttleFirst(1500, TimeUnit.MILLISECONDS)
                .subscribe(new Action1<Void>() {
                    @Override
                    public void call(Void aVoid) {
                        if (onClickListener != null) {
                            onClickListener.onClick(view);
                        }
                    }
                });
    }

    protected void setToolbarTitle(int strResId) {
        setToolbarTitle(getString(strResId));
    }

    protected void setToolbarTitle(String string) {
        if (setupToolbarTitleCenter()) {
            if (mToolbarTitleView != null) {
                mToolbarTitleView.setText(string);
                mToolbar.setTitle("");
                mToolbarTitleView.setVisibility(View.VISIBLE);
            }
            if (mCollapsingToolbarLayout != null) {
                mCollapsingToolbarLayout.setTitle("");
            }
        } else {
            if (mToolbarTitleView != null) {
                mToolbarTitleView.setText("");
                mToolbar.setTitle(string);
                mToolbarTitleView.setVisibility(View.GONE);
            }
            if (mCollapsingToolbarLayout != null) {
                mCollapsingToolbarLayout.setTitle(string);
            }
        }
    }

    /**
     * =====================================private===========================
     */
    private void initToolbar() {
        mToolbar = findById(setupToolbarResId());
        if (mToolbar == null) {
            return;
        }
        mToolbarTitleView = findById(mToolbar, R.id.id_toolbar_title);

        if (setupCollapsingToolbarLayoutResId() != 0) {
            mCollapsingToolbarLayout = findById(setupCollapsingToolbarLayoutResId());
        }
        /**
         * setToolbarTitle
         */
        this.setToolbarTitle(mToolbar.getTitle() != null ? mToolbar.getTitle().toString() : "");

        mToolbar.setVisibility(View.VISIBLE);
        //替换ActionBar
        setSupportActionBar(mToolbar);
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            if (!setupHideHomeAsUp()) {
                actionBar.setDisplayHomeAsUpEnabled(true);
            }
            //actionBar.setHomeAsUpIndicator(R.mipmap.ic_launcher);
        }
        //必须设置在setSupportActionBar(mToolbar);后才有效
        mToolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //结束当前aty
                //###finish();
                finishAtyAfterTransition();
            }
        });
    }

    /**
     * 在 setContentView 方法调用后再设置.
     * 如果使用了全屏 Activity ,记得调用StatusBarCompat.translucentStatusBar(activity);
     * <p>
     * //设置状态栏的颜色
     * StatusBarCompat.setStatusBarColor(Activity activity, int color)
     * //添加alpha值
     * StatusBarCompat.setStatusBarColor(Activity activity, int statusColor, int alpha)
     * <p>
     * //透明状态栏
     * StatusBarCompat.translucentStatusBar(activity);
     * //SDK >= 21时, 取消状态栏的阴影
     * StatusBarCompat.translucentStatusBar(Activity activity, boolean hideStatusBarBackground);
     * <p>
     * //为 CollapsingToolbarLayout 设置颜色
     * setStatusBarColorForCollapsingToolbar(Activity activity, AppBarLayout appBarLayout, CollapsingToolbarLayout collapsingToolbarLayout,
     * Toolbar toolbar, int statusColor)
     * <p>
     * 4.4的问题：
     * 如果用在 TabActivity 上, 会有一条黑线在状态栏下面, 推荐使用 StatusBarCompat.setStatusBarColor(Activity activity, int statusColor, int alpha) 方法, 推荐的透明值为 112.
     * 如果 layout 中第一个 View 为 DrawerLayout, 那么它的子 View 的 fitsSystemWindow 需要设置为 false.
     */
    private void initStatusBarAfterSetContentView(Activity activity) {
        int statusBarColorResId;
        //-1 不设置
        if (setupStatusBarColorResId() == -1) {
            return;
        } else if (setupStatusBarColorResId() == 0) { //0 默认
            statusBarColorResId = R.color.baseStatusBarColor;
        } else {//id 传入颜色
            statusBarColorResId = setupStatusBarColorResId();
        }
        //
        int baseStatusBarColor = ContextCompat.getColor(activity, statusBarColorResId);
        int baseStatusBarAlpha = 0;

        //StatusBarUtil.setColor简单使用：android4.4下在没有SwipeBack页面Toolbar占用StatusBar  =》要调整
        //StatusBarUtil.setColor简单使用：android4.4下在有SwipeBack页面StatusBar会空白  用setColorForSwipeBack
        ////
        //StatusBarUtil.setColorForSwipeBack简单使用：会让背景变成设置的颜色  所以得手动fix背景色
        /**
         *
         */
        //StatusBarCompat.setStatusBarColor简单使用：android4.4下在有SwipeBack页面StatusBar也会空白 =》bug

        if (!notNeedSwipeBack()) {//有 SwipeBack
            //需要手动fix view 背景色
            ///###StatusBarUtil.setColorForSwipeBack(activity, baseStatusBarColor);//默认alpha 112
            StatusBarUtil.setColorForSwipeBack(activity, baseStatusBarColor, baseStatusBarAlpha);
            /**
             * !!!!!!!!!!!! fix这个setColorForSwipeBack造成的问题
             *
             * 相当于根layout 加入android:background=""
             */
            getContentViewRootLayout().setBackgroundColor(ContextCompat.getColor(activity, R.color.baseViewBg));
        } else {
            StatusBarCompat.setStatusBarColor(activity, baseStatusBarColor);
        }

    }


    private void initTabHost() {
        /**
         * 常规使用
         *
         TabHostShowDataInfo tabHostShowDataInfo = new TabHostShowDataInfo();
         tabHostShowDataInfo.addFragmentClass(NewsPagerFragment.class, R.mipmap.ic_launcher, R.string.base_main_title_1);
         tabHostShowDataInfo.addFragmentClass(NewsPagerFragment.class, R.mipmap.ic_image_no, R.string.base_main_title_2);
         tabHostShowDataInfo.addFragmentClass(NewsPagerFragment.class, R.mipmap.ic_launcher, R.string.base_main_title_3);
         */
        TabHostShowDataInfo tabHostShowDataInfo = setupTabHostDataInfo();
        //
        if (tabHostShowDataInfo == null || tabHostShowDataInfo.getFragmentClassesList() == null || tabHostShowDataInfo.getFragmentClassesList().size() <= 0) {
            return;
        }
        FragmentTabHostUnDestroySupport fragmentTabHost = findById(android.R.id.tabhost);
        for (int i = 0; i < tabHostShowDataInfo.getFragmentClassesList().size(); i++) {
            TabHost.TabSpec tabSpec = fragmentTabHost.newTabSpec(getString(tabHostShowDataInfo.getImageResIDList().get(i)));
            View indicatorView = buildIndicatorView(getString(tabHostShowDataInfo.getTabLabelResIDList().get(i)),
                    tabHostShowDataInfo.getImageResIDList().get(i));
            //
          /*   ImageView   id_iv_main_center= (ImageView) findViewById(R.id.id_iv_main_center);
           if (hasCenterBtn){
              id_iv_main_center.setVisibility(View.VISIBLE);
            //R.string.tab_label_add hide
            if (i==2){
                *//**占位*//*
                indicatorView.setVisibility(View.INVISIBLE);
            }
            }else{
                id_iv_main_center.setVisibility(View.GONE);
            }*/
            tabSpec.setIndicator(indicatorView);
            /*另一种FragmentTabHost布局方式见：https://code.csdn.net/snippets/1958337 */
            //mFragmentTabHost.setup(this,getSupportFragmentManager(),R.id.realtabcontent);
            fragmentTabHost.setup(this, getSupportFragmentManager(), android.R.id.tabcontent);
            //Bundle 会传给底层的Fragment的实例创建  可以传递参数 b;传递公共的userid,version,sid   可以为null
            Bundle bundle = new Bundle();
            bundle.putString("name", i + "XX");
            fragmentTabHost.addTab(tabSpec, tabHostShowDataInfo.getFragmentClassesList().get(i), bundle);
            // fragmentTabHost.getTabWidget().getChildAt()
        }
        fragmentTabHost.getTabWidget().setShowDividers(LinearLayout.SHOW_DIVIDER_NONE);
        fragmentTabHost.getTabWidget().setBackgroundColor(ContextCompat.getColor(this, android.R.color.white));

        /**
         * 当叶卡超过1个的时候才显示底部标记
         */
        if (tabHostShowDataInfo.getFragmentClassesList().size() > 1) {
            fragmentTabHost.getTabWidget().setVisibility(View.VISIBLE);
        } else {
            fragmentTabHost.getTabWidget().setVisibility(View.GONE);
        }
        fragmentTabHost.setOnTabChangedListener(new TabHost.OnTabChangeListener() {
            @Override
            public void onTabChanged(String tabId) {
                // KLog.d("onTabChanged:" + tabId);

            }
        });
    }

    private View buildIndicatorView(String title, int imgResID) {
        View view = LayoutInflater.from(this).inflate(R.layout.base_fragment_tabhost_indicator_normal, null, false);
        ImageView imageView = findById(view, R.id.id_iv_indicator);
        TextView textView = findById(view, R.id.id_tv_indicator);
        imageView.setImageResource(imgResID);
        textView.setText(title);
        return view;
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onEventMainThread(EventBase event) {

    }

}
