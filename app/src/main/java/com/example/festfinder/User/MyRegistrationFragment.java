package com.example.festfinder.User;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
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
import com.example.festfinder.databinding.MyRegistrationBinding;
import com.example.festfinder.databinding.MyRegistrationRcvBinding;
import com.example.festfinder.databinding.MyRegistrationRcvBinding;

import java.util.ArrayList;
import java.util.List;

public class MyRegistrationFragment extends Fragment {

    MyRegistrationBinding binding;
    FragmentActivity activity;
    Context context;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding = MyRegistrationBinding.inflate(inflater, container, false);

        try {
            context = requireContext();
            activity = requireActivity();
        } catch (Exception e) {
            e.printStackTrace();
        }

        List<MyRegistrationData>  data = new ArrayList<>();
        data.add(new MyRegistrationData(R.drawable.img_codeboot, "24 DEC", "10.00 am", "Code boot"));
        data.add(new MyRegistrationData(R.drawable.img_codeboot, "21 Jan", "9.00 am", "Java hackathon"));
        data.add(new MyRegistrationData(R.drawable.img_codeboot, "date", "time", "title"));
        data.add(new MyRegistrationData(R.drawable.img_codeboot, "date", "time", "title"));
        data.add(new MyRegistrationData(R.drawable.img_codeboot, "date", "time", "title"));

        MyRegistrationAdapter adapter = new MyRegistrationAdapter(data, activity);
        binding.recyclerViewEvents.setLayoutManager(new LinearLayoutManager(activity));
        binding.recyclerViewEvents.setAdapter(adapter);

        return binding.getRoot();
    }

    private class MyRegistrationData {
        private int image;
        private String date;
        private String time;
        private String name;

        public MyRegistrationData(int image, String date, String time, String name) {
            this.image = image;
            this.date = date;
            this.time = time;
            this.name = name;
        }

        public int getImage() {
            return image;
        }

        public String getDate() {
            return date;
        }

        public String getTime() {
            return time;
        }

        public String getName() {
            return name;
        }
    }

    private class MyRegistrationAdapter extends RecyclerView.Adapter<MyRegistrationAdapter.MyViewHolder>{

        List<MyRegistrationData> list;
        FragmentActivity activity;

        public MyRegistrationAdapter(List<MyRegistrationData> list, FragmentActivity activity) {
            this.list = list;
            this.activity = activity;
        }

        @NonNull
        @Override
        public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            MyRegistrationRcvBinding binding1 = MyRegistrationRcvBinding.inflate(activity.getLayoutInflater(), parent, false);
            return new MyViewHolder(binding1);
        }

        @Override
        public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
            MyRegistrationData data = list.get(position);
            Glide.with(activity).load(data.getImage()).into(holder.binding.eventImage);
            holder.binding.eventTitle1.setText(data.getName());
            holder.binding.eventDate.setText(data.getDate());
            holder.binding.eventTime.setText(data.getTime());
            holder.binding.eventCard.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    startActivity(new Intent(requireActivity(), EventDetailFragment.class));
                }
            });
        }

        @Override
        public int getItemCount() {
            return list.size();
        }

        public class MyViewHolder extends RecyclerView.ViewHolder {
            MyRegistrationRcvBinding binding;
            public MyViewHolder(MyRegistrationRcvBinding binding) {
                super(binding.getRoot());
                this.binding = binding;

            }
        }

    }

}
