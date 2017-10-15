package com.cn.gov.jms.model;

import java.util.List;

/**
 * Created by wangjiawei on 2017-10-15.
 */

public class ApplyPublic {


    /**
     * results : [{"id":"95","name":"杨*","work":"医*","title":"申请公开《2017年佳木斯小学升初中招生工作方案》","createTime":"2017-09-26 16:44:27.63","handleTime":"2017-10-09 10:58:34.503","state1str":"同意公开"},{"id":"94","name":"王**","work":"培***","title":"高中毕业证丢失如何补办？","createTime":"2017-09-25 14:59:51.83","handleTime":"2017-10-09 10:44:57.567","state1str":"同意公开"},{"id":"93","name":"谭*","work":"无*","title":"佳木斯市友谊路南近乡街棚户区改造项目信息","createTime":"2017-08-13 21:33:26.42","handleTime":"2017-08-21 16:04:55.35","state1str":"非本部门掌握 "},{"id":"92","name":"刘*","work":"无","title":"公开环路收费费用使用明细","createTime":"2017-08-08 15:30:58.5","handleTime":"2017-08-11 09:21:48.373","state1str":"非本部门掌握 "},{"id":"91","name":"晋**","work":"兰*********","title":"浏览量","createTime":"2017-07-10 21:51:07.52","handleTime":"2017-07-11 10:00:31.947","state1str":"同意公开"},{"id":"90","name":"张**","work":"枣*********","title":"行政处罚信息","createTime":"2017-06-22 17:20:14.767","handleTime":"2017-06-29 15:44:27.607","state1str":"同意公开"},{"id":"89","name":"张**","work":"上********","title":"公开办学许可证的相关信息","createTime":"2017-06-08 16:18:20.687","handleTime":"2017-06-21 15:32:38.677","state1str":"同意公开"},{"id":"88","name":"徐*","work":"成**************","title":"审计局2016年预算支出及人员数等相关信息","createTime":"2017-04-13 17:36:33.853","handleTime":"2017-04-18 14:42:24.127","state1str":"同意公开"},{"id":"87","name":"马**","work":"青***","title":"人口、法人基础数据库建设","createTime":"2017-04-13 08:40:24.677","handleTime":"2017-04-24 16:40:00.427","state1str":"申请内容不明确"},{"id":"86","name":"马**","work":"青***","title":"公共视频整合","createTime":"2017-04-12 18:11:39.887","handleTime":"2017-05-03 10:21:07.3","state1str":"部分公开"},{"id":"85","name":"马**","work":"青***","title":"商务信用平台","createTime":"2017-04-12 16:56:08.203","handleTime":"2017-04-13 16:33:17.947","state1str":"同意公开"},{"id":"84","name":"马**","work":"青***","title":"一站式办理率","createTime":"2017-04-12 15:45:40.597","handleTime":"2017-04-13 15:50:13.303","state1str":"同意公开"},{"id":"83","name":"马**","work":"青***","title":"每万人应有社会组织数","createTime":"2017-04-12 14:35:31.74","handleTime":"2017-04-17 14:10:56.647","state1str":"同意公开"},{"id":"82","name":"张**","work":"佳****","title":"城市生活垃圾处理调研","createTime":"2017-03-02 10:47:05.503","handleTime":"2017-03-08 10:01:31.457","state1str":"非本部门掌握 "},{"id":"81","name":"刘**","work":"个*","title":"我想建一个小型养殖场","createTime":"2017-02-27 22:18:06.607","handleTime":"2017-03-06 16:38:02.973","state1str":"同意公开"}]
     * success : true
     */

    private boolean success;
    private List<ResultsBean> results;

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public List<ResultsBean> getResults() {
        return results;
    }

    public void setResults(List<ResultsBean> results) {
        this.results = results;
    }

    public static class ResultsBean {
        /**
         * id : 95
         * name : 杨*
         * work : 医*
         * title : 申请公开《2017年佳木斯小学升初中招生工作方案》
         * createTime : 2017-09-26 16:44:27.63
         * handleTime : 2017-10-09 10:58:34.503
         * state1str : 同意公开
         */

        private String id;
        private String name;
        private String work;
        private String title;
        private String createTime;
        private String handleTime;
        private String state1str;

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getWork() {
            return work;
        }

        public void setWork(String work) {
            this.work = work;
        }

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public String getCreateTime() {
            return createTime;
        }

        public void setCreateTime(String createTime) {
            this.createTime = createTime;
        }

        public String getHandleTime() {
            return handleTime;
        }

        public void setHandleTime(String handleTime) {
            this.handleTime = handleTime;
        }

        public String getState1str() {
            return state1str;
        }

        public void setState1str(String state1str) {
            this.state1str = state1str;
        }
    }
}
