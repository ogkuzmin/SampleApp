package com.example.devnull.sampleapp;

import android.app.Application;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.util.Log;

import com.example.devnull.sampleapp.presentation.languagepreference.Languages;

import java.util.Locale;

import io.realm.Realm;

public class SampleApplication extends Application {

    private static final String LOG_TAG = SampleApplication.class.getSimpleName();

    @Override
    public void onCreate() {
        super.onCreate();
        Realm.init(this);
        setUpLocale();
    }

    private void setUpLocale() {
        int localeIndex = Languages.getSelectedValue(this);
        Log.d(LOG_TAG, "::setUpLocale() with indx " + localeIndex);
        String language;
        switch (localeIndex) {

            case Languages.DEFAULT_VALUE:
                language = Locale.getDefault().getLanguage();
                break;

            case Languages.ENGLISH_VALUE:
                language = Locale.ENGLISH.getLanguage();
                break;

            case Languages.RUSSIAN_VALUE:
                language = "ru";
                break;

            default:
                language = Locale.getDefault().getLanguage();
        }
        setLocale(language);
    }

    private void setLocale(String language) {
        Resources res = getResources();
        Configuration config = res.getConfiguration();
        config.setLocale(new Locale(language));
        res.updateConfiguration(config, res.getDisplayMetrics());
        Log.d(LOG_TAG, "Set local with language " + language);
    }
}
