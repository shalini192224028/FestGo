package com.example.festfinder.User;

import android.os.Bundle;
import android.widget.Toast;

import com.example.festfinder.R;
import com.google.android.material.navigation.NavigationView;

import androidx.navigation.NavController;
import androidx.navigation.fragment.NavHostFragment;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.appcompat.app.AppCompatActivity;

import com.example.festfinder.databinding.ActivityUserSideNavBinding;

public class UserSideNavActivity extends AppCompatActivity {

    private AppBarConfiguration AppBarConfiguration;
    private ActivityUserSideNavBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityUserSideNavBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

//        setSupportActionBar(binding.appBarUserSideNav.toolbar);

        DrawerLayout drawer = binding.drawerLayout;
        NavigationView navigationView = binding.navView;
        navigationView.setNavigationItemSelectedListener(item -> {

            if (item.getItemId() == R.id.nav_logoutbb){
                Toast.makeText(this, "logout", Toast.LENGTH_SHORT).show();
            }
            return true;
        });
//        // Passing each menu ID as a set of Ids because each
//        // menu should be considered as top level destinations.
        AppBarConfiguration = new AppBarConfiguration.Builder(
                R.id.nav_home, R.id.nav_gallery, R.id.nav_slideshow, R.id.nav_profile, R.id.nav_certificate, R.id.eventListFragment)
                .setOpenableLayout(drawer)
                .build();
        NavHostFragment navHostFragment = (NavHostFragment) getSupportFragmentManager().findFragmentById(R.id.nav_host_fragment_content_user_side_nav);
        NavController navController = navHostFragment.getNavController();
////        NavigationUI.setupActionBarWithNavController(this, navController);
        NavigationUI.setupWithNavController(navigationView, navController);
    }

}