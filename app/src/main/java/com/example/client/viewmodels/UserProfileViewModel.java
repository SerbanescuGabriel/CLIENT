package com.example.client.viewmodels;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.ViewModel;
import android.util.Log;

import com.example.client.entitymodels.user.User;

public class UserProfileViewModel extends ViewModel {

    private LiveData<User> user;

    public void init(int userId) {
        if (this.user != null) {
            // ViewModel is created on a per-Fragment basis, so the userId
            // doesn't change.
            return;
        }
        user = getUser();
    }

    public LiveData<User> getUser() {
        MutableLiveData<User> data = new MutableLiveData<User>();
        data.setValue(new User());
        return data; //FOR TEST ONLY
    }


}
