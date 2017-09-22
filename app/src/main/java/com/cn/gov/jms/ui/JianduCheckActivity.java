package com.cn.gov.jms.ui;

import android.content.Intent;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.cn.gov.jms.Config;
import com.cn.gov.jms.adapter.GovInfoAdapter;
import com.cn.gov.jms.base.BaseActivity;
import com.cn.gov.jms.base.EndLessOnScrollListener;
import com.cn.gov.jms.model.Detail;
import com.cn.gov.jms.model.Gongzuonianbao;
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

public class JianduCheckActivity extends BaseActivity implements SwipeRefreshLayout.OnRefreshListener {

    @BindView(R.id.tv_title)
    TextView tv_title;

    //照片墙
    @BindView(R.id.refreshLayout)
    SwipeRefreshLayout refreshLayout;
    @BindView(R.id.recyerview)
    RecyclerView mRecyclerView;
    private ArrayList<Gongzuonianbao.ResultsBean> list;
    private GovInfoAdapter picAdapter;
    private LinearLayoutManager linearLayoutManager;
    private int pages=1;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_list_zwgk;
    }

    @Override
    protected void initView() {

        tv_title.setText("监督检查");

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

        picAdapter=new GovInfoAdapter(this,list);


        //条目点击事件
        picAdapter.setOnItemClickLitener(new GovInfoAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(int position) {
                getData(Integer.parseInt(list.get(position)._id));
                //Toast.makeText(NewsCenterActivity.this,"点击了"+position,Toast.LENGTH_SHORT).show();
            }
        });
        mRecyclerView.setAdapter(picAdapter);

        if(list.size()>6){
            picAdapter.addFooterView(R.layout.view_footer);//添加脚布局
        }

        mRecyclerView.addOnScrollListener(new EndLessOnScrollListener(linearLayoutManager) {//滑动到底部 加载更多
            //EndLessOnScrollListener 是自定义的监听器
            @Override
            public void onLoadMore() {
                pages++;
                initNewsData(pages);
                //picAdapter.setFooterVisible(View.VISIBLE);---------------崩溃
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
        Call<Gongzuonianbao> call=api.getMinShengData("000100020006",pages);
        call.enqueue(new Callback<Gongzuonianbao>() {
            @Override
            public void onResponse(Call<Gongzuonianbao> call, Response<Gongzuonianbao> response) {
                if(response.body().getResults().size()==0){
                    Toast.makeText(JianduCheckActivity.this,"已经没有数据了!",Toast.LENGTH_SHORT).show();
                }else{
                    list.addAll(response.body().getResults());
                    Log.e("xxxxxx请求数据集合大小", String.valueOf(list.size()));
                    Log.e("xxxxxx请求数据response", String.valueOf(response.body().getResults().size()));
                    picAdapter.notifyDataSetChanged();
                    refreshLayout.setRefreshing(false);
                }
            }

            @Override
            public void onFailure(Call<Gongzuonianbao> call, Throwable t) {
                Toast.makeText(JianduCheckActivity.this,"请求失败!",Toast.LENGTH_SHORT).show();
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
        Call<Detail> call=api.getDetailData(id);
        call.enqueue(new Callback<Detail>() {
            @Override
            public void onResponse(Call<Detail> call, Response<Detail> response) {
                if(response!=null){
                    Detail detail=response.body();
                    Detail.ResultsBean resultsBean=detail.getResults().get(0);
                    Intent intent = new Intent(JianduCheckActivity.this,DetailActivity.class);
                    intent.putExtra(Config.NEWS,resultsBean);
                    startActivity(intent);
                    Log.e("xxxxxxx",resultsBean.content);
                }else{
                    Toast.makeText(JianduCheckActivity.this,"数据为空!",Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<Detail> call, Throwable t) {
                Toast.makeText(JianduCheckActivity.this,"请求失败!",Toast.LENGTH_SHORT).show();
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
        //picAdapter.setFooterVisible(View.GONE);
        pages = 1;
        list.clear();
        initNewsData(1);
    }
}
