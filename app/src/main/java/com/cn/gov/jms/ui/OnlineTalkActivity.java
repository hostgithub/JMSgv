package com.cn.gov.jms.ui;

import android.view.View;

import com.bumptech.glide.Glide;
import com.cn.gov.jms.base.BaseActivity;

import butterknife.BindView;
import butterknife.OnClick;
import fm.jiecao.jcvideoplayer_lib.JCVideoPlayer;
import fm.jiecao.jcvideoplayer_lib.JCVideoPlayerStandard;

public class OnlineTalkActivity extends BaseActivity {

    @BindView(R.id.videoplayer)
    JCVideoPlayerStandard mJcVideoPlayerStandard;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_online_talk;
    }

    @Override
    protected void initView() {
        mJcVideoPlayerStandard.setUp("http://www.jmzsjy.com/UploadFile/微课/地方风味小吃——宫廷香酥牛肉饼.mp4"
                , JCVideoPlayerStandard.SCREEN_LAYOUT_NORMAL, "视频播放");
        Glide.with(this)
                .load("http://img4.jiecaojingxuan.com/2016/11/23/00b026e7-b830-4994-bc87-38f4033806a6.jpg@!640_360")
                .into(mJcVideoPlayerStandard.thumbImageView);

    }

    @Override
    public void onBackPressed() {
        if (JCVideoPlayer.backPress()) {
            return;
        }
        super.onBackPressed();
    }
    @Override
    protected void onPause() {
        super.onPause();
        JCVideoPlayer.releaseAllVideos();
    }

    @OnClick({ R.id.iv_back})
    public void onClick(View view) {
        finish();
    }
}
