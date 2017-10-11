package com.example.devnull.sampleapp.di;

import com.example.devnull.sampleapp.data.GalleryRepo;
import com.example.devnull.sampleapp.presentation.Gallery.GalleryPresenter;

import javax.inject.Singleton;

import dagger.Component;

@Component(modules = GalleryRepoModule.class)
@Singleton
public interface GalleryRepoComponent {

    @Singleton
    GalleryRepo provideGalleryRepo();

    void inject(GalleryPresenter presenter);
}
