package com.cn.gov.jms.services;

import com.cn.gov.jms.model.Banners;
import com.cn.gov.jms.model.DataInfo;
import com.cn.gov.jms.model.Detail;
import com.cn.gov.jms.model.NewCenter;
import com.cn.gov.jms.model.PublicNotice;

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

    //http://192.168.0.110:8080/app/corNewsListImg.do   获取轮播图json
    @POST("/app/corNewsListImg.do")
    Call<Banners> getBannerData();

    //http://192.168.0.122:8080//app/corGetById.do?id=103558   获取轮播图详情json
    @GET("/app/corGetById.do")
    Call<Detail> getDetailData(@Query("id") int id);

    //http://192.168.0.124:8080/app/corTabloid.do?id=000100050007   获取新闻中心列表json  固定ID
    @GET("/app/corTabloid.do")
    Call<NewCenter> getNewCenterData(@Query("id") String id,@Query("pages") int pages);

    //http://192.168.0.124:8080/app/publicNotice.do?id=000100050009  获取公告公示列表json  固定ID
    @GET("/app/publicNotice.do")
    Call<PublicNotice> getPublicNoticeData(@Query("id") String id, @Query("pages") int pages);

    //http://192.168.0.122:8080/app/singlePageData.do?id=103558   获取市情概况json
    //@GET("/app/singlePageData.do")
    //Call<Detail> getDetailData(@Query("id") int id);
}
