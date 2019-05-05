package com.example.client.repositories;

import android.app.Application;
import android.arch.persistence.room.Room;

import com.example.client.dao.UserDao;
import com.example.client.database.UserDatabase;
import com.example.client.webservices.userWebservice.UserWebservice;

import javax.inject.Inject;

public class UserRepository {
    UserWebservice userWebservice;
    UserDao userDao;

    @Inject
    public UserRepository(UserWebservice userWebservice, Application application) {
        this.userWebservice = userWebservice;
        UserDatabase database = UserDatabase.getInstance(application.getApplicationContext());
        userDao = database.userDao();
    }

    public void TestLogin(){
        userWebservice.TestLogin();
    }

    public void Login(String username, String password){
        userWebservice.Login(username, password);
    }
}
