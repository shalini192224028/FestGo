package com.example.festfinder.Admin;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;

import com.bumptech.glide.Glide;
import com.example.festfinder.R;
import com.example.festfinder.databinding.AddDialogLayoutBinding;
import com.example.festfinder.databinding.AdminRequestBinding;
import com.example.festfinder.service.Constants;
import com.example.festfinder.service.Service;
import com.example.festfinder.service.response.AdminRequestResponse;
import com.example.festfinder.service.response.ApproveRejectResponse;
import com.example.festfinder.service.response.MyRequest;
import com.example.festfinder.service.response.SingleEventResposne;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class AdminRequestFragment extends Fragment {

    private AdminRequestBinding binding;
    private FragmentActivity activity;
    private Context context;

    int eventId;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        binding = AdminRequestBinding.inflate(inflater, container, false);

        try {
            activity = requireActivity();
            context = requireContext();
        } catch (Exception e) {
            e.printStackTrace();
        }

        // Fetch data and update UI
        Bundle bundle = getArguments();
        if(bundle!=null) {
            eventId = bundle.getInt("eventId");
            fetchEventData(eventId);
        } else Toast.makeText(activity, "eventId null", Toast.LENGTH_SHORT).show();

        return binding.getRoot();
    }

    private void fetchEventData(int eventId) {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(Constants.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        Service apiService = retrofit.create(Service.class);

        apiService.fetchEventById(eventId).enqueue(new Callback<SingleEventResposne>() {
            @Override
            public void onResponse(Call<SingleEventResposne> call, Response<SingleEventResposne> response) {
                if (response.isSuccessful() && response.body() != null) {

                    if (response.body().getEvent()!=null) {
                        updateUIWithEventDetails(response.body().getEvent());
                    } else {
                        Toast.makeText(context, "No events found", Toast.LENGTH_SHORT).show();
                        Log.e("API_RESPONSE", "No events in response");
                    }
                } else {
                    Toast.makeText(context, "Failed to fetch data", Toast.LENGTH_SHORT).show();
                    Log.e("API_ERROR", "Response unsuccessful or empty");
                }
            }

            @Override
            public void onFailure(Call<SingleEventResposne> call, Throwable t) {
                Toast.makeText(context, "Error fetching data", Toast.LENGTH_SHORT).show();
                Log.e("API_ERROR", "Failed to fetch events", t);
            }
        });
    }
    private void approveOrRejectEvent(int eventId, String action) {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(Constants.BASE_URL) // Replace with actual base URL
                .addConverterFactory(GsonConverterFactory.create())
                .build();
                 binding.approvesstatus.setVisibility(View.GONE);
                 binding.rejectstatus.setVisibility(View.GONE);
        Service apiService = retrofit.create(Service.class);

        MyRequest.ApproveRejectRequest request = new MyRequest.ApproveRejectRequest(eventId, action);

        apiService.approveRejectEvent(request).enqueue(new Callback<ApproveRejectResponse>() {
            @Override
            public void onResponse(Call<ApproveRejectResponse> call, Response<ApproveRejectResponse> response) {
                if (response.isSuccessful() && response.body() != null) {

                    Toast.makeText(context, response.body().getMessage(), Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(context, "Failed to update status", Toast.LENGTH_SHORT).show();
                    Log.e("API_ERROR", "Response unsuccessful or empty");
                }
            }

            @Override
            public void onFailure(Call<ApproveRejectResponse> call, Throwable t) {
                Toast.makeText(context, "Error updating status", Toast.LENGTH_SHORT).show();
                Log.e("API_ERROR", "Failed to update event status", t);
            }
        });
    }


    private void updateUIWithEventDetails(SingleEventResposne.EventDetail eventDetails) {
        if (eventDetails != null) {
            binding.organizer.setText(eventDetails.getOrganizer());
            binding.eventName.setText(eventDetails.getTitle());
            binding.description.setText(eventDetails.getDescription());
            binding.date.setText(eventDetails.getDate());
            binding.time.setText(eventDetails.getTime());
            binding.location.setText(eventDetails.getLocation());
            binding.eventType.setText(eventDetails.getEvent_type());
            binding.registrationFee.setText(String.valueOf(eventDetails.getRegistration_fee()));
            binding.email.setText(eventDetails.getEmail());
            binding.phone.setText(eventDetails.getPhone());
            Toast.makeText(context, eventDetails.getStatus(), Toast.LENGTH_SHORT).show();
            if (eventDetails.getStatus().equalsIgnoreCase("pending")){
                binding.approvereject.setVisibility(View.VISIBLE);
            }
            else{
                binding.approvereject.setVisibility(View.GONE);
            }
            binding.viewPosterButton.setOnClickListener(v -> {
                Dialog dialog = new Dialog(context);
                AddDialogLayoutBinding binding = AddDialogLayoutBinding.inflate(dialog.getLayoutInflater());
                dialog.setContentView(binding.getRoot());
                dialog.getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
                dialog.show();
                Glide.with(context).load(Constants.BASE_URL+"uploads/"+eventDetails.getPoster_url()).into(binding.proveImageView);
            });
            binding.viewPermissionLetterButton.setOnClickListener(v -> {
                Dialog dialog = new Dialog(context);
                AddDialogLayoutBinding binding = AddDialogLayoutBinding.inflate(dialog.getLayoutInflater());
                dialog.setContentView(binding.getRoot());
                dialog.getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
                dialog.show();
                Glide.with(context).load(Constants.BASE_URL+"uploads/"+eventDetails.getPermission_letter_url()).into(binding.proveImageView);
            });
            binding.approvesstatus.setOnClickListener(v -> approveOrRejectEvent(eventId, "approve"));
            binding.rejectstatus.setOnClickListener(v -> approveOrRejectEvent(eventId, "reject"));
        }
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}
