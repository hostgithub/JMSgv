package com.cn.gov.jms.model;

import java.io.Serializable;
import java.util.List;

/**
 * Created by wangjiawei on 2017-9-22.
 */

public class Gongzuonianbao implements Serializable{

    private boolean success;
    private List<Gongzuonianbao.ResultsBean> results;

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public List<Gongzuonianbao.ResultsBean> getResults() {
        return results;
    }

    public void setResults(List<Gongzuonianbao.ResultsBean> results) {
        this.results = results;
    }

    public static class ResultsBean implements Serializable {

        public String _id;
        public String title;
        public String addTime;
    }
}
