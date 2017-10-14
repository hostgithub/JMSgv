package com.cn.gov.jms.model;

/**
 * Created by wangjiawei on 2017-10-14.
 */

public class Video {

    /**
     * interview : {"addIp":"","addMan":"","addTime":{"date":14,"day":1,"hours":15,"minutes":6,"month":7,"nanos":437000000,"seconds":17,"time":1502694377437,"timezoneOffset":-480,"year":117},"compere":"任燕","createIp":"","createMan":"","createTime":null,"deptName":"佳木斯市医疗保险管理局","hasDeleted":"0","id":62,"logo":"data/interview/1502682129528.jpg","modifyDate":null,"modifyIp":"","modifyMan":"","quest":"佳木斯市医疗保险管理局副局长 刘黎光","state":"0","subject":"整合城乡医保 落实惠民新政","subjectTime":"2017年8月14日","summary":"整合城乡居民医保制度是党中央、国务院对完善社会保障制度建设作出的一项重要安排，习近平总书记和李克强总理对加快推进城乡医保制度整合，构建统筹城乡社会保障网，都有明确要求。省政府去年两次召开会议研究整合工作，并对全省推进整合工作进行全面动员部署，佳木斯市政府将这项工作列为2017年全面深化改革的重点任务，将在2018年1月1日全市范围内施行整合后的城乡医保政策。此次政务访谈将对相关政策进行解读。","upid":"1_1502681781008","videoName":"data/interview/video/1502682129544.mp4","videoUrl":"\r\n\r\n\r\n\r\n<\/embed><\/object>"}
     * success : true
     * basePath : http://221.210.9.87:8080/
     */

    private InterviewBean interview;
    private boolean success;
    private String basePath;

    public InterviewBean getInterview() {
        return interview;
    }

    public void setInterview(InterviewBean interview) {
        this.interview = interview;
    }

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public String getBasePath() {
        return basePath;
    }

    public void setBasePath(String basePath) {
        this.basePath = basePath;
    }

    public static class InterviewBean {
        /**
         * addIp :
         * addMan :
         * addTime : {"date":14,"day":1,"hours":15,"minutes":6,"month":7,"nanos":437000000,"seconds":17,"time":1502694377437,"timezoneOffset":-480,"year":117}
         * compere : 任燕
         * createIp :
         * createMan :
         * createTime : null
         * deptName : 佳木斯市医疗保险管理局
         * hasDeleted : 0
         * id : 62
         * logo : data/interview/1502682129528.jpg
         * modifyDate : null
         * modifyIp :
         * modifyMan :
         * quest : 佳木斯市医疗保险管理局副局长 刘黎光
         * state : 0
         * subject : 整合城乡医保 落实惠民新政
         * subjectTime : 2017年8月14日
         * summary : 整合城乡居民医保制度是党中央、国务院对完善社会保障制度建设作出的一项重要安排，习近平总书记和李克强总理对加快推进城乡医保制度整合，构建统筹城乡社会保障网，都有明确要求。省政府去年两次召开会议研究整合工作，并对全省推进整合工作进行全面动员部署，佳木斯市政府将这项工作列为2017年全面深化改革的重点任务，将在2018年1月1日全市范围内施行整合后的城乡医保政策。此次政务访谈将对相关政策进行解读。
         * upid : 1_1502681781008
         * videoName : data/interview/video/1502682129544.mp4
         * videoUrl :



         </embed></object>
         */

        private String addIp;
        private String addMan;
        private AddTimeBean addTime;
        private String compere;
        private String createIp;
        private String createMan;
        private Object createTime;
        private String deptName;
        private String hasDeleted;
        private int id;
        private String logo;
        private Object modifyDate;
        private String modifyIp;
        private String modifyMan;
        private String quest;
        private String state;
        private String subject;
        private String subjectTime;
        private String summary;
        private String upid;
        private String videoName;
        private String videoUrl;

        public String getAddIp() {
            return addIp;
        }

        public void setAddIp(String addIp) {
            this.addIp = addIp;
        }

        public String getAddMan() {
            return addMan;
        }

        public void setAddMan(String addMan) {
            this.addMan = addMan;
        }

        public AddTimeBean getAddTime() {
            return addTime;
        }

        public void setAddTime(AddTimeBean addTime) {
            this.addTime = addTime;
        }

        public String getCompere() {
            return compere;
        }

        public void setCompere(String compere) {
            this.compere = compere;
        }

        public String getCreateIp() {
            return createIp;
        }

        public void setCreateIp(String createIp) {
            this.createIp = createIp;
        }

        public String getCreateMan() {
            return createMan;
        }

        public void setCreateMan(String createMan) {
            this.createMan = createMan;
        }

        public Object getCreateTime() {
            return createTime;
        }

        public void setCreateTime(Object createTime) {
            this.createTime = createTime;
        }

        public String getDeptName() {
            return deptName;
        }

        public void setDeptName(String deptName) {
            this.deptName = deptName;
        }

        public String getHasDeleted() {
            return hasDeleted;
        }

        public void setHasDeleted(String hasDeleted) {
            this.hasDeleted = hasDeleted;
        }

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public String getLogo() {
            return logo;
        }

        public void setLogo(String logo) {
            this.logo = logo;
        }

        public Object getModifyDate() {
            return modifyDate;
        }

        public void setModifyDate(Object modifyDate) {
            this.modifyDate = modifyDate;
        }

        public String getModifyIp() {
            return modifyIp;
        }

        public void setModifyIp(String modifyIp) {
            this.modifyIp = modifyIp;
        }

        public String getModifyMan() {
            return modifyMan;
        }

        public void setModifyMan(String modifyMan) {
            this.modifyMan = modifyMan;
        }

        public String getQuest() {
            return quest;
        }

        public void setQuest(String quest) {
            this.quest = quest;
        }

        public String getState() {
            return state;
        }

        public void setState(String state) {
            this.state = state;
        }

        public String getSubject() {
            return subject;
        }

        public void setSubject(String subject) {
            this.subject = subject;
        }

        public String getSubjectTime() {
            return subjectTime;
        }

        public void setSubjectTime(String subjectTime) {
            this.subjectTime = subjectTime;
        }

        public String getSummary() {
            return summary;
        }

        public void setSummary(String summary) {
            this.summary = summary;
        }

        public String getUpid() {
            return upid;
        }

        public void setUpid(String upid) {
            this.upid = upid;
        }

        public String getVideoName() {
            return videoName;
        }

        public void setVideoName(String videoName) {
            this.videoName = videoName;
        }

        public String getVideoUrl() {
            return videoUrl;
        }

        public void setVideoUrl(String videoUrl) {
            this.videoUrl = videoUrl;
        }

        public static class AddTimeBean {
            /**
             * date : 14
             * day : 1
             * hours : 15
             * minutes : 6
             * month : 7
             * nanos : 437000000
             * seconds : 17
             * time : 1502694377437
             * timezoneOffset : -480
             * year : 117
             */

            private String date;
            private String day;
            private String hours;
            private String minutes;
            private String month;
            private String nanos;
            private String seconds;
            private String time;
            private String timezoneOffset;
            private String year;

            public String getDate() {
                return date;
            }

            public void setDate(String date) {
                this.date = date;
            }

            public String getDay() {
                return day;
            }

            public void setDay(String day) {
                this.day = day;
            }

            public String getHours() {
                return hours;
            }

            public void setHours(String hours) {
                this.hours = hours;
            }

            public String getMinutes() {
                return minutes;
            }

            public void setMinutes(String minutes) {
                this.minutes = minutes;
            }

            public String getMonth() {
                return month;
            }

            public void setMonth(String month) {
                this.month = month;
            }

            public String getNanos() {
                return nanos;
            }

            public void setNanos(String nanos) {
                this.nanos = nanos;
            }

            public String getSeconds() {
                return seconds;
            }

            public void setSeconds(String seconds) {
                this.seconds = seconds;
            }

            public String getTime() {
                return time;
            }

            public void setTime(String time) {
                this.time = time;
            }

            public String getTimezoneOffset() {
                return timezoneOffset;
            }

            public void setTimezoneOffset(String timezoneOffset) {
                this.timezoneOffset = timezoneOffset;
            }

            public String getYear() {
                return year;
            }

            public void setYear(String year) {
                this.year = year;
            }
        }
    }
}
