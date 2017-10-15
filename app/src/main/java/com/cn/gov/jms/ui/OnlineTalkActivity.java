package com.cn.gov.jms.ui;

import android.content.Intent;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.cn.gov.jms.Config;
import com.cn.gov.jms.adapter.OnlineTalkDetailAdapter;
import com.cn.gov.jms.base.BaseActivity;
import com.cn.gov.jms.model.OnlineTalkDetail;
import com.cn.gov.jms.model.Video;
import com.cn.gov.jms.services.Api;
import com.cn.gov.jms.utils.FullyLinearLayoutManager;
import com.cn.gov.jms.utils.RecyclerViewSpacesItemDecoration;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;
import fm.jiecao.jcvideoplayer_lib.JCVideoPlayer;
import fm.jiecao.jcvideoplayer_lib.JCVideoPlayerStandard;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class OnlineTalkActivity extends BaseActivity {

    @BindView(R.id.videoplayer)
    JCVideoPlayerStandard mJcVideoPlayerStandard;
    private String picUrl;
    @BindView(R.id.imageview)
    ImageView imageview;
    @BindView(R.id.tv_dept)
    TextView tv_dept;
    @BindView(R.id.tv_theme)
    TextView tv_theme;
    @BindView(R.id.tv_jiabin)
    TextView tv_jiabin;
    @BindView(R.id.tv_zhuchiren)
    TextView tv_zhuchiren;
    @BindView(R.id.tv_time)
    TextView tv_time;

    @BindView(R.id.recyerview)
    RecyclerView mRecyclerView;
    private List<OnlineTalkDetail.HostBean> hostBeanList;
    private List<OnlineTalkDetail.ResultsBean> resultsBeanList;
    private OnlineTalkDetailAdapter onlineTalkDetailAdapter;
    FullyLinearLayoutManager linearLayoutManager;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_online_talk;
    }

    @Override
    protected void initView() {

        Intent intent=getIntent();
        hostBeanList = new ArrayList<>();
        resultsBeanList = new ArrayList<>();
        linearLayoutManager=new FullyLinearLayoutManager(this);
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        mRecyclerView.setLayoutManager(linearLayoutManager);
        mRecyclerView.setHasFixedSize(true);
        HashMap<String, Integer> stringIntegerHashMap = new HashMap<>();
        stringIntegerHashMap.put(RecyclerViewSpacesItemDecoration.TOP_DECORATION,10);//top间距
        stringIntegerHashMap.put(RecyclerViewSpacesItemDecoration.BOTTOM_DECORATION,10);//底部间距
        //stringIntegerHashMap.put(RecyclerViewSpacesItemDecoration.LEFT_DECORATION,10);//左间距
        //stringIntegerHashMap.put(RecyclerViewSpacesItemDecoration.RIGHT_DECORATION,10);//右间距
        mRecyclerView.addItemDecoration(new RecyclerViewSpacesItemDecoration(stringIntegerHashMap));

        if(intent!=null){
            hostBeanList= (List<OnlineTalkDetail.HostBean>) intent.getSerializableExtra(Config.LIST_HOST);
            Log.e("----host------",hostBeanList.toString());
            onlineTalkDetailAdapter = new OnlineTalkDetailAdapter(OnlineTalkActivity.this,hostBeanList);
            mRecyclerView.setAdapter(onlineTalkDetailAdapter);
            resultsBeanList= (List<OnlineTalkDetail.ResultsBean>) intent.getSerializableExtra(Config.RESULT_BEAN);

            Glide.with(OnlineTalkActivity.this)
                    .load("http://221.210.9.87:8080/"+resultsBeanList.get(0).getPicName())
                    .into(imageview);
            mJcVideoPlayerStandard.setUp("http://221.210.9.87:8080/"+resultsBeanList.get(0).getVideoName()
                            , JCVideoPlayerStandard.SCREEN_LAYOUT_NORMAL, "视频播放");

//            mJcVideoPlayerStandard.setUp("http://221.210.9.87:8080/data/interview/video/1435194398872.mp4"
//                    , JCVideoPlayerStandard.SCREEN_LAYOUT_NORMAL, "视频播放");
            Log.e("------------",resultsBeanList.get(0).getVideoName());
            tv_dept.setText(resultsBeanList.get(0).getDeptName());
            tv_theme.setText(resultsBeanList.get(0).getTitle());
            tv_jiabin.setText(resultsBeanList.get(0).getQuest());
            tv_zhuchiren.setText(resultsBeanList.get(0).getCompere());
            tv_time.setText(resultsBeanList.get(0).getSubjectTime());
        }
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

    private void getData(int id){
        //使用retrofit配置api
        Retrofit retrofit=new Retrofit.Builder()
                .baseUrl(Config.BANNER_BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        Api api =retrofit.create(Api.class);
        Call<Video> call=api.getInterviewData(id,"1_1502681781008");
        call.enqueue(new Callback<Video>() {
            @Override
            public void onResponse(Call<Video> call, Response<Video> response) {
                if(response!=null){
                    Video video=response.body();
                    picUrl=video.getBasePath()+video.getInterview().getLogo();
                    Log.e("-----picUrl-----",picUrl);
                    Log.e("----vodeoUrl---",video.getInterview().getVideoUrl());

                    Glide.with(OnlineTalkActivity.this)
                            .load(picUrl)
                            .into(imageview);
//                    mJcVideoPlayerStandard.setUp("http://www.jms.gov.cn/data/interview/0814.flv"
//                            , JCVideoPlayerStandard.SCREEN_LAYOUT_NORMAL, "视频播放");
                    mJcVideoPlayerStandard.setUp("http://www.jms.gov.cn/"+video.getInterview().getVideoName()
                            , JCVideoPlayerStandard.SCREEN_LAYOUT_NORMAL, "视频播放");
                    tv_dept.setText(video.getInterview().getDeptName());
                    tv_theme.setText(video.getInterview().getSubject());
                    tv_jiabin.setText(video.getInterview().getQuest());
                    tv_zhuchiren.setText(video.getInterview().getCompere());
                    tv_time.setText(video.getInterview().getSubjectTime());

                }else{
                    Toast.makeText(OnlineTalkActivity.this,"数据为空!",Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<Video> call, Throwable t) {
                Toast.makeText(OnlineTalkActivity.this,"请求失败!",Toast.LENGTH_SHORT).show();
                Log.e("-------------",t.getMessage().toString());
            }
        });
    }

    @OnClick({ R.id.iv_back})
    public void onClick(View view) {
        finish();
    }
}
