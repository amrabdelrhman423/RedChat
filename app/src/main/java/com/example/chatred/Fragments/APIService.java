package com.example.chatred.Fragments;

import com.example.chatred.Notifications.Myresponse;
import com.example.chatred.Notifications.Sender;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Headers;
import retrofit2.http.POST;

public interface APIService {
    @Headers(
            {
                    "Content-Type:application/json",
                    "Authorization:key=AAAAfsMyte8:APA91bHMCnMs28TdiVDUt5bb_c1vg93JYLHg-6on5n_xfcRm6RIK1v6zgyW6X1j3aUYxXdegDfu4tR1gh7SRHCqSdnXjBkQT3J5hmj2KevHnNmachntmmMIKdORBlxxUYYzltcS8Pezw"
            }
    )

    @POST("fcm/send")
    Call<Myresponse>sendNotification(@Body Sender body);
}
