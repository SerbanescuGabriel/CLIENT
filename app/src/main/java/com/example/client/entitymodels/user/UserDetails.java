package com.example.client.entitymodels.user;

import android.arch.persistence.room.PrimaryKey;
import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class UserDetails implements Serializable {

    @PrimaryKey
    private long UserDetailId;
    private String FirstName;
    private String LastName;
    private int Age;
    private boolean Sex;
    private String ProfilePicture;

    public UserDetails() {
        UserDetailId=10;
        FirstName="";
        LastName="";
        Age=0;
        Sex=true;
        ProfilePicture="";
    }

    protected UserDetails(Parcel in) {
        UserDetailId = in.readLong();
        FirstName = in.readString();
        LastName = in.readString();
        Age = in.readInt();
        Sex = in.readByte() != 0;
        ProfilePicture = in.readString();
    }
/*
    public static final Creator<UserDetails> CREATOR = new Creator<UserDetails>() {
        @Override
        public UserDetails createFromParcel(Parcel in) {
            return new UserDetails(in);
        }

        @Override
        public UserDetails[] newArray(int size) {
            return new UserDetails[size];
        }
    };
*/
    public long getUserDetailId() {
        return UserDetailId;
    }

    public void setUserDetailId(long userDetailId) {
        UserDetailId = userDetailId;
    }

    public String getFirstName() {
        return FirstName;
    }

    public void setFirstName(String firstName) {
        FirstName = firstName;
    }

    public String getLastName() {
        return LastName;
    }

    public void setLastName(String lastName) {
        LastName = lastName;
    }

    public int getAge() {
        return Age;
    }

    public void setAge(int age) {
        Age = age;
    }

    public boolean isSex() {
        return Sex;
    }

    public void setSex(boolean sex) {
        Sex = sex;
    }

    public String getProfilePicture() {
        return ProfilePicture;
    }

    public void setProfilePicture(String profilePicture) {
        ProfilePicture = profilePicture;
    }
/*
    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeLong(UserDetailId);
        dest.writeString(FirstName);
        dest.writeString(LastName);
        dest.writeInt(Age);
        dest.writeByte((byte) (Sex ? 1 : 0));
        dest.writeString(ProfilePicture);
    }
    */
}
