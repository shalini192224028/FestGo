package com.example.festfinder.User;

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
import com.example.festfinder.constant.Static;
import com.example.festfinder.databinding.EventDetailBinding;
import com.example.festfinder.service.Constants;
import com.example.festfinder.service.Service;
import com.example.festfinder.service.response.SingleEventResposne;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class EventDetailFragment extends Fragment {

    private EventDetailBinding binding;
    private FragmentActivity activity;
    private Context context;
    private int eventId;
    private int organizerId;

    Bundle bundle;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {

        binding = EventDetailBinding.inflate(inflater, container, false);

        try {
            activity = requireActivity();
            context = requireContext();
        } catch (Exception e) {
            e.printStackTrace();
        }

        // Fetch data and update UI
        bundle = getArguments();
        if (bundle != null) {
            eventId = bundle.getInt("eventId");
//            organizerId = bundle.getInt("organizer_id");
            bundle.putInt("eventId", eventId);
            bundle.putInt("organizer_id", organizerId);
            fetchEventData(eventId);
            fetchEventData(organizerId);
        } else {
            Toast.makeText(activity, "Event ID is null", Toast.LENGTH_SHORT).show();
        }


        binding.register.setOnClickListener(v ->
                Static.navigate(activity, R.id.action_event_detail_to_eventRegistrationFragment4,bundle)
        );

        binding.filter.setOnClickListener(v ->
                Static.navigate(activity, R.id.action_event_detail_to_filterFragment3)
        );

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

                    if (response.body().getEvent() != null) {
                        updateUIWithEventDetails(response.body().getEvent());
                    } else {
                        Toast.makeText(context, "No events found", Toast.LENGTH_SHORT).show();
                        Log.e("API_RESPONSE", "No events in response");
                    }
                } else {
//                    Toast.makeText(context, "Failed to fetch data", Toast.LENGTH_SHORT).show();
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

    private void updateUIWithEventDetails(SingleEventResposne.EventDetail eventDetails) {
        if (eventDetails != null) {
            binding.organizer.setText(eventDetails.getOrganizer());
            organizerId = eventDetails.getOrganizer_id();
//            Toast.makeText(activity, ""+organizerId, Toast.LENGTH_SHORT).show();
            bundle.putInt("organizer_id", organizerId);
            binding.eventName.setText(eventDetails.getTitle());
            binding.description.setText(eventDetails.getDescription());
            binding.date.setText(eventDetails.getDate());
            binding.time.setText(eventDetails.getTime());
            binding.location.setText(eventDetails.getLocation());
            bundle.putInt("price", eventDetails.getRegistration_fee());
            binding.registrationFee.setText(String.valueOf(eventDetails.getRegistration_fee()));
            binding.email.setText(eventDetails.getEmail());
            binding.phone.setText(eventDetails.getPhone());
            Glide.with(context).load(Constants.BASE_URL + "uploads/" + eventDetails.getPoster_url()).into(binding.posterImage);
        }
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}
