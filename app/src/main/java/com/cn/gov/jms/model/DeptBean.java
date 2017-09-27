package com.cn.gov.jms.model;

import java.io.Serializable;
import java.util.List;

/**
 * Created by wangjiawei on 2017-9-27.
 */

public class DeptBean implements Serializable{
    private boolean success;
    private List<DeptBean.ResultsBean> results;

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public List<DeptBean.ResultsBean> getResults() {
        return results;
    }

    public void setResults(List<DeptBean.ResultsBean> results) {
        this.results = results;
    }

    public static class ResultsBean implements Serializable {

        public String deptId;
        public String deptName;
    }
}
