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
import com.cn.gov.jms.model.Gongzuonianbao;
import com.cn.gov.jms.model.ZixunFanyingDetailBean;
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

public class FanyingQuestionListActivity extends BaseActivity implements SwipeRefreshLayout.OnRefreshListener {

    @BindView(R.id.tv_title)
    TextView tv_title;
    @BindView(R.id.tv_bianji)
    TextView tv_bianji;

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
        return R.layout.activity_list_jinyanxiance;
    }

    @Override
    protected void initView() {

        tv_title.setText("反映问题");
        tv_bianji.setText("我要反映");
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
        Call<Gongzuonianbao> call=api.getZixunProblemData("10030002",pages);
        call.enqueue(new Callback<Gongzuonianbao>() {
            @Override
            public void onResponse(Call<Gongzuonianbao> call, Response<Gongzuonianbao> response) {
                if(response.body().getResults().size()==0){
                    Toast.makeText(FanyingQuestionListActivity.this,"已经没有数据了!",Toast.LENGTH_SHORT).show();
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
                Toast.makeText(FanyingQuestionListActivity.this,"请求失败!",Toast.LENGTH_SHORT).show();
            }
        });
    }


    private void getData(final int id){
        //使用retrofit配置api
        Retrofit retrofit=new Retrofit.Builder()
                .baseUrl(Config.BANNER_BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        Api api =retrofit.create(Api.class);
        Call<ZixunFanyingDetailBean> call=api.getByIdrocData(id);
        call.enqueue(new Callback<ZixunFanyingDetailBean>() {
            @Override
            public void onResponse(Call<ZixunFanyingDetailBean> call, Response<ZixunFanyingDetailBean> response) {
                if(response!=null){
                    ZixunFanyingDetailBean detail=response.body();
                    ZixunFanyingDetailBean.ResultsBean resultsBean=detail.getResults().get(0);
                    Intent intent = new Intent(FanyingQuestionListActivity.this,FanyingDetailActivity.class);
                    intent.putExtra(Config.NEWS,resultsBean);
                    intent.putExtra(Config.LIST_ID,id);
                    startActivity(intent);
                    Log.e("xxx咨询详情xxxx",response.body().toString());
                }else{
                    Toast.makeText(FanyingQuestionListActivity.this,"数据为空!",Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<ZixunFanyingDetailBean> call, Throwable t) {
                Toast.makeText(FanyingQuestionListActivity.this,"请求失败!",Toast.LENGTH_SHORT).show();
                Log.e("-------------",t.getMessage().toString());
            }
        });
    }

    @OnClick({ R.id.iv_back,R.id.tv_bianji})
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.iv_back:
                finish();
                break;
            case R.id.tv_bianji: //我要反映
                startActivity(new Intent(FanyingQuestionListActivity.this,FanyingQuestionActivity.class));
                break;
            default:
                break;
        }
    }

    @Override
    public void onRefresh() {
        //picAdapter.setFooterVisible(View.GONE);
        pages = 1;
        list.clear();
        initNewsData(1);
    }

    @Override
    protected void onResume() {
        pages = 1;
        list.clear();
        initNewsData(1);
        super.onResume();
    }
}
