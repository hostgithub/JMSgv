package com.cn.gov.jms.ui;

import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.cn.gov.jms.adapter.NewsCenterAdapter;
import com.cn.gov.jms.base.BaseActivity;
import com.cn.gov.jms.base.EndLessOnScrollListener;
import com.cn.gov.jms.model.DataInfo;
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

public class NewsCenterActivity extends BaseActivity implements SwipeRefreshLayout.OnRefreshListener {

    //照片墙
    @BindView(R.id.refreshLayout)
    SwipeRefreshLayout refreshLayout;
    @BindView(R.id.recyerview)
    RecyclerView mRecyclerView;
    private ArrayList<DataInfo.Info> arrayList;
    private NewsCenterAdapter picAdapter;
    private LinearLayoutManager linearLayoutManager;
    private int pages=1;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_news_center;
    }

    @Override
    protected void initView() {

        //图文
        refreshLayout.setOnRefreshListener(this);
        arrayList=new ArrayList();
        initData(1);
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

        picAdapter=new NewsCenterAdapter(this,arrayList);
        //条目点击事件
        picAdapter.setOnItemClickLitener(new NewsCenterAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(int position) {
                Toast.makeText(NewsCenterActivity.this,"点击了"+position,Toast.LENGTH_SHORT).show();
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
                initData(pages);
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
    private void initData(int pages) {
        //使用retrofit配置api
        Retrofit retrofit=new Retrofit.Builder()
                .baseUrl("http://gank.io/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        Api api =retrofit.create(Api.class);
        Call<DataInfo> call=api.getData(6,pages);
        call.enqueue(new Callback<DataInfo>() {
            @Override
            public void onResponse(Call<DataInfo> call, Response<DataInfo> response) {

                arrayList.addAll(response.body().results);
                picAdapter.notifyDataSetChanged();
                Log.i("aaaa", arrayList.size() + "");
                refreshLayout.setRefreshing(false);
            }

            @Override
            public void onFailure(Call<DataInfo> call, Throwable t) {
                refreshLayout.setRefreshing(false);
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
        arrayList.clear();
        initData(1);
    }
}
