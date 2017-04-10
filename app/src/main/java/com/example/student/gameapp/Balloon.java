package com.example.student.gameapp;

import android.animation.Animator;
import android.animation.ValueAnimator;
import android.content.Context;
import android.view.MotionEvent;
import android.view.ViewGroup;
import android.view.animation.LinearInterpolator;
import android.widget.ImageView;

import com.example.student.gameapp.utils.PixelHelper;

/**
 * Created by Student on 4/9/2017.
 */

public class Balloon extends android.support.v7.widget.AppCompatImageView implements Animator.AnimatorListener, ValueAnimator.AnimatorUpdateListener {

    private ValueAnimator mAnimator;
    private BalloonListener mListener;
    private boolean mPopped;

    public Balloon(Context context) {
        super(context);
    }

    public Balloon(Context context, int color, int rawSize) {
        super(context);

        mListener = (BalloonListener) context;

        this.setImageResource(R.drawable.ball);
        this.setColorFilter(color);

        int dpHeight = PixelHelper.pixelsToDp(rawSize, context);
        int dpWidth = PixelHelper.pixelsToDp(rawSize, context);
        ViewGroup.LayoutParams params = new ViewGroup.LayoutParams(dpWidth, dpHeight);
        setLayoutParams(params);
    }

    public void releaseBalloon(int screenHeight, int duration){
        mAnimator = new ValueAnimator();
        mAnimator.setDuration(duration);
        mAnimator.setFloatValues(screenHeight, 0f);
        mAnimator.setInterpolator(new LinearInterpolator());
        mAnimator.setTarget(this);
        mAnimator.addListener(this);
        mAnimator.addUpdateListener(this);
        mAnimator.start();
    }

    @Override
    public void onAnimationStart(Animator animation) {

    }

    @Override
    public void onAnimationEnd(Animator animation) {
        if (!mPopped) {
            mListener.popBalloon(this, false);
        }
    }

    @Override
    public void onAnimationCancel(Animator animation) {

    }

    @Override
    public void onAnimationRepeat(Animator animation) {

    }

    @Override
    public void onAnimationUpdate(ValueAnimator animation) {
        setY((Float) animation.getAnimatedValue());
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        if (!mPopped && event.getAction() == MotionEvent.ACTION_DOWN) {
            mListener.popBalloon(this, true);
            mPopped = true;
            mAnimator.cancel();
        }
        return super.onTouchEvent(event);
    }

    public void setPopped(boolean popped) {
        mPopped = popped;
        if(popped){
            mAnimator.cancel();
        }
    }

    public interface BalloonListener{
        void popBalloon(Balloon balloon, boolean userTouch);
    }
}
