package com.example.devnull.sampleapp;

import android.app.Application;

import io.realm.Realm;

public class SampleApplication extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        Realm.init(this);
    }
}
