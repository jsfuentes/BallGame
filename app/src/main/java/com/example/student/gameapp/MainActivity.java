package com.example.student.gameapp;

import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;

public class MainActivity extends AppCompatActivity {

    private int[] mBallonColors = new int[3];
    private int mNextColor, mScreenWidth, mScreenHeight;

    private ViewGroup mContentView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mBallonColors[0] = Color.argb(255, 255, 0, 0);
        mBallonColors[1] = Color.argb(255, 0, 255, 0);
        mBallonColors[2] = Color.argb(255, 0, 0, 255);

        getWindow().setBackgroundDrawableResource(R.drawable.sg);

        mContentView = (ViewGroup) findViewById(R.id.activity_main);
        setToFullScreen();

        ViewTreeObserver viewTreeObserver = mContentView.getViewTreeObserver();
        if (viewTreeObserver.isAlive()) {
            viewTreeObserver.addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
                @Override
                public void onGlobalLayout() {
                    mContentView.getViewTreeObserver().removeOnGlobalLayoutListener(this);
                    mScreenWidth = mContentView.getWidth();
                    mScreenHeight = mContentView.getHeight();
                }
            });
        }

        mContentView.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View view){
                setToFullScreen();
            }
        });

        mContentView.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if (event.getAction() == MotionEvent.ACTION_UP) {
                    Ballon b = new Ballon(MainActivity.this, mBallonColors[mNextColor], 100);
                    b.setX(event.getX());
                    b.setY(mScreenHeight);
                    mContentView.addView(b);
                    b.releaseBallon(mScreenHeight, 3000);

                    if (mNextColor + 1 == mBallonColors.length) {
                        mNextColor = 0;
                    } else {
                        mNextColor += 1;
                    }
                }
                return false;
            }
        });
    }

    private void setToFullScreen(){
        ViewGroup rootLayout = (ViewGroup) findViewById(R.id.activity_main);
        rootLayout.setSystemUiVisibility(View.SYSTEM_UI_FLAG_LOW_PROFILE
                | View.SYSTEM_UI_FLAG_FULLSCREEN
                | View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                | View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY
                | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION);
    }

    @Override
    protected void onResume() {
        super.onResume();
        setToFullScreen();
    }
}
