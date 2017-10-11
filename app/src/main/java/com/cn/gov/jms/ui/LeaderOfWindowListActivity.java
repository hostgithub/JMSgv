package com.cn.gov.jms.ui;

import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.cn.gov.jms.Config;
import com.cn.gov.jms.adapter.LeaderOfWindowAdapter;
import com.cn.gov.jms.base.BaseActivity;
import com.cn.gov.jms.model.AboutAndContact;
import com.cn.gov.jms.model.SqgkDetail;
import com.cn.gov.jms.services.Api;
import com.cn.gov.jms.utils.RecyclerViewSpacesItemDecoration;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class LeaderOfWindowListActivity extends BaseActivity {

    @BindView(R.id.tv_title)
    TextView tv_title;
    @BindView(R.id.recyerview)
    RecyclerView mRecyclerView;
    private List<AboutAndContact.ResultsBean> list;
    private LeaderOfWindowAdapter leaderOfWindowAdapter;
    LinearLayoutManager linearLayoutManager;
    private boolean connect = false;//判断网络是否连接正常

    @Override
    protected int getLayoutId() {

        return R.layout.activity_shi_qing_gai_kuang;
    }

    @Override
    protected void initView() {

        tv_title.setText("领导之窗");
        list = new ArrayList<>();
        checkNet();
        linearLayoutManager=new LinearLayoutManager(this);
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        mRecyclerView.setLayoutManager(linearLayoutManager);
        mRecyclerView.setHasFixedSize(true);
        HashMap<String, Integer> stringIntegerHashMap = new HashMap<>();
        stringIntegerHashMap.put(RecyclerViewSpacesItemDecoration.TOP_DECORATION,10);//top间距
        stringIntegerHashMap.put(RecyclerViewSpacesItemDecoration.BOTTOM_DECORATION,10);//底部间距
        //stringIntegerHashMap.put(RecyclerViewSpacesItemDecoration.LEFT_DECORATION,10);//左间距
        //stringIntegerHashMap.put(RecyclerViewSpacesItemDecoration.RIGHT_DECORATION,10);//右间距
        mRecyclerView.addItemDecoration(new RecyclerViewSpacesItemDecoration(stringIntegerHashMap));

//        Gson gson=new Gson();
//        LocalJsonFile localJsonFile=gson.fromJson(getJson(this),LocalJsonFile.class);
//        Log.e("+++++++++++",localJsonFile.getDynamic().get(0).getContent());
//        sqgkAdapter = new SQGKAdapter(ShiQingGaiKuangActivity.this, localJsonFile.getDynamic()); //加载本地json
//        mRecyclerView.setAdapter(sqgkAdapter);

        if(connect){
            initBannerData(getIntent().getStringExtra(Config.NEWS));
        }else{
            Toast.makeText(this,"您还没有连接网络!",Toast.LENGTH_SHORT).show();
        }
    }

    public void checkNet()//网络是否已经连接上
    {
        ConnectivityManager manager = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo info = manager.getActiveNetworkInfo();
        connect = info != null && info.isConnected();
    }
    /**
     *初始化 Banner数据
     */
    private void initBannerData(String id) {
        //使用retrofit配置api
        Retrofit retrofit=new Retrofit.Builder()
                .baseUrl(Config.BANNER_BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        Api api =retrofit.create(Api.class);
        Call<AboutAndContact> call=api.getLeaderOfWindowListData(id);
        call.enqueue(new Callback<AboutAndContact>() {
            @Override
            public void onResponse(Call<AboutAndContact> call, Response<AboutAndContact> response) {
                if(response!=null){
                    AboutAndContact banners=response.body();
                    list=banners.getResults();
                    //sqgkAdapter = new SQGKAdapter(ShiQingGaiKuangActivity.this, resultsBeanList);
                    leaderOfWindowAdapter = new LeaderOfWindowAdapter(LeaderOfWindowListActivity.this, list);
                    mRecyclerView.setAdapter(leaderOfWindowAdapter);

                    leaderOfWindowAdapter.setOnItemClickLitener(new LeaderOfWindowAdapter.OnItemClickListener() {
                        @Override
                        public void onItemClick(int position) {
                            getData(Integer.parseInt(list.get(position).id));
                        }
                    });
                }else{
                    Toast.makeText(LeaderOfWindowListActivity.this,"数据为空!",Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<AboutAndContact> call, Throwable t) {
                Toast.makeText(LeaderOfWindowListActivity.this,"请求失败!",Toast.LENGTH_SHORT).show();
            }
        });
    }


    private void getData(int id){
        //使用retrofit配置api
        Retrofit retrofit=new Retrofit.Builder()
                .baseUrl(Config.BANNER_BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        Api api =retrofit.create(Api.class);
        Call<SqgkDetail> call=api.getSqgkDetailData(id);
        call.enqueue(new Callback<SqgkDetail>() {
            @Override
            public void onResponse(Call<SqgkDetail> call, Response<SqgkDetail> response) {
                if(response!=null){
                    SqgkDetail detail=response.body();
                    SqgkDetail.ResultsBean resultsBean=detail.getResults().get(0);
                    Intent intent = new Intent(LeaderOfWindowListActivity.this,LeaderInfoDetailActivity.class);
                    intent.putExtra(Config.NEWS,resultsBean);
                    startActivity(intent);
                    Log.e("xxxxxxx",resultsBean.content);
                }else{
                    Toast.makeText(LeaderOfWindowListActivity.this,"数据为空!",Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<SqgkDetail> call, Throwable t) {
                Toast.makeText(LeaderOfWindowListActivity.this,"请求失败!",Toast.LENGTH_SHORT).show();
                Log.e("-------------",t.getMessage().toString());
            }
        });
    }
    @OnClick({ R.id.iv_back})
    public void onClick(View view) {
        finish();
    }
}
