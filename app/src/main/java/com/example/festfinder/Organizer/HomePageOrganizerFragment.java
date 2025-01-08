package com.example.festfinder.Organizer;

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
import com.example.festfinder.databinding.HomepageOrganizerBinding;



public class HomePageOrganizerFragment extends Fragment {
        HomepageOrganizerBinding binding;
        FragmentActivity activity;
        Context context;

        @Nullable
        @Override
        public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                                 @Nullable Bundle savedInstanceState) {

            binding = HomepageOrganizerBinding.inflate(inflater, container, false);
            try {
                activity = requireActivity();
                context  = requireContext();
            } catch (Exception e) {
                e.printStackTrace();
            }

        binding.createEventCV.setOnClickListener(v -> {
        Static.orgnavi(activity, R.id.action_homePageOrganizerFragment_to_eventAddOrganizerFragment);
        });

         return binding.getRoot();
        }
}

