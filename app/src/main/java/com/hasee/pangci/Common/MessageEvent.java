package com.hasee.pangci.Common;

import com.hasee.pangci.bean.User;

/**
 * Created by Administrator on 2017/9/11.
 */

public class MessageEvent {

    private User mUser;

    public MessageEvent(User mUser) {
        this.mUser = mUser;
    }

    public User getUser() {
        return mUser;
    }

    public void setUser(User user) {
        mUser = user;
    }



}
