package com.cn.gov.jms.services;

import com.cn.gov.jms.model.AboutAndContact;
import com.cn.gov.jms.model.Banners;
import com.cn.gov.jms.model.DataInfo;
import com.cn.gov.jms.model.Detail;
import com.cn.gov.jms.model.Gongzuonianbao;
import com.cn.gov.jms.model.NewCenter;
import com.cn.gov.jms.model.PublicNotice;
import com.cn.gov.jms.model.ResponseBean;
import com.cn.gov.jms.model.Sqgk;
import com.cn.gov.jms.model.SqgkDetail;

import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Headers;
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

    //http://192.168.0.124:8080/app/corTabloid.do?id=000100050007&pages=1   获取新闻中心列表json  固定ID
    @GET("/app/corTabloid.do")
    Call<NewCenter> getNewCenterData(@Query("id") String id,@Query("pages") int pages);

    //http://192.168.0.124:8080/app/publicNotice.do?id=000100050009&pages=1  获取公告公示列表json  固定ID
    @GET("/app/publicNotice.do")
    Call<PublicNotice> getPublicNoticeData(@Query("id") String id, @Query("pages") int pages);


    //http://192.168.0.124:8080/app/corGetzwgkData.do?id=000100020022&pages=1  获取政务公开列表json  固定ID
    @GET("/app/corGetzwgkData.do")
    Call<PublicNotice> getZhengwuPublicData(@Query("id") String id, @Query("pages") int pages);

    @GET("/app/corGetzwgkData.do")
    Call<Gongzuonianbao> getGongzuonianbaoData(@Query("id") String id, @Query("pages") int pages);

    //http://192.168.0.122:8080/app/singlePageData.do   获取市情概况json
    @GET("/app/singlePageData.do")
    Call<Sqgk> getSqgkData();

    //http://192.168.0.130:8080/app/singlePageById.do?id=2  市情概况里面每个模块的详情信息json   id=568    578 关于我们与联系我们 详情信息json
    @GET("/app/singlePageById.do")
    Call<SqgkDetail> getSqgkDetailData(@Query("id") int id);

    //http://192.168.0.122:8080/app/singlePageManData.do  关于我们与联系我们json
    @GET("/app/singlePageManData.do")
    Call<AboutAndContact> getAboutAndContactData();

    //http://192.168.0.130:8080/app/fuzzyQuery.do?id=000100020004&pages=1   人事信息  法规公文
    @GET("/app/fuzzyQuery.do")
    Call<Gongzuonianbao> getPersonThingInfoData(@Query("id") String id, @Query("pages") int pages);

    //http://192.168.0.130:8080/app/corGetzwgkData.do?id=000100020012&pages=1   民生工程  直属机构
    @GET("/app/corGetzwgkData.do")
    Call<Gongzuonianbao> getMinShengData(@Query("id") String id, @Query("pages") int pages);

    //http://192.168.0.130:8080/app/keyAreas.do?pages=1   民生工程  直属机构
    @GET("/app/keyAreas.do")
    Call<Gongzuonianbao> getKeyAreasData(@Query("pages") int pages);

    @Headers({"Content-Type: application/json","Accept: application/json"})//需要添加头
    @POST("app/receiveData.do")
    Call<ResponseBean> postFlyRoute(@Body RequestBody route);//传入的参数为RequestBody
}
