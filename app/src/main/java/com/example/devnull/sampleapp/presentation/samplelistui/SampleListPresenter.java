package com.example.devnull.sampleapp.presentation.samplelistui;

import android.support.annotation.AnyThread;
import android.support.annotation.UiThread;

import com.example.devnull.sampleapp.data.SampleRepo;
import com.example.devnull.sampleapp.di.DaggerSampleRepoComponent;
import com.example.devnull.sampleapp.di.SampleRepoComponent;
import com.example.devnull.sampleapp.domain.SampleEntity;
import com.hannesdorfmann.mosby3.mvp.MvpBasePresenter;

import java.util.List;

import javax.inject.Inject;

import io.reactivex.Single;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;


public class SampleListPresenter extends MvpBasePresenter<SampleListView> implements SampleRepo.Listener{

    @Inject
    SampleRepo repo;

    public SampleListPresenter() {
        SampleRepoComponent repoComponent = DaggerSampleRepoComponent.builder().build();
        repo = repoComponent.provideRepo();
    }

    @Override
    public void onRepoChanged() {
        requestDataAndSetToView();
    }

    @AnyThread
    public void requestDataAndSetToView() {
        Single.just(repo.getAll())
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(data -> postDataToView(data));
    }

    @UiThread
    private void postDataToView(List<SampleEntity> data) {
        SampleListView view = getView();

        if (view == null)
            return;

        view.setData(data);
        view.showContent();
    }
}
