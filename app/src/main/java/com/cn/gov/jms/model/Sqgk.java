package com.cn.gov.jms.model;

import java.io.Serializable;
import java.util.List;

/**
 * Created by wangjiawei on 2017-9-22.
 */

public class Sqgk implements Serializable{

    private boolean success;
    private List<Sqgk.ResultsBean> results;

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public List<Sqgk.ResultsBean> getResults() {
        return results;
    }

    public void setResults(List<Sqgk.ResultsBean> results) {
        this.results = results;
    }

    public static class ResultsBean implements Serializable {

        public String id;
        public String title;
        public String text;
    }
}
