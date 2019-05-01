package com.example.client.entitymodels.user;

public class UserDetails {
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
}
