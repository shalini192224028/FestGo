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
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;

import com.example.festfinder.R;
import com.example.festfinder.constant.Static;
import com.example.festfinder.databinding.EventRegistrationBinding;
import com.example.festfinder.service.Constants;
import com.example.festfinder.service.RestClient;
import com.example.festfinder.service.Service;
import com.example.festfinder.service.response.MyRequest;
import com.example.festfinder.service.response.RegistrationResponse;
import com.example.festfinder.service.response.SingleEventResposne;


import java.io.IOException;

import okhttp3.MediaType;

import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class EventRegistrationFragment extends Fragment {
    public static final String KEY_EVENT_ID = "eventId";
    public static final String KEY_PRICE = "price";
    public static final String KEY_USER_ID = "UserId";
    public static final String KEY_USERNAME = "UserName";
    public static final String KEY_EMAIL_ID = "EmailId";
    public static final String KEY_MEMBER = "selectedMember";



    private EventRegistrationBinding binding;
    private FragmentActivity activity;
    private Context context;
    private int selectedMember;
    private int eventId;
    private int price;
    private int UserId, organizerId;
    private String UserName;
    private String  EmailId;
    private Spinner memberSpinner;


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        binding = EventRegistrationBinding.inflate(inflater, container, false);

        try {
            activity = requireActivity();
            context = requireContext();
        } catch (Exception e) {
            e.printStackTrace();
        }

        SharedPreferences LoginSp=activity.getSharedPreferences("UserDetail",Context.MODE_PRIVATE);
        UserId=LoginSp.getInt("UserId",0);
        UserName=LoginSp.getString("Username","");
        EmailId=LoginSp.getString("EmailId","");
        binding.username.setText(UserName);
        binding.emailId.setText(EmailId);

        // Retrieve event ID and price from arguments
        Bundle bundle = getArguments();
        if (bundle != null) {
            eventId = bundle.getInt(KEY_EVENT_ID, -1);
            price = bundle.getInt(KEY_PRICE, 0);
//            UserId = bundle.getInt(KEY_USER_ID, 0);
//            UserName = bundle.getString(KEY_USERNAME, "");
//            EmailId = bundle.getString(KEY_EMAIL_ID, "");
            selectedMember = bundle.getInt(KEY_MEMBER, 0);
            organizerId = bundle.getInt("organizer_id", 0);
            Toast.makeText(activity, ""+organizerId, Toast.LENGTH_SHORT).show();

            if (eventId != -1) {
                fetchEventData(eventId);
            } else {
                Toast.makeText(activity, "Invalid Event ID", Toast.LENGTH_SHORT).show();
            }
        } else {
            Toast.makeText(activity, "Event ID is null", Toast.LENGTH_SHORT).show();
        }

        // Setup the member spinner
        memberSpinner = binding.memberSpinner;
        setupMemberSpinner();

        // Handle Pay button click
        binding.paybutton.setOnClickListener(v -> {
            if (bundle != null) {
                bundle.putInt(KEY_PRICE, price);
                bundle.putString(KEY_USERNAME,UserName);
                bundle.putString(KEY_EMAIL_ID,EmailId);
                bundle.putInt(KEY_MEMBER,selectedMember);
                bundle.putInt(KEY_USER_ID,UserId);
                bundle.putInt("organizer_id",organizerId);

                Intent intent = new Intent(context, PaymentFragment.class);
                intent.putExtra("bundle", bundle);

                startActivity(intent);
            } else {
                Toast.makeText(activity, "No event data to proceed with payment", Toast.LENGTH_SHORT).show();
            }
        });

        return binding.getRoot();
    }

    private void fetchEventData(int eventId) {
        // Initialize Retrofit
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(Constants.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        Service apiService = retrofit.create(Service.class);

        // Fetch event details by ID
        apiService.fetchEventById(eventId).enqueue(new Callback<SingleEventResposne>() {
            @Override
            public void onResponse(Call<SingleEventResposne> call, Response<SingleEventResposne> response) {
                if (response.isSuccessful() && response.body() != null && response.body().getEvent() != null) {
                    updateUIWithEventDetails(response.body().getEvent());
                } else {
                    Toast.makeText(context, "No event details found", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<SingleEventResposne> call, Throwable t) {
                Toast.makeText(context, "Error fetching data", Toast.LENGTH_SHORT).show();
                Log.e("API_ERROR", "Failed to fetch events for event ID: " + eventId, t);
            }
        });
    }

    private void updateUIWithEventDetails(SingleEventResposne.EventDetail eventDetails) {
        if (binding != null && eventDetails != null) {
            binding.eventName.setText(eventDetails.getTitle() != null ? eventDetails.getTitle() : "N/A");
            binding.date.setText(eventDetails.getDate() != null ? eventDetails.getDate() : "N/A");
            binding.time.setText(eventDetails.getTime() != null ? eventDetails.getTime() : "N/A");
            binding.location.setText(eventDetails.getLocation() != null ? eventDetails.getLocation() : "N/A");
            binding.paymentfee.setText(String.valueOf(eventDetails.getRegistration_fee()));

        }
    }

    private void setupMemberSpinner() {
        // Populate spinner with member options
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(
                requireContext(),
                R.array.memberSpinner, // Ensure this array exists in strings.xml
                android.R.layout.simple_spinner_item
        );
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        memberSpinner.setAdapter(adapter);

        // Handle spinner selection
        memberSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String selectedM = parent.getItemAtPosition(position).toString();
                Toast.makeText(requireContext(), "Selected: " + selectedM, Toast.LENGTH_SHORT).show();
                price = price * Integer.parseInt(selectedM);
                selectedMember = Integer.parseInt(selectedM);
                binding.paymentfee.setText(String.valueOf(price));
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                // Do nothing
            }
        });
    }


    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}
