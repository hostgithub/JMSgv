package com.cn.gov.jms.services;

import com.cn.gov.jms.model.Banners;
import com.cn.gov.jms.model.DataInfo;
import com.cn.gov.jms.model.Detail;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;
import retrofit2.http.Query;

/**
 * Created by Administrator on 2016/6/25.
 */
public interface Api {    //retrofit方式
    //http://gank.io/api/data/福利/6/1
    @GET("api/data/福利/{pageCount}/{pageIndex}")
    Call<DataInfo> getData(@Path("pageCount") int pageCount,
                           @Path("pageIndex") int pageIndex);

    //http://192.168.0.110:8080/app/corNewsListImg.do   获取json
    @POST("/app/corNewsListImg.do")
    Call<Banners> getBannerData();

    //http://192.168.0.122:8080//app/corGetById.do?id=103558   获取json
    @GET("/app/corGetById.do")
    Call<Detail> getDetailData(@Query("id") int id);
}
