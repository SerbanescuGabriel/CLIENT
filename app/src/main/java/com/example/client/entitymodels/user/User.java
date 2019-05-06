package com.example.client.entitymodels.user;

import android.arch.persistence.room.Embedded;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;

import com.google.gson.annotations.SerializedName;

public class User {

    @SerializedName("UserId")
    private long userId;

    @SerializedName("Username")
    private String userName;

    @SerializedName("Password")
    private String password;

    @SerializedName("Email")
    private String email;

    @SerializedName("UserDetails")
    @Embedded
    private UserDetails userDetails;

    public User(long userId, String userName, String password, String email, UserDetails userDetails) {
        this.userId = userId;
        this.userName = userName;
        this.password = password;
        this.email = email;
        this.userDetails = userDetails;
    }

    public User() {
        userId=1;
        userName="test";
        password="test";
        email="test";
        userDetails=new UserDetails();
    }

    public long getUserId() {
        return userId;
    }

    public void setUserId(long userId) {
        this.userId = userId;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public UserDetails getUserDetails() {
        return userDetails;
    }

    public void setUserDetails(UserDetails userDetails) {
        this.userDetails = userDetails;
    }

    @Override
    public String toString() {
        return "User{" +
                "userId=" + userId +
                ", userName='" + userName + '\'' +
                ", password='" + password + '\'' +
                ", email='" + email + '\'' +
                ", userDetails=" + userDetails +
                '}';
    }
}
