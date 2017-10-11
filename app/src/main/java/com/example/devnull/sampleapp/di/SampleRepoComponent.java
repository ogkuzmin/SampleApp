package com.example.devnull.sampleapp.di;

import com.example.devnull.sampleapp.data.SampleRepo;
import com.example.devnull.sampleapp.presentation.addnewsampleitem.EditOrAddItemPresenter;
import com.example.devnull.sampleapp.presentation.samplelist.SampleListPresenter;

import javax.inject.Singleton;

import dagger.Component;


@Component(modules = SampleRepoModule.class)
@Singleton
public interface SampleRepoComponent {

    SampleRepo provideRepo();

    void inject(SampleListPresenter presenter);

    void inject(EditOrAddItemPresenter presenter);
}
