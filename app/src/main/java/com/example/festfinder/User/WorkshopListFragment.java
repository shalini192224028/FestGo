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
import com.example.festfinder.constant.Static;
import com.example.festfinder.databinding.WorkshopListBinding;
import com.example.festfinder.databinding.WorkshopListRcvBinding;

import java.util.ArrayList;
import java.util.List;

public class WorkshopListFragment extends Fragment {

    WorkshopListBinding binding;
    FragmentActivity activity;
    Context context;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding = WorkshopListBinding.inflate(inflater, container, false);

        try {
            context = requireContext();
            activity = requireActivity();
        } catch (Exception e) {
            e.printStackTrace();
        }

        List<WorkshopListData> data = new ArrayList<>();
        data.add(new WorkshopListData(R.drawable.img_codewar, "25 DEC", "10.00 am", "Code boot"));
        data.add(new WorkshopListData(R.drawable.img_codewar, "date", "time", "title"));
        data.add(new WorkshopListData(R.drawable.img_codewar, "date", "time", "title"));
        data.add(new WorkshopListData(R.drawable.img_codewar, "date", "time", "title"));
        data.add(new WorkshopListData(R.drawable.img_codewar, "date", "time", "title"));

        WorkshopListAdapter adapter = new WorkshopListAdapter(data, activity);
        binding.recyclerViewEvents.setLayoutManager(new LinearLayoutManager(activity));
        binding.recyclerViewEvents.setAdapter(adapter);

        return binding.getRoot();
    }

    private class WorkshopListData {
        private int image;
        private String date;
        private String time;
        private String name;

        public WorkshopListData(int image, String date, String time, String name) {
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

    private class WorkshopListAdapter extends RecyclerView.Adapter<WorkshopListAdapter.MyViewHolder>{

        List<WorkshopListData> list;
        FragmentActivity activity;

        public WorkshopListAdapter(List<WorkshopListData> list, FragmentActivity activity) {
            this.list = list;
            this.activity = activity;
        }

        @NonNull
        @Override
        public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            WorkshopListRcvBinding binding1 = WorkshopListRcvBinding.inflate(activity.getLayoutInflater(), parent, false);
            return new MyViewHolder(binding1);
        }

        @Override
        public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
            WorkshopListData data = list.get(position);
            Glide.with(activity).load(data.getImage()).into(holder.binding.workshopImage);
            holder.binding.workshopTitle1.setText(data.getName());
            holder.binding.workshopDate.setText(data.getDate());
            holder.binding.workshopTime.setText(data.getTime());
            holder.binding.workshopCard.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
//                    startActivity(new Intent(requireActivity(), EventDetailFragment.class));
                    Static.navigate(activity, R.id.action_eventListFragment_to_event_detail);
                }
            });
        }

        @Override
        public int getItemCount() {
            return list.size();
        }

        public class MyViewHolder extends RecyclerView.ViewHolder {
            WorkshopListRcvBinding binding;
            public MyViewHolder(WorkshopListRcvBinding binding) {
                super(binding.getRoot());
                this.binding = binding;

            }
        }

    }

}
