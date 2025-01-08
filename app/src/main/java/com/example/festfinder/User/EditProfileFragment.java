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
import com.example.festfinder.databinding.EditProfileBinding;

public class EditProfileFragment extends Fragment {
    EditProfileBinding binding;
    FragmentActivity activity;
    Context context;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.edit_profile, container, false);
        binding = EditProfileBinding.inflate(inflater, container, false);

        try {
            activity = requireActivity();
            context  = requireContext();
        } catch (Exception e) {
            e.printStackTrace();
        }
        binding.editProfileText.setOnClickListener(v -> {
            Static.navigate(activity, R.id.action_editProfileFragment_to_nav_home);
        });
        return binding.getRoot();

    }

}
