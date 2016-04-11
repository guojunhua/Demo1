package com.lzl_rjkx.doctor.bean;

import java.util.List;

/**
 * Created by lzl_os on 16/2/26.
 */
public class Patient {
    /**
     * code : 0
     * data : {"totalcount":3,"order":[{"pName":"lin","docId":1,"oStatus":5,"pId":208,"pDesc":"66666666666666","sTime":"2016-03-14 16:10","vip":0,"pSick":"脊柱"},{"pName":"李春","docId":1,"oStatus":3,"pId":194,"pDesc":"测试","sTime":"2016-03-14 09:22","vip":1,"pSick":"创伤科,脊柱,关节"},{"pName":"潘林林","docId":1,"oStatus":3,"pId":204,"pDesc":"2222222222","sTime":"2016-02-24 10:00","vip":0,"pSick":"创伤科"}]}
     */

    private int code;
    /**
     * totalcount : 3
     * order : [{"pName":"lin","docId":1,"oStatus":5,"pId":208,"pDesc":"66666666666666","sTime":"2016-03-14 16:10","vip":0,"pSick":"脊柱"},{"pName":"李春","docId":1,"oStatus":3,"pId":194,"pDesc":"测试","sTime":"2016-03-14 09:22","vip":1,"pSick":"创伤科,脊柱,关节"},{"pName":"潘林林","docId":1,"oStatus":3,"pId":204,"pDesc":"2222222222","sTime":"2016-02-24 10:00","vip":0,"pSick":"创伤科"}]
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
         * pName : lin
         * docId : 1
         * oStatus : 5
         * pId : 208
         * pDesc : 66666666666666
         * sTime : 2016-03-14 16:10
         * vip : 0
         * pSick : 脊柱
         */

        private List<OrderEntity> order;

        public void setTotalcount(int totalcount) {
            this.totalcount = totalcount;
        }

        public void setOrder(List<OrderEntity> order) {
            this.order = order;
        }

        public int getTotalcount() {
            return totalcount;
        }

        public List<OrderEntity> getOrder() {
            return order;
        }

        public static class OrderEntity {
            private String pName;
            private int docId;
            private int oStatus;
            private int pId;
            private String pDesc;
            private String sTime;
            private int vip;
            private String pSick;

            public void setPName(String pName) {
                this.pName = pName;
            }

            public void setDocId(int docId) {
                this.docId = docId;
            }

            public void setOStatus(int oStatus) {
                this.oStatus = oStatus;
            }

            public void setPId(int pId) {
                this.pId = pId;
            }

            public void setPDesc(String pDesc) {
                this.pDesc = pDesc;
            }

            public void setSTime(String sTime) {
                this.sTime = sTime;
            }

            public void setVip(int vip) {
                this.vip = vip;
            }

            public void setPSick(String pSick) {
                this.pSick = pSick;
            }

            public String getPName() {
                return pName;
            }

            public int getDocId() {
                return docId;
            }

            public int getOStatus() {
                return oStatus;
            }

            public int getPId() {
                return pId;
            }

            public String getPDesc() {
                return pDesc;
            }

            public String getSTime() {
                return sTime;
            }

            public int getVip() {
                return vip;
            }

            public String getPSick() {
                return pSick;
            }
        }
    }
}