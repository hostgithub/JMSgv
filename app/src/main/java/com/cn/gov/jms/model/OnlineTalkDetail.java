package com.cn.gov.jms.model;

import java.io.Serializable;
import java.util.List;

/**
 * Created by wangjiawei on 2017-10-14.
 */

public class OnlineTalkDetail implements Serializable{


    /**
     * results : [{"title":"整合城乡医保 落实惠民新政","deptName":"null","quest":"佳木斯市医疗保险管理局副局长 刘黎光","compere":"任燕","subjectTime":"null","picName":"data/interview/1502682129528.jpg","videoName":"null","content":null}]
     * host : [{"hostSaid":"各位网友，今天的访谈就到这里，再次感谢您，刘局长。"}]
     * answer : [{"questionAnswering":"一是全面加强业务经办。9月底前，要明确机构编制、配齐配强经办力量，落实场所、经费、办公设备。要完成经办管理服务流程制定、原新农合基金财政专户与城镇居民医保基金财政专户的归并、定点医药机构梳理和协议签订工作。二是尽快统一信息系统。全省统一的信息系统平台已在搭建，我市已向省提出信息系统本地化需求，8月份完成城乡居民医保数据初始化工作，近期组织市、县、乡、村各级配齐信息系统运行所需的硬件设备，并进行信息系统试运行。三是加强基金收支监管。年内，要结合社会保险经办风险管理专项行动和医疗保险基金审计情况，对基金收支管理、定点医药机构服务行为等情况开展全面自查和检查，及时发现问题，整改问题。要在医疗费用审核、结算等工作中充分利用医保智能监控系统，确保城乡居民医保基金安全平稳运行。"}]
     * success : true
     */

    private boolean success;
    private List<ResultsBean> results;
    private List<HostBean> host;
    private List<AnswerBean> answer;

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

    public List<HostBean> getHost() {
        return host;
    }

    public void setHost(List<HostBean> host) {
        this.host = host;
    }

    public List<AnswerBean> getAnswer() {
        return answer;
    }

    public void setAnswer(List<AnswerBean> answer) {
        this.answer = answer;
    }

    public static class ResultsBean implements Serializable{
        /**
         * title : 整合城乡医保 落实惠民新政
         * deptName : null
         * quest : 佳木斯市医疗保险管理局副局长 刘黎光
         * compere : 任燕
         * subjectTime : null
         * picName : data/interview/1502682129528.jpg
         * videoName : null
         * content : null
         */

        private String title;
        private String deptName;
        private String quest;
        private String compere;
        private String subjectTime;
        private String picName;
        private String videoName;
        private Object content;

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public String getDeptName() {
            return deptName;
        }

        public void setDeptName(String deptName) {
            this.deptName = deptName;
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

        public String getSubjectTime() {
            return subjectTime;
        }

        public void setSubjectTime(String subjectTime) {
            this.subjectTime = subjectTime;
        }

        public String getPicName() {
            return picName;
        }

        public void setPicName(String picName) {
            this.picName = picName;
        }

        public String getVideoName() {
            return videoName;
        }

        public void setVideoName(String videoName) {
            this.videoName = videoName;
        }

        public Object getContent() {
            return content;
        }

        public void setContent(Object content) {
            this.content = content;
        }
    }

    public static class HostBean implements Serializable{
        /**
         * hostSaid : 各位网友，今天的访谈就到这里，再次感谢您，刘局长。
         */

        private String hostSaid;

        public String getHostSaid() {
            return hostSaid;
        }

        public void setHostSaid(String hostSaid) {
            this.hostSaid = hostSaid;
        }
    }

    public static class AnswerBean implements Serializable{
        /**
         * questionAnswering : 一是全面加强业务经办。9月底前，要明确机构编制、配齐配强经办力量，落实场所、经费、办公设备。要完成经办管理服务流程制定、原新农合基金财政专户与城镇居民医保基金财政专户的归并、定点医药机构梳理和协议签订工作。二是尽快统一信息系统。全省统一的信息系统平台已在搭建，我市已向省提出信息系统本地化需求，8月份完成城乡居民医保数据初始化工作，近期组织市、县、乡、村各级配齐信息系统运行所需的硬件设备，并进行信息系统试运行。三是加强基金收支监管。年内，要结合社会保险经办风险管理专项行动和医疗保险基金审计情况，对基金收支管理、定点医药机构服务行为等情况开展全面自查和检查，及时发现问题，整改问题。要在医疗费用审核、结算等工作中充分利用医保智能监控系统，确保城乡居民医保基金安全平稳运行。
         */

        private String questionAnswering;

        public String getQuestionAnswering() {
            return questionAnswering;
        }

        public void setQuestionAnswering(String questionAnswering) {
            this.questionAnswering = questionAnswering;
        }
    }
}
