package com.example.devnull.sampleapp.presentation.samplelistui;

import com.example.devnull.sampleapp.data.SampleRepo;
import com.hannesdorfmann.mosby3.mvp.MvpBasePresenter;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;


public class SampleListPresenter extends MvpBasePresenter<SampleListView> implements SampleRepo.Listener{

    SampleRepo repo;

    @Override
    public void onRepoChanged() {
        requestDataAndSetToView();
    }

    public void requestDataAndSetToView() {
        Observable.just(repo.getAll())
                .observeOn(Schedulers.newThread())
                .subscribeOn(AndroidSchedulers.mainThread())
                .subscribe(data -> getView().setData(data));
    }
}
