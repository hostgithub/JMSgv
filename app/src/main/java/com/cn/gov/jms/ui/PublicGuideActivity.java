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
import com.cn.gov.jms.adapter.PublicGuideBeanAdapter;
import com.cn.gov.jms.base.BaseActivity;
import com.cn.gov.jms.model.PublicGuideBean;
import com.cn.gov.jms.model.PublicGuideDetailBean;
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

public class PublicGuideActivity extends BaseActivity {

    @BindView(R.id.tv_title)
    TextView tv_title;
    @BindView(R.id.recyerview)
    RecyclerView mRecyclerView;
    private List<PublicGuideBean.ResultsBean> list;
    private PublicGuideBeanAdapter publicGuideBeanAdapter;
    LinearLayoutManager linearLayoutManager;
    private boolean connect = false;//判断网络是否连接正常

    @Override
    protected int getLayoutId() {

        return R.layout.activity_shi_qing_gai_kuang;
    }

    @Override
    protected void initView() {

        tv_title.setText("政府信息公开指南");
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
        Call<PublicGuideBean> call=api.getPublicGuideData();
        call.enqueue(new Callback<PublicGuideBean>() {
            @Override
            public void onResponse(Call<PublicGuideBean> call, Response<PublicGuideBean> response) {
                if(response!=null){
                    PublicGuideBean banners=response.body();
                    list=banners.getResults();
                    //sqgkAdapter = new SQGKAdapter(ShiQingGaiKuangActivity.this, resultsBeanList);
                    publicGuideBeanAdapter = new PublicGuideBeanAdapter(PublicGuideActivity.this, list);
                    mRecyclerView.setAdapter(publicGuideBeanAdapter);

                    publicGuideBeanAdapter.setOnItemClickLitener(new PublicGuideBeanAdapter.OnItemClickListener() {
                        @Override
                        public void onItemClick(int position) {
                            getData(Integer.parseInt(list.get(position).source));
                        }
                    });
                }else{
                    Toast.makeText(PublicGuideActivity.this,"数据为空!",Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<PublicGuideBean> call, Throwable t) {
                Toast.makeText(PublicGuideActivity.this,"请求失败!",Toast.LENGTH_SHORT).show();
            }
        });
    }


    private void getData(int source){        //json格式有问题 解析失败
        //使用retrofit配置api
        Retrofit retrofit=new Retrofit.Builder()
                .baseUrl(Config.BANNER_BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        Api api =retrofit.create(Api.class);
        Call<PublicGuideDetailBean> call=api.getGuideBySourceData(source);
        call.enqueue(new Callback<PublicGuideDetailBean>() {
            @Override
            public void onResponse(Call<PublicGuideDetailBean> call, Response<PublicGuideDetailBean> response) {
                if(response!=null){
                    PublicGuideDetailBean detail=response.body();
                    PublicGuideDetailBean.ResultsBean resultsBean=detail.getResults().get(0);
                    Intent intent = new Intent(PublicGuideActivity.this,PublicGuideDetailActivity.class);
                    intent.putExtra(Config.NEWS,resultsBean);
                    startActivity(intent);
                    Log.e("xxxxxxx",resultsBean.content);
                }else{
                    Toast.makeText(PublicGuideActivity.this,"数据为空!",Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<PublicGuideDetailBean> call, Throwable t) {
                Toast.makeText(PublicGuideActivity.this,"请求失败!",Toast.LENGTH_SHORT).show();
                Log.e("-------------",t.getMessage().toString());
            }
        });
    }
    @OnClick({ R.id.iv_back})
    public void onClick(View view) {
        finish();
    }
}
