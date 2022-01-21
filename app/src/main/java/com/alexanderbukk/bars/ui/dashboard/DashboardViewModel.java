package com.alexanderbukk.bars.ui.dashboard;

import android.app.Application;

import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.alexanderbukk.bars.User;
import com.alexanderbukk.bars.UserRepository;

import java.util.List;

public class DashboardViewModel extends AndroidViewModel {

    private UserRepository repository;
    private final LiveData<List<User>> allUsers;

    public DashboardViewModel(Application application) {
        super(application);
        repository = new UserRepository(application);
        allUsers = repository.getAllUsers();
    }

    public LiveData<List<User>> getAllUsers() {
        return allUsers;
    }
}