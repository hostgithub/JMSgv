package com.cn.gov.jms.model;

import java.io.Serializable;
import java.util.List;

/**
 * Created by wangjiawei on 2017-9-28.
 */

public class Search implements Serializable{

    private boolean success;
    public List<Search.ResultsBean> results;

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public class ResultsBean implements Serializable {

        public String title;
        public String _id;
        public String addTime;
        public String text;
    }
}
