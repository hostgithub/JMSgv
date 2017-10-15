package com.cn.gov.jms.model;

import java.io.Serializable;
import java.util.List;

/**
 * Created by wangjiawei on 2017-10-15.
 */

public class ApplyPublicDetail implements Serializable{

    /**
     * results : [{"name":"刘**","company_name":"","business_license":"","legal_representative":"","organization_code":"","work":"个*","identity_number":"2*****************","address":"佳****","code":"1*****","iphone":"1**********","email":"1****************","fax":"","dept_name":"环境保护局","createTime":"2017-02-27 22:18:06","updateTime":"2017-03-06 16:38:02","appointstr":"电子邮件，","obtainstr":"电子邮件，","handle_time":"2017-03-06 16:38:02","acceptstr":"接收其他方式","isFree":"申请","state1str":"同意公开","identity_typestr":"身份证","typestr":"公民","title":"我想建一个小型养殖场","content1":"我想建一个一万只鸡的小型养殖场需要到环保局办理吗？","handle_name":"环境保护局","handle_content1":"按照《中华人民共和国环境保护法》有关规定，新、改、扩建项目必须进行环境影响评价，该建设项目属于新项目应该到当地环保部门提出环评申请，然后再委托具有相应资质的环境影响评价机构对建设项目进行环境影响评价，最后到环保部门进行审批。建设项目环境影响评价的目的，是为了避免和减少新建项目对周围环境造成污染，同时通过环境影响评价提出建设项目产生污染的治理方案和措施，最终实现经济、社会和环境协调发展。此项目需进行建设项目环境影响登记表备案，并且要求与居民等敏感点设置500米卫生防护距离。\n建设项目环境影响登记表备案系统网址?:\n?http://221.212.115.5:7080/REG??(建议用360极速版浏览器)","purpose":"办理相关手续"}]
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

    public static class ResultsBean implements Serializable{
        /**
         * name : 刘**
         * company_name :
         * business_license :
         * legal_representative :
         * organization_code :
         * work : 个*
         * identity_number : 2*****************
         * address : 佳****
         * code : 1*****
         * iphone : 1**********
         * email : 1****************
         * fax :
         * dept_name : 环境保护局
         * createTime : 2017-02-27 22:18:06
         * updateTime : 2017-03-06 16:38:02
         * appointstr : 电子邮件，
         * obtainstr : 电子邮件，
         * handle_time : 2017-03-06 16:38:02
         * acceptstr : 接收其他方式
         * isFree : 申请
         * state1str : 同意公开
         * identity_typestr : 身份证
         * typestr : 公民
         * title : 我想建一个小型养殖场
         * content1 : 我想建一个一万只鸡的小型养殖场需要到环保局办理吗？
         * handle_name : 环境保护局
         * handle_content1 : 按照《中华人民共和国环境保护法》有关规定，新、改、扩建项目必须进行环境影响评价，该建设项目属于新项目应该到当地环保部门提出环评申请，然后再委托具有相应资质的环境影响评价机构对建设项目进行环境影响评价，最后到环保部门进行审批。建设项目环境影响评价的目的，是为了避免和减少新建项目对周围环境造成污染，同时通过环境影响评价提出建设项目产生污染的治理方案和措施，最终实现经济、社会和环境协调发展。此项目需进行建设项目环境影响登记表备案，并且要求与居民等敏感点设置500米卫生防护距离。
         建设项目环境影响登记表备案系统网址?:
         ?http://221.212.115.5:7080/REG??(建议用360极速版浏览器)
         * purpose : 办理相关手续
         */

        private String name;
        private String company_name;
        private String business_license;
        private String legal_representative;
        private String organization_code;
        private String work;
        private String identity_number;
        private String address;
        private String code;
        private String iphone;
        private String email;
        private String fax;
        private String dept_name;
        private String createTime;
        private String updateTime;
        private String appointstr;
        private String obtainstr;
        private String handle_time;
        private String acceptstr;
        private String isFree;
        private String state1str;
        private String identity_typestr;
        private String typestr;
        private String title;
        private String content1;
        private String handle_name;
        private String handle_content1;
        private String purpose;

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getCompany_name() {
            return company_name;
        }

        public void setCompany_name(String company_name) {
            this.company_name = company_name;
        }

        public String getBusiness_license() {
            return business_license;
        }

        public void setBusiness_license(String business_license) {
            this.business_license = business_license;
        }

        public String getLegal_representative() {
            return legal_representative;
        }

        public void setLegal_representative(String legal_representative) {
            this.legal_representative = legal_representative;
        }

        public String getOrganization_code() {
            return organization_code;
        }

        public void setOrganization_code(String organization_code) {
            this.organization_code = organization_code;
        }

        public String getWork() {
            return work;
        }

        public void setWork(String work) {
            this.work = work;
        }

        public String getIdentity_number() {
            return identity_number;
        }

        public void setIdentity_number(String identity_number) {
            this.identity_number = identity_number;
        }

        public String getAddress() {
            return address;
        }

        public void setAddress(String address) {
            this.address = address;
        }

        public String getCode() {
            return code;
        }

        public void setCode(String code) {
            this.code = code;
        }

        public String getIphone() {
            return iphone;
        }

        public void setIphone(String iphone) {
            this.iphone = iphone;
        }

        public String getEmail() {
            return email;
        }

        public void setEmail(String email) {
            this.email = email;
        }

        public String getFax() {
            return fax;
        }

        public void setFax(String fax) {
            this.fax = fax;
        }

        public String getDept_name() {
            return dept_name;
        }

        public void setDept_name(String dept_name) {
            this.dept_name = dept_name;
        }

        public String getCreateTime() {
            return createTime;
        }

        public void setCreateTime(String createTime) {
            this.createTime = createTime;
        }

        public String getUpdateTime() {
            return updateTime;
        }

        public void setUpdateTime(String updateTime) {
            this.updateTime = updateTime;
        }

        public String getAppointstr() {
            return appointstr;
        }

        public void setAppointstr(String appointstr) {
            this.appointstr = appointstr;
        }

        public String getObtainstr() {
            return obtainstr;
        }

        public void setObtainstr(String obtainstr) {
            this.obtainstr = obtainstr;
        }

        public String getHandle_time() {
            return handle_time;
        }

        public void setHandle_time(String handle_time) {
            this.handle_time = handle_time;
        }

        public String getAcceptstr() {
            return acceptstr;
        }

        public void setAcceptstr(String acceptstr) {
            this.acceptstr = acceptstr;
        }

        public String getIsFree() {
            return isFree;
        }

        public void setIsFree(String isFree) {
            this.isFree = isFree;
        }

        public String getState1str() {
            return state1str;
        }

        public void setState1str(String state1str) {
            this.state1str = state1str;
        }

        public String getIdentity_typestr() {
            return identity_typestr;
        }

        public void setIdentity_typestr(String identity_typestr) {
            this.identity_typestr = identity_typestr;
        }

        public String getTypestr() {
            return typestr;
        }

        public void setTypestr(String typestr) {
            this.typestr = typestr;
        }

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public String getContent1() {
            return content1;
        }

        public void setContent1(String content1) {
            this.content1 = content1;
        }

        public String getHandle_name() {
            return handle_name;
        }

        public void setHandle_name(String handle_name) {
            this.handle_name = handle_name;
        }

        public String getHandle_content1() {
            return handle_content1;
        }

        public void setHandle_content1(String handle_content1) {
            this.handle_content1 = handle_content1;
        }

        public String getPurpose() {
            return purpose;
        }

        public void setPurpose(String purpose) {
            this.purpose = purpose;
        }
    }
}
