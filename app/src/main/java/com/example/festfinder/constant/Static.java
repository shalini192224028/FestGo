package com.example.festfinder.constant;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.Gravity;

import androidx.annotation.NonNull;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.FragmentActivity;
import androidx.navigation.NavController;
import androidx.navigation.fragment.NavHostFragment;

import com.example.festfinder.R;
import com.example.festfinder.User.UserSideNavActivity;
import com.example.festfinder.databinding.ActivityUserSideNavBinding;

public interface Static {

    @SuppressLint("RtlHardcoded")
    static void openDrawer(FragmentActivity activity) {
        UserSideNavActivity drawerActivity = (UserSideNavActivity) activity;
        DrawerLayout drawerLayout = drawerActivity.findViewById(R.id.drawer_layout);
//        ActivityUserSideNavBinding binding = ActivityUserSideNavBinding.inflate(drawerActivity.getLayoutInflater());

        if(drawerLayout.isOpen()) {
            drawerLayout.closeDrawer(Gravity.LEFT);
        } else {
            drawerLayout.openDrawer(Gravity.LEFT);
        }

//        drawerLayout.openDrawer(Gravity.LEFT);
    }

    static void navigate(@NonNull FragmentActivity activity, int id) {
        NavHostFragment navHostFragment = (NavHostFragment) activity.getSupportFragmentManager().findFragmentById(R.id.nav_host_fragment_content_user_side_nav);
        NavController navController = navHostFragment.getNavController();
        navController.navigate(id);
    }
    static void navigate(@NonNull FragmentActivity activity, int id, Bundle bundle) {
        NavHostFragment navHostFragment = (NavHostFragment) activity.getSupportFragmentManager().findFragmentById(R.id.nav_host_fragment_content_user_side_nav);
        NavController navController = navHostFragment.getNavController();
        navController.navigate(id, bundle);
    }
    static void orgnavi(@NonNull FragmentActivity activity, int id) {
        NavHostFragment navHostFragment = (NavHostFragment) activity.getSupportFragmentManager().findFragmentById(R.id.nav_host_fragment_content_organizer_side_nav);
        NavController navController = navHostFragment.getNavController();
        navController.navigate(id);
    }
    static void adminnavi(@NonNull FragmentActivity activity, int id) {
        NavHostFragment navHostFragment = (NavHostFragment) activity.getSupportFragmentManager().findFragmentById(R.id.nav_host_fragment_content_admin_side_nav);
        NavController navController = navHostFragment.getNavController();
        navController.navigate(id);
    }

    static void adminnavi(@NonNull FragmentActivity activity, int id, Bundle bundle) {
        NavHostFragment navHostFragment = (NavHostFragment) activity.getSupportFragmentManager().findFragmentById(R.id.nav_host_fragment_content_admin_side_nav);
        NavController navController = navHostFragment.getNavController();
        navController.navigate(id, bundle);
    }

}
