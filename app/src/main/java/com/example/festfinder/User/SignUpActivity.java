package com.example.festfinder.User;

import android.os.Bundle;
import android.util.Log;
import android.widget.RadioGroup;
import android.widget.Toast;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

import com.example.festfinder.databinding.ActivitySignUpBinding;
import com.example.festfinder.service.RestClient;
import com.example.festfinder.service.Service;
import com.example.festfinder.service.response.Response;
import com.example.festfinder.service.response.MyRequest;
import com.google.gson.Gson;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import retrofit2.Call;
import retrofit2.Callback;

public class SignUpActivity extends AppCompatActivity {

    private ActivitySignUpBinding binding;
    String userType;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivitySignUpBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        // Get the submit button reference
        Button submitButton = binding.submitButton;

        // Radio group listener for user type
        binding.usertype.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                if (binding.user.getId() == checkedId) {
                    userType = binding.user.getText().toString();
                } else {
                    userType = binding.organizer.getText().toString();
                }
            }
        });

        // Set an OnClickListener for the submit button
        submitButton.setOnClickListener(v -> {
            // Get the values from the EditText fields when the button is clicked
            String username = binding.username.getText().toString();
            String email = binding.email.getText().toString();
            String password = binding.password.getText().toString();
            String confirmPassword = binding.confirmpassword.getText().toString();

            // Validate username
            if (!isValidUsername(username)) {
                Toast.makeText(SignUpActivity.this, "Invalid username! Only letters, numbers, and underscores are allowed (3–15 characters).", Toast.LENGTH_SHORT).show();
                return; // Stop further execution
            }

            // Validate email domain
            if (!isValidEmail(email)) {
                Toast.makeText(SignUpActivity.this, "Invalid email! Only Gmail, Yahoo, and Example domains are allowed.", Toast.LENGTH_SHORT).show();
                return; // Stop further execution
            }

            // Check if password and confirm password match
            if (!password.equals(confirmPassword)) {
                Toast.makeText(SignUpActivity.this, "Passwords do not match!", Toast.LENGTH_SHORT).show();
                return; // Stop further execution
            }

            // Create request object
            MyRequest.SignupRequest request = new MyRequest.SignupRequest(username, email, password, userType);

            // Get Retrofit client and API service
            Service apiService = RestClient.getApiClient().create(Service.class);

            // Make the API call
            Call<Response.SignUpResponse> call = apiService.signup(request);
            call.enqueue(new Callback<Response.SignUpResponse>() {
                @Override
                public void onResponse(Call<Response.SignUpResponse> call, retrofit2.Response<Response.SignUpResponse> response) {
                    if (response.isSuccessful() && response.body() != null) {
                        Toast.makeText(SignUpActivity.this, response.body().getMessage(), Toast.LENGTH_SHORT).show();
                    } else {
                        try {
                            Response.ErrorResponse errorResponse = new Gson().fromJson(response.errorBody().charStream(), Response.ErrorResponse.class);
                            Toast.makeText(SignUpActivity.this, errorResponse.getMessage(), Toast.LENGTH_SHORT).show();
                        } catch (Exception e) {
                            Toast.makeText(SignUpActivity.this, e.getMessage(), Toast.LENGTH_LONG).show();
                            Log.e("SignUpActivity", e.getMessage());
                            e.printStackTrace();
                        }
                    }
                }

                @Override
                public void onFailure(Call<Response.SignUpResponse> call, Throwable t) {
                    Toast.makeText(SignUpActivity.this, "API Error: " + t.getMessage(), Toast.LENGTH_SHORT).show();
                }
            });
        });
    }

    // Method to validate email domain
    private boolean isValidEmail(String email) {
        // Define allowed email domains (gmail, yahoo, example)
        String emailPattern = "^[a-zA-Z0-9._%+-]+@(gmail\\.com|yahoo\\.com|example\\.com)$";
        Pattern pattern = Pattern.compile(emailPattern);
        Matcher matcher = pattern.matcher(email);
        return matcher.matches();
    }

    // Method to validate username
    private boolean isValidUsername(String username) {
        // Define a regex pattern for valid usernames (e.g., only letters, numbers, and underscores, 3–15 characters)
        String usernamePattern = "^[a-zA-Z0-9_]{3,15}$";
        Pattern pattern = Pattern.compile(usernamePattern);
        Matcher matcher = pattern.matcher(username);
        return matcher.matches();
    }
}
