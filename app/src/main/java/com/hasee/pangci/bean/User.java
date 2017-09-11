package com.hasee.pangci.bean;

import cn.bmob.v3.BmobObject;
import cn.bmob.v3.datatype.BmobDate;

/**
 * Created by Administrator on 2017/9/11.
 */

public class User extends BmobObject {

    private String userAccount;

    private String userPassword;

    private BmobDate userRegisterTime;

    private String userHeadImg;

    private String memberLevel;

    private BmobDate memberStartDate;

    private BmobDate memberEndDate;

    public User() {
    }

    public User(String userAccount, String userPassword, BmobDate userRegisterTime, String userHeadImg, String memberLevel, BmobDate memberStartDate, BmobDate memberEndDate) {
        this.userAccount = userAccount;
        this.userPassword = userPassword;
        this.userHeadImg = userHeadImg;
        this.memberLevel = memberLevel;
        this.memberStartDate = memberStartDate;
        this.memberEndDate = memberEndDate;
        this.userRegisterTime = userRegisterTime;
    }

    public String getUserAccount() {
        return userAccount;
    }

    public void setUserAccount(String userAccount) {
        this.userAccount = userAccount;
    }

    public String getUserPassword() {
        return userPassword;
    }

    public void setUserPassword(String userPassword) {
        this.userPassword = userPassword;
    }

    public BmobDate getUserRegisterTime() {
        return userRegisterTime;
    }

    public void setUserRegisterTime(BmobDate userRegisterTime) {
        this.userRegisterTime = userRegisterTime;
    }

    public String getUserHeadImg() {
        return userHeadImg;
    }

    public void setUserHeadImg(String userHeadImg) {
        this.userHeadImg = userHeadImg;
    }

    public String getMemberLevel() {
        return memberLevel;
    }

    public void setMemberLevel(String memberLevel) {
        this.memberLevel = memberLevel;
    }

    public BmobDate getMemberStartDate() {
        return memberStartDate;
    }

    public void setMemberStartDate(BmobDate memberStartDate) {
        this.memberStartDate = memberStartDate;
    }

    public BmobDate getMemberEndDate() {
        return memberEndDate;
    }

    public void setMemberEndDate(BmobDate memberEndDate) {
        this.memberEndDate = memberEndDate;
    }

    @Override
    public String toString() {
        return "User{" +
                "userAccount='" + userAccount + '\'' +
                ", userPassword='" + userPassword + '\'' +
                ", userRegisterTime=" + userRegisterTime +
                ", userHeadImg='" + userHeadImg + '\'' +
                ", memberLevel='" + memberLevel + '\'' +
                ", memberStartDate=" + memberStartDate +
                ", memberEndDate=" + memberEndDate +
                '}';
    }
}
