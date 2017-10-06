package com.example.devnull.sampleapp.presentation.samplelist;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.UiThread;
import android.util.Log;
import android.view.View;

import com.example.devnull.sampleapp.R;
import com.example.devnull.sampleapp.data.SampleRepo;
import com.example.devnull.sampleapp.di.DaggerSampleRepoComponent;
import com.example.devnull.sampleapp.di.SampleRepoComponent;
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
        SampleRepoComponent component = DaggerSampleRepoComponent.builder().build();
        component.inject(this);
        Log.d(LOG_TAG, "Constructor");
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

        Log.d(LOG_TAG, "::postDataToView() posting list data with size " + data.size());

        SampleListView view = getView();

        if (view == null)
            return;

        view.setData(data);
        view.showContent();
    }

    @UiThread
    public void startAddingActivity(Context context, SampleItemView itemView) {
        Intent intent = new Intent(context, EditOrAddItemActivity.class);
        context.startActivity(intent);
        Log.d(LOG_TAG, "::startAddingActivity()");
    }

    public void performClickOnItemView(View view) {
        SampleItemView itemView = (SampleItemView) view.getRootView();

        switch (view.getId()){
            case R.id.clickable_content_frame:
                startAddingActivity(view.getContext(), itemView);
                break;

            case R.id.checkbox:
                performUpdateEntity(itemView);
                break;

        }
    }

    private void performUpdateEntity(SampleItemView itemView) {

    }
}
