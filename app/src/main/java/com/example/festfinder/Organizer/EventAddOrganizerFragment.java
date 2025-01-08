package com.example.festfinder.Organizer;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;

import com.example.festfinder.R;
import com.example.festfinder.databinding.EventAddOrganizerBinding;
import com.example.festfinder.service.RestClient;
import com.example.festfinder.service.Service;
import com.example.festfinder.service.response.AddEventResponse;

import java.io.File;
import java.io.IOException;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class EventAddOrganizerFragment extends Fragment {

    EventAddOrganizerBinding binding;
    FragmentActivity activity;
    Context context;

    private static final int IMAGE_REQUEST_CODE = 101;
    private static final int REQUEST_CODE_READ_STORAGE = 100;
    private Uri posterUri;
    private Uri permissionLetterUri;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {

        binding = EventAddOrganizerBinding.inflate(inflater, container, false);
        activity = requireActivity();
        context = requireContext();

        binding.postLay.setOnClickListener(v -> {
            openGallery(IMAGE_REQUEST_CODE); // Open gallery for poster
        });

        binding.proofLay.setOnClickListener(v -> {
            openGallery(IMAGE_REQUEST_CODE + 1); // Open gallery for permission letter
        });

        binding.requestButton.setOnClickListener(v -> {
            submitEventForm();
        });

        // Set up the spinner for event types
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(context,
                R.array.event_types, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        binding.eventTypeSpinner.setAdapter(adapter);

        binding.eventTypeSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parentView, View selectedItemView, int position, long id) {
                String selectedEventType = parentView.getItemAtPosition(position).toString();
                Log.i("selectedSpinnerItem", selectedEventType);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parentView) {
                // Handle no selection
            }
        });

        // Check for external storage permissions
        if (ContextCompat.checkSelfPermission(getContext(), Manifest.permission.READ_EXTERNAL_STORAGE)
                != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(getActivity(),
                    new String[]{Manifest.permission.READ_EXTERNAL_STORAGE},
                    REQUEST_CODE_READ_STORAGE);
        }

        return binding.getRoot();
    }

    private void openGallery(int requestCode) {
        Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        startActivityForResult(intent, requestCode);
    }

    // Method to get the file from URI
    public File getFileFromUri(Uri uri) {
        String[] filePathColumn = {MediaStore.Images.Media.DATA};
        Cursor cursor = getActivity().getContentResolver().query(uri, filePathColumn, null, null, null);
        if (cursor != null) {
            cursor.moveToFirst();
            int columnIndex = cursor.getColumnIndex(filePathColumn[0]);
            String filePath = cursor.getString(columnIndex);
            cursor.close();
            return new File(filePath);
        } else {
            return null; // Handle the case when the cursor is null
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (resultCode == getActivity().RESULT_OK && data != null) {
            Uri selectedImage = data.getData();

            if (requestCode == IMAGE_REQUEST_CODE) {
                posterUri = selectedImage;
                try {
                    Bitmap bitmap = MediaStore.Images.Media.getBitmap(getActivity().getContentResolver(), posterUri);
                    binding.imgEventPoster.setImageBitmap(bitmap);  // Set the image in ImageView
                } catch (IOException e) {
                    e.printStackTrace();
                }
            } else if (requestCode == IMAGE_REQUEST_CODE + 1) {
                permissionLetterUri = selectedImage;
                try {
                    Bitmap bitmap = MediaStore.Images.Media.getBitmap(getActivity().getContentResolver(), permissionLetterUri);
                    binding.imgProof.setImageBitmap(bitmap);  // Set the image in ImageView
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    private void submitEventForm() {
        // Validate input fields
        if (TextUtils.isEmpty(binding.eventName.getText()) ||
                TextUtils.isEmpty(binding.description.getText()) ||
                TextUtils.isEmpty(binding.organizer.getText()) ||
                TextUtils.isEmpty(binding.date.getText()) ||
                TextUtils.isEmpty(binding.time.getText()) ||
                TextUtils.isEmpty(binding.location.getText()) ||
                TextUtils.isEmpty(binding.eventTypeSpinner.getSelectedItem().toString()) ||
                TextUtils.isEmpty(binding.registrationFee.getText()) ||
                TextUtils.isEmpty(binding.email.getText()) ||
                TextUtils.isEmpty(binding.phone.getText())) {
            Toast.makeText(context, "All fields are required to add the event", Toast.LENGTH_SHORT).show();
            return;
        }

        SharedPreferences sf = activity.getSharedPreferences("LOGIN", Context.MODE_PRIVATE);
        int userId = sf.getInt("userId", 0);

        String organizer = binding.organizer.getText().toString().trim();
        String title = binding.eventName.getText().toString().trim();
        String description = binding.description.getText().toString().trim();
        String date = binding.date.getText().toString().trim();
        String time = binding.time.getText().toString().trim();
        String location = binding.location.getText().toString().trim();
        String eventType = binding.eventTypeSpinner.getSelectedItem().toString();
        String registrationFee = binding.registrationFee.getText().toString().trim();
        String email = binding.email.getText().toString().trim();
        String phone = binding.phone.getText().toString().trim();

        if (posterUri == null || permissionLetterUri == null) {
            Toast.makeText(context, "Both poster and permission letter are required", Toast.LENGTH_SHORT).show();
            return;
        }

        File posterFile = getFileFromUri(posterUri);
        File permissionLetterFile = getFileFromUri(permissionLetterUri);

        if (posterFile == null || permissionLetterFile == null) {
            Toast.makeText(context, "Error in fetching files", Toast.LENGTH_SHORT).show();
            return;
        }

        RequestBody userIdBody = RequestBody.create(MediaType.parse("text/plain"), String.valueOf(userId));
        RequestBody organizerBody = RequestBody.create(MediaType.parse("text/plain"), organizer);
        RequestBody titleBody = RequestBody.create(MediaType.parse("text/plain"), title);
        RequestBody descriptionBody = RequestBody.create(MediaType.parse("text/plain"), description);
        RequestBody dateBody = RequestBody.create(MediaType.parse("text/plain"), date);
        RequestBody timeBody = RequestBody.create(MediaType.parse("text/plain"), time);
        RequestBody locationBody = RequestBody.create(MediaType.parse("text/plain"), location);
        RequestBody eventTypeBody = RequestBody.create(MediaType.parse("text/plain"), eventType);
        RequestBody registrationFeeBody = RequestBody.create(MediaType.parse("text/plain"), registrationFee);
        RequestBody emailBody = RequestBody.create(MediaType.parse("text/plain"), email);
        RequestBody phoneBody = RequestBody.create(MediaType.parse("text/plain"), phone);

        MultipartBody.Part posterPart = MultipartBody.Part.createFormData("poster", posterFile.getName(),
                RequestBody.create(MediaType.parse("image/*"), posterFile));
        MultipartBody.Part permissionLetterPart = MultipartBody.Part.createFormData("permission_letter",
                permissionLetterFile.getName(), RequestBody.create(MediaType.parse("image/*"), permissionLetterFile));

        Service apiService = RestClient.getApiClient().create(Service.class);

        Call<AddEventResponse> call = apiService.addEvent(
                userIdBody,organizerBody, titleBody, descriptionBody, dateBody, timeBody, locationBody, eventTypeBody,
                registrationFeeBody, emailBody, phoneBody, posterPart, permissionLetterPart
        );

        call.enqueue(new Callback<AddEventResponse>() {
            @Override
            public void onResponse(Call<AddEventResponse> call, Response<AddEventResponse> response) {
                if (response.isSuccessful()) {
                    Log.i("succResponse", response.body().getMessage());
                    Toast.makeText(context, "Event Requested successfully!", Toast.LENGTH_SHORT).show();
                } else {
                    // Log the response code and the body
                    Log.e("APIError", "Response Code: " + response.code());
                    try {
                        Toast.makeText(context, "", Toast.LENGTH_SHORT).show();
                        Log.e("APIError", "Error Body: " + response.errorBody().string());
                    } catch (IOException e) {
                        Toast.makeText(context, "Failed to add event", Toast.LENGTH_SHORT).show();
                        Log.e("APIError", "Error reading error body: " + e.getMessage());
                    }
                }
            }

            @Override
            public void onFailure(Call<AddEventResponse> call, Throwable t) {
                Log.e("apiError", t.getMessage());
                Toast.makeText(context, "Request failed: " + t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }
}
