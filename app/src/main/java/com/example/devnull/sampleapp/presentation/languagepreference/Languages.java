package com.example.devnull.sampleapp.presentation.languagepreference;

import android.content.Context;
import android.content.SharedPreferences;

import com.example.devnull.sampleapp.R;

import java.util.Locale;

public class Languages {

    public static final String PREFERENCE_DESCRIPTOR = "language_pref_desc";
    public static final String PREFERENCE_KEY_NAME = "language_pref";

    public static final int DEFAULT_RES_CODE = R.string.default_language_value;
    public static final int DEFAULT_VALUE = 0;
    public static final int ENGLISH_RES_CODE = R.string.english_language_value;
    public static final int ENGLISH_VALUE = 1;
    public static final int RUSSIAN_RES_CODE = R.string.russian_language_value;
    public static final int RUSSIAN_VALUE = 2;

    private Languages() { }

    public static int getSelectedValue(Context context) {
        SharedPreferences preferences = context.getSharedPreferences(PREFERENCE_DESCRIPTOR, 0);
        int value = DEFAULT_VALUE;
        if (preferences != null) {
            value = preferences.getInt(PREFERENCE_KEY_NAME, DEFAULT_VALUE);
        }
        return value;
    }
}
