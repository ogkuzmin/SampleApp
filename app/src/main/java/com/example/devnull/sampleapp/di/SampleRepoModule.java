package com.example.devnull.sampleapp.di;

import com.example.devnull.sampleapp.data.SampleRepo;
import com.example.devnull.sampleapp.data.SampleRepoImpl;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

@Singleton
@Module
public class SampleRepoModule {

    @Provides
    @Singleton
    SampleRepo provideRepo() {
        return new SampleRepoImpl();
    }
}
