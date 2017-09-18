package com.cn.gov.jms.ui;

import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.SimpleAdapter;
import android.widget.Toast;

import com.cn.gov.jms.base.BaseActivity;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.OnClick;

public class Online_servicesActivity extends BaseActivity {

    //网格布局
    @BindView(R.id.gridview)
    GridView mGridview;
    private List<Map<String, Object>> data_list;
    private SimpleAdapter sim_adapter;
    // 图片封装为一个数组
    private int[] icon = {
            R.drawable.line_shu, R.drawable.line_shu, R.drawable.line_shu,R.drawable.line_shu,R.drawable.line_shu,R.drawable.line_shu,
            R.drawable.line_shu,R.drawable.line_shu,R.drawable.line_shu,R.drawable.line_shu,R.drawable.line_shu,R.drawable.line_shu,
            R.drawable.line_shu,R.drawable.line_shu,R.drawable.line_shu,R.drawable.line_shu, R.drawable.line_shu, R.drawable.line_shu,
            R.drawable.line_shu, R.drawable.line_shu, R.drawable.line_shu, R.drawable.line_shu, R.drawable.line_shu, R.drawable.line_shu,
            R.drawable.line_shu, R.drawable.line_shu, R.drawable.line_shu, R.drawable.line_shu, R.drawable.line_shu, R.drawable.line_shu,
            R.drawable.line_shu, R.drawable.line_shu, R.drawable.line_shu, R.drawable.line_shu, R.drawable.line_shu, R.drawable.line_shu};
    private String[] iconName = { "粮食局", "烟草局", "畜牧局", "旅游局", "民政局", "交通局", "工商局",
            "民宗局", "环保局", "城管局", "物价局", "水务局", "公安局", "质监局", "国士局", "地震局",
            "规划局", "建设局", "气象局", "体育局", "财政局","人社局","林业局","科技局","教育局","安监局",
            "食药监局","不动产局","住房保障局","住房公积金","商务和经济","消防支队","人防办",
            "卫计委","发改委","农委"};

    @Override
    protected int getLayoutId() {
        return R.layout.activity_online_services;
    }

    @Override
    protected void initView() {

        //网格布局
        // 新建List
        data_list = new ArrayList<Map<String, Object>>();
        //获取数据
        getData();
        //新建适配器
        String [] from ={"image","text"};
        int [] to = {R.id.image,R.id.text};
        sim_adapter = new SimpleAdapter(this, data_list, R.layout.item_gridview_online_service, from, to);
        //配置适配器
        mGridview.setAdapter(sim_adapter);
        mGridview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Toast.makeText(Online_servicesActivity.this,"点击了第"+(i+1)+"个条目",Toast.LENGTH_SHORT).show();
            }
        });
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

    @OnClick({ R.id.iv_back,R.id.personal_business,R.id.zheng_people_interact,
            R.id.query,R.id.enterprise_management,R.id.open_government,R.id.public_service,R.id.organisation})
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.iv_back:
                finish();
            case R.id.personal_business:finish();
                break;
            case R.id.zheng_people_interact:finish();
                break;
            case R.id.query:finish();
                break;
            case R.id.enterprise_management:finish();
                break;
            case R.id.open_government:finish();
                break;
            case R.id.public_service:finish();
                break;
            case R.id.organisation:finish();
                break;
            default:
                break;
        }
    }
}
