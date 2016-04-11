package com.lzl_rjkx.doctor.bean;

import java.util.List;

/**
 * Created by lzl_os on 16/3/15.
 */
public class Article {

    /**
     * code : 0
     * data : {"totalcount":1,"list":[{"favCount":0,"msgType":2,"msgImg":"http://attched.yiyuan591.com/dataupload/lecture/photo/LECTURE_PAV_6224990476792542.jpg","opType":2,"opId":1,"msgId":18,"readCount":3,"zanCount":0,"msgContent":"<p><br><\/p>","opName":"ADMIN","msgTitle":"文章1","reCount":0,"sTime":"2016-03-11 14:33"}]}
     */

    private int code;
    /**
     * totalcount : 1
     * list : [{"favCount":0,"msgType":2,"msgImg":"http://attched.yiyuan591.com/dataupload/lecture/photo/LECTURE_PAV_6224990476792542.jpg","opType":2,"opId":1,"msgId":18,"readCount":3,"zanCount":0,"msgContent":"<p><br><\/p>","opName":"ADMIN","msgTitle":"文章1","reCount":0,"sTime":"2016-03-11 14:33"}]
     */

    private DataEntity data;

    public void setCode(int code) {
        this.code = code;
    }

    public void setData(DataEntity data) {
        this.data = data;
    }

    public int getCode() {
        return code;
    }

    public DataEntity getData() {
        return data;
    }

    public static class DataEntity {
        private int totalcount;
        /**
         * favCount : 0
         * msgType : 2
         * msgImg : http://attched.yiyuan591.com/dataupload/lecture/photo/LECTURE_PAV_6224990476792542.jpg
         * opType : 2
         * opId : 1
         * msgId : 18
         * readCount : 3
         * zanCount : 0
         * msgContent : <p><br></p>
         * opName : ADMIN
         * msgTitle : 文章1
         * reCount : 0
         * sTime : 2016-03-11 14:33
         */

        private List<ListEntity> list;

        public void setTotalcount(int totalcount) {
            this.totalcount = totalcount;
        }

        public void setList(List<ListEntity> list) {
            this.list = list;
        }

        public int getTotalcount() {
            return totalcount;
        }

        public List<ListEntity> getList() {
            return list;
        }

        public static class ListEntity extends SerializableMap {
            private int favCount;
            private int msgType;
            private String msgImg;
            private int opType;
            private int opId;
            private int msgId;
            private int readCount;
            private int zanCount;
            private String msgContent;
            private String opName;
            private String msgTitle;
            private int reCount;
            private String sTime;

            public void setFavCount(int favCount) {
                this.favCount = favCount;
            }

            public void setMsgType(int msgType) {
                this.msgType = msgType;
            }

            public void setMsgImg(String msgImg) {
                this.msgImg = msgImg;
            }

            public void setOpType(int opType) {
                this.opType = opType;
            }

            public void setOpId(int opId) {
                this.opId = opId;
            }

            public void setMsgId(int msgId) {
                this.msgId = msgId;
            }

            public void setReadCount(int readCount) {
                this.readCount = readCount;
            }

            public void setZanCount(int zanCount) {
                this.zanCount = zanCount;
            }

            public void setMsgContent(String msgContent) {
                this.msgContent = msgContent;
            }

            public void setOpName(String opName) {
                this.opName = opName;
            }

            public void setMsgTitle(String msgTitle) {
                this.msgTitle = msgTitle;
            }

            public void setReCount(int reCount) {
                this.reCount = reCount;
            }

            public void setSTime(String sTime) {
                this.sTime = sTime;
            }

            public int getFavCount() {
                return favCount;
            }

            public int getMsgType() {
                return msgType;
            }

            public String getMsgImg() {
                return msgImg;
            }

            public int getOpType() {
                return opType;
            }

            public int getOpId() {
                return opId;
            }

            public int getMsgId() {
                return msgId;
            }

            public int getReadCount() {
                return readCount;
            }

            public int getZanCount() {
                return zanCount;
            }

            public String getMsgContent() {
                return msgContent;
            }

            public String getOpName() {
                return opName;
            }

            public String getMsgTitle() {
                return msgTitle;
            }

            public int getReCount() {
                return reCount;
            }

            public String getSTime() {
                return sTime;
            }
        }
    }
}
