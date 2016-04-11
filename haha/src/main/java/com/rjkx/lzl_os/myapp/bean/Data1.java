package com.rjkx.lzl_os.myapp.bean;

import java.util.List;

/**
 * Created by Administrator on 2015/12/23.
 */
public class Data1 {

    /**
     * status : 0
     * message : ok
     * data : [{"video_id":1891,"video_name":"健康运动 ","video_picture":"http://www.appzg.org/uploadImage/videoLogoImgs/2015/4/21/cdd7aceb5b86f4dfc5a1898a2cbeb577.jpg","video_url":"http://v.youku.com/v_show/id_XMTY5MjE5MTky.html?from=s1.8-1-1.2","video_AppUserID":"0b465072fdba2334249759af9e6b9d6d","video_createDate":1429587401269,"video_desc":"最新恒怡工间操-激情飞扬 工间操 瑜伽 白领健康运动 ","video_androidUrl":"http%3A%2F%2Fv.youku.com%2Fv_show%2Fid_XMTY5MjE5MTky.html%3Ffrom%3Ds1.8-1-1.2","video_iosUrl":"http://v.youku.com/v_show/id_XMTY5MjE5MTky.html?from=s1.8-1-1.2"},{"video_id":1890,"video_name":"养生堂：饮食决定健康","video_picture":"http://www.appzg.org/uploadImage/videoLogoImgs/2015/4/21/de1ee9c4567e70c409c266683fb578c8.jpg","video_url":"http://v.youku.com/v_show/id_XNTAxMzM0MjQ0.html?from=s1.8-1-1.2","video_AppUserID":"0b465072fdba2334249759af9e6b9d6d","video_createDate":1429587271914,"video_desc":"《养生堂》以\u201c传播养生之道、传授养生之术\u201d为栏目宗旨。按照二十四节气来安排节目内容，每天既系统介绍中国传统养生文化、又有针对性的介绍实用养生方法。","video_androidUrl":"http%3A%2F%2Fv.youku.com%2Fv_show%2Fid_XNTAxMzM0MjQ0.html%3Ffrom%3Ds1.8-1-1.2","video_iosUrl":"http://v.youku.com/v_show/id_XNTAxMzM0MjQ0.html?from=s1.8-1-1.2"},{"video_id":1889,"video_name":"健康瘦身操","video_picture":"http://www.appzg.org/uploadImage/videoLogoImgs/2015/4/21/76160ec1b24196dd51e82a4f5f0e42b3.jpg","video_url":"http://v.youku.com/v_show/id_XODE3MjMwMjA0.html?from=s1.8-1-1.2","video_AppUserID":"0b465072fdba2334249759af9e6b9d6d","video_createDate":1429587010925,"video_desc":"筷子兄弟之【小苹果】广场舞教学","video_androidUrl":"http%3A%2F%2Fv.youku.com%2Fv_show%2Fid_XODE3MjMwMjA0.html%3Ffrom%3Ds1.8-1-1.2","video_iosUrl":"http://v.youku.com/v_show/id_XODE3MjMwMjA0.html?from=s1.8-1-1.2"},{"video_id":1887,"video_name":"戴军宁静抢主持人饭碗","video_picture":"http://www.appzg.org/uploadImage/videoLogoImgs/2015/4/21/a05c339e1ce5a82ef673a7c4adb1a919.jpg","video_url":"http://v.youku.com/v_show/id_XNzczMDgzODEy.html?from=s1.8-1-1.1","video_AppUserID":"0b465072fdba2334249759af9e6b9d6d","video_createDate":1429586412976,"video_desc":"健康007》是国内首个娱乐化综艺健康节目，通过真实病例影视再现的方式来让年轻医生诊断病患，每期将有一个国家级的知名医生来主持本期病人的病情，他将以超级医生的名义再现现实中医生诊断的病例。","video_androidUrl":"http%3A%2F%2Fv.youku.com%2Fv_show%2Fid_XNzczMDgzODEy.html%3Ffrom%3Ds1.8-1-1.1","video_iosUrl":"http://v.youku.com/v_show/id_XNzczMDgzODEy.html?from=s1.8-1-1.1"}]
     * dataCount : 4
     * timestamp : 0
     */

    private int status;
    private String message;
    private int dataCount;
    private int timestamp;
    /**
     * video_id : 1891
     * video_name : 健康运动
     * video_picture : http://www.appzg.org/uploadImage/videoLogoImgs/2015/4/21/cdd7aceb5b86f4dfc5a1898a2cbeb577.jpg
     * video_url : http://v.youku.com/v_show/id_XMTY5MjE5MTky.html?from=s1.8-1-1.2
     * video_AppUserID : 0b465072fdba2334249759af9e6b9d6d
     * video_createDate : 1429587401269
     * video_desc : 最新恒怡工间操-激情飞扬 工间操 瑜伽 白领健康运动
     * video_androidUrl : http%3A%2F%2Fv.youku.com%2Fv_show%2Fid_XMTY5MjE5MTky.html%3Ffrom%3Ds1.8-1-1.2
     * video_iosUrl : http://v.youku.com/v_show/id_XMTY5MjE5MTky.html?from=s1.8-1-1.2
     */

    private List<DataEntity> data;

    public void setStatus(int status) {
        this.status = status;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public void setDataCount(int dataCount) {
        this.dataCount = dataCount;
    }

    public void setTimestamp(int timestamp) {
        this.timestamp = timestamp;
    }

    public void setData(List<DataEntity> data) {
        this.data = data;
    }

    public int getStatus() {
        return status;
    }

    public String getMessage() {
        return message;
    }

    public int getDataCount() {
        return dataCount;
    }

    public int getTimestamp() {
        return timestamp;
    }

    public List<DataEntity> getData() {
        return data;
    }

    public static class DataEntity {
        private int video_id;
        private String video_name;
        private String video_picture;
        private String video_url;
        private String video_AppUserID;
        private long video_createDate;
        private String video_desc;
        private String video_androidUrl;
        private String video_iosUrl;

        public void setVideo_id(int video_id) {
            this.video_id = video_id;
        }

        public void setVideo_name(String video_name) {
            this.video_name = video_name;
        }

        public void setVideo_picture(String video_picture) {
            this.video_picture = video_picture;
        }

        public void setVideo_url(String video_url) {
            this.video_url = video_url;
        }

        public void setVideo_AppUserID(String video_AppUserID) {
            this.video_AppUserID = video_AppUserID;
        }

        public void setVideo_createDate(long video_createDate) {
            this.video_createDate = video_createDate;
        }

        public void setVideo_desc(String video_desc) {
            this.video_desc = video_desc;
        }

        public void setVideo_androidUrl(String video_androidUrl) {
            this.video_androidUrl = video_androidUrl;
        }

        public void setVideo_iosUrl(String video_iosUrl) {
            this.video_iosUrl = video_iosUrl;
        }

        public int getVideo_id() {
            return video_id;
        }

        public String getVideo_name() {
            return video_name;
        }

        public String getVideo_picture() {
            return video_picture;
        }

        public String getVideo_url() {
            return video_url;
        }

        public String getVideo_AppUserID() {
            return video_AppUserID;
        }

        public long getVideo_createDate() {
            return video_createDate;
        }

        public String getVideo_desc() {
            return video_desc;
        }

        public String getVideo_androidUrl() {
            return video_androidUrl;
        }

        public String getVideo_iosUrl() {
            return video_iosUrl;
        }
    }
}
