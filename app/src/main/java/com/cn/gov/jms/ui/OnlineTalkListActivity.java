package com.cn.gov.jms.ui;

import android.content.Intent;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.cn.gov.jms.Config;
import com.cn.gov.jms.adapter.OnlineTalkListAdapter;
import com.cn.gov.jms.base.BaseActivity;
import com.cn.gov.jms.base.EndLessOnScrollListener;
import com.cn.gov.jms.model.OnlineTalk;
import com.cn.gov.jms.model.OnlineTalkDetail;
import com.cn.gov.jms.services.Api;
import com.cn.gov.jms.utils.RecyclerViewSpacesItemDecoration;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;

import butterknife.BindView;
import butterknife.OnClick;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class OnlineTalkListActivity extends BaseActivity implements SwipeRefreshLayout.OnRefreshListener {

//    private ListView listview;
//    private ArrayList<String> list;
//    public int clickPosition = -1;

    //照片墙
    @BindView(R.id.refreshLayout)
    SwipeRefreshLayout refreshLayout;
    @BindView(R.id.recyclerView)
    RecyclerView mRecyclerView;
    private ArrayList<OnlineTalk.ResultsBean> list;
    private OnlineTalkListAdapter picAdapter;
    private LinearLayoutManager linearLayoutManager;
    private int pages=1;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_online_talk_list;
    }

    @Override
    protected void initView() {

//        listview = (ListView) findViewById(R.id.listview);
//        list = new ArrayList<>();
//        for (int i = 0; i < 200; i++) {
//            list.add("我是第" + i + "个条目");
//        }
//        adapter = new MyAdapter();
//        listview.setAdapter(adapter);

        //图文
        refreshLayout.setOnRefreshListener(this);
        list=new ArrayList();
        initData(1);
        linearLayoutManager = new LinearLayoutManager(this);
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

        picAdapter=new OnlineTalkListAdapter(list,this);
        //条目点击事件
        picAdapter.setOnItemClickLitener(new OnlineTalkListAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(int position) {
                getData(Integer.parseInt(list.get(position).get_id()),list.get(position).getUpId());
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
        Call<OnlineTalk> call=api.getInterviewListData(pages);
        call.enqueue(new Callback<OnlineTalk>() {
            @Override
            public void onResponse(Call<OnlineTalk> call, Response<OnlineTalk> response) {
                if(response.body()!=null){
                    list.addAll(response.body().getResults());
                    Log.e("xxxxxx",response.body().toString());
                    picAdapter.notifyDataSetChanged();
                    refreshLayout.setRefreshing(false);
                }
            }

            @Override
            public void onFailure(Call<OnlineTalk> call, Throwable t) {
                Toast.makeText(OnlineTalkListActivity.this,"请求失败!",Toast.LENGTH_SHORT).show();
                refreshLayout.setRefreshing(false);
            }
        });
    }

    private void getData(int id,String upId){
        //使用retrofit配置api
        Retrofit retrofit=new Retrofit.Builder()
                .baseUrl(Config.BANNER_BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        Api api =retrofit.create(Api.class);
        Call<OnlineTalkDetail> call=api.getOnlineTalkByIdData(id,upId);
        call.enqueue(new Callback<OnlineTalkDetail>() {
            @Override
            public void onResponse(Call<OnlineTalkDetail> call, Response<OnlineTalkDetail> response) {
                if(response.body()!=null){
                    OnlineTalkDetail onlineTalkDetail=response.body();
                    Log.e("-onlineTalkDetail---",onlineTalkDetail.getResults().toString());
                    Log.e("----host---",onlineTalkDetail.getHost().toString());
                    Intent intent=new Intent(OnlineTalkListActivity.this,OnlineTalkActivity.class);
                    intent.putExtra(Config.RESULT_BEAN, (Serializable) onlineTalkDetail.getResults());
                    intent.putExtra(Config.LIST_HOST, (Serializable) onlineTalkDetail.getHost());
                    startActivity(intent);
                }else{
                    Toast.makeText(OnlineTalkListActivity.this,"数据为空!",Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<OnlineTalkDetail> call, Throwable t) {
                Toast.makeText(OnlineTalkListActivity.this,"抱歉可能还未添加信息!",Toast.LENGTH_SHORT).show();
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
        initData(1);
    }

    //private MyAdapter adapter;

//    class MyAdapter extends BaseAdapter {
//
//        private ArrayList<String> sublist;
//        private SubAdapter subAdapter;
//
//        @Override
//        public int getCount() {
//            return list.size();
//        }
//
//        @Override
//        public Object getItem(int position) {
//            return null;
//        }
//
//        @Override
//        public long getItemId(int position) {
//            return 0;
//        }
//
//        @Override
//        public View getView(final int position, View convertView, ViewGroup parent) {
//            final MyViewHolder vh;
//            if (convertView == null) {
//                convertView = View.inflate(ZhengminHudongActivity.this, R.layout.item_hide_show_all, null);
//                vh = new MyViewHolder(convertView);
//                convertView.setTag(vh);
//            } else {
//                vh = (MyViewHolder) convertView.getTag();
//            }
//            vh.tv_test.setText(list.get(position));
//            //刷新adapter的时候，getview重新执行，此时对在点击中标记的position做处理
//            if (clickPosition == position) {//当条目为刚才点击的条目时
//                if (vh.selectorImg.isSelected()) {//当条目状态图标为选中时，说明该条目处于展开状态，此时让它隐藏，并切换状态图标的状态。
//                    vh.selectorImg.setSelected(false);
//                    vh.selectorImg.setImageResource(R.drawable.show_icon);
//                    vh.ll_hide.setVisibility(View.GONE);
//                    Log.e("listview","if执行");
//                    clickPosition=-1;//隐藏布局后需要把标记的position去除掉，否则，滑动listview让该条目划出屏幕范围，
//                    // 当该条目重新进入屏幕后，会重新恢复原来的显示状态。经过打log可知每次else都执行一次 （条目第二次进入屏幕时会在getview中寻找他自己的状态，相当于重新执行一次getview）
//                    //因为每次滑动的时候没标记得position填充会执行click
//                } else {//当状态条目处于未选中时，说明条目处于未展开状态，此时让他展开。同时切换状态图标的状态。
//                    vh.selectorImg.setSelected(true);
//                    vh.selectorImg.setImageResource(R.drawable.hint_icon);
//                    vh.ll_hide.setVisibility(View.VISIBLE);
//
//                    Log.e("listview","else执行");
//                }
////                ObjectAnimator//
////                        .ofInt(vh.ll_hide, "rotationX", 0.0F, 360.0F)//
////                        .setDuration(500)//
////                        .start();
//                // vh.selectorImg.setSelected(true);
//            } else {//当填充的条目position不是刚才点击所标记的position时，让其隐藏，状态图标为false。
//
//                //每次滑动的时候没标记得position填充会执行此处，把状态改变。所以如果在以上的if (vh.selectorImg.isSelected()) {}中不设置clickPosition=-1；则条目再次进入屏幕后，还是会进入clickposition==position的逻辑中
//                //而之前的滑动（未标记条目的填充）时，执行此处逻辑，已经把状态图片的selected置为false。所以上面的else中的逻辑会在标记过的条目第二次进入屏幕时执行，如果之前的状态是显示，是没什么影响的，再显示一次而已，用户看不出来，但是如果是隐藏状态，就会被重新显示出来
//                vh.ll_hide.setVisibility(View.GONE);
//                vh.selectorImg.setImageResource(R.drawable.show_icon);
//                vh.selectorImg.setSelected(false);
//
//                Log.e("listview","状态改变");
//            }
////            vh.hide_1.setOnClickListener(this);
////            vh.hide_2.setOnClickListener(this);
////            vh.hide_3.setOnClickListener(this);
////            vh.hide_4.setOnClickListener(this);
////            vh.hide_5.setOnClickListener(this);
//
//            sublist = new ArrayList<>();
//            for (int i = 0; i < 10; i++) {
//                sublist.add("我是第" + i + "个子条目");
//            }
//            subAdapter = new SubAdapter(sublist,ZhengminHudongActivity.this);
//            vh.subListView.setAdapter(subAdapter);
//            Utility.setListViewHeightBasedOnChildren(vh.subListView);
//
//            vh.selectorImg.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View v) {
//                    Toast.makeText(ZhengminHudongActivity.this, "被点了", Toast.LENGTH_SHORT).show();
//                    clickPosition = position;//记录点击的position
//                    notifyDataSetChanged();//刷新adapter重新填充条目。在重新填充的过程中，被记录的position会做展开或隐藏的动作，具体的判断看上面代码
//                    //在此处需要明确的一点是，当adapter执行刷新操作时，整个getview方法会重新执行，也就是条目重新做一次初始化被填充数据。
//                    //所以标记position，不会对条目产生影响，执行刷新后 ，条目重新填充当，填充至所标记的position时，我们对他处理，达到展开和隐藏的目的。
//                    //明确这一点后，每次点击代码执行逻辑就是 onclick（）---》getview（）
//                }
//            });
//            return convertView;
//        }
//        class MyViewHolder {
//            View itemView;
//            TextView tv_test;
//            ListView subListView;
//            ImageView selectorImg;
//            LinearLayout ll_hide;
//
//            public MyViewHolder(View itemView) {
//                this.itemView = itemView;
//                tv_test = (TextView) itemView.findViewById(R.id.tv_test);
//                selectorImg = (ImageView) itemView.findViewById(R.id.imageview);
//                ll_hide = (LinearLayout) itemView.findViewById(R.id.ll_hide);
//                subListView = (ListView) itemView.findViewById(R.id.sub_listview);
//            }
//        }
//    }
}
