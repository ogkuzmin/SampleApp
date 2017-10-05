package com.example.devnull.sampleapp.di;

import com.example.devnull.sampleapp.data.SampleRepo;
import com.hannesdorfmann.mosby3.mvp.MvpBasePresenter;

import javax.inject.Singleton;

import dagger.Component;


@Component(modules = SampleRepoModule.class)
@Singleton
public interface SampleRepoComponent {

    SampleRepo provideRepo();

    void inject(Object object);
}
