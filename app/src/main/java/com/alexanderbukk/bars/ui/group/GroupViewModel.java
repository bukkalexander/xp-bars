package com.alexanderbukk.bars.ui.group;

import android.app.Application;

import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.alexanderbukk.bars.data.group.Group;
import com.alexanderbukk.bars.data.group.GroupRepository;

import java.util.List;

public class GroupViewModel extends AndroidViewModel {

    private GroupRepository repository;
    private final LiveData<List<Group>> allGroups;

    public GroupViewModel(Application application) {
        super(application);
        repository = new GroupRepository(application);
        allGroups = repository.getAllGroups();
    }

    public LiveData<List<Group>> getAllGroups() {
        return allGroups;
    }
}