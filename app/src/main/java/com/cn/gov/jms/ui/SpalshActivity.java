package com.cn.gov.jms.ui;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.widget.ImageView;

import butterknife.BindView;
import butterknife.ButterKnife;

public class SpalshActivity extends AppCompatActivity
{

    @BindView(R.id.loading_imageCenter)
    ImageView mLoadingImageCenter;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_loading);
        ButterKnife.bind(this);

        initViews();
    }

    private void initViews()
    {
        AlphaAnimation animation = new AlphaAnimation(1.0f, 1.0f);
        animation.setDuration(2000);
        mLoadingImageCenter.setAnimation(animation);
        animation.setAnimationListener(new Animation.AnimationListener()
        {
            @Override
            public void onAnimationStart(Animation animation)
            {

            }

            @Override
            public void onAnimationEnd(Animation animation)
            {
                Intent it = new Intent(SpalshActivity.this, MainActivity.class);
                startActivity(it);
                SpalshActivity.this.finish();
            }

            @Override
            public void onAnimationRepeat(Animation animation)
            {

            }
        });
        animation.start();
    }
}
