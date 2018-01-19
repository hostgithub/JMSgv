package com.cn.gov.jms.ui;

import android.content.Intent;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import com.cn.gov.jms.Config;
import com.cn.gov.jms.adapter.XinWenTitleAdapter;
import com.cn.gov.jms.base.BaseActivity;
import com.cn.gov.jms.utils.RecyclerViewSpacesItemDecoration;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

public class XinWenCenterActivity extends BaseActivity {

    @BindView(R.id.iv_back)
    ImageView imageView;

    @BindView(R.id.recyerview)
    RecyclerView mRecyclerView;
    LinearLayoutManager linearLayoutManager;
    private boolean connect = false;//判断网络是否连接正常
    private List<String> new_title;
    private XinWenTitleAdapter xinWenTitleAdapter;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_xin_wen_center;
    }

    @Override
    protected void initView() {
        new_title = new ArrayList<>();
        new_title = Arrays.asList(Config.XINWEN_TITLE);

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

        xinWenTitleAdapter=new XinWenTitleAdapter(this,new_title);
        mRecyclerView.setAdapter(xinWenTitleAdapter);

        xinWenTitleAdapter.setOnItemClickLitener(new XinWenTitleAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(int position) {

                switch (position){
                    case 0:
                        Toast.makeText(XinWenCenterActivity.this,new_title.get(position),Toast.LENGTH_SHORT).show();
                        Intent intent=new Intent(XinWenCenterActivity.this, LocalNewsActivity.class);
                        intent.putExtra("position","0");
                        startActivity(intent);
                        break;
                    case 1:
                        Toast.makeText(XinWenCenterActivity.this,new_title.get(position),Toast.LENGTH_SHORT).show();
                        Intent intent1=new Intent(XinWenCenterActivity.this, LocalNewsActivity.class);
                        intent1.putExtra("position","1");
                        startActivity(intent1);
                        break;
                    case 2:
                        Toast.makeText(XinWenCenterActivity.this,new_title.get(position),Toast.LENGTH_SHORT).show();
                        Intent intent2=new Intent(XinWenCenterActivity.this, LocalNewsActivity.class);
                        intent2.putExtra("position","2");
                        startActivity(intent2);
                        break;
                    case 3:
                        Toast.makeText(XinWenCenterActivity.this,new_title.get(position),Toast.LENGTH_SHORT).show();
                        Intent intent3=new Intent(XinWenCenterActivity.this, LocalNewsActivity.class);
                        intent3.putExtra("position","3");
                        startActivity(intent3);
                        break;
                    case 4:
                        Toast.makeText(XinWenCenterActivity.this,new_title.get(position),Toast.LENGTH_SHORT).show();
                        startActivity(new Intent(XinWenCenterActivity.this, NewsCenterActivity.class));
                        break;
                    case 5:
                        Toast.makeText(XinWenCenterActivity.this,new_title.get(position),Toast.LENGTH_SHORT).show();
                        Intent intent4=new Intent(XinWenCenterActivity.this, LocalNewsActivity.class);
                        intent4.putExtra("position","4");
                        startActivity(intent4);
                        break;
                    case 6:
                        Toast.makeText(XinWenCenterActivity.this,new_title.get(position),Toast.LENGTH_SHORT).show();
                        Intent intent5=new Intent(XinWenCenterActivity.this, LocalNewsActivity.class);
                        intent5.putExtra("position","5");
                        startActivity(intent5);
                        break;
                    case 7:
                        Toast.makeText(XinWenCenterActivity.this,new_title.get(position),Toast.LENGTH_SHORT).show();
                        Intent intent6=new Intent(XinWenCenterActivity.this, LocalNewsActivity.class);
                        intent6.putExtra("position","6");
                        startActivity(intent6);
                        break;
                    case 8:
                        Toast.makeText(XinWenCenterActivity.this,new_title.get(position),Toast.LENGTH_SHORT).show();
//                        Intent intent7=new Intent(XinWenCenterActivity.this, LocalNewsActivity.class);
                        Intent intent7 =new Intent(XinWenCenterActivity.this, JmsDayReportActivity.class);
                        intent7.putExtra("position","7");
                        startActivity(intent7);
                        break;
                    case 9:
                        Toast.makeText(XinWenCenterActivity.this,new_title.get(position),Toast.LENGTH_SHORT).show();
                        Intent intent8 =new Intent(XinWenCenterActivity.this, JmsDayReportActivity.class);
                        intent8.putExtra("position","8");
                        startActivity(intent8);
//                        startActivity(new Intent(XinWenCenterActivity.this, JmsDayReportActivity.class));
                        break;
                    default:
                        break;
                }
            }
        });

    }

    @OnClick({ R.id.iv_back})
    public void onClick(View view) {
        finish();
    }
}
