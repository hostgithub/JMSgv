package com.cn.gov.jms.fragments;

import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.RelativeLayout;
import android.widget.SimpleAdapter;
import android.widget.Toast;

import com.bartoszlipinski.recyclerviewheader.RecyclerViewHeader;
import com.cn.gov.jms.Config;
import com.cn.gov.jms.adapter.PicAdapter;
import com.cn.gov.jms.adapter.TopAdapter;
import com.cn.gov.jms.base.BaseFragment;
import com.cn.gov.jms.base.EndLessOnScrollListener;
import com.cn.gov.jms.model.Banners;
import com.cn.gov.jms.model.DataInfo;
import com.cn.gov.jms.model.Datas;
import com.cn.gov.jms.presenter.NewsPresenterImpl;
import com.cn.gov.jms.services.Api;
import com.cn.gov.jms.ui.Convenience_ServicesActivity;
import com.cn.gov.jms.ui.Investment_guideActivity;
import com.cn.gov.jms.ui.Online_servicesActivity;
import com.cn.gov.jms.ui.R;
import com.cn.gov.jms.ui.ShiQingGaiKuangActivity;
import com.cn.gov.jms.ui.ZhengWuGongKaiActivity;
import com.cn.gov.jms.ui.ZhengminHudongActivity;
import com.cn.gov.jms.utils.RecyclerViewSpacesItemDecoration;
import com.github.library.BaseRecyclerAdapter;
import com.zanlabs.widget.infiniteviewpager.InfiniteViewPager;
import com.zanlabs.widget.infiniteviewpager.indicator.CirclePageIndicator;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by wangjiawei on 2017/7/19.
 */

public class HomeFragment extends BaseFragment implements SwipeRefreshLayout.OnRefreshListener {

    private static HomeFragment homeFragment;//单例模式
    private List<Banners.ResultsBean> resultsBeanList;
    public static HomeFragment getInstance() {
        if(homeFragment==null){
            homeFragment = new HomeFragment();
        }
        return homeFragment;
    }

    //隐藏显示的布局
    @BindView(R.id.floatBtn)
    FloatingActionButton floatBtn;
    @BindView(R.id.rl)
    RelativeLayout relativeLayout;

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
    @BindView(R.id.recyerview)
    RecyclerView mRecyclerView;
    private ArrayList<DataInfo.Info> arrayList;
    private PicAdapter picAdapter;
    private GridLayoutManager linearLayoutManager;
    private int pages=1;


    //网格布局
    @BindView(R.id.gridview)
    GridView mGridview;
    private List<Map<String, Object>> data_list;
    private SimpleAdapter sim_adapter;
    // 图片封装为一个数组
    private int[] icon = { R.drawable.icon_01, R.drawable.icon_02,
            R.drawable.icon_03, R.drawable.icon_04, R.drawable.icon_05,
            R.drawable.icon_06, R.drawable.icon_07, R.drawable.icon_08,};
    private String[] iconName = { "市情概况", "新闻中心", "公告公示", "政务公开",
            "在线服务", "便民服务", "政民互动", "投资指南"};

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
        return R.layout.fragment_home;
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

//        mAdapter = new BaseRecyclerAdapter<Datas.ResultBean.DataBean>(getActivity(), null, R.layout.item)
//        {
//            @Override
//            protected void convert(BaseViewHolder helper, final Datas.ResultBean.DataBean item)
//            {
//                helper.setText(R.id.author_name, item.getAuthor_name());
//                String title = item.getTitle();
//                if (title.length() > 15)
//                {
//                    String s = title.substring(0, 15) + "...";
//                    helper.setText(R.id.text_title, s);
//                }else
//                {
//                    helper.setText(R.id.text_title, title);
//                }
//                helper.setText(R.id.realtype, item.getRealtype());
//                helper.setText(R.id.date, item.getDate());
//                SimpleDraweeView imageViews = (SimpleDraweeView) helper.getConvertView().findViewById(R.id.thumbnail);
//                imageViews.setImageURI(item.getThumbnail_pic_s());
//                RelativeLayout layout = (RelativeLayout) helper.getConvertView().findViewById(R.id.home_item);
//                layout.setOnClickListener(new View.OnClickListener()
//                {
//                    @Override
//                    public void onClick(View v)
//                    {
//                        Intent it = new Intent(getActivity(), DetailActivity.class);
//                        it.putExtra(Config.NEWS, item.getUrl());
//                        getActivity().startActivity(it);
//                    }
//                });
//            }
//        };
//        mHomeRecycler.setAdapter(mAdapter);

//        mHomeRefresh.setColorSchemeResources(R.color.colorBG);
//        mHomeRefresh.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener()
//        {
//            @Override
//            public void onRefresh()
//            {
//                mHomeRefresh.setRefreshing(true);
//                initLoadData();
//                mHandler.postDelayed(new Runnable()
//                {
//                    @Override
//                    public void run()
//                    {
//                        mHomeRefresh.setRefreshing(false);
//                    }
//                }, 1200);
//            }
//        });

        //初始化  广告窗体
        mHomeHeader.attachTo(mHomeRecycler,true);
        //initBannerData(); //服务器 链接不上  网页404

        //网格布局
        // 新建List
        data_list = new ArrayList<Map<String, Object>>();
        //获取数据
        getData();
        //新建适配器
        String [] from ={"image","text"};
        int [] to = {R.id.image,R.id.text};
        sim_adapter = new SimpleAdapter(getActivity(), data_list, R.layout.item_gridview, from, to);
        //配置适配器
        mGridview.setAdapter(sim_adapter);
        mGridview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                switch (i){
                    case 0:
                        getActivity().startActivity(new Intent(getActivity(), ShiQingGaiKuangActivity.class));
                        break;
                    case 1:
                        break;
                    case 2:
                        break;
                    case 3:
                        getActivity().startActivity(new Intent(getActivity(), ZhengWuGongKaiActivity.class));
                        break;
                    case 4:
                        getActivity().startActivity(new Intent(getActivity(), Online_servicesActivity.class));
                        break;
                    case 5:
                        getActivity().startActivity(new Intent(getActivity(), Convenience_ServicesActivity.class));
                        break;
                    case 6:
                        getActivity().startActivity(new Intent(getActivity(), ZhengminHudongActivity.class));
                        break;
                    case 7:
                        getActivity().startActivity(new Intent(getActivity(), Investment_guideActivity.class));
                        break;
                    default:
                        break;
                }
            }
        });

        //图文
        refreshLayout.setOnRefreshListener(this);
        arrayList=new ArrayList();
        initData(1);
        linearLayoutManager=new GridLayoutManager(getActivity(), 2);
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

        picAdapter=new PicAdapter(getActivity(),arrayList);
        //条目点击事件
        picAdapter.setOnItemClickLitener(new PicAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(int position) {
                Toast.makeText(getActivity(),"点击了"+position,Toast.LENGTH_SHORT).show();
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
                relativeLayout.animate().translationY(-relativeLayout.getHeight()).setInterpolator(new AccelerateDecelerateInterpolator());
                relativeLayout.setVisibility(View.GONE);//效果 不是很好  总闪动

                floatBtn.setVisibility(View.VISIBLE);//效果 不是很好  总闪动
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

    public void checkNet()//网络是否已经连接上
    {
        ConnectivityManager manager = (ConnectivityManager) getActivity().getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo info = manager.getActiveNetworkInfo();
        connect = info != null && info.isConnected();
    }

    /**
     * 网格布局初始化数据
     * @return
     */
    public List<Map<String, Object>> getData(){
        //cion和iconName的长度是相同的，这里任选其一都可以
        for(int i=0;i<icon.length;i++){
            Map<String, Object> map = new HashMap<String, Object>();
            map.put("image", icon[i]);
            map.put("text", iconName[i]);
            data_list.add(map);
        }
        return data_list;
    }

    @Override
    public void initLoadData()
    {
//        if (connect)
//        {
//            mPresenter.loadingDatasFromNet();
//        }else
//        {
//            mPresenter.loadingDatasFromCache();
//        }
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


//    @Override
//    public void showInfo(List<Datas.ResultBean.DataBean> news)
//    {
//        mAdapter.setData(news);
//    }
//
//    @Override
//    public void showError(String msg)
//    {
//        Log.e("info", "--------------------->" + msg);
//    }

    @Override
    public void onRefresh() {
        picAdapter.setFooterVisible(View.GONE);
        pages = 1;
        arrayList.clear();
        initData(1);
    }

    @OnClick({ R.id.floatBtn})
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.floatBtn:
                relativeLayout.animate().translationY(0).setInterpolator(new AccelerateDecelerateInterpolator());
                relativeLayout.setVisibility(View.VISIBLE);
                floatBtn.setVisibility(View.GONE);
                break;
            default:
                break;
        }
    }
}
