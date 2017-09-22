package com.cn.gov.jms.model;

import java.io.Serializable;
import java.util.List;

/**
 * Created by wangjiawei on 2017-9-22.
 */

public class SqgkDetail implements Serializable{

    private boolean success;
    private List<SqgkDetail.ResultsBean> results;

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public List<SqgkDetail.ResultsBean> getResults() {
        return results;
    }

    public void setResults(List<SqgkDetail.ResultsBean> results) {
        this.results = results;
    }

    public static class ResultsBean implements Serializable {

        public String title;
        public String content;
    }
}
