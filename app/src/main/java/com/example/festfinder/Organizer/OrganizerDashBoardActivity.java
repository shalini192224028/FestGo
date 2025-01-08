package com.example.festfinder.Organizer;

import android.os.Bundle;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.festfinder.R;
import com.example.festfinder.databinding.ActivityOrganizerDashBoardBinding;

public class OrganizerDashBoardActivity extends AppCompatActivity {

    ActivityOrganizerDashBoardBinding Binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Binding = ActivityOrganizerDashBoardBinding.inflate(getLayoutInflater());
        EdgeToEdge.enable(this);
        setContentView(Binding.getRoot());

        // Reference the correct ID from your XML layout
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.drawer_layout), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
    }
}
