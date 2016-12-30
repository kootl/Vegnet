package com.sunstar.vegnet.kootl.webview.ui;

import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.view.View;
import android.webkit.DownloadListener;
import android.webkit.JsPromptResult;
import android.webkit.JsResult;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.socks.library.KLog;
import com.sunstar.vegnet.R;
import com.sunstar.vegnet.kootl.comm.base.BaseActivity;
import com.sunstar.vegnet.kootl.comm.base.BasePresenter;
import com.sunstar.vegnet.kootl.comm.dialog.OkCancelDialogFragment;
import com.sunstar.vegnet.kootl.comm.dialog.OkDialogFragment;
import com.sunstar.vegnet.kootl.comm.rvheaderfooter.RVHeaderFooterAdapter;
import com.sunstar.vegnet.kootl.comm.tabhost.TabHostShowDataInfo;
import com.sunstar.vegnet.kootl.comm.tool.DialogTool;
import com.sunstar.vegnet.kootl.imageshow.helper.ImageShowDataHelper;
import com.sunstar.vegnet.kootl.webview.helper.JavascriptInterfaceHelper;
import com.sunstar.vegnet.kootl.webview.widget.OkCancelPromptDialogFragment;


@Deprecated
public class WebViewActivity extends BaseActivity<BasePresenter> {
    private WebView mWebview;
    private String mUrl = "http://www.qq.com";

    @Override
    protected int setupLayoutId() {
        return R.layout.activity_webview;
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
        return R.color.colorPrimary_Black;
    }


    @Override
    protected void begin() {
        initWebView();
    }

    private void initWebView() {
        /**
         *
         */
        mWebview = (WebView) findViewById(R.id.id_webview);
        /**
         *
         */
        /**
         *WebSettings
         */
        WebSettings webSetting = mWebview.getSettings();
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
        // webSetting.setPageCacheCapacity(WebSettings.DEFAULT_CACHE_CAPACITY);
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
        mWebview.loadUrl(mUrl);

        /**
         * javascriptInterfaceHelper 别名要和js里面的一致
         */
        mWebview.addJavascriptInterface(new JavascriptInterfaceHelper(WebViewActivity.this), "javascriptInterfaceHelper");


        mWebview.setOnLongClickListener(new View.OnLongClickListener() {

            @Override
            public boolean onLongClick(View v) {
                WebView.HitTestResult hitTestResult = mWebview.getHitTestResult();
                final String imagePath = hitTestResult.getExtra();
                switch (hitTestResult.getType()) {
                    //获取点击的标签是否为图片
                    case WebView.HitTestResult.IMAGE_TYPE:
                        // Toast.makeText(getApplicationContext(), "IMAGE_TYPE"+ path, Toast.LENGTH_LONG).show();
                    case WebView.HitTestResult.SRC_IMAGE_ANCHOR_TYPE:
                        // Toast.makeText(getApplicationContext(), "SRC_IMAGE_ANCHOR_TYPE"+ path, Toast.LENGTH_LONG).show();
                        //
                        showNeedSeeImageDialog(imagePath);
                        break;
                }
                KLog.d("hitTestResult:" + hitTestResult.getType());
                KLog.d("getView onLongClick:" + v);
                return false;
            }
        });

        mWebview.setWebViewClient(new WebViewClient() {

            @Override
            public void onPageStarted(WebView view, String url, Bitmap favicon) {
                super.onPageStarted(view, url, favicon);
                setToolbarTitle("");
                //
                KLog.d("onPageStarted;" + url);

                showSmileLoadingDialog();

            }

            @Override
            public void onPageFinished(WebView view, String url) {
                super.onPageFinished(view, url);
                //这个是一定要加上那个的,配合scrollView和WebView的height=wrap_content属性使用
                int w = View.MeasureSpec.makeMeasureSpec(0,
                        View.MeasureSpec.UNSPECIFIED);
                int h = View.MeasureSpec.makeMeasureSpec(0,
                        View.MeasureSpec.UNSPECIFIED);
                //重新测量
                mWebview.measure(w, h);
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
                //####mWebview.loadUrl(javascriptInterfaceHelper_openImageShow_NeedAddJsStr);

                hideLoadingDialog();

                KLog.d("onPageFinished;" + url);
            }
        });


        mWebview.setWebChromeClient(new WebChromeClient() {
            @Override
            public boolean onJsAlert(WebView view, String url, String message, JsResult result) {
                KLog.d("TEST:onJsAlert");
                OkDialogFragment okDialogFragment = OkDialogFragment.newInstance("", message);
                okDialogFragment.show(getSupportFragmentManager(), "onJsAlert");
                //return super.onJsAlert(view, url, message, result);
                result.confirm();
                return true;
            }

            @Override
            public boolean onJsConfirm(WebView view, String url, String message, final JsResult result) {
                KLog.d("TEST:onJsConfirm");
                OkCancelDialogFragment okCancelDialogFragment = OkCancelDialogFragment.newInstance("", message);
                okCancelDialogFragment.show(getSupportFragmentManager(), "onJsConfirm");
                okCancelDialogFragment.setOnBtnClickListener(new OkCancelDialogFragment.OnBtnClickListener() {
                    @Override
                    public void onOkBtnClick(DialogInterface dialogInterface) {
                        result.confirm();
                    }

                    @Override
                    public void onCancelBtnClick(DialogInterface dialogInterface) {
                        result.cancel();
                    }
                });
                //  return super.onJsConfirm(view, url, message, result);
                return true;
            }

            @Override
            public boolean onJsPrompt(WebView view, String url, String message, String defaultValue, final JsPromptResult result) {
                KLog.d("TEST:onJsPrompt");
                OkCancelPromptDialogFragment okCancelPromptDialogFragment = OkCancelPromptDialogFragment.newInstance("", message);
                okCancelPromptDialogFragment.show(getSupportFragmentManager(), "onJsPrompt");
                okCancelPromptDialogFragment.setOnBtnClickListener(new OkCancelPromptDialogFragment.OnBtnClickListener() {
                    @Override
                    public void onOkBtnClick(DialogInterface dialogInterface, String editTextValue) {
                        result.confirm(editTextValue);
                    }

                    @Override
                    public void onCancelBtnClick(DialogInterface dialogInterface) {
                        result.cancel();
                    }
                });
                // return super.onJsPrompt(view, url, message, defaultValue, result);
                return true;
            }

            @Override
            public void onReceivedTitle(WebView view, String title) {
                super.onReceivedTitle(view, title);
                KLog.d("TEST:onReceivedTitle");
                setToolbarTitle(title);
            }
        });

        mWebview.setDownloadListener(new DownloadListener() {
            @Override
            public void onDownloadStart(String url, String userAgent, String contentDisposition, String mimeType, long contentLength) {
                Uri uri = Uri.parse(url);
                Intent intent = new Intent(Intent.ACTION_VIEW, uri);
                startActivity(intent);
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
                ImageShowDataHelper.setDataAndToImageShow(WebViewActivity.this, path);
            }

            @Override
            public void onCancelBtnClick(DialogInterface dialogInterface) {
                /* do nothing */
            }
        });
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
    public void onBackPressed() {
        if (mWebview.canGoBack()) {
            mWebview.goBack();
        } else {
            mWebview.destroy();
            super.onBackPressed();
        }
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
