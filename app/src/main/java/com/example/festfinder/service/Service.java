package com.example.festfinder.service;
import com.example.festfinder.service.response.AddEventResponse;
import com.example.festfinder.service.response.AdminRequestResponse;
import com.example.festfinder.service.response.ApproveRejectResponse;
import com.example.festfinder.service.response.FetchEventResponse;
import com.example.festfinder.service.response.MyRequestResponse;
import com.example.festfinder.service.response.RegistrationResponse;
import com.example.festfinder.service.response.Response;
import com.example.festfinder.service.response.MyRequest;
import com.example.festfinder.service.response.SingleEventResposne;

import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;
import retrofit2.http.Query;

public interface Service {

    // Signup endpoint
    @POST("user/signup")
    Call<Response.SignUpResponse> signup(@Body MyRequest.SignupRequest request);

    // Signin endpoint
    @POST("user/login")
    Call<Response.LoginResponse> login(@Body MyRequest.LoginRequest request);


    @Multipart
    @POST("organiser/addevent")
    Call<AddEventResponse> addEvent(
            @Part("organizerId") RequestBody userIdBody,
            @Part("organizer") RequestBody organizer,
            @Part("title") RequestBody title,
            @Part("description") RequestBody description,
            @Part("date") RequestBody date,
            @Part("time") RequestBody time,
            @Part("location") RequestBody location,
            @Part("event_type") RequestBody eventType,
            @Part("registrationFee") RequestBody registrationFee,
            @Part("email") RequestBody email,
            @Part("phone") RequestBody phone,
            @Part MultipartBody.Part poster,
            @Part MultipartBody.Part permission_letter
    );

        @GET("organiser/fetchpending")
        Call<MyRequestResponse> fetchEvents();

        @GET("organiser/fetchapproved")
        Call<MyRequestResponse> fetchEventapprove();

        @GET("organiser/fetchrejected")
        Call<MyRequestResponse> fetchEventreject();

        @GET("organiser/fetchEventById")
        Call<SingleEventResposne> fetchEventById(@Query("id")int id);

        @POST("admin/approverejectevent")
        Call<ApproveRejectResponse> approveRejectEvent(@Body MyRequest.ApproveRejectRequest request);

        @GET("organiser/fetchbytype")
        Call<FetchEventResponse> getEvents(@Query("eventType")String eventType);

    @POST("user/addregistration")
    Call<RegistrationResponse> registration(
            @Body MyRequest.RegistrationRequest request
    );

    @GET("user/fetchregistrationById")
    Call<FetchEventResponse> fetchRegistrationById(@Query("id")int id);

}
