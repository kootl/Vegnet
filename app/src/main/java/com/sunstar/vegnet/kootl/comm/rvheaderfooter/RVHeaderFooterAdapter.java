package com.sunstar.vegnet.kootl.comm.rvheaderfooter;

import android.content.Context;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;

/**
 * Created by louisgeek on 2016/9/12.
 */
public abstract class RVHeaderFooterAdapter<T> extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    /**
     * 子类使用==============start====================
     */
    //分页第一页
    public static final int PAGE_NUM_DEFAULT = 1;
    //分页数量
    public static final int PAGE_SIZE = 20;
    //当前页码
    private int mPageNum = PAGE_NUM_DEFAULT;
    //是否在加载ing
    private boolean mDataLoading;
    //是否所有数据加载完毕（没有下一页）
    private boolean mLoadComplete;

    /**
     * 下一页
     *
     * @return
     */
    public int getNextPageNum() {
        return mPageNum + 1;
    }

    /**
     * 翻页
     *
     * @return
     */
    public void turnNextPageNum() {
        mPageNum = mPageNum + 1;
        //return mPageNum;
    }

    public int getPageNum() {
        return mPageNum;
    }

    //重置页码，一般用在下拉刷新
    public void resetPageNum() {
        mPageNum = PAGE_NUM_DEFAULT;
    }

    public void setDataLoading(boolean dataLoading) {
        mDataLoading = dataLoading;
    }

    public boolean isDataLoading() {
        return mDataLoading;
    }

    public void setLoadComplete(boolean loadComplete) {
        mLoadComplete = loadComplete;
    }

    public boolean isLoadComplete() {
        return mLoadComplete;
    }

    /**
     * 判断是否到达底部
     * 2016-11-3 16:24:00
     */
    public static boolean isReachedBottom(RecyclerView recyclerView) {
        LinearLayoutManager layoutManager = (LinearLayoutManager) recyclerView.getLayoutManager();
        //屏幕中最后一个可见子项的position
        int lastVisibleItemPosition = layoutManager.findLastVisibleItemPosition();
        //当前屏幕所看到的子项个数
        int visibleItemCount = layoutManager.getChildCount();
        //当前RecyclerView的所有子项个数
        int totalItemCount = layoutManager.getItemCount();
        //RecyclerView的滑动状态
        int state = recyclerView.getScrollState();
        if (visibleItemCount > 0 && lastVisibleItemPosition == totalItemCount - 1 && state == recyclerView.SCROLL_STATE_IDLE) {
            return true;
        } else {
            return false;
        }
    }

    /**
     * 子类使用===============start===================
     */

    public static final int TYPE_HEADER = 3210;
    public static final int TYPE_FOOTER = 3211;
    public static final int TYPE_NORMAL = 3212;
    private static final String TAG = "RVHeaderFooterAdapter";


    //private int mListItemLayoutID;
    //private int mHeaderViewLayoutID;
    //private int mFooterViewLayoutID;

    private RecyclerView mRecyclerView;
    private View mHeaderView;
    private View mFooterView;


    protected List<T> mDataList;
    private Context mContext;

    public RVHeaderFooterAdapter(List<T> dataList, RecyclerView recyclerView) {
        //
        mDataList = dataList;
        mContext = recyclerView.getContext();
        mRecyclerView = recyclerView;
    }

    public void setHeaderView(int headerViewLayoutID) {
        //dealHeaderFooterLayoutType();
        //mHeaderViewLayoutID = headerViewLayoutID;
        /**注意  root view 为recycleview*/
        View headerView = LayoutInflater.from(mContext).inflate(headerViewLayoutID, mRecyclerView, false);
        mHeaderView = headerView;
        notifyItemInserted(0);
    }

    public void setHeaderView(View headerView) {
        mHeaderView = headerView;
        notifyItemInserted(0);
    }

    public View getHeaderView() {
        return mHeaderView;
    }

    public void setFooterView(int footerViewLayoutID) {
        //dealHeaderFooterLayoutType();
        //mFooterViewLayoutID = footerViewLayoutID ;
        View footerView = LayoutInflater.from(mContext).inflate(footerViewLayoutID, mRecyclerView, false);
        mFooterView = footerView;
        notifyItemInserted(getItemCount() - 1);
    }

    public void setFooterView(View footerView) {
        mFooterView = footerView;
        notifyItemInserted(getItemCount() - 1);
    }

    public View getFooterView() {
        return mFooterView;
    }


    /**
     * 传入的dataList  因为引用传递和值传递的    所以引用地址不能和mDataList一致
     *
     * @param dataList
     */
    public void refreshDataList(List<T> dataList) {
        mDataList.clear();
        mDataList.addAll(dataList);
        notifyDataSetChanged();
    }

    /**
     * 传入的dataList  因为引用传递和值传递的    所以引用地址不能和mDataList一致
     *
     * @param dataList
     */
    public void appendDataList(List<T> dataList) {
        mDataList.addAll(dataList);
        notifyDataSetChanged();
    }

    @Override
    public void onAttachedToRecyclerView(RecyclerView recyclerView) {
        super.onAttachedToRecyclerView(recyclerView);
        // Log.i(TAG, "onAttachedToRecyclerView: ");

        //为GridLayoutManager 合并头布局的跨度
        RecyclerView.LayoutManager layoutManager = recyclerView.getLayoutManager();
        if (layoutManager instanceof GridLayoutManager) {
            final GridLayoutManager gridLayoutManager = (GridLayoutManager) layoutManager;
            gridLayoutManager.setSpanSizeLookup(new GridLayoutManager.SpanSizeLookup() {
                /**
                 * 抽象方法  返回当前index位置的item所占用的跨度的数量
                 * ##单元格合并  就是相当于占据了设定列spanCount的数量
                 * ##不合并     就是相当于占据了原来1个跨度
                 *
                 * @param position
                 * @return
                 */
                @Override
                public int getSpanSize(int position) {
                    return (getItemViewType(position) == TYPE_HEADER || getItemViewType(position) == TYPE_FOOTER) ? gridLayoutManager.getSpanCount() : 1;
                }
            });
            //
           /* mNowItemLayoutID = gridLayoutManager.getOrientation() == GridLayoutManager.HORIZONTAL
                    ? mList_Item_LayoutID_4_horizontal : mList_Item_LayoutID;*/
            //
        } else if (layoutManager instanceof StaggeredGridLayoutManager) {
            StaggeredGridLayoutManager staggeredGridLayoutManager = (StaggeredGridLayoutManager) layoutManager;
           /* mNowItemLayoutID = staggeredGridLayoutManager.getOrientation() == StaggeredGridLayoutManager.HORIZONTAL
                    ? mList_Item_LayoutID_4_horizontal : mList_Item_LayoutID;*/

        } else if (layoutManager instanceof LinearLayoutManager) {
            LinearLayoutManager linearLayoutManager = (LinearLayoutManager) layoutManager;
          /*  mNowItemLayoutID = linearLayoutManager.getOrientation() == LinearLayoutManager.HORIZONTAL
                    ? mList_Item_LayoutID_4_horizontal : mList_Item_LayoutID;*/

        }
    }

    @Override
    public int getItemViewType(int position) {
        int realPos = position;
        if (mHeaderView != null && mFooterView != null) {
            if (position == 0) {
                return TYPE_HEADER;
            } else if (position == getItemCount() - 1) {
                return TYPE_FOOTER;
            }
            //
            realPos = position - 1;
        } else if (mHeaderView != null) {
            if (position == 0) {
                return TYPE_HEADER;
            }
            //
            realPos = position - 1;
        } else if (mFooterView != null) {
            if (position == getItemCount() - 1) {
                return TYPE_FOOTER;
            }
        }


        int viewType = TYPE_NORMAL;

        final T data = mDataList.get(realPos);
        if (getItemViewTypeInner(realPos, data) != 0) {
            viewType = getItemViewTypeInner(realPos, data);
        }

        //KLog.d("qqq getItemViewType:" + position);

        return viewType;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if (mHeaderView != null && viewType == TYPE_HEADER) {
            return new RVHeaderFooterViewHolder(mHeaderView);
        }
        if (mFooterView != null && viewType == TYPE_FOOTER) {
            return new RVHeaderFooterViewHolder(mFooterView);
        }
        return onCreateViewHolderInner(parent, viewType);
        //  return new RVHeaderFooterViewHolder(view);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder viewHolder, final int position) {
        //   Log.i(TAG, "onBindViewHolder: ");
        //
        //is StaggeredGridLayoutManager  config  header FullSpan
/*        ViewGroup.LayoutParams vg_lp = viewHolder.itemView.getLayoutParams();
        if (vg_lp != null && vg_lp instanceof StaggeredGridLayoutManager.LayoutParams) {
            StaggeredGridLayoutManager.LayoutParams sglm_lp = (StaggeredGridLayoutManager.LayoutParams) vg_lp;
            //### sglm_lp.setFullSpan(viewHolder.getLayoutPosition()==0);
            if (getItemViewType(position) == TYPE_HEADER) {
                sglm_lp.setFullSpan(position == 0);
            }
            if (getItemViewType(position) == TYPE_FOOTER) {
                sglm_lp.setFullSpan(position == getItemCount() - 1);
            }

            //
         *//*   if (position != 0) {
                if (list_Item_LayoutID == R.layout.list_item) {
                    sglm_lp.height = position % 3 == 0 ? 200 : 400;
                } else if (list_Item_LayoutID == R.layout.list_item_4_horizontal) {
                    sglm_lp.width = position % 3 == 0 ? 150 : 300;
                }
            }*//*
        }*/

        int itemType = getItemViewType(position);

        //KLog.d("qqq itemType:" + itemType);
        if (itemType == TYPE_HEADER || itemType == TYPE_FOOTER) {
            return;
        }

       /*### int position = viewHolder.getLayoutPosition();
        return mHeaderView == null ? position : position - 1;*/
        int realPos = position;
        if (mHeaderView != null) {
            realPos = position - 1;
        }

        final int pos = realPos;

        final T data = mDataList.get(pos);

        onBindViewHolderInner(viewHolder, pos, data);

       /* if(viewHolder instanceof MyRecyclerViewViewHolder) {
            ((MyRecyclerViewViewHolder) viewHolder).id_tv.setText(data);
           */
        viewHolder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (onItemClickListener != null) {
                    onItemClickListener.onItemClick(v, pos, data);
                }
            }
        });
        viewHolder.itemView.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                if (onItemLongClickListener != null) {
                    onItemLongClickListener.onItemLongClick(v, position, data);
                }
                return false;
            }
        });


    }

    public void dealHeaderFooterLayoutType() {
        /**单独为HORIZONTAL 设置左侧的布局 右侧布局*/
        RecyclerView.LayoutManager layoutManager = mRecyclerView.getLayoutManager();
        if (layoutManager instanceof StaggeredGridLayoutManager) {
            StaggeredGridLayoutManager staggeredGridLayoutManager = (StaggeredGridLayoutManager) layoutManager;
            /*mNowHeaderLayoutID = staggeredGridLayoutManager.getOrientation() == StaggeredGridLayoutManager.HORIZONTAL
                    ? mHeader_LayoutID_4_horizontal : mHeader_LayoutID;
            mNowFooterLayoutID = staggeredGridLayoutManager.getOrientation() == StaggeredGridLayoutManager.HORIZONTAL
                    ? mFooter_LayoutID_4_horizontal : mFooter_LayoutID;*/
        } else if (layoutManager instanceof GridLayoutManager) {
            GridLayoutManager gridLayoutManager = (GridLayoutManager) layoutManager;
            /*mNowHeaderLayoutID = gridLayoutManager.getOrientation() == GridLayoutManager.HORIZONTAL
                    ? mHeader_LayoutID_4_horizontal : mHeader_LayoutID;
            mNowFooterLayoutID = gridLayoutManager.getOrientation() == GridLayoutManager.HORIZONTAL
                    ? mFooter_LayoutID_4_horizontal : mFooter_LayoutID;*/
        } else if (layoutManager instanceof LinearLayoutManager) {
            LinearLayoutManager linearLayoutManager = (LinearLayoutManager) layoutManager;
            /*mNowHeaderLayoutID = linearLayoutManager.getOrientation() == LinearLayoutManager.HORIZONTAL
                    ? mHeader_LayoutID_4_horizontal : mHeader_LayoutID;
            mNowFooterLayoutID = linearLayoutManager.getOrientation() == LinearLayoutManager.HORIZONTAL
                    ? mFooter_LayoutID_4_horizontal : mFooter_LayoutID;*/
        }
    }

    public abstract void onBindViewHolderInner(RecyclerView.ViewHolder viewHolder, int realPosition, T data);

    public abstract RecyclerView.ViewHolder onCreateViewHolderInner(ViewGroup parent, int viewType);

    public abstract int getItemViewTypeInner(int realPosition, T data);

    @Override
    public int getItemCount() {
        if (mHeaderView != null && mFooterView != null) {
            return mDataList.size() + 1 + 1;
        } else if (mHeaderView != null) {
            return mDataList.size() + 1;
        } else if (mFooterView != null) {
            return mDataList.size() + 1;
        }
        return mDataList.size();
    }

/*    class MyRecyclerViewViewHolder extends RecyclerView.ViewHolder {

        TextView id_tv;

        public MyRecyclerViewViewHolder(View itemView) {
            super(itemView);
            if (itemView == mHeaderView || itemView == mFooterView) {
                return;
            }
          //  id_tv = (TextView) itemView.findViewById(R.id.id_tv);
        }
    }*/

    public interface OnItemClickListener {
        void onItemClick(View itemView, int position, Object data);
    }

    public interface OnItemLongClickListener {
        void onItemLongClick(View itemView, int position, Object data);
    }

    private OnItemClickListener onItemClickListener;
    private OnItemLongClickListener onItemLongClickListener;

    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }

    public void setOnItemLongClickListener(OnItemLongClickListener onItemLongClickListener) {
        this.onItemLongClickListener = onItemLongClickListener;
    }


}
