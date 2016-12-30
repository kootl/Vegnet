package com.sunstar.vegnet.kootl.preference;


import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.preference.CheckBoxPreference;
import android.preference.Preference;
import android.preference.PreferenceFragment;

import com.louisgeek.checkappupdatelib.helper.CheckUpdateHelper;
import com.louisgeek.checkappupdatelib.tool.SimpleCheckUpdateTool;
import com.sunstar.vegnet.R;
import com.sunstar.vegnet.kootl.changepd.ChangePasswordActivity;
import com.sunstar.vegnet.kootl.comm.base.BaseActivity;
import com.sunstar.vegnet.kootl.comm.base.BasePresenter;
import com.sunstar.vegnet.kootl.comm.bus.event.EventCheckUpdate;
import com.sunstar.vegnet.kootl.comm.bus.event.EventPreferenceShowAskDialog;
import com.sunstar.vegnet.kootl.comm.config.CommFinalData;
import com.sunstar.vegnet.kootl.comm.dialog.OkCancelDialogFragment;
import com.sunstar.vegnet.kootl.comm.helper.PreferenceManagerHelper;
import com.sunstar.vegnet.kootl.comm.lrucache.LruCacheBitmapTool;
import com.sunstar.vegnet.kootl.comm.lrucache.LruCacheStringTool;
import com.sunstar.vegnet.kootl.comm.rvheaderfooter.RVHeaderFooterAdapter;
import com.sunstar.vegnet.kootl.comm.tabhost.TabHostShowDataInfo;
import com.sunstar.vegnet.kootl.comm.tool.AppTool;
import com.sunstar.vegnet.kootl.comm.tool.DialogTool;
import com.sunstar.vegnet.kootl.comm.tool.SharedPreferencesTool;
import com.sunstar.vegnet.kootl.comm.tool.ToastTool;
import com.sunstar.vegnet.kootl.feedback.FeedBackActivity;
import com.sunstar.vegnet.kootl.scanqr.QrCodeActivity;
import com.sunstar.vegnet.kootl.usercenter.ui.UserLoginActivity;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

/**
 * 基本控件：
 * <p>
 * PreferenceScreen:PreferenceActivity的根元素
 * |-PreferenceCategory: 用于分组。
 * |-CheckBoxPreference:    CheckBox选择项
 * |-EditTextPreference:    编辑框，会弹出输入对话框。
 * |-ListPreference:        列表选择，弹出对话框选择。
 * |-Preference:            文本显示。
 * |-RingtonePreference：    系统玲声选择。
 * 常用属性：
 * <p>
 * title:标题
 * key：唯一标识
 * summary:副标题、说明
 * defaultValue:默认值
 * android:summaryOn：属性开启的时候的说明
 * android:summaryOff：属性关闭时候的说明
 * dialogTitle：弹出对话框的标题
 * entries：列表中显示的值。为一个数组，通读通过资源文件进行设置。
 * entryValues：列表中保存的值，为一个数组，通读通过资源文件进行设置。
 */
public class SettingActivity extends BaseActivity<BasePresenter> {


    @Override
    protected int setupLayoutId() {
        return R.layout.activity_setting;
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
        return 0;
    }

    @Override
    protected int setupStatusBarColorResId() {
        return 0;
    }

    @Override
    protected void begin() {
        setToolbarTitle(R.string.nav_title_setting);

        initPreferenceFragment();

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        SimpleCheckUpdateTool.updateNormalUnregister(this);
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onEventMainThread(EventCheckUpdate event) {
        showSmileLoadingDialog();
        //### SimpleCheckUpdateTool.updateNormal(this, CommFinalData.PGYER_APP_ID, CommFinalData.PGYER_API_KEY);
        CheckUpdateHelper.initFirst(CommFinalData.PGYER_APP_ID, CommFinalData.PGYER_API_KEY);
        CheckUpdateHelper.checkUpdate(this, new CheckUpdateHelper.CheckUpdateCallBack() {
            @Override
            public void backHasUpdate(boolean hasUpdate) {
                hideLoadingDialog(0);
                if (!hasUpdate) {
                    ToastTool.showImageInfo("当前已经是最新版本！");
                }
            }
        });
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onEventMainThread(EventPreferenceShowAskDialog event) {
        DialogTool.showAskDialog(getSupportFragmentManager(), "是否清除缓存?", new OkCancelDialogFragment.OnBtnClickListener() {
            @Override
            public void onOkBtnClick(DialogInterface dialogInterface) {
                //
                SharedPreferencesTool.clear(mContext);
                LruCacheStringTool.removeAllDiskCache();
                LruCacheBitmapTool.removeAllDiskCache();
                ToastTool.showImageOk("清除成功");
            }

            @Override
            public void onCancelBtnClick(DialogInterface dialogInterface) {

            }
        });
    }


    private void initPreferenceFragment() {
        //
        FragmentManager fragmentManager = getFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.id_frame_content, new SettingPreferenceFragement());
        fragmentTransaction.commit();//
    }


    public static class SettingPreferenceFragement extends PreferenceFragment implements Preference.OnPreferenceChangeListener,
            Preference.OnPreferenceClickListener {
        @Override
        public void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);

            addPreferencesFromResource(R.xml.pref_setting);


            Preference preference_user = findPreference("preference_user");
            Preference preference_password = findPreference("preference_password");

            CheckBoxPreference checkbox_preference_nopic = (CheckBoxPreference) findPreference("checkbox_preference_nopic");

            Preference preference_clear = findPreference("preference_clear");
            Preference preference_update = findPreference("preference_update");

            String versionFormat = getResources().getString(R.string.base_version);
            String versionStr = String.format(versionFormat, AppTool.getVersionName(getActivity()), AppTool.getVersionCode(getActivity()));
            preference_update.setSummary(versionStr);

            Preference preference_feedback = findPreference("preference_feedback");
            Preference preference_about = findPreference("preference_about");

            preference_user.setOnPreferenceClickListener(this);
            preference_password.setOnPreferenceClickListener(this);

            //setOnPreferenceChangeListener
            checkbox_preference_nopic.setOnPreferenceChangeListener(this);

            preference_clear.setOnPreferenceClickListener(this);
            preference_update.setOnPreferenceClickListener(this);


            preference_feedback.setOnPreferenceClickListener(this);
            preference_about.setOnPreferenceClickListener(this);


            boolean isNoPic = PreferenceManagerHelper.isNoPic(getActivity());
            checkbox_preference_nopic.setChecked(isNoPic);
            /*//程序获取android实现的保存状态的SharedPreferences值 【方法1】

            SharedPreferences sp_big = checkbox_preference_big.getSharedPreferences();
            checkbox_preference_big.setChecked(sp_big.getBoolean("checkbox_preference_big", false));

            SharedPreferences sp_msg = checkbox_preference_msg.getSharedPreferences();
            checkbox_preference_msg.setChecked(sp_wifi.getBoolean("checkbox_preference_msg", false));*/

           /* // 程序获取android实现的保存状态的SharedPreferences值 【方法2】
            // 得到我们的存储Preferences值的对象，然后对其进行相应操作
            SharedPreferences shp = PreferenceManager.getDefaultSharedPreferences(this);
            boolean apply_wifiChecked = shp.getBoolean("apply_wifi", false);*/

        }

        @Override
        public boolean onPreferenceChange(Preference preference, Object newValue) {
            switch (preference.getKey()) {

                case "checkbox_preference_nopic":
                    //ToastTool.showShort("checkbox_preference_nopic newValue:" + newValue);
                    boolean isNoPic = (boolean) newValue;
                    PreferenceManagerHelper.setNoPic(preference.getEditor(), isNoPic);
                    break;
            }
            return true;
            // return false;
        }

        @Override
        public boolean onPreferenceClick(Preference preference) {
            switch (preference.getKey()) {
                case "preference_user":
                    //Toast.makeText(getActivity(), "preference_user", Toast.LENGTH_SHORT).show();
                    startActivity(new Intent(getActivity(), UserLoginActivity.class));
                    break;
                case "preference_password":
                    //Toast.makeText(getActivity(), "preference_password", Toast.LENGTH_SHORT).show();
                    startActivity(new Intent(getActivity(), ChangePasswordActivity.class));
                    break;
                case "preference_clear":
                    // startActivity(new Intent(getActivity(), GuideActivity.class));
                    EventBus.getDefault().post(new EventPreferenceShowAskDialog());
                    break;
                case "preference_update":
                    // ToastTool.showImageOk("已经是最新版本了！");
                    EventBus.getDefault().post(new EventCheckUpdate());
                    break;
                case "preference_feedback":
                    //  Toast.makeText(getActivity(), "preference_feedback", Toast.LENGTH_SHORT).show();
                    startActivity(new Intent(getActivity(), FeedBackActivity.class));
                    break;
                case "preference_about":
                    // Toast.makeText(getActivity(), "preference_about", Toast.LENGTH_SHORT).show();
                    startActivity(new Intent(getActivity(), QrCodeActivity.class));
                    break;
            }
            return true;
            // return false;
        }
    }


}
