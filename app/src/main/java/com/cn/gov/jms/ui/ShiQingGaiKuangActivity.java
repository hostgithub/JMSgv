package com.cn.gov.jms.ui;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import com.cn.gov.jms.Config;
import com.cn.gov.jms.adapter.SQGKAdapter;
import com.cn.gov.jms.base.BaseActivity;
import com.cn.gov.jms.model.Banners;
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

public class ShiQingGaiKuangActivity extends BaseActivity {

    @BindView(R.id.iv_back)
    ImageView imageView;

    @BindView(R.id.recyerview)
    RecyclerView mRecyclerView;
    private List<Banners.ResultsBean> resultsBeanList;
    private SQGKAdapter sqgkAdapter;
    LinearLayoutManager linearLayoutManager;
    private boolean connect = false;//判断网络是否连接正常

    @Override
    protected int getLayoutId() {
        checkNet();
        return R.layout.activity_shi_qing_gai_kuang;
    }

    @Override
    protected void initView() {
        resultsBeanList = new ArrayList<>();
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
        if(connect){
            initBannerData();
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
    private void initBannerData() {
        //使用retrofit配置api
        Retrofit retrofit=new Retrofit.Builder()
                .baseUrl(Config.BANNER_BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        Api api =retrofit.create(Api.class);
        Call<Banners> call=api.getBannerData();
        call.enqueue(new Callback<Banners>() {
            @Override
            public void onResponse(Call<Banners> call, Response<Banners> response) {
                if(response!=null){
                    Banners banners=response.body();
                    Log.e("++++++++++",banners.success);
                    resultsBeanList=banners.getResults();
                    Log.e("++++++++++",resultsBeanList.get(0).get_id());
                    Log.e("++++++++++",resultsBeanList.get(0).getTitle());
                    Log.e("++++++++++",resultsBeanList.get(0).getUrl());
                    Log.e("++++++++++",resultsBeanList.size()+"");

                    sqgkAdapter = new SQGKAdapter(ShiQingGaiKuangActivity.this, resultsBeanList);
                    mRecyclerView.setAdapter(sqgkAdapter);
                }else{
                    Toast.makeText(ShiQingGaiKuangActivity.this,"数据为空!",Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<Banners> call, Throwable t) {
                Toast.makeText(ShiQingGaiKuangActivity.this,"请求失败!",Toast.LENGTH_SHORT).show();
            }
        });
    }
    @OnClick({ R.id.iv_back})
    public void onClick(View view) {
        finish();
    }
}
