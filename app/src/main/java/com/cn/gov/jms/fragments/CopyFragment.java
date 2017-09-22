package com.cn.gov.jms.fragments;

import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.bartoszlipinski.recyclerviewheader.RecyclerViewHeader;
import com.cn.gov.jms.Config;
import com.cn.gov.jms.adapter.GirlAdapter;
import com.cn.gov.jms.adapter.TopAdapter;
import com.cn.gov.jms.base.BaseFragment;
import com.cn.gov.jms.base.EndLessOnScrollListener;
import com.cn.gov.jms.model.Banners;
import com.cn.gov.jms.model.Datas;
import com.cn.gov.jms.model.Detail;
import com.cn.gov.jms.model.NewCenter;
import com.cn.gov.jms.presenter.NewsPresenterImpl;
import com.cn.gov.jms.services.Api;
import com.cn.gov.jms.ui.DetailActivity;
import com.cn.gov.jms.ui.R;
import com.cn.gov.jms.utils.RecyclerViewSpacesItemDecoration;
import com.github.library.BaseRecyclerAdapter;
import com.zanlabs.widget.infiniteviewpager.InfiniteViewPager;
import com.zanlabs.widget.infiniteviewpager.indicator.CirclePageIndicator;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
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
 * Created by wangjiawei on 2017/7/19.
 */

public class CopyFragment extends BaseFragment implements SwipeRefreshLayout.OnRefreshListener {

    private static CopyFragment homeFragment;//单例模式
    private List<Banners.ResultsBean> resultsBeanList;
    public static CopyFragment getInstance() {
        if(homeFragment==null){
            homeFragment = new CopyFragment();
        }
        return homeFragment;
    }

    @BindView(R.id.home_recycler)
    RecyclerView mHomeRecycler;
    @BindView(R.id.home_viewpager)
    InfiniteViewPager mHomeViewpager;
    @BindView(R.id.home_indicator)
    CirclePageIndicator mHomeIndicator;
    @BindView(R.id.home_header)
    RecyclerViewHeader mHomeHeader;

    //照片墙
    @BindView(R.id.refreshLayout)
    SwipeRefreshLayout refreshLayout;
    @BindView(R.id.recyclerView)
    RecyclerView mRecyclerView;
    //private ArrayList<DataInfo.Info> arrayList;
    private ArrayList<NewCenter.ResultsBean> list;
    private GirlAdapter picAdapter;
    private LinearLayoutManager linearLayoutManager;
    private int pages=1;


    private Unbinder mUnbinder;
    private BaseRecyclerAdapter<Datas.ResultBean.DataBean> mAdapter;
    private NewsPresenterImpl mPresenter;
    //private List<String> banner_img;
    private List<String> banner_url;
    private boolean connect = false;//判断网络是否连接正常
    //private Handler mHandler;

    @Override
    public int getLayoutId()
    {
        checkNet();
        return R.layout.fragment_collect;
    }

    @Override
    public void initVariables()
    {

        //mPresenter = new NewsPresenterImpl(getActivity(), this);

        //banner_img = new ArrayList<>();
        banner_url = new ArrayList<>();
        //banner_img = Arrays.asList(Config.BANNER_IMGS);
        resultsBeanList = new ArrayList<>();
        banner_url = Arrays.asList(Config.BANNER_URL);
        //mHandler = new Handler();
    }

    @Override
    public void initViews(View view, Bundle savedInstanceState)
    {
        mUnbinder = ButterKnife.bind(this, view);
        mHomeRecycler.setLayoutManager(new LinearLayoutManager(getActivity()));

        //初始化  广告窗体
        mHomeHeader.attachTo(mHomeRecycler,true);
        initBannerData(); //服务器 链接不上  网页404

//        //网格布局
//        // 新建List
//        data_list = new ArrayList<Map<String, Object>>();
//        //获取数据
//        getData();
//        //新建适配器
//        String [] from ={"image","text"};
//        int [] to = {R.id.image,R.id.text};
//        sim_adapter = new SimpleAdapter(getActivity(), data_list, R.layout.item_gridview, from, to);
//        //配置适配器
//        mGridview.setAdapter(sim_adapter);
//        mGridview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
//            @Override
//            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
//                switch (i){
//                    case 0:
//                        getActivity().startActivity(new Intent(getActivity(), ShiQingGaiKuangActivity.class));
//                        break;
//                    case 1:
//                        getActivity().startActivity(new Intent(getActivity(), NewsCenterActivity.class));
//                        break;
//                    case 2:
//                        getActivity().startActivity(new Intent(getActivity(), PublicNoticeActivity.class));
//                        break;
//                    case 3:
//                        getActivity().startActivity(new Intent(getActivity(), ZhengWuGongKaiActivity.class));
//                        break;
//                    case 4:
//                        getActivity().startActivity(new Intent(getActivity(), Online_servicesActivity.class));
//                        break;
//                    case 5:
//                        getActivity().startActivity(new Intent(getActivity(), Convenience_ServicesActivity.class));
//                        break;
//                    case 6:
//                        getActivity().startActivity(new Intent(getActivity(), ZhengminHudongActivity.class));
//                        break;
//                    case 7:
//                        //getActivity().startActivity(new Intent(getActivity(), Investment_guideActivity.class));
//                        Toast.makeText(getActivity(),"投资指南待定",Toast.LENGTH_SHORT).show();
//                        break;
//                    default:
//                        break;
//                }
//            }
//        });

        //图文
        refreshLayout.setOnRefreshListener(this);
        list=new ArrayList();
        initData(1);
        linearLayoutManager = new LinearLayoutManager(getActivity());
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        mRecyclerView.setLayoutManager(linearLayoutManager);
        mRecyclerView.setHasFixedSize(true);
        mRecyclerView.setNestedScrollingEnabled(false);

        HashMap<String, Integer> stringIntegerHashMap = new HashMap<>();
        stringIntegerHashMap.put(RecyclerViewSpacesItemDecoration.TOP_DECORATION,0);//top间距

        stringIntegerHashMap.put(RecyclerViewSpacesItemDecoration.BOTTOM_DECORATION,0);//底部间距

        stringIntegerHashMap.put(RecyclerViewSpacesItemDecoration.LEFT_DECORATION,10);//左间距

        stringIntegerHashMap.put(RecyclerViewSpacesItemDecoration.RIGHT_DECORATION,10);//右间距

        mRecyclerView.addItemDecoration(new RecyclerViewSpacesItemDecoration(stringIntegerHashMap));

        picAdapter=new GirlAdapter(list,getActivity());
        //条目点击事件
        picAdapter.setOnItemClickLitener(new GirlAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(int position) {
                getData(Integer.parseInt(list.get(position)._id));
                //Toast.makeText(getActivity(),"点击了"+position,Toast.LENGTH_SHORT).show();
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
//                relativeLayout.animate().translationY(0).setInterpolator(new AccelerateDecelerateInterpolator());
//                relativeLayout.setVisibility(View.VISIBLE);
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
                if(response!=null){
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
                }else{
                    Toast.makeText(getActivity(),"数据为空!",Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<Banners> call, Throwable t) {
                Toast.makeText(getActivity(),"请求失败!",Toast.LENGTH_SHORT).show();
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
                .baseUrl(Config.BANNER_BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        Api api =retrofit.create(Api.class);
        Call<NewCenter> call=api.getNewCenterData("000100050007",pages);
        call.enqueue(new Callback<NewCenter>() {
            @Override
            public void onResponse(Call<NewCenter> call, Response<NewCenter> response) {
                list.addAll(response.body().results);
                Log.e("xxxxxx",response.body().toString());
                picAdapter.notifyDataSetChanged();
                refreshLayout.setRefreshing(false);
            }

            @Override
            public void onFailure(Call<NewCenter> call, Throwable t) {
                Toast.makeText(getActivity(),"请求失败!",Toast.LENGTH_SHORT).show();
                refreshLayout.setRefreshing(false);
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
                    Intent intent = new Intent(getActivity(), DetailActivity.class);
                    intent.putExtra(Config.NEWS,resultsBean);
                    startActivity(intent);
                    Log.e("xxxxxxx",resultsBean.content);
                }else{
                    Toast.makeText(getActivity(),"数据为空!",Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<Detail> call, Throwable t) {
                Toast.makeText(getActivity(),"请求失败!",Toast.LENGTH_SHORT).show();
                Log.e("-------------",t.getMessage().toString());
            }
        });
    }

    public void checkNet()//网络是否已经连接上
    {
        ConnectivityManager manager = (ConnectivityManager) getActivity().getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo info = manager.getActiveNetworkInfo();
        connect = info != null && info.isConnected();
    }


    @Override
    public void initLoadData()
    {
    }

    @Override
    protected void lazyFetchData() {

    }

    @Override
    public void onDestroyView()
    {
        super.onDestroyView();
        mUnbinder.unbind();
    }


    @Override
    public void onRefresh() {
        picAdapter.setFooterVisible(View.GONE);
        pages = 1;
        list.clear();
        initData(1);
    }
}
