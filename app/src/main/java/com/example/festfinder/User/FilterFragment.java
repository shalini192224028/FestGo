package com.example.festfinder.User;

import android.app.DatePickerDialog;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.SeekBar;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.festfinder.R;
import com.example.festfinder.databinding.FilterBinding;

import java.util.Calendar;

public class FilterFragment extends Fragment {
    FilterBinding binding;

    private Button datePickerButton, resetButton, applyButton;
    private SeekBar priceRangeSeekbar;
    private Spinner locationSpinner;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding = FilterBinding.inflate(inflater, container, false);

        // Initialize UI elements
        datePickerButton = binding.datePickerButton;
        resetButton = binding.resetButton;
        applyButton = binding.applyButton;
        priceRangeSeekbar = binding.priceRangeSeekbar;
        locationSpinner = binding.locationSpinner;

        // Set up Date Picker
        datePickerButton.setOnClickListener(v -> showDatePicker());

        // Set up Spinner
        setupLocationSpinner();

        // Reset Filters
        resetButton.setOnClickListener(v -> resetFilters());

        // Apply Filters
        applyButton.setOnClickListener(v -> applyFilters());

        return binding.getRoot();
    }

    private void setupLocationSpinner() {
        // Populate spinner with location options
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(
                requireContext(),
                R.array.location_list,
                android.R.layout.simple_spinner_item
        );
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        locationSpinner.setAdapter(adapter);

        // Handle selection
        locationSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                // Display selected location
                String selectedLocation = parent.getItemAtPosition(position).toString();
                Toast.makeText(requireContext(), "Selected: " + selectedLocation, Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                // Do nothing when no item is selected
            }
        });
    }

    private void showDatePicker() {
        Calendar calendar = Calendar.getInstance();
        DatePickerDialog datePickerDialog = new DatePickerDialog(
                requireContext(),
                (view, year, month, dayOfMonth) -> {
                    String selectedDate = dayOfMonth + "/" + (month + 1) + "/" + year;
                    datePickerButton.setText(selectedDate);
                },
                calendar.get(Calendar.YEAR),
                calendar.get(Calendar.MONTH),
                calendar.get(Calendar.DAY_OF_MONTH)
        );
        datePickerDialog.show();
    }

    private void resetFilters() {
        locationSpinner.setSelection(0); // Reset spinner to "All Locations"
        priceRangeSeekbar.setProgress(0);
        datePickerButton.setText("Choose from Calendar");
        Toast.makeText(requireContext(), "Filters reset", Toast.LENGTH_SHORT).show();
    }

    private void applyFilters() {
        String location = locationSpinner.getSelectedItem().toString();
        int priceRange = priceRangeSeekbar.getProgress();
        Toast.makeText(requireContext(), "Filters Applied\nLocation: " + location + "\nPrice: ₹" + priceRange, Toast.LENGTH_SHORT).show();
    }
}
