package com.example.devnull.sampleapp.presentation.gallery.fullscreenimageviewer;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import com.example.devnull.sampleapp.R;
import com.github.chrisbanes.photoview.PhotoView;

/**
 * Full screen activity to show image as a gallery view.
 */

public class FullScreenImageViewerActivity extends AppCompatActivity {

    public static final String IMAGE_PATH_EXTRA_KEY_NAME = "image_path_key";

    private static final String LOG_TAG = FullScreenImageViewerActivity.class.getSimpleName();

    private PhotoView mImageView;
    private String mImagePath;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        View decorView = getWindow().getDecorView();
        // Hide the status bar.
        int uiOptions = View.SYSTEM_UI_FLAG_FULLSCREEN;
        decorView.setSystemUiVisibility(uiOptions);
        getSupportActionBar().hide();
        setContentView(R.layout.full_screen_image_layout);
        mImageView = (PhotoView) findViewById(R.id.image_view_container);

        if (savedInstanceState == null) {
            setBitmapResToImageView();
        }
    }

    private void setBitmapResToImageView() {
        mImagePath = getIntent().getStringExtra(IMAGE_PATH_EXTRA_KEY_NAME);
        Bitmap bitmap = BitmapFactory.decodeFile(mImagePath);
        mImageView.setImageBitmap(bitmap);
    }

}
