package com.cn.gov.jms.ui;

import android.content.Intent;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.cn.gov.jms.Config;
import com.cn.gov.jms.adapter.ApplyPublicAdapter;
import com.cn.gov.jms.base.BaseActivity;
import com.cn.gov.jms.base.EndLessOnScrollListener;
import com.cn.gov.jms.model.ApplyPublic;
import com.cn.gov.jms.model.ApplyPublicDetail;
import com.cn.gov.jms.services.Api;
import com.cn.gov.jms.utils.RecyclerViewSpacesItemDecoration;

import java.util.ArrayList;
import java.util.HashMap;

import butterknife.BindView;
import butterknife.OnClick;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ApplyPublicActivity extends BaseActivity implements SwipeRefreshLayout.OnRefreshListener {

    //照片墙
    @BindView(R.id.refreshLayout)
    SwipeRefreshLayout refreshLayout;
    @BindView(R.id.recyerview)
    RecyclerView mRecyclerView;
    private ArrayList<ApplyPublic.ResultsBean> list;
    private ApplyPublicAdapter picAdapter;
    private LinearLayoutManager linearLayoutManager;
    private int pages=1;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_apply_public;
    }

    @Override
    protected void initView() {

        //图文
        refreshLayout.setOnRefreshListener(this);
        list=new ArrayList();
        initNewsData(1);
        linearLayoutManager=new LinearLayoutManager(this);
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        mRecyclerView.setLayoutManager(linearLayoutManager);
        mRecyclerView.setHasFixedSize(true);
        mRecyclerView.setNestedScrollingEnabled(false);

        HashMap<String, Integer> stringIntegerHashMap = new HashMap<>();
        stringIntegerHashMap.put(RecyclerViewSpacesItemDecoration.TOP_DECORATION,1);//top间距

        stringIntegerHashMap.put(RecyclerViewSpacesItemDecoration.BOTTOM_DECORATION,1);//底部间距

        //stringIntegerHashMap.put(RecyclerViewSpacesItemDecoration.LEFT_DECORATION,10);//左间距

        //stringIntegerHashMap.put(RecyclerViewSpacesItemDecoration.RIGHT_DECORATION,10);//右间距

        mRecyclerView.addItemDecoration(new RecyclerViewSpacesItemDecoration(stringIntegerHashMap));

        picAdapter=new ApplyPublicAdapter(this,list);
        //条目点击事件
        picAdapter.setOnItemClickLitener(new ApplyPublicAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(int position) {
                getData(Integer.parseInt(list.get(position).getId()));
                //Toast.makeText(ApplyPublicActivity.this,"点击了"+position,Toast.LENGTH_SHORT).show();
            }
        });
        mRecyclerView.setAdapter(picAdapter);

        //mRecyclerView.setAdapter(picAdapter);
        picAdapter.addFooterView(R.layout.view_footer);//添加脚布局
        mRecyclerView.addOnScrollListener(new EndLessOnScrollListener(linearLayoutManager) {//滑动到底部 加载更多
            //EndLessOnScrollListener 是自定义的监听器
            @Override
            public void onLoadMore() {
                pages++;
                picAdapter.setFooterVisible(View.VISIBLE);
                initNewsData(pages);
            }

            @Override
            public void hide() {
            }
            @Override
            public void show() {
            }
        });
    }

    /**
     * 图片瀑布流 初始化 网络请求第一页数据
     * @param pages
     */
    private void initNewsData(int pages) {
        //使用retrofit配置api
        Retrofit retrofit=new Retrofit.Builder()
                .baseUrl(Config.BANNER_BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        Api api =retrofit.create(Api.class);
        Call<ApplyPublic> call=api.getPublicApplicationData(pages);
        call.enqueue(new Callback<ApplyPublic>() {
            @Override
            public void onResponse(Call<ApplyPublic> call, Response<ApplyPublic> response) {
                if(response.body().getResults()!=null){
                    list.addAll(response.body().getResults());
                    Log.e("xxxxxx",response.body().toString());
                    picAdapter.notifyDataSetChanged();
                    refreshLayout.setRefreshing(false);
                }
            }

            @Override
            public void onFailure(Call<ApplyPublic> call, Throwable t) {
                Toast.makeText(ApplyPublicActivity.this,"请求失败!",Toast.LENGTH_SHORT).show();
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
        Call<ApplyPublicDetail> call=api.getApplyPublicDetailData(id);
        call.enqueue(new Callback<ApplyPublicDetail>() {
            @Override
            public void onResponse(Call<ApplyPublicDetail> call, Response<ApplyPublicDetail> response) {
                if(response!=null){
                    ApplyPublicDetail detail=response.body();
                    ApplyPublicDetail.ResultsBean resultsBean=detail.getResults().get(0);
                    Intent intent = new Intent(ApplyPublicActivity.this,ApplyPublicDetailActivity.class);
                    intent.putExtra(Config.NEWS,resultsBean);
                    startActivity(intent);
                    Log.e("-----dept_name-------",resultsBean.getDept_name());
                }else{
                    Toast.makeText(ApplyPublicActivity.this,"数据为空!",Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<ApplyPublicDetail> call, Throwable t) {
                Toast.makeText(ApplyPublicActivity.this,"请求失败!",Toast.LENGTH_SHORT).show();
                Log.e("-------------",t.getMessage().toString());
            }
        });
    }

    @OnClick({ R.id.iv_back})
    public void onClick(View view) {
        finish();
    }

    @Override
    public void onRefresh() {
        picAdapter.setFooterVisible(View.GONE);
        pages = 1;
        list.clear();
        initNewsData(1);
    }
}
