package com.cn.gov.jms.fragments;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;

import com.bartoszlipinski.recyclerviewheader.RecyclerViewHeader;
import com.cn.gov.jms.Config;
import com.cn.gov.jms.adapter.GirlAdapter;
import com.cn.gov.jms.adapter.TopAdapter;
import com.cn.gov.jms.base.BaseFragment;
import com.cn.gov.jms.base.EndLessOnScrollListener;
import com.cn.gov.jms.collectmokuan.GirlContract;
import com.cn.gov.jms.collectmokuan.GirlPresenter;
import com.cn.gov.jms.entity.JcodeEntity;
import com.cn.gov.jms.model.Banners;
import com.cn.gov.jms.services.Api;
import com.cn.gov.jms.ui.R;
import com.cn.gov.jms.ui.WebViewActivity;
import com.zanlabs.widget.infiniteviewpager.InfiniteViewPager;
import com.zanlabs.widget.infiniteviewpager.indicator.CirclePageIndicator;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by Administrator on 2016/12/2.
 */

public class CollectFragment extends BaseFragment implements GirlContract.View, SwipeRefreshLayout.OnRefreshListener {

    private static CollectFragment collectFragment;//单例模式
    private List<Banners.ResultsBean> resultsBeanList;
    public static CollectFragment getInstance() {

        if(collectFragment == null){
            collectFragment = new CollectFragment();
        }
        return collectFragment;
    }

    private Unbinder mUnbinder;

    @BindView(R.id.home_recycler)
    RecyclerView mHomeRecycler;
    @BindView(R.id.home_viewpager)
    InfiniteViewPager mHomeViewpager;
    @BindView(R.id.home_indicator)
    CirclePageIndicator mHomeIndicator;
    @BindView(R.id.home_header)
    RecyclerViewHeader mHomeHeader;

    private boolean connect = false;//判断网络是否连接正常
    private List<String> banner_img;
    private List<String> banner_url;



    @BindView(R.id.recyclerView) //列表控件
    RecyclerView mRecyclerView;
    @BindView(R.id.refreshLayout)//刷新控件
    SwipeRefreshLayout mRefreshLayout;

    private GirlAdapter mAdapter; //适配器
    private List<JcodeEntity> jcodes = new ArrayList<>();//数据源 集合
    private GirlPresenter mPresenter;//MVP 中的P
    private int pageIndex = 1;//第一页

    private static final String HOST = "http://www.jcodecraeer.com";//baseUrl

    @Override
    public int getLayoutId()
    {
        checkNet();
        return R.layout.fragment_collect;
    }

    @Override
    public void initVariables()
    {
        banner_img = new ArrayList<>();
        banner_url = new ArrayList<>();
        banner_img = Arrays.asList(Config.BANNER_IMGS);
        banner_url = Arrays.asList(Config.BANNER_URL);
        resultsBeanList = new ArrayList<>();
    }

    @Override
    public void initViews(View view, Bundle savedInstanceState)
    {
        mUnbinder = ButterKnife.bind(this, view);

        mHomeRecycler.setLayoutManager(new LinearLayoutManager(getActivity()));
        //广告窗体
        mHomeHeader.attachTo(mHomeRecycler,true);
        initBannerData();


        mPresenter = new GirlPresenter(this);
        //设置刷新时 进度条不同颜色 变换
        mRefreshLayout.setColorSchemeResources(R.color.colorPrimary, android.R.color.holo_red_light, android.R.color.holo_orange_light, android.R.color.holo_green_light);
        //刷新 监听
        mRefreshLayout.setOnRefreshListener(this);
        LinearLayoutManager manager = new LinearLayoutManager(getActivity());
        manager.setOrientation(LinearLayoutManager.VERTICAL);
        mRecyclerView.setLayoutManager(manager);
        mRecyclerView.setHasFixedSize(true);
        //mRecyclerView.setItemAnimator(new DefaultItemAnimator());
        //mRecyclerView.addItemDecoration(new DefaultItemDecoration(getActivity()));
        //mAdapter = new GirlAdapter(jcodes, getActivity());----------------------------------------------------------------------
        //条目点击事件
        mAdapter.setOnItemClickLitener(new GirlAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(int position) {
                mPresenter.addRecord(getActivity(), jcodes.get(position));
                WebViewActivity.lanuch(getActivity(), HOST + jcodes.get(position).getDetailUrl());
            }
        });



        mRecyclerView.setAdapter(mAdapter);
        mAdapter.addFooterView(R.layout.layout_footer);//添加脚布局
        mRecyclerView.addOnScrollListener(new EndLessOnScrollListener(manager) {//滑动到底部 加载更多
            @Override
            public void onLoadMore() {
                pageIndex++;
                mAdapter.setFooterVisible(View.VISIBLE);
                mPresenter.loadData("http://www.jcodecraeer.com/plus/list.php?tid=18&TotalResult=1801&PageNo=" + pageIndex);
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

                TopAdapter adapter = new TopAdapter(getActivity(), resultsBeanList,banner_url);
                mHomeViewpager.setAdapter(adapter);
                mHomeViewpager.setAutoScrollTime(3000);
                mHomeViewpager.startAutoScroll();
                mHomeIndicator.setViewPager(mHomeViewpager);
            }

            @Override
            public void onFailure(Call<Banners> call, Throwable t) {

            }
        });
    }

    @Override
    public void initLoadData()
    {
    }

    @Override
    protected void lazyFetchData() {
        mRefreshLayout.setRefreshing(true);
        mPresenter.loadData("http://www.jcodecraeer.com/plus/list.php?tid=18&TotalResult=1801&PageNo=" + pageIndex);
    }

    @Override
    public void onRefresh() {
        pageIndex = 1;
        jcodes.clear();
        mPresenter.loadData("http://www.jcodecraeer.com/plus/list.php?tid=18&TotalResult=1801&PageNo=" + pageIndex);
    }

    @Override
    public void onResume() {
        mHomeViewpager.startAutoScroll();

        mRefreshLayout.setRefreshing(true);
        mPresenter.loadData("http://www.jcodecraeer.com/plus/list.php?tid=18&TotalResult=1801&PageNo=" + pageIndex);
        mRefreshLayout.setRefreshing(false);
        super.onResume();
    }

    @Override
    public void onDestroyView()
    {
        super.onDestroyView();
        mUnbinder.unbind();
    }

    public void checkNet()//网络是否已经连接上
    {
        ConnectivityManager manager = (ConnectivityManager) getActivity().getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo info = manager.getActiveNetworkInfo();
        connect = info != null && info.isConnected();
    }

    @Override
    public void showLoading() {
        startProgressDialog();
    }

    @Override
    public void stopLoading() {
        stopProgressDialog();
    }

    @Override
    public void showErrorTip(String msg) {
        showErrorHint(msg);
        mRefreshLayout.setRefreshing(false);
    }

    @Override
    public void returnData(List<JcodeEntity> datas) {
        if (pageIndex == 1) {
            //mAdapter.replaceAll(datas);----------------------------------
            mRefreshLayout.setRefreshing(false);
        } else {
            //mAdapter.addAll(datas);---------------------------------------------------
            mAdapter.setFooterVisible(View.GONE);
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        jcodes.clear();
        jcodes = null;
    }
}
