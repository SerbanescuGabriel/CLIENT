package com.example.client.entitymodels.user;

import android.arch.persistence.room.Embedded;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;
import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

public class User implements Parcelable {

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

    protected User(Parcel in) {
        userId = in.readLong();
        userName = in.readString();
        password = in.readString();
        email = in.readString();
    }

    public static final Creator<User> CREATOR = new Creator<User>() {
        @Override
        public User createFromParcel(Parcel in) {
            return new User(in);
        }

        @Override
        public User[] newArray(int size) {
            return new User[size];
        }
    };

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

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeLong(userId);
        dest.writeString(userName);
        dest.writeString(password);
        dest.writeString(email);
    }
}
