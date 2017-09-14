package com.hasee.pangci.bean;

import cn.bmob.v3.BmobObject;

/**
 * Created by Administrator on 2017/9/14.
 */

public class Resources extends BmobObject{

    /***
     * 资源标题
     */
    private String Title;

    /***
     * 资源请求头类别
     */
    private String httpstype;

    /***
     * 资源封面请求id
     */
    private String CoverId;

    /***
     *资源类型
     */
    private String ContentType;

    /***
     * 资源喜欢人数
     */
    private String ContentLike;

    /***
     * 资源视频内容请求id
     */
    private String ContentId;

    /***
     * 资源免费还是收费
     */
    private String Authority;

    public Resources(String title, String httpstype, String coverId, String contentType, String contentLike, String contentId, String authority) {
        Title = title;
        this.httpstype = httpstype;
        CoverId = coverId;
        ContentType = contentType;
        ContentLike = contentLike;
        ContentId = contentId;
        Authority = authority;
    }

    public String getTitle() {
        return Title;
    }

    public void setTitle(String title) {
        Title = title;
    }

    public String getHttpstype() {
        return httpstype;
    }

    public void setHttpstype(String httpstype) {
        this.httpstype = httpstype;
    }

    public String getCoverId() {
        return CoverId;
    }

    public void setCoverId(String coverId) {
        CoverId = coverId;
    }

    public String getContentType() {
        return ContentType;
    }

    public void setContentType(String contentType) {
        ContentType = contentType;
    }

    public String getContentLike() {
        return ContentLike;
    }

    public void setContentLike(String contentLike) {
        ContentLike = contentLike;
    }

    public String getContentId() {
        return ContentId;
    }

    public void setContentId(String contentId) {
        ContentId = contentId;
    }

    public String getAuthority() {
        return Authority;
    }

    public void setAuthority(String authority) {
        Authority = authority;
    }

    @Override
    public String toString() {
        return "Resources{" +
                "Title='" + Title + '\'' +
                ", httpstype='" + httpstype + '\'' +
                ", CoverId='" + CoverId + '\'' +
                ", ContentType='" + ContentType + '\'' +
                ", ContentLike='" + ContentLike + '\'' +
                ", ContentId='" + ContentId + '\'' +
                ", Authority='" + Authority + '\'' +
                '}';
    }
}
