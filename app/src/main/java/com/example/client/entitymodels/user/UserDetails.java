package com.example.client.entitymodels.user;

public class UserDetails {
    public long UserDetailId;
    public String FirstName;
    public String LastName;
    public int Age;
    public boolean Sex;
    public String ProfilePicture;

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
