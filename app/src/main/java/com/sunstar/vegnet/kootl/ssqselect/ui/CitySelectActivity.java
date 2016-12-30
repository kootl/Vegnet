package com.sunstar.vegnet.kootl.ssqselect.ui;

import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.github.promeg.pinyinhelper.Pinyin;
import com.sunstar.vegnet.R;
import com.sunstar.vegnet.kootl.ssqselect.adapter.SelectAreaHFRecyclerViewAdapter;
import com.sunstar.vegnet.kootl.ssqselect.bean.AreaSelectBean;
import com.sunstar.vegnet.kootl.ssqselect.widget.PinyinBannerColumnView;
import com.sunstar.vegnet.kootl.comm.base.BaseActivity;
import com.sunstar.vegnet.kootl.comm.base.BasePresenter;
import com.sunstar.vegnet.kootl.comm.rvheaderfooter.RVHeaderFooterAdapter;
import com.sunstar.vegnet.kootl.comm.tabhost.TabHostShowDataInfo;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;



@Deprecated
public class CitySelectActivity extends BaseActivity<BasePresenter> {
    private static final String TAG = "CitySelectActivity";

    private RecyclerView id_recycler_view;
    private List<AreaSelectBean> mAreaSelectBeanList = new ArrayList<>();
    private TextView id_tv_indicator_top_view;


    @Override
    protected int setupLayoutId() {
        return R.layout.activity_city_select;
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

        /**
         * 重庆等多音字待处理。。。
         */
        initData();

        id_tv_indicator_top_view  = (TextView) findViewById(R.id.id_tv_indicator_top_view);
        //id_tv_sticky_header_view.setBackgroundColor(ContextCompat.getColor(this,R.color.colorAccent));
        PinyinBannerColumnView id_pinyin_banner_column_view = (PinyinBannerColumnView) findViewById(R.id.id_pinyin_banner_column_view);
        TextView id_tv_indicator_center_view = (TextView) findViewById(R.id.id_tv_indicator_center_view);
        id_pinyin_banner_column_view.setupIndicatorCenterView(id_tv_indicator_center_view);
        id_pinyin_banner_column_view.setSelectAreaBeanList(mAreaSelectBeanList);
        id_pinyin_banner_column_view.setOnSlidedListener(new PinyinBannerColumnView.OnSlidedListener() {
            @Override
            public void onSlided(String pinyinFirst) {
                dealListstayWhere(pinyinFirst);
            }
        });
        id_recycler_view = (RecyclerView) findViewById(R.id.id_recycler_view);
        id_recycler_view.setLayoutManager(new LinearLayoutManager(this));
        SelectAreaHFRecyclerViewAdapter adapter = new SelectAreaHFRecyclerViewAdapter(mAreaSelectBeanList);
        adapter.setOnItemClickListener(new SelectAreaHFRecyclerViewAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View v, int position) {

                //Toast.makeText(MySelectAreaActivity.this, "position:" + position, Toast.LENGTH_SHORT).show();
            }
        });
        id_recycler_view.setAdapter(adapter);
        id_recycler_view.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);

                //Log.d(TAG, "onScrolled: dx:" + dx + ",dy:" + dy);
                /**
                 * 头部停留   参考自网络
                 */
                //返回指定位置的childView，这里也就是取得item的头部布局stickyInfoView，x,y只要是id_tv_sticky_header_view里面就行
                View stickyInfoView = recyclerView.findChildViewUnder(
                        id_tv_indicator_top_view.getMeasuredWidth() / 2, id_tv_indicator_top_view.getMeasuredHeight() / 2);
                if (stickyInfoView != null && stickyInfoView.getContentDescription() != null) {
                    id_tv_indicator_top_view.setText(stickyInfoView.getContentDescription().toString());
                }
                //返回的是固定在屏幕上方那个id_tv_sticky_header_view下面1像素位置的RecyclerView的item，
                // 根据这个item来更新id_tv_sticky_header_view要translate多少距离。
                Log.d(TAG, "onScrolled: id_tv_indicator_top_view.getMeasuredHeight()" + id_tv_indicator_top_view.getMeasuredHeight());
                View nextInfoView = recyclerView.findChildViewUnder(
                        id_tv_indicator_top_view.getMeasuredWidth() / 2, id_tv_indicator_top_view.getMeasuredHeight() + 1);
                //如果tag为HAS_STICKY_VIEW，表示当前item需要展示头部布局，那么根据这个item的getTop和id_tv_sticky_header_view的
                // 高度相差的距离来滚动id_tv_sticky_header_view；如果tag为NONE_STICKY_VIEW，
                // 表示当前item不需要展示头部布局，那么就不会引起id_tv_sticky_header_view的滚动。
                if (nextInfoView != null && nextInfoView.getTag() != null) {
                    int moveInfoViewStatus = (int) nextInfoView.getTag();
                    //view 顶部距离Y
                    int dealtY = nextInfoView.getTop() - id_tv_indicator_top_view.getMeasuredHeight();
                    Log.d(TAG, "onScrolled: dealtY:" + dealtY + ",getTop:" + nextInfoView.getTop());
                    if (moveInfoViewStatus == SelectAreaHFRecyclerViewAdapter.HAS_STICKY_VIEW) {
                        if (nextInfoView.getTop() > 0) {
                            //nextInfoView距离最顶部还有距离
                            //位移
                            id_tv_indicator_top_view.setTranslationY(dealtY);
                            Log.d(TAG, "onScrolled: dealtY:" + dealtY);
                        } else {
                            //nextInfoView已经往上移动到或超出最顶部区域了
                            //归位
                            id_tv_indicator_top_view.setTranslationY(0);
                            Log.d(TAG, "onScrolled: dealtY: setTranslationY(0)");
                        }
                    } else if (moveInfoViewStatus == SelectAreaHFRecyclerViewAdapter.NONE_STICKY_VIEW) {
                        //nextInfoView 没有头部 不做移动改变
                        //归位
                        id_tv_indicator_top_view.setTranslationY(0);
                        Log.d(TAG, "onScrolled: NONE_STICKY_VIEW: setTranslationY(0)");
                    } else if (moveInfoViewStatus == SelectAreaHFRecyclerViewAdapter.FIRST_STICKY_VIEW) {
                        //
                        Log.d(TAG, "onScrolled: FIRST_STICKY_VIEW");
                    }
                }

            }
        });


    }

    private void dealListstayWhere(String pinyinFirst) {
        for (int i = 0; i < mAreaSelectBeanList.size(); i++) {
            String firstPinyin = mAreaSelectBeanList.get(i).getPinyin().charAt(0) + "";
            if (firstPinyin.equals(pinyinFirst)) {
                id_recycler_view.smoothScrollToPosition(i);
                break;
            }
        }
    }

    private void initData() {
        //
        mAreaSelectBeanList = new ArrayList<>();
        for (int i = 0; i < AreaSelectBean.provinceArray.length; i++) {
            AreaSelectBean selectAreaBean = new AreaSelectBean();
            selectAreaBean.setID(i);
            selectAreaBean.setAreaID("key_" + i);
            selectAreaBean.setName(AreaSelectBean.provinceArray[i]);
            selectAreaBean.setPinyin(parseChineseToPinyin(AreaSelectBean.provinceArray[i]));
            mAreaSelectBeanList.add(selectAreaBean);
        }
        //比较器
        PinyinComparator pinyinComparator = new PinyinComparator();
        //排序
        Collections.sort(mAreaSelectBeanList, pinyinComparator);
    }

    /**
     * /**
     * 如果c为汉字，则返回大写拼音；如果c不是汉字，则返回String.valueOf(c)
     * Pinyin.toPinyin(char c)
     * c为汉字，则返回true，否则返回false
     * Pinyin.isChinese(char c)
     *
     * @param inStr
     * @return
     */
    private String parseChineseToPinyin(String inStr) {
        StringBuilder stringBuilder = new StringBuilder();
        for (int i = 0; i < inStr.length(); i++) {
            stringBuilder.append(Pinyin.toPinyin(inStr.charAt(i)));
        }
        return stringBuilder.toString();
    }


    public class PinyinComparator implements Comparator<AreaSelectBean> {
        @Override
        public int compare(AreaSelectBean first, AreaSelectBean second) {
            return first.getPinyin().compareTo(second.getPinyin());
        }
    }
}