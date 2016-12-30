package com.sunstar.vegnet.kootl.rvItemtouch.adapter;

import android.app.Service;
import android.content.Context;
import android.os.Vibrator;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.socks.library.KLog;
import com.sunstar.vegnet.R;
import com.sunstar.vegnet.kootl.comm.listener.OnNotFastClickListener;
import com.sunstar.vegnet.kootl.rvItemtouch.bean.ItemTouchBean;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Created by louisgeek on 2016/12/13.
 */

public class ItemTouchHelperRVAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    public final static int VIEW_TYPE_MY_TITLE = 111;
    public final static int VIEW_TYPE_MY_LIST = 112;
    public final static int VIEW_TYPE_MORE_TITLE = 113;
    public final static int VIEW_TYPE_MORE_LIST = 114;

    public List<ItemTouchBean> getItemTouchBeanList() {
        return mItemTouchBeanList;
    }

    private List<ItemTouchBean> mItemTouchBeanList = new ArrayList<>();
    private List<Integer> mCanNotDragPosList = new ArrayList<>();


    public ItemTouchHelperRVAdapter(List<ItemTouchBean> itemTouchBeanList, RecyclerView recyclerView, int listItemLayoutID) {
        initItemTouchHelper(recyclerView);
        mListItemLayoutID = listItemLayoutID;
        mItemTouchBeanList = itemTouchBeanList;
        /**
         *不可移动的index
         */
        mCanNotDragPosList.add(0);
        mCanNotDragPosList.add(1);


        /**
         * 类似于合并单元格
         */
        if (recyclerView.getLayoutManager() instanceof GridLayoutManager) {
            GridLayoutManager gridLayoutManager = (GridLayoutManager) recyclerView.getLayoutManager();
            final int spanCount = gridLayoutManager.getSpanCount();
            gridLayoutManager.setSpanSizeLookup(new GridLayoutManager.SpanSizeLookup() {
                @Override
                public int getSpanSize(int position) {
                    switch (getItemViewType(position)) {
                        case VIEW_TYPE_MY_TITLE:
                        case VIEW_TYPE_MORE_TITLE:
                            return spanCount;//标题跨行
                        default:
                            return 1;
                    }
                }
            });
        }
    }

    private int findMoreTitleItemIndex() {
        int pos = 0;
        /**
         *找到VIEW_TYPE_MORE_TITLE的index
         */
        for (int i = 0; i < mItemTouchBeanList.size(); i++) {
            if (mItemTouchBeanList.get(i).getViewType() == VIEW_TYPE_MORE_TITLE) {
                pos = i;
                break;
            }
        }
        KLog.d("MoreTitleItemIndex:" + pos);
        return pos;
    }

    private int mListItemLayoutID;

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        RecyclerView.ViewHolder viewHolder = null;
        switch (viewType) {
            case VIEW_TYPE_MY_TITLE:
            case VIEW_TYPE_MORE_TITLE:
                View titleView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_list_title_one_line, parent, false);
                viewHolder = new TitleRecyclerViewViewHolder(titleView);
                break;
            case VIEW_TYPE_MY_LIST:
            case VIEW_TYPE_MORE_LIST:
                View listView = LayoutInflater.from(parent.getContext()).inflate(mListItemLayoutID, parent, false);
                viewHolder = new ListRecyclerViewViewHolder(listView);
                break;
        }
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, final int position) {
        if (holder instanceof ListRecyclerViewViewHolder) {
            final ListRecyclerViewViewHolder viewHolder = (ListRecyclerViewViewHolder) holder;
            viewHolder.id_tv_item_title.setText(mItemTouchBeanList.get(position).getText());

            viewHolder.itemView.setBackgroundColor(ContextCompat.getColor(viewHolder.itemView.getContext(),R.color.baseGridItemBg));
            /**
             * 设置不可拖动的背景
             */
            if (mCanNotDragPosList.contains(position)) {
                viewHolder.itemView.setAlpha(0.6f);
            } else {
                viewHolder.itemView.setAlpha(1f);
            }

            viewHolder.itemView.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View v) {
                    KLog.d("position:" + position);
                    int moreTitleItemIndex = findMoreTitleItemIndex();
                    int longClickPosition = viewHolder.getAdapterPosition();
                    /**
                     * 设置可拖动
                     *
                     * 我的订阅部分的item能拖动，
                     * 并且 item未设置不可拖动的，则执行拖拽
                     */
                    if (!mCanNotDragPosList.contains(position) && longClickPosition < moreTitleItemIndex) {
                        mItemTouchHelper.startDrag(viewHolder);
                        KLog.d("viewHolder.getAdapterPosition() startDrag:" + viewHolder.getAdapterPosition());
                        /**
                         *
                         */
                        Context context = viewHolder.itemView.getContext();
                        //获取系统震动服务
                        Vibrator vib = (Vibrator) context.getSystemService(Service.VIBRATOR_SERVICE);
                        //震动70毫秒
                        vib.vibrate(70);
                    }
                    // return false;
                    return true;//消费事件
                }
            });
            /**
             * 设置OnNotFastViewClickListener 代替View.OnClickListener
             * 因为过快点击会导致在交换的时候pos错乱
             */
            viewHolder.itemView.setOnClickListener(new OnNotFastClickListener() {
                @Override
                protected void onNotFastClick(View v) {

                    //item 不可拖动
                    if (mCanNotDragPosList.contains(position)) {
                        return;
                    }

                    int moreTitleItemIndex = findMoreTitleItemIndex();

                    int clickPosition = viewHolder.getAdapterPosition();//得到点击ViewHolder的position
                    int newPosition;
                    KLog.d("clickPosition:" + clickPosition);
                    if (clickPosition > moreTitleItemIndex) {//大于moreTitleItemIndex  支持点击订阅新的channel
                        newPosition = moreTitleItemIndex;//相当于和更多订阅交换位置
                    } else {
                        //小于等于moreTitleItemIndex  支持点击取消订阅 当然等于是不会进来了
                        newPosition = mItemTouchBeanList.size() - 1;
                    }
                    if (clickPosition < newPosition) {
                        for (int i = clickPosition; i < newPosition; i++) {
                            Collections.swap(mItemTouchBeanList, i, i + 1);
                        }
                    } else {
                        for (int i = clickPosition; i > newPosition; i--) {
                            Collections.swap(mItemTouchBeanList, i, i - 1);
                        }
                    }
                    /**
                     * adapter.notifyItemMoved(fromPosition, toPosition);
                     */
                    notifyItemMoved(clickPosition, newPosition);


                }
            });

        } else if (holder instanceof TitleRecyclerViewViewHolder) {
            TitleRecyclerViewViewHolder viewHolder = (TitleRecyclerViewViewHolder) holder;
            viewHolder.id_tv_item_title.setText(mItemTouchBeanList.get(position).getText());
            viewHolder.id_tv_item_title_more.setText(mItemTouchBeanList.get(position).getText2());

        }
    }

    @Override
    public int getItemCount() {
        return mItemTouchBeanList.size();
    }

    @Override
    public int getItemViewType(int position) {
        //return super.getItemViewType(position);
        return mItemTouchBeanList.get(position).getViewType();
    }

    public class ListRecyclerViewViewHolder extends RecyclerView.ViewHolder {

        private TextView id_tv_item_title;

        public ListRecyclerViewViewHolder(View itemView) {
            super(itemView);
            id_tv_item_title = (TextView) itemView.findViewById(R.id.id_tv_item_title);
        }


    }

    public class TitleRecyclerViewViewHolder extends RecyclerView.ViewHolder {

        private TextView id_tv_item_title;
        private TextView id_tv_item_title_more;

        public TitleRecyclerViewViewHolder(View itemView) {
            super(itemView);
            id_tv_item_title = (TextView) itemView.findViewById(R.id.id_tv_item_title);
            id_tv_item_title_more = (TextView) itemView.findViewById(R.id.id_tv_item_title_more);
        }


    }

    private ItemTouchHelper mItemTouchHelper;

    /**
     * 初始ItemTouchHelper 和RecyclerView关联
     *
     * @param recyclerView
     */
    private void initItemTouchHelper(RecyclerView recyclerView) {
        mItemTouchHelper = new ItemTouchHelper(new KooItemTouchHelperCallback());
        mItemTouchHelper.attachToRecyclerView(recyclerView);
    }

    /**
     * 定义继承ItemTouchHelper.Callback内部类
     */
    public class KooItemTouchHelperCallback extends ItemTouchHelper.Callback {
        /**
         * 用于设置是否处理拖拽事件和滑动事件，以及拖拽和滑动操作的方向
         *
         * @param recyclerView
         * @param viewHolder
         * @return
         */
        @Override
        public int getMovementFlags(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder) {
            //dragFlags 是拖拽标志，swipeFlags是滑动标志，把swipeFlags设置为0，表示不处理滑动操作。
            int dragFlags;
            int swipeFlags;
            if (recyclerView.getLayoutManager() instanceof GridLayoutManager) {
                /**
                 * grid
                 */
                dragFlags = ItemTouchHelper.UP | ItemTouchHelper.DOWN | ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT;//拖动
                swipeFlags = 0;//滑动
            } else {
                /**
                 *list
                 */
                dragFlags = ItemTouchHelper.UP | ItemTouchHelper.DOWN;//拖动支持向左和向右
                swipeFlags = ItemTouchHelper.START | ItemTouchHelper.END;//滑动支持向左和向右
            }
            return makeMovementFlags(dragFlags, swipeFlags);
            //return 0;
        }

        /**
         * 拖动回调
         *
         * @param recyclerView
         * @param viewHolder
         * @param target
         * @return
         */
        @Override
        public boolean onMove(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder, RecyclerView.ViewHolder target) {
            int fromPosition = viewHolder.getAdapterPosition();//得到拖动ViewHolder的position
            int toPosition = target.getAdapterPosition();//得到目标ViewHolder的position

            KLog.d("layoutPosition:" + viewHolder.getLayoutPosition());
            KLog.d("fromPosition:" + fromPosition);
            /**
             * 设置不可拖动
             */
            if (mCanNotDragPosList.contains(fromPosition) || mCanNotDragPosList.contains(toPosition)) {
                return false;
            }
         /*
       //交换方法1
       String prev = mItems.remove(fromPosition);
        mItems.add(toPosition > fromPosition ? toPosition - 1 : toPosition, prev);*/
        /*
       交换方法2
        Collections.swap(mItems, fromPosition, toPosition);
       */

            if (fromPosition < toPosition) {
                for (int i = fromPosition; i < toPosition; i++) {
                    Collections.swap(mItemTouchBeanList, i, i + 1);
                }
            } else {
                for (int i = fromPosition; i > toPosition; i--) {
                    Collections.swap(mItemTouchBeanList, i, i - 1);
                }
            }
            /**
             * adapter.notifyItemMoved(fromPosition, toPosition);
             */
            notifyItemMoved(fromPosition, toPosition);
            return true;
            //return false;
        }

        /**
         * 是否支持长按进入拖动
         * 要支持长按RecyclerView item进入拖动操作，你必须在isLongPressDragEnabled()方法中返回true。
         * 或者，也可以调用ItemTouchHelper.startDrag(RecyclerView.ViewHolder) 方法来开始一个拖动
         *
         * @return 默认返回是true
         */
        @Override
        public boolean isLongPressDragEnabled() {
            // return super.isLongPressDragEnabled();
            return false;
        }

        /**
         * 滑动回调
         *
         * @param viewHolder
         * @param direction
         */
        @Override
        public void onSwiped(RecyclerView.ViewHolder viewHolder, int direction) {

        }

        /**
         * 在每次View Holder的状态变成拖拽 (ACTION_STATE_DRAG) 或者 滑动 (ACTION_STATE_SWIPE)的时候被调用。
         *
         * @param viewHolder
         * @param actionState
         */
        @Override
        public void onSelectedChanged(RecyclerView.ViewHolder viewHolder, int actionState) {
            super.onSelectedChanged(viewHolder, actionState);
            //
            if (actionState != ItemTouchHelper.ACTION_STATE_IDLE) {
                viewHolder.itemView.setBackgroundColor(ContextCompat.getColor(viewHolder.itemView.getContext(),R.color.baseGridItemSelectedBg));
            }
        }

        //当手指松开的时候（拖拽完成的时候）调用
        @Override
        public void clearView(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder) {
            super.clearView(recyclerView, viewHolder);
            //
            viewHolder.itemView.setBackgroundColor(ContextCompat.getColor(viewHolder.itemView.getContext(),R.color.baseGridItemBg));
        }
    }

}
