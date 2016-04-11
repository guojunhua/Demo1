package com.lzl_rjkx.doctor.bean;

import java.util.List;

/**
 * Created by lzl_os on 16/3/10.
 */
public class Advert {

    /**
     * code : 0
     * data : {"adv":[{"newsId":2,"newsTitle":"test2","newsImg":"http://test.waikegj.com/SurgicalKeeper/dataupload/adv/yiyuan2.jpg"},{"newsId":1,"newsTitle":"test1","newsImg":"http://test.waikegj.com/SurgicalKeeper/dataupload/adv/yiyuan1.jpg"}]}
     */

    private int code;
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
        /**
         * newsId : 2
         * newsTitle : test2
         * newsImg : http://test.waikegj.com/SurgicalKeeper/dataupload/adv/yiyuan2.jpg
         */

        private List<AdvEntity> adv;

        public void setAdv(List<AdvEntity> adv) {
            this.adv = adv;
        }

        public List<AdvEntity> getAdv() {
            return adv;
        }

        public static class AdvEntity {
            private int newsId;
            private String newsTitle;
            private String newsImg;

            public void setNewsId(int newsId) {
                this.newsId = newsId;
            }

            public void setNewsTitle(String newsTitle) {
                this.newsTitle = newsTitle;
            }

            public void setNewsImg(String newsImg) {
                this.newsImg = newsImg;
            }

            public int getNewsId() {
                return newsId;
            }

            public String getNewsTitle() {
                return newsTitle;
            }

            public String getNewsImg() {
                return newsImg;
            }
        }
    }
}
