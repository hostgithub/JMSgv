package com.cn.gov.jms.services;

import com.cn.gov.jms.model.AboutAndContact;
import com.cn.gov.jms.model.ApplyPublic;
import com.cn.gov.jms.model.ApplyPublicDetail;
import com.cn.gov.jms.model.Banners;
import com.cn.gov.jms.model.DataInfo;
import com.cn.gov.jms.model.DeptBean;
import com.cn.gov.jms.model.Detail;
import com.cn.gov.jms.model.Gongzuonianbao;
import com.cn.gov.jms.model.NewCenter;
import com.cn.gov.jms.model.OnlineTalk;
import com.cn.gov.jms.model.OnlineTalkDetail;
import com.cn.gov.jms.model.PublicGuideBean;
import com.cn.gov.jms.model.PublicGuideDetailBean;
import com.cn.gov.jms.model.PublicNotice;
import com.cn.gov.jms.model.ResponseBean;
import com.cn.gov.jms.model.Search;
import com.cn.gov.jms.model.Sqgk;
import com.cn.gov.jms.model.SqgkDetail;
import com.cn.gov.jms.model.Video;
import com.cn.gov.jms.model.ZixunFanyingDetailBean;

import java.util.Map;

import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.FieldMap;
import retrofit2.http.GET;
import retrofit2.http.Headers;
import retrofit2.http.POST;
import retrofit2.http.Part;
import retrofit2.http.Path;
import retrofit2.http.Query;
import retrofit2.http.Url;

/**
 * Created by Administrator on 2016/6/25.
 */
public interface Api {    //retrofit方式
    //http://gank.io/api/data/福利/6/1
    @GET("api/data/福利/{pageCount}/{pageIndex}")
    Call<DataInfo> getData(@Path("pageCount") int pageCount,
                           @Path("pageIndex") int pageIndex);

    //http://192.168.0.110:8080/app/corNewsListImg.do   获取轮播图json
    @POST("app/corNewsListImg.do")
    Call<Banners> getBannerData();

    //http://192.168.0.122:8080//app/corGetById.do?id=103558   获取轮播图详情json
    @GET("app/corGetById.do")
    Call<Detail> getDetailData(@Query("id") int id);

    //http://192.168.0.124:8080/app/corTabloid.do?id=000100050007&pages=1   获取新闻中心列表json  固定ID
    @GET("app/corTabloid.do")
    Call<NewCenter> getNewCenterData(@Query("id") String id,@Query("pages") int pages);

    //http://192.168.0.124:8080/app/publicNotice.do?id=000100050009&pages=1  获取公告公示列表json  固定ID
    @GET("app/publicNotice.do")
    Call<PublicNotice> getPublicNoticeData(@Query("id") String id, @Query("pages") int pages);

    //http://192.168.0.122:8080/app/singlePageData.do   获取市情概况json
    @GET("app/singlePageData.do")
    Call<Sqgk> getSqgkData();

    //http://192.168.0.117:8080/app/getTypeAndDept.do 部门
    @GET("app/getTypeAndDept.do")
    Call<DeptBean> getTypeAndDeptData();

    //http://192.168.0.130:8080/app/singlePageById.do?id=2  市情概况里面每个模块的详情信息json   id=568    578 关于我们与联系我们 详情信息json
    @GET("app/singlePageById.do")
    Call<SqgkDetail> getSqgkDetailData(@Query("id") int id);

    //http://192.168.0.122:8080/app/singlePageManData.do  关于我们与联系我们json
    @GET("app/singlePageManData.do")
    Call<AboutAndContact> getAboutAndContactData();

    //http://192.168.0.130:8080/app/fuzzyQuery.do?id=000100020004&pages=1   人事信息  法规公文
    @GET("app/fuzzyQuery.do")
    Call<Gongzuonianbao> getPersonThingInfoData(@Query("id") String id, @Query("pages") int pages);

    //http://192.168.0.130:8080/app/corGetzwgkData.do?id=000100020012&pages=1   民生工程  直属机构  热点回应
    @GET("app/corGetzwgkData.do")
    Call<Gongzuonianbao> getMinShengData(@Query("id") String id, @Query("pages") int pages);

    //http://192.168.0.130:8080/app/keyAreas.do?pages=1   民生工程  直属机构
    @GET("app/keyAreas.do")
    Call<Gongzuonianbao> getKeyAreasData(@Query("pages") int pages);

    @Headers({"Content-Type: application/json","Accept: application/json"})//需要添加头
    @POST("app/receiveData.do")
    Call<ResponseBean> postFlyRoute(@Body RequestBody route);//传入的参数为RequestBody

    //http://192.168.0.137:8080/app/getSomeSuggestions.do?pages=1  进言献策列表
    @GET("app/getSomeSuggestions.do")
    Call<PublicNotice> getSomeSuggestionsData(@Query("pages") int pages);

    //http://192.168.0.137:8080/app/getSomeSuggestions.do?pages=1  进言献策详情
    @GET("app/getByIdSome.do")
    Call<Detail> getgetByIdSomeData(@Query("id") int id);

    @Headers({"Content-Type: application/json","Accept: application/json"})//需要添加头  咨询问题和反映问题
    @POST("app/addProblem.do")
    Call<ResponseBean> addProblem(@Body RequestBody route);//传入的参数为RequestBody

    @POST()
    Call<ResponseBean> upLoadTextAndPic(  //动态的Url  图文
            @Url() String url,
            @FieldMap Map<String , String> map,
            @Part("avatar\"; filename=\"avatar.jpg") RequestBody avatar);

    //http://192.168.0.130:8080/app/consultingProblem.do?id=10030001&pages=1   咨询问题和反映问题列表
    @GET("app/consultingProblem.do")
    Call<Gongzuonianbao> getZixunProblemData(@Query("id") String id,@Query("pages") int pages);

    //http://192.168.0.137:8080/app/getByIdroc.do?id=7621  咨询问题详情
    @GET("app/getByIdroc.do")
    Call<ZixunFanyingDetailBean> getByIdrocData(@Query("id") int id);


    //http://192.168.0.130:8080/app/corGetzwgkData.do?id=000100020012&pages=1   保存意见
    @GET("app/saveOpinions.do")
    Call<ResponseBean> getSaveOpinionsData(@Query("id") String id, @Query("pj") String pj);

    @GET("app/fullTextRetrieval.do")
    Call<Search> getSearchData(@Query("text") String text, @Query("pages") int pages);//搜索列表


    @POST("app/uplodImg.do")
    Call<ResponseBean> upload(@Query("image") String image, @Query("filename") String filename);//上传文件


    //http://192.168.0.115:8080/app/guidelinesAndReports.do?type=2&pages=1  公开制度列表  年报
    @GET("app/guidelinesAndReports.do")
    Call<Gongzuonianbao> getZhiDuData(@Query("type") String type, @Query("pages") int pages);

    @GET("app/guideAndReById.do")                           //详情页
    Call<Detail> getGuideAndReByIdData(@Query("id") int id);


    @GET("app/corGetzwgkData.do")
    Call<Gongzuonianbao> getGongzuonianbaoData(@Query("id") String id, @Query("pages") int pages);//政府文件

    @GET("app/publicBriefing.do")
    Call<Gongzuonianbao> getPublicBriefingData(@Query("pages") int pages);//工作简报

    @GET("app/briefingById.do")                           //工作简报详情页
    Call<Detail> getBriefingByIdData(@Query("id") int id);


    @GET("app/publicDirectory.do")
    Call<Gongzuonianbao> getPublicDirectoryData(@Query("pages") int pages);//公开目录

    @GET("app/directoryById.do")                           //公开目录详情页
    Call<Detail> getDirectoryByIdData(@Query("id") int id);


    @GET("app/getDepartmentAll.do")
    Call<PublicGuideBean> getPublicGuideData();//公开指南

    @GET("app/publicGuide.do")                           //公开指南详情页
    Call<PublicGuideDetailBean> getGuideBySourceData(@Query("source") int source,@Query("genre") int genre);

    //http://192.168.0.122:8080/app/leadershipWindow.do  领导之窗分类json
    @GET("app/leadershipWindow.do")
    Call<AboutAndContact> getLeaderOfWindowData();

    @GET("app/leadershipWindowList.do")            //领导之窗列表json
    Call<AboutAndContact> getLeaderOfWindowListData(@Query("id") String id);


    @GET("app/mayorSecretary.do")//书记 市长
    Call<SqgkDetail> getMayorSecretaryData(@Query("name") String name);


    @GET("client/interview/show.do")            //服务器 在线访谈 视频  测试json
    Call<Video> getInterviewData(@Query("id") int id,@Query("upId") String upId);

    @GET("app/interview.do")            //在线访谈 列表
    Call<OnlineTalk> getInterviewListData(@Query("pages") int pages);

    @GET("app/getViewByid.do")            //在线访谈 详情
    Call<OnlineTalkDetail> getOnlineTalkByIdData(@Query("id") int id, @Query("upId") String upId);

    @GET("app/publicApplication.do")            //依申请公开 列表
    Call<ApplyPublic> getPublicApplicationData(@Query("pages") int pages);

    @GET("app/getPublicById.do")            //依申请公开 列表详情
    Call<ApplyPublicDetail> getApplyPublicDetailData(@Query("id") int id);


    @GET("app/corGetzfwjData.do")            //新的政府文件
    Call<Gongzuonianbao> getNewGovFileData(@Query("id") String id, @Query("pages") int pages);

    @GET("app/singlezfwjById.do")
    Call<Detail> getNewDetailData(@Query("id") int id);
}
