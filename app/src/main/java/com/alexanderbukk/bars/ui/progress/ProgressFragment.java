package com.alexanderbukk.bars.ui.progress;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.alexanderbukk.bars.databinding.FragmentProgressBinding;

public class ProgressFragment extends Fragment {

    private ProgressViewModel progressViewModel;
    private FragmentProgressBinding binding;

    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        progressViewModel = new ViewModelProvider(this).get(ProgressViewModel.class);
        binding = FragmentProgressBinding.inflate(inflater, container, false);
        View root = binding.getRoot();


        return root;
    }

@Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}