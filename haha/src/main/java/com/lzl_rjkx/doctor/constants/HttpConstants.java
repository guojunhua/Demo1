package com.lzl_rjkx.doctor.constants;

/**
 * Created by Administrator on 2016/3/18.
 * 所有接口的封装类
 */
public class HttpConstants {

    public final static String HTTP_HEAD="http://";
    /**
     * 内网
     */
    public final static String HTTP_IP="192.168.1.103:8080";
    /**
     * 外网
     */
    public final static String HTTP_OUT_IP="182.50.125.84";

    /**
     * 请求内容
     */
    public final static String HTTP_CONTEXT="/SurgicalKeeper/appItf/";
    /**
     * 请求地址
     */
    public final static String HTTP_REQUEST=HTTP_HEAD+HTTP_IP+HTTP_CONTEXT;

    /**
     * 登录[loginIn]
     */
    public final static String HTTP_LOGININ=HTTP_REQUEST+"loginIn.do";
    /**
     * 退出[loginOut]
     */
    public final static String HTTP_LOGINOUT=HTTP_REQUEST+"loginOut.do";
    /**
     * 获取验证码[getAuthCode]
     */
    public final static String HTTP_GETAUTHCODE=HTTP_REQUEST+"getAuthCode.do";
    /**
     *找回密码[fogotPwd]
     */
    public final static String HTTP_FOGOTPWD=HTTP_REQUEST+"fogotPwd.do";
    /**
     * 更新头像[updateHeadPhoto]
     */
    public final static String HTTP_UPDATAHEADPHOTO=HTTP_REQUEST+"updateHeadPhoto.do";
    /**
     * 获取广告条[queryNewsAdvert]
     */
    public final static String HTTP_QUERYNEWSADVERT=HTTP_REQUEST+"queryNewsAdvert.do";
    /**
     * 获取医生当天订单[queryDoctorTodayOrder]
     */
    public final static String HTTP_QUERYDOCTOR_TODAYORDER=HTTP_REQUEST+"queryDoctorTodayOrder.do";
    /**
     * 获取医生订单[queryDoctorOrder]
     */
    public final static String HTTP_QUERYDOCTOR_ORDER=HTTP_REQUEST+"queryDoctorOrder.do";
    /**
     * 获取热门圈子列表[queryHotsNews]
     */
    public final static String HTTP_QUERYHOTSNEWS=HTTP_REQUEST+"queryHotsNews.do";
    /**
     * 获取圈子列表[queryNewsList]
     */
    public final static String HTTP_QUERYNEWSLIST=HTTP_REQUEST+"queryNewsList.do";
    /**
     * 删除圈子内容[delNews]
     */
    public final static String HTTP_DELNEWS=HTTP_REQUEST+"delNews.do";
    /**
     * 获取某人发布的圈子列表[queryNewsListForUser]
     */
    public final static String HTTP_QUERYNEWSLISTFORUSER=HTTP_REQUEST+"queryNewsListForUser.do";
    /**
     * 获取单个圈子内容[getNews]
     */
    public final static String HTTP_GETNEWS=HTTP_REQUEST+"getNews.do";
    /**
     * 获取收藏列表[queryNewsFavList]
     */
    public final static String HTTP_QUERYNEWSFAVLIST=HTTP_REQUEST+"queryNewsFavList.do";
    /**
     * 删除某人收藏的圈子[delNewsFav]
     */
    public final static String HTTP_DELNEWSFAV=HTTP_REQUEST+"delNewsFav.do";
    /**
     * 多文件上传[uploadFile]
     */
    public final static String HTTP_UPLOADFILE=HTTP_REQUEST+"uploadFile.do";
    /**
     * 查询金币量[queryMoney]
     */
    public final static String HTTP_QUERYMONERY=HTTP_REQUEST+"queryMoney.do";
    /**
     *圈子点赞[addNewsZan]
     */
    public final static String HTTP_ADDNEWSZAN=HTTP_REQUEST+"addNewsZan.do";
    /**
     * 圈子点收藏[addNewsFav]
     */
    public final static String HTTP_ADDNEWSFAV=HTTP_REQUEST+"addNewsFav.do";
    /**
     *更新圈子内容[updateNews]
     */
    public final static String HTTP_UPDATENEWS=HTTP_REQUEST+"updateNews.do";
    /**
     *添加圈子内容[addNews]
     */
    public final static String HTTP_ADDNEWS=HTTP_REQUEST+"addNews.do";
    /**
     * 更新圈子阅读量[addNewsRead]
     */
    public final static String HTTP_ADDNEWSREAD=HTTP_REQUEST+"addNewsRead.do";
    /**
     * 添加圈子回复[addNewsReply]
     */
    public final static String HTTP_ADDNEWSREPLY=HTTP_REQUEST+"addNewsReply.do";
    /**
     * 删除圈子回复[delNewsReply]
     */
    public final static String HTTP_DElNEWSREPLY=HTTP_REQUEST+"delNewsReply.do";
    /**
     * 获取圈子回复列表[getNewsReplyList]
     */
    public final static String HTTP_GETNEWSREPLYLIST=HTTP_REQUEST+"getNewsReplyList.do";
}