package com.example.devnull.sampleapp.di;

import com.example.devnull.sampleapp.data.SampleRepo;

import dagger.Component;

@Component(modules = SampleRepoModule.class)
public interface SampleRepoComponent {
    SampleRepo provideRepo();
}
