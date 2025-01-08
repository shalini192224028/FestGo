package com.example.festfinder.User.ui;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;

import com.example.festfinder.R;
import com.example.festfinder.constant.Static;
import com.example.festfinder.databinding.HomepageuserBinding;

public class HomePageUserFragment extends Fragment {

    HomepageuserBinding binding;
    FragmentActivity activity;
    Context context;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding = HomepageuserBinding.inflate(inflater, container, false);

        try {
            context  = requireContext();
            activity = requireActivity();
        } catch (Exception e) {
            e.printStackTrace();
        }

        Bundle bundle = new Bundle();

        binding.menuIcon.setOnClickListener(v -> {
            Static.openDrawer(activity);
//            Toast.makeText(activity, "Open", Toast.LENGTH_SHORT).show();
        });

        binding.eventCV.setOnClickListener(v -> {
            bundle.putString("eventType", "event");
            Static.navigate(activity, R.id.action_nav_home_to_eventListFragment, bundle);
        });

        binding.workshopCV.setOnClickListener(v -> {
            bundle.putString("eventType", "workshop");
            Static.navigate(activity, R.id.action_nav_home_to_eventListFragment, bundle);
        });

        binding.seminarCV.setOnClickListener(v -> {
            bundle.putString("eventType", "Seminar");
            Static.navigate(activity, R.id.action_nav_home_to_eventListFragment, bundle);
        });

        binding.webinarCV.setOnClickListener(v -> {
            bundle.putString("eventType", "webinar");
            Static.navigate(activity, R.id.action_nav_home_to_eventListFragment, bundle);
        });



        return binding.getRoot();

    }

}
