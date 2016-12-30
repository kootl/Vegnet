package com.sunstar.vegnet.news.ui;

import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatDelegate;
import android.support.v7.widget.Toolbar;
import android.view.Gravity;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TabWidget;
import android.widget.TextView;

import com.baidu.location.BDLocation;
import com.makeramen.roundedimageview.RoundedImageView;
import com.socks.library.KLog;
import com.sunstar.vegnet.R;
import com.sunstar.vegnet.custom.KooApplication;
import com.sunstar.vegnet.images.ui.ImagesFragment;
import com.sunstar.vegnet.kootl.comm.app.DataHolderSingleton;
import com.sunstar.vegnet.kootl.comm.base.BaseActivity;
import com.sunstar.vegnet.kootl.comm.base.BasePresenter;
import com.sunstar.vegnet.kootl.comm.bus.event.EventAreaSelected;
import com.sunstar.vegnet.kootl.comm.bus.event.EventShowHideTabHost;
import com.sunstar.vegnet.kootl.comm.bus.event.EventShowHideToolbar;
import com.sunstar.vegnet.kootl.comm.config.CommFinalData;
import com.sunstar.vegnet.kootl.comm.helper.DayNightHelper;
import com.sunstar.vegnet.kootl.comm.helper.VectorOrImageResHelper;
import com.sunstar.vegnet.kootl.comm.helper.ViewHelper;
import com.sunstar.vegnet.kootl.comm.listener.OnNotFastClickListener;
import com.sunstar.vegnet.kootl.comm.rvheaderfooter.RVHeaderFooterAdapter;
import com.sunstar.vegnet.kootl.comm.tabhost.TabHostShowDataInfo;
import com.sunstar.vegnet.kootl.comm.tool.SharedPreferencesTool;
import com.sunstar.vegnet.kootl.comm.tool.ViewTool;
import com.sunstar.vegnet.kootl.preference.SettingActivity;
import com.sunstar.vegnet.kootl.rvItemtouch.ui.ItemTouchActivity;
import com.sunstar.vegnet.kootl.scanqr.ScanZbarActivity;
import com.sunstar.vegnet.kootl.search.ui.SearchActivity;
import com.sunstar.vegnet.kootl.ssqselect.helper.SSQAtyHelper;
import com.sunstar.vegnet.kootl.ssqselect.helper.SSQHelper;
import com.sunstar.vegnet.kootl.ssqselect.ui.SSQSelectActivity;
import com.sunstar.vegnet.kootl.userinfo.ui.UserInfoActivity;

import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import static com.sunstar.vegnet.R.id.id_iv_userface;
import static com.sunstar.vegnet.R.id.id_ll_menu_item_3;
import static com.sunstar.vegnet.R.id.id_toolbar_left_tv;


public class NewsActivity extends BaseActivity<BasePresenter> {

    @Override
    protected int setupLayoutId() {
        return R.layout.activity_news;
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
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (savedInstanceState != null) {
            KLog.d("ssssssss:" + savedInstanceState.getString("XXX"));
            // Restore value of members from saved state
            //ToastTool.showImageInfo("不重建");
        } else {
            //ToastTool.showImageInfo("重建");
            // Probably initialize members with default values for a new instance
        }
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putString("XXX", "ZFQqqq");


    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        String ssss = savedInstanceState.getString("XXX");
        KLog.d("ssss:" + ssss);
    }

    @Override
    protected TabHostShowDataInfo setupTabHostDataInfo() {
        TabHostShowDataInfo tabHostShowDataInfo = new TabHostShowDataInfo();
        tabHostShowDataInfo.addFragmentClass(NewsPagerFragment.class, R.mipmap.ic_launcher, R.string.base_main_title_1);
        tabHostShowDataInfo.addFragmentClass(ImagesFragment.class, R.mipmap.ic_image_no, R.string.base_main_title_2);
        // tabHostShowDataInfo.addFragmentClass(BlankFragment.class, R.mipmap.ic_launcher, R.string.base_main_title_3);
        return tabHostShowDataInfo;
    }

    @Override
    protected boolean notNeedSwipeBack() {
        return true;
    }

    @Override
    protected boolean setupToolbarTitleCenter() {
        return true;
    }

    @Override
    protected boolean setupHideHomeAsUp() {
        return true;
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
        return R.menu.menu_user;
    }

    @Override
    protected int setupStatusBarColorResId() {
        return 0;
    }

    TextView mToolbarLeftTv;

    @Override
    protected void begin() {
     /*   TextView textView=findById(R.id.id_tv);
        textView.setText("XXXXXXXX");
        setClickListener(textView, new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showToastShort("XXXXADASD");
            }
        });*/

       /* OkCancelDialogFragment okCancelDialogFragment=OkCancelDialogFragment.newInstance("1","2");
        okCancelDialogFragment.show(getSupportFragmentManager(),"ss");

        OkDialogFragment okDialogFragment=OkDialogFragment.newInstance("3","4");
        okDialogFragment.show(getSupportFragmentManager(),"ff");*/

        LinearLayout id_ll_toolbar_left = (LinearLayout) findViewById(R.id.id_ll_toolbar_left);
        id_ll_toolbar_left.setOnClickListener(new OnNotFastClickListener() {
            @Override
            protected void onNotFastClick(View v) {
                SSQSelectActivity.startMe(mContext, SSQSelectActivity.class);
            }
        });
        //
        ImageView id_toolbar_left_iv = (ImageView) findViewById(R.id.id_toolbar_left_iv);
        Drawable drawable = VectorOrImageResHelper.getDrawable(mContext, R.drawable.ic_place_black_24dp);
        id_toolbar_left_iv.setImageDrawable(drawable);
        mToolbarLeftTv = (TextView) findViewById(id_toolbar_left_tv);
        //
        initLeftTextValue();


        setToolbarTitle(R.string.nav_title_news);


        /**
         * mTabWidget
         */
        mTabWidget = findById(android.R.id.tabs);

        mTabWidgetHeight = ViewTool.getMeasuredHeightMy(mTabWidget);
        KLog.d("mTabWidgetHeight" + mTabWidgetHeight);

        initDrawerLayout();
        initDrawerLayoutRightMenuContent();
    }

    private void initLeftTextValue() {
        if (DataHolderSingleton.getInstance().getData(CommFinalData.HOLD_BDLOCATION) != null) {
            BDLocation bdLocation = (BDLocation) DataHolderSingleton.getInstance().getData(CommFinalData.HOLD_BDLOCATION);
            mToolbarLeftTv.setText(bdLocation.getDistrict());
            mToolbarLeftTv.setTag(R.id.id_view_location_holder, SSQHelper.getAreaID(bdLocation.getProvince(), bdLocation.getCity(), bdLocation.getDistrict()));
        } else {
            mToolbarLeftTv.setText("地区");
        }
    }

    RoundedImageView mImageView_userface;
    TextView id_day_night_switch;

    private void initDrawerLayoutRightMenuContent() {
        /**
         * drawerLayoutToolbar
         */
        Toolbar drawerLayoutToolbar = findById(R.id.id_drawer_layout_toolbar);
        drawerLayoutToolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mDrawerLayout.closeDrawers();
            }
        });
        //===========================
        mImageView_userface = findById(R.id.id_iv_userface);
        TextView id_tv_username = findById(R.id.id_tv_username);

        id_day_night_switch = findById(R.id.id_day_night_switch);
        id_day_night_switch.setText(DayNightHelper.isNight() ? R.string.text_night_to_day : R.string.text_day_to_night);
        LinearLayout id_ll_menu_item_1 = findById(R.id.id_ll_menu_item_1);
        LinearLayout id_ll_menu_item_2 = findById(R.id.id_ll_menu_item_2);
        LinearLayout id_ll_menu_item_3 = findById(R.id.id_ll_menu_item_3);
        LinearLayout id_ll_menu_item_4 = findById(R.id.id_ll_menu_item_4);
        LinearLayout id_ll_menu_item_5 = findById(R.id.id_ll_menu_item_5);
        LinearLayout id_ll_menu_item_6 = findById(R.id.id_ll_menu_item_6);

        mImageView_userface.setOnClickListener(onNotFastClickListener);
        id_tv_username.setOnClickListener(onNotFastClickListener);
        id_ll_menu_item_1.setOnClickListener(onNotFastClickListener);
        id_ll_menu_item_2.setOnClickListener(onNotFastClickListener);
        id_ll_menu_item_3.setOnClickListener(onNotFastClickListener);
        id_ll_menu_item_4.setOnClickListener(onNotFastClickListener);
        id_ll_menu_item_5.setOnClickListener(onNotFastClickListener);
        id_ll_menu_item_6.setOnClickListener(onNotFastClickListener);

    }

    private void goToUserInfo() {
        UserInfoActivity.startMe(mContext, UserInfoActivity.class);
    }

    private OnNotFastClickListener onNotFastClickListener = new OnNotFastClickListener() {
        @Override
        protected void onNotFastClick(View v) {
            switch (v.getId()) {
                case id_iv_userface:
                    //showToastShort("id_iv_userface");
                    goToUserInfo();
                    break;
                case R.id.id_tv_username:
                    //showToastShort("id_tv_username");
                    goToUserInfo();
                    break;
                case R.id.id_ll_menu_item_1://订阅
                    //showToastShort("1");
                    //startAty(ItemTouchActivity.class);
                    ItemTouchActivity.startMe(mContext, ItemTouchActivity.class);
                    break;
                case R.id.id_ll_menu_item_2://搜索
                    //showToastShort("2");
                    SearchActivity.startMe(mContext, SearchActivity.class);
                    // startAty(SearchActivity.class);
                    break;
                case id_ll_menu_item_3://夜间模式
                    // showToastShort("3");
                    // startAty(AppAuthActivity.class);
                    //mDrawerLayout.closeDrawers();
                    boolean isNight = (boolean) SharedPreferencesTool.get(KooApplication.getAppContext(), CommFinalData.SP_IS_NIGHT, false);
                    if (DayNightHelper.isNight()) {//当前isNight
                        id_day_night_switch.setText(R.string.text_night_to_day);
                        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
                    } else {
                        id_day_night_switch.setText(R.string.text_day_to_night);
                        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES);
                    }
                    SharedPreferencesTool.put(KooApplication.getAppContext(), CommFinalData.SP_IS_NIGHT, !isNight);
                    //recreate(); //调用recreate()使设置生效
                    DayNightHelper.refreshActivity(NewsActivity.this);
                    break;
                case R.id.id_ll_menu_item_4://扫一扫
                    //showToastShort("4");
                    //startAty(ScanZbarActivity.class);
                    ScanZbarActivity.startMe(mContext, ScanZbarActivity.class);
                    break;
                case R.id.id_ll_menu_item_5://收藏
                    showToastShort("5");
                    // startAty(ScanZXingActivity.class);
                    // startActivity(new Intent(NewsActivity.this, SSQSelectActivity.class));
                    break;
                case R.id.id_ll_menu_item_6://设置
                    //showToastShort("6");
                    //startAty(SettingActivity.class);
                    SettingActivity.startMe(mContext, SettingActivity.class);
                    break;

            }
        }
    };


    protected DrawerLayout mDrawerLayout;
    private ActionBarDrawerToggle mDrawerToggle;

    private void initDrawerLayout() {
        mDrawerLayout = (DrawerLayout) findViewById(R.id.id_drawer_layout);
        /**
         *
         */
        mDrawerToggle = new ActionBarDrawerToggle(this, mDrawerLayout, mToolbar, R.string.base_drawer_open, R.string.base_drawer_close);
        /**同步toolbar与drawerlayout的状态，也就是home的图标会改变,
         该方法会自动和actionBar关联, 将开关的图片显示在了action上，
         如果不设置，也可以有抽屉的效果，不过是默认的图标*/
        //######mDrawerToggle.syncState();

        mDrawerLayout.addDrawerListener(mDrawerToggle);
    }


    TabWidget mTabWidget;
    int mTabWidgetHeight;

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onEventMainThread(EventAreaSelected event) {
        mToolbarLeftTv.setText(event.getAreaName());
        mToolbarLeftTv.setTag(R.id.id_view_location_holder, event.getAreaID());
        //
        SSQAtyHelper.finishAll();
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onEventMainThread(EventShowHideTabHost event) {
        showOrHideTabHost(event.isShow());
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onEventMainThread(EventShowHideToolbar event) {
        showOrHideToolbar(event.isShow());
    }

    private void showOrHideTabHost(boolean show) {
        if (show) {
            ViewHelper.showView(mTabWidget);
        } else {
            ViewHelper.hideView(mTabWidget);
        }
    }

    private void showOrHideToolbar(boolean show) {
        if (show) {
            // ViewHelper.showView(mToolbar);
        } else {
            //ViewHelper.hideView(mToolbar);
        }
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.id_menu_item_user:
                mDrawerLayout.openDrawer(Gravity.RIGHT);
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onBackPressed() {
        /**
         * 先关闭DrawerLayout
         */
        if (mDrawerLayout.isDrawerOpen(Gravity.RIGHT)) {
            mDrawerLayout.closeDrawers();
        } else {
            super.onBackPressed();
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        KLog.d("main  aty  destroy!!!");
    }
}
