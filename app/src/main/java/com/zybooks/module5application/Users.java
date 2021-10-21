package com.zybooks.module5application;


import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "Users")
public class Users {

    @NonNull
    @PrimaryKey
    @ColumnInfo(name="username")
    private String userName = "";

    @NonNull
    @ColumnInfo(name="userpassword")
    private String userPassword = "";

    public Users() {
    }

    public Users(@NonNull String userName, @NonNull String userPassword) {
        this.userName = userName;
        this.userPassword = userPassword;
    }

    @NonNull
    public String getUserName() {
        return userName;
    }

    public void setUserName(@NonNull String userName) {
        this.userName = userName;
    }

    @NonNull
    public String getUserPassword() {
        return userPassword;
    }

    public void setUserPassword(@NonNull String userPassword) {
        this.userPassword = userPassword;
    }
}
