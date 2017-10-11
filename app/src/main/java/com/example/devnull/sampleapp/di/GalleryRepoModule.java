package com.example.devnull.sampleapp.di;

import com.example.devnull.sampleapp.data.GalleryRepo;
import com.example.devnull.sampleapp.data.GalleryRepoImpl;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

@Singleton
@Module
class GalleryRepoModule {

    @Provides
    @Singleton
    GalleryRepo provideGalleryRepo() {
        return new GalleryRepoImpl();
    }
}
