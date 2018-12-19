package com.example.bawa.testapp;

import android.app.Application;

import com.firebase.client.Firebase;

public class TestApp extends Application {
    public void onCreate(){
        super.onCreate();
        Firebase.setAndroidContext(this);
    }
}
