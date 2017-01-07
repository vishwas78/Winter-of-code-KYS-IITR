package com.example.vishwasmittal.woc;

import android.app.Application;

import com.firebase.client.Firebase;

public class FirebaseAppConfig extends Application {

    public static String firebaseURL = "https://polar-casing-152812.firebaseio.com/";

    @Override
    public void onCreate() {
        super.onCreate();

        Firebase.setAndroidContext(this);
    }
}
