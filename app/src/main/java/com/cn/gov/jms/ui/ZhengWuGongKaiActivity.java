package com.cn.gov.jms.ui;

import android.content.Intent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.SimpleAdapter;

import com.cn.gov.jms.base.BaseActivity;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.OnClick;

public class ZhengWuGongKaiActivity extends BaseActivity {

    //网格布局
    @BindView(R.id.gridview)
    GridView mGridview;
    private List<Map<String, Object>> data_list;
    private SimpleAdapter sim_adapter;
    // 图片封装为一个数组
    private int[] icon = { R.drawable.icon_a07, R.drawable.icon_a02, R.drawable.icon_a03, R.drawable.icon_a04, R.drawable.icon_public_services,
            R.drawable.icon_open_accounts_open, R.drawable.icon_a01, R.drawable.icon_organizations,R.drawable.icon_a09,
            R.drawable.market_surveillance,R.drawable.icon_a14, R.drawable.icon_ghjh,R.drawable.icon_tjxx,
            R.drawable.icon_a16,R.drawable.icon_a13, R.drawable.icon_education_and_health,R.drawable.icon_price_charged,
            R.drawable.icon_power_list};
    private String[] iconName = { "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", ""};

    @Override
    protected int getLayoutId() {
        return R.layout.activity_zheng_wu_gong_kai;
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
        sim_adapter = new SimpleAdapter(this, data_list, R.layout.item_gridview_zwgk, from, to);
        //配置适配器
        mGridview.setAdapter(sim_adapter);
        mGridview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                switch (i){
                    case 0://领导之窗
                        startActivity(new Intent(ZhengWuGongKaiActivity.this,LeaderOfWindowActivity.class));
                        break;
                    case 1://政策解读
                        startActivity(new Intent(ZhengWuGongKaiActivity.this,ZhengcejieduActivity.class));
                        break;
                    case 2://热点回应
                        startActivity(new Intent(ZhengWuGongKaiActivity.this,ReDianHuiYingActivity.class));
                        break;
                    case 3://人事信息
                        Intent intent3=new Intent(ZhengWuGongKaiActivity.this,PeronThingInfoActivity.class);
                        intent3.putExtra("info_id","000100020004");
                        startActivity(intent3);
                        break;
                    case 4://公共服务
                        startActivity(new Intent(ZhengWuGongKaiActivity.this,PublicServicesListActivity.class));
                        break;
                    case 5:// 法规公文 //预决算公开
                        startActivity(new Intent(ZhengWuGongKaiActivity.this,OpenAccountsOpenActivity.class));
                        break;
                    case 6://政府文件
                        startActivity(new Intent(ZhengWuGongKaiActivity.this,GovFileActivity.class));
                        break;
                    case 7://组织机构
                        startActivity(new Intent(ZhengWuGongKaiActivity.this,ZhishujigouActivity.class));
                        break;
                    case 8://政府报告
                        startActivity(new Intent(ZhengWuGongKaiActivity.this,GovReportActivity.class));
                        break;
                    case 9://市场监管
                        startActivity(new Intent(ZhengWuGongKaiActivity.this,JianduCheckActivity.class));
                        break;
                    case 10://环境保护
                        startActivity(new Intent(ZhengWuGongKaiActivity.this,EnviromentProtectActivity.class));
                        break;
                    case 11://规划计划
                        startActivity(new Intent(ZhengWuGongKaiActivity.this,GuiHuaJiHuaActivity.class));
                        break;

                    case 12://统计信息
                        startActivity(new Intent(ZhengWuGongKaiActivity.this,TongJiMessageActivity.class));
                        break;
                    case 13://政府采购
                        startActivity(new Intent(ZhengWuGongKaiActivity.this,GovBuyActivity.class));
                        break;
                    case 14://食药安全
                        startActivity(new Intent(ZhengWuGongKaiActivity.this,ShiYaoAnQuanActivity.class));
                        break;
                    case 15://教育卫生
                        startActivity(new Intent(ZhengWuGongKaiActivity.this,EducationAndHealthActivity.class));
                        break;
                    case 16://价格收费
                        startActivity(new Intent(ZhengWuGongKaiActivity.this,PriceChargedActivity.class));
                        break;
                    case 17://权力清单
                        startActivity(new Intent(ZhengWuGongKaiActivity.this,PowerListActivity.class));
                        break;
//                    case 18://专题信息
//                        Intent intent=new Intent(ZhengWuGongKaiActivity.this,PeronThingInfoActivity.class);
//                        intent.putExtra("info_id","000100020013");
//                        startActivity(intent);
//                        break;
                    default:
                        break;
                }
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

    @OnClick({ R.id.iv_back,R.id.system_of_government_information_publicity,R.id.government_information_public_directory,
            R.id.guide_to_government_information_disclosure,R.id.open_in_accordance_with_the_application,R.id.government_information_public_briefing,
            R.id.annual_report_on_government_information_work,R.id.important_area})
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.iv_back:
                finish();
                break;
            case R.id.system_of_government_information_publicity://政府信息公开制度
                startActivity(new Intent(ZhengWuGongKaiActivity.this,ListZwgkActivity.class));
                break;
            case R.id.government_information_public_directory:
                startActivity(new Intent(ZhengWuGongKaiActivity.this,PublicDirectoryActivity.class));//政府信息公开目录
                break;
            case R.id.guide_to_government_information_disclosure://政府信息公开指南
                startActivity(new Intent(ZhengWuGongKaiActivity.this,PublicGuideActivity.class));
                break;
            case R.id.open_in_accordance_with_the_application:
                startActivity(new Intent(ZhengWuGongKaiActivity.this,ApplyPublicActivity.class));
                break;
            case R.id.government_information_public_briefing:
                startActivity(new Intent(ZhengWuGongKaiActivity.this,GongkaijianbaoActivity.class));//政府信息公开简报
                break;
            case R.id.annual_report_on_government_information_work://政府信息工作年报
                startActivity(new Intent(ZhengWuGongKaiActivity.this,ListGongkainianbaoActivity.class));
                break;
            case R.id.important_area://重点领域
                startActivity(new Intent(ZhengWuGongKaiActivity.this,KeyAreaActivity.class));
                break;
            default:
                break;
        }
    }



}
