package com.alexanderbukk.bars.ui.dashboard;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.alexanderbukk.bars.RecyclerViewEventGroupAdapter;
import com.alexanderbukk.bars.databinding.FragmentDashboardBinding;

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

    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        dashboardViewModel = new ViewModelProvider(this).get(DashboardViewModel.class);
        binding = FragmentDashboardBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        /*final TextView textView = binding.textDashboard;
        dashboardViewModel.getText().observe(getViewLifecycleOwner(), new Observer<String>() {
            @Override
            public void onChanged(@Nullable String s) {
                textView.setText(s);
            }
        });*/


        rvEventGroup = binding.rvEventGroup;
        RecyclerViewEventGroupAdapter rvega = new RecyclerViewEventGroupAdapter(getContext(), eventGroupNames);
        rvEventGroup.setAdapter(rvega);
        rvEventGroup.setLayoutManager(new GridLayoutManager(getContext(), 2));

        return root;
    }

@Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}