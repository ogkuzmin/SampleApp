package com.example.devnull.sampleapp.presentation.languagepreference;

import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;

import com.hannesdorfmann.mosby3.mvp.MvpBasePresenter;

import static com.example.devnull.sampleapp.presentation.languagepreference.Languages.*;

public class LanguagePreferencePresenter extends MvpBasePresenter<LanguagePreferenceView> {

    private static final String LOG_TAG = LanguagePreferencePresenter.class.getSimpleName();

    public void performItemClick(Context context, int newValue) {

        Log.d(LOG_TAG, "::performItemClick() with value " + newValue);

        int value = getSelectedValue(context);

        if (value != newValue) {
            SharedPreferences preferences = context.getSharedPreferences(PREFERENCE_DESCRIPTOR, 0);
            preferences.edit().putInt(PREFERENCE_KEY_NAME, newValue).commit();
            getView().showChangesAfterRebootDialog();
        }
    }

    public int getSelectedValue(Context context) {
        return Languages.getSelectedValue(context);
    }
}
