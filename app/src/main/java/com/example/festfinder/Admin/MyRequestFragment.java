package com.example.festfinder.Admin;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.festfinder.R;
import com.example.festfinder.constant.Static;
import com.example.festfinder.databinding.MyRequestBinding;
import com.example.festfinder.databinding.MyRequestRcvBinding;
import com.example.festfinder.service.Constants;
import com.example.festfinder.service.Service;
import com.example.festfinder.service.response.MyRequestResponse;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MyRequestFragment extends Fragment {

    private MyRequestBinding binding;
    private FragmentActivity activity;
    private Context context;
    private MyRequestAdapter adapter;
    private List<MyRequestData> dataList = new ArrayList<>();

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = MyRequestBinding.inflate(inflater, container, false);

        try {
            context = requireContext();
            activity = requireActivity();
        } catch (Exception e) {
            e.printStackTrace();
        }

        // Set up RecyclerView
        adapter = new MyRequestAdapter(dataList, activity);
        binding.recyclerViewEvents.setLayoutManager(new LinearLayoutManager(activity));
        binding.recyclerViewEvents.setAdapter(adapter);

        // Fetch data from API
        fetchEventData();

        binding.ApproveButton.setOnClickListener(v -> {
            fetchApprovedEvent();
        });
        binding.RejectButton.setOnClickListener(v -> {
            fetchRejectedEvent();
        });

        binding.PendingButton.setOnClickListener(v -> {
            fetchRejectedEvent();
        });

        return binding.getRoot();
    }

    /**
     * Fetches event data from the API and updates the RecyclerView.
     */
    private void fetchEventData() {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(Constants.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        Service apiService = retrofit.create(Service.class);

        apiService.fetchEvents().enqueue(new Callback<MyRequestResponse>() {
            @Override
            public void onResponse(Call<MyRequestResponse> call, Response<MyRequestResponse> response) {
                if (response.isSuccessful() && response.body() != null) {
                    List<MyRequestResponse.EventDetails> events = response.body().getData();

                    // Convert API data to RecyclerView data model
                    List<MyRequestData> newData = new ArrayList<>();
                    for (MyRequestResponse.EventDetails event : events) {
                        newData.add(new MyRequestData(
                                event.getEvent_id(),
                                R.drawable.img_profilem, // Placeholder for image, update if the API provides URLs
                                event.getOrganizer(),
                                event.getTitle(),
                                event.getDescription()
                        ));
                    }

                    // Update RecyclerView with new data
                    updateEventData(newData);
                } else {
                    Log.e("API_ERROR", "Response unsuccessful or empty");
                }
            }

            @Override
            public void onFailure(Call<MyRequestResponse> call, Throwable t) {
                Log.e("API_ERROR", "Failed to fetch events", t);
            }
        });
    }


    private void fetchRejectedEvent() {
        dataList.clear();
        adapter.notifyDataSetChanged();
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(Constants.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        Service apiService = retrofit.create(Service.class);

        apiService.fetchEventreject().enqueue(new Callback<MyRequestResponse>() {
            @Override
            public void onResponse(Call<MyRequestResponse> call, Response<MyRequestResponse> response) {
                if (response.isSuccessful() && response.body() != null) {
                    List<MyRequestResponse.EventDetails> events = response.body().getData();

                    // Convert API data to RecyclerView data model
                    List<MyRequestData> newData = new ArrayList<>();
                    for (MyRequestResponse.EventDetails event : events) {
                        newData.add(new MyRequestData(
                                event.getEvent_id(),
                                R.drawable.img_profilem, // Placeholder for image, update if the API provides URLs
                                event.getOrganizer(),
                                event.getTitle(),
                                event.getDescription()
                        ));
                    }

                    // Update RecyclerView with new data
                    updateEventData(newData);
                } else {
                    Log.e("API_ERROR", "Response unsuccessful or empty");
                }
            }

            @Override
            public void onFailure(Call<MyRequestResponse> call, Throwable t) {
                Log.e("API_ERROR", "Failed to fetch events", t);
            }
        });
    }

    private void fetchApprovedEvent() {
        dataList.clear();
        adapter.notifyDataSetChanged();
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(Constants.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        Service apiService = retrofit.create(Service.class);

        apiService.fetchEventapprove().enqueue(new Callback<MyRequestResponse>() {
            @Override
            public void onResponse(Call<MyRequestResponse> call, Response<MyRequestResponse> response) {
                if (response.isSuccessful() && response.body() != null) {
                    List<MyRequestResponse.EventDetails> events = response.body().getData();

                    // Convert API data to RecyclerView data model
                    List<MyRequestData> newData = new ArrayList<>();
                    for (MyRequestResponse.EventDetails event : events) {
                        newData.add(new MyRequestData(
                                event.getEvent_id(),
                                R.drawable.img_profilem, // Placeholder for image, update if the API provides URLs
                                event.getOrganizer(),
                                event.getTitle(),
                                event.getDescription()
                        ));
                    }

                    // Update RecyclerView with new data
                    updateEventData(newData);
                } else {
                    Log.e("API_ERROR", "Response unsuccessful or empty");
                }
            }

            @Override
            public void onFailure(Call<MyRequestResponse> call, Throwable t) {
                Log.e("API_ERROR", "Failed to fetch events", t);
            }
        });
    }

    /**
     * Updates the event data dynamically and refreshes the adapter.
     */
    public void updateEventData(List<MyRequestData> newData) {
        if (newData != null && adapter != null) {
            dataList.clear();
            dataList.addAll(newData);
            adapter.notifyDataSetChanged();
        }
    }

    /**
     * Data model class for events.
     */
    private static class MyRequestData {
        private final int image;
        private final int id;
        private final String name;
        private final String title;
        private final String description;

        public MyRequestData(int id, int image, String name, String title, String description) {
            this.id = id;
            this.image = image;
            this.name = name;
            this.title = title;
            this.description = description;
        }

        public int getImage() {
            return image;
        }

        public String getName() {
            return name;
        }

        public String getTitle() {
            return title;
        }

        public String getDescription() {
            return description;
        }

        public int getId() {
            return id;
        }
    }

    /**
     * RecyclerView Adapter for displaying event data.
     */
    private class MyRequestAdapter extends RecyclerView.Adapter<MyRequestAdapter.MyViewHolder> {

        private final List<MyRequestData> list;
        private final FragmentActivity activity;

        public MyRequestAdapter(List<MyRequestData> list, FragmentActivity activity) {
            this.list = list;
            this.activity = activity;
        }

        @NonNull
        @Override
        public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            MyRequestRcvBinding binding = MyRequestRcvBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false);
            return new MyViewHolder(binding);
        }

        @Override
        public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
            MyRequestData data = list.get(position);
            Glide.with(activity).load(data.getImage()).into(holder.binding.requestAvatar);
            holder.binding.requestname.setText(data.getName());
            holder.binding.requestTitle.setText(data.getTitle());
            holder.binding.requestDescription.setText(data.getDescription());
            holder.binding.textContent.setOnClickListener(v -> {
                Bundle bundle = new Bundle();
                bundle.putInt("eventId", data.getId());
                Static.adminnavi(activity, R.id.action_myRequestFragment_to_adminRequestFragment, bundle);
            });
        }

        @Override
        public int getItemCount() {
            return list.size();
        }

        public class MyViewHolder extends RecyclerView.ViewHolder {
            private final MyRequestRcvBinding binding;

            public MyViewHolder(MyRequestRcvBinding binding) {
                super(binding.getRoot());
                this.binding = binding;
            }
        }
    }
}
