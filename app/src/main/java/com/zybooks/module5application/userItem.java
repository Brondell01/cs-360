package com.zybooks.module5application;

import java.io.Serializable;

public class userItem implements Serializable {

    public String userName;
    public String passWord;

    public userItem() {
    }

    public userItem(String userName, String passWord) {
        this.userName = userName;
        this.passWord = passWord;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getPassWord() {
        return passWord;
    }

    public void setPassWord(String passWord) {
        this.passWord = passWord;
    }
}
