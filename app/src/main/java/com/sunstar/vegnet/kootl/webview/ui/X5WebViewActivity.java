package com.sunstar.vegnet.kootl.webview.ui;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.res.Configuration;
import android.graphics.Bitmap;
import android.graphics.PixelFormat;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;

import com.socks.library.KLog;
import com.sunstar.vegnet.R;
import com.sunstar.vegnet.custom.KooApplication;
import com.sunstar.vegnet.kootl.comm.base.BaseActivity;
import com.sunstar.vegnet.kootl.comm.base.BasePresenter;
import com.sunstar.vegnet.kootl.comm.dialog.OkCancelDialogFragment;
import com.sunstar.vegnet.kootl.comm.dialog.OkDialogFragment;
import com.sunstar.vegnet.kootl.comm.rvheaderfooter.RVHeaderFooterAdapter;
import com.sunstar.vegnet.kootl.comm.social.SocialHelper;
import com.sunstar.vegnet.kootl.comm.tabhost.TabHostShowDataInfo;
import com.sunstar.vegnet.kootl.comm.tool.DialogTool;
import com.sunstar.vegnet.kootl.comm.tool.ToastTool;
import com.sunstar.vegnet.kootl.imageshow.helper.ImageShowDataHelper;
import com.sunstar.vegnet.kootl.webview.helper.JavascriptInterfaceHelper;
import com.sunstar.vegnet.kootl.webview.widget.OkCancelPromptDialogFragment;
import com.tencent.smtt.export.external.interfaces.HttpAuthHandler;
import com.tencent.smtt.export.external.interfaces.JsPromptResult;
import com.tencent.smtt.export.external.interfaces.JsResult;
import com.tencent.smtt.export.external.interfaces.WebResourceRequest;
import com.tencent.smtt.export.external.interfaces.WebResourceResponse;
import com.tencent.smtt.sdk.DownloadListener;
import com.tencent.smtt.sdk.WebChromeClient;
import com.tencent.smtt.sdk.WebSettings;
import com.tencent.smtt.sdk.WebView;
import com.tencent.smtt.sdk.WebViewClient;


public class X5WebViewActivity extends BaseActivity<BasePresenter> {

    private String mUrl = "http://baidu.com";
    private WebView mX5webview;


    /**
     * 输入法设置
     * 避免输入法界面弹出后遮挡输入光标的问题
     * <p>
     * 方法一：在AndroidManifest.xml中设置
     * android:windowSoftInputMode="stateHidden|adjustResize"
     * 方法二：在代码中动态设置：
     * getWindow().setSoftInputMode(WindowManager.LayoutParams.
     * <p>
     * SOFT_INPUT_ADJUST_RESIZE | WindowManager.LayoutParams.
     * <p>
     * SOFT_INPUT_STATE_HIDDEN);
     *
     * @param savedInstanceState
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //##setContentView(R.layout.activity_x5_webview);
        //
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE | WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN);
        //
        getWindow().setFormat(PixelFormat.TRANSLUCENT);//（这个对宿主没什么影响，建议声明）


    }

    @Override
    protected int setupLayoutId() {
        return R.layout.activity_x5_webview;
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
        return R.menu.menu_share;
    }

    @Override
    protected int setupStatusBarColorResId() {
        return 0;
    }


    @Override
    protected void begin() {
        //start
        //## mHandler.post(mRunnable);
        initX5Webview();

        SocialHelper.initConfig(this);
    }

    private void initX5Webview() {

        //WebView x5webview=new WebView(this);
        mX5webview = (WebView) findViewById(R.id.id_x5webview);

        /**
         * 其他判断方法有问题  注意测试
         */
        if (mX5webview.getX5WebViewExtension() == null && KooApplication.isDebug()) {
            ToastTool.showShort("X5 内核未真正加载！");
        }
        /**
         *WebSettings
         */
        WebSettings webSetting = mX5webview.getSettings();
        webSetting.setJavaScriptEnabled(true);
        webSetting.setAllowFileAccess(true);
        webSetting.setLayoutAlgorithm(WebSettings.LayoutAlgorithm.NARROW_COLUMNS);
        webSetting.setSupportZoom(true);
        webSetting.setBuiltInZoomControls(true);
        webSetting.setUseWideViewPort(true);
        webSetting.setSupportMultipleWindows(false);
        //webSetting.setLoadWithOverviewMode(true);
        webSetting.setAppCacheEnabled(true);
        //webSetting.setDatabaseEnabled(true);
        webSetting.setDomStorageEnabled(true);
        webSetting.setGeolocationEnabled(true);
        webSetting.setAppCacheMaxSize(Long.MAX_VALUE);
        webSetting.setAppCachePath(this.getDir("appcache", 0).getPath());
        webSetting.setDatabasePath(this.getDir("databases", 0).getPath());
        webSetting.setGeolocationDatabasePath(this.getDir("geolocation", 0)
                .getPath());
        // webSetting.setPageCacheCapacity(IX5WebSettings.DEFAULT_CACHE_CAPACITY);
        webSetting.setPluginState(WebSettings.PluginState.ON_DEMAND);
        //webSetting.setRenderPriority(WebSettings.RenderPriority.HIGH);
        // webSetting.setPreFectch(true);

        /**
         * loadUrl
         */
        if (getIntent() != null && getIntent().getData() != null && !getIntent().getData().equals("")) {
            mUrl = getIntent().getData().toString();
        }
        //
        mX5webview.loadUrl(mUrl);

        /**
         * javascriptInterfaceHelper 别名要和js里面的一致
         */
        mX5webview.addJavascriptInterface(new JavascriptInterfaceHelper(X5WebViewActivity.this), "javascriptInterfaceHelper");


        //
        mX5webview.setWebViewClient(new WebViewClient() {

            /**
             * 防止加载网页时调起系统浏览器
             */
            @Override
            public boolean shouldOverrideUrlLoading(WebView webView, String url) {
                //return super.shouldOverrideUrlLoading(webView, url);
                webView.loadUrl(url);
                return true;
            }

            @Override
            public WebResourceResponse shouldInterceptRequest(WebView webView, WebResourceRequest webResourceRequest) {
                return super.shouldInterceptRequest(webView, webResourceRequest);
            }

            @Override
            public void onPageStarted(WebView webView, String url, Bitmap bitmap) {
                super.onPageStarted(webView, url, bitmap);
                KLog.d("onPageStarted:" + url);

                showSmileLoadingDialog();


            }

            @Override
            public void onPageFinished(WebView webView, String url) {
                super.onPageFinished(webView, url);

                /**
                 * 有链接也会add
                 */
                String javascriptInterfaceHelper_openImageShow_NeedAddJsStr =
                        "javascript:(function(){"
                                + "  var objs = document.getElementsByTagName(\"img\"); "
                                + "  for(var i=0;i<objs.length;i++){"
                                + "     objs[i].onclick=function(){"
                                + "          window.javascriptInterfaceHelper.jsWillCallJavaFun_openImageShow(this.src);"
                                + "     }"
                                + "  }"
                                + "})()";
                // KLog.d(javascriptInterfaceHelper_openImageShow_NeedAddJsStr);
                //###mX5webview.loadUrl(javascriptInterfaceHelper_openImageShow_NeedAddJsStr);

                hideLoadingDialog();

                KLog.d("onPageFinished;" + url);
            }

            @Override
            public void onReceivedHttpAuthRequest(WebView webView, HttpAuthHandler httpAuthHandler, String s, String s1) {
                super.onReceivedHttpAuthRequest(webView, httpAuthHandler, s, s1);
                boolean flag = httpAuthHandler.useHttpAuthUsernamePassword();
                Log.i("xxx", "useHttpAuthUsernamePassword is" + flag);
                //  Log.i("xxx", "HttpAuth host is" + host);
            }

            @Override
            public void onDetectedBlankScreen(String s, int i) {
                super.onDetectedBlankScreen(s, i);
            }
        });


        mX5webview.setWebChromeClient(new WebChromeClient() {
            @Override
            public void onReceivedTitle(WebView webView, String title) {
                super.onReceivedTitle(webView, title);
                setToolbarTitle(title);
            }

            @Override
            public void onProgressChanged(WebView webView, int newProgress) {
                super.onProgressChanged(webView, newProgress);
                //KLog.d("onProgressChanged:" + newProgress);
            }

            @Override
            public boolean onJsAlert(WebView webView, String url, String message, JsResult jsResult) {
                //return super.onJsAlert(webView, url, message, jsResult);
                OkDialogFragment okDialogFragment = OkDialogFragment.newInstance("", message);
                okDialogFragment.show(getSupportFragmentManager(), "onJsAlert");
                jsResult.confirm();
                return true;

            }

            @Override
            public boolean onJsPrompt(WebView webView, String url, String message, String defaultValue, final JsPromptResult jsPromptResult) {
                OkCancelPromptDialogFragment okCancelPromptDialogFragment = OkCancelPromptDialogFragment.newInstance("", message);
                okCancelPromptDialogFragment.show(getSupportFragmentManager(), "onJsPrompt");
                okCancelPromptDialogFragment.setOnBtnClickListener(new OkCancelPromptDialogFragment.OnBtnClickListener() {
                    @Override
                    public void onOkBtnClick(DialogInterface dialogInterface, String editTextValue) {
                        jsPromptResult.confirm(editTextValue);
                    }

                    @Override
                    public void onCancelBtnClick(DialogInterface dialogInterface) {
                        jsPromptResult.cancel();
                    }
                });
                return true;
                //return super.onJsPrompt(webView, s, s1, s2, jsPromptResult);
            }

            @Override
            public boolean onJsConfirm(WebView webView, String url, String message, final JsResult jsResult) {
                OkCancelDialogFragment okCancelDialogFragment = OkCancelDialogFragment.newInstance("", message);
                okCancelDialogFragment.show(getSupportFragmentManager(), "onJsConfirm");
                okCancelDialogFragment.setOnBtnClickListener(new OkCancelDialogFragment.OnBtnClickListener() {
                    @Override
                    public void onOkBtnClick(DialogInterface dialogInterface) {
                        jsResult.confirm();
                    }

                    @Override
                    public void onCancelBtnClick(DialogInterface dialogInterface) {
                        jsResult.cancel();
                    }
                });
                return true;
                //return super.onJsConfirm(webView, s, s1, jsResult);
            }

            @Override
            public boolean onJsTimeout() {
                hideLoadingDialog();
                return super.onJsTimeout();
            }
        });


        mX5webview.setDownloadListener(new DownloadListener() {
            @Override
            public void onDownloadStart(String url, String userAgent, String contentDisposition, String mimeType,
                                        long contentLength) {
                Uri uri = Uri.parse(url);
                Intent intent = new Intent(Intent.ACTION_VIEW, uri);
                startActivity(intent);
            }
        });


        /**
         *
         */
        mX5webview.getView().setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                WebView.HitTestResult hitTestResult = mX5webview.getHitTestResult();
                final String imagePath = hitTestResult.getExtra();
                switch (hitTestResult.getType()) {
                    //获取点击的标签是否为图片
                    case WebView.HitTestResult.IMAGE_TYPE:
                        // Toast.makeText(getApplicationContext(), "IMAGE_TYPE"+ path, Toast.LENGTH_LONG).show();
                    case WebView.HitTestResult.SRC_IMAGE_ANCHOR_TYPE:
                        // Toast.makeText(getApplicationContext(), "SRC_IMAGE_ANCHOR_TYPE"+ path, Toast.LENGTH_LONG).show();
                        showNeedSeeImageDialog(imagePath);
                        break;
                }

                KLog.d("hitTestResult:" + hitTestResult.getType());
                KLog.d("getView onLongClick:" + v);
                return false;
            }
        });
    }

    private void showNeedSeeImageDialog(final String path) {
        if ("".equals(path.trim())) {
            return;
        }
        DialogTool.showAskDialog(getSupportFragmentManager(), "是否确定查看图片?", new OkCancelDialogFragment.OnBtnClickListener() {
            @Override
            public void onOkBtnClick(DialogInterface dialogInterface) {
                ImageShowDataHelper.setDataAndToImageShow(X5WebViewActivity.this, path);
            }

            @Override
            public void onCancelBtnClick(DialogInterface dialogInterface) {
                /* do nothing */
            }
        });
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.id_menu_item_share:
                shareOperator();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    private void shareOperator() {
        SocialHelper.openShareView();
    }

    @Override
    public void onBackPressed() {
        //
        if (mX5webview.canGoBack()) {
            mX5webview.goBack();// 返回前一个页面
        } else {
            mX5webview.destroy();
            super.onBackPressed();
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        //
        SocialHelper.callAtOnActivityResult(requestCode, resultCode, data);
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        //
        SocialHelper.callAtOnConfigurationChanged();
    }

    /**
     * 在finish掉此activity时，把子view 全部remove掉。理论上说，只需要remove这个zoom view就可以，
     * 但是我没找到获取该view的办法，只好remove掉所有的子view。这样在activity destroy时就不会报 WindowLeaked的错误了
     */
    @Override
    public void finish() {
       /*和侧滑关闭  冲突
        ViewGroup view = (ViewGroup) getWindow().getDecorView();
        view.removeAllViews();*/
        super.finish();
    }
}
