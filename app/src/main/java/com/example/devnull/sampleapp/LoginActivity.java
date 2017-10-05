package com.example.devnull.sampleapp;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;

import com.example.devnull.sampleapp.presentation.navigationdrawer.NavigationDrawerActivity;

public class LoginActivity extends Activity {
    private static final String LOG_TAG = LoginActivity.class.getSimpleName();

    private Button mLoginButton;
    private AlertDialog mAlertDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        mLoginButton = (Button) findViewById(R.id.loginButton);
        mLoginButton.setOnClickListener(v -> new ShortTimeDelayLoginAsyncTask().execute());
    }

    private void showDialogWithProgressbar() {
        AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(this);
        dialogBuilder.setCancelable(false);
        dialogBuilder.setView(R.layout.progress_dialog_with_login);
        mAlertDialog = dialogBuilder.show();
    }

    private void dismissDialogAndStartNavigationBarActivity() {
        if (mAlertDialog != null && mAlertDialog.isShowing()) {
            mAlertDialog.dismiss();
            Intent startActivity = new Intent(this, NavigationDrawerActivity.class);
            startActivity(startActivity);
        } else {
            Log.e(LOG_TAG, "Failed to dismiss null dialog.");
        }
    }

    private class ShortTimeDelayLoginAsyncTask extends AsyncTask<Void, Void, Void> {

        @Override
        protected void onPreExecute() {
            LoginActivity.this.showDialogWithProgressbar();
        }

        @Override
        protected Void doInBackground(Void... voids) {
            try {
                Thread.currentThread().sleep(3000);
            } catch (InterruptedException ex) {
                Log.e(LOG_TAG, "Current thread was interrupted! ");
            }
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            dismissDialogAndStartNavigationBarActivity();
        }
    }
}
