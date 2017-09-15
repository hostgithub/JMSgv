package com.cn.gov.jms.ui;

import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;

import com.cn.gov.jms.Config;
import com.cn.gov.jms.adapter.SQGKAdapter;
import com.cn.gov.jms.base.BaseActivity;
import com.cn.gov.jms.model.Banners;
import com.cn.gov.jms.services.Api;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ShiQingGaiKuangActivity extends BaseActivity {

    @BindView(R.id.recyerview)
    RecyclerView mRecyclerView;
    private List<Banners.ResultsBean> resultsBeanList;
    private SQGKAdapter sqgkAdapter;
    LinearLayoutManager linearLayoutManager;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_shi_qing_gai_kuang;
    }

    @Override
    protected void initView() {
        resultsBeanList = new ArrayList<>();
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        mRecyclerView.setLayoutManager(linearLayoutManager);
        mRecyclerView.setHasFixedSize(true);
        initBannerData();
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
                Banners banners=response.body();
                Log.e("++++++++++",banners.success);
                resultsBeanList=banners.getResults();
                Log.e("++++++++++",resultsBeanList.get(0).get_id());
                Log.e("++++++++++",resultsBeanList.get(0).getTitle());
                Log.e("++++++++++",resultsBeanList.get(0).getUrl());
                Log.e("++++++++++",resultsBeanList.size()+"");

                sqgkAdapter = new SQGKAdapter(ShiQingGaiKuangActivity.this, resultsBeanList);
                mRecyclerView.setAdapter(sqgkAdapter);
            }

            @Override
            public void onFailure(Call<Banners> call, Throwable t) {

            }
        });
    }
}
