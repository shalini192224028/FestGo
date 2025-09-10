package com.simats.festgo_subscription.User;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.simats.festgo_subscription.Admin.AdminDashBoardActivity;
import com.simats.festgo_subscription.Organizer.OrganizerDashBoardActivity;
import com.simats.festgo_subscription.databinding.LoginBinding;
import com.simats.festgo_subscription.service.RestClient;
import com.simats.festgo_subscription.service.Service;
import com.simats.festgo_subscription.service.response.Response;
import com.simats.festgo_subscription.service.response.MyRequest;

import retrofit2.Call;
import retrofit2.Callback;

public class LoginActivity extends AppCompatActivity {

    private LoginBinding binding;
    private Service apiService;

    SharedPreferences sf;
    SharedPreferences.Editor editor;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = LoginBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        sf = getSharedPreferences("LOGIN", Context.MODE_PRIVATE);
        editor = sf.edit();

        // Initialize the API service using RestClient

        // Handle Login button click
        binding.loginButton.setOnClickListener(v -> {
            String emailid = binding.email.getText().toString().trim();
            String password = binding.password.getText().toString().trim();

            if (emailid.isEmpty() || password.isEmpty()) {
                Toast.makeText(this, "Please enter email and password", Toast.LENGTH_SHORT).show();
                return;
            }

            loginUser(emailid, password);
        });
    }

    private void loginUser(String emailid, String password) {
        // Create the request object
        MyRequest.LoginRequest Login = new MyRequest.LoginRequest(emailid, password);

        // Make the API call using the Service object
        Service apiService = RestClient.getApiClient().create(Service.class);

        Call<Response.LoginResponse> call = apiService.login(Login);
        call.enqueue(new Callback<Response.LoginResponse>() {
            @Override
            public void onResponse(Call<Response.LoginResponse> call, retrofit2.Response<Response.LoginResponse> response) {
                if (response.isSuccessful() && response.body() != null) {
                    Response.LoginResponse responseBody = response.body();

                    editor.putInt("userId", responseBody.getUser().getUserId()).apply();
                    // Check the user type and redirect accordingly
                    String userType = responseBody.getUser().getUser_type(); // Assuming the response contains a "userType" field
                    if ("user".equalsIgnoreCase(userType)) {
                        startActivity(new Intent(LoginActivity.this, UserSideNavActivity.class));
                    } else if ("organizer".equalsIgnoreCase(userType)) {
                        startActivity(new Intent(LoginActivity.this, OrganizerDashBoardActivity.class));
                    } else if ("admin".equalsIgnoreCase(userType)) {
                        startActivity(new Intent(LoginActivity.this, AdminDashBoardActivity.class));
                    } else {
                        Toast.makeText(LoginActivity.this, "Invalid user type", Toast.LENGTH_SHORT).show();
                        return;
                    }

                    SharedPreferences LoginSp=getSharedPreferences("UserDetail",Context.MODE_PRIVATE);
                    SharedPreferences.Editor User=LoginSp.edit();
                    User.putInt("UserId",response.body().getUser().getUserId());
                    User.putString("Username",response.body().getUser().getUsername());
                    User.putString("EmailId",response.body().getUser().getEmailid());
                    User.apply();
                    Toast.makeText(LoginActivity.this, responseBody.getMessage(), Toast.LENGTH_SHORT).show();
                    finish();

                } else {
                    // Handle API error response
                    Toast.makeText(LoginActivity.this, "Login Failed: " + response.message(), Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<Response.LoginResponse> call, Throwable t) {
                // Handle network or other errors
                Toast.makeText(LoginActivity.this, "Error: " + t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    public void skip(View V) {
        Intent i = new Intent(this, UserSideNavActivity.class);
        startActivity(i);
    }
    public void signup(View V) {
        Intent i = new Intent(this, SignUpActivity.class);
        startActivity(i);
    }
}
