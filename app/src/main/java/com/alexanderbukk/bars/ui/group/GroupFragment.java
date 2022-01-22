package com.alexanderbukk.bars.ui.group;

import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.alexanderbukk.bars.data.AppDatabase;
import com.alexanderbukk.bars.data.group.Group;
import com.alexanderbukk.bars.databinding.FragmentDashboardBinding;

import java.util.List;

public class GroupFragment extends Fragment {

    private GroupViewModel groupViewModel;
    private FragmentDashboardBinding binding;
    private RecyclerView rvEventGroup;

    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        groupViewModel = new ViewModelProvider(this).get(GroupViewModel.class);
        binding = FragmentDashboardBinding.inflate(inflater, container, false);
        View root = binding.getRoot();




        rvEventGroup = binding.rvEventGroup;
        GroupRecyclerViewAdapter rvega = new GroupRecyclerViewAdapter(getContext(), groupViewModel.getAllUsers().getValue());
        rvEventGroup.setAdapter(rvega);
        rvEventGroup.setLayoutManager(new GridLayoutManager(getContext(), 2));

        //final TextView textView = binding.textDashboard;
        //rvega.setAllUsers(dashboardViewModel.getAllUsers().getValue());
        groupViewModel.getAllUsers().observe(getViewLifecycleOwner(), new Observer<List<Group>>() {
            @Override
            public void onChanged(@Nullable List<Group> groups) {
                rvega.setAllUsers(groups);
            }
        });

        return root;
    }

    @Override
    public void onResume() {
        super.onResume();
        List<Group> allGroups = groupViewModel.getAllUsers().getValue();
        int size;
        if(allGroups == null || allGroups.isEmpty())
            size = 0;
        else
            size = allGroups.size();


        Log.d("DashboardFragment.java, onResume()", Integer.toString(size));
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}