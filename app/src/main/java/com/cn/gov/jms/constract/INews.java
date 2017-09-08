package com.cn.gov.jms.constract;


import com.cn.gov.jms.model.Datas;

import java.util.List;

/**
 * Created by Administrator on 2016/12/5.
 */

public interface INews
{
    public interface Views
    {
        void showInfo(List<Datas.ResultBean.DataBean> news);

        void showError(String msg);
    }

    public interface presenter
    {
        void loadingDatasFromNet();//网络获取数据

        void loadingDatasFromCache();//缓存获取数据
    }
}
