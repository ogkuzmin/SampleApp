package com.example.devnull.sampleapp.di;

import com.example.devnull.sampleapp.data.SampleRepo;
import com.example.devnull.sampleapp.data.SampleRepoImpl;

import dagger.Module;
import dagger.Provides;

@Module
public class SampleRepoModule {

    @Provides
    static SampleRepo provideRepo() {
        return new SampleRepoImpl();
    }
}
