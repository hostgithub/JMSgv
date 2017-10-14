package com.cn.gov.jms.model;

import java.util.List;

/**
 * Created by wangjiawei on 2017-10-14.
 */

public class OnlineTalk {

    /**
     * results : [{"_id":"19","upId":"1_1411110794421","title":"工伤认定及工伤保险新政解读","quest":"市人社局副局长、医保局局长朱智辉","compere":"任燕","addTime":"null"},{"_id":"18","upId":"1_1409899502301","title":"关注建设项目环保行政审批提质提速","quest":"佳木斯市环境保护局副局长王佳建","compere":"任燕","addTime":"null"},{"_id":"17","upId":"1_1409194823207","title":"2014年佳木斯市区中小学阳光招生访谈","quest":"佳木斯市教育局副局长阚学章","compere":"任燕","addTime":"null"},{"_id":"16","upId":"1_1405565213697","title":"佳木斯市青年创业助航计划访谈","quest":"佳木斯团市委副书记钟华","compere":"任燕","addTime":"null"},{"_id":"15","upId":"1_1403229086380","title":"2014年高考志愿填报解读","quest":"佳木斯市招生办副主任张力庆","compere":"任燕","addTime":"null"},{"_id":"14","upId":"1_1402641148577","title":"佳木斯市城乡低保政策解读","quest":"佳木斯市最低生活保障管理局副局长 张文彬","compere":"任燕","addTime":"null"},{"_id":"13","upId":"1_1401255323188","title":"单独两孩生育政策解读","quest":"佳木斯市人口和计划生育委员会副主任 陈宏伟","compere":"任燕","addTime":"null"},{"_id":"12","upId":"1_1399876090174","title":"全科医生（家庭医生团队）签约服务","quest":"佳木斯市卫生局副局长 张立斌","compere":"任燕","addTime":"null"},{"_id":"11","upId":"1_1399523671262","title":"关注\u201c两票\u201d 关注便民办税春风行动","quest":"佳木斯市地方税务局总会计师 邓春明","compere":"任燕","addTime":"null"},{"_id":"10","upId":"1_1398308131184","title":"注册资本登记制度改革","quest":"佳木斯市工商局企业分局局长 王国伟","compere":"任燕","addTime":"null"},{"_id":"9","upId":"1_1398217323970","title":"关注饮食业发票 关注便民办税春风行动","quest":"佳木斯市地方税务局副局长 兰卫东","compere":"任燕","addTime":"null"},{"_id":"8","upId":"1_1397616380362","title":"车辆购置税相关政策解读","quest":"佳木斯市国税局车辆购置税征收管理分局局长 刘德明","compere":"任燕","addTime":"null"},{"_id":"7","upId":"1_1395976257863","title":"生源地助学贷款","quest":"佳木市教育局学生资助管理中心主任 华正崴","compere":"任燕","addTime":"null"},{"_id":"6","upId":"1_1393999282679","title":"调整住房公积金提取政策利民惠民","quest":"市住房公积金管理中心办事处主任李珊珊","compere":"盛丽冰","addTime":"null"},{"_id":"5","upId":"1_1392273508554","title":"如何认识和预防甲型H1N1流感和人感染H7N9禽流感","quest":"市疾病预防控制中心副主任包名家","compere":"刘军","addTime":"null"}]
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
         * _id : 19
         * upId : 1_1411110794421
         * title : 工伤认定及工伤保险新政解读
         * quest : 市人社局副局长、医保局局长朱智辉
         * compere : 任燕
         * addTime : null
         */

        private String _id;
        private String upId;
        private String title;
        private String quest;
        private String compere;
        private String addTime;

        public String get_id() {
            return _id;
        }

        public void set_id(String _id) {
            this._id = _id;
        }

        public String getUpId() {
            return upId;
        }

        public void setUpId(String upId) {
            this.upId = upId;
        }

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public String getQuest() {
            return quest;
        }

        public void setQuest(String quest) {
            this.quest = quest;
        }

        public String getCompere() {
            return compere;
        }

        public void setCompere(String compere) {
            this.compere = compere;
        }

        public String getAddTime() {
            return addTime;
        }

        public void setAddTime(String addTime) {
            this.addTime = addTime;
        }
    }
}
