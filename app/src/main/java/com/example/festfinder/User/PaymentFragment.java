package com.example.festfinder.User;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;

import com.example.festfinder.R;
import com.example.festfinder.constant.Static;
import com.example.festfinder.databinding.PaymentBinding;
import com.example.festfinder.service.Constants;
import com.example.festfinder.service.RestClient;
import com.example.festfinder.service.Service;
import com.example.festfinder.service.response.MyRequest;
import com.example.festfinder.service.response.RegistrationResponse;
import com.example.festfinder.service.response.SingleEventResposne;
import com.razorpay.Checkout;
import com.razorpay.PaymentResultListener;

import org.json.JSONException;
import org.json.JSONObject;


import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class PaymentFragment extends AppCompatActivity implements PaymentResultListener {

    PaymentBinding binding;
    FragmentActivity activity;
    Context context;
    private String UserName;
    private String EmailId;
    private int selectedMember;
    private int UserId, organizerId;
    private int eventId;


    int price = 100;
    @Nullable
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = PaymentBinding.inflate(getLayoutInflater());

        setContentView(binding.getRoot());
        try {
            activity = this;
            context  = this;
        } catch (Exception e) {
            e.printStackTrace();
        }

        Intent intent = getIntent();
        if(intent!=null) {
            Bundle bundle = intent.getBundleExtra("bundle");
            if(bundle!=null) {
                price = bundle.getInt("price");
                UserName = bundle.getString("UserName");
                EmailId = bundle.getString("EmailId");
                selectedMember = bundle.getInt("selectedMember");
                eventId = bundle.getInt("eventId");
                UserId = bundle.getInt("UserId");
                organizerId = bundle.getInt("organizer_id");
                binding.teamFee.setText(""+price);
            }
        }

        binding.buttonPayNow.setOnClickListener(v -> {
            payment("com.example.festfinder", price, "shalini", "shalini@gmail.com");
        });

    }
    private void updateUIWithEventDetails(SingleEventResposne.EventDetail eventDetails) {
        if (eventDetails != null) {
            binding.buttonPayNow.setText(String.valueOf(eventDetails.getRegistration_fee()));
        }
    }
    @Override
    public void onPaymentSuccess(String s) {
        Toast.makeText(this, "Payment Success", Toast.LENGTH_SHORT).show();
        apiCall(UserName,UserId,EmailId,eventId,organizerId, selectedMember,s,price);
    }

    private void apiCall(String name, int user_id,String email_id, int event_id,
                         int organizer_id, int no_of_member, String transaction_id,
                         double payment_amt) {
        Service apiService = RestClient.getApiClient().create(Service.class);
        MyRequest.RegistrationRequest request = new MyRequest.RegistrationRequest(name,user_id, email_id, event_id, organizer_id, no_of_member, transaction_id, payment_amt);
        apiService.registration(request).enqueue(new Callback<RegistrationResponse>() {
            @Override
            public void onResponse(Call<RegistrationResponse> call, Response<RegistrationResponse> response) {
                if(response.isSuccessful()) {
                    Toast.makeText(activity, response.body().getMessage(), Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(activity, response.message(), Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<RegistrationResponse> call, Throwable t) {
                Toast.makeText(activity, t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void payment(String packageName, long amount,String username,String userEmail)
    {
        // initialize Razorpay account.
        Checkout checkout = new Checkout();

        // set your id as below
        checkout.setKeyID("rzp_test_U2XWpODmhRkL0l" +
                "");

        // set image
//        checkout.setImage(R.drawable.profile);

        // initialize json objecthis
        JSONObject object = new JSONObject();
        try {
            // to put name
            object.put("name", username);

            // put description
            object.put("description", "Test payment");

            // to set theme color
            object.put("theme.color", "");

            // put the currency
            object.put("currency", "INR");

            // put amount
            object.put("amount", (amount*100));

            // put mobile number
            object.put("prefill.contact", "");

            // put package name
            object.put("package name",packageName);

            // put email
            object.put("prefill.email", userEmail);

            // open razorpay to checkout activity
            checkout.open(this, object);
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }


    @Override
    public void onPaymentError(int i, String s) {
        Toast.makeText(this,"Payment Failure",Toast.LENGTH_SHORT).show();
    }

}
