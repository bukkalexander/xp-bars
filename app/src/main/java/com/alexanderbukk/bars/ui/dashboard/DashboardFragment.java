package com.alexanderbukk.bars.ui.dashboard;

import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.alexanderbukk.bars.RecyclerViewEventGroupAdapter;
import com.alexanderbukk.bars.User;
import com.alexanderbukk.bars.databinding.FragmentDashboardBinding;

import java.util.List;

public class DashboardFragment extends Fragment {

    private DashboardViewModel dashboardViewModel;
    private FragmentDashboardBinding binding;

    private RecyclerView rvEventGroup;
    private String[] eventGroupNames = {
            "Chores",
            "Wellness",
            "Fitness",
            "Study",
            "Work",
            "Entrepreneur",
            "Project",
            "Social",
    };
    private int[] eventGroupColor = {
            Color.MAGENTA,
            Color.GREEN,
            Color.DKGRAY,
            Color.YELLOW,
            Color.GRAY,
            Color.RED,
            Color.CYAN,
            Color.BLUE,
    };

    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        dashboardViewModel = new ViewModelProvider(this).get(DashboardViewModel.class);
        binding = FragmentDashboardBinding.inflate(inflater, container, false);
        View root = binding.getRoot();




        rvEventGroup = binding.rvEventGroup;
        RecyclerViewEventGroupAdapter rvega = new RecyclerViewEventGroupAdapter(getContext(), dashboardViewModel.getAllUsers().getValue());
        rvEventGroup.setAdapter(rvega);
        rvEventGroup.setLayoutManager(new GridLayoutManager(getContext(), 2));

        //final TextView textView = binding.textDashboard;
        //rvega.setAllUsers(dashboardViewModel.getAllUsers().getValue());
        dashboardViewModel.getAllUsers().observe(getViewLifecycleOwner(), new Observer<List<User>>() {
            @Override
            public void onChanged(@Nullable List<User> users) {
                rvega.setAllUsers(users);
            }
        });

        return root;
    }

    @Override
    public void onResume() {
        super.onResume();
        List<User> allUsers = dashboardViewModel.getAllUsers().getValue();
        int size;
        if(allUsers == null || allUsers.isEmpty())
            size = 0;
        else
            size = allUsers.size();


        Log.d("DashboardFragment.java, onResume()", Integer.toString(size));
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}