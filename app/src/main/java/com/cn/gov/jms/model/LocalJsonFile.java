package com.cn.gov.jms.model;

import java.util.List;

/**
 * Created by wangjiawei on 2017-9-21.
 */

public class LocalJsonFile {


    private List<DynamicBean> dynamic;

    public List<DynamicBean> getDynamic() {
        return dynamic;
    }

    public void setDynamic(List<DynamicBean> dynamic) {
        this.dynamic = dynamic;
    }

    public static class DynamicBean {
        /**
         * username : 燕潇洒
         * createTime : 04-23 13:12
         * commentNum : 45
         * content : 喔喔喔
         * headIcon : http://img2.imgtn.bdimg.com/it/u=1576185143,2361770572&fm=214&gp=0.jpg
         * images : http://img4.imgtn.bdimg.com/it/u=3776739438,757564394&fm=214&gp=0.jpg
         * level : 公告
         * priseNum : 23
         */

        private String username;
        private String createTime;
        private String commentNum;
        private String content;
        private String headIcon;
        private String images;
        private String level;
        private String priseNum;

        public String getUsername() {
            return username;
        }

        public void setUsername(String username) {
            this.username = username;
        }

        public String getCreateTime() {
            return createTime;
        }

        public void setCreateTime(String createTime) {
            this.createTime = createTime;
        }

        public String getCommentNum() {
            return commentNum;
        }

        public void setCommentNum(String commentNum) {
            this.commentNum = commentNum;
        }

        public String getContent() {
            return content;
        }

        public void setContent(String content) {
            this.content = content;
        }

        public String getHeadIcon() {
            return headIcon;
        }

        public void setHeadIcon(String headIcon) {
            this.headIcon = headIcon;
        }

        public String getImages() {
            return images;
        }

        public void setImages(String images) {
            this.images = images;
        }

        public String getLevel() {
            return level;
        }

        public void setLevel(String level) {
            this.level = level;
        }

        public String getPriseNum() {
            return priseNum;
        }

        public void setPriseNum(String priseNum) {
            this.priseNum = priseNum;
        }
    }
}
