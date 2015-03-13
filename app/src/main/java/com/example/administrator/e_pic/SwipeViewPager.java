package com.example.administrator.e_pic;

import android.content.Context;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.widget.Button;

/**
 * Created by Administrator on 12/03/2015.
 */
public class SwipeViewPager extends ViewPager {

    private float mStartDragX;
    private OnSwipeOutListener mListener;
    private Button addSneezeButton;
    private Context context;
    private iSneezeFragment iSneezeFragment;

    public SwipeViewPager(Context context) {
        super(context);
        this.context = context;

    }


    public SwipeViewPager(Context context, AttributeSet attrs) {
        super(context, attrs);
    }


    public void setFragment(iSneezeFragment is) {
        this.iSneezeFragment = is;
    }
    public SwipeViewPager(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs);
    }

    public void setOnSwipeOutListener(OnSwipeOutListener listener) {
        mListener = listener;
    }





    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
        float x = ev.getX();
        switch (ev.getAction()) {
            case MotionEvent.ACTION_DOWN:
                mStartDragX = x;
                System.out.println("ahzo zit het dus");
                break;
            case MotionEvent.ACTION_MOVE:
                if (mStartDragX < x  && getCurrentItem() == 1) {
                    //iSneezeFragment.setButtonLocation(x-mStartDragX);
                    System.out.println("wadde? hahaha");
                } else if (mStartDragX > x && getCurrentItem() == 1) {//&& getCurrentItem() == getAdapter().getCount() - 1) {
                    //iSneezeFragment.setButtonLocation(mStartDragX-x);
                    System.out.println("wadddsqfjklmsjdqfje? hahaha");
                } else {
                    //addSneezeButton.setY(addSneezeButton.getY() + (mStartDragX - getCurrentItem()));
                    System.out.println("aaaaaaah");
                }

                break;
            case MotionEvent.ACTION_CANCEL:
                //iSneezeFragment.resetButtonLocation();
                break;
            case MotionEvent.ACTION_SCROLL:
                System.out.println("scrollingzzz");
                break;
            case MotionEvent.ACTION_UP:
                //iSneezeFragment.resetButtonLocation();
        }
        return super.onInterceptTouchEvent(ev);
    }

    public interface OnSwipeOutListener {
        public void onSwipeOutAtStart();
        public void onSwipeOutAtEnd();
    }
}
