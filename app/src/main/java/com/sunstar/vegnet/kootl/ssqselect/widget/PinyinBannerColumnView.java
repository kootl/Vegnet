package com.sunstar.vegnet.kootl.ssqselect.widget;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Typeface;
import android.support.v4.content.ContextCompat;
import android.util.AttributeSet;
import android.util.Log;
import android.util.TypedValue;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewConfiguration;
import android.widget.TextView;

import com.sunstar.vegnet.R;
import com.sunstar.vegnet.kootl.ssqselect.bean.AreaSelectBean;

import java.util.List;

/**
 * Created by louisgeek on 2016/8/14.
 */
public class PinyinBannerColumnView extends View {
    private static final String TAG = "PinyinBannerColumnView";
    private Context mContext;
    private Paint mPaint;
    private int width = 80;
    private int height = 1000;
    private int textHeight;
    private int textSpacing = dp2px(5);
    private int choosePos = -1;
    private final int DEFAULT_BACKGROUNDCOLOR = 0x55555555;
    private final int DEFAULT_TEXTCOLOR = 0xFFffffff;
    private int backgroundColor = DEFAULT_BACKGROUNDCOLOR;
    private int textColor = DEFAULT_TEXTCOLOR;
    private boolean isSliding = false;
    private int yDown, yMove, ScaledTouchSlop;
    private String sectionStr = "#ABCDEFGHIJKLMNOPQRSTUVWXYZ";
    private TextView mIndicatorCenterView;


    public PinyinBannerColumnView(Context context) {
        this(context, null, 0);
    }

    public PinyinBannerColumnView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public PinyinBannerColumnView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        mContext = context;
        initView();
    }

    private void initView() {
        ScaledTouchSlop = ViewConfiguration.get(mContext).getScaledTouchSlop();
        mPaint = new Paint();

        mPaint.setAntiAlias(true);
        mPaint.setTextSize(40);//提供测量
        mPaint.setTypeface(Typeface.DEFAULT_BOLD);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        //当xml未设置固定值时候需要显示的宽度和高度
        int textWidth = (int) mPaint.measureText("A");
        textHeight = (int) Math.abs(mPaint.descent() + mPaint.ascent());
        Log.d(TAG, "onMeasure:textWidth: " + textWidth);
        Log.d(TAG, "onMeasure:textHeight: " + textHeight);
        int w_my_when_no_size_configed = textWidth + this.getPaddingLeft() + this.getPaddingRight();
        int h_my_when_no_size_configed = textHeight * sectionStr.length() + textSpacing * (sectionStr.length() - 1) + this.getPaddingTop() + this.getPaddingBottom();
        int w = resolveSize(w_my_when_no_size_configed, widthMeasureSpec);
        int h = resolveSize(h_my_when_no_size_configed, heightMeasureSpec);
        setMeasuredDimension(w, h);
        width = w;
        height = h;
    }

    public void setupIndicatorCenterView(TextView indicatorCenterView) {
        mIndicatorCenterView = indicatorCenterView;
    }


    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        // width=getWidth();
        //   height=getHeight();
        mPaint.setColor(backgroundColor);
        canvas.drawRect(0, 0, width, height, mPaint);

        mPaint.setColor(textColor);

        for (int i = 0; i < sectionStr.length(); i++) {
            // 选中的状态
            if (i == choosePos) {
                mPaint.setColor(ContextCompat.getColor(mContext, R.color.colorAccent));
                // mPaint.setFakeBoldText(true);
            } else {
                mPaint.setColor(textColor);
            }
            int textWidth = (int) mPaint.measureText(sectionStr.charAt(i) + "");
            //依托测量后的height来设定文本
            // int y=this.getPaddingTop()+(textHeight)*(i+1)+textSpacing*i;
            int newTextSpacing = (height - this.getPaddingTop() - this.getPaddingBottom() - sectionStr.length() * textHeight) / (sectionStr.length() - 1);
            canvas.drawText(sectionStr.charAt(i) + "", (width - textWidth) / 2, this.getPaddingTop() + textHeight * (i + 1) + newTextSpacing * i, mPaint);
        }

    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent event) {
        int action = event.getAction();
        int y = (int) event.getY();// 点击y坐标
        switch (action) {
            case MotionEvent.ACTION_DOWN:
                yDown = y;
                Log.d(TAG, "dispatchTouchEvent: ACTION_DOWN");
                break;
            case MotionEvent.ACTION_MOVE:
                yMove = y;
                int dy = yMove - yDown;
                Log.d(TAG, "dispatchTouchEvent: ACTION_MOVE");
                //如果是竖直方向滑动
                if (Math.abs(dy) > ScaledTouchSlop) {
                    isSliding = true;
                }
                break;
            case MotionEvent.ACTION_UP:
                Log.d(TAG, "dispatchTouchEvent: ACTION_UP");
                isSliding = false;
                break;
        }
        return super.dispatchTouchEvent(event);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        int action = event.getAction();
        int y = (int) event.getY();
        switch (action) {
            case MotionEvent.ACTION_DOWN:
                Log.d(TAG, "onTouchEvent: ACTION_DOWN");
                backgroundColor = 0xFF676767;
                choosePos = y / (height / sectionStr.length());
                choosePos = choosePos > sectionStr.length() - 1 ? sectionStr.length() - 1 : choosePos;
                invalidate();
                showIndicatorCenterView();
                onSlidedListener.onSlided(sectionStr.charAt(choosePos) + "");
                break;
            case MotionEvent.ACTION_MOVE:
                Log.d(TAG, "onTouchEvent: ACTION_MOVE isSliding:" + isSliding);
                if (isSliding) {
                    backgroundColor = 0xFF676767;
                    choosePos = y / (height / sectionStr.length());
                    choosePos = choosePos > sectionStr.length() - 1 ? sectionStr.length() - 1 : choosePos;
                    invalidate();
                    showIndicatorCenterView();
                    onSlidedListener.onSlided(sectionStr.charAt(choosePos) + "");
                }
                break;
            case MotionEvent.ACTION_UP:
                Log.d(TAG, "onTouchEvent: ACTION_UP");
                backgroundColor = DEFAULT_BACKGROUNDCOLOR;
               /* choosePos= y / (height / sectionStr.length());
                choosePos=choosePos>sectionStr.length()-1?sectionStr.length()-1:choosePos;*/
                choosePos = -1;
                invalidate();
                /*if (isSliding){
                    showTextBox();
                }else{*/
                hideIndicatorCenterView();
                //}
                break;
            default:
                break;
        }
        return true;//消费掉事件
        //return super.onTouchEvent(event);
    }

   /* private int getPositionByPinyinFirst(String pinyinFirst) {
    }*/

    private void showIndicatorCenterView() {
        if (mIndicatorCenterView != null) {
            //   textShowBox.clearAnimation();
            mIndicatorCenterView.setText(sectionStr.charAt(choosePos) + "");
            mIndicatorCenterView.setVisibility(View.VISIBLE);
                   /* ObjectAnimator anim=ObjectAnimator.ofFloat(textShowBox,"Alpha",0f,100f);
                    anim.setDuration(3000);
                    anim.start();*/
        }
    }

    private void hideIndicatorCenterView() {
        if (mIndicatorCenterView != null) {
            mIndicatorCenterView.setVisibility(GONE);
           /* textShowBox.clearAnimation();
            ObjectAnimator anim=ObjectAnimator.ofFloat(textShowBox,"Alpha",100f,0f);
            anim.setDuration(300);
            anim.addListener(new AnimatorListenerAdapter() {
                @Override
                public void onAnimationEnd(Animator animation) {
                    super.onAnimationEnd(animation);
                    textShowBox.setVisibility(View.GONE);
                }
            });
            anim.start();*/
        }
    }

    public int dp2px(int dpValue) {
        int px = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dpValue, getResources().getDisplayMetrics());
        return px;
    }

    public void setSelectAreaBeanList(List<AreaSelectBean> selectAreaBeanList) {
        // StringBuilder stringBuilder=new StringBuilder();
        sectionStr = "";
        if (selectAreaBeanList != null && selectAreaBeanList.size() > 0) {
            for (int i = 0; i < selectAreaBeanList.size(); i++) {
                String firstPinyin = selectAreaBeanList.get(i).getPinyin().charAt(0) + "";
                if (!sectionStr.contains(firstPinyin)) {
                    sectionStr += firstPinyin;
                }
            }
        }

    }

    public interface OnSlidedListener {
        void onSlided(String pinyinFirst);
    }

    public void setOnSlidedListener(OnSlidedListener onSlidedListener) {
        this.onSlidedListener = onSlidedListener;
    }

    public OnSlidedListener onSlidedListener;
}
