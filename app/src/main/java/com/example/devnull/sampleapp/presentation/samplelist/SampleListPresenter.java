package com.example.devnull.sampleapp.presentation.samplelist;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.AnyThread;
import android.support.annotation.UiThread;
import android.util.Log;

import com.example.devnull.sampleapp.data.SampleRepo;
import com.example.devnull.sampleapp.di.DaggerSampleRepoComponent;
import com.example.devnull.sampleapp.domain.SampleEntity;
import com.example.devnull.sampleapp.presentation.addnewsampleitem.EditOrAddItemActivity;
import com.hannesdorfmann.mosby3.mvp.MvpBasePresenter;

import java.util.List;

import javax.inject.Inject;

import io.reactivex.Single;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;


public class SampleListPresenter extends MvpBasePresenter<SampleListView> implements SampleRepo.Listener{

    private static final String LOG_TAG = SampleListPresenter.class.getSimpleName();

    @Inject
    SampleRepo repo;

    public SampleListPresenter() {
        DaggerSampleRepoComponent.builder().build().inject(this);
        repo.s
    }

    @Override
    public void onRepoChanged() {
        requestDataAndSetToView();
    }

    @UiThread
    public void requestDataAndSetToView() {
        getView().showLoading(false);
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

    @UiThread
    public void startAddingActivity(Context context) {
        Intent intent = new Intent(context, EditOrAddItemActivity.class);
        context.startActivity(intent);
        Log.d(LOG_TAG, "::startAddingActivity()");
    }
}
