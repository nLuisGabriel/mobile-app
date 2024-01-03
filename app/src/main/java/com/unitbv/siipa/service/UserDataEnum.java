package com.unitbv.siipa.service;

import com.unitbv.siipa.user.User;

public enum UserDataEnum {
    INSTANCE;

    private User user;

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }
}
