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
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.festfinder.R;
import com.example.festfinder.constant.Static;
import com.example.festfinder.databinding.EventListBinding;
import com.example.festfinder.databinding.EventListRcvBinding;
import com.example.festfinder.service.Constants;
import com.example.festfinder.service.Service;
import com.example.festfinder.service.response.FetchEventResponse;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class EventFragment extends Fragment {

    private EventListBinding binding;
    private Context context;
    private EventAdapter adapter;
    private FragmentActivity activity;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = EventListBinding.inflate(inflater, container, false);
        context = requireContext();

        setupRecyclerView();
        Bundle bundle = getArguments();
        if(bundle!=null) {
            String eventType = bundle.getString("eventType");
            fetchEventsFromApi(eventType);
        }

        return binding.getRoot();
    }

    private void setupRecyclerView() {
        activity = requireActivity();
        adapter = new EventAdapter(context, activity);
        binding.recyclerViewEvents.setLayoutManager(new LinearLayoutManager(context));
        binding.recyclerViewEvents.setAdapter(adapter);
    }

    private void fetchEventsFromApi(String eventType) {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(Constants.BASE_URL) // Replace with your API base URL
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        Service eventApi = retrofit.create(Service.class);

        eventApi.getEvents(eventType).enqueue(new Callback<FetchEventResponse>() {
            @Override
            public void onResponse(@NonNull Call<FetchEventResponse> call, @NonNull Response<FetchEventResponse> response) {
                if (response.isSuccessful() && response.body() != null) {
                    adapter.updateData(response.body().getEvents());
                } else {
                    Toast.makeText(context, "Error: " + response.message(), Toast.LENGTH_SHORT).show();
                    Log.e("API Error", "Response Code: " + response.code());
                }
            }

            @Override
            public void onFailure(@NonNull Call<FetchEventResponse> call, @NonNull Throwable t) {
                Toast.makeText(context, "Failed to fetch data: " + t.getMessage(), Toast.LENGTH_SHORT).show();
                Log.e("API Error", "Failed to fetch data", t);
            }
        });
    }

    private static class EventAdapter extends RecyclerView.Adapter<EventAdapter.MyViewHolder> {

        private final Context context;
        private final FragmentActivity activity;
        private List<FetchEventResponse.EventDetails> events = new ArrayList<>();

        public EventAdapter(Context context, FragmentActivity activity) {
            this.context = context;
            this.activity = activity;
        }

        public void updateData(List<FetchEventResponse.EventDetails> newEvents) {
            this.events = newEvents;
            notifyDataSetChanged();
        }

        @NonNull
        @Override
        public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            EventListRcvBinding binding = EventListRcvBinding.inflate(LayoutInflater.from(context), parent, false);
            return new MyViewHolder(binding);
        }

        @Override
        public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
            FetchEventResponse.EventDetails event = events.get(position);

            // Load event image
            Glide.with(context).load(Constants.BASE_URL + "uploads/" + event.getPosterUrl())
                    .into(holder.binding.eventImage);

            // Set event details
            holder.binding.eventTitle1.setText(event.getTitle());
            holder.binding.eventDate.setText(String.valueOf(event.getDate()));
            holder.binding.eventTime.setText(String.valueOf(event.getTime()));

            // Set click listener for navigation
            View.OnClickListener navigateToDetails = v -> {
                Bundle bundle = new Bundle();
                bundle.putInt("eventId", event.getEvent_id());
                Static.navigate(activity, R.id.action_eventListFragment_to_event_detail, bundle);
            };

            holder.binding.textContent.setOnClickListener(navigateToDetails);
            holder.binding.eventCard.setOnClickListener(navigateToDetails);
        }

        @Override
        public int getItemCount() {
            return events.size();
        }

        public static class MyViewHolder extends RecyclerView.ViewHolder {
            EventListRcvBinding binding;

            public MyViewHolder(EventListRcvBinding binding) {
                super(binding.getRoot());
                this.binding = binding;
            }
        }
    }
}
