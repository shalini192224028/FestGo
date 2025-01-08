package com.example.festfinder.Admin;
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
import com.example.festfinder.databinding.HomepageAdminBinding;


 public class HomePageAdminFragment extends Fragment {
        HomepageAdminBinding binding;
        FragmentActivity activity;
        Context context;

        @Nullable
        @Override
        public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                                 @Nullable Bundle savedInstanceState) {

            binding = HomepageAdminBinding.inflate(inflater, container, false);
            try {
                activity = requireActivity();
                context  = requireContext();
            } catch (Exception e) {
                e.printStackTrace();
            }

            binding.myRequestLayout.setOnClickListener(v -> {
                Static.adminnavi(activity, R.id.action_homePageAdminFragment_to_myRequestFragment);
            });


            return binding.getRoot();
        }
    }

