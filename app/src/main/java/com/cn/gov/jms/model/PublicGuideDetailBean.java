package com.cn.gov.jms.model;

import java.io.Serializable;
import java.util.List;

/**
 * Created by wangjiawei on 2017-9-27.
 */

public class PublicGuideDetailBean implements Serializable{
    private boolean success;
    private List<PublicGuideDetailBean.ResultsBean> results;

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public List<PublicGuideDetailBean.ResultsBean> getResults() {
        return results;
    }

    public void setResults(List<PublicGuideDetailBean.ResultsBean> results) {
        this.results = results;
    }

    public static class ResultsBean implements Serializable {

        public String title;
        public String content;
        public String userName;
        public String addTime;
    }
}
