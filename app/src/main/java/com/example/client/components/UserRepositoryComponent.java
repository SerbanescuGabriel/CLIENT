package com.example.client.components;

import android.app.Application;

import com.example.client.activities.DashboardActivity;
import com.example.client.activities.LoginActivity;
import com.example.client.activities.RegisterActivity;
import com.example.client.modules.AppModule;
import com.example.client.modules.RetrofitModule;

import javax.inject.Singleton;

import dagger.Component;

@Singleton
@Component(modules = {RetrofitModule.class, AppModule.class})
public interface UserRepositoryComponent {

    void getUserRepositoryDashboardActivity(DashboardActivity activity);

    void getUserRepositoryLoginActivity(LoginActivity activity);

    void getUserRepositoryRegisterActivity(RegisterActivity activity);
}
