package com.example.devnull.sampleapp.presentation.Gallery;

import android.content.Intent;

import com.example.devnull.sampleapp.domain.GalleryFile;
import com.hannesdorfmann.mosby3.mvp.MvpView;

import java.util.List;

public interface GalleryView extends MvpView {

    void setData(List<GalleryFile> data);

    void startActivityByRequest(Intent intent, int request);

    void showFailToast();
}
