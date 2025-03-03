package com.example.festfinder.User;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;

import com.example.festfinder.R;
import com.example.festfinder.constant.Static;
import com.example.festfinder.databinding.SuccessfullBinding;

public class SuccessfullFragment extends Fragment {
    SuccessfullBinding binding;
    FragmentActivity activity;
    Context context;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        binding = SuccessfullBinding.inflate(inflater, container, false);

        try {
            activity = requireActivity();
            context  = requireContext();
        } catch (Exception e) {
            e.printStackTrace();
        }
        binding.donebutton.setOnClickListener(v -> {
            Static.navigate(activity, R.id.action_successfullFragment_to_nav_home);
        });
        return binding.getRoot();

    }

}
