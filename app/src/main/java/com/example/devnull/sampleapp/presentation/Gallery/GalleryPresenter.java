package com.example.devnull.sampleapp.presentation.Gallery;

import com.example.devnull.sampleapp.data.GalleryRepo;
import com.example.devnull.sampleapp.di.DaggerGalleryRepoComponent;
import com.hannesdorfmann.mosby3.mvp.MvpBasePresenter;

import javax.inject.Inject;

public class GalleryPresenter extends MvpBasePresenter<GalleryView> {

    private static final String LOG_TAG = GalleryPresenter.class.getSimpleName();

    @Inject
    GalleryRepo mRepo;

    public GalleryPresenter() {
        DaggerGalleryRepoComponent.builder().build().inject(this);
    }
}
