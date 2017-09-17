package com.hasee.pangci.Common;

import com.hasee.pangci.bean.User;

/**
 * Created by Administrator on 2017/9/11.
 */

public class MessageEvent {

    private User mUser;
    private String flag;

    public MessageEvent(User mUser, String flag) {
        this.mUser = mUser;
        this.flag = flag;
    }

    public User getUser() {
        return mUser;
    }

    public void setUser(User user) {
        mUser = user;
    }

    public String getFlag() {
        return flag;
    }

    public void setFlag(String flag) {
        this.flag = flag;
    }


}
